package services;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Map;

import org.bson.Document;
import org.json.JSONException;
import org.json.JSONObject;

import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;

import bd.Database;
import tools.MessageTools;
import tools.SondageTools;
import tools.UserTools;
import tools.Usual;

public class Sondage {
	
	public static JSONObject createSondage(String user, String question,String reponse1, String reponse2) throws JSONException {
		
		//Check Format
		if (Usual.wrongFormat(user, question)) {
			return Usual.JSONAnsweRefused("Erreur Format", -1);
		}
		//Check Format
		if (Usual.wrongFormat(reponse1, reponse2)) {
			return Usual.JSONAnsweRefused("Erreur Format", -1);
		}
				
		try {
					
			Connection connection = Database.getMySQLConnection();
			MongoDatabase mongodb = Database.getMongoDBConnection();
			MongoCollection<Document> sondage_collection = mongodb.getCollection("sondage");
					
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
					
			//Check sondage exists
			if (SondageTools.sondageExists(user, question, sondage_collection)) {
				connection.close();
				return Usual.JSONAnsweRefused("Erreur sondage exists", 1000);
			}	
					
			//Create Sondage
			if (SondageTools.createSondage(user, question, reponse1,reponse2 , sondage_collection)) {
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
	
	public static JSONObject removeSondage( String user, String question) throws JSONException {
		
		//Check format
		if (Usual.wrongFormat(user, question)) {
			return Usual.JSONAnsweRefused("Erreur Format", -1);
		}
		
		try {
			
			Connection connection = Database.getMySQLConnection();
			MongoDatabase mongodb = Database.getMongoDBConnection();
			MongoCollection<Document> sondage_collection = mongodb.getCollection("sondage");
			
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
			
			//Check sondage exists
			if (!SondageTools.sondageExists(user, question, sondage_collection)) {
				connection.close();
				return Usual.JSONAnsweRefused("Erreur sondage does not exist", 1000);
			}
			
			//Remove sondage
			if (SondageTools.removeSondage(user, question, sondage_collection)) {
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
	
	public static JSONObject selectAnswer( String user, String question, String response) throws JSONException {
		
		//Check format
		if (Usual.wrongFormat(user, question) || response == null) {
			return Usual.JSONAnsweRefused("Erreur Format", -1);
		}
		
		try {
			
			Connection connection = Database.getMySQLConnection();
			MongoDatabase mongodb = Database.getMongoDBConnection();
			MongoCollection<Document> sondage_collection = mongodb.getCollection("sondage");
			
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
			
			//Check sondage exists
			if (!SondageTools.sondageExists(user, question, sondage_collection)) {
				connection.close();
				return Usual.JSONAnsweRefused("Erreur sondage does not exist", 1000);
			}
			
			//Answer add
			if (SondageTools.select(question, response, sondage_collection)) {
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
	
	public static JSONObject unselectAnswer( String user, String question, String response) throws JSONException {
		
		//Check format
		if (Usual.wrongFormat(user, question) || response == null) {
			return Usual.JSONAnsweRefused("Erreur Format", -1);
		}
		
		try {
			
			Connection connection = Database.getMySQLConnection();
			MongoDatabase mongodb = Database.getMongoDBConnection();
			MongoCollection<Document> sondage_collection = mongodb.getCollection("sondage");
			
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
			
			//Check sondage exists
			if (!SondageTools.sondageExists(user, question, sondage_collection)) {
				connection.close();
				return Usual.JSONAnsweRefused("Erreur sondage does not exist", 1000);
			}
			
			//Answer add
			if (SondageTools.unselect(question, response, sondage_collection)) {
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
}
