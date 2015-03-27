package dao;

import database.ConnectionManager;
import model.User;
import model.UserType;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashSet;
import java.util.Set;

public class UserDAO extends DAO<User> {
    // SQL statements
    private final String addUser = "INSERT into USERACCOUNT(username, password, firstName, lastName, email, pictureUrl, userType, nif, phoneNumber, address, gender) values(?,?,?,?,?,?,?,?,?,?,?)";
    private final String deleteUser = "DELETE from USERACCOUNT where id=?";
    private final String selectAllUsers = "SELECT * from USERACCOUNT";
    private final String selectUser = "SELECT * from USERACCOUNT where id = ?";
    private final String updateUser = "UPDATE USERACCOUNT SET userName = ?, password = ?, firstName = ?, lastName = ?, email = ?, pictureUrl = ?, userType = ?, nif = ?, phoneNumber = ?, address = ?, gender = ? where id = ?";

    public UserDAO() {
        super();
        initLog(UserDAO.class.getName());
    }

    @Override
    public void add(User toBeAdded) {
        PreparedStatement stmt = null;

	    Connection connection = null;
        try {
	        connection = ConnectionManager.getLazyConnection();
            stmt = connection.prepareStatement(addUser);
            stmt.setString(1, toBeAdded.getUsername());
            stmt.setString(2, toBeAdded.getPassword());
            stmt.setString(3, toBeAdded.getFirstName());
            stmt.setString(4, toBeAdded.getLastName());
            stmt.setString(5, toBeAdded.getEmail());
            stmt.setString(6, toBeAdded.getPictureURL());
            stmt.setString(7, toBeAdded.getType().getBbddName());
            stmt.setString(8, toBeAdded.getNIF());
            stmt.setString(9, toBeAdded.getPhoneNumber());
            stmt.setString(10, toBeAdded.getAddress());
            stmt.setBoolean(11, toBeAdded.isGender());

            stmt.executeUpdate();

        } catch (SQLException e) {
            ErrorHandler.dealWithStatementCreation(e);
        } finally {
            ErrorHandler.dealWithStatementExecution(stmt, connection);
        }
    }

    @Override
    public void remove(User toBeRemoved) {
        PreparedStatement stmt = null;

	    Connection connection = null;
        try {
	        connection = ConnectionManager.getLazyConnection();
            stmt = connection.prepareStatement(deleteUser);
            stmt.setLong(1, toBeRemoved.getUserId());
            stmt.executeUpdate();
        } catch (SQLException e) {
            ErrorHandler.dealWithStatementCreation(e);
        } finally {
            ErrorHandler.dealWithStatementExecution(stmt, connection);
        }

    }

    @Override
    public void update(User toBeUpdated) {
        PreparedStatement stmt = null;

	    Connection connection = null;
        try {
	        connection = ConnectionManager.getLazyConnection();
            stmt = connection.prepareStatement(updateUser);
            stmt.setString(1, toBeUpdated.getUsername());
            stmt.setString(2, toBeUpdated.getPassword());
            stmt.setString(3, toBeUpdated.getFirstName());
            stmt.setString(4, toBeUpdated.getLastName());
            stmt.setString(5, toBeUpdated.getEmail());
            stmt.setString(6, toBeUpdated.getPictureURL());
            stmt.setString(7, toBeUpdated.getType().getBbddName());
            stmt.setString(8, toBeUpdated.getNIF());
            stmt.setString(9, toBeUpdated.getPhoneNumber());
            stmt.setString(10, toBeUpdated.getAddress());
            stmt.setBoolean(11, toBeUpdated.isGender());

            stmt.setLong(12, toBeUpdated.getUserId());

            stmt.executeUpdate();
        } catch (SQLException e) {
            ErrorHandler.dealWithStatementCreation(e);
        } finally {
            ErrorHandler.dealWithStatementExecution(stmt, connection);
        }

    }

    @Override
    public User get(long id) {

        PreparedStatement stmt = null;
        ResultSet resultSet = null;
        User user = new User();

	    Connection connection = null;
        try {
	        connection = ConnectionManager.getLazyConnection();
            stmt = connection.prepareStatement(selectUser);
            stmt.setLong(1, id);
            resultSet = stmt.executeQuery();

            resultSet.next();
            user.setUserId(resultSet.getLong("id"));
            user.setUsername(resultSet.getString("userName"));
            user.setPassword(resultSet.getString("password"));
            user.setFirstName(resultSet.getString("firstName"));
            user.setLastName(resultSet.getString("lastName"));
            user.setNIF(resultSet.getString("nif"));
            user.setAddress(resultSet.getString("address"));
            user.setPhoneNumber(resultSet.getString("phoneNumber"));
            user.setEmail(resultSet.getString("email"));
            user.setType(UserType.fromBBDD(resultSet.getString("userType")));
            user.setPictureURL(resultSet.getString("pictureUrl"));
            user.setGender(resultSet.getBoolean("gender"));

        } catch (SQLException e) {
            ErrorHandler.dealWithStatementCreation(e);
            user = null;
        } finally {
            ErrorHandler.dealWithStatementAndResultExecution(stmt, resultSet, connection);
        }

        return user;
    }

    @Override
    public Set<User> list() {
        PreparedStatement stmt = null;
        ResultSet resultSet = null;
        Set<User> users = new HashSet<>();

	    Connection connection = null;
        try {
	        connection = ConnectionManager.getLazyConnection();
            stmt = connection.prepareStatement(selectAllUsers);
            resultSet = stmt.executeQuery();

            while(resultSet.next()) {
                User user = new User();
                user.setUserId(resultSet.getLong("id"));
                user.setUsername(resultSet.getString("userName"));
                user.setPassword(resultSet.getString("password"));
                user.setFirstName(resultSet.getString("firstName"));
                user.setLastName(resultSet.getString("lastName"));
                user.setNIF(resultSet.getString("nif"));
                user.setAddress(resultSet.getString("address"));
                user.setPhoneNumber(resultSet.getString("phoneNumber"));
                user.setEmail(resultSet.getString("email"));
                user.setType(UserType.fromBBDD(resultSet.getString("userType")));
                user.setPictureURL(resultSet.getString("pictureUrl"));
                user.setGender(resultSet.getBoolean("gender"));

                users.add(user);
            }
        } catch (SQLException e) {
            ErrorHandler.dealWithStatementCreation(e);
            users = null;
        } finally {
            ErrorHandler.dealWithStatementAndResultExecution(stmt, resultSet, connection);
        }

        return users;
    }
}
