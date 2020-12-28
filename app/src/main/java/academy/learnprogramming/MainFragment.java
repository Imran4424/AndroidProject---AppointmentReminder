package academy.learnprogramming;

import android.content.Context;
import android.os.Bundle;
import androidx.fragment.app.Fragment;

import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Objects;

public class MainFragment extends Fragment {


    public ArrayList<academy.learnprogramming.Appointment> appointmentArrayList = new ArrayList<academy.learnprogramming.Appointment>();

    private OnItemSelectedListener listener;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                  Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_main, container, false);

        Button b = (Button)view.findViewById(R.id.button);
        b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                listener.onButtonSelected();
            }
        });
        return view;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnItemSelectedListener) {
            listener = (OnItemSelectedListener) context;
        } else {
            throw new ClassCastException(context.toString()
                    + " must implemenet MyListFragment.OnItemSelectedListener");
        }
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        populateAppointments();
    }

    public interface OnItemSelectedListener {
        public void onButtonSelected();
    }

    public void updateAppointmentList(academy.learnprogramming.Appointment appt)
    {
        appointmentArrayList.add(appt);
    }

    private void populateAppointments() {

        for(int i = 0; i < appointmentArrayList.size(); i++){
            PopulateTable(i);
        }
    }

    private String SetToDateAndTime(academy.learnprogramming.Appointment appointment){
        long currentDateAndTime = System.currentTimeMillis(); //Todays Date
        SimpleDateFormat formatDate = new SimpleDateFormat("MMM d, yyyy"); //Date Format

        String todaysDate = formatDate.format(currentDateAndTime); //Today's date formated
        String passDate = appointment.monthDate +" " + appointment.dayDate +", " + appointment.yearDate; //Tasks date formated the same way

        if(Objects.equals(todaysDate, passDate)){ //Compare today's date and passed date, return time if dates match
            return appointment.hourTime +":" +appointment.minuteTime +" " +appointment.AMorPMTime;
        }
        return appointment.monthDate +" " + appointment.dayDate +", " + appointment.yearDate; //Otherwise, return the date

    }

    private void PopulateTable(int arrayListCounter) {
        TableLayout appointmentTBL = (TableLayout) getActivity().findViewById(R.id.tblTaskContent);

        TableRow newTableRow = new TableRow(getActivity());
        TableRow.LayoutParams lp = new TableRow.LayoutParams(TableRow.LayoutParams.MATCH_PARENT, TableRow.LayoutParams.WRAP_CONTENT);
        newTableRow.setLayoutParams(lp);

        TextView txtvName = new TextView(getActivity());
        txtvName.setLayoutParams(lp);
        txtvName.setGravity(Gravity.CENTER);
        txtvName.setText(appointmentArrayList.get(arrayListCounter).name);
        txtvName.setWidth(140);
        txtvName.setTextSize(12);
        txtvName.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);

        TextView txtvType = new TextView(getActivity());
        txtvType.setLayoutParams(lp);
        txtvType.setGravity(Gravity.CENTER);
        txtvType.setText(appointmentArrayList.get(arrayListCounter).type);
        txtvType.setWidth(93);
        txtvType.setTextSize(12);
        txtvType.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);

        TextView txtvDate = new TextView(getActivity()

        );
        txtvDate.setLayoutParams(lp);
        txtvDate.setGravity(Gravity.CENTER);
        txtvDate.setText(SetToDateAndTime(appointmentArrayList.get(arrayListCounter)));
        txtvDate.setWidth(97);
        txtvDate.setTextSize(12);
        txtvDate.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);

        newTableRow.addView(txtvName);
        newTableRow.addView(txtvType);
        newTableRow.addView(txtvDate);
        appointmentTBL.addView(newTableRow,arrayListCounter+1);
    }
}
