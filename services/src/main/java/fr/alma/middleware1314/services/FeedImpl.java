package fr.alma.middleware1314.services;

import java.net.URL;

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
				.createQuery("SELECT * FROM FEED WHERE feed.url = :feedUrl");
		query.setParameter("feedUrl", url);
		Feed feed = (Feed) query.getSingleResult();
		if (feed == null) {
			URL source = new URL(url);
			SyndFeedInput input = new SyndFeedInput();
			SyndFeed syndFeed;
			syndFeed = input.build(new XmlReader(source));
			Feed feedToAdd = new Feed();
			feedToAdd.setTitle(syndFeed.getTitle());
			feedToAdd.setDescription(syndFeed.getDescription());
			feedToAdd.setUrl(url);
			em.persist(feed);
		}
		user.addFeed(feed);
		em.merge(user);
	}

	@Override
	public void unsubscribe(User user, Feed feed) {
		user.removeFeed(feed);
		em.merge(user);
	}

	@Override
	public void unsubscribe(User user, String url) {
		Query query = em
				.createQuery("SELECT * FROM FEED WHERE feed.url = :feedUrl");
		query.setParameter("feedUrl", url);
		Feed feed = (Feed) query.getSingleResult();
		if (feed != null) {
			user.removeFeed(feed);
			em.merge(user);
		}
	}

	// TODO Supprimer un Feed ?
}
