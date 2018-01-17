package com.app;

import java.util.concurrent.BlockingQueue;

import com.twitter.hbc.ClientBuilder;
import com.twitter.hbc.core.Constants;
import com.twitter.hbc.core.endpoint.DefaultStreamingEndpoint;
import com.twitter.hbc.core.endpoint.StatusesSampleEndpoint;
import com.twitter.hbc.core.processor.StringDelimitedProcessor;
import com.twitter.hbc.httpclient.BasicClient;
import com.twitter.hbc.httpclient.auth.Authentication;
import com.twitter.hbc.httpclient.auth.OAuth1;

public class Client {

	private Authentication auth;

	private BasicClient client;

	public Client() {
		this.auth = new OAuth1(Credential.API_KEY, Credential.SECRET_KEY, //
				Credential.ACCESS_TOKEN, Credential.TOKEN_SECRET);
		// Authentication auth = new com.twitter.hbc.httpclient.auth.BasicAuth(username,
		// password);
	}

	public void connectToClient(DefaultStreamingEndpoint endpoint, BlockingQueue<String> queue) {
		// Create a new BasicClient. By default gzip is enabled.
		this.client = new ClientBuilder() //
				.name("sampleExampleClient") //
				.hosts(Constants.STREAM_HOST) //
				.endpoint(endpoint) //
				.authentication(auth) //
				.processor(new StringDelimitedProcessor(queue)) //
				.build();

		// Establish a connection
		client.connect();
	}

	public String getMessage() throws Exception {
		return client.getExitEvent().getMessage();
	}

	public boolean isDone() {
		return client.isDone();
	}

	public void disconnetClient() {
		client.stop();
	}

	public long msgCount() throws Exception {
		return client.getStatsTracker().getNumMessages();
	}

}