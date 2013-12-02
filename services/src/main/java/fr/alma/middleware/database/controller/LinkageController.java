package fr.alma.middleware.database.controller;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;

import fr.alma.middleware.database.DatabaseManager;

public class LinkageController {

	/**
	 * Remove all the entries in Linkage.
	 * @throws SQLException
	 */
	public void removeAll() throws SQLException{
		Statement s = DatabaseManager.getInstance().getConnection().createStatement();
		String sqlquery = "DELETE FROM Linkage;";
		s.executeUpdate(sqlquery);
	}

	/**
	 * Test this class.
	 * @param args
	 */
	public static void main(String args[]){
		LinkageController linkageContr = new LinkageController();
		try {
			linkageContr.removeAll();
		} catch (SQLException ex) {
			Logger.getLogger(LinkageController.class.getName()).log(Level.SEVERE, null, ex);
		}
	}

}
