package servlets;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class helloworld extends HttpServlet {

	protected void doGet(HttpServletRequest req, HttpServletResponse res)
		throws ServletException, IOException {
		PrintWriter writer = res.getWriter();
		
		writer.println("Hello World !!!");
	}
	
	protected void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		doGet(req, res);
	}

}