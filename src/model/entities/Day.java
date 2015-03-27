package model.entities;

public enum Day {
    MONDAY("Monday"),
    TUESDAY("Tuesday"),
    WEDNESDAY("Wednesday"),
    THURSDAY("Thursday"),
    FRIDAY("Friday"),
    SATURDAY("Saturday"),
    SUNDAY("Sunday");

    private String dayName;

    Day (String day){
        this.dayName = day;
    }

    public String getDayName() {
        return dayName;
    }

	public static String toBBDDName(Day day) {
		return day.getDayName().substring(0, 2).toUpperCase();
	}

    public static Day fromBBDDName(String dayName) {
        Day day = null;

        switch (dayName){
            case "MO":
                day = MONDAY;
                break;

            case "TU":
                day = TUESDAY;
                break;

            case "WE":
                day = WEDNESDAY;
                break;

            case "TH":
                day = THURSDAY;
                break;

            case "FR":
                day = FRIDAY;
                break;

            case "SA":
                day = SATURDAY;
                break;

            case "SU":
                day = SUNDAY;
                break;

            default:
	            break;
        }

        return day;
    }


}
