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
	
	public AppTask() {
		this.name = "";
		this.difficulty = null;
		this.typ = null;
		this.done = false;
	}

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

	public Type getTyp() {
		return typ;
	}

	public void setTyp(Type typ) {
		this.typ = typ;
	}

	public boolean isDone() {
		return done;
	}

	public void setDone(boolean done) {
		this.done = done;
	}

}
