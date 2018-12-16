package com.example.webflux.pullpush;

import com.google.common.collect.Lists;
import io.reactivex.Observable;

import java.util.Iterator;
import java.util.List;

public final class PushVSPull {

	public static void main(final String[] args) {
		new PushVSPull().run();
	}

	private static void pullExample() {
		final List<String> list = Lists.newArrayList("Java", "C", "C++", "PHP", "Go");

		final Iterator<String> iterator = list.iterator();

		while (iterator.hasNext()) {
			System.out.println(iterator.next());
		}
	}

	private static void pushExample() {
		final List<String> list = Lists.newArrayList("Java", "C", "C++", "PHP", "Go");

		final Observable<String> observable = Observable.fromIterable(list);

		observable.subscribe(
				System.out::println,
				System.out::println,
				() -> System.out.println("We are done!")
		);
	}

	public void run() {
		pullExample();
		pushExample();
	}
}