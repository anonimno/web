package model.database;

import model.dao.Loggable;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class ConnectionManager extends Loggable {
	private final static String CONFIG_FILE = "./jdbc.properties";
	private final static String DBNAME = "ei102714fnv";
	private final static String DRIVER_NAME = "org.postgresql.Driver";
	private final static String URL = "jdbc:postgresql://db-aules.uji.es/" + DBNAME;
	private static Connection db = null;

	private static ConnectionErrors errors;

	static {
		initLog(ConnectionManager.class.getName());
	}

	public ConnectionManager() {
		errors = new ConnectionErrors(Log);
	}

	public static Connection getConnection() throws SQLException {
		if(db != null && !db.isClosed()) {
			return db;
		}

		try {
			Class.forName(DRIVER_NAME);
		} catch (ClassNotFoundException e) {
			Log.severe(ConnectionErrors.ERROR_DRIVER);
			e.printStackTrace();
		}

		ClassLoader loader = Thread.currentThread().getContextClassLoader();
		InputStream file = loader.getResourceAsStream(CONFIG_FILE);
		Properties props = new Properties();

		if(file == null) {
			Log.severe(ConnectionErrors.FILE_NOT_READABLE + " " + CONFIG_FILE);
		} else {
			try {
				props.load(file);
			} catch(IOException e) {
				Log.severe(ConnectionErrors.FILE_NOT_READABLE + " " + CONFIG_FILE);
				e.printStackTrace();
			}
		}

		db = DriverManager.getConnection(URL, props);

		if(db == null) {
			Log.severe(ConnectionErrors.ERROR_CREATE_CONNECTION);
		} else {
			Log.fine("Database connection successful");
		}

		return db;
	}

	public static Connection getLazyConnection() throws SQLException {
		Connection conn = getConnection();
		//conn.setAutoCommit(false);
		return conn;
	}

	public static ConnectionErrors getErrors() {
		if(errors == null) {
			errors = new ConnectionErrors(Log);
		}

		return errors;
	}

	public static void queryInMultipleDAO(boolean optionActivated) {
		ConnectionErrors.CLOSE_CONNECTION = !optionActivated;
	}
}
