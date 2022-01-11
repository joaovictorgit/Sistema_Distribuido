package br.com.mavenquickstart;

import static javax.naming.Context.INITIAL_CONTEXT_FACTORY;
import static javax.naming.Context.PROVIDER_URL;
import static javax.naming.Context.URL_PKG_PREFIXES;
import java.util.Hashtable;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import ufr.br.RemoteServer;
	public class App{
		public static void main(String[] args) {
			String host = "localhost";
			String port = "8080";
			Context remotingContext;
			try {
				remotingContext = createRemoteEjbContext(host, port);
			} catch (NamingException e) {
				System.err.println("Error setting up remoting context");
				e.printStackTrace();
				return;
			}
			String ejbUrl ="ejb:/ServerEJB/RemoteServerImpl!ufr.br.RemoteServer";
			RemoteServer service;
			try {
				service = createEjbProxy(remotingContext, ejbUrl,
				RemoteServer.class);
			} catch (NamingException e) {
				System.out.println("Error resolving bean");
				e.printStackTrace();
				return;
			} catch (ClassCastException e) {
				System.out.println("Resolved EJB is of wrong type");
				e.printStackTrace();
			return;
			}
			// Call remote method with parameter
			String toGreet = "World";
			System.out.println("Fazendo chamada...");
			String exampleResult;
			try {
				exampleResult = service.getString(toGreet);
				System.out.println(exampleResult);
			} catch (Exception e) {
				System.out.println("Error accessing remote bean");
				e.printStackTrace();
				return;
			}
				System.out.println("Example result: " + exampleResult);
		}
		private static Context createRemoteEjbContext(String host, String port) throws NamingException {
				Hashtable<Object, Object> props = new Hashtable<>();
				props.put(INITIAL_CONTEXT_FACTORY,
				"org.jboss.naming.remote.client.InitialContextFactory");
				props.put(URL_PKG_PREFIXES, "org.jboss.ejb.client.naming");
				props.put("jboss.naming.client.ejb.context", false);
				props.put("org.jboss.ejb.client.scoped.context", true);
				props.put("endpoint.name", "client-endpoint");
				props.put("remote.connectionprovider.create.options.org.xnio.Options.SSL_ENABLED", false);
				props.put("remote.connections", "default");
				props.put("remote.connection.default.connect.options.org.xnio.Options.SASL_POLICY_NOANONYMOUS", false);
				props.put(PROVIDER_URL, "http-remoting://" + host + ":" +port);
				props.put("remote.connection.default.host", host);
				props.put("remote.connection.default.port", port);
				return new InitialContext(props);
		}
	
		@SuppressWarnings("unchecked")
		private static <T> T createEjbProxy(Context remotingContext, String ejbUrl, Class<T> ejbInterfaceClass)throws NamingException, ClassCastException {
			Object resolvedproxy = remotingContext.lookup(ejbUrl);
			return (T) resolvedproxy;
		}
}