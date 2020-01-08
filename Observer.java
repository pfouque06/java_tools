package javaTools;

import java.util.LinkedHashSet;

public interface Observer {

	// Interface implemented by Observer in order to receive an update request from Observed class
	// LinkedHashSet<String[]> pLHS is genric and can be used as suited
	// for instance :
	// - getOpts : String[] buffer = { type, key, valueName, action };
	//
	// idea : change terminology to transmitter / receiver ???
	//
	public void update(LinkedHashSet<String[]> pLHS);
	  
}
