package model.entities;

import org.joda.time.LocalTime;

import java.util.List;

public class HourRange implements RowPrinter {
	private long id;
	private long idActivity;
	private LocalTime startTime;
    private LocalTime endTime;
    private List<Day> days;

	public HourRange() {}

	public HourRange(long id, long idActivity, LocalTime startTime, LocalTime endTime, List<Day> days) {
		this.id = id;
		this.idActivity = idActivity;
		this.startTime = startTime;
        this.endTime = endTime;
        this.days = days;
	}

	@Override
	public String[] rowData() {
		String[] rowData = new String[5];
		rowData[0] = Long.toString(id);
		rowData[1] = Long.toString(idActivity);
		rowData[2] = startTime.toString();
		rowData[3] = endTime.toString();
        rowData[4] = days.toString();
		return rowData;
	}

	@Override
	public String[] columnNames() {
		String[] columnNames = new String[5];
		columnNames[0] = "id";
		columnNames[1] = "idActivity";
		columnNames[2] = "startTime";
		columnNames[3] = "endTime";
        columnNames[4] = "days";
		return columnNames;
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
    public LocalTime getStartTime() {
        return startTime;
    }
    public void setStartTime(LocalTime startTime) {
        this.startTime = startTime;
    }
    public LocalTime getEndTime() {
        return endTime;
    }
    public void setEndTime(LocalTime endTime) {
        this.endTime = endTime;
    }
    public List<Day> getDays() {
        return days;
    }
    public void setDays(List<Day> days) {
        this.days = days;
    }
}
