package fr.alma.middleware1314.services;

import java.util.Hashtable;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;

public class Demo {

	public static void main(String[] args) throws NamingException {
		final Hashtable jndiProperties = new Hashtable();
		jndiProperties.put(Context.URL_PKG_PREFIXES, "org.jboss.ejb.client.naming");
		final Context context = new InitialContext(jndiProperties);
		// Nom de la classe d'implémentation + /local ou /remote ???????
		UserImpl userService = (UserImpl) context.lookup("ejb:/reader-services-0.1-SNAPSHOT/HelloBean!fr.alma.middleware1314.services.sample.HelloRemote");
//		userService.signIn(login, password, mailAddress);
	}
}
