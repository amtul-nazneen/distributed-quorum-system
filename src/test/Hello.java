package test;

import quorum.app.util.Utils;

public class Hello {
	public static void main(String[] args) {
		String a = "[2019-10-17 19:42:23.2]";
		String b = "[2019-10-17 19:42:23.201]";
		String c = "[2019-10-17 19:42:23.20]";
		System.out.println(a.length());
		System.out.println(b.length());
		System.out.println(c.length());
		int i = 0;
		while (i++ < 40) {
			// Timestamp tp=Utils.getTimestampForLog().get
			System.out.println(Utils.getTimestampForLog() + "hey!");
		}
	}

}
