package fr.isima.ejb.container;

import java.util.HashMap;
import java.util.Map;

import fr.isima.ejb.exceptions.invokeMethodsByAnnotationException;

public class ClientManager {

	private Map<Object, Client> clients;

	public ClientManager() {
		clients = new HashMap<Object, Client>();
	}

	public boolean verifyClient(Object client) {
		if (clients.containsKey(client))
			return true;
		clients.put(client, new Client(client));
		return false;
	}

	public Client getClient(Object client) {
		verifyClient(client);
		return clients.get(client);
	}

	public void removeClient(Object client) {
		if (clients.containsKey(client)) {
			clients.remove(client);
		}
	}

	public void destructClient(Client client)
			throws invokeMethodsByAnnotationException {
		if (clients.containsKey(client)) {
			clients.remove(client);
			client.release();
		}
	}

}
