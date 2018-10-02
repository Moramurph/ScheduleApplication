package cs420.cs.edu.wm.scheduleapplication;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class WelcomeActivity extends AppCompatActivity {

    private Button continueButton;
    private final String TAG = "WelcomeActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);

        continueButton = findViewById(R.id.continue_button);
        setupContinueButton();
    }


    private void setupContinueButton() {
        continueButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), MainListActivity.class);
                startActivity(intent);

                Log.v(TAG, "Continue button clicked, switching to Main List Page...");
                Context context = getApplicationContext();
                CharSequence text = "Continue button clicked, switching to Main List Page...";
                int duration = Toast.LENGTH_SHORT;
                Toast.makeText(context, text, duration).show();
            }
        });
    }

}
