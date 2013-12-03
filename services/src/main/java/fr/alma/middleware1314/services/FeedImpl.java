package fr.alma.middleware1314.services;

import java.net.URL;
import java.util.List;

import javax.ejb.Remote;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import com.sun.syndication.feed.synd.SyndFeed;
import com.sun.syndication.io.SyndFeedInput;
import com.sun.syndication.io.XmlReader;

import fr.alma.middleware1314.api.Feed;
import fr.alma.middleware1314.api.IFeed;
import fr.alma.middleware1314.api.User;

@Stateless
@Remote(IFeed.class)
public class FeedImpl implements IFeed {

	@PersistenceContext
	private EntityManager em;

	@Override
	public void subscribe(User user, Feed feed) {
		user.addFeed(feed);
		em.flush();
	}

	@Override
	public void subscribe(User user, String url) throws Exception {
		Query query = em
				.createQuery("from fr.alma.middleware1314.api.Feed feed where feed.url = :feedUrl");
		query.setParameter("feedUrl", url);
		List<Feed> feeds = (List<Feed>) query.getResultList();
		if (feeds.size() == 0) {
			System.out.println("Create new Feed");
			URL source = new URL(url);
			SyndFeedInput input = new SyndFeedInput();
			SyndFeed syndFeed;
			syndFeed = input.build(new XmlReader(source));
			Feed feedToAdd = new Feed();
			feedToAdd.setTitle(syndFeed.getTitle());
			feedToAdd.setDescription(syndFeed.getDescription());
			feedToAdd.setUrl(url);
			user.addFeed(feedToAdd);
			em.persist(feedToAdd);
		} else {
			user.addFeed(feeds.get(0));
		}
		em.flush();
		System.out.println("Test list size " + user.getListFeed().size());
		System.out.println("The user " + user.getLogin()
				+ " was subscribe to the feed");
	}

	@Override
	public void unsubscribe(User user, Feed feed) {
		user.removeFeed(feed);
		em.flush();
	}

	@Override
	public void unsubscribe(User user, String url) {
		Query query = em
				.createQuery("from fr.alma.middleware1314.api.Feed feed where feed.url = :feedUrl");
		query.setParameter("feedUrl", url);
		List<Feed> feeds = (List<Feed>) query.getResultList();
		Feed feed = feeds.get(0);
		if (feed != null) {
			user.removeFeed(feed);
			em.flush();
		}
	}

	@Override
	public String displayFeeds(User user) throws Exception {
		System.out.println("Display feed");
		Query query = em
				.createQuery("from fr.alma.middleware1314.api.User user where user.login = :userLogin");
		query.setParameter("userLogin", user.getLogin());
		User userBase = (User) query.getSingleResult();
		System.out.println("Nombre de flux " + userBase.getListFeed().size());
		String result = "";
		for (Feed feed : userBase.getListFeed()) {
			URL source = new URL(feed.getUrl());
			SyndFeedInput input = new SyndFeedInput();
			SyndFeed syndFeed;
			syndFeed = input.build(new XmlReader(source));
			result += syndFeed.getDescription() + "\n";
		}
		return result;
	}

}
