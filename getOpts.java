package java_tools;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintStream;
import java.util.LinkedHashSet;
import java.util.LinkedList;

public class getOpts {

	//String optionFilename = "src/_666_/getOptsTable.txt"; // default filename for getoptsTable definition
	String optionFilename = "getOptsTable.txt"; // default filename for getoptsTable definition
	LinkedHashSet<String[]> optionList = new LinkedHashSet<String[]>();
	LinkedList<String[]> optionTable = new LinkedList<String[]>();

	//	constructor with file with argOptTable
	//	split into LinkedHashSet<String> optTable = new LinkedHashSet<String>();
	//	BufferedReader reader = new BufferedReader(new FileReader(filename));
	//	while(String line = reader.readLine()) {
	//		if (line.indexOf("in") >= 0) {
	//			String[] fields = line.split(":");
	//			System.out.println(fields[2]);
	//		}
	//	}
	//	parse args :
	//		- loop into flags by key
	//		- loop into flags by keywords
	//		- loop into values
	//	fill LinkedHashSet<String> options = new LinkedHashSet<String>();

	//	display usage
	//	display options

	// constructor
	public getOpts() {
		//System.out.println("getOpts()-1.0");
		if (! setOptionTable()) System.out.println("getOpts Error");
		//System.out.println("getOpts()-1.1");
	}

	public getOpts(String pFilename) {
		optionFilename = pFilename;
		if (! setOptionTable()) System.out.println("getOpts Error");
	}

	void setOptionsFilename(String pFilename) {
		optionFilename = pFilename;
	}

	public String getOptionsFilename() {
		return optionFilename;
	}



	// optionTable mgt
	boolean setOptionTable() {
		//System.out.println("setOptionDefs()-1.0");
		if (optionFilename.isEmpty()) return false;

		//System.out.println("setOptionDefs()-1.1");
		String line;
		BufferedReader reader;
		try {
			File fileTable=new File(optionFilename);
			if (!fileTable.exists()) { 
				System.out.println("le fichier "+optionFilename+" n'existe pas");
				System.exit(1);
			}
			System.out.println("> Nom du fichier    : "+fileTable.getName());
			System.out.println("> Chemin du fichier : "+fileTable.getPath());
			System.out.println("> Chemin absolu     : "+fileTable.getAbsolutePath());

			reader = new BufferedReader(new FileReader(optionFilename));
			while ( (line = reader.readLine()) != null ) {
				//System.out.println("line="+line);
				// exclude comment and keep header line : TYPE:KEY:KEYWORD:VALUE:DETAIL:ACTION
				if ( ! line.matches("^#.*") ) { 
					//split line ins String[]
					String[] fields = line.split(":");
					//for (String cell : fields) System.out.print("cell="+cell+ "|"); System.out.println();
					// add fields[] to optionTable
					optionTable.add(fields);
				}
			}
			reader.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		}
		//System.out.println("optionTable="+optionTable);
		return true;
	}

	public String optionTable_toString() {
		String result= "";
		for (String[] fields : optionTable) {
			//System.out.println("fields="+fields.toString() );
			for (String cell : fields)
				result += cell+":";
			result += "\n";
		}
		return result;
	}



	// optionList mgt
	public boolean setOptionList(String[] pArgs) {

		//LinkedHashSet<String[]> tempList = new LinkedHashSet<String[]>();

		// reset optionList
		optionList.clear();

		//System.out.println("setOptionList-1.0");
		// get index of cell TYPE, KEY, KEYWORD, ACTION in optionTable[0]
		int indexType=-1, indexKey=-1, indexKeyword=-1, indexValue=-1, indexValueType=-1, indexAction=-1;
		String[] header = optionTable.getFirst();
		for (int i = 0; i < header.length; i++) {
			switch ( header[i]) {
			case "TYPE" :
				indexType = i;
				break;
			case "KEY" :
				indexKey = i;
				break;
			case "KEYWORD" :
				indexKeyword = i;
				break;
			case "VALUENAME" :
				indexValue = i;
				break;
			case "VALUETYPE" :
				indexValueType = i;
				break;
			case "ACTION" :
				indexAction = i;
				break;
			}
		}

		//System.out.println("setOptionList-2.0");
		// check all index
		if ( indexType<0 || indexKey<0 || indexKeyword<0 || indexValue<0 || indexValueType<0 || indexAction<0) {
			System.err.println("Error: index are missing in getOps parsing process");
			return false;
		}

		//System.out.println("setOptionList-3.0");
		// loop on args
		for (int i = 0; i < pArgs.length; i++) {
			// loop on optionTable entries
			for ( String[] entry : optionTable ) {
				String type = entry[indexType];
				String key = entry[indexKey];
				String keyword = entry[indexKeyword];
				String valueName = entry[indexValue];
				String valueType = entry[indexValueType];
				String action = entry[indexAction];
				System.out.println("setOptionList-3.1 "+ type + " " + key + " " + keyword + " " + valueName + " " + valueType + " " + action );

				// check if options is key type
				if ( pArgs[i].matches("^-[a-z]*$")) {
					//System.out.println("setOptionList-3.2 " + type);
					// check flag type
					if ( type.matches("F") ) {
						//System.out.println("setOptionList-3.3");
						if ( pArgs[i].matches("^-.*"+key+".*$")) {
							System.out.println("setOptionList-3.3.1 - found");
							String[] buffer = {"F", key, valueName, action};
							optionList.add(buffer);
						}
					}
					// check value type
					else if ( type.matches("V") ) {
						//System.out.println("setOptionList-3.4");
						if ( pArgs[i].matches("^-"+key+"$")) {
							System.out.println("setOptionList-3.4.1 - found");
							if (i == pArgs.length - 1) {
								System.err.println("Error: missing argument for " + pArgs[i]);
								//usage(System.err);
								return false;
							}
							String value = pArgs[++i];
							if ( ! value.matches("\\d+")) {
								System.err.println("Error: argument " + value + " must be a number");
								//usage(System.err);
								return false;
							}
							System.out.println("setOptionList-3.4.2");
							String[] buffer = {"F", key, valueName, value}; 
							optionList.add(buffer);
						}
					}
				}

				// check if options is keyword type
				else if ( pArgs[i].matches("^--[a-z]*$")) {
					if ( pArgs[i].matches("^--"+keyword+"$")) {
						//System.out.println("setOptionList-3.5");

						// check flag type
						if ( type.matches("F") ) {
							System.out.println("setOptionList-3.5.1 - found");
							String[] buffer = {"F", key, valueName, action};
							optionList.add(buffer);
						}

						// check value type
						else if ( type.matches("V") ) {
							System.out.println("setOptionList-3.5.2 - found");
							if (i == pArgs.length - 1) {
								System.err.println("Error: missing argument for " + pArgs[i]);
								//usage(System.err);
								return false;
							}
							String value = pArgs[++i];
							if ( ! value.matches("\\d+")) {
								System.err.println("Error: argument " + value + " must be a number");
								//usage(System.err);
								return false;
							}
							String[] buffer = {"F", key, valueName, value}; 
							optionList.add(buffer);
						}
					}
				}
			}
		}

		System.out.println("setOptionList-end");
		return true;
	}

	public String optionList_toString() {
		String result= "";
		for (String[] fields : optionList) {
			//System.out.println("fields="+fields.toString() );
			for (String cell : fields)
				result += cell+":";
			result += "\n";
		}
		return result;
	}

	public LinkedHashSet<String[]> getOptionList() {
		return optionList;
	}

	public static boolean getOpt(String[] pArgs) {
		String buffer="";
		//File file = null;

		for (int i = 0; i < pArgs.length; i++) {
			if ("--help".equals(pArgs[i]) || "-h".equals(pArgs[i])) {
				getUsage(System.out); // use STDOUT when help is requested
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

	private static void getUsage(PrintStream ps) {
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
