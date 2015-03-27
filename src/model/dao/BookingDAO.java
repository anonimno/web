package model.dao;

import model.database.ConnectionManager;
import model.entities.Booking;
import org.joda.time.DateTime;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashSet;
import java.util.Set;

public class BookingDAO extends DAO<Booking>{
    private final String addBooking = "INSERT into BOOKING(idActivity, creationDate, state, numAttendees) values(?, ?, ?, ?)";
    private final String deleteBooking = "DELETE from BOOKING where id=?";
    private final String selectAllBookings = "SELECT * from BOOKING";
    private final String selectBooking = "SELECT * from BOOKING where id = ?";
    private final String updateBooking = "UPDATE BOOKING SET state = ?, numAttendees = ? where id = ?";

    public BookingDAO() {
        super();
        initLog(BookingDAO.class.getName());
    }

    @Override
    public void add(Booking bookingToBeAdded) {
        PreparedStatement stmt = null;

	    Connection connection = null;
	    try {
		    connection = ConnectionManager.getLazyConnection();
            stmt = connection.prepareStatement(addBooking);
            stmt.setLong(1, bookingToBeAdded.getIdActivity());
            java.sql.Date date = new java.sql.Date(bookingToBeAdded.getCreatedAt().getMillis());
            stmt.setDate(2, date);
            stmt.setString(3, bookingToBeAdded.getState());
            stmt.setInt(4, bookingToBeAdded.getNumAtendees());
	        stmt.executeUpdate();
        } catch (SQLException e) {
		    ErrorHandler.dealWithStatementCreation(e);
        } finally {
	        ErrorHandler.dealWithStatementExecution(stmt, connection);
        }

    }

    @Override
    public void remove(Booking toBeRemoved) {
        PreparedStatement stmt = null;


	    Connection connection = null;
        try {
	        connection = ConnectionManager.getLazyConnection();
            stmt = connection.prepareStatement(deleteBooking);
            stmt.setLong(1, toBeRemoved.getId());
            stmt.executeUpdate();
        } catch (SQLException e) {
	        ErrorHandler.dealWithStatementCreation(e);
        } finally {
            ErrorHandler.dealWithStatementExecution(stmt, connection);
        }

    }

    @Override
    public void update(Booking toBeUpdated) {
        PreparedStatement stmt = null;

	    Connection connection = null;
        try {
	        connection = ConnectionManager.getLazyConnection();
            stmt = connection.prepareStatement(updateBooking);

            stmt.setString(1, toBeUpdated.getState());
            stmt.setInt(2, toBeUpdated.getNumAtendees());

            stmt.setLong(3, toBeUpdated.getId());

            stmt.executeUpdate();
        } catch (SQLException e) {
	        ErrorHandler.dealWithStatementCreation(e);
        } finally {
	        ErrorHandler.dealWithStatementExecution(stmt, connection);
        }

    }

    @Override
    public Booking get(long id) {
        PreparedStatement stmt = null;
        ResultSet resultSet = null;
        Booking booking = new Booking();

	    Connection connection = null;
        try {
	        connection = ConnectionManager.getLazyConnection();
            stmt = connection.prepareStatement(selectBooking);
            stmt.setLong(1, id);
            resultSet = stmt.executeQuery();

            resultSet.next();

            booking.setId(resultSet.getLong("id"));
            booking.setIdActivity(resultSet.getLong("idActivity"));
            booking.setCreatedAt(new DateTime(resultSet.getDate("creationDate")));
            booking.setState(resultSet.getString("state"));
            booking.setNumAtendees(resultSet.getInt("numAttendees"));

        } catch (SQLException e) {
	        ErrorHandler.dealWithStatementCreation(e);
	        booking = null;
        } finally {
	        ErrorHandler.dealWithStatementAndResultExecution(stmt, resultSet, connection);
        }

        return booking;
    }

    @Override
    public Set<Booking> list() {
        PreparedStatement stmt = null;
        ResultSet resultSet = null;
        Set<Booking> bookings = new HashSet<>();

	    Connection connection = null;
        try {
	        connection = ConnectionManager.getLazyConnection();
            stmt = connection.prepareStatement(selectAllBookings);
            resultSet = stmt.executeQuery();

            while(resultSet.next()){
                Booking booking = new Booking();

                booking.setId(resultSet.getLong("id"));
                booking.setIdActivity(resultSet.getLong("idActivity"));
                booking.setCreatedAt(new DateTime(resultSet.getDate("creationDate")));
                booking.setState(resultSet.getString("state"));
                booking.setNumAtendees(resultSet.getInt("numAttendees"));

                bookings.add(booking);
            }
        } catch (SQLException e) {
	        ErrorHandler.dealWithStatementCreation(e);
	        bookings = null;
        } finally {
	        ErrorHandler.dealWithStatementAndResultExecution(stmt, resultSet, connection);
        }

        return bookings;
    }
}
