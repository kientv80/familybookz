package com.vnsoftware.midle;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

import com.vnsoftware.db.JDBCConnection;
import com.vnsoftware.giapha.entirty.BroadcastingFeed;
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
			feedss.add(new BroadcastingFeed(owner.getId(), feedId));
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
					broadcastFeed(f.getOwner(), f.getFeedId());
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
	private void broadcastFeed(long owner, long feedId) throws Exception {
		Connection con = JDBCConnection.getInstance().getConnection();
		PreparedStatement stm = null;
		PreparedStatement stm2 = null;
		ResultSet rs = null;
		String sql = "select profile_b from relation where profile_a = " + owner;
		try {
			 stm = con.prepareStatement(sql);
			 rs = stm.executeQuery();
			 List<Integer> broadcastIds = new ArrayList<>();
			 while (rs.next()) {
				broadcastIds.add(rs.getInt("profile_b"));
			 }
			 if(broadcastIds.size() > 0){
				con.setAutoCommit(false);
				stm2 = con.prepareStatement("insert into profile_feeds_timeline (profile_id, feed_id) values (?,?)");
				for (int id : broadcastIds) {
					stm2.setInt(1, id);
					stm2.setLong(2, feedId);
					stm2.addBatch();
				}
				stm2.executeBatch();
				con.commit();	
			 }
		} finally {
			try {
				if(stm != null)
					stm.close();
				if(stm2 != null)
					stm2.close();
				
				if(rs != null)
					rs.close();
				if (con != null)
					con.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}
}
