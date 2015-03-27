package model.entities;

public class Tag implements RowPrinter{
    private long idTag;
	private String tagName;
    public Tag(){}

	public Tag(String tagName) {
		this.tagName = tagName;
	}

	public String getTagName() {
		return tagName;
	}

    public long getIdTag() {
        return idTag;
    }

    public void setIdTag(long idTag) {
        this.idTag = idTag;
    }

    public void setTagName(String tagName) {
        this.tagName = tagName;
    }

    public String[] rowData() {
        String[] rowData = new String[2];
        rowData[0] = Long.toString(idTag);
        rowData[1] = tagName;

        return rowData;
    }

    public String[] columnNames() {
        String[] columnNames = new String[2];
        columnNames[0] = "id";
        columnNames[1] = "Name";

        return columnNames;
    }
}
