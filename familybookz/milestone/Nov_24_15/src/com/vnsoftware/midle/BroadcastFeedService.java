package com.vnsoftware.midle;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

import com.vnsoftware.db.JDBCConnection;
import com.vnsoftware.giapha.entirty.BroadcastingFeed;
import com.vnsoftware.giapha.entirty.DumyDB;
import com.vnsoftware.giapha.entirty.Person;

public class BroadcastFeedService {
	BlockingQueue<BroadcastingFeed> feedss = new ArrayBlockingQueue<>(1000000);
	private static BroadcastFeedService instance;

	public static BroadcastFeedService getInstance() {
		if (instance == null)
			instance = new BroadcastFeedService();
		return instance;
	}

	public void broadCastFeeds(Person owner, long feedId) {
		if (owner != null && feedId > 0)
			feedss.add(new BroadcastingFeed(owner, feedId));
	}

	public void broadcastFeeds() {
		int size = feedss.size();
		System.out.println(">>>>>>>>>>>>>>>>>>>>>>> START broadcasting " + size + " feeds");
		Connection con = null;
		if (!feedss.isEmpty())
			con = JDBCConnection.getInstance().getConnection();
		try {
			while (!feedss.isEmpty()) {
				BroadcastingFeed f = feedss.poll();
				try {
					broadcastFeed(f.getOwner(), con, f.getFeedId());
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		} finally {
			try {
				if (con != null)
					con.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		System.out.println(">>>>>>>>>>>>>>>>>>>>>>> END broadcasting " + size + " feeds");
	}

	private void broadcastFeed(Person owner, Connection con, long feedId) throws Exception {
		Statement stm = null;
		ResultSet rs = null;
		try {
			stm = con.createStatement();
			String sql;
			// ========= broadcast to feed ============================
			StringBuilder boMeToiIds = new StringBuilder();
			StringBuilder grandParentAntUncleIds_ = new StringBuilder();
			StringBuilder conCoDiChuBacIds = new StringBuilder();
			Set<Long> grandParentAntUncleIds = new HashSet<>();
			Person mom = null;
			Person dad = null;
			if (owner.getMom() > 0) {
				boMeToiIds.append(owner.getMom()).append(",");
				mom = DumyDB.getInstance().getPerson(owner.getMom(), false);
				if (mom.getMom() > 0) {
					grandParentAntUncleIds.add(mom.getMom());// add bà
					for (Person p : DumyDB.getInstance().getPerson(mom.getMom(), true).getChildren()) {
						grandParentAntUncleIds.add(p.getId());// add cô, chú,
																// bác
					}
				}
				if (mom.getDad() > 0) {
					grandParentAntUncleIds.add(mom.getDad());// add ông
					for (Person p : DumyDB.getInstance().getPerson(mom.getDad(), true).getChildren()) {
						grandParentAntUncleIds.add(p.getId());// add cô, chú,
																// bác
					}
				}

			}

			if (owner.getDad() > 0) {
				boMeToiIds.append(owner.getDad()).append(",");
				dad = DumyDB.getInstance().getPerson(owner.getDad(), false);
				if (dad.getMom() > 0) {
					grandParentAntUncleIds.add(dad.getMom());// add bà
					for (Person p : DumyDB.getInstance().getPerson(dad.getMom(), true).getChildren()) {
						grandParentAntUncleIds.add(p.getId());// add cô, chú,
																// bác
					}
				}
				if (dad.getDad() > 0) {
					grandParentAntUncleIds.add(dad.getDad());// add ông
					for (Person p : DumyDB.getInstance().getPerson(dad.getDad(), true).getChildren()) {
						grandParentAntUncleIds.add(p.getId());// add cô, chú,
																// bác
					}
				}

			}
			// ========== load con của các cô, chú, bác =================
			if (grandParentAntUncleIds.size() > 0) {
				for (long id : grandParentAntUncleIds) {
					grandParentAntUncleIds_.append(id).append(",");
				}
				sql = "select id from profile where mom in (" + grandParentAntUncleIds_.subSequence(0, grandParentAntUncleIds_.length() - 1) + ") or dad in (" + grandParentAntUncleIds_.subSequence(0, grandParentAntUncleIds_.length() - 1) + ")";
				rs = stm.executeQuery(sql);
				while (rs.next()) {
					conCoDiChuBacIds.append(rs.getLong("id")).append(",");
				}
				rs.close();
			}
			// ========== load co, di , chu, bac và cac con tôi ============
			boMeToiIds.append(owner.getId());
			sql = "select id from profile where mom in (" + boMeToiIds.toString() + ") or dad in (" + boMeToiIds.toString() + ")";
			rs = stm.executeQuery(sql);
			StringBuilder coDiChuBacOngBaVaCacConToiIDs = new StringBuilder();
			while (rs.next()) {
				coDiChuBacOngBaVaCacConToiIDs.append(rs.getLong("id")).append(",");
			}
			rs.close();
			if (coDiChuBacOngBaVaCacConToiIDs.length() > 0) {
				// ========= broadcast to các cháu ============================
				sql = "select id from profile where mom in(" + coDiChuBacOngBaVaCacConToiIDs.toString().substring(0, coDiChuBacOngBaVaCacConToiIDs.length() - 1) + ") or dad in (" + coDiChuBacOngBaVaCacConToiIDs.toString().substring(0, coDiChuBacOngBaVaCacConToiIDs.length() - 1) + ")";
				rs = stm.executeQuery(sql);
				while (rs.next()) {
					coDiChuBacOngBaVaCacConToiIDs.append(rs.getLong("id")).append(",");
				}
				rs.close();
				if (coDiChuBacOngBaVaCacConToiIDs.toString().endsWith(",")) {
					boMeToiIds.append(",").append(coDiChuBacOngBaVaCacConToiIDs.substring(0, coDiChuBacOngBaVaCacConToiIDs.length() - 1));
				} else {
					boMeToiIds.append(",").append(coDiChuBacOngBaVaCacConToiIDs);
				}
			}
			if (grandParentAntUncleIds_.length() > 0) {
				if (grandParentAntUncleIds_.toString().endsWith(",")) {
					boMeToiIds.append(",").append(grandParentAntUncleIds_.substring(0, grandParentAntUncleIds_.length() - 1));
				} else {
					boMeToiIds.append(",").append(grandParentAntUncleIds_);
				}
			}

			if (conCoDiChuBacIds.length() > 0) {
				if (conCoDiChuBacIds.toString().endsWith(",")) {
					boMeToiIds.append(",").append(conCoDiChuBacIds.substring(0, conCoDiChuBacIds.length() - 1));
				} else {
					boMeToiIds.append(",").append(conCoDiChuBacIds);
				}
			}
			// ========= broad cast tới vợ =========================
			if (owner.getWifeorhusbandIds() != null && owner.getWifeorhusbandIds().length() > 0)
				boMeToiIds.append(",").append(owner.getWifeorhusbandIds());
			// ============== update broadcast list ================
			sql = "update profile_feeds_timeline set feed_ids = CONCAT(feed_ids,'," + feedId + "') where profile_id =";
			Set<String> uniqueIds = new HashSet<>(Arrays.asList(boMeToiIds.toString().split(",")));
			for (String id : uniqueIds) {
				if (id != null && !id.isEmpty() && stm.executeUpdate(sql + id) == 0) {
					stm.execute("insert into profile_feeds_timeline (profile_id, feed_ids) values (" + id + ",'" + feedId + "')");
				}
			}

		} finally {
			if (stm != null)
				stm.close();
			if (rs != null)
				rs.close();
		}
	}
}
