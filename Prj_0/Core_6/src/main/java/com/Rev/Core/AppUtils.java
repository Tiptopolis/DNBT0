package com.Rev.Core;

import java.io.IOException;

public class AppUtils {


	
	
	public static void Log() {
		System.out.println("");
	}

	public static void Log(String l) {
		System.out.println(l);
	}

	public static void Log(Object o) {
		if (o == null)
			System.out.println(o);
		else
			System.out.println(o.toString());
	}

	public static void Page() {
		for (int i = 0; i < 8; i++) {
			Log();
		}

	}

	public static void Clear() {
		Page();
		Page();

	}
}
