package test;

import java.sql.SQLException;

import org.json.JSONException;
import org.json.JSONObject;

import services.Friend;


public class FriendTest {

	public static void main(String[] args) throws SQLException {
		
		try {
			
			JSONObject json = new JSONObject();
			
			System.out.println("Add Friend :");
			json = Friend.addFriend("kamel", "vsh");
			System.out.println(json.toString());
			
			System.out.println("Add Friend :");
			json = Friend.addFriend("kamel", "sura");
			System.out.println(json.toString());
			
			System.out.println("Add Friend :");
			json = Friend.addFriend("kamel", "papa");
			System.out.println(json.toString());
			
			System.out.println("Add Friend :");
			json = Friend.addFriend("kamel", "kamel");
			System.out.println(json.toString());
			
			System.out.println("Remove Friend :");
			json = Friend.removeFriend("kamel", "vsh");
			System.out.println(json.toString());
			
			System.out.println("Get Friends :");
			json = Friend.getFriend("kamel");
			System.out.println(json.toString());
			
		}catch (JSONException e) {
			
			System.out.println("Erreur JSON in test, code 100");
		}
			
		}
}
