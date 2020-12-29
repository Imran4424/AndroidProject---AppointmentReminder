package academy.learnprogramming;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class RegisterActivity extends AppCompatActivity {
    private EditText name;
    private EditText email;
    private EditText password;
    private EditText repeatPassword;
    private ProgressBar progressBar;

    private FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        name = (EditText) findViewById(R.id.editTextNameSignUp);
        email = (EditText) findViewById(R.id.editTextEmailSignUp);
        password = (EditText) findViewById(R.id.editTextPasswordSignUp);
        repeatPassword = (EditText) findViewById(R.id.editTextRePasswordSignUp);
        progressBar = (ProgressBar) findViewById(R.id.progressBarSignUp);

        firebaseAuth = FirebaseAuth.getInstance();
    }

    @Override
    protected void onResume() {
        super.onResume();
        progressBar.setVisibility(View.GONE);
    }

    public void registerUser(View view) {
        String nameInput = name.getText().toString().trim();
        String emailInput = email.getText().toString().trim();
        String passwordInput = password.getText().toString().trim();
        String repeatPasswordInput = repeatPassword.getText().toString().trim();

        if (TextUtils.isEmpty(nameInput)) {
            showToast("User name is empty");
            return;
        }

        if (TextUtils.isEmpty(emailInput)) {
            showToast("Email is empty");
            return;
        }

        if (passwordInput.isEmpty()) {
            showToast("Password is empty");
            return;
        }

        if (repeatPasswordInput.isEmpty()) {
            showToast("Repeat password is empty");
            return;
        }

        if (!passwordInput.equals(repeatPasswordInput)) {
            showToast("passwords not matched");
            return;
        }

        if (passwordInput.length() < 8) {
            showToast("Password is too short");
            return;
        }

        progressBar.setVisibility(View.VISIBLE);

        // create user
        firebaseAuth.createUserWithEmailAndPassword(emailInput, passwordInput)
                .addOnCompleteListener(RegisterActivity.this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        showToast("User created with Email");
                        progressBar.setVisibility(View.GONE);

                        // if sign in fails, display a message to the user
                        // if sign in succeeds, the auth state listener will be notified and logic to handle
                        // signed in user can be handled in the listener
                        if (!task.isSuccessful()) {
                            showToast("Authentication Failed");
                            Log.e("Authentication errors", task.getException().toString());
                        } else {
                            startActivity(new Intent(RegisterActivity.this, MainActivity.class));
                            finish();
                        }
                    }
                });
    }

    private void showToast(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }

    public void onLogInClick(View view) {
        startActivity(new Intent(this, LogInActivity.class));
    }
}