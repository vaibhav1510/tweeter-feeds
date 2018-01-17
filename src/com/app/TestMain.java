package com.app;

public class TestMain {

	public static void main(String[] args) throws Exception {

		PollData poll = new PollData();
		poll.start();

		CelebrityFinder finder = new CelebrityFinder();
		finder.start();

	}
}