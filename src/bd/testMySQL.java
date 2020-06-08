package bd;
import java.sql.SQLException;


import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;

public class testMySQL {

	public static void main(String[] args) throws SQLException {
		
		
		Connection connection = Database.getMySQLConnection();
		
		// on récupère le statement afin de pouvoir lancer la requête
		Statement statement = connection.createStatement();
		
		// on definit la requête SQL
		String query = "SELECT * from user ;";
		ResultSet rs =  statement.executeQuery(query);
		while (rs.next()) {
			System.out.println(rs.getString("nickname"));
		}
		// fermeture du statement et de la connexion
		/*Attention si l'on voulais être sur de fermer la connexion 
		 * en cas d'erreur il faudrait recupérer l'exception
		 * et fermer la connexion dans le catch.*/
		
		query = "INSERT INTO `user` (`nickname`,`password`,`connected`) VALUES ('kamel','lala','false');";
		statement.executeUpdate(query);
		statement.close();
		connection.close();
	}
}