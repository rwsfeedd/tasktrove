package standard;

import java.time.LocalDate;
import java.util.Calendar;

public class CalendarDate {
	public final static int VALID = 0;
	public final static int INVALID_NAME = 1; 
	public final static int INVALID_START_YEAR = 2;
	public final static int INVALID_START_MONTH = 4;
	public final static int INVALID_START_DAY = 8;
	public final static int INVALID_END_YEAR = 16;
	public final static int INVALID_END_MONTH = 32;
	public final static int INVALID_END_DAY = 64;
	public final static int INVALID_START_HOUR = 128;
	public final static int INVALID_START_MINUTE = 256;
	public final static int INVALID_END_HOUR = 512;
	public final static int INVALID_END_MINUTE = 1024;
	
	private String name;
	private int startYear;
	private int startMonth;
	private int startDay;
	private int endYear;
	private int endMonth;
	private int endDay;
	private int startHour;
	private int startMinute;
	private int endHour;
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
	
	public CalendarDate(String name, LocalDate startDate, LocalDate endDate
			, String startTime, String endTime) {
		this.name = name;
		if(startDate == null) {
			this.startDay = -1;
			this.startMonth = -1;
			this.startYear = -1;
		} else {
			this.startDay = startDate.getDayOfMonth();
			this.startMonth = startDate.getMonthValue();
			this.startYear = startDate.getYear();
		}
		if(endDate == null) {
			this.endDay = -1;
			this.endMonth = -1;
			this.endYear = -1;
		} else {
			this.endDay = endDate.getDayOfMonth();
			this.endMonth = endDate.getMonthValue();
			this.endYear = endDate.getYear();
		}
		if(startTime.equals(null) || startTime.equals("")) {
			this.startHour = -1;
			this.startMinute = -1;
		}else {
			String[] start = startTime.split(":");
			if(start.length != 2) {
				this.startHour = -1;
				this.startMinute = -1;
			} else {
				try {
					this.startHour = Integer.valueOf(start[0]);
					this.startMinute = Integer.valueOf(start[1]);
				}catch(NumberFormatException nfex) {
					this.startHour = -1;
					this.startMinute = -1;
				}
			}
		}//if-else StartTime
		if(endTime.equals(null) || endTime.equals("")) {
			this.endHour = -1;
			this.endMinute = -1;
		}else {
			String[] end = endTime.split(":");
			if(end.length != 2) {
				this.endHour = -1;
				this.endMinute = -1;
			} else {
				try {
					this.endHour = Integer.valueOf(end[0]);
					this.endMinute = Integer.valueOf(end[1]);
				}catch(NumberFormatException nfex) {
					this.endHour = -1;
					this.endMinute = -1;
				}
			}
		}//if-else EndTime
	}//constructor
	
	public CalendarDate(String name, int startDay, int endDay, int startMonth
			, int endMonth, int startYear, int endYear, int startHour
			, int endHour, int startMinute, int endMinute){
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
		int erg = 0;
		if(name.equals(null) || name.equals("")) erg += INVALID_NAME;
		if(startYear < 0 || endYear > 10000) erg += INVALID_START_YEAR;
		if(startMonth < Calendar.JANUARY || startMonth > Calendar.DECEMBER) erg += INVALID_START_MONTH;
		if(startDay < 0 || endDay > 31) erg += INVALID_START_DAY;
		if(endYear < 0 || endYear > 10000) erg += INVALID_END_YEAR;
		if(endMonth < Calendar.JANUARY || endMonth > Calendar.DECEMBER) erg += INVALID_END_MONTH;
		if(endDay < 0 || endDay > 31) erg += INVALID_END_DAY;
		if(startHour < 0 || startHour > 23) erg += INVALID_START_HOUR;
		if(startMinute < 0 || startMinute > 59) erg += INVALID_START_MINUTE;
		if(endHour < 0 || endHour > 23) erg += INVALID_END_HOUR;
		if(endMinute < 0 || endMinute > 59) erg += INVALID_END_MINUTE;
		return erg;
	}
	
	public static CalendarDate getTestObject(){
		CalendarDate date = new CalendarDate();
		date.name = "TestObjekt";
		date.startDay = 1;
		date.endDay = 2;
		date.startMonth = Calendar.JANUARY;
		date.endMonth = Calendar.FEBRUARY;
		date.startYear = 2024;
		date.endYear = 2024;
		date.startHour = 3;
		date.endHour = 7;
		date.startMinute = 10;
		date.endMinute = 48;
		return date;

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
