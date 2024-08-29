package standard;

public class AppTask {
	public enum Difficulty {
		LOW,
		MEDIUM,
		HIGH
	}
	public enum Type {
		DAILY,
		WEEKLY,
		MONHLY
	}
	private String name;
	private Difficulty difficulty;
	private Type typ; 
	private boolean done;
	
	public AppTask(String name, Difficulty difficulty, Type typ, boolean done) {
		this.name = name;
		this.difficulty = difficulty;
		this.typ = typ;
		this.done = done;
	}
	
	public boolean valid() {
		if(name.equals("") || name.length() > 20) return false;
		if(difficulty == null) return false;
		if(typ == null) return false;
		return true;
	}
}
