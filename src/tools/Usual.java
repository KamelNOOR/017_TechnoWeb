package tools;

import org.json.JSONException;
import org.json.JSONObject;

public class Usual {
		
		//Check format
		public static boolean wrongFormat(String string1, String string2) {
			return (string1 == null && string2 == null);
		}
		
		public static JSONObject JSONAnsweAccepted() throws JSONException{
			
			JSONObject JSONAnswer = new JSONObject();
			JSONAnswer.put("Status", "OK");
			return JSONAnswer;
			
		}
		
		public static JSONObject JSONAnsweRefused(String message, int codeErreur) throws JSONException{
			
			JSONObject JSONAnswer = new JSONObject();
			JSONAnswer.put("Status", "KO");
			JSONAnswer.put("Message", message);
			JSONAnswer.put("codeErreur", codeErreur);
			return JSONAnswer;
			
		}
		
}
