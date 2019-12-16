package java_tools;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintStream;
import java.nio.charset.StandardCharsets;
import java.util.LinkedHashSet;
import java.util.LinkedList;

public class getOpts {

	String optionFilename = "getOptsTable.txt"; // default filename for getoptsTable definition
	String optionFilenameAlt = "src/_666_/getOptsTable.txt"; // default filename for getoptsTable definition

	int indexType=-1, indexKey=-1, indexKeyword=-1, indexValue=-1, indexValueType=-1, indexDetail=-1, indexAction=-1;
	LinkedHashSet<String[]> optionList = new LinkedHashSet<String[]>();

	LinkedList<String[]> optionTable = new LinkedList<String[]>();

	// constructor
	public getOpts() {
		if (setOptionTable())
			setIndex();
		else
			System.err.println("getOpts Error");
	}

	public getOpts(String pFilename) {
		optionFilename = pFilename;
		if (setOptionTable())
			setIndex();
		else
			System.err.println("getOpts Error");
	}

	public getOpts(String[] pOptions) {
		BufferedWriter writer;
		String workingDir = new File("").getAbsolutePath();
		try {
			File fileTable=new File(workingDir + "/" + optionFilename);
			fileTable.createNewFile();
			if (!fileTable.exists()) {
				System.err.println("file "+optionFilename+" can't be created");
				System.exit(1);
			}
			
			writer = new BufferedWriter(new FileWriter(optionFilename, StandardCharsets.UTF_8));
			for (String line : pOptions) {
				writer.write(line);
				writer.newLine();
			}
			writer.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			//writer.close();
		}
		if (setOptionTable())
			setIndex();
		else
			System.err.println("getOpts Error");
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
			String workingDir = new File("").getAbsolutePath();
			//File projectDir = new File(getOpts.class.getProtectionDomain()
			// .getCodeSource() // .getLocation() // .getPath());
			// projectDir.getName()); // projectDir.getPath()); // projectDir.getAbsolutePath());
			File fileTable=new File(workingDir + "/" + optionFilename);
			if (!fileTable.exists()) {
				System.err.println("le fichier "+optionFilename+" n'existe pas");
				fileTable=new File(optionFilenameAlt);
				if (!fileTable.exists()) {
					System.err.println("le fichier "+optionFilenameAlt+" n'existe pas");
					System.exit(1);
				}
			}
			// fileTable.getName()); // fileTable.getPath()); // fileTable.getAbsolutePath());

			reader = new BufferedReader(new FileReader(optionFilename));
			while ( (line = reader.readLine()) != null ) {
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

	// index mgt
	void setIndex() {

		// get index of cell TYPE, KEY, KEYWORD, ACTION in optionTable[0]
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
			case "DETAIL" :
				indexDetail = i;
				break;
			case "ACTION" :
				indexAction = i;
				break;
			}
		}
	}

	// optionList mgt
	public boolean setOptionList(String[] pArgs) {

		//System.out.println("setOptionList-1.0");
		// get index of cell TYPE, KEY, KEYWORD, ACTION in optionTable[0] from class local variables
		// reset optionList
		optionList.clear();


		//System.out.println("setOptionList-2.0");
		// check all index
		if ( indexType<0 || indexKey<0 || indexKeyword<0 || indexValue<0 || indexValueType<0 || indexAction<0) {
			System.err.println("Error: index are missing in getOps parsing process");
			return false;
		}

		//System.out.println("setOptionList-3.0");
		// loop on args
		for (int i = 0; i < pArgs.length; i++) {
			boolean found= false;
			//System.out.println("i="+i);
			// loop on optionTable entries
			// check if options is key type
			if ( pArgs[i].matches("^-[a-z]+$")) {
				int argLength = pArgs[i].length();
				for (int argID = 1; argID < argLength; argID++) {
					found = false;
					String keyID = pArgs[i].substring(argID,argID+1);
					//System.out.println("setOptionList-3.1 : pArgs["+i+"]="+pArgs[i]+" length="+ argLength +" keyID="+ keyID + " argID=" + argID);

					for ( String[] entry : optionTable ) {
						String type = entry[indexType];
						String key = entry[indexKey];
						String keyword = entry[indexKeyword];
						String valueName = entry[indexValue];
						String valueType = entry[indexValueType];
						String action = entry[indexAction];
						//System.out.println("setOptionList-3.1 "+ type + " " + key + " " + keyword + " " + valueName + " " + valueType + " " + action );

						// check key against arg>keyId
						if ( keyID.equals(key)) {
							// check flag type
							if ( type.matches("F") ) {
								//System.out.println("setOptionList-3.1.1 - found : "+ type + " " + key + " " + keyword + " " + valueName + " " + valueType + " " + action );
								String[] buffer = {type, key, valueName, action};
								optionList.add(buffer);
								found = true;
							}

							// check value type
							else if ( type.matches("V") ) {
								if (pArgs[i].length() > 2 ) {
									System.err.println("Error: flag -"+key+" should be isolated from other flags unlike " + pArgs[i]);
									//usage(System.err);
									return false;
								}
								if (i == pArgs.length - 1) {
									System.err.println("Error: missing argument for " + pArgs[i]);
									//usage(System.err);
									return false;
								}
								String value = pArgs[i+1];
								if ( ! value.matches("\\d+")) {
									System.err.println("Error: value \"" + value + "\" for option -" + key + " must be a number");
									//usage(System.err);
									return false;
								}
								//System.out.println("setOptionList-3.1.2 - found : "+ type + " " + key + " " + keyword + " " + valueName + " " + valueType + " " + value );
								String[] buffer = {type, key, valueName, value}; 
								optionList.add(buffer);
								argID = pArgs[i++].length();
								found = true;
							}
						}
					}
					//System.out.println("argID="+argID);
					if ( ! found ) {
						System.err.println("Error:" + /*" arg["+i+"] = "+pArgs[i] + " ->" +*/" -" + keyID + " is an unknown flag argument");
						//usage(System.err);
						return false;
					}
				}
			}

			// check if options is keyword type
			else if ( pArgs[i].matches("^--[a-z]+$")) {
				for ( String[] entry : optionTable ) {
					String type = entry[indexType];
					String key = entry[indexKey];
					String keyword = entry[indexKeyword];
					String valueName = entry[indexValue];
					String valueType = entry[indexValueType];
					String action = entry[indexAction];

					// check keyword against arg
					if ( pArgs[i].matches("^--"+keyword+"$")) {
						//System.out.println("setOptionList-3.2");

						// check flag type
						if ( type.matches("F") ) {
							//System.out.println("setOptionList-3.2.1 - found : "+ type + " " + key + " " + keyword + " " + valueName + " " + valueType + " " + action );
							String[] buffer = {type, key, valueName, action};
							optionList.add(buffer);
							found = true;
						}

						// check value type
						else if ( type.matches("V") ) {
							//System.out.println("setOptionList-3.2.2");
							if (i == pArgs.length - 1) {
								System.err.println("Error: missing argument for " + pArgs[i]);
								//usage(System.err);
								return false;
							}
							String value = pArgs[++i];
							if ( ! value.matches("\\d+")) {
								System.err.println("Error: value \"" + value + "\" for option --" + keyword + " must be a number");
								//usage(System.err);
								return false;
							}
							//System.out.println("setOptionList-3.2.2 - found : "+ type + " " + key + " " + keyword + " " + valueName + " " + valueType + " " + value );
							String[] buffer = {type, key, valueName, value}; 
							optionList.add(buffer);
							found = true;
						}
					}
				}
			}
			//System.out.println("i="+i);
			if ( ! found ) {
				System.err.println("Error: "  + pArgs[i] + " is an unknown argument");
				//usage(System.err);
				return false;
			}
		}

		//System.out.println("setOptionList-end");
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

	// get program usage
	public void getUsage(PrintStream ps) {
		String usage= "", help = "";

		ps.println();
		ps.println("Usage: application [-<flags>] [--<keyword>] [[-<flag>|--<keyword>] <value>]");
		ps.println("Options:");

		for (String[] fields : optionTable) {
			if ( ! fields[indexKey].equals("KEY")) {
				usage =  String.format("\t-%s, --%-21s", fields[indexKey], fields[indexKeyword] + " <"+fields[indexValue]+">");
				usage += "\t"+ fields[indexDetail];

				if (fields[indexKey].equals("h"))
					help = usage;
				else
					ps.println(usage);			
			}
		}

		ps.println();
		ps.println(help);
	}

}
