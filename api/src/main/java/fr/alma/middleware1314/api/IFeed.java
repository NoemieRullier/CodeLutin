package fr.alma.middleware1314.api;

/**
 * @author FAGNIEZ Florian and RULLIER Noemie
 * 
 */
public interface IFeed {

	/**
	 * Subscribe to a {@link Feed}
	 * 
	 * @param feed
	 *            The {@link Feed}
	 */
	public void subscribe(Feed feed);

	/**
	 * Subscribe to a {@link Feed}
	 * 
	 * @param url
	 *            The url of the {@link Feed}
	 */
	public void subscribe(String url);

	/**
	 * Unsubscribe to a {@link Feed}
	 * 
	 * @param feed
	 */
	public void unsubscribe(Feed feed);

	/**
	 * Unsubscribe to a {@link Feed}
	 * 
	 * @param url
	 *            The url of the {@link Feed}
	 */
	public void unsubscribe(String url);

}
