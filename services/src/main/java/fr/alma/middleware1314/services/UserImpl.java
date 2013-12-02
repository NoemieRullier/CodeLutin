package fr.alma.middleware1314.services;

import javax.ejb.Remote;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import api.IUser;
import api.User;

@Stateless
@Remote(IUser.class)
public class UserImpl implements IUser {

	@PersistenceContext
	private EntityManager em;

	@Override
	public void signIn(String login, String password, String mailAddress) {
		// Verify that the login do not exist
		Query query = em
				.createQuery("SELECT COUNT(user.login) FROM USER WHERE user.login = :userLogin");
		query.setParameter("userLogin", login);
		int nombre = query.getFirstResult();
		if (nombre > 0){
			// TODO Exception
			System.out.println("Le login existe deja");
		}
		else {
			// Add the user in the BD
			User user = new User(login, password, mailAddress);
			em.persist(user);			
		}
	}

	@Override
	public boolean connection(String login, String password) {
		// Verify that the user exist in the BD
		Query query = em
				.createQuery("SELECT COUNT(user.id) FROM USER WHERE user.login = :userLogin AND user.password = :userPassword");
		query.setParameter("userLogin", login);
		query.setParameter("userPassword", password);
		int nombre = query.getFirstResult();
		boolean exist = false;
		if (nombre > 0) {
			exist = true;
		}
		return exist;
	}

	@Override
	public void disconnection() {
		// TODO Add boolean in user to know if he's connected ????
	}

}
