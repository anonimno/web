package database;

import java.sql.Connection;
import java.sql.ResultSet;
import java.util.logging.Logger;
import java.sql.SQLException;
import java.sql.Statement;

public class ConnectionErrors {
	public final static String FILE_NOT_READABLE = "Problem reading config file";
	public final static String ERROR_DRIVER = "Error JDBC driver not found";
	public final static String ERROR_CREATE_CONNECTION = "Error JDBC connection could not be created, failed connection";
	public final static String ERROR_CLOSE_CONNECTION = "Error JDBC connection could not be closed";
	public final static String ERROR_EXECUTE_PREPARED_STATEMENT = "Error JDBC prepared statement could not be executed";
	public final static String ERROR_CLOSE_PREPARED_STATEMENT = "Error JDBC prepared statement could not be closed";
	public final static String ERROR_CLOSE_RESULT = "Error JDBC result set could not be closed";

	protected static boolean CLOSE_CONNECTION = true;

	private static Logger log;

	public ConnectionErrors(Logger l) {
		log = l;
	}

	public void dealWithStatementCreation(Exception e) {
		CLOSE_CONNECTION = true;
		log.severe(ConnectionErrors.ERROR_EXECUTE_PREPARED_STATEMENT);
		e.printStackTrace();
	}

	public void dealWithStatementAndResultExecution(Statement stmt, ResultSet rs, Connection conn) {
		if (rs != null) {
			try {
				rs.close();
			} catch (SQLException e) {
				CLOSE_CONNECTION = true;
				log.warning(ERROR_CLOSE_RESULT);
				e.printStackTrace();
			}
		}

		dealWithStatementExecution(stmt, conn);
	}

	public void dealWithStatementExecution(Statement stmt, Connection conn) {
		if (stmt != null) {
			try {
				stmt.close();
			} catch (SQLException e) {
				CLOSE_CONNECTION = true;
				log.warning(ERROR_CLOSE_PREPARED_STATEMENT);
				e.printStackTrace();
			}
		}

		closeConnection(conn);
	}

	public void closeConnection(Connection conn) {
		if(CLOSE_CONNECTION && conn != null) {
			try {
				conn.close();
			} catch (SQLException e) {
				log.warning(ERROR_CLOSE_CONNECTION);
				e.printStackTrace();
			}
		}
	}
}
