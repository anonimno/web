package model.dao;

import model.database.ConnectionManager;
import model.entities.Day;
import model.entities.HourRange;
import org.joda.time.LocalTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedHashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

public class HourRangeDAO extends DAO<HourRange> {
    private final String addHourRange = "INSERT into HOURRANGE(idActivity, startTime, endTime, days) values(?, ?, ?, ?)";
    private final String deleteHourRange = "DELETE from HOURRANGE where id=?";
    private final String selectAllHourRanges = "SELECT * from HOURRANGE";
    private final String selectHourRange = "SELECT * from HOURRANGE where id = ?";
    private final String updateHourRange = "UPDATE HOURRANGE SET  startTime = ?, endTime = ?, days = ? where id = ?";
	private final String selectHourRangeByActivity = "SELECT * FROM HOURRANGE WHERE idActivity = ?";
	private final String multipleTagsInsert = "INSERT INTO HOURRANGE( idActivity, startTime, endTime, days ) VALUES ";

    public HourRangeDAO() {
        super();
        initLog(HourRangeDAO.class.getName());
    }

    public void add(HourRange toBeAdded) {
        PreparedStatement stmt = null;
	    Connection connection = null;

        try {
	        connection = ConnectionManager.getLazyConnection();
            stmt = ConnectionManager.getLazyConnection().prepareStatement(addHourRange);
	        stmt.setLong(1, toBeAdded.getIdActivity());

	        stmt.setTime(2, new java.sql.Time(toBeAdded.getStartTime().getMillisOfDay()));
	        stmt.setTime(3, new java.sql.Time(toBeAdded.getEndTime().getMillisOfDay()));

            StringBuilder stringBuilder = new StringBuilder();
            for(Day day : toBeAdded.getDays()){
                stringBuilder.append(Day.toBBDDName(day));
            }
            stmt.setString(4, stringBuilder.toString());

            stmt.executeUpdate();

        } catch (SQLException e) {
            ErrorHandler.dealWithStatementCreation(e);
        } finally {
            ErrorHandler.dealWithStatementExecution(stmt, connection);
        }
    }

	public void addBulk(List<HourRange> toBeAdded) {
		if(!toBeAdded.isEmpty()) {
			String modifiedTagsInsert = multipleTagsInsert;

			for(HourRange hourRange : toBeAdded) {
				String daysField = "\'" + toDaysField(hourRange.getDays()) + "\'";
				DateTimeFormatter fmt = DateTimeFormat.forPattern("hh:mm:ss");
				String startTime = "\'" +  hourRange.getStartTime().toString(fmt) + "\'";
				String endTime = "\'" + hourRange.getEndTime().toString(fmt) + "\'";

				modifiedTagsInsert += "(" + hourRange.getIdActivity() + ", " + startTime + ", "
						+ endTime + ", " + daysField + "),";
			}

			// Deleting last comma of query
			modifiedTagsInsert = modifiedTagsInsert.substring(0, modifiedTagsInsert.length() - 1);

			PreparedStatement stmt = null;
			Connection connection = null;

			try {
				connection = ConnectionManager.getLazyConnection();
				stmt = connection.prepareStatement(modifiedTagsInsert);
				stmt.executeUpdate();

			} catch (SQLException e) {
				ErrorHandler.dealWithStatementCreation(e);
			} finally {
				ErrorHandler.dealWithStatementExecution(stmt, connection);
			}
		}
	}

	private String toDaysField(List<Day> days) {
		if(days.isEmpty()) return null;

		String daysField = "";

		for(Day day : days) {
			daysField += Day.toBBDDName(day);
		}

		return daysField;
	}

	private List<Day> fromDaysField(String daysField) {
		List<Day> days = new LinkedList<>();

		for(String daySymbol : daysField.split("(?<=\\G.{2})")) {
			days.add(Day.fromBBDDName(daySymbol));
		}

		return days;
	}

	public List<HourRange> getBulk(long idActivity) {
		List<HourRange> hourRanges = new LinkedList<>();
		PreparedStatement stmt = null;
		ResultSet results = null;
		Connection connection = null;

		try {
			connection = ConnectionManager.getLazyConnection();
			stmt = connection.prepareStatement(selectHourRangeByActivity);
			stmt.setLong(1, idActivity);
			results = stmt.executeQuery();

			while (results.next()){
				HourRange fetchedHourRange = new HourRange();

				fetchedHourRange.setId(results.getLong("id"));
				fetchedHourRange.setIdActivity(results.getLong("idActivity"));
				fetchedHourRange.setStartTime(new LocalTime(results.getTime("startTime")));
				fetchedHourRange.setEndTime(new LocalTime(results.getTime("endTime")));
				fetchedHourRange.setDays(fromDaysField(results.getString("days")));

				hourRanges.add(fetchedHourRange);
			}

		} catch (SQLException e) {
			ErrorHandler.dealWithStatementCreation(e);
			hourRanges = null;
		} finally {
			ErrorHandler.dealWithStatementAndResultExecution(stmt, results, connection);
		}

		return hourRanges;
	}

    @Override
    public HourRange get(long id) {
        PreparedStatement stmt = null;
        ResultSet results = null;
	    HourRange hourRange = new HourRange();

	    Connection connection = null;
        try {
	        connection = ConnectionManager.getLazyConnection();
            stmt = connection.prepareStatement(selectHourRange);
            stmt.setLong(1, id);
            results = stmt.executeQuery();

            results.next();

            hourRange.setId(results.getLong("id"));
            hourRange.setIdActivity(results.getLong("idActivity"));
	        hourRange.setStartTime(new LocalTime(results.getTime("startTime")));
	        hourRange.setEndTime(new LocalTime(results.getTime("endTime")));
	        hourRange.setDays(fromDaysField(results.getString("days")));

        } catch (SQLException e) {
            ErrorHandler.dealWithStatementCreation(e);
            hourRange = null;
        } finally {
            ErrorHandler.dealWithStatementAndResultExecution(stmt, results, connection);
        }

        return hourRange;
    }

    @Override
    public Set<HourRange> list() {
        Set<HourRange> hourRanges = new LinkedHashSet<>();
        PreparedStatement prepStatement = null;
        ResultSet results = null;
	    Connection connection = null;

        try {
	        connection = ConnectionManager.getLazyConnection();
            prepStatement = connection.prepareStatement(selectAllHourRanges);
            results = prepStatement.executeQuery();

            while (results.next()){
                HourRange fetchedHourRange = new HourRange();

                fetchedHourRange.setId(results.getLong("id"));
                fetchedHourRange.setIdActivity(results.getLong("idActivity"));
	            fetchedHourRange.setStartTime(new LocalTime(results.getTime("startTime")));
	            fetchedHourRange.setEndTime(new LocalTime(results.getTime("endTime")));
                fetchedHourRange.setDays(fromDaysField(results.getString("days")));

                hourRanges.add(fetchedHourRange);
            }
        } catch (SQLException e) {
            ErrorHandler.dealWithStatementCreation(e);
            hourRanges = null;
        } finally {
            ErrorHandler.dealWithStatementAndResultExecution(prepStatement, results, connection);
        }

        return hourRanges;
    }

    @Override
    public void update(HourRange toBeUpdated) {
        PreparedStatement stmt = null;

	    Connection connection = null;
        try {
	        connection = ConnectionManager.getLazyConnection();
            stmt = connection.prepareStatement(updateHourRange);

	        stmt.setLong(1, toBeUpdated.getIdActivity());
            stmt.setTime(2, new java.sql.Time(toBeUpdated.getStartTime().getMillisOfDay()));
	        stmt.setTime(3, new java.sql.Time(toBeUpdated.getEndTime().getMillisOfDay()));
            stmt.setString(4, toDaysField(toBeUpdated.getDays()));

            stmt.executeUpdate();


        } catch (SQLException e) {
            ErrorHandler.dealWithStatementCreation(e);
        } finally {
            ErrorHandler.dealWithStatementExecution(stmt, connection);
        }

    }

    @Override
    public void remove(HourRange toBeRemoved) {
        PreparedStatement stmt = null;

	    Connection connection = null;
        try {
	        connection = ConnectionManager.getLazyConnection();
            stmt = connection.prepareStatement(deleteHourRange);
            stmt.setLong(1, toBeRemoved.getId());
            stmt.executeUpdate();

        } catch (SQLException e) {
            ErrorHandler.dealWithStatementCreation(e);
        } finally {
            ErrorHandler.dealWithStatementExecution(stmt, connection);
        }

    }
}
