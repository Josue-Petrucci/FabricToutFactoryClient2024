package be.petrucci.dao;

import java.net.URI;
import java.util.ArrayList;

import javax.ws.rs.core.UriBuilder;

import com.sun.jersey.api.client.*;
import com.sun.jersey.api.client.config.*;

public abstract class DAO<T> {
	private WebResource resource;
	
	public DAO( ) {
		ClientConfig config = new DefaultClientConfig();
		Client client = Client.create(config);
		resource = client.resource(getBaseURI());
	}
	
	public WebResource getResource() {
		return resource;
	}
	
	private static URI getBaseURI() {
		return UriBuilder.fromUri("http://localhost:8080/FabricToutFactoryServer2024/api/").build();
	}
	
	public abstract boolean create(T obj);
	public abstract boolean delete(T obj);
	public abstract boolean update(T obj);
	public abstract T find(T obj);
	public abstract ArrayList<T> findAll();
}
