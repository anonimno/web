package dao;

import java.util.logging.Logger;

public abstract class Loggable {
	protected static Logger Log = null;

	protected static void initLog(String name) {
		if(Log == null) {
			Log = Logger.getLogger(name);
		}
	}
}
