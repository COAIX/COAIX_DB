package com.coaix.databaseprototype.tools;

import java.io.*;

public class KeyInputTools {
	static InputStreamReader isr = new InputStreamReader(System.in);
	static BufferedReader br = new BufferedReader(isr);

	public static int readInt() {
		int i = 0;
		try {
			i = Integer.parseInt(readString());
		} catch (Exception e) {
			System.out.println(e);
		}
		return i;
	}

	public static float readFloat() {
		float f = 0.0f;
		try {
			f = Float.parseFloat(readString());
		} catch (Exception e) {
			System.out.println(e);
		}
		return f;
	}

	public static double readDouble() {
		double d = 0.0;
		try {
			d = Double.parseDouble(readString());
		} catch (Exception e) {
			System.out.println(e);
		}
		return d;
	}

	public static String readString() {
		String s = "";
		try {
			s = br.readLine();
			if (s.length() == 0) {
				s = br.readLine();
			}
		} catch (Exception e) {
			System.out.println(e);
		}
		return s;
	}

	public static boolean readBoolean() {
		if (readString().toUpperCase().equals("TRUE")) {
			return true;
		} else {
			return false;
		}
	}

}
