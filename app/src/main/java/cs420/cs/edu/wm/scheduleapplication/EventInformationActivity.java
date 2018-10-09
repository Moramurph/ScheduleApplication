package cs420.cs.edu.wm.scheduleapplication;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

public class EventInformationActivity extends AppCompatActivity {

    private TextView informationView;
    private String informationText;
    private String imageFilePath;
    private Bitmap imageBitmap;
    private ImageView informationImage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event_information);

        Bundle b = getIntent().getExtras();
        informationView = findViewById(R.id.event_information_text);
        informationImage = findViewById(R.id.event_information_image);

        if (b.getString("info") != null) {
            informationText = b.getString("info");
            informationView.setText(informationText);
        }

        if (b.getString("image") != null) {
            imageFilePath = b.getString("image");
            imageBitmap = BitmapFactory.decodeFile(imageFilePath);
            informationImage.setImageBitmap(imageBitmap);
        }

    }

    @Override
    public void onBackPressed() {
        finish();
    }
}
