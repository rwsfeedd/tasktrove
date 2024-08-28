package standard;

import java.time.LocalDate;
import java.time.Month;
import java.util.Calendar;

public class CalendarDate {
	public final static int VALID = 0;
	public final static int INVALID_NAME = 1; 
	public final static int INVALID_START_DATE = 2;
	public final static int INVALID_END_DATE = 16;
	public final static int INVALID_START_HOUR = 32;
	public final static int INVALID_START_MINUTE = 64;
	public final static int INVALID_END_HOUR = 128;
	public final static int INVALID_END_MINUTE = 256;
	public final static int INVALID_DATE_LENGTH = 512;
	
	private String name;
	private LocalDate startDate;
	private LocalDate endDate;
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
		this.startDate = null;
		this.endDate = null;
		this.startHour = -1;
		this.endHour = -1;
		this.startMinute = -1;
		this.endMinute = -1;
	}
	
	public CalendarDate(String name, LocalDate startDate, LocalDate endDate
			, String startTime, String endTime) {
		this.name = name;
		if(startDate == null) {
			this.startDate = null;
		} else {
			this.startDate = startDate;
		}
		if(endDate == null) {
			this.endDate = null;
		} else {
			this.endDate = endDate;
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
	
	/*
	 * @return 	returns int Every Invalid Argument of this.CalendarDate is saved in one Bit
	 */
	public int validate() {
		int erg = 0;
		if(name.equals(null) || name.equals("") || name.length() > 20) erg += INVALID_NAME;
		if(startDate == null) erg += INVALID_START_DATE;
		if(endDate == null) erg += INVALID_END_DATE;
		if(startDate.isAfter(endDate)) erg += INVALID_DATE_LENGTH;
		if(startHour < 0 || startHour > 23) erg += INVALID_START_HOUR;
		if(startMinute < 0 || startMinute > 59) erg += INVALID_START_MINUTE;
		if(endHour < 0 || endHour > 23) erg += INVALID_END_HOUR;
		if(endMinute < 0 || endMinute > 59) erg += INVALID_END_MINUTE;
		return erg;
	}
	
	public int compareTo(CalendarDate calendarDate) {
		if(this.getStartDate().compareTo(calendarDate.getStartDate()) < 0)  return -1;
		if(this.getStartDate().compareTo(calendarDate.getStartDate()) > 0)  return 1;
		
		if(this.getEndDate().compareTo(calendarDate.getEndDate()) < 0)  return -1;
		if(this.getEndDate().compareTo(calendarDate.getEndDate()) > 0)  return 1;
		
		if(this.getStartHour() < calendarDate.getStartHour()) return -1;
		if(this.getStartHour() > calendarDate.getStartHour()) return 1;
		
		if(this.getStartMinute() < calendarDate.getStartMinute()) return -1;
		if(this.getStartMinute() > calendarDate.getStartMinute()) return 1;
		
		return 0;
	}

	public static Month parseMonthCalendarToEnum(int month) {
		switch(month) {
			case Calendar.JANUARY: return Month.JANUARY;
			case Calendar.FEBRUARY: return Month.FEBRUARY;
			case Calendar.MARCH: return Month.MARCH;
			case Calendar.APRIL: return Month.APRIL;
			case Calendar.MAY: return Month.MAY;
			case Calendar.JUNE: return Month.JUNE;
			case Calendar.JULY: return Month.JULY;
			case Calendar.AUGUST: return Month.AUGUST;
			case Calendar.SEPTEMBER: return Month.SEPTEMBER;
			case Calendar.OCTOBER: return Month.OCTOBER;
			case Calendar.NOVEMBER: return Month.NOVEMBER;
			case Calendar.DECEMBER: return Month.DECEMBER; 
			default: return null;
		}
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setStartDate(LocalDate startDate) {
		this.startDate = startDate;
	}
	
	public void setEndDate(LocalDate endDate) {
		this.endDate = endDate;
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

	public LocalDate getStartDate() {
		return startDate;
	}

	public LocalDate getEndDate() {
		return endDate;
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
