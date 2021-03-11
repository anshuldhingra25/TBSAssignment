package in.tbsassignment.activity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AppCompatActivity;
import in.tbsassignment.R;

public class LoginActivity extends AppCompatActivity {
    EditText eTEmail, eTPassword;
    Button btnLogin;
    boolean isEmailValid, isPasswordValid;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        eTEmail = findViewById(R.id.eTEmail);
        eTPassword = findViewById(R.id.eTPassword);
        btnLogin = findViewById(R.id.btnLogin);
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SetValidation();
            }
        });

    }

    public void SetValidation() {
        try {
            if (eTEmail.getText().toString().isEmpty()) {
                showSnackBar("Enter Email Address");
                isEmailValid = false;
            } else if (!Patterns.EMAIL_ADDRESS.matcher(eTEmail.getText().toString()).matches()) {
                showSnackBar("Enter valid Email Address");
                isEmailValid = false;
            } else if (eTPassword.getText().toString().isEmpty()) {
                showSnackBar("Enter Password");
                isPasswordValid = false;
            } else if (eTPassword.getText().length() < 6) {
                showSnackBar("Password length should be more than 6");
                isPasswordValid = false;
            } else {
                isEmailValid = true;
                isPasswordValid = true;
            }

            if (isEmailValid && isPasswordValid) {
                Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                startActivity(intent);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    private void showSnackBar(String message) throws Exception {
        Snackbar snackbar = Snackbar.make(findViewById(android.R.id.content), message, Snackbar.LENGTH_LONG);
        snackbar.show();
    }
}