package fr.alma.middleware1314.api;

import java.util.List;

/**
 * @author FAGNIEZ Florian and RULLIER Noemie
 * 
 */
public class User {

	private String login;
	private String password;
	private String mailAddress;
	private List<Feed> listFeed;

	/**
	 * @return the login
	 */
	public String getLogin() {
		return login;
	}

	/**
	 * @param login
	 *            the login to set
	 */
	public void setLogin(String login) {
		this.login = login;
	}

	/**
	 * @return the password
	 */
	public String getPassword() {
		return password;
	}

	/**
	 * @param password
	 *            the password to set
	 */
	public void setPassword(String password) {
		this.password = password;
	}

	/**
	 * @return the mailAddress
	 */
	public String getMailAddress() {
		return mailAddress;
	}

	/**
	 * @param mailAddress
	 *            the mailAddress to set
	 */
	public void setMailAddress(String mailAddress) {
		this.mailAddress = mailAddress;
	}

	/**
	 * @return the listFeed
	 */
	public List<Feed> getListFeed() {
		return listFeed;
	}

	/**
	 * @param listFeed
	 *            the listFeed to set
	 */
	public void setListFeed(List<Feed> listFeed) {
		this.listFeed = listFeed;
	}

}
