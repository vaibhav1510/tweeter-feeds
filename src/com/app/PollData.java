package com.app;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.TimeUnit;

import org.json.JSONObject;

import com.twitter.hbc.core.endpoint.DefaultStreamingEndpoint;
import com.twitter.hbc.core.endpoint.StatusesFilterEndpoint;
import com.twitter.hbc.core.endpoint.StatusesSampleEndpoint;

public class PollData extends Thread {

	public void run() {
		while (true) {
			try {
				getData();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	private void getData() throws Exception {
		BlockingQueue<String> queue = new LinkedBlockingQueue<String>(10000);
		
		
		
		Client cli = new Client();
		cli.connectToClient(sampleStatusEndPoint(), queue);
//		cli.connectToClient(statusFilterEndPoint(), queue);
		
		for (int msgRead = 0; msgRead < 10; msgRead++) {
			if (cli.isDone()) {
				System.out.println("Client connection closed unexpectedly: " + cli.getMessage());
				break;
			}
			String msg = queue.poll(5, TimeUnit.SECONDS);

			if (msg == null) {
				System.out.println("Did not receive a message in 5 seconds");
			} else {
				try {
					System.out.println(msg);
					JSONObject mainJson = new JSONObject(msg);
					JSONObject json = mainJson.getJSONObject("user");
					User u = new User();
					u.setUserId(json.getLong("id"));
					u.setName(json.getString("name"));
					u.setScreenName(json.getString("screen_name"));

					UserMessage um = new UserMessage(u);
					um.setMessage(mainJson.getString("text"));
					um.setCreatedAt(toTimestamp(mainJson.getString("created_at")));
					DataStore.inst().addUserMessage(u, um);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
		cli.disconnetClient();
		System.out.printf("The client read %d messages!\n", cli.msgCount());

	}

	public static Timestamp toTimestamp(String strDate) throws Exception {
		// SimpleDateFormat sdf = new SimpleDateFormat("Sat Dec 16 07:28:56 +0000
		// 2017");
		SimpleDateFormat sdf = new SimpleDateFormat("EEE MMM dd HH:mm:ss +0000 yyyy");
		sdf.setLenient(false);
		Date baseDate = sdf.parse(strDate);
		Calendar c = Calendar.getInstance();
		c.setTimeInMillis(baseDate.getTime());
		return new Timestamp(c.getTimeInMillis());
	}
	
	private DefaultStreamingEndpoint sampleStatusEndPoint(){
		StatusesSampleEndpoint endpoint = new StatusesSampleEndpoint();
		endpoint.stallWarnings(false);
		return endpoint;
	}
	
	private DefaultStreamingEndpoint statusFilterEndPoint(){
		StatusesFilterEndpoint endpoint = new StatusesFilterEndpoint();
		endpoint.stallWarnings(false);
		endpoint.addQueryParameter("locations", "20.593684, 78.962880");
		endpoint.addQueryParameter("locations", "20.593684, 78.962880");
		return endpoint;
	}
}
