package servlets.friend;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONException;
import org.json.JSONObject;

public class GetFriend extends HttpServlet{
	
	protected void doGet(HttpServletRequest req, HttpServletResponse rep) throws IOException, ServletException {
		
		String user = req.getParameter("user");
		
		try {
			JSONObject retour = services.Friend.getFriend(user);
			
			rep.setContentType("text/json");
			PrintWriter out = rep.getWriter();
			out.println(retour.toString());
		} catch (JSONException e) {
			e.printStackTrace();
		}
		
	}
	
}
