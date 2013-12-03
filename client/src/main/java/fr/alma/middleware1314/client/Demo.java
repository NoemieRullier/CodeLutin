package fr.alma.middleware1314.client;

import java.io.IOException;
import java.util.Hashtable;

import javax.naming.Context;
import javax.naming.InitialContext;

import fr.alma.middleware1314.api.Feed;
import fr.alma.middleware1314.api.IFeed;
import fr.alma.middleware1314.api.IUser;
import fr.alma.middleware1314.api.User;

public class Demo {

	public static void main(String[] args) throws IllegalArgumentException, IOException, Exception {
		final Hashtable jndiProperties = new Hashtable();
		jndiProperties.put(Context.URL_PKG_PREFIXES, "org.jboss.ejb.client.naming");
		final Context context = new InitialContext(jndiProperties);
		// Create service for User and Feed
		IUser userService = (IUser) context.lookup("ejb:/reader-services-0.1-SNAPSHOT/UserImpl!fr.alma.middleware1314.api.IUser");
		IFeed feedService = (IFeed) context.lookup("ejb:/reader-services-0.1-SNAPSHOT/FeedImpl!fr.alma.middleware1314.api.IFeed");

		// Sign a new user
		userService.signIn("Nomyx", "Pa$$word", "noemie.rullier@etu.univ-nantes.fr");
		// Connect the new user
		User user = userService.connection("Nomyx", "Pa$$word");
		// Subscribe the user to a new feed 
		feedService.subscribe(user, "https://sites.google.com/site/usagymancenis/infos-pratiques/messages/posts.xml");
		user = userService.connection("Nomyx", "Pa$$word");
		System.out.println("Mail: "+user.getMailAddress());
		System.out.println("Size list " + user.getListFeed().size());
		// Display description of the user's feeds
		feedService.displayFeeds(user);
	}
}
