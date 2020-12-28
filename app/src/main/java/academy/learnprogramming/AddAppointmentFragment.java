package academy.learnprogramming;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;

import java.util.Calendar;

public class AddAppointmentFragment extends Fragment {

    TextView txtDate;
    TextView txtTime;

    private int year;
    private int month;
    private int day;

    private int hour;
    private int minute;

    private AddAppointmentFragment.OnItemSelectedListener listener;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_add_appointment, container, false);

        Button cancel = (Button)view.findViewById(R.id.btnCancel);
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                listener.onCancel();
            }
        });

        Button addTask = (Button)view.findViewById(R.id.btnAddTask);
        addTask.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                EditText editAppointmentName = (EditText) getActivity().findViewById(R.id.editTaskName);
                Spinner spinnerAppointmentType = (Spinner) getActivity().findViewById(R.id.spnTaskType);
                if(!(editAppointmentName.getText().toString()).isEmpty()){

                    academy.learnprogramming.Appointment app = new academy.learnprogramming.Appointment(editAppointmentName.getText().toString(), spinnerAppointmentType.getSelectedItem().toString(),
                            DisplayTheMonthInCharacters(month), day, year, FormatTheHour(hour), minute, AMorPM(hour));

                    listener.onAddAppointmentSelected(app);
                }
                else {
                    Toast toast = Toast.makeText(getActivity(), "Please enter an Appointment Name", Toast.LENGTH_SHORT);
                    toast.show();
                }
            }
        });

        return view;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof AddAppointmentFragment.OnItemSelectedListener) {
            listener = (AddAppointmentFragment.OnItemSelectedListener) context;
        } else {
            throw new ClassCastException(context.toString()
                    + " must implemenet MyListFragment.OnItemSelectedListener");
        }
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        Bundle bundle = getArguments();
        if (bundle != null) {
        }

        setCurrentDateAndTimeOnView();

        TextView time = (TextView) getActivity().findViewById(R.id.txtvTime);
        time.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showTimePicker();
            }
        });

        TextView date = (TextView) getActivity().findViewById(R.id.txttvDate);
        date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDatePicker();
            }
        });
    }

    public interface OnItemSelectedListener {
        public void onAddAppointmentSelected(academy.learnprogramming.Appointment appt);
        public void onCancel();
    }


    // display current date and time
    public void setCurrentDateAndTimeOnView() {
        txtDate = (TextView)getActivity().findViewById(R.id.txttvDate);
        txtTime = (TextView)getActivity().findViewById(R.id.txtvTime);

        final Calendar c = Calendar.getInstance();
        year = c.get(Calendar.YEAR);
        month = c.get(Calendar.MONTH);
        day = c.get(Calendar.DAY_OF_MONTH);

        hour = c.get(Calendar.HOUR_OF_DAY);
        minute = c.get(Calendar.MINUTE);

        // set current time into textview
        txtTime.setText(new StringBuilder().append(pad(hour))
                .append(":").append(pad(minute)));

        // set current date into textview
        txtDate.setText(new StringBuilder()
                // Month is 0 based, just add 1
                .append(month + 1).append("-").append(day).append("-")
                .append(year).append(" "));
    }

    private void showDatePicker() {
        DatePickerFragment date = new DatePickerFragment();

        Bundle args = new Bundle();
        args.putInt("year", year);
        args.putInt("month", month);
        args.putInt("day", day);
        date.setArguments(args);

        date.setCallBack(ondate);
        date.show(getFragmentManager(), "Date Picker");
    }

    DatePickerDialog.OnDateSetListener ondate = new DatePickerDialog.OnDateSetListener() {

        public void onDateSet(DatePicker view, int selectedYear, int selectedMonth,
                              int selectedDay) {

            year = selectedYear;
            month = selectedMonth;
            day = selectedDay;

            txtDate.setText(new StringBuilder().append(month + 1)
                    .append("-").append(day).append("-").append(year)
                    .append(" "));
        }
    };

    private void showTimePicker() {
        TimePickerFragment time = new TimePickerFragment();

        Bundle args = new Bundle();
        args.putInt("hour", hour);
        args.putInt("minute", month);
        time.setArguments(args);

        time.setCallBack(onTime);
        time.show(getFragmentManager(), "Time Picker");
    }

    TimePickerDialog.OnTimeSetListener onTime = new TimePickerDialog.OnTimeSetListener() {

        public void onTimeSet(TimePicker view, int selectedHour, int selectedMinute) {
            hour = selectedHour;
            minute = selectedMinute;

            // set current time into textview
            txtTime.setText(new StringBuilder().append(pad(hour))
                    .append(":").append(pad(minute)));
        }
    };

 /* Helper Methods */

    //Returns the Month Abbreviation for the corresponding number.
    private String DisplayTheMonthInCharacters(int passedMonth){
        switch(passedMonth){
            case 0:
                return "Jan";
            case 1:
                return"Feb";
            case 2:
                return"Mar";
            case 3:
                return"Apr";
            case 4:
                return"May";
            case 5:
                return"Jun";
            case 6:
                return"Jul";
            case 7:
                return"Aug";
            case 8:
                return"Sept";
            case 9:
                return"Oct";
            case 10:
                return"Nov";
            case 11:
                return"Dec";

        }
        return "";
    }

    //Converts the 24 hours PassedHour to a 12 Hour time.
    private int FormatTheHour(int passedHour){
        if (passedHour > 12){ passedHour -= 12; }
        return passedHour;
    }

    //Returns AM or PM depending on the hour (1-24)
    private String AMorPM(int passedHour){
        if (passedHour > 12){ return "PM"; }
        else{ return "AM"; }
    }


    private String pad(int c) {
        if (c >= 10)
            return String.valueOf(c);
        else
            return "0" + String.valueOf(c);
    }

    public static class DatePickerFragment extends DialogFragment {
        DatePickerDialog.OnDateSetListener ondateSet;
        private int year, month, day;

        public DatePickerFragment() {}

        public void setCallBack(DatePickerDialog.OnDateSetListener ondate) {
            ondateSet = ondate;
        }

        @Override
        public void setArguments(Bundle args) {
            super.setArguments(args);
            year = args.getInt("year");
            month = args.getInt("month");
            day = args.getInt("day");
        }

        @Override
        public Dialog onCreateDialog(Bundle savedInstanceState) {
            return new DatePickerDialog(getActivity(), ondateSet, year, month, day);
        }
    }

    public static class TimePickerFragment extends DialogFragment {
        TimePickerDialog.OnTimeSetListener onTimeSet;
        private int hour, minute;

        public TimePickerFragment() {}

        public void setCallBack(TimePickerDialog.OnTimeSetListener ontime) {
            onTimeSet = ontime;
        }

        @Override
        public void setArguments(Bundle args) {
            super.setArguments(args);
            hour = args.getInt("hour");
            minute = args.getInt("minute");
        }

        @Override
        public Dialog onCreateDialog(Bundle savedInstanceState) {
            return new TimePickerDialog(getActivity(), onTimeSet, hour, minute, false);
        }
    }
}
