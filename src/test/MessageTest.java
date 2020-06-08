package test;
//sudo systemctl start mongod
import java.sql.SQLException;

import org.json.JSONException;
import org.json.JSONObject;

import services.Message;


public class MessageTest {
	
	public static void main(String[] args) throws SQLException {
		
		try {
			
			JSONObject json = new JSONObject();
			
			System.out.println("Add Message :");
			json = Message.addMessage("kamel", "coucou");
			System.out.println(json.toString());
			
			/*
			System.out.println("Remove Message :");
			json = Message.removeMessage("kamel", "coucou");
			System.out.println(json.toString());
			*/
			
			System.out.println("Add Message :");
			json = Message.addMessage("kamel", "ca va");
			System.out.println(json.toString());
			
			System.out.println("Get Message :");
			json = Message.getMessage("kamel");
			System.out.println(json.toString());
			
			System.out.println("Get Specific Message :");
			json = Message.getSpecificMessage("ca");
			System.out.println(json.toString());
			
		}catch (JSONException e) {
			
			System.out.println("Erreur JSON in test, code 100");
		}
			
	}
}
