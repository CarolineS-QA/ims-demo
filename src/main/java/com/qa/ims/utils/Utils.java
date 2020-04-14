package com.qa.ims.utils;

import java.util.Scanner;

public class Utils {

	private Utils() {
		// empty constructor
	}

	public static String getInput() {
		@SuppressWarnings("resource")
		Scanner scanner = new Scanner(System.in);
		return scanner.nextLine();
	}

}
