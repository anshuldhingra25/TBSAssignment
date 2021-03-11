package in.tbsassignment.activity;

import androidx.appcompat.app.AppCompatActivity;
import in.tbsassignment.R;

import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.widget.TextView;

public class SplashActivity extends AppCompatActivity {
    private TextView tvSplash;
    public int counter = 3;// Set the time 3 seconds

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        tvSplash = findViewById(R.id.tvSplash);
        startCountDown();
    }

    private void startCountDown() {
        try {
            new CountDownTimer(3000, 1000) {
                public void onTick(long millisUntilFinished) {
                    tvSplash.setText(String.valueOf(counter));
                    counter--;
                }

                public void onFinish() {
                    tvSplash.setText("FINISH!!");
                    startActivity(new Intent(SplashActivity.this, LoginActivity.class));
                    finish();
                }
            }.start();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
