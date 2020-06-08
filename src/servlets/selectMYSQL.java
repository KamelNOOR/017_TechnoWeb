package servlets;


import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import bd.Database;

/**
 * Servlet implementation class selectMYSQL
 */
public class selectMYSQL extends HttpServlet {


	protected void doGet(HttpServletRequest req, HttpServletResponse res)
			throws ServletException, IOException {
		PrintWriter writer = res.getWriter();
		selectUser();
		/*
		// JDBC driver name and database URL
	    final String JDBC_DRIVER="com.mysql.jdbc.Driver";  
	    final String DB_URL="jdbc:mysql://localhost:3306/Kamel?serverTimezone=UTC";
	
	    //  Database credentials
	    final String USER = "phpadmin";
	    final String PASS = "Noor1950";
	
	    // Set response content type
	    res.setContentType("text/html");
	    PrintWriter out = res.getWriter();
	    String title = "Database Result";
	    out.println("<html>\n" +
	       "<head><title>" + title + "</title></head>\n" +
	       "<body bgcolor=\"#f0f0f0\">\n" +
	       "<h1 align=\"center\">" + title + "</h1>\n");
	    try{
	       // Register JDBC driver
	    	out.println("aaaaaaaaaaaa");
	    	Class.forName(JDBC_DRIVER);
	    // Open a connection
	    	out.println("bbbbbbbbbb");
	    	Connection conn = DriverManager.getConnection(DB_URL,USER,PASS);
	    	out.println("ccccccccccc");
	       // Execute SQL query
	    	Statement stmt = conn.createStatement();
	    	String sql;
	    	sql = "SELECT nom FROM user";
	    	ResultSet rs = stmt.executeQuery(sql);
	    	out.println("ddddddddddddddddd");
	       // Extract data from result set
	    	while(rs.next()){
	          //Retrieve by column name
	    		out.println("eeeeeeeee");
	    		out.println(rs.getString("nom"));
	       }
	    	out.println("fffffffff");
	    	out.println("</body></html>");
	    // Clean-up environment
	    	rs.close();
	    	stmt.close();
	    	conn.close();
	    }catch(SQLException se){
	       //Handle errors for JDBC
	    	se.printStackTrace();
	    }catch(Exception e){
	       //Handle errors for Class.forName
	    	e.printStackTrace();
	    }
	    */
	    writer.println("MySQL OK !!!");
	}


	public void selectUser() {
		try {
			// TODO Auto-generated method stub
			Connection connection = Database.getMySQLConnection();
			// on récupère le statement afin de pouvoir lancer la requête
			Statement statement = connection.createStatement();

			// on definit la requête SQL
			String query = "SELECT * from user ;";
			ResultSet rs =  statement.executeQuery(query);
			while (rs.next()) {
				System.out.println(rs.getString("nom"));
			}
			// fermeture du statement et de la connexion
			/*Attention si l'on voulais être sur de fermer la connexion
			 * en cas d'erreur il faudrait recupérer l'exception
			 * et fermer la connexion dans le catch.*/

			query = "INSERT INTO `user` (`nom`) VALUES ('Jacqueline');";
			statement.executeUpdate(query);
			statement.close();
			connection.close();
		}catch(Exception e) {

		}
	}
	
	public void selectUse(HttpServletResponse response, HttpServletRequest request) {
		
		
	    	   
	       
	}
}