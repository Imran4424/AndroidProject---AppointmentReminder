package academy.learnprogramming;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

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

    public void onLogOut(View view) {
        FirebaseAuth.getInstance().signOut();
        Toast.makeText(this, "Signing Out", Toast.LENGTH_SHORT).show();
        startActivity(new Intent(this, LogInActivity.class));
    }
}
