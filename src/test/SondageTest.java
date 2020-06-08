package test;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

import org.json.JSONException;
import org.json.JSONObject;


import services.Sondage;

public class SondageTest {
	
public static void main(String[] args) throws SQLException {
		
		try {
			
			JSONObject json = new JSONObject();
	
			System.out.println("Create Sondage :");
			json = Sondage.createSondage("kamel", "ca va","oui","non" );
			System.out.println(json.toString());
			
			System.out.println("Add Answer :");
			json = Sondage.selectAnswer("kamel", "ca va","oui");
			System.out.println(json.toString());
			
			System.out.println("Delete Answer :");
			json = Sondage.unselectAnswer("kamel", "ca va","oui");
			System.out.println(json.toString());
			
			/*System.out.println("Remove Sondage :");
			json = Sondage.removeSondage("kamel", "ca va" );
			System.out.println(json.toString());*/
			
			
		}catch (JSONException e) {
			
			System.out.println("Erreur JSON in test, code 100");
		}
			
	}
}
