package fr.alma.middleware.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class DatabaseManager {

	private static DatabaseManager instance;
	private Connection connection = null;

	/**
	 * Constructor
	 */
	private DatabaseManager(){

		// Connection to Database
		try {
			Class.forName("org.h2.Driver"); // loads the driver
			this.setConnection(DriverManager.getConnection(
					"jdbc:h2:~/test", "sa", ""));

			// Create table for the database
			createTableUsers(); 
			createTableFeeds(); 
			createTableLinkage(); 
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	/**
	 * Create Table Users
	 * An user owns a login, a password and a mail address
	 */
	private void createTableUsers() {
		String sqlquery = "CREATE TABLE IF NOT EXISTS Users" + "(id INT,"
				+ "login VARCHAR(20)," 
				+ "password VARCHAR(20)," 
				+ "mailAddress VARCHAR(40),"
				+ "PRIMARY KEY(id));";

		try {
			Statement stmt = connection.createStatement();
			stmt.executeUpdate(sqlquery);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Create Table Feeds
	 * A feed owns a title, a description and an URL
	 */
	private void createTableFeeds() {
		String sqlquery = "CREATE TABLE IF NOT EXISTS Feeds" + "(id INT,"
				+ "title VARCHAR(50)," 
				+ "description VARCHAR(200)," 
				+ "url VARCHAR(100),"
				+ "PRIMARY KEY(id));";

		try {
			Statement stmt = connection.createStatement();
			stmt.executeUpdate(sqlquery);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Create Table Linkage
	 * The table owns the idUser and the idFeed
	 */
	private void createTableLinkage() {
		String sqlquery = "CREATE TABLE IF NOT EXISTS Linkage" + "(idUser INT," 
				+ "idFeed INT,"
				+ "PRIMARY KEY(idUser, idFeed));";

		try {
			Statement stmt = connection.createStatement();
			stmt.executeUpdate(sqlquery);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}



	/**
	 * Pattern Singleton with Configuration
	 * 
	 * @return The Database
	 * @throws WrongInterfacePortException
	 * @throws NoSuchPortException
	 */
	public static synchronized DatabaseManager getInstance(){
		if (instance == null) {
			instance = new DatabaseManager();
		}
		return instance;
	}

	/**
	 * Close the Database
	 * @throws SQLException 
	 */
	public void close() throws SQLException{
		this.connection.close();
	}

	/**
	 * Main method : launch database
	 * 
	 * @param args
	 * @throws SQLException 
	 * @throws WrongInterfacePortException
	 * @throws NoSuchPortException
	 */
	public static void main(String args[]) throws SQLException{
		DatabaseManager.getInstance();
	}

	/**
	 * Set the Connection
	 * 
	 * @param connection
	 */
	private void setConnection(Connection connection) {
		this.connection = connection;
	}

	/**
	 * Get the Connection
	 * 
	 * @return
	 */
	public Connection getConnection() {
		return this.connection;
	}
}

