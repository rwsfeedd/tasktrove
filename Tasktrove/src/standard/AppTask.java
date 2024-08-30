package standard;

public class AppTask {
	public enum Difficulty {
		LOW,
		MEDIUM,
		HIGH
	}
	public static final String EINFACH = "Einfach";
	public static final String MITTEL = "Mittel";
	public static final String SCHWER = "Schwer";

	public enum Type {
		DAILY,
		WEEKLY,
		MONHLY
	}
	public static final String TAG = "Tagesaufgabe";
	public static final String WOCHE = "Wochenaufgabe";
	public static final String MONAT = "Monatsaufgabe";
	
	private String name;
	private Difficulty difficulty;
	private Type type; 
	private boolean done;
	
	public AppTask() {
		this.name = "";
		this.difficulty = null;
		this.type = null;
		this.done = false;
	}

	public AppTask(String name, Difficulty difficulty, Type typ, boolean done) {
		this.name = name;
		this.difficulty = difficulty;
		this.type = typ;
		this.done = done;
	}
	
	public boolean isValid() {
		if(name.equals("") || name.length() > 20) return false;
		if(difficulty == null) return false;
		if(type == null) return false;
		return true;
	}
	
	public boolean equals(AppTask task) {
		if(!(this.getName().equals(task.getName()))) return false;
		if(this.getDifficulty() != task.getDifficulty()) return false;
		if(this.getType() != task.getType()) return false;
		if(this.isDone() != task.isDone()) return false;
		return true;
	}
	
	public static Difficulty parseStringToDifficulty(String str) {
		if(str == null) return null;
		if(str.equals(EINFACH)) return Difficulty.LOW;
		if(str.equals(MITTEL)) return Difficulty.MEDIUM;
		if(str.equals(SCHWER)) return Difficulty.HIGH;
		System.err.println("Eingabestring kann keinem Wert von enum Difficulty zugeordnet werden");
		return null;
	}

	public static String parseDifficultyToString(Difficulty difficulty) {
		switch(difficulty) {
		case LOW:
			return EINFACH;
		case MEDIUM:
			return MITTEL;
		case HIGH:
			return SCHWER;
		default:
			System.err.println("Wert von enum Difficulty kann keinem String zugeordnet werden!");
			return null;
		}
	}
	
	public static Type parseStringToType(String str) {
		if(str == null) return null;
		if(str.equals(TAG)) return Type.DAILY;
		if(str.equals(WOCHE)) return Type.WEEKLY;
		if(str.equals(MONAT)) return Type.MONHLY;
		System.err.println("Eingabestring kann keinem Wert von enum Type zugeordnet werden!");
		return null;
	}

	public static String parseTypeToString(Type type) {
		switch(type) {
		case DAILY:
			return TAG;
		case WEEKLY:
			return WOCHE;
		case MONHLY:
			return MONAT;
		default:
			System.err.println("Wert von enum Type kann keinem String zugeordnet werden!");
			return null;
		}
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Difficulty getDifficulty() {
		return difficulty;
	}

	public void setDifficulty(Difficulty difficulty) {
		this.difficulty = difficulty;
	}

	public Type getType() {
		return type;
	}

	public void setType(Type typ) {
		this.type = typ;
	}

	public boolean isDone() {
		return done;
	}

	public void setDone(boolean done) {
		this.done = done;
	}
	
	public void invertDone() {
		if(this.done == true) {
			this.done = false;
		}else {
			this.done = true;
		}
	}

}
