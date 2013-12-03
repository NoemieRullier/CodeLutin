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
		em.merge(user);
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
			em.persist(feedToAdd);
			user.addFeed(feedToAdd);
		} else {
			user.addFeed(feeds.get(0));
		}
		em.merge(user);
		System.out.println("The user " + user.getLogin()
				+ " was subscribe to the feed");
	}

	@Override
	public void unsubscribe(User user, Feed feed) {
		user.removeFeed(feed);
		em.merge(user);
	}

	@Override
	public void unsubscribe(User user, String url) {
		Query query = em
				.createQuery("from fr.alma.middleware1314.api.Feed feed where feed.url = :feedUrl");
		query.setParameter("feedUrl", url);
		List<Feed> feeds = query.getResultList();
		Feed feed = feeds.get(0);
		if (feed != null) {
			user.removeFeed(feed);
			em.merge(user);
		}
	}

	@Override
	public String displayFeed(Feed feed) throws Exception {
		System.out.println("Display feed");
		URL source = new URL(feed.getUrl());
		SyndFeedInput input = new SyndFeedInput();
		SyndFeed syndFeed;
		syndFeed = input.build(new XmlReader(source));
		return syndFeed.getDescription();
	}

}
