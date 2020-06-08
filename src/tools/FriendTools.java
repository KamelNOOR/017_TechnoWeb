package tools;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import org.json.JSONException;
import org.json.JSONObject;

public class FriendTools {

	public static boolean alreadyFriend(String user, String friend, Connection connection) throws SQLException{
		
		boolean alreadyFriend = false;
		Statement statement = connection.createStatement();
		String query = "SELECT * FROM friend WHERE user='"+user+"' AND friend='"+friend+"'";
		ResultSet rs =  statement.executeQuery(query);
		
		// If it contains something then already friend
		while(rs.next()) {
			alreadyFriend = true;
		}
		
		rs.close();
		statement.close();
		return alreadyFriend;
	}
	
	public static boolean addFriend(String user,String friend, Connection connection) throws SQLException{
		
		boolean friendAdded = false;
		Statement statement = connection.createStatement();
		String query = "INSERT INTO `friend` (`user`,`friend`) VALUES ('"+user+"','"+friend+"');";
		int rs =  statement.executeUpdate(query);
		
		// If changes done
		if(rs != 0) {
			friendAdded= true;
		}
		
		statement.close();
		
		return friendAdded;
	}
	
	public static boolean removeFriend(String user,String friend, Connection connection) throws SQLException{
		
		boolean friendDeleted = false;
		Statement statement = connection.createStatement();
		String query = "DELETE FROM `friend` WHERE `user` = '"+user+"' AND `friend`='"+friend+"'";
		int rs =  statement.executeUpdate(query);
		
		// If changes done
		if(rs != 0) {
			friendDeleted= true;
		}
		
		statement.close();
		
		return friendDeleted;
	}

	public static JSONObject getFriend(String user, Connection connection) throws SQLException, JSONException {
		
		JSONObject json = new JSONObject();
		json.put("User", user);
		boolean haveFriend = false;
		ArrayList<String> friends = new ArrayList<String>();
		
		Statement statement = connection.createStatement();
		String query = "SELECT * FROM friend WHERE user='"+user+"'";
		ResultSet rs =  statement.executeQuery(query);
		
		// If it contains something then user has friend
		while(rs.next()) {
			haveFriend = true;
			friends.add(rs.getString("friend"));
		}
		
		json.put("FriendList", friends);
		
		rs.close();
		statement.close();
		if(haveFriend) {
			return json;
		}
		return null;
		
	}
}
