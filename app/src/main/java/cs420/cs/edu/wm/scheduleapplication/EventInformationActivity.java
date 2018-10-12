package cs420.cs.edu.wm.scheduleapplication;

import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class EventInformationActivity extends AppCompatActivity {

    private TextView informationView;
    private String informationText;
    private String imageFilePath;
    private String urlLink;
    private Bitmap imageBitmap;
    private ImageView informationImage;
    private Button urlButton;

    private final String TAG = "EventInformationActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event_information);

        Bundle b = getIntent().getExtras();
        informationView = findViewById(R.id.event_information_text);
        informationImage = findViewById(R.id.event_information_image);
        urlButton = findViewById(R.id.event_information_url_button);
        setupURLButton();

        if (b.getString("info") != null) {
            informationText = b.getString("info");
            informationView.setText(informationText);
        }

        if (b.getString("image") != null) {
            imageFilePath = b.getString("image");
            imageBitmap = BitmapFactory.decodeFile(imageFilePath);
            informationImage.setImageBitmap(imageBitmap);
        }

        if (b.getString("url") != null) {
            urlLink = b.getString("url");
            setupURLButton();
        }

    }

    private void setupURLButton() {
        urlButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(urlLink));
                intent.setPackage("com.android.chrome");

                try {
                    startActivity(intent);
                } catch (ActivityNotFoundException ex) {
                    // Chrome browser presumably not installed so allow user to choose instead
                    intent.setPackage(null);
                    startActivity(intent);
                }

                Log.v(TAG, "URL button clicked, switching to webpage...");
                Context context = getApplicationContext();
                CharSequence text = "URL button clicked, switching to webpage...";
                int duration = Toast.LENGTH_SHORT;
                Toast.makeText(context, text, duration).show();
            }
        });
    }

    @Override
    public void onBackPressed() {
        finish();
    }
}
