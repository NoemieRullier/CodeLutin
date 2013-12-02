package api;

import java.io.Serializable;
import java.util.List;

import javax.persistence.Basic;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

/**
 * @author FAGNIEZ Florian and RULLIER Noemie
 * 
 */
@Entity
@Table(name = "USER")
public class User implements Serializable {

	private static final long serialVersionUID = -212528778761501136L;

	private int id;
	// TODO login is unique
	private String login;
	private String password;
	private String mailAddress;
	private List<Feed> listFeed;

	public User(final String login, final String password,
			final String mailAddress) {
		this.login = login;
		this.password = password;
		this.mailAddress = mailAddress;
	}

	/**
	 * @return the id
	 */
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	public int getId() {
		return id;
	}

	/**
	 * @param id
	 *            the id to set
	 */
	public void setId(int id) {
		this.id = id;
	}

	/**
	 * @return the login
	 */
	@Id
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
	@Basic
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
	@Basic
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
	@ManyToMany
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

	/**
	 * Adding feed to the user list
	 * 
	 * @param feed
	 */
	public void addFeed(Feed feed) {
		this.listFeed.add(feed);
	}

	/**
	 * Removing fee to the user list
	 * 
	 * @param feed
	 */
	public void removeFeed(Feed feed) {
		this.listFeed.remove(feed);
	}

}
