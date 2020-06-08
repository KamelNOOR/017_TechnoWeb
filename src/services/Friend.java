package services;

import java.sql.Connection;
import java.sql.SQLException;

import org.json.JSONException;
import org.json.JSONObject;

import bd.Database;
import tools.FriendTools;
import tools.UserTools;
import tools.Usual;

public class Friend {

	public static JSONObject addFriend( String user, String friend) throws JSONException {
		
		//Check format
		if (Usual.wrongFormat(user, friend)) {
			return Usual.JSONAnsweRefused("Erreur Format", -1);
		}
		
		try {
			
			Connection connection = Database.getMySQLConnection();
			
			//Check user exists
			if (!UserTools.userExists(user, connection)) {
				connection.close();
				return Usual.JSONAnsweRefused("Erreur user doest not exist", 1000);
			}
			
			//Check key valid 
			if (!UserTools.keyValid(user, connection)) {
				if(UserTools.connectionUpdate(user, "false", connection)) {
					connection.close();
					return Usual.JSONAnsweRefused("Erreur user not connected/disconnected", 1000);
				}
				connection.close();
				return Usual.JSONAnsweRefused("Erreur SQL", 1000);
			}
			
			//Check friend exists
			if (!UserTools.userExists(friend, connection)) {
				connection.close();
				return Usual.JSONAnsweRefused("Erreur friend does not exist", 1000);
			}
			
			//Check if already friend
			if (FriendTools.alreadyFriend(user, friend, connection)) {
				connection.close();
				return Usual.JSONAnsweRefused("Erreur already friend", 1000);
			}
			
			//Add Friend
			if (FriendTools.addFriend(user, friend, connection)) {
				if(UserTools.keyTimeUpdate(user, connection)){
					connection.close();
					return Usual.JSONAnsweAccepted();
				}
				connection.close();
				return Usual.JSONAnsweRefused("Update can not done", 1000);
			}
			connection.close();
		} catch (SQLException e) {
		}
		
		return Usual.JSONAnsweRefused("Erreur SQL", 1000);
	}
	
	public static JSONObject removeFriend( String user, String friend) throws JSONException {
			
		//Check format
		if (Usual.wrongFormat(user, friend)) {
			return Usual.JSONAnsweRefused("Erreur Format", -1);
		}
		
		try {
			
			Connection connection = Database.getMySQLConnection();
			
			//Check user exists
			if (!UserTools.userExists(user, connection)) {
				connection.close();
				return Usual.JSONAnsweRefused("Erreur User does not exist", 1000);
			}
			
			//Check key valid 
			if (!UserTools.keyValid(user, connection)) {
				if(UserTools.connectionUpdate(user, "false", connection)) {
					connection.close();
					return Usual.JSONAnsweRefused("Erreur user not connected/disconnected", 1000);
				}
				connection.close();
				return Usual.JSONAnsweRefused("Erreur SQL", 1000);
			}
			
			//Check friend exists
			if (!UserTools.userExists(friend, connection)) {
				connection.close();
				return Usual.JSONAnsweRefused("Erreur Friend does not exist", 1000);
			}
			
			//Check if friend
			if (!FriendTools.alreadyFriend(user, friend, connection)) {
				connection.close();
				return Usual.JSONAnsweRefused("Erreur not friend", 1000);
			}
			
			//Delete Friend
			if (FriendTools.removeFriend(user, friend, connection)) {
				if(UserTools.keyTimeUpdate(user, connection)){
					connection.close();
					return Usual.JSONAnsweAccepted();
				}
				connection.close();
				return Usual.JSONAnsweRefused("Update can not done", 1000);
			}
			connection.close();
			
		} catch (SQLException e) {
		}
		
		return Usual.JSONAnsweRefused("Erreur SQL", 1000);
	}
	
	public static JSONObject getFriend( String user) throws JSONException {
		
		//Check format
		if (Usual.wrongFormat(user, " ")) {
			return Usual.JSONAnsweRefused("Erreur Format", -1);
		}
		
		try {
				
			Connection connection = Database.getMySQLConnection();
					
			//Check user exists
			if (!UserTools.userExists(user, connection)) {
				connection.close();
				return Usual.JSONAnsweRefused("Erreur User does not exist", 1000);
			}
			
			//Check key valid 
			if (!UserTools.keyValid(user, connection)) {
				if(UserTools.connectionUpdate(user, "false", connection)) {
					connection.close();
					return Usual.JSONAnsweRefused("Erreur user not connected/disconnected", 1000);
				}
				connection.close();
				return Usual.JSONAnsweRefused("Erreur SQL", 1000);
			}
					
			//Get All Friend
			JSONObject friend = new JSONObject();
			friend = FriendTools.getFriend(user, connection);
			if(UserTools.keyTimeUpdate(user, connection)){
				if (friend !=null) {
					connection.close();
					return friend;
				}
				connection.close();
				return Usual.JSONAnsweRefused("No Friend Founded", 1000);
			}
			connection.close();
			return Usual.JSONAnsweRefused("Update can not done", 1000);
		
					
		} catch (SQLException e) {
			return Usual.JSONAnsweRefused("Erreur SQL", 1000);
		}
				
	}
	
}
