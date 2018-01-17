package com.app;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.google.common.collect.ImmutableList;

public class DataStore {

	private Map<User, List<UserMessage>> pool = new HashMap<>();

	private static DataStore inst = new DataStore();

	private DataStore() {
	}

	public static DataStore inst() {
		if (inst == null) {
			inst = new DataStore();
		}
		return inst;
	}

	public void addUserMessage(User u, UserMessage um) {
		if (pool.containsKey(u)) {
			pool.get(u).add(um);
		} else {
			pool.put(u, ImmutableList.of(um));
		}
	}

	public Map<User, List<UserMessage>> pool() {
		return pool;
	}

}
