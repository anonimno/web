package dao;

import database.ConnectionErrors;
import database.ConnectionManager;

import java.util.Set;

public abstract class DAO<T> extends Loggable {
	protected final static ConnectionErrors ErrorHandler = ConnectionManager.getErrors();

	public DAO() {}

	public abstract void add(T toBeAdded);
	public abstract void remove(T toBeRemoved);
	public abstract void update(T toBeUpdated);
	public abstract T get(long id);
	public abstract Set<T> list();
}
