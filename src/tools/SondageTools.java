package tools;

import java.util.HashMap;
import java.util.Map;

import org.bson.Document;

import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;

public class SondageTools {

	public static boolean createSondage(String user, String question,String reponse1, String reponse2, MongoCollection<Document> sondage_collection) {
		boolean sondageCreated = false;
		
		Document query = new Document();
		query.append("User",user);
		query.append("Question",question);
		query.append(reponse1,0);
		query.append(reponse2,0);
		
		sondage_collection.insertOne(query);
		
		//Check if worked
		FindIterable<Document> find = sondage_collection.find(query);
		MongoCursor<Document> cursor = find.iterator();
		
		while(cursor.hasNext()) {
			cursor.next();
			sondageCreated = true;
		}
		
		return sondageCreated;
	}

	public static boolean sondageExists(String user, String question, MongoCollection<Document> sondage_collection) {
		
		boolean sondageExists = false;
		
		Document query = new Document();
		query.append("User",user);
		query.append("Question",question);
		
		//Check if exists
		FindIterable<Document> find = sondage_collection.find(query);
		MongoCursor<Document> cursor = find.iterator();
		
		while(cursor.hasNext()) {
			cursor.next();
			sondageExists = true;
		}
		
		return sondageExists;
	}

	public static boolean removeSondage(String user, String question, MongoCollection<Document> sondage_collection) {
		
		boolean sondageRemoved = false;
		long first = sondage_collection.countDocuments();
		Document query = new Document();
		query.append("User",user);
		query.append("Question",question);
		
		sondage_collection.deleteOne(query);
		
		//Check if worked
		long second = sondage_collection.countDocuments();
		if(first != second) {
			sondageRemoved = true;
		}
		return sondageRemoved;
	}

	public static boolean select(String question,String response, MongoCollection<Document> sondage_collection) {
		
		boolean answerAdded = true;
		int i = 0;
		Document query = new Document();
		query.append("Question",question);
		
		//Check if exists
		FindIterable<Document> find = sondage_collection.find(query);
		MongoCursor<Document> cursor = find.iterator();
		MongoCursor<Document> cursor2 = find.iterator();
		while(cursor.hasNext()) {		
			Document doc = cursor.next();
			i = doc.getInteger(response);
			i++;
			doc.replace(response,i );
			
			Document doc2 = cursor2.next();

			sondage_collection.findOneAndReplace(doc2, doc);
		}
		
		return answerAdded;
		
	}

	public static boolean unselect(String question, String response, MongoCollection<Document> sondage_collection) {
		boolean answerAdded = true;
		int i = 0;
		Document query = new Document();
		query.append("Question",question);
		
		//Check if exists
		FindIterable<Document> find = sondage_collection.find(query);
		MongoCursor<Document> cursor = find.iterator();
		MongoCursor<Document> cursor2 = find.iterator();
		while(cursor.hasNext()) {		
			Document doc = cursor.next();
			i = doc.getInteger(response);
			i--;
			doc.replace(response,i );
			
			Document doc2 = cursor2.next();
			
			sondage_collection.findOneAndReplace(doc2, doc);
		}
		
		return answerAdded;
	}

}
