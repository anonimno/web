package test;

import dao.TagDAO;
import model.*;
import org.joda.time.DateTime;
import org.joda.time.LocalTime;

import java.util.*;


public class IOInterface {

    private Controller delegate;
    private Scanner scanner = new Scanner(System.in);

    public void setDelegate(Controller delegate) {
        this.delegate = delegate;
    }

    public IOInterface(){
        super();
        scanner.useDelimiter("\n");
    }

    public IOInterface(Controller delegate) {
        this.delegate = delegate;
        scanner.useDelimiter("\n");
    }


    public void welcome() {
        System.out.println("Test application for DB access");
    }

    public void printMessage(String message){
        System.out.println(message);
    }

    public byte askOption() throws InputMismatchException, NumberFormatException{
        System.out.println("Choose option: ");
        return scanner.nextByte();
    }

    //Common used methods
    public long getId() throws InputMismatchException, NumberFormatException{
        System.out.println("ID: ");
        return scanner.nextLong();
    }

    private String buildStringFromCollection(String[] row){
        StringBuilder stringBuilder = new StringBuilder();
        for(int i=0;i<row.length-1;i++){
            stringBuilder.append(row[i]+"\t");
        }
        stringBuilder.append(row[row.length-1]+"\n");
        return stringBuilder.toString();
    }

    public <E extends RowPrinter> void printCollection(Collection<E> collection, String title) {
        System.out.println("");
        System.out.println("");
	    System.out.println(title);

        if(collection != null && !collection.isEmpty()){
            Iterator<E> iterador = collection.iterator();
            E first = iterador.next();
            System.out.println(buildStringFromCollection(first.columnNames()));
            System.out.print(buildStringFromCollection(first.rowData()));
            while(iterador.hasNext()){
                System.out.print(buildStringFromCollection(iterador.next().rowData()));
            }
        }
        System.out.println("");

    }

    public <E extends RowPrinter> void printEntity(E entity) {
        System.out.println(buildStringFromCollection(entity.columnNames()));
        System.out.print(buildStringFromCollection(entity.rowData()));
    }

    //USER ACTIONS
    public User getNewUserInfo(){
        User newUser = new User();

        printMessage("User name:");
        String userName = scanner.next();
        printMessage("Password:");
        String password = scanner.next();
        printMessage("First name:");
        String firstName = scanner.next();
        printMessage("Last name:");
        String lastName = scanner.next();
        printMessage("Email:");
        String email = scanner.next();
        printMessage("Address:");
        String address = scanner.next();
        printMessage("Phone:");
        String phone = scanner.next();
        printMessage("Nif:");
        String nif = scanner.next();
        printMessage("Type (0-Client, 1-Instructor):");
        UserType userType = UserType.fromId(scanner.nextByte());
        printMessage("Gender:");
        boolean gender = scanner.nextBoolean();

        newUser.setUsername(userName);
        newUser.setFirstName(firstName);
        newUser.setLastName(lastName);
        newUser.setEmail(email);
        newUser.setAddress(address);
        newUser.setNIF(nif);
        newUser.setPassword(password);
        newUser.setPhoneNumber(phone);
        newUser.setType(userType);
        newUser.setGender(gender);

        return newUser;
    }

    public User updateUser(User user) {
        printMessage(user.getUsername());
        printMessage("User name:");
        String userName = scanner.next();
        printMessage(user.getPassword());
        printMessage("Password:");
        String password = scanner.next();
        printMessage(user.getFirstName());
        printMessage("First name:");
        String firstName = scanner.next();
        printMessage(user.getLastName());
        printMessage("Last name:");
        String lastName = scanner.next();
        printMessage(user.getEmail());
        printMessage("Email:");
        String email = scanner.next();
        printMessage(user.getAddress());
        printMessage("Address:");
        String address = scanner.next();
        printMessage(user.getPhoneNumber());
        printMessage("Phone:");
        String phone = scanner.next();
        printMessage(user.getNIF());
        printMessage("Nif:");
        String nif = scanner.next();
        printMessage(user.getType().getBbddName());
        printMessage("Type (0-Client, 1-Instructor):");
        UserType userType = UserType.fromId(scanner.nextByte());
        printMessage(Boolean.toString(user.isGender()));
        printMessage("Gender:");
        boolean gender = scanner.nextBoolean();

        user.setUsername(userName);
        user.setFirstName(firstName);
        user.setLastName(lastName);
        user.setEmail(email);
        user.setAddress(address);
        user.setNIF(nif);
        user.setPassword(password);
        user.setPhoneNumber(phone);
        user.setType(userType);
        user.setGender(gender);

        return user;
    }

    //ACTIVITY ACTIONS
    public Activity getNewActivity(TagDAO tagDAO) {
        Activity newActivity = new Activity();
        printMessage("Name:");
        String activityName = scanner.next();
        printMessage("Description:");
        String description = scanner.next();
        printMessage("Dificulty level (0-5):");
        byte dificulty = scanner.nextByte();
        printMessage("Price/Pers:");
        float pricePers = scanner.nextFloat();
        printMessage("Min persons:");
        int minPers = scanner.nextInt();
        printMessage("Max persons:");
        int maxPers = scanner.nextInt();
        printMessage("Recomended age:");
        int recommendedAge = scanner.nextInt();
        printMessage("Choose tags:");
        List<Tag> tags = getTags(tagDAO);
        List<HourRange> hourRanges = getHourRanges();

        newActivity.setName(activityName);
        newActivity.setDescription(description);
        newActivity.setDifficultyLevel(dificulty);
        newActivity.setPricePerPerson(pricePers);
        newActivity.setMinPers(minPers);
        newActivity.setMaxPers(maxPers);
        newActivity.setRecommendedAge(recommendedAge);
        newActivity.setTags(tags);
        newActivity.setSchedule(hourRanges);

        return newActivity;
    }

    private List<Tag> getTags(TagDAO tagDAO) {
        List<Tag> tags = new LinkedList<>();
        printCollection(tagDAO.list(),"Tags");
        byte option = 0;
        do{
            printAddTagsMenu();
            option = askOption();
            executeOptionMenuTags(option, tags, tagDAO);
        }while (option!=0);
        return tags;
    }

    private void printAddTagsMenu() {
        System.out.println();
        System.out.println("1.Add tag");
        System.out.println("2.List tags");
        System.out.println("3.Create tag");
        System.out.println("0.Exit");
    }

    private void executeOptionMenuTags(byte option, List<Tag> tags, TagDAO tagDAO) {
        switch (option){
            case 1: long id = getId();
                    Tag tag = tagDAO.get(id);
                    tags.add(tag);
                    break;
            case 2: printCollection(tagDAO.list(), "Tags");
                    break;
            case 3: tag = getNewTag();
                    tagDAO.add(tag);
                    break;
            default:break;
        }
    }
    private List<HourRange> getHourRanges() {
        List<HourRange> hours = new LinkedList<>();

        byte option = 0;

        do {
            printAddHourRangesMenu();
            option = askOption();
            executeOptionMenuHourRanges(option, hours);
        } while (option!=0);

        return hours;
    }

    private void printAddHourRangesMenu() {
        System.out.println();
        System.out.println("1.Add hour range");
        System.out.println("0.Exit");
    }

    private void executeOptionMenuHourRanges(byte option, List<HourRange> hourRanges) {
        switch (option){
            case 1:
                HourRange hourRange = getNewHourRange();
	            System.out.println(hourRange);
                hourRanges.add(hourRange);
                break;
            default:break;
        }
    }
    private HourRange getNewHourRange() {
        List<Day> daysActivity = new LinkedList<>();

        printMessage("Start hour: (i.e. hh:mm)");
        String[] fields = scanner.next().split(":");
	    LocalTime startTime = new LocalTime(Integer.valueOf(fields[0]), Integer.valueOf(fields[1]));

        printMessage("End hour: (i.e. hh:mm)");
	    fields = scanner.next().split(":");
	    LocalTime endTime = new LocalTime(Integer.valueOf(fields[0]), Integer.valueOf(fields[1]));

        printMessage("Days (MO,TU,WE,TH,FR,SA,SU separated by comma):");
        String days = scanner.next();
        String[] daysSplitted = days.split(",");

        for(String day : daysSplitted){
            daysActivity.add(Day.fromBBDDName(day));
        }

        HourRange hourRange = new HourRange();
        hourRange.setStartTime(startTime);
        hourRange.setEndTime(endTime);
        hourRange.setDays(daysActivity);

        return hourRange;
    }

    public Activity updateActivity(Activity activity) {
        printMessage(activity.getName());
        printMessage("New name:");
        String activityName = scanner.next();
        printMessage(activity.getDescription());
        printMessage("New description:");
        String description = scanner.next();
        printMessage(Byte.toString(activity.getDifficultyLevel()));
        printMessage("New dificulty level (0-5):");
        byte dificulty = scanner.nextByte();
        printMessage(Float.toString(activity.getPricePerPerson()));
        printMessage("New Price/Pers:");
        long pricePers = scanner.nextLong();
        printMessage(Integer.toString(activity.getMinPers()));
        printMessage("New min persons:");
        int minPers = scanner.nextInt();
        printMessage(Integer.toString(activity.getMaxPers()));
        printMessage("New max persons:");
        int maxPers = scanner.nextInt();
        printMessage(Integer.toString(activity.getRecommendedAge()));
        printMessage("New recomended age:");
        int recomendedAge = scanner.nextInt();

        activity.setName(activityName);
        activity.setDescription(description);
        activity.setDifficultyLevel(dificulty);
        activity.setPricePerPerson(pricePers);
        activity.setMinPers(minPers);
        activity.setMaxPers(maxPers);
        activity.setRecommendedAge(recomendedAge);

        return activity;
    }

    //BOOKING ACTIONS
    public Booking getNewBooking() {

        Booking newBooking = new Booking();
        printMessage("idActivity:");
        long activityID = scanner.nextLong();
        printMessage("State:");
        String state = scanner.next();
        DateTime created = new DateTime();
        printMessage("Num atendees:");
        int numAtendees = scanner.nextInt();

        newBooking.setIdActivity(activityID);
        newBooking.setState(state);
        newBooking.setCreatedAt(created);
        newBooking.setNumAtendees(numAtendees);

        return newBooking;
    }

    public Booking updateBooking(Booking booking) {
        printMessage(Long.toString(booking.getIdActivity()));
        printMessage("idActivity:");
        long activityID = scanner.nextLong();
        printMessage(booking.getState());
        printMessage("State:");
        String state = scanner.next();
        printMessage(Integer.toString(booking.getNumAtendees()));
        printMessage("Num atendees:");
        int numAtendees = scanner.nextInt();

        booking.setIdActivity(activityID);
        booking.setState(state);
        booking.setNumAtendees(numAtendees);

        return booking;
    }

    //TAG ACTIONS
    public Tag getNewTag() {
        Tag newTag = new Tag();
        printMessage("Name:");
        String tagName = scanner.next();
        newTag.setTagName(tagName);
        return newTag;
    }

    public Tag updateTag(Tag tag) {
        printMessage(tag.getTagName());
        printMessage("Name:");
        String tagName = scanner.next();

        tag.setTagName(tagName);
        return tag;
    }
}
