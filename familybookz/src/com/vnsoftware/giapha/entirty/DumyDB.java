package com.vnsoftware.giapha.entirty;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import com.vnsoftware.db.JDBCConnection;
import com.vnsoftware.giapha.entirty.Action.ACTION_TYPE;
import com.vnsoftware.giapha.entirty.Person.GENDER;
import com.vnsoftware.midle.BroadcastFeedService;

/**
 * @author LAP10572-local
 * 
 */
public class DumyDB {
	private static DumyDB instance;

	private DumyDB() {

	}

	public static DumyDB getInstance() {
		if (instance == null)
			instance = new DumyDB();
		return instance;
	}

	Map<Long, Person> persons = new HashMap<>();
	Map<Long, List<Feed>> feeds = new HashMap<>();

	public Person getPerson(long id, boolean loadChildren) throws Exception {
		String sql = "select * from profile where id=?";
		Connection con = JDBCConnection.getInstance().getConnection();
		PreparedStatement stm = null;
		ResultSet rs = null;
		Person p = null;
		try {
			stm = con.prepareStatement(sql);
			stm.setLong(1, id);
			rs = stm.executeQuery();
			while (rs.next()) {
				p = toPerson(rs, con, loadChildren);
			}
		} catch (Exception e) {

		} finally {
			if (stm != null)
				stm.close();
			if (rs != null)
				rs.close();
			con.close();
		}
		return p;
	}

	public Person getPersonByFBId(long id, boolean loadChildren) throws Exception {
		String sql = "select * from profile where facebook_id = ?";
		Connection con = JDBCConnection.getInstance().getConnection();
		PreparedStatement stm = null;
		ResultSet rs = null;
		Person p = null;
		try {
			stm = con.prepareStatement(sql);
			stm.setLong(1, id);
			rs = stm.executeQuery();
			while (rs.next()) {
				p = toPerson(rs, con, loadChildren);
			}
		} catch (Exception e) {

		} finally {
			if (stm != null)
				stm.close();
			if (rs != null)
				rs.close();
			con.close();
		}
		return p;
	}

	private Person toPerson(ResultSet rs, Connection con, boolean loadChildren) throws SQLException {
		Person p = new Person(rs.getLong("id"), rs.getString("surname") + rs.getString("firstname"), null, rs.getString("born_date"), null);
		if (rs.getBoolean("gender")) {
			p.setGender(GENDER.MALE);
		} else {
			p.setGender(GENDER.FEMALE);
		}
		p.setImage(rs.getString("image"));
		p.setAccessToken(rs.getString("access_token"));
		p.setFaceBookId(rs.getString("facebook_id"));
		p.setEmail(rs.getString("email"));
		p.setFirstName(rs.getString("firstname"));
		p.setSurname(rs.getString("surname"));
		p.setUserName(rs.getString("username"));
		p.setName(p.getFirstName() + " " + p.getSurname());
		List<Person> relations  = getRelation(con, p.getId(), new int[]{RELATION_TYPE.MOM.ordinal(),RELATION_TYPE.DAD.ordinal()});
		for(Person r : relations){
			if(r.getRelationType()==RELATION_TYPE.MOM.ordinal()){
				p.setMom(r.getId());
			}else if(r.getRelationType()==RELATION_TYPE.DAD.ordinal()){
				p.setDad(r.getId());
			}
		}
		if (loadChildren) {
			p.setChildren(getRelation(con, p.getId(), new int[]{RELATION_TYPE.KID.ordinal()}));
		}
		p.setOwnerId(rs.getLong("owner_id"));
		return p;
	}

	public Family login(String username, String password) throws Exception {
		String sql = "select * from profile where username=? and password = ?";
		Connection con = JDBCConnection.getInstance().getConnection();
		PreparedStatement stm = null;
		ResultSet rs = null;
		Person me = null;
		List<Person> enagedRelations = new ArrayList<>();
		List<Person> relations = null; 
		Family f = null;
		try {
			stm = con.prepareStatement(sql);
			stm.setString(1, username);
			stm.setString(2, password);
			rs = stm.executeQuery();
			while (rs.next()) {
				me = toPerson(rs, con, true);
			}
			if (me != null) {
				relations  = getRelation(con, me.getId(), new int[]{RELATION_TYPE.WIFE_OR_HUSBAND.ordinal(),RELATION_TYPE.MOM.ordinal(),RELATION_TYPE.DAD.ordinal()});
			}
			if (me != null) {
				for(Person r : relations){
					if(r.getRelationType()==RELATION_TYPE.MOM.ordinal()){
						me.setMom(r.getId());
					}else if(r.getRelationType()==RELATION_TYPE.DAD.ordinal()){
						me.setDad(r.getId());
					}else if(r.getRelationType()==RELATION_TYPE.WIFE_OR_HUSBAND.ordinal()){
						enagedRelations.add(r);
					}
				}
				f = new Family(me, enagedRelations, me.getChildren());
			}

		} finally {
			if (stm != null)
				stm.close();
			if (rs != null)
				rs.close();

			con.close();
		}
		return f;
	}

	

	private List<Person> getRelation(Connection con, long id, int[] types) throws SQLException {
		List<Person> relatedPerson = new ArrayList<>();
		ResultSet rs = null;
		StringBuilder strTypes  = new StringBuilder();
		for(int type : types){
			strTypes.append(type).append(",");
		}
		if(strTypes.length() > 0)
			strTypes.deleteCharAt(strTypes.length()-1);
		String sql = "SELECT p.*, re.relation_type FROM profile p, (select * from relation r where r.profile_a="+id+" and relation_type in ("+strTypes.toString()+")) re where re.profile_b = p.id;";
		PreparedStatement stm = con.prepareStatement(sql);
		try {
			rs = stm.executeQuery();
			while (rs.next()) {
				Person p = toPerson(rs, con, false);
				p.setRelationType(rs.getInt("relation_type"));
				relatedPerson.add(p);
			}
		} finally {
			if (rs != null)
				rs.close();
			if (stm != null)
				stm.close();
		}
		return relatedPerson;
	}

	public long addPerson(Person p) throws Exception {
		String sql = "insert into profile(firstname, surname,image,email,gender,phonenum,mom,dad,wifeorhusband_ids,facebook_id,access_token,born_date,passed_date,username,password, owner_id) values(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
		Connection con = JDBCConnection.getInstance().getConnection();
		con.setAutoCommit(false);
		PreparedStatement stm = null;
		ResultSet rs = null;
		long id = 0;
		try {
			stm = con.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS);
			stm.setString(1, p.getFirstName());
			stm.setString(2, p.getSurname());
			stm.setString(3, p.getImage());
			stm.setString(4, p.getEmail());
			stm.setInt(5, p.getGender().ordinal());
			stm.setString(6, p.getPhoneNum());
			stm.setLong(7, p.getMom());
			stm.setLong(8, p.getDad());
			stm.setString(9, p.getWifeorhusbandIds());
			stm.setString(10, p.getFaceBookId());
			stm.setString(11, p.getAccessToken());
			stm.setDate(12, new Date(System.currentTimeMillis()));
			stm.setDate(13, null);
			stm.setString(14, p.getUserName());
			stm.setString(15, p.getPassWorld());
			stm.setLong(16, p.getOwnerId());
			stm.execute();
			rs = stm.getGeneratedKeys();
			while (rs.next()) {
				id = rs.getLong(1);
			}
			con.commit();
			con.setAutoCommit(true);
		} finally {
			if (stm != null)
				stm.close();
			if (rs != null)
				rs.close();

			con.close();
		}
		return id;
	}

	public long createRelationRequest(RelationRequest req) throws Exception {
		String sql = "insert into relation_request(requesting_id, requested_id,request_msg,status,relation,requester_name,requester_image) values(?,?,?,?,?,?,?)";
		Connection con = JDBCConnection.getInstance().getConnection();
		PreparedStatement stm = null;
		ResultSet rs = null;
		long id = 0;
		try {
			stm = con.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS);
			stm.setLong(1, req.getRequestingId());
			stm.setLong(2, req.getRequestedId());
			stm.setString(3, req.getRequestMsg());
			stm.setString(4, req.getStatus());
			stm.setString(5, req.getRelation());
			stm.setString(6, req.getRequesterName());
			stm.setString(7, req.getRequesterImage());
			stm.execute();
			rs = stm.getGeneratedKeys();
			while (rs.next()) {
				id = rs.getLong(1);
			}
		} finally {
			if (stm != null)
				stm.close();
			if (rs != null)
				rs.close();

			con.close();
		}
		return id;
	}

	public void updateRelation(Person p) throws Exception {
		String sql = "update profile set wifeorhusband_ids=? , mom=?, dad=? WHERE id=?";
		Connection con = JDBCConnection.getInstance().getConnection();
		PreparedStatement stm = null;
		try {
			stm = con.prepareStatement(sql);
			stm.setString(1, p.getWifeorhusbandIds());
			stm.setLong(2, p.getMom());
			stm.setLong(3, p.getDad());
			stm.setLong(4, p.getId());
			stm.executeUpdate();
		} finally {
			if (stm != null)
				stm.close();
			con.close();
		}
	}

	public long postFeed(Feed f, Person p) throws Exception {
		String sql = "insert into feed(type,content,images,title,url,owner_id,owner_name,owner_avatar,description,website,owner_domain_name) values (?,?,?,?,?,?,?,?,?,?,?)";
		Connection con = JDBCConnection.getInstance().getConnection();
		PreparedStatement stm = null;
		ResultSet rs = null;
		long id = 0;
		try {
			stm = con.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS);
			stm.setInt(1, f.getType());
			stm.setString(2, f.getContent());
			StringBuilder images = new StringBuilder();
			if (f.getImages() != null && f.getImages().size() > 0) {
				for (String img : f.getImages()) {
					images.append(img).append(",");
				}
				stm.setString(3, images.substring(0, images.length() - 1));
			} else {
				stm.setString(3, null);
			}

			stm.setString(4, f.getTitle());
			stm.setString(5, f.getUrl());
			stm.setLong(6, f.getOwnerId());
			stm.setString(7, f.getOwnerInfo().getName());
			stm.setString(8, f.getOwnerInfo().getImage());
			stm.setString(9, f.getDesc());
			stm.setString(10, f.getWebsite());
			stm.setString(11, f.getOwnerInfo().getDomainName());
			stm.execute();
			rs = stm.getGeneratedKeys();
			while (rs.next()) {
				id = rs.getLong(1);
			}
			BroadcastFeedService.getInstance().broadCastFeeds(p, id);
		} finally {
			if (stm != null)
				stm.close();
			if (rs != null)
				rs.close();

			con.close();
		}
		return id;
	}

	SimpleDateFormat df = new SimpleDateFormat("dd MMM yyyy hh:mm");

	public List<Feed> getFeeds(long ownerId) throws Exception {
		Connection con = JDBCConnection.getInstance().getConnection();
		PreparedStatement stm = null;
		ResultSet rs = null;
		Map<Long, Feed> feeds = new HashMap<>();
		try {
			stm = con.prepareStatement("select * from feed where id in (select feed_id from profile_feeds_timeline where profile_id="+ownerId+") order by id desc");
			rs = stm.executeQuery();
			while (rs.next()) {
				Feed f = toFeed(rs);
				feeds.put(f.getId(), f);
			}
		} finally {
			if (stm != null)
				stm.close();
			if (rs != null)
				rs.close();
			con.close();
		}
		Map<Long, Action> acts = DumyDB.getInstance().getActions(feeds.keySet());
		getComments(acts);
		for (Long id : acts.keySet()) {
			feeds.get(id).setAct(acts.get(id));
		}
		ArrayList<Feed> r = new ArrayList<>(feeds.values());
		Collections.sort(r);
		return r;
	}

	private Feed toFeed(ResultSet rs) throws SQLException {
		Feed f = new Feed();
		f.setId(rs.getLong("id"));
		f.setContent(rs.getString("content"));
		f.setOwnerId(rs.getLong("owner_id"));
		String images = rs.getString("images");
		if (images != null && !images.isEmpty()) {
			List<String> imgs = new ArrayList<>();
			for (String s : images.split(",")) {
				imgs.add(s);
			}
			f.setImages(imgs);
		}
		f.setType(rs.getInt("type"));
		f.setUrl(rs.getString("url"));
		f.setDesc(rs.getString("description"));
		f.setWebsite(rs.getString("website"));
		f.setPostedDate(df.format(rs.getDate("created_date")));
		f.setTitle(rs.getString("title"));
		ShortProfile s = new ShortProfile(rs.getLong("owner_id"), rs.getString("owner_name"),rs.getString("owner_domain_name"), rs.getString("owner_avatar"));
		f.setOwnerInfo(s);
		return f;
	}

	public List<Feed> getMyFeeds(long ownerId) throws Exception {
		String sql = "SELECT * FROM feed  where owner_id = ? order by id desc";
		Connection con = JDBCConnection.getInstance().getConnection();
		PreparedStatement stm = null;
		ResultSet rs = null;
		Map<Long, Feed> feeds = new HashMap<>();
		try {
			stm = con.prepareStatement(sql);
			stm.setLong(1, ownerId);
			rs = stm.executeQuery();
			while (rs.next()) {
				Feed f = toFeed(rs);
				feeds.put(f.getId(), f);
			}
			Map<Long, Action> acts = DumyDB.getInstance().getActions(feeds.keySet());
			getComments(acts);
			for (Long id : acts.keySet()) {
				feeds.get(id).setAct(acts.get(id));
			}
		} finally {
			if (stm != null)
				stm.close();
			if (rs != null)
				rs.close();
			con.close();
		}
		ArrayList<Feed> r = new ArrayList<>(feeds.values());
		Collections.sort(r);
		return r;
	}

	public List<Person> search(String searchText) throws Exception {
		List<Person> result = new ArrayList<>();
		String sql = "select * from profile where firstname like ?";
		Connection con = JDBCConnection.getInstance().getConnection();
		PreparedStatement stm = null;
		ResultSet rs = null;
		try {
			stm = con.prepareStatement(sql);
			stm.setString(1, "%" + searchText + "%");
			rs = stm.executeQuery();
			while (rs.next()) {
				result.add(toPerson(rs, con, false));
			}
		} finally {
			if (stm != null)
				stm.close();
			if (rs != null)
				rs.close();
			con.close();
		}
		return result;
	}

	public Person getFamilyTree(Person me, int level) throws Exception {
		Person root = null;
		if (me.getDad() > 0) {
			root = getPerson(me.getDad(), true);
		} else if (me.getMom() > 0) {
			root = getPerson(me.getMom(), true);
		} else {
			root = me;
		}
		if (root.getChildren().contains(me)) {
			root.getChildren().remove(me);
			root.getChildren().add(me);
		}
		return root;
	}

	// Broadcast to mom, dad, brothers sisters and children
	public Set<Long> listToBroadcastFeed(Person p) throws Exception {
		Set<Long> ids = new HashSet<Long>();
		if (p.getDad() != 0) {
			ids.add(p.getDad());
			Person d = DumyDB.getInstance().getPerson(p.getDad(), true);
			if (d.getChildren() != null) {
				for (Person c : d.getChildren()) {
					if (!c.equals(p))
						ids.add(c.getId());
				}
			}
		}
		if (p.getMom() != 0) {
			ids.add(p.getMom());
			Person m = DumyDB.getInstance().getPerson(p.getMom(), true);
			if (m.getChildren() != null) {
				for (Person c : m.getChildren()) {
					if (!c.equals(p))
						ids.add(c.getId());
				}
			}
		}
		if (p.getChildren() != null) {
			for (Person c : p.getChildren()) {
				if (!c.equals(p))
					ids.add(c.getId());
			}
		}
		return ids;
	}

	public List<RelationRequest> getRetationRequests(long id) throws Exception {
		String sql = "select * from relation_request where status = 'requesting'  AND requested_id = " + id;
		Connection con = JDBCConnection.getInstance().getConnection();
		PreparedStatement stm = null;
		ResultSet rs = null;
		List<RelationRequest> request = new ArrayList<>();
		try {
			stm = con.prepareStatement(sql);
			rs = stm.executeQuery();
			while (rs.next()) {
				RelationRequest r = new RelationRequest(rs.getLong("id"), rs.getLong("requesting_id"), rs.getLong("requested_id"), rs.getString("request_msg"), rs.getString("relation"), rs.getString("status"), rs.getString("requester_name"), rs.getString("requester_image"));
				r.setRequestedDate(rs.getDate("requested_date"));
				request.add(r);
			}
		} finally {
			if (stm != null)
				stm.close();
			if (rs != null)
				rs.close();
			con.close();
		}
		return request;
	}

	public RelationRequest getRetationRequest(long requestId) throws Exception {
		String sql = "select * from relation_request where id = " + requestId;
		Connection con = JDBCConnection.getInstance().getConnection();
		PreparedStatement stm = null;
		ResultSet rs = null;
		RelationRequest r = null;
		try {
			stm = con.prepareStatement(sql);
			rs = stm.executeQuery();
			while (rs.next()) {
				r = new RelationRequest(rs.getLong("id"), rs.getLong("requesting_id"), rs.getLong("requested_id"), rs.getString("request_msg"), rs.getString("relation"), rs.getString("status"), rs.getString("requester_name"), rs.getString("requester_image"));
				r.setRequestedDate(rs.getDate("requested_date"));
			}
		} finally {
			if (stm != null)
				stm.close();
			if (rs != null)
				rs.close();
			con.close();
		}
		return r;
	}

	public void updateRequestStatus(long requestId, String status) throws Exception {
		String sql = "update relation_request set status ='" + status + "' where id = " + requestId;
		Connection con = JDBCConnection.getInstance().getConnection();
		PreparedStatement stm = con.prepareStatement(sql);
		try {
			stm.executeUpdate();
		} finally {
			if (stm != null)
				stm.close();
			con.close();
		}
	}

	enum RELATION_TYPE {
		MOM, DAD, PROTHER_OR_SYSTER, WIFE_OR_HUSBAND, KID
	}

	public void confirmRequest(long id) throws Exception {
		RelationRequest req = DumyDB.getInstance().getRetationRequest(id);
		if (req == null)
			return;
		Person p = DumyDB.getInstance().getPerson(req.getRequestedId(), false);
		Person requesting = DumyDB.getInstance().getPerson(req.getRequestingId(), false);
		String type = req.getRelation();
		String sql = "insert into relation(profile_a,profile_b,relation_type) values (?,?,?)";
		Connection con = JDBCConnection.getInstance().getConnection();
		con.setAutoCommit(false);
		PreparedStatement stm = con.prepareStatement(sql);

		try {

			stm.setLong(1, req.getRequestingId());
			stm.setLong(2, req.getRequestedId());
			// Update requesting person
			if ("mom".equals(type)) {
				requesting.setMom(p.getId());
				stm.setLong(3, RELATION_TYPE.MOM.ordinal());
				stm.addBatch();
				stm.setLong(1, req.getRequestedId());
				stm.setLong(2, req.getRequestingId());
				stm.setLong(3, RELATION_TYPE.KID.ordinal());
				stm.addBatch();
				addProtherAndSister(req.getRequestingId(), con, stm);
			} else if ("dad".equals(type)) {
				requesting.setDad(p.getId());
				stm.setLong(3, RELATION_TYPE.DAD.ordinal());
				stm.addBatch();
				stm.setLong(1, req.getRequestedId());
				stm.setLong(2, req.getRequestingId());
				stm.setLong(3, RELATION_TYPE.KID.ordinal());
				stm.addBatch();
				addProtherAndSister(req.getRequestingId(), con, stm);
			} else if ("wife".equals(type) || "husband".equals(type)) {
				stm.setLong(3, RELATION_TYPE.WIFE_OR_HUSBAND.ordinal());
				stm.addBatch();
				stm.setLong(1, req.getRequestedId());
				stm.setLong(2, req.getRequestingId());
				stm.setLong(3, RELATION_TYPE.WIFE_OR_HUSBAND.ordinal());
				stm.addBatch();
			}
			// update requested person
			if ("kid".equals(req.getRelation())) {
				stm.setLong(3, RELATION_TYPE.KID.ordinal());
				stm.addBatch();
				stm.setLong(1, req.getRequestedId());
				stm.setLong(2, req.getRequestingId());
				if (GENDER.MALE.equals(requesting.getGender())) {
					stm.setLong(3, RELATION_TYPE.DAD.ordinal());
				} else {
					stm.setLong(3, RELATION_TYPE.MOM.ordinal());
				}
				stm.addBatch();
				addProtherAndSister(req.getRequestedId(), con, stm);
			}
			stm.executeBatch();
			con.commit();
			updateRequestStatus(id, "confirmed");
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			stm.close();
			con.close();
		}
	}
	public void addRelation(long profileA, long profileB,String type, GENDER g) throws Exception{
		String sql = "insert into relation(profile_a,profile_b,relation_type) values (?,?,?)";
		Connection con = JDBCConnection.getInstance().getConnection();
		con.setAutoCommit(false);
		PreparedStatement stm = con.prepareStatement(sql);

		try {

			stm.setLong(1, profileA);
			stm.setLong(2, profileB);
			// Update requesting person
			if ("mom".equals(type)) {
				stm.setLong(3, RELATION_TYPE.MOM.ordinal());
				stm.addBatch();
				stm.setLong(1, profileB);
				stm.setLong(2, profileA);
				stm.setLong(3, RELATION_TYPE.KID.ordinal());
				stm.addBatch();
				addProtherAndSister(profileA, con, stm);
			} else if ("dad".equals(type)) {
				stm.setLong(3, RELATION_TYPE.DAD.ordinal());
				stm.addBatch();
				stm.setLong(1, profileB);
				stm.setLong(2, profileA);
				stm.setLong(3, RELATION_TYPE.KID.ordinal());
				stm.addBatch();
				addProtherAndSister(profileA, con, stm);
			} else if ("wife".equals(type) || "husband".equals(type)) {
				stm.setLong(3, RELATION_TYPE.WIFE_OR_HUSBAND.ordinal());
				stm.addBatch();
				stm.setLong(1, profileB);
				stm.setLong(2, profileA);
				stm.setLong(3, RELATION_TYPE.WIFE_OR_HUSBAND.ordinal());
				stm.addBatch();
			}
			// update requested person
			if ("kid".equals(type)) {
				stm.setLong(3, RELATION_TYPE.KID.ordinal());
				stm.addBatch();
				stm.setLong(1, profileB);
				stm.setLong(2, profileA);
				if (GENDER.MALE.equals(g)) {
					stm.setLong(3, RELATION_TYPE.DAD.ordinal());
				} else {
					stm.setLong(3, RELATION_TYPE.MOM.ordinal());
				}
				stm.addBatch();
				addProtherAndSister(profileB, con, stm);
			}
			stm.executeBatch();
			con.commit();
		}catch(Exception ex){
			ex.printStackTrace();
		}finally{
			stm.close();
			con.setAutoCommit(true);
			con.close();
		}
		
	}
	private void addProtherAndSister(long profile, Connection con, PreparedStatement stm) throws SQLException {
		List<Person> children = getRelation(con, profile, new int[]{RELATION_TYPE.KID.ordinal()});
		if(children == null || children.size() == 0)
			return;
		
		for(Person p1 : children){
			stm.setLong(1, profile);
			stm.setLong(2, p1.getId());
			stm.setLong(3, RELATION_TYPE.PROTHER_OR_SYSTER.ordinal());
			stm.addBatch();
			stm.setLong(1, p1.getId());
			stm.setLong(2, profile);
			stm.setLong(3, RELATION_TYPE.PROTHER_OR_SYSTER.ordinal());
			stm.addBatch();
		}
	}

	public OfflineEvent getOfflineEvents(long id) throws Exception {
		String offlineRelationRequest = "SELECT count(*) as pending_request FROM relation_request where status='requesting' and requested_id=" + id;
		Connection con = JDBCConnection.getInstance().getConnection();
		PreparedStatement stm = null;
		ResultSet rs = null;
		OfflineEvent e = new OfflineEvent();
		try {
			stm = con.prepareStatement(offlineRelationRequest);
			rs = stm.executeQuery();
			while (rs.next()) {
				e.setRelationRequestPending(rs.getInt("pending_request"));
			}
			rs.close();
		} finally {
			if (stm != null)
				stm.close();
			if (rs != null)
				rs.close();
			con.close();
		}
		return e;
	}

	public void clearofflineFeeds(long id) throws Exception {
		String sql = "delete from profile_unread_feeds where profile_id = " + id;
		Connection con = JDBCConnection.getInstance().getConnection();
		PreparedStatement stm = con.prepareStatement(sql);
		try {
			stm.execute();
		} finally {
			if (stm != null)
				stm.close();
			con.close();
		}
	}

	public void likeShareComment(Action action) throws Exception {
		String sqlUpdate = null;
		String sqlInsert = null;
		Connection con = JDBCConnection.getInstance().getConnection();
		PreparedStatement stm = null;
		try {

			if (ACTION_TYPE.LIKE.equals(action.getAct())) {
				sqlUpdate = "update like_share_comment set like_count = like_count + 1, like_ids=concat(like_ids,'," + action.getProfileId() + "') where item_id = " + action.getItemId();
				sqlInsert = "insert into like_share_comment(item_id,like_count,like_ids)values(" + action.getItemId() + ",1," + action.getProfileId() + ")";
			} else if (ACTION_TYPE.SHARE.equals(action.getAct())) {
				sqlUpdate = "update like_share_comment set share_count = share_count + 1, share_ids=concat(share_ids,'," + action.getProfileId() + "') where item_id = " + action.getItemId();
				sqlInsert = "insert into like_share_comment(item_id,share_count,share_ids)values(" + action.getItemId() + ",1," + action.getProfileId() + ")";
			} else if (ACTION_TYPE.COMMENT.equals(action.getAct())) {
				sqlUpdate = "update like_share_comment set comment_count = comment_count + 1, comment_ids=concat(comment_ids,'," + action.getCommetnIds() + "') where item_id = " + action.getItemId();
				sqlInsert = "insert into like_share_comment(item_id,comment_count,comment_ids)values(" + action.getItemId() + ",1," + action.getCommetnIds() + ")";
			}

			if (ACTION_TYPE.LIKE.equals(action.getAct()) || ACTION_TYPE.SHARE.equals(action.getAct())) {
				stm = con.prepareStatement(sqlUpdate);
				if (stm.executeUpdate() == 0) {
					stm.close();
					stm = con.prepareStatement(sqlInsert);
					stm.execute();
				}
			} else if (ACTION_TYPE.COMMENT.equals(action.getAct())) {
				stm = con.prepareStatement(sqlUpdate);
				if (stm.executeUpdate() == 0) {
					stm.close();
					stm = con.prepareStatement(sqlInsert);
					stm.execute();
				}
			}
		} finally {
			stm.close();
			con.close();
		}
	}

	public Map<Long, Action> getActions(Collection<Long> itemIds) throws Exception {
		Connection con = JDBCConnection.getInstance().getConnection();
		PreparedStatement stm = null;
		ResultSet rs = null;
		Map<Long, Action> result = new HashMap<>();
		try {
			StringBuilder ids = new StringBuilder();
			for (Long id : itemIds) {
				ids.append("'").append(id).append("'").append(",");
			}
			if (ids.length() > 0) {
				ids = ids.replace(ids.length() - 1, ids.length(), "");
				stm = con.prepareStatement("select * from like_share_comment where item_id in (" + ids.toString() + ")");
				rs = stm.executeQuery();
				while (rs.next()) {
					Action act = new Action(rs.getLong("item_id"), rs.getInt("like_count"), "", rs.getInt("share_count"), "", rs.getInt("comment_count"), "");
					act.setLikeIds(rs.getString("like_ids"));
					act.setShareIds(rs.getString("share_ids"));
					act.setCommentCount(rs.getInt("comment_count"));
					act.setCommetnIds(rs.getString("comment_ids"));
					result.put(act.getItemId(), act);
				}
			}

		} finally {
			if (stm != null)
				stm.close();
			if (rs != null)
				rs.close();
			con.close();
		}
		return result;
	}

	public List<ShortProfile> getShortProfiles(String ids) throws Exception {
		String sql = "SELECT * FROM profile  where id in (" + ids + ")";
		Connection con = JDBCConnection.getInstance().getConnection();
		PreparedStatement stm = null;
		ResultSet rs = null;
		List<ShortProfile> r = new ArrayList<>();
		try {
			stm = con.prepareStatement(sql);
			rs = stm.executeQuery();
			while (rs.next()) {
				r.add(new ShortProfile(rs.getLong("id"), rs.getString("firstname") + " " + rs.getString("surname"),rs.getString("username"), rs.getString("image")));
			}
		} finally {
			if (stm != null)
				stm.close();
			if (rs != null)
				rs.close();
			con.close();
		}
		return r;
	}

	public long postComment(Comment c) throws Exception {
		String sql = "insert into comment(owner_id,reply_comment_id,feed_id,content,profile_avatar,profile_name,title,url,image,description,type,commenter_domain_name) values (?,?,?,?,?,?,?,?,?,?,?,?)";
		Connection con = JDBCConnection.getInstance().getConnection();
		PreparedStatement stm = null;
		ResultSet rs = null;
		long id = 0;
		try {
			stm = con.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS);
			stm.setLong(1, c.getOwnerId());
			stm.setLong(2, c.getReplyCommentId());
			stm.setLong(3, c.getFeedId());
			stm.setString(4, c.getComment());
			stm.setString(5, c.getProfileAvatar());
			stm.setString(6, c.getProfileName());
			stm.setString(7, c.getTitle());
			stm.setString(8, c.getUrl());
			stm.setString(9, c.getImage());
			stm.setString(10, c.getDesc());
			stm.setInt(11, c.getType());
			stm.setString(12, c.getCommenterDomainName());
			stm.execute();
			rs = stm.getGeneratedKeys();
			while (rs.next()) {
				id = rs.getLong(1);
			}
			Action act = new Action(ACTION_TYPE.COMMENT, c.getOwnerId(), c.getFeedId());
			act.setCommetnIds(id + "");
			likeShareComment(act);
		} finally {
			if (stm != null)
				stm.close();
			if (rs != null)
				rs.close();

			con.close();
		}
		return id;
	}

	private void getComments(Map<Long, Action> feedActionMapping) throws Exception {
		Connection con = JDBCConnection.getInstance().getConnection();
		PreparedStatement stm = null;
		ResultSet rs = null;
		try {
			for (Entry<Long, Action> f : feedActionMapping.entrySet()) {
				if (f.getValue().getCommetnIds() != null && !f.getValue().getCommetnIds().isEmpty()) {
					String ids = f.getValue().getCommetnIds();
					if (ids.startsWith(","))
						ids = ids.substring(1, ids.length());
					String sql = "SELECT * FROM comment  where id in (" + ids + ")";
					stm = con.prepareStatement(sql);
					rs = stm.executeQuery();
					List<Comment> cmts = new ArrayList<>();
					while (rs.next()) {
						Comment c = new Comment(rs.getLong("id"), rs.getLong("reply_comment_id"), rs.getLong("owner_id"), rs.getLong("feed_id"), rs.getString("content"), rs.getString("profile_name"), rs.getString("commenter_domain_name"), rs.getString("profile_avatar"));
						c.setType(rs.getInt("type"));
						c.setTitle(rs.getString("title"));
						c.setDesc(rs.getString("description"));
						c.setUrl(rs.getString("url"));
						c.setImage(rs.getString("image"));
						c.setPostedDate(rs.getDate("post_date"));
						cmts.add(c);
					}
					f.getValue().setComments(cmts);
					rs.close();
					stm.close();
				}
			}

		} finally {
			if (stm != null)
				stm.close();
			if (rs != null)
				rs.close();
			con.close();
		}
	}

	private static List<Emoticon> emoticons = new ArrayList<Emoticon>();
	private static Map<Integer, Emoticon> emoticonsMap = new HashMap<Integer, Emoticon>();
	static {
		emoticons.add(new Emoticon("Smile", "/images/emoticons/angel.gif"));
		emoticons.add(new Emoticon("Smile", "/images/emoticons/angry.gif"));
		emoticons.add(new Emoticon("Smile", "/images/emoticons/bartlett_80_anim_gif.gif"));
		emoticons.add(new Emoticon("Smile", "/images/emoticons/beer_80_anim_gif.gif"));
		emoticons.add(new Emoticon("Smile", "/images/emoticons/bigsmile.gif"));
		emoticons.add(new Emoticon("Smile", "/images/emoticons/bike_80_anim_gif.gif"));
		emoticons.add(new Emoticon("Smile", "/images/emoticons/blushing.gif"));
		emoticons.add(new Emoticon("Smile", "/images/emoticons/bomb_80_anim_gif.gif"));
		emoticons.add(new Emoticon("Smile", "/images/emoticons/bow.gif"));
		emoticons.add(new Emoticon("Smile", "/images/emoticons/cake_80_anim_gif.gif"));
		emoticons.add(new Emoticon("Smile", "/images/emoticons/call_80_anim_gif.gif"));
		emoticons.add(new Emoticon("Smile", "/images/emoticons/cash_80_anim_gif.gif"));
		emoticons.add(new Emoticon("Smile", "/images/emoticons/cat_80_anim_gif.gif"));
		emoticons.add(new Emoticon("Smile", "/images/emoticons/clapping.gif"));
		emoticons.add(new Emoticon("Smile", "/images/emoticons/coffee_80_anim_gif.gif"));
		emoticons.add(new Emoticon("Smile", "/images/emoticons/cool.gif"));
		emoticons.add(new Emoticon("Smile", "/images/emoticons/crying.gif"));
		emoticons.add(new Emoticon("Smile", "/images/emoticons/dancing_80_anim_gif.gif"));
		emoticons.add(new Emoticon("Smile", "/images/emoticons/devil.gif"));
		emoticons.add(new Emoticon("Smile", "/images/emoticons/dog_80_anim_gif.gif"));
		emoticons.add(new Emoticon("Smile", "/images/emoticons/doh.gif"));
		emoticons.add(new Emoticon("Smile", "/images/emoticons/drink_80_anim_gif.gif"));
		emoticons.add(new Emoticon("Smile", "/images/emoticons/dull.gif"));
		emoticons.add(new Emoticon("Smile", "/images/emoticons/emo_80_anim_gif.gif"));
		emoticons.add(new Emoticon("Smile", "/images/emoticons/envy.gif"));
		emoticons.add(new Emoticon("Smile", "/images/emoticons/evilgrin.gif"));
		emoticons.add(new Emoticon("Smile", "/images/emoticons/facepalm.gif"));
		emoticons.add(new Emoticon("Smile", "/images/emoticons/fingerscrossed.gif"));
		emoticons.add(new Emoticon("Smile", "/images/emoticons/fistbump_80_anim_gif.gif"));
		emoticons.add(new Emoticon("Smile", "/images/emoticons/giggle.gif"));
		emoticons.add(new Emoticon("Smile", "/images/emoticons/hands_in_air_80_anim_gif.gif"));
		emoticons.add(new Emoticon("Smile", "/images/emoticons/handshake.gif"));
		emoticons.add(new Emoticon("Smile", "/images/emoticons/headbang_80_anim_gif.gif"));
		emoticons.add(new Emoticon("Smile", "/images/emoticons/heart.gif"));
		emoticons.add(new Emoticon("Smile", "/images/emoticons/hi.gif"));
		emoticons.add(new Emoticon("Smile", "/images/emoticons/hug.gif"));
		emoticons.add(new Emoticon("Smile", "/images/emoticons/idea_80_anim_gif.gif"));
		emoticons.add(new Emoticon("Smile", "/images/emoticons/inlove.gif"));
		emoticons.add(new Emoticon("Smile", "/images/emoticons/itwasntme.gif"));
		emoticons.add(new Emoticon("Smile", "/images/emoticons/kiss.gif"));
		emoticons.add(new Emoticon("Smile", "/images/emoticons/lalala.gif"));
		emoticons.add(new Emoticon("Smile", "/images/emoticons/lipssealed.gif"));
		emoticons.add(new Emoticon("Smile", "/images/emoticons/mail_80_anim_gif.gif"));
		emoticons.add(new Emoticon("Smile", "/images/emoticons/makeup.gif"));
		emoticons.add(new Emoticon("Smile", "/images/emoticons/mmm.gif"));
		emoticons.add(new Emoticon("Smile", "/images/emoticons/monkey_80_anim_gif.gif"));
		emoticons.add(new Emoticon("Smile", "/images/emoticons/movember.gif"));
		emoticons.add(new Emoticon("Smile", "/images/emoticons/muscle_80_anim_gif.gif"));
		emoticons.add(new Emoticon("Smile", "/images/emoticons/music.gif"));
		emoticons.add(new Emoticon("Smile", "/images/emoticons/nerd.gif"));
		emoticons.add(new Emoticon("Smile", "/images/emoticons/ninja_80_anim_gif.gif"));
		emoticons.add(new Emoticon("Smile", "/images/emoticons/no.gif"));
		emoticons.add(new Emoticon("Smile", "/images/emoticons/pizza_80_anim_gif.gif"));
		emoticons.add(new Emoticon("Smile", "/images/emoticons/poke_80_anim_gif.gif"));
		emoticons.add(new Emoticon("Smile", "/images/emoticons/praying_80_anim_gif.gif"));
		emoticons.add(new Emoticon("Smile", "/images/emoticons/puking.gif"));
		emoticons.add(new Emoticon("Smile", "/images/emoticons/punch_80_anim_gif.gif"));
		emoticons.add(new Emoticon("Smile", "/images/emoticons/rof.gif"));
		emoticons.add(new Emoticon("Smile", "/images/emoticons/sad.gif"));
		emoticons.add(new Emoticon("Smile", "/images/emoticons/sheep_80_anim_gif.gif"));
		emoticons.add(new Emoticon("Smile", "/images/emoticons/sleepy.gif"));
		emoticons.add(new Emoticon("Smile", "/images/emoticons/smile.gif"));
		emoticons.add(new Emoticon("Smile", "/images/emoticons/speechless.gif"));
		emoticons.add(new Emoticon("Smile", "/images/emoticons/surprised.gif"));
		emoticons.add(new Emoticon("Smile", "/images/emoticons/sweating.gif"));
		emoticons.add(new Emoticon("Smile", "/images/emoticons/talktothehand_80_anim_gif.gif"));
		emoticons.add(new Emoticon("Smile", "/images/emoticons/thinking.gif"));
		emoticons.add(new Emoticon("Smile", "/images/emoticons/tmi.gif"));
		emoticons.add(new Emoticon("Smile", "/images/emoticons/tongueout.gif"));
		emoticons.add(new Emoticon("Smile", "/images/emoticons/victory_80_anim_gif.gif"));
		emoticons.add(new Emoticon("Smile", "/images/emoticons/wait.gif"));
		emoticons.add(new Emoticon("Smile", "/images/emoticons/waiting_80_anim_gif.gif"));
		emoticons.add(new Emoticon("Smile", "/images/emoticons/wink.gif"));
		emoticons.add(new Emoticon("Smile", "/images/emoticons/wondering.gif"));
		emoticons.add(new Emoticon("Smile", "/images/emoticons/worried.gif"));
		emoticons.add(new Emoticon("Smile", "/images/emoticons/yawning.gif"));
		emoticons.add(new Emoticon("Smile", "/images/emoticons/yes.gif"));
		for (int i = 0; i < emoticons.size(); i++) {
			emoticons.get(i).setId(i);
			emoticonsMap.put(i, emoticons.get(i));
		}
	}

	public List<Emoticon> getEmoticons() {
		return emoticons;
	}

	public Emoticon getEmoticon(int id) {
		return emoticonsMap.get(id);
	}

	public Person getMyFamilyTree(Person me, int level) throws Exception {
		Person root = null;
		Person mom = null;
		Person dad = null;
		List<Person> wifeOrHusbands = new ArrayList<>();
		List<Person> protherOrSyster = new ArrayList<>();
		List<Person> kids = new ArrayList<>();
		String sql = "SELECT p.*,r.relation_type FROM profile p, relation r where r.profile_b in (select profile_b from relation where profile_a = " + me.getId() + ") and profile_a = " + me.getId() + " and p.id = r.profile_b";
		Connection con = JDBCConnection.getInstance().getConnection();
		PreparedStatement stm = null;
		ResultSet rs = null;
		try {
			stm = con.prepareStatement(sql);
			rs = stm.executeQuery();
			while (rs.next()) {
				if (rs.getInt("relation_type") == RELATION_TYPE.DAD.ordinal()) {
					dad = toPerson(rs, con, false);
				} else if (rs.getInt("relation_type") == RELATION_TYPE.MOM.ordinal()) {
					mom = toPerson(rs, con, false);
				} else if (rs.getInt("relation_type") == RELATION_TYPE.WIFE_OR_HUSBAND.ordinal()) {
					wifeOrHusbands.add(toPerson(rs, con, false));
				} else if (rs.getInt("relation_type") == RELATION_TYPE.PROTHER_OR_SYSTER.ordinal()) {
					protherOrSyster.add(toPerson(rs, con, false));
				} else if (rs.getInt("relation_type") == RELATION_TYPE.KID.ordinal()) {
					kids.add(toPerson(rs, con, false));
				}
			}
			me.setChildren(kids);
			if (dad != null) {
				root = dad;
				root.setChildren(protherOrSyster);
				root.getChildren().add(me);
			} else if (mom != null) {
				root = mom;
				root.setChildren(protherOrSyster);
				root.getChildren().add(me);
			} else {
				root = me;
				me.setChildren(kids);
			}

		} finally {
			if (stm != null)
				stm.close();
			if (rs != null)
				rs.close();
			con.close();
		}
		return root;
	}
	
	
	
	
	
/*	private void getWifeOrHusband(Connection con, List<Person> enagedRelations, String ids) throws SQLException {
		ResultSet rs = null;
		PreparedStatement stm = con.prepareStatement("select * from profile where id in (" + ids + ")");
		try {
			rs = stm.executeQuery();
			while (rs.next()) {
				enagedRelations.add(toPerson(rs, con, false));
			}
		} finally {
			if (rs != null)
				rs.close();
			if (stm != null)
				stm.close();
		}
	}

	private List<Person> getChildren(Connection con, long momOrDadId, boolean isDad) throws SQLException {
		List<Person> children = new ArrayList<>();
		ResultSet rs = null;
		String sql = "select * from profile where dad = " + momOrDadId;
		if (!isDad)
			sql = "select * from profile where mom = " + momOrDadId;
		PreparedStatement stm = con.prepareStatement(sql);
		try {
			rs = stm.executeQuery();
			while (rs.next()) {
				children.add(toPerson(rs, con, false));
			}
		} finally {
			if (rs != null)
				rs.close();
			if (stm != null)
				stm.close();
		}
		return children;
	}*/
}
