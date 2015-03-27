package model.entities;

enum Language {
	SPANISH("Espanol"),
	ENGLISH("Ingles"),
	FRENCH("Frances"),
	GERMAN("Aleman"),
	ROMANIAN("Rumano");

	final String name;

	Language(String name) {
		this.name = name;
	}

	byte toId(Language lang) {
		return (byte) lang.ordinal();
	}
}

public class Languages {
	public static Language[] list() {
		return Language.values();
	}

	public static Language fromId(byte id) {
		return Language.values()[id];
	}
}
