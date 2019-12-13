package java_tools;

import java.io.PrintStream;

public class argOpts {

	public static boolean argOpt(String[] pArgs) {
		String buffer="";
		//File file = null;

		for (int i = 0; i < pArgs.length; i++) {
			if ("--help".equals(pArgs[i]) || "-h".equals(pArgs[i])) {
				usage(System.out); // use STDOUT when help is requested
				return false;
			}

			// set colorMode flag
			else if ("--mono".equals(pArgs[i]) || "-m".equals(pArgs[i])) {
//				colorMode = false; // toggle
//				System.out.println("colorMode="+colorMode);
			}

			// set betMax
			else if ("--bet".equals(pArgs[i]) || "-b".equals(pArgs[i])) {
				if (i == pArgs.length - 1) {
					System.err.println("Error: missing argument for " + pArgs[i]);
					//usage(System.err);
					return false;
				}
				buffer=pArgs[++i];
				if ( ! buffer.matches("\\d+")) {
					System.err.println("Error: argument for " + buffer + " must be a number");
					//usage(System.err);
					return false;
				}
//				betMax=Integer.valueOf(buffer);
//				System.out.println("betMax="+betMax);
			}

			// set jetonMax
			else if ("--jetons".equals(pArgs[i]) || "-j".equals(pArgs[i])) {
				if (i == pArgs.length - 1) {
					System.err.println("Error: missing argument for " + pArgs[i]);
					//usage(System.err);
					return false;
				}
				buffer=pArgs[++i];
				if ( ! buffer.matches("\\d+")) {
					System.err.println("Error: argument for " + buffer + " must be a number");
					//usage(System.err);
					return false;
				}
//				jetonLimite=Integer.valueOf(buffer);
//				System.out.println("jetonMax="+jetonLimite);
			}

			// set gainMax
			else if ("--gain".equals(pArgs[i]) || "-g".equals(pArgs[i])) {
				if (i == pArgs.length - 1) {
					System.err.println("Error: missing argument for " + pArgs[i]);
					//usage(System.err);
					return false;
				}
				buffer=pArgs[++i];
				if ( ! buffer.matches("\\d+")) {
					System.err.println("Error: argument for " + buffer + " must be a number");
					//usage(System.err);
					return false;
				}
//				gainMax=Integer.valueOf(buffer);
//				System.out.println("gainMax="+gainMax);
			}

			// set phaseMax
			else if ("--phase".equals(pArgs[i]) || "-p".equals(pArgs[i])) {
				if (i == pArgs.length - 1) {
					System.err.println("Error: missing argument for " + pArgs[i]);
					//usage(System.err);
					return false;
				}
				buffer=pArgs[++i];
				if ( ! buffer.matches("\\d+")) {
					System.err.println("Error: argument for " + buffer + " must be a number");
					//usage(System.err);
					return false;
				}
//				phaseMax=Integer.valueOf(buffer);
//				System.out.println("phaseMax="+phaseMax);
			}

			// set toursMax
			else if ("--tours".equals(pArgs[i]) || "-t".equals(pArgs[i])) {
				if (i == pArgs.length - 1) {
					System.err.println("Error: missing argument for " + pArgs[i]);
					//usage(System.err);
					return false;
				}
				buffer=pArgs[++i];
				if ( ! buffer.matches("\\d+")) {
					System.err.println("Error: argument for " + buffer + " must be a number");
					//usage(System.err);
					return false;
				}
//				toursMax=Integer.valueOf(buffer);
//				System.out.println("toursMax="+toursMax);
			}

			//			else if ("--file".equals(pArgs[i]) || "-f".equals(pArgs[i])) {
			//				if (i == pArgs.length - 1) {
			//					System.err.println("Error: missing argument for " + pArgs[i]);
			//					usage(System.err);
			//					return;
			//				}
			//				file = new File(pArgs[++i]);
			//			}
		}
		return true;
	}

	private static void usage(PrintStream ps) {
		ps.println("Usage: myapp [-x|--flag] --count=NUM --file=FILE");
		ps.println("Options:");
		ps.println("	-m, --mono				set monocolor mode (color mode par default");
		ps.println("	-a, --auto				set auto mode with random roulette");
		ps.println("	-b, --bet <betMax>		set maximum bets allowed per tour");
		ps.println("	-j, --jetons <jetonMax>	set maximum jeton before alerting per phase");
		ps.println("	-g, --gain <gainMax>	set maximum total gain before quit game");
		ps.println("	-p, --phase <phaseMax>	set maximum phase before quit game");
		ps.println("	-t, --tours <toursMax>	set maximum total tours before quit game");
		ps.println("	-h, --help				Prints this help message and exits");
	}


	
}
