package test;


import java.sql.SQLException;
import java.util.Calendar;
import java.util.GregorianCalendar;

import org.json.JSONException;
import org.json.JSONObject;

import services.User;



public class UserTest {
		
	public static void main(String[] args) throws SQLException {
		
		try {
			
			JSONObject json = new JSONObject();
			
			System.out.println("CreateUser :");
			json = User.createUser("kamel","kamel");
			System.out.println(json.toString());
			
			System.out.println("Login :");
			json = User.login("kamel","kamel");
			System.out.println(json.toString());
			/*
			System.out.println("Logout :");
			json = User.logout("kamel", "kamel");
			System.out.println(json.toString());
		*/
			System.out.println("Search User :");
			json = User.getSpecificUser("a");
			System.out.println(json.toString());
			
		}catch (JSONException e) {
			
			System.out.println("Erreur JSON in test, code 100");
		}
			
		}
}
