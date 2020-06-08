package servlets;

import bd.Database;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

public class insertMongo extends HttpServlet {

    //!!! Il vous faut créer  une table test_soulier avec un attribut non

    protected void doGet(HttpServletRequest req, HttpServletResponse res)
            throws ServletException, IOException {
        PrintWriter writer = res.getWriter();
        testMongo();
        writer.println("Mongo OK !!!");
    }

    private void testMongo(){
        MongoDatabase mongodb = Database.getMongoDBConnection();
        MongoCollection<Document> message_collection = mongodb.getCollection("users");

        Document query = new Document();
        query.append("nom", 10);
        message_collection.insertOne(query);
    }
}