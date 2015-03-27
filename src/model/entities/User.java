package model.entities;

/**
 * User is a generalization entity over Client, Manager and Instructor.
 * The decision to handle all the information in one class has been taken
 * to avoid an excessive amount of joins. Moreover, there are no big differences
 * between the three in data requirements, only in functionality.
 *
 * Hence, we insert a discriminator column and mark NULL all data related to
 * a specific type of user.
 *
 *
 * */

 public class User implements RowPrinter {
	private long userId;
	private String username;
	private String password;
    private String firstName;
    private String lastName;
    private String email;
	private String pictureURL;
	private UserType type;

	// Client data
	private String NIF;
	private String phoneNumber;
	private String address;

	// Instructor data
	private boolean gender;
	//private Language[] languages;

	// Manager data -> No extra data required

	public long getUserId() {
		return userId;
	}

	public void setUserId(long id) {
		this.userId = id;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPictureURL() {
		return pictureURL;
	}

	public void setPictureURL(String pictureURL) {
		this.pictureURL = pictureURL;
	}

	public UserType getType() {
		return type;
	}

	public void setType(UserType type) {
		this.type = type;
	}

	public String getNIF() {
		return NIF;
	}

	public void setNIF(String NIF) {
		this.NIF = NIF;
	}

	public String getPhoneNumber() {
		return phoneNumber;
	}

	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

    public boolean isGender() {
        return gender;
    }

    public void setGender(boolean gender) {
        this.gender = gender;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

	/*public Language[] getLanguages() {
		return languages;
	}

	public void setLanguages(Language[] languages) {
		this.languages = languages;
	}*/


    public String[] rowData() {
        String[] rowData = new String[12];
        rowData[0] = Long.toString(userId);
        rowData[1] = username;
        rowData[2] = password;
        rowData[3] = firstName;
        rowData[4] = lastName;
        rowData[5] = email;
        rowData[6] = pictureURL;
        rowData[7] = type.getBbddName();
        rowData[8] = NIF;
        rowData[9] = phoneNumber;
        rowData[10] = address;
        rowData[11] = Boolean.toString(gender);
        return rowData;
    }

    public String[] columnNames() {
        String[] columnNames = new String[12];
        columnNames[0] = "id";
        columnNames[1] = "User name";
        columnNames[2] = "Password";
        columnNames[3] = "First name";
        columnNames[4] = "Last name";
        columnNames[5] = "Email";
        columnNames[6] = "Picture URL";
        columnNames[7] = "Type";
        columnNames[8] = "Nif";
        columnNames[9] = "Phone";
        columnNames[10] = "Address";
        columnNames[11] = "Gender";
        return columnNames;
    }
}
