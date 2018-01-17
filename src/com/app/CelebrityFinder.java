package com.app;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class CelebrityFinder extends Thread {

	private static final int TOP_COUNT = 10;

	@Override
	public void run() {
		while (true) {
			try {
				for (User u : find(TOP_COUNT)) {
					System.out.println("++++++>: " + u.print());
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

	}

	private List<User> find(int count) {
		DataStore ds = DataStore.inst();

		Map<User, Integer> uVsCount = new HashMap<>();
		for (User u : ds.pool().keySet()) {
			if (uVsCount.containsKey(u)) {
				uVsCount.put(u, uVsCount.get(u) + 1);
			} else {
				uVsCount.put(u, 1);
			}
		}
		LinkedList<Integer> ll = new LinkedList<Integer>(uVsCount.values());
		java.util.Collections.sort(ll);

		List<Integer> top = new ArrayList<>();
		for (Iterator<Integer> itr = ll.descendingIterator(); itr.hasNext();) {
			if (count > 0) {
				top.add(itr.next());
			}
			count--;
		}

		List<User> toRet = new ArrayList<>();
		for (User u : uVsCount.keySet()) {
			if (top.contains(uVsCount.get(u))) {
				toRet.add(u);
			}
		}
		return toRet;
	}
}