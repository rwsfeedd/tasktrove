package standard;

public class CalendarDate {
	public final static int VALID = 0;
	public final static int INVALID_NAME = 1; 
	public final static int INVALID_START_DAY = 2;
	public final static int INVALID_END_DAY = 3;
	public final static int INVALID_START_MONTH = 4;
	public final static int INVALID_END_MONTH = 5;
	public final static int INVALID_START_YEAR = 6;
	public final static int INVALID_END_YEAR = 7;
	public final static int INVALID_START_HOUR = 8;
	public final static int INVALID_END_HOUR = 9;
	public final static int INVALID_START_MINUTE = 10;
	public final static int INVALID_END_MINUTE = 11;
	
	private String name;
	private int startDay;
	private int endDay;
	private int startMonth;
	private int endMonth;
	private int startYear;
	private int endYear;
	private int startHour;
	private int endHour;
	private int startMinute;
	private int endMinute;
	
	/**
	 * 
	 * @param name Name
	 * @param startDay ersterTag
	 * @param endDay letzterTag
	 * @param startMonth erster Monat
	 * @param endMonth letzter Monat
	 * @param startYear erstes Jahr
	 * @param endYear letztes Jahr
	 * @param startHour erste Stunde
	 * @param endHour letzte Stunde
	 * @param startMinute erste Minute
	 * @param endMinute letzte Minute
	 */
	
	public CalendarDate() {
		this.name = null;
		this.startDay = -1;
		this.endDay = -1;
		this.startMonth = -1;
		this.endMonth = -1;
		this.startYear = -1;
		this.endYear = -1;
		this.startHour = -1;
		this.endHour = -1;
		this.startMinute = -1;
		this.endMinute = -1;
	}
	
	public CalendarDate(String name, int startDay, int endDay, int startMonth, int endMonth, int startYear, int endYear, int startHour, int endHour, int startMinute, int endMinute){
		this.name = name;
		this.startDay = startDay;
		this.endDay = endDay;
		this.startMonth = startMonth;
		this.endMonth = endMonth;
		this.startYear = startYear;
		this.endYear = endYear;
		this.startHour = startHour;
		this.endHour = endHour;
		this.startMinute = startMinute;
		this.endMinute = endMinute;
	}
	
	public int validate() {
		if(name.equals(null) || name.equals("")) return INVALID_NAME;
		if(startDay < 0) return INVALID_START_DAY;
		if(endDay < 0) return INVALID_END_DAY;
		if(startMonth < 0) return INVALID_START_MONTH;
		if(endMonth < 0) return INVALID_END_MONTH;
		if(startYear < 0) return INVALID_START_YEAR;
		if(endYear < 0) return INVALID_END_YEAR;
		if(startHour < 0) return INVALID_START_HOUR;
		if(endHour < 0) return INVALID_END_HOUR;
		if(startMinute < 0) return INVALID_START_MINUTE;
		if(endMinute < 0) return INVALID_END_MINUTE;
		return VALID;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setStartDay(int startDay) {
		this.startDay = startDay;
	}

	public void setEndDay(int endDay) {
		this.endDay = endDay;
	}

	public void setStartMonth(int startMonth) {
		this.startMonth = startMonth;
	}

	public void setEndMonth(int endMonth) {
		this.endMonth = endMonth;
	}

	public void setStartYear(int startYear) {
		this.startYear = startYear;
	}

	public void setEndYear(int endYear) {
		this.endYear = endYear;
	}

	public void setStartHour(int startHour) {
		this.startHour = startHour;
	}

	public void setEndHour(int endHour) {
		this.endHour = endHour;
	}

	public void setStartMinute(int startMinute) {
		this.startMinute = startMinute;
	}

	public void setEndMinute(int endMinute) {
		this.endMinute = endMinute;
	}

	public String getName() {
		return name;
	}

	public int getStartDay() {
		return startDay;
	}

	public int getEndDay() {
		return endDay;
	}

	public int getStartMonth() {
		return startMonth;
	}

	public int getEndMonth() {
		return endMonth;
	}

	public int getStartYear() {
		return startYear;
	}

	public int getEndYear() {
		return endYear;
	}

	public int getStartHour() {
		return startHour;
	}

	public int getEndHour() {
		return endHour;
	}

	public int getStartMinute() {
		return startMinute;
	}

	public int getEndMinute() {
		return endMinute;
	}
}
