package fr.alma.middleware1314.services;

import java.util.List;

import javax.ejb.Remote;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import fr.alma.middleware1314.api.IUser;
import fr.alma.middleware1314.api.User;

@Stateless
@Remote(IUser.class)
public class UserImpl implements IUser {

	@PersistenceContext
	private EntityManager em;

	@Override
	public void signIn(String login, String password, String mailAddress) {
		// Verify that the login do not exist
		Query query = em
				.createQuery("from fr.alma.middleware1314.api.User user where user.login = :userLogin");
		query.setParameter("userLogin", login);
		List<User> users = query.getResultList();
		if (users.size() > 0) {
			System.out.println("Le login existe deja");
		} else {
			// Add the user in the BD
			User user = new User(login, password, mailAddress);
			em.persist(user);
			System.out.println("Utilisateur ajoute");
		}
	}

	@Override
	public User connection(String login, String password) {
		// Verify that the user exist in the BD
		Query query = em
				.createQuery("from fr.alma.middleware1314.api.User user where user.login = :userLogin and user.password = :userPassword");
		query.setParameter("userLogin", login);
		query.setParameter("userPassword", password);
		List<User> users = query.getResultList();
		User user = null;
		if (users.size() > 0) {
			user = users.get(0);
		}
		return user;
	}

	@Override
	public void disconnection() {
		// TODO Add boolean in user to know if he's connected ????
	}

}
