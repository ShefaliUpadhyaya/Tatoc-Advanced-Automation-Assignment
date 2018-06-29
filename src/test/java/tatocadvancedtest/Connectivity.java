package tatocadvancedtest;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Connectivity {
	public Connection connection;
	public Connectivity() {
		try {
			Class.forName("com.mysql.jdbc.Driver");
			connection = DriverManager.getConnection("jdbc:mysql://10.0.1.86:3306/tatoc","tatocuser","tatoc01");
		} 
		catch (ClassNotFoundException e) {
			System.out.println(e.getMessage());
		}  
		catch (SQLException e) {
			System.out.println(e.getMessage());
		}
		
	}
}
