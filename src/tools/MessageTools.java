package tools;




import java.sql.SQLException;

import java.util.ArrayList;

import org.bson.Document;
import org.json.JSONException;
import org.json.JSONObject;

import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;

public class MessageTools {

	public static boolean insertMessage(String user, String msg, MongoCollection<Document> message_collection){
		
		boolean msgInserted = false;
		
		Document query = new Document();
		query.append("user",user);
		query.append("msg",msg);
		
		message_collection.insertOne(query);
		
		//Check if worked
		FindIterable<Document> find = message_collection.find(query);
		MongoCursor<Document> cursor = find.iterator();
		
		while(cursor.hasNext()) {
			cursor.next();
			msgInserted = true;
		}
		
		return msgInserted;
	}
	
	public static boolean removeMessage(String user, String msg, MongoCollection<Document> message_collection){
		
		boolean msgRemoved = true;
		
		Document query = new Document();
		query.append("user",user);
		query.append("msg",msg);
		
		message_collection.deleteOne(query);
		
		//Check if worked
		FindIterable<Document> find = message_collection.find(query);
		MongoCursor<Document> cursor = find.iterator();
		
		while(cursor.hasNext()) {
			cursor.next();
			msgRemoved = false;
		}
		
		return msgRemoved;
	}
	
	public static boolean messageExists(String user, String msg, MongoCollection<Document> message_collection){
		
		boolean msgExists = false;
		
		Document query = new Document();
		query.append("user",user);
		query.append("msg",msg);
		
		//Check if exists
		FindIterable<Document> find = message_collection.find(query);
		MongoCursor<Document> cursor = find.iterator();
		
		while(cursor.hasNext()) {
			cursor.next();
			msgExists = true;
		}
		
		return msgExists;
	}

	public static JSONObject getMessage(String user, MongoCollection<Document> message_collection) throws JSONException, SQLException {
		
		JSONObject json = new JSONObject();
		json.put("User", user);
		boolean haveMessage = false;
		ArrayList<String> messages = new ArrayList<String>();
		
		Document query = new Document();
		query.append("user",user);
		
		//Check if exists
		FindIterable<Document> find = message_collection.find(query);
		MongoCursor<Document> cursor = find.iterator();
		
		while(cursor.hasNext()) {
			messages.add((cursor.next().getString("msg")));
			haveMessage = true;
		}
		
		json.put("MessageList", messages);
		
		if(haveMessage) {
			json.put("Status", "OK");
			return json;
		}
		return null;
		
	}

	public static JSONObject getSpecificMessage(String search, MongoCollection<Document> message_collection) throws JSONException {
		
		JSONObject json = new JSONObject();
		
		boolean haveMessage = false;
		ArrayList<String> messages = new ArrayList<String>();
		
		Document query = new Document();
		query.append("$regex",search);
		Document findQuery = new Document();
		findQuery.append("msg", query);
		//Check if exists
		FindIterable<Document> find = message_collection.find(findQuery);
		MongoCursor<Document> cursor = find.iterator();
		
		while(cursor.hasNext()) {
			messages.add((cursor.next().getString("msg")));
			haveMessage = true;
		}
		
		json.put("Message Searched List", messages);
		
		if(haveMessage) {
			return json;
		}
		return null;
	}
}
