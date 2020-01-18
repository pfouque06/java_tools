package javaTools;

import java.util.Scanner;

public class ScanTools {

	static Scanner scan = new Scanner(System.in);
	
	public static int scanInt(String pLabel) {
		//Scanner scan = new Scanner(System.in);
		String buffer = "";
		int result = 0;
		do {
			System.out.print(pLabel);
			buffer = scan.nextLine();
			if (buffer.matches("-?\\d+")) { // wait int
				result = Integer.valueOf(buffer);
				break;
			}
			buffer = "";
			System.out.println("\tvaleur invalide, veuillez entrer un entier");
		} while (buffer.isEmpty());

		// scan.close();
		return result;
	}

	public static int scanIntRange(String pLabel, int pMin, int pMax) {
		//Scanner scan = new Scanner(System.in);
		String buffer = "";
		int result = 0;
		do {
			System.out.print(pLabel);
			buffer = scan.nextLine();
			// buffer.matches("(^0?[1-9]$)|(^1[0-2]$)|^$"))); // wait int from 1 to 12 or
			// empty
			if (buffer.matches("-?\\d+")) { // wait int
				result = Integer.valueOf(buffer);
				if (result >= pMin && result <= pMax)
					break;
			}
			buffer = "";
			System.out.println("\tvaleur invalide, veuillez entrer un entier de " + pMin + " à " + pMax);

		} while (buffer.isEmpty());

		// scan.close();
		return result;
	}

	public static double scanDouble(String pLabel) {
		//Scanner scan = new Scanner(System.in);
		String buffer = "";
		double result = 0;
		do {
			System.out.print(pLabel);
			buffer = scan.nextLine();
			if (buffer.matches("-?\\d+[.]?\\d*")) { // wait double
				result = Double.valueOf(buffer);
				break;
			}
			buffer = "";
			System.out.println("\tvaleur invalide, veuillez entrer un double");
		} while (buffer.isEmpty());

		// scan.close();
		return result;
	}

	public static String scanMatchedBuffer(String pLabel, String pMatch) {

		//Scanner scan = new Scanner(System.in);
		String buffer = "";
		do {
			System.out.print(pLabel);
			buffer = scan.nextLine();
			if (buffer.matches(pMatch)) {
				// . : any character
				// .? : zero or one any character
				// .* : zero or any character
				// .+ : one or any character
				// [a-zA-Z] : character in the range ..
				// [a-zA-Z]? : zero or one character in the range ..
				// [a-zA-Z]* : zero or more character in the range ..
				// [a-zA-Z]+ : one more character in the range ..
				// \s : any whitespace char
				// \S : any non-whitespace char
				// \d : any digit char
				// \D : any non-digit char
				// [ON]|^$ : O, N ou empty
				// -?\\d* : any integer
				// [1-5]|^$ : int from 1 to 5 or empty
				// (^0?[1-9]$)|(^1[0-2]$)|^$ : int from 1 to 12 or empty
				// -?\\d*[.]?\\d* : any double
				return buffer;
			}
			System.out.println("\tvaleur invalide");
			buffer = "";

		} while (buffer.isEmpty());

		// scan.close();
		return buffer;
	}
	
	public static void close() {
		scan.close();
	}
	
}
