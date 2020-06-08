package services;

import java.sql.Connection;
import java.sql.SQLException;
import org.json.JSONException;
import org.json.JSONObject;

import bd.Database;
import tools.FriendTools;
import tools.UserTools;
import tools.Usual;

public class User {
	
	public static JSONObject createUser(String nickname, String pwd ) throws JSONException{
		
		//Check format
		if (Usual.wrongFormat(nickname, pwd)) {
			return Usual.JSONAnsweRefused("Erreur Format", -1);
		}
		
		
		try {
			Connection connection = Database.getMySQLConnection();
			
			//Check user exists
			if (UserTools.userExists(nickname, connection)) {
				connection.close();	
				return Usual.JSONAnsweRefused("Erreur User already exists", 1000);
			}
			
			//Add User in db
			
			if (UserTools.addUser(nickname, pwd, connection)) {
				connection.close();
				return Usual.JSONAnsweAccepted();
			}
			connection.close();
			
		}catch ( SQLException e ){
	
		}
		
		return Usual.JSONAnsweRefused("Erreur SQL", 1000);
	}
	
	public static JSONObject login(String nickname, String pwd ) throws JSONException {
			
		//Check format
		if (Usual.wrongFormat(nickname, pwd)) {
			return Usual.JSONAnsweRefused("Erreur Format", -1);
		}
		
		try {
			Connection connection = Database.getMySQLConnection();
			
			//Check user exists
			if (!(UserTools.userExists(nickname, connection))) {
				connection.close();
				return Usual.JSONAnsweRefused("Erreur User doesn't exist", 1000);
			}
			
			//Check password
			if(UserTools.wrongPwd(nickname, pwd, connection)){
				connection.close();
				return Usual.JSONAnsweRefused("Erreur wrong nickname or/and password", -1);
			}
			
			//Check user connected
			if(UserTools.userConnected(nickname, connection)){
				connection.close();
				return Usual.JSONAnsweRefused("User already connected", -1);
			}
			
			//Connection
			if(UserTools.connectionUpdate(nickname, "true", connection)) {
				connection.close();
				return Usual.JSONAnsweAccepted();
			}
			
			connection.close();
			
		}catch( SQLException e ) {
			
		}
		
		return Usual.JSONAnsweRefused("Erreur SQL", 1000);
	}
	
	public static JSONObject logout(String nickname, String pwd ) throws JSONException{
				
		//Check format
		if (Usual.wrongFormat(nickname, pwd)) {
			return Usual.JSONAnsweRefused("Erreur Format", -1);
		}
		
		try {
			Connection connection = Database.getMySQLConnection();
			
			//Check user exists
			if (!(UserTools.userExists(nickname, connection))) {
				connection.close();
				return Usual.JSONAnsweRefused("Erreur user doesn't exist", 1000);
			}
			
			//Check user connected
			if(!(UserTools.userConnected(nickname, connection))){
				connection.close();
				return Usual.JSONAnsweRefused("Erreur user not connected", -1);
			}
			
			//Disconnection
			if(UserTools.connectionUpdate(nickname, "false", connection)) {
				connection.close();
				return Usual.JSONAnsweAccepted();
			}
			
			connection.close();
			
		}catch( SQLException e ) {
			
		}
				
		return Usual.JSONAnsweRefused("Erreur SQL", 1000);
	}
	
	public static JSONObject getSpecificUser( String search) throws JSONException, SQLException {
		
		Connection connection = Database.getMySQLConnection();	
			
		//Get All Friend
		JSONObject user = new JSONObject();
		user = UserTools.getSpecificUser(search, connection);
		
		if (user !=null) {
			connection.close();
			return user;
		}
		connection.close();
		return Usual.JSONAnsweRefused("No User Founded", 1000);
				
	}
}
