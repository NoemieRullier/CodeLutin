package fr.alma.middleware.database.controller;

import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import fr.alma.middleware.database.DatabaseManager;
import fr.alma.middleware1314.api.Feed;


/**
 * This class provides an implementation for FeedsController.
 * He owns methods for add and get Feeds, etc ...
 * @author Niiner
 */
public class FeedsController {


	/*@Inject
    private ItineraryController itineraryContr;*/
	/*@Inject
    private LinkageController linkageContr;*/


	/**
	 * Add the Feed in the database.
	 * @param Feed
	 */
	public void add(Feed feed) throws SQLException{

		Statement s = DatabaseManager.getInstance().getConnection().createStatement();
		String sqlquery = "INSERT INTO Feeds (title, description, url)"
				+ "VALUES('"+ feed.getTitle() + "', "
				+ "'" + feed.getDescription() + "', "
				+ "'" + feed.getUrl() + "') ";
		System.out.println(sqlquery);
		s.executeUpdate(sqlquery);
	}

	/**
	 * Return all the Feeds from Feed table.
	 * @return a List of Feeds
	 * @throws SQLException
	 */
	public List<Feed> getAll() throws SQLException{
		List<Feed> Feeds = new ArrayList();

		Statement s = DatabaseManager.getInstance().getConnection().createStatement();
		String sqlquery = "SELECT * FROM Feeds;";
		ResultSet res = s.executeQuery(sqlquery);

		/* 
		while(res.next()){
			Feeds.add(new Feed(res.getString("title"),
					res.getString("description"),
					res.getString("url")));
		}
		*/

		return Feeds;
	}

	/**
	 * Remove all the Feeds in Feed table.
	 * @throws SQLException
	 */
	public void removeAll() throws SQLException{
		Statement s = DatabaseManager.getInstance().getConnection().createStatement();
		String sqlquery = "DELETE FROM Feeds;";
		s.executeUpdate(sqlquery);
	}

	/**
	 * Test this class
	 * @param args
	 */
	public static void main(String args[]){

		System.out.print(System.getProperty("user.dir" ));
		FeedsController FeedContr = new FeedsController();
		try {
			FeedContr.removeAll();
		} catch (SQLException ex) {
			Logger.getLogger(FeedsController.class.getName()).log(Level.SEVERE, null, ex);
		}
		try {
			System.out.println(FeedContr.getAll().size());
		} catch (SQLException ex) {
			Logger.getLogger(FeedsController.class.getName()).log(Level.SEVERE, null, ex);
		}
	}


}
