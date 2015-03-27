package model.entities;

public enum UserType {
	CLIENT("client"),
	INSTRUCTOR("instructor");

	private String bbddName;

	UserType(String name) {
		this.bbddName = name;
	}

	public static UserType fromId(byte id) {
		return values()[id];
	}
	
	public static UserType fromBBDD(String name) {
		UserType userType = null;
        byte b;
		if("client".equals(name)) {
            b = 0;
			userType = fromId(b);
		} else if("instructor".equals(name)) {
            b = 1;
			userType = fromId(b);
		}
		return userType;
	}

	public byte toId(UserType type) {
		return (byte) type.ordinal();
	}
	
	public String getBbddName() {
		return bbddName;
	}
}
