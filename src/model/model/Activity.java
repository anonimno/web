package model;

import java.util.List;

public class Activity implements RowPrinter {
    private long id;
    private String name;
    private List<Tag> tags;
    private List<HourRange> schedule;
    private String description;
    private byte dificultyLevel;
    private float pricePerPerson;
    private int minPers;
    private int maxPers;
    private int recommendedAge;

    public Activity() {}

    public Activity(long id, List<Tag> tags, List<HourRange> schedule,
                    String description, byte dificultyLevel, long pricePerPerson,
                    int minPers, int maxPers, int recommendedAge) {
        this.id = id;
        this.tags = tags;
        this.schedule = schedule;
        this.description = description;
        this.dificultyLevel = dificultyLevel;
        this.pricePerPerson = pricePerPerson;
        this.minPers = minPers;
        this.maxPers = maxPers;
        this.recommendedAge = recommendedAge;
    }

    public int getRecommendedAge() {return recommendedAge;}
    public void setRecommendedAge(int recomandedAge) {this.recommendedAge = recomandedAge;}
    public long getId() {return id;}
    public void setId(long id) {this.id = id;}
    public String getName() {return name;}
    public void setName(String name) {this.name = name;}
    public List<Tag> getTags() {return tags;}
    public void setTags(List<Tag> tags) {this.tags = tags;}
    public List<HourRange> getSchedule() {return schedule;}
    public void setSchedule(List<HourRange> schedule) {this.schedule = schedule;}
    public String getDescription() {return description;}
    public void setDescription(String description) {this.description = description;}
    public byte getDifficultyLevel() {return dificultyLevel;}
    public void setDifficultyLevel(byte dificultyLevel) {this.dificultyLevel = dificultyLevel;}
    public float getPricePerPerson() {return pricePerPerson;}
    public void setPricePerPerson(float pricePerPerson) {this.pricePerPerson = pricePerPerson;}
    public int getMinPers() {return minPers;}
    public void setMinPers(int minPers) {this.minPers = minPers;}
    public int getMaxPers() {return maxPers;}
    public void setMaxPers(int maxPers) {this.maxPers = maxPers;}


    public String[] rowData() {
        String[] rowData = new String[10];
        rowData[0] = Long.toString(id);
        rowData[1] = name;
        rowData[2] = description;
        rowData[3] = buildStringFromList(tags);
        rowData[4] = Byte.toString(dificultyLevel);
        rowData[5] = Float.toString(pricePerPerson);
        rowData[6] = Integer.toString(minPers);
        rowData[7] = Integer.toString(maxPers);
        rowData[8] = Integer.toString(recommendedAge);
        rowData[9] = buildStringFromList(getSchedule());
        return rowData;
    }

    public String[] columnNames() {
        String[] columnNames = new String[10];
        columnNames[0] = "Id";
        columnNames[1] = "Name";
        columnNames[2] = "Description";
        columnNames[3] = "Tags";
        columnNames[4] = "Difficulty level";
        columnNames[5] = "Price/Pers";
        columnNames[6] = "Min persons";
        columnNames[7] = "Max persons";
        columnNames[8] = "Recommended age";
        columnNames[9] = "Hour ranges";
        return columnNames;
    }

    private <R extends RowPrinter> String buildStringFromList(List<R> entities){
        StringBuilder stringBuilder = new StringBuilder();
        for(R entity : entities){
            for(String info : entity.rowData()){
                stringBuilder.append(info+" ");
            }
            stringBuilder.append("\n");
        }
        return stringBuilder.toString();
    }
}
