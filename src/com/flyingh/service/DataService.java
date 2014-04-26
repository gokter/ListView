package com.flyingh.service;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class DataService {
	public static final List<String> getData(int offset, int maxSize) {
		try {
			Thread.sleep(TimeUnit.SECONDS.toMillis(1));
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		List<String> list = new ArrayList<>();
		for (int i = 0; i < maxSize; i++) {
			list.add("hello world!!!" + i);
		}
		return list;
	}
}
