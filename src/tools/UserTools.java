package tools;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;

import org.json.JSONException;
import org.json.JSONObject;

public class UserTools {

	public static boolean userExists(String nickname, Connection connection) throws SQLException{
		
		boolean userExists = false;
		Statement statement = connection.createStatement();
		String query = "SELECT nickname FROM user WHERE nickname='"+nickname+"'";
		ResultSet rs =  statement.executeQuery(query);
		
		// If it contains something then user exists already
		while(rs.next()) {
			userExists = true;
		}
		
		rs.close();
		statement.close();
		return userExists;
		
	}
	
	public static boolean addUser(String nickname, String pwd, Connection connection) throws SQLException{
		
		boolean userAdded = false;
		Statement statement = connection.createStatement();
		String query = "INSERT INTO `user` (`nickname`,`password`,`connected`) VALUES ('"+nickname+"','"+pwd+"','false');";
		int rs =  statement.executeUpdate(query);
		
		//If added successfully
		if (rs != 0) {
			userAdded = true;
		}
		
		statement.close();
		
		return userAdded;
	}
	
	public static boolean wrongPwd(String nickname, String pwd, Connection connection) throws SQLException{
		
		boolean wrongPwd = true;
		Statement statement = connection.createStatement();
		String query = "SELECT * FROM user WHERE nickname='"+nickname+"' AND password='"+pwd+"'";
		ResultSet rs =  statement.executeQuery(query);
		
		// If it contains something then user and pwd correct
		while(rs.next()) {
			wrongPwd = false;
		}
		
		rs.close();
		statement.close();
		
		return wrongPwd;
	}
	
	public static boolean userConnected(String nickname, Connection connection) throws SQLException{
		
		boolean userConnected = false;
		Statement statement = connection.createStatement();
		String query = "SELECT * FROM `user` WHERE nickname='"+nickname+"' AND connected='true'";
		ResultSet rs =  statement.executeQuery(query);
		
		// If it contains something then user connected
		while(rs.next()) {
			userConnected = true;
		}
		
		rs.close();
		statement.close();
		
		return userConnected;
	}
	
	public static boolean connectionUpdate(String nickname,String bool, Connection connection) throws SQLException{
		
		boolean connectionUpdate = false;
		Statement statement = connection.createStatement();
		String query = "UPDATE `user` SET connected = '"+bool+"' WHERE nickname='"+nickname+"'";
		int rs =  statement.executeUpdate(query);
		
		GregorianCalendar calendar = new GregorianCalendar();
		int heure_actuel = calendar.get(Calendar.HOUR);
		int minute_actuel = calendar.get(Calendar.MINUTE);
		
		String query3 = "UPDATE `user` SET heure_key = '"+heure_actuel+"' WHERE nickname='"+nickname+"'";
		int rs3 =  statement.executeUpdate(query3	);
		
		String query2 = "UPDATE `user` SET minute_key = '"+minute_actuel+"' WHERE nickname='"+nickname+"'";
		int rs2 =  statement.executeUpdate(query2);
		
		int rs4 = 0;
		if(bool.equals("false")) {
			
			String query4 = "UPDATE `user` SET key_conn = '"+null+"' WHERE nickname='"+nickname+"'";
			rs4 =  statement.executeUpdate(query4);
			
		} else {
			String key = createKey(connection);
			
			String query4 = "UPDATE `user` SET key_conn = '"+key+"' WHERE nickname='"+nickname+"'";
			rs4 =  statement.executeUpdate(query4);
			
		}
		
		// If changes done
		if(rs != 0 && rs2 != 0 && rs3 != 0) {
			connectionUpdate = true;
		
		}
		
		statement.close();
		
		return connectionUpdate;
	}
	
	public static String createKey(Connection connection) throws SQLException {
		
		
		// 10 character key
		String chars = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ1234567890";
	    String key = "";
	    for(int x=0;x<10;x++)
	    {
	       int i = (int)Math.floor(Math.random() * 62); 
	       key += chars.charAt(i);
	    }
	    
	    //Check if key exists
	    
	    if(keyExists(key, connection)) {
	    	key = createKey(connection);
	    }
	    
		return key;
	}
	
	public static boolean keyExists(String key, Connection connection) throws SQLException {
		
		boolean keyExists = false;
		Statement statement = connection.createStatement();
		String query = "SELECT * FROM `user` WHERE key_conn='"+key+"'";
		ResultSet rs =  statement.executeQuery(query);
		
		// If it contains something then key exists
		while(rs.next()) {
			keyExists = true;
		}
		
		rs.close();
		statement.close();
		
		return keyExists;
	}
	
	public static boolean keyValid(String nickname, Connection connection) throws SQLException {
		
		boolean keyValid = false;
		Statement statement = connection.createStatement();
		String query = "SELECT * FROM `user` WHERE nickname='"+nickname+"'";
		ResultSet rs =  statement.executeQuery(query);
		
		while(rs.next()) {
			if(!rs.getString("key_conn").equals("null")) {
		
				int heure_key = rs.getInt("heure_key");
				int minute_key = rs.getInt("minute_key");
				GregorianCalendar calendar = new GregorianCalendar();
				int heure_actuel = calendar.get(Calendar.HOUR);
				int minute_actuel = calendar.get(Calendar.MINUTE);
				
				if((heure_key == heure_actuel) && (minute_actuel - minute_key < 30)) {
					keyValid = true;
				}
			}
			
		}
		
		rs.close();
		statement.close();
		
		return keyValid;
	}

	public static boolean keyTimeUpdate(String nickname,Connection connection) throws SQLException {
		
		GregorianCalendar calendar = new GregorianCalendar();
		int heure_actuel = calendar.get(Calendar.HOUR);
		int minute_actuel = calendar.get(Calendar.MINUTE);
		
		boolean heureUpdate = false;
		Statement statement = connection.createStatement();
		String query = "UPDATE `user` SET heure_key = '"+heure_actuel+"' WHERE nickname='"+nickname+"'";
		int rs =  statement.executeUpdate(query);
		
		// If changes done
		if(rs != 0) {
			heureUpdate = true;
		}
				
				
		boolean minuteUpdate = false;
		String query2 = "UPDATE `user` SET minute_key = '"+minute_actuel+"' WHERE nickname='"+nickname+"'";
		int rs2 =  statement.executeUpdate(query2);
		
		// If changes done
		if(rs2 != 0) {
			minuteUpdate = true;
		}
				
		statement.close();
		
		if(heureUpdate && minuteUpdate) {
			return true;
		}
		
		return false;
	}
	
	public static JSONObject getSpecificUser(String search, Connection connection) throws SQLException, JSONException {
		
		JSONObject json = new JSONObject();
		
		boolean searchUser = false;
		ArrayList<String> users = new ArrayList<String>();
		
		Statement statement = connection.createStatement();
		String query = "SELECT * FROM user WHERE nickname LIKE '%"+search+"%'";
		ResultSet rs =  statement.executeQuery(query);
		
		// If it contains something then user has friend
		while(rs.next()) {
			searchUser = true;
			users.add(rs.getString("nickname"));
		}
		
		json.put("UserSearchList", users);
		
		rs.close();
		statement.close();
		if(searchUser) {
			return json;
		}
		return null;
	}
}
