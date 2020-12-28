package academy.learnprogramming;

public class Appointment {

    public String name;
    public String type;
    public String monthDate;
    public int dayDate;
    public int yearDate;
    public int hourTime;
    public int minuteTime;
    public String AMorPMTime;

    public Appointment(String passedAppointmentName, String passedAppointType,
                       String passedAppointmentDateMonth, int passedAppointmentDateDay, int passedAppointmentDateYear,
                       int passedAppointmentTimeHour, int passedAppointmentTimeMinute, String passedAppointmentTimeAMorPM){
        name = passedAppointmentName;
        type = passedAppointType;
        monthDate = passedAppointmentDateMonth;
        dayDate = passedAppointmentDateDay;
        yearDate = passedAppointmentDateYear;
        hourTime = passedAppointmentTimeHour;
        minuteTime = passedAppointmentTimeMinute;
        AMorPMTime = passedAppointmentTimeAMorPM;
    }

}
