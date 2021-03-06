package javaTools;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.LinkedHashSet;
import java.util.LinkedList;

/*
// GetOpts class to parse Command Lien arguments
// must be used with an option Table such as :

	static String[] optionArray = {
		"##### DO NOT FORGET FOLLOWING HEADER LINE !! #####",
		"TYPE:KEY:KEYWORD:VALUENAME:VALUETYPE:DETAIL:ACTION:",
		"F:h:help:usage:-:prints this help message:true:",
		"F:L:log:logging:boolean:set logging mode (to console/terminal):true:",
		"F:a:auto:autoMode:boolean:set auto mode with random roulette (default is OFF):true:",
		"F:c:color:colorMode:boolean:set color mode (default is color):true:",
		"F:m:mono:colorMode:boolean:set monocolor mode (default is color):false:",
		"V:d:deposit:deposit:int:set jetons for deposit required to start a game (default is 0):-:",
		"V:w:warning:jetonWarning:int:set warning spent before alerting per phase (default is 200):-:",
		"V:j:jeton:jetonMax:int:set maximum spent to quit game:-:",
		"V:p:phase:phaseMax:int:set maximum phase to quit game:-:",
		"V:t:tour:tourMax:int:set maximum total tours to quit game:-:",
		"V:g:gain:gainMax:int:set maximum total gain to quit game:-:",
		"V:b:bet:betMax:int:set maximum bets allowed per tour:-:",
		};

	public static boolean setOpts(LinkedHashSet<String[]> pList) {
		// Loop on each options of pList
		for (String[] fields : pList) {
			//System.out.println("fields="+fields.toString() );
			switch (fields[2]) {
			case "colorMode":
				colorMode = fields[3].equals("true");
				// System.out.println("colorMode=" + colorMode);
				break;
			case "autoMode":
				autoMode = fields[3].equals("true");
				// System.out.println("auto=" + auto);
				break;
			case "logging":
				logging = fields[3].equals("true");
				break;
			case "deposit":
				deposit += Integer.valueOf(fields[3]);
				// System.out.println("deposit=" + deposit);
				break;
			case "jetonWarning":
				warning = Integer.valueOf(fields[3]);
				// System.out.println("warning=" + warning);
				break;
			case "jetonMax":
				jetonLimite = Integer.valueOf(fields[3]);
				// System.out.println("jetonLimite=" + jetonLimite);
				break;
			case "phaseMax":
				phaseMax = Integer.valueOf(fields[3]);
				// System.out.println("phaseMax=" + phaseMax);
				break;
			case "tourMax":
				tourMax = Integer.valueOf(fields[3]);
				// System.out.println("tourMax=" + tourMax);
				break;
			case "gainMax":
				gainMax = Integer.valueOf(fields[3]);
				// System.out.println("gainMax=" + gainMax);
				break;
			case "betMax":
				betMax = Integer.valueOf(fields[3]);
				// System.out.println("betMax=" + betMax);
				break;
			default:
				System.err.println("Error: option > valuename unknown");
			case "usage":
				return false;
			}
		}
		if (warning == 0)
			warning = deposit / 2;
		return true;
	}

	public static String optsToString() {

		String buffer = "";

		buffer += " auto: " + (autoMode ? c_green() + "ON" : c_red() + "OFF") + c_reset();
		buffer += " mode: " + (colorMode ? c_green() + "color" : c_red() + "mono") + c_reset();
		buffer += " mode: " + (logging ? c_green() + "ON" : c_red() + "OFF") + c_reset();

		if (deposit > 0)
			buffer += " deposit: " + c_blue() + deposit + c_reset();
		if (warning > 0)
			buffer += " warning: " + c_blue() + warning + c_reset();
		if (jetonLimite > 0)
			buffer += " jetonLimite: " + c_blue() + jetonLimite + c_reset();
		if (betMax > 0)
			buffer += " betMax: " + c_blue() + betMax + c_reset();
		if (gainMax > 0)
			buffer += " gainMax: " + c_blue() + gainMax + c_reset();
		if (phaseMax > 0)
			buffer += " phaseMax: " + c_blue() + phaseMax + c_reset();
		if (tourMax > 0)
			buffer += " toursMax: " + c_blue() + tourMax + c_reset();
		return buffer;
	}

	public static void main(String[] args) {

		// initiate getOpts options and parse args :
		options = new GetOpts(optionArray, args);

		// set options according to args parsing
		if (!setOpts(options.getOptionList())) {
			System.out.println(options.getUsage()); // display usage is requested
			return;
		}
		logger.logging("options:" + optsToString());

		...
	}
*/

public class GetOpts {

	private boolean status;
	private String optionFilename = "getOptsTable.txt"; // default filename for getoptsTable definition
	//private String optionFilenameAlt = "src/_666_/getOptsTable.txt"; // default filename for getoptsTable definition
	private int indexType = -1, indexKey = -1, indexKeyword = -1, indexValue = -1, indexValueType = -1,
			indexDetail = -1, indexAction = -1;
	private LinkedHashSet<String[]> optionList = new LinkedHashSet<String[]>();
	private LinkedList<String[]> optionTable = new LinkedList<String[]>();

	// constructor
	public GetOpts(String pFilename, String[] pArgs) {
		optionFilename = pFilename;

		// build options Table, check indexes and parse options pArgs
		status = init(pArgs);
	}

	public GetOpts(String[] pOptions, String[] pArgs) {

		try {
			BufferedWriter writer;
			String workingDir = PackageTools.getRunningDir();
			File fileTable = new File(workingDir + "/" + optionFilename);
			fileTable.createNewFile();
			if (!fileTable.exists()) {
				System.err.println("file " + optionFilename + " can't be created");
				System.exit(1);
			}
			fileTable.deleteOnExit();

			writer = new BufferedWriter(new FileWriter(optionFilename));
			for (String line : pOptions) {
				writer.write(line);
				writer.newLine();
			}
			writer.close();

			// build options Table, check indexes and parse options pArgs
			status = init(pArgs);

		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private boolean init(String[] pArgs) {

		// build options Table
		if (!setOptionTable()) {
			System.err.println("getOpts Error : setOptionTable() can't set optionTable");
			return false;
		}
		// check Index validity
		if (!setIndex()) {
			System.err.println("getOpts Error : setIndex() can't find Header Line for index settings");
			return false;
		}
		// parse options pArgs
		if (!setOptionList(pArgs)) {
			System.err.println("getOpts Error : Parsing error, please retry or use -h, --help to get usage ...");
			return false;
		}
		return true;
	}

	public boolean isEnabled() {
		return status;
	}

	@SuppressWarnings("unused")
	private void setOptionsFilename(String pFilename) {
		optionFilename = pFilename;
	}

	// optionFilename mgt
	public String getOptionsFilename() {
		return optionFilename;
	}

	// optionTable mgt
	public boolean setOptionTable() {
		// System.out.println("setOptionDefs()-1.0");
		if (optionFilename.isEmpty())
			return false;

		// System.out.println("setOptionDefs()-1.1");
		try {
			String line;
			BufferedReader reader;
			String workingDir = PackageTools.getRunningDir();
			File fileTable = new File(workingDir + "/" + optionFilename);
			if (!fileTable.exists()) {
				System.err.println("le fichier " + optionFilename + " n'existe pas");
				// fileTable = new File(optionFilenameAlt);
				// if (!fileTable.exists()) {
				//	System.err.println("le fichier " + optionFilenameAlt + " n'existe pas");
				//	System.exit(1);
				// }
				System.exit(1);
			}
			// fileTable.getName()); // fileTable.getPath()); //
			// fileTable.getAbsolutePath());

			reader = new BufferedReader(new FileReader(optionFilename));
			while ((line = reader.readLine()) != null) {
				// exclude comment and keep header line : TYPE:KEY:KEYWORD:VALUE:DETAIL:ACTION
				if (!line.matches("^#.*")) {
					// split line ins String[]
					String[] fields = line.split(":");
					// for (String cell : fields) System.out.print("cell="+cell+ "|");
					// System.out.println();
					// add fields[] to optionTable
					optionTable.add(fields);
				}
			}
			reader.close();
		} catch (IOException e) {
			e.printStackTrace();
			return false;
		}
		// System.out.println("optionTable="+optionTable);
		return true;
	}

	public String optionTable_toString() {
		String result = "";
		for (String[] fields : optionTable) {
			// System.out.println("fields="+fields.toString() );
			for (String cell : fields)
				result += cell + ":";
			result += "\n";
		}
		return result;
	}

	// index mgt
	private boolean setIndex() {

		// get index of cell TYPE, KEY, KEYWORD, ACTION in optionTable[0]
		String[] header = optionTable.getFirst();
		for (int i = 0; i < header.length; i++) {
			switch (header[i]) {
			case "TYPE":
				indexType = i;
				break;
			case "KEY":
				indexKey = i;
				break;
			case "KEYWORD":
				indexKeyword = i;
				break;
			case "VALUENAME":
				indexValue = i;
				break;
			case "VALUETYPE":
				indexValueType = i;
				break;
			case "DETAIL":
				indexDetail = i;
				break;
			case "ACTION":
				indexAction = i;
				break;
			}
		}
		return checkIndex();
	}

	private boolean checkIndex() {
		// check all index
		if (indexType < 0 || indexKey < 0 || indexKeyword < 0 || indexValue < 0 || indexValueType < 0
				|| indexAction < 0)
			return false;
		return true;
	}

	// optionList mgt
	public boolean setOptionList(String[] pArgs) {

		// System.out.println("setOptionList-1.0");
		// get index of cell TYPE, KEY, KEYWORD, ACTION in optionTable[0] from class
		// local variables
		// reset optionList
		optionList.clear();

		// System.out.println("setOptionList-2.0");
		// check all index
		if (!setIndex()) {
			System.err.println("Error: index are missing in getOps parsing process");
			return false;
		}

		// System.out.println("setOptionList-3.0");
		// loop on args
		for (int i = 0; i < pArgs.length; i++) {
			boolean found = false;
			// System.out.println("i="+i);
			// loop on optionTable entries
			// check if options is key type
			if (pArgs[i].matches("^-[a-zA-Z]+$")) {
				int argLength = pArgs[i].length();
				for (int argID = 1; argID < argLength; argID++) {
					found = false;
					String keyID = pArgs[i].substring(argID, argID + 1);
					//System.out.println("setOptionList-3.1 : pArgs["+i+"]="+pArgs[i]+" length="+ argLength +" keyID="+ keyID + " argID=" + argID);

					for (String[] entry : optionTable) {
						String type = entry[indexType];
						String key = entry[indexKey];
						// String keyword = entry[indexKeyword];
						String valueName = entry[indexValue];
						// String valueType = entry[indexValueType];
						String action = entry[indexAction];
						//System.out.println("setOptionList-3.1 "+ type + " " + key + " " + valueName + " " + action );

						// check key against arg>keyId
						if (keyID.equals(key)) {
							// check flag type
							if (type.matches("F")) {
								//System.out.println("setOptionList-3.1.1 - found : "+ type + " " + key + " " + valueName + " " + action );
								String[] buffer = { type, key, valueName, action };
								optionList.add(buffer);
								found = true;
							}

							// check value type
							else if (type.matches("V")) {
								if (pArgs[i].length() > 2) {
									System.err.println("Error: flag -" + key
											+ " should be isolated from other flags unlike " + pArgs[i]);
									// usage(System.err);
									return false;
								}
								if (i == pArgs.length - 1) {
									System.err.println("Error: missing argument for " + pArgs[i]);
									// usage(System.err);
									return false;
								}
								String value = pArgs[i + 1];
								if (!value.matches("\\d+")) {
									System.err.println(
											"Error: value \"" + value + "\" for option -" + key + " must be a number");
									// usage(System.err);
									return false;
								}
								//System.out.println("setOptionList-3.1.2 - found : "+ type + " " + key + " " + valueName + " " + value );
								String[] buffer = { type, key, valueName, value };
								optionList.add(buffer);
								argID = pArgs[i++].length();
								found = true;
							}
						}
					}
					//System.out.println("argID="+argID);
					if (!found) {
						System.err.println("Error:" + /* " arg["+i+"] = "+pArgs[i] + " ->" + */" -" + keyID
								+ " is an unknown flag argument");
						// usage(System.err);
						return false;
					}
				}
			}

			// check if options is keyword type
			else if (pArgs[i].matches("^--[a-zA-Z]+$")) {
				for (String[] entry : optionTable) {
					String type = entry[indexType];
					String key = entry[indexKey];
					String keyword = entry[indexKeyword];
					String valueName = entry[indexValue];
					// String valueType = entry[indexValueType];
					String action = entry[indexAction];

					// check keyword against arg
					if (pArgs[i].matches("^--" + keyword + "$")) {
						// System.out.println("setOptionList-3.2");

						// check flag type
						if (type.matches("F")) {
							//System.out.println("setOptionList-3.2.1 - found : "+ type + " " + key + " " + keyword + " " + valueName + " " + action );
							String[] buffer = { type, key, valueName, action };
							optionList.add(buffer);
							found = true;
						}

						// check value type
						else if (type.matches("V")) {
							// System.out.println("setOptionList-3.2.2");
							if (i == pArgs.length - 1) {
								System.err.println("Error: missing argument for " + pArgs[i]);
								// usage(System.err);
								return false;
							}
							String value = pArgs[++i];
							if (!value.matches("\\d+")) {
								System.err.println(
										"Error: value \"" + value + "\" for option --" + keyword + " must be a number");
								// usage(System.err);
								return false;
							}
							//System.out.println("setOptionList-3.2.2 - found : "+ type + " " + key + " " + keyword + " " + valueName + " " + value );
							String[] buffer = { type, key, valueName, value };
							optionList.add(buffer);
							found = true;
						}
					}
				}
			}
			// System.out.println("i="+i);
			if (!found) {
				System.err.println("Error: " + pArgs[i] + " is an unknown argument");
				// usage(System.err);
				return false;
			}
		}

		// System.out.println(optionList_toString());
		// System.out.println("setOptionList-end");
		return true;
	}

	public String optionList_toString() {
		String result = "";
		for (String[] fields : optionList) {
			// System.out.println("fields="+fields.toString() );
			result += (result.isEmpty() ? "" : "\n");
			for (String cell : fields)
				result += cell + ":";
		}
		return result;
	}

	public LinkedHashSet<String[]> getOptionList() {
		return optionList;
	}

	// get program usage
	public String getUsage() {
		String usage = "", help = "";

		// check all index are validated
		if (!setIndex()) {
			System.err.println("Error: index are missing in getOps parsing process");
			return usage;
		}

		usage += "Usage: application [-<flags>] [--<keyword>] [[-<flag>|--<keyword>] <value>]\n";
		usage += "Options:\n";

		for (String[] fields : optionTable) {
			if (!fields[indexKey].equals("KEY")) {
				String line = "";
				if (fields[indexType].equals("F")) { // usage for flags
					line += String.format("\t-%s, --%-21s", fields[indexKey], fields[indexKeyword]);
				} else { // usage for value options
					line += String.format("\t-%s, --%-21s", fields[indexKey],
							fields[indexKeyword] + " <" + fields[indexValue] + ">");
				}
				line += "\t" + fields[indexDetail] + "\n";
				if (fields[indexKey].equals("h"))
					help = line;
				else
					usage += line;
			}
		}

		usage += "\n";
		usage += help;
		return usage;
	}

}
