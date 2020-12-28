package academy.learnprogramming;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

public class MainActivity extends AppCompatActivity implements academy.learnprogramming.MainFragment.OnItemSelectedListener, academy.learnprogramming.AddAppointmentFragment.OnItemSelectedListener {

    academy.learnprogramming.MainFragment myFragment;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        FragmentManager fm = getSupportFragmentManager();
        myFragment = new academy.learnprogramming.MainFragment();

        // add
        FragmentTransaction ft = fm.beginTransaction();
        ft.add(R.id.myContainer, myFragment);
        ft.commit();
    }

    public void onAddAppointmentSelected(academy.learnprogramming.Appointment appt)
    {
        FragmentManager fragmentManager = getSupportFragmentManager();

        myFragment.updateAppointmentList(appt);

        FragmentTransaction ft = fragmentManager.beginTransaction();
        ft.replace(R.id.myContainer, myFragment);
        ft.commit();
    }

    public void onCancel()
    {
        FragmentManager fragmentManager = getSupportFragmentManager();

        FragmentTransaction ft = fragmentManager.beginTransaction();
        ft.replace(R.id.myContainer, myFragment);
        ft.commit();
    }


    @Override
    public void onButtonSelected() {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction ft = fragmentManager.beginTransaction();
        ft.replace(R.id.myContainer, new academy.learnprogramming.AddAppointmentFragment());
        ft.commit();
    }
}
