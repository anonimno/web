package dao;

import java.sql.SQLException;

public class DAOManager {
	private ActivityDAO activityDAO;
	private TagDAO tagDAO;
	private UserDAO userDAO;
	private BookingDAO bookingDAO;
    private HourRangeDAO hourRangeDAO;

    public HourRangeDAO getHourRangeDAO() {
	    if(hourRangeDAO == null) {
		    hourRangeDAO = new HourRangeDAO();
	    }

        return hourRangeDAO;
    }

    public DAOManager() {}

	public ActivityDAO getActivityDAO() throws SQLException {
		if(activityDAO == null) {
			activityDAO = new ActivityDAO();
			activityDAO.setHourRangeDAO(getHourRangeDAO());
		}

		return activityDAO;
	}

	public TagDAO getTagDAO() throws SQLException {
		if(tagDAO == null) {
			tagDAO = new TagDAO();
		}

		return tagDAO;
	}

	public BookingDAO getBookingDAO() throws SQLException {
		if(bookingDAO == null) {
			bookingDAO = new BookingDAO();
		}

		return bookingDAO;
	}

	public UserDAO getUserDAO() throws SQLException {
		if(userDAO == null) {
			userDAO = new UserDAO();
		}

		return userDAO;
	}
}
