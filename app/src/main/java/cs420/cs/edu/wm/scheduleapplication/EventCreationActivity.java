package cs420.cs.edu.wm.scheduleapplication;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class EventCreationActivity extends AppCompatActivity {

    private Button submitButton;
    private EditText titleInput;
    private EditText dateInput;
    private EditText descriptionInput;
    private EditText urlInput;
    private String title;
    private String date;
    private String description;
    private String url;
    private final String TAG = "EventCreation";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event_creation);

        titleInput = findViewById(R.id.title_input);
        dateInput = findViewById(R.id.date_input);
        descriptionInput = findViewById(R.id.description_input);
        urlInput = findViewById(R.id.url_input);

        //TODO Make sure that url is using proper https://
        submitButton = findViewById(R.id.submit_button);
        setupSubmitButton();

    }

    private void setupSubmitButton() {
        submitButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), MainListActivity.class);
                getInputs();

                intent.putExtra("title", title);
                intent.putExtra("date", date);
                intent.putExtra("description", description);
                intent.putExtra("url", url);

                setResult(Activity.RESULT_OK, intent);
                finish();

                Log.v(TAG, "Submit button clicked, sending result back...");
                Context context = getApplicationContext();
                CharSequence text = "Submit button clicked, sending result back...";
                int duration = Toast.LENGTH_SHORT;
                Toast.makeText(context, text, duration).show();
            }
        });
    }

    private void getInputs() {
        title = titleInput.getText().toString();
        description = descriptionInput.getText().toString();
        date = dateInput.getText().toString();
        url = urlInput.getText().toString();

    }



    private void onDelete( ) {
        assert true;
    }
}
