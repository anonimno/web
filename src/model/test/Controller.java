package test;

import dao.*;
import model.Activity;
import model.Booking;
import model.Tag;
import model.User;

import java.sql.SQLException;
import java.util.Set;

public class Controller {
    private IOInterface ioInterface;
    private ActivityDAO activityDAO;
    private UserDAO userDAO;
    private BookingDAO bookingDAO;
    private TagDAO tagDAO;
    private HourRangeDAO hourRangeDAO;

    public void setIoInterface(IOInterface ioInterface) {
        this.ioInterface = ioInterface;
    }

    public void start(){
        //Initialize DAOs
        initializeDAOs();

        //Print Menu and execute actions
        byte option = 0;
        do{
            printMenu();
            option = ioInterface.askOption();
            executeOption(option);
        }while (option!=0);
    }

    private void initializeDAOs(){
        try{
            DAOManager daoManager = new DAOManager();
            activityDAO = daoManager.getActivityDAO();
            bookingDAO = daoManager.getBookingDAO();
            tagDAO = daoManager.getTagDAO();
            userDAO = daoManager.getUserDAO();
            hourRangeDAO = daoManager.getHourRangeDAO();

        } catch (SQLException e){
            e.printStackTrace();
        }

    }

    private void executeOption(byte option) {
        switch (option){
            case 1:
                Activity newActivity = ioInterface.getNewActivity(tagDAO);
                activityDAO.add(newActivity);
                break;
            case 2:
                long id = ioInterface.getId();
                Activity activity = activityDAO.get(id);
                newActivity = ioInterface.updateActivity(activity);
                activityDAO.update(newActivity);
                break;
            case 3:
                id = ioInterface.getId();
                Activity activityToRemove = new Activity();
                activityToRemove.setId(id);
                activityDAO.remove(activityToRemove);
                break;
            case 4:
                Set<Activity> activities = activityDAO.list();
                ioInterface.printCollection(activities,"Activities");
                break;
            case 5:
                id = ioInterface.getId();
                activity = activityDAO.get(id);
                ioInterface.printEntity(activity);
                break;
            case 6:
                Booking newBooking = ioInterface.getNewBooking();
                bookingDAO.add(newBooking);
                break;
            case 7:
                id = ioInterface.getId();
                Booking booking = bookingDAO.get(id);
                newBooking = ioInterface.updateBooking(booking);
                bookingDAO.update(newBooking);
            case 8:
                id = ioInterface.getId();
                Booking bookingToRemove = new Booking();
                bookingToRemove.setId(id);
                bookingDAO.remove(bookingToRemove);
                break;
            case 9:
                Set<Booking> bookings = bookingDAO.list();
                ioInterface.printCollection(bookings, "Bookings");
                break;
            case 10:
                id = ioInterface.getId();
                booking = bookingDAO.get(id);
                ioInterface.printEntity(booking);
                break;
            case 11:
                Tag newTag = ioInterface.getNewTag();
                tagDAO.add(newTag);
                break;
            case 12:
                id = ioInterface.getId();
                Tag tag = tagDAO.get(id);
                newTag = ioInterface.updateTag(tag);
                tagDAO.update(newTag);
                break;
            case 13:
                id = ioInterface.getId();
                Tag tagtoRemove = new Tag();
                tagtoRemove.setIdTag(id);
                tagDAO.remove(tagtoRemove);
                break;
            case 14:
                Set<Tag> tags = tagDAO.list();
                ioInterface.printCollection(tags, "Tags");
                break;
            case 15:
                id = ioInterface.getId();
                tag = tagDAO.get(id);
                ioInterface.printEntity(tag);
                break;
            case 16:
                User newUser = ioInterface.getNewUserInfo();
                userDAO.add(newUser);
                break;
            case 17:
                id = ioInterface.getId();
                User user = userDAO.get(id);
                newUser = ioInterface.updateUser(user);
                userDAO.update(newUser);
                break;
            case 18:
                id = ioInterface.getId();
                User userToRemove = new User();
                userToRemove.setUserId(id);
                userDAO.remove(userToRemove);
                break;
            case 19:
                Set<User> users = userDAO.list();
                ioInterface.printCollection(users, "Users");
                break;
            case 20:
                id = ioInterface.getId();
                user = userDAO.get(id);
                ioInterface.printEntity(user);
                break;
            default:
                break;
        }
    }

    private void printMenu(){
	    System.out.println(delimiter(15));
        System.out.println("1.Add activity");
        System.out.println("2.Update activity");
        System.out.println("3.Delete activty");
        System.out.println("4.See all activities");
        System.out.println("5.Get activity");
	    System.out.println(delimiter(15));
        System.out.println("6.Create booking");
        System.out.println("7.Update booking");
        System.out.println("8.Delete booking");
        System.out.println("9.See all bookings");
        System.out.println("10.Get booking");
	    System.out.println(delimiter(15));
        System.out.println("11.Create tag");
        System.out.println("12.Update tag");
        System.out.println("13.Delete tag");
        System.out.println("14.See all tag");
        System.out.println("15.Get tag");
	    System.out.println(delimiter(15));
        System.out.println("16.Create user");
        System.out.println("17.Update user");
        System.out.println("18.Delete user");
        System.out.println("19.See all user");
        System.out.println("20.Get user");
	    System.out.println(delimiter(15));
	    System.out.println("\n");
        System.out.println("- 0 to Exit");
        System.out.println(delimiter(15));

    }

	private String delimiter(int length) {
		StringBuilder sb = new StringBuilder(length);
		int i = 0;
		String delimiter = "=";

		while(i < length) {
			sb.append(delimiter);
			i++;
		}

		return sb.toString();
	}
}
