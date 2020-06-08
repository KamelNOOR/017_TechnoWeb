package services;

import java.sql.Connection;
import java.sql.SQLException;

import org.bson.Document;
import org.json.JSONException;
import org.json.JSONObject;

import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;

import bd.Database;
import tools.MessageTools;
import tools.UserTools;
import tools.Usual;

public class Message {
	
	public static JSONObject addMessage( String user, String msg) throws JSONException {
		
		//Check format
		if (Usual.wrongFormat(user, msg)) {
			return Usual.JSONAnsweRefused("Erreur Format", -1);
		}
		
		try {
			
			Connection connection = Database.getMySQLConnection();
			MongoDatabase mongodb = Database.getMongoDBConnection();
			MongoCollection<Document> message_collection = mongodb.getCollection("message");
			
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
				return Usual.JSONAnsweRefused("Erreur MongoDB", 1000);
			}
			
			//Check message exists
			if (MessageTools.messageExists(user, msg, message_collection)) {
				connection.close();
				return Usual.JSONAnsweRefused("Erreur message already exists", 1000);
			}
			
			//Insert message
			if (MessageTools.insertMessage(user, msg, message_collection)) {
				if(UserTools.keyTimeUpdate(user, connection)){
					connection.close();
					return Usual.JSONAnsweAccepted();
				}
				connection.close();
				return Usual.JSONAnsweRefused("Update can not done", 1000);
			}
			
			connection.close();
			
		} catch(SQLException e) {
			
		}
		return Usual.JSONAnsweRefused("Erreur MongoDB", 1000);
	}
	
	public static JSONObject removeMessage( String user, String msg) throws JSONException {
		
		//Check format
		if (Usual.wrongFormat(user, msg)) {
			return Usual.JSONAnsweRefused("Erreur Format", -1);
		}
		
		try {
			
			Connection connection = Database.getMySQLConnection();
			MongoDatabase mongodb = Database.getMongoDBConnection();
			MongoCollection<Document> message_collection = mongodb.getCollection("message");
			
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
				return Usual.JSONAnsweRefused("Erreur MongoDB", 1000);
			}
			
			//Check message exists
			if (!MessageTools.messageExists(user, msg, message_collection)) {
				connection.close();
				return Usual.JSONAnsweRefused("Erreur message does not exist", 1000);
			}
			
			//Remove message
			if (MessageTools.removeMessage(user, msg, message_collection)) {
				if(UserTools.keyTimeUpdate(user, connection)){
					connection.close();
					return Usual.JSONAnsweAccepted();
				}
				connection.close();
				return Usual.JSONAnsweRefused("Update can not done", 1000);
			}
			
			connection.close();
			
		} catch(SQLException e) {
			
		}
		return Usual.JSONAnsweRefused("Erreur MongoDB", 1000);
	}
	
	public static JSONObject getMessage( String user) throws JSONException {
		
		//Check format
		if (Usual.wrongFormat(user, " ")) {
			return Usual.JSONAnsweRefused("Erreur Format", -1);
		}
		
		try {
				
			Connection connection = Database.getMySQLConnection();
			MongoDatabase mongodb = Database.getMongoDBConnection();
			MongoCollection<Document> message_collection = mongodb.getCollection("message");
					
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
				return Usual.JSONAnsweRefused("Erreur MongoDB", 1000);
			}
			
			//Get All Messages
			JSONObject message = new JSONObject();
			message = MessageTools.getMessage(user, message_collection);
			if(UserTools.keyTimeUpdate(user, connection)){
				if (message !=null) {
					connection.close();
					return message;
				}
				connection.close();
				return Usual.JSONAnsweRefused("No Messages Founded", 1000);
			}
			connection.close();
			return Usual.JSONAnsweRefused("Update can not done", 1000);
			
			
					
		} catch (SQLException e) {
			return Usual.JSONAnsweRefused("Erreur MongoDB", 1000);
		}
		
	}
	
	//Search Message
	public static JSONObject getSpecificMessage( String search) throws JSONException {
		
		MongoDatabase mongodb = Database.getMongoDBConnection();
		MongoCollection<Document> message_collection = mongodb.getCollection("message");

		//Get message with specific character ( string search)
		JSONObject message = new JSONObject();
		message = MessageTools.getSpecificMessage(search,message_collection);
	
		if (message !=null) {
			return message;
		}

		return Usual.JSONAnsweRefused("No Messages Founded", 1000);
	}
}
