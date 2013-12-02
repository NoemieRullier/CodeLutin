package api;

import java.io.IOException;

/**
 * @author FAGNIEZ Florian and RULLIER Noemie
 * 
 */
public interface IFeed {

	/**
	 * Subscribe to a {@link Feed}
	 * 
	 * @param user
	 *            The {@link User}
	 * @param feed
	 *            The {@link Feed}
	 */
	public void subscribe(User user, Feed feed);

	/**
	 * Subscribe to a {@link Feed}
	 * 
	 * @param user
	 *            The {@link User}
	 * @param url
	 *            The url of the {@link Feed}
	 * @throws Exception 
	 */
	public void subscribe(User user, String url)
			throws IllegalArgumentException, IOException, Exception;

	/**
	 * Unsubscribe to a {@link Feed}
	 * 
	 * @param user
	 *            The {@link User}
	 * @param feed
	 *            The {@link Feed}
	 */
	public void unsubscribe(User user, Feed feed);

	/**
	 * Unsubscribe to a {@link Feed}
	 * 
	 * @param user
	 *            The {@link User}
	 * @param url
	 *            The url of the {@link Feed}
	 */
	public void unsubscribe(User user, String url);

}
