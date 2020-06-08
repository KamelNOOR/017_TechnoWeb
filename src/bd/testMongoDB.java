package bd;

import org.bson.Document;

import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;

public class testMongoDB {

	public static void main(String[] args) {
		
		MongoDatabase mongodb = Database.getMongoDBConnection();
		
		MongoCollection<Document> message_collection = mongodb.getCollection("users");
		
		Document query = new Document();
		query.append("nom", 1);
		query.append("nom", 2);
		message_collection.insertOne(query);
		
		System.out.println("######******#########");
	}
}	