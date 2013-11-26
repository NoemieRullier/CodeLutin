package fr.alma.middleware1314.api;

/**
 * @author FAGNIEZ Florian and RULLIER Noemie
 * 
 */
public interface IUser {

	/**
	 * Register an user
	 * 
	 * @param login
	 *            The login
	 * @param password
	 *            The password
	 * @param mailAddress
	 *            The user's mail address
	 * @return a new {@link User}
	 */
	public User signIn(String login, String password, String mailAddress);

	/**
	 * Connect an {@link User}
	 * 
	 * @param login
	 */
	public void connection(String login);

	/**
	 * Disconnect an {@link User}
	 */
	public void disconnection();

}
