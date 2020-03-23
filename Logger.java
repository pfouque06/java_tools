package javaTools;

import java.io.IOException;
import java.io.PrintStream;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Logger {

	// logger
	private String filename = ""; // or "logger.log"
	private PrintStream handler;

	public Logger() {
		this.init(""); // no logging !
	}

	public Logger(String handler) {
		this.init(handler);
	}

	void init(String handler) {

		try {
			switch (handler) {
			case "":
				this.filename = "";
				this.handler = null;
				break;
			case "syso":
			case "System.out":
				this.filename = "System.out";
				this.handler = new PrintStream(System.out);
				break;
			default:
				this.filename = handler;
				this.handler = new PrintStream(handler);
				break;
			}
		} catch (IOException e) {
			System.out.println("can't log on : \"" + this.filename + "\", switch to no logging");
			this.handler = null;
		} finally {
			this.logging("Logging ready ...");
		}
	}

	public void setHandler(String handler) {

		if (handler == null) return;
		// check if new handler is same as current
		if (handler.equals(this.filename)) {
			this.logging("Logging goes on with same handler -> " + this.filename + "...");
			return;
		}
		// close previous handler if not null
		if ( this.handler != null) this.close();
		// init new handler
		this.init(handler);
	}

	public void logging(String message) {

		if (this.handler == null)
			return;
		// get local dame and time
		DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd-HH:mm:ss.SSS");
		LocalDateTime now = LocalDateTime.now();
		String log = dtf.format(now) + " ";
		// prepare logging message
		if (message.isEmpty())
			message = "";
		log += message;
		// handle logging message
		this.handler.println(log);
	}
	
	public void close() {
		this.logging("Closing logging ...");
		if (this.handler != null)
			this.handler.close();
		return;
	}
}
