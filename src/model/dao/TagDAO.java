package model.dao;


import model.database.ConnectionManager;
import model.entities.Tag;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashSet;
import java.util.Set;

public class TagDAO extends DAO<Tag> {
    private final String addTag = "INSERT into TAG(name) values(?)";
    private final String deleteTag = "DELETE from TAG where id=?";
    private final String selectAllTags = "SELECT * from TAG";
    private final String selectTag = "SELECT * from TAG where id = ?";
    private final String updateTag = "UPDATE TAG SET name = ? where id = ?";

    public TagDAO() {
        super();
        initLog(BookingDAO.class.getName());
    }

    public void add(Tag toBeAdded) {
        PreparedStatement stmt = null;

	    Connection connection = null;
        try {
	        connection = ConnectionManager.getLazyConnection();
            stmt = ConnectionManager.getLazyConnection().prepareStatement(addTag);
            stmt.setString(1, toBeAdded.getTagName());
            stmt.executeUpdate();

            //Get autogenerated ID for Tag
            //ResultSet tableKeys = stmt.getGeneratedKeys();
            //tableKeys.next();
            //toBeAdded.setIdTag(tableKeys.getLong(1));

        } catch (SQLException e) {
            ErrorHandler.dealWithStatementCreation(e);
        } finally {
            ErrorHandler.dealWithStatementExecution(stmt, connection);
        }

        //return toBeAdded;

    }

    @Override
    public Tag get(long id) {
        PreparedStatement stmt = null;
        ResultSet resultSet = null;
        Tag tag = new Tag();

	    Connection connection = null;
        try {
	        connection = ConnectionManager.getLazyConnection();
            stmt = connection.prepareStatement(selectTag);
            stmt.setLong(1, id);
            resultSet = stmt.executeQuery();

            resultSet.next();

            tag.setIdTag(resultSet.getLong("id"));
            tag.setTagName(resultSet.getString("name"));

        } catch (SQLException e) {
            ErrorHandler.dealWithStatementCreation(e);
            tag = null;
        } finally {
            ErrorHandler.dealWithStatementAndResultExecution(stmt, resultSet, connection);
        }

        return tag;
    }

    @Override
    public Set<Tag> list() {
        HashSet<Tag> tags = new HashSet<>();
        PreparedStatement prepStatement = null;
        ResultSet results = null;

	    Connection connection = null;
        try {
	        connection = ConnectionManager.getLazyConnection();
            prepStatement = connection.prepareStatement(selectAllTags);
            results = prepStatement.executeQuery();

            while (results.next()){
                Tag fetchedTag = new Tag();

                fetchedTag.setIdTag(results.getLong("id"));
                fetchedTag.setTagName(results.getString("name"));

                tags.add(fetchedTag);
            }
        } catch (SQLException e) {
            ErrorHandler.dealWithStatementCreation(e);
            tags = null;
        } finally {
            ErrorHandler.dealWithStatementAndResultExecution(prepStatement, results, connection);
        }

        return tags;
    }

    @Override
    public void update(Tag toBeUpdated) {
        PreparedStatement stmt = null;

	    Connection connection = null;
        try {
	        connection = ConnectionManager.getLazyConnection();
            stmt = connection.prepareStatement(updateTag);
            stmt.setString(1, toBeUpdated.getTagName());
            stmt.setLong(2, toBeUpdated.getIdTag());
            stmt.executeUpdate();


        } catch (SQLException e) {
            ErrorHandler.dealWithStatementCreation(e);
        } finally {
            ErrorHandler.dealWithStatementExecution(stmt, connection);
        }

    }

    @Override
    public void remove(Tag toBeRemoved) {
        PreparedStatement stmt = null;

	    Connection connection = null;
        try {
	        connection = ConnectionManager.getLazyConnection();
            stmt = connection.prepareStatement(deleteTag);
            stmt.setLong(1, toBeRemoved.getIdTag());
            stmt.executeUpdate();

        } catch (SQLException e) {
            ErrorHandler.dealWithStatementCreation(e);
        } finally {
            ErrorHandler.dealWithStatementExecution(stmt, connection);
        }

    }
}
