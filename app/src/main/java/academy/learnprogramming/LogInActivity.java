package academy.learnprogramming;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class LogInActivity extends AppCompatActivity {
    private EditText email;
    private EditText password;
    private ProgressBar progressBar;

    private FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_log_in);

        email = (EditText) findViewById(R.id.editTextEmailSignIn);
        password = (EditText) findViewById(R.id.editTextPasswordSignIn);
        progressBar = (ProgressBar) findViewById(R.id.progressBarSignIn);

        firebaseAuth = FirebaseAuth.getInstance();
    }

    @Override
    protected void onStart() {
        super.onStart();

        // if user is logged In, go to signed in screen
        if (firebaseAuth.getCurrentUser() != null) {
            //startActivity(new Intent(this, SignedInActivity.class));
            finish();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        progressBar.setVisibility(View.GONE);
    }

    public void signInForm(View view) {
        String emailInput = email.getText().toString().trim();
        String passwordInput = password.getText().toString().trim();

        if (emailInput.isEmpty()) {
            showToast("email is empty");
            return;
        }

        if (passwordInput.isEmpty()) {
            showToast("password is empty");
            return;
        }

        progressBar.setVisibility(View.VISIBLE);

        // authenticate user
        firebaseAuth.signInWithEmailAndPassword(emailInput, passwordInput)
                .addOnCompleteListener(LogInActivity.this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (!task.isSuccessful()) {
                            showToast("Authentication not successful");
                            Log.e("Authentication errors", task.getException().toString());
                        } else {
                            //startActivity(new Intent(LogInActivity.this, SignedInActivity.class));
                            finish();
                        }
                    }
                });
    }

    private void showToast(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }
}