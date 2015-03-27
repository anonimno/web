package model.entities;


import org.joda.time.DateTime;
import java.util.List;

public class Booking implements RowPrinter{
    private long id;
    private long idActivity;
    private String state;
    private DateTime createdAt;
    private int numAtendees;
    private List<User> instructors;
    private List<Review> reviews;

    public Booking() {}
    public Booking(long id, long idActivity, String state, DateTime createdAt,
                   int numAtendees, List<User> instructors,
                   List<Review> reviews){
        this.id = id;
        this.idActivity = idActivity;
        this.state = state;
        this.createdAt = createdAt;
        this.numAtendees = numAtendees;
        this.instructors = instructors;
        this.reviews = reviews;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getIdActivity() {
        return idActivity;
    }

    public void setIdActivity(long idActivity) {
        this.idActivity = idActivity;
    }

    public String getState() {return state;}

    public void setState(String state) {
        this.state = state;
    }

    public DateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(DateTime createdAt) {
        this.createdAt = createdAt;
    }

    public int getNumAtendees() {
        return numAtendees;
    }

    public void setNumAtendees(int numAtendees) {
        this.numAtendees = numAtendees;
    }

    public List<User> getInstructors() {
        return instructors;
    }

    public void setInstructors(List<User> instructors) {
        this.instructors = instructors;
    }

    public List<Review> getReviews() {return reviews;}

    public void setReviews(List<Review> reviews) {this.reviews = reviews;}


    public String[] rowData() {
        String[] rowData = new String[7];
        rowData[0] = Long.toString(id);
        rowData[1] = Long.toString(idActivity);
        rowData[2] = state;
        rowData[3] = createdAt.toString();
        rowData[4] = Integer.toString(numAtendees);
        rowData[5] = "instructors";
        rowData[6] = "reviews";

        return rowData;
    }

    public String[] columnNames() {
        String[] columnNames = new String[7];
        columnNames[0] = "id";
        columnNames[1] = "idActivity";
        columnNames[2] = "State";
        columnNames[3] = "Created";
        columnNames[4] = "Num Atendees";
        columnNames[5] = "Instructors";
        columnNames[6] = "Reviews";

        return columnNames;
    }
}
