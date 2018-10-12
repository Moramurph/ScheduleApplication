package cs420.cs.edu.wm.scheduleapplication;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.MediaPlayer;
import android.media.MediaRecorder;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v4.content.FileProvider;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class EventCreationActivity extends AppCompatActivity {

    private Button submitButton;
    private Button cameraButton;
    private ImageButton micRecord;
    private ImageButton micPlay;
    private ImageButton micPause;

    private MediaRecorder mRecorder;
    private MediaPlayer mPlayer;

    private EditText titleInput;
    private EditText dateInput;
    private EditText descriptionInput;
    private EditText urlInput;

    private Bitmap imageBitmap;
    private ImageView pictureImage;

    private String title;
    private String date;
    private String description;
    private String url;
    private static String imageFilePath = null;
    private static String audioFilePath = null;

    private final String TAG = "EventCreation";
    static final int REQUEST_IMAGE_CAPTURE = 1;
    private static final int REQUEST_RECORD_AUDIO_PERMISSION = 200;

    // Requesting permission to RECORD_AUDIO
    private boolean permissionToRecordAccepted = false;
    private String [] permissions = {Manifest.permission.RECORD_AUDIO};

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode){
            case REQUEST_RECORD_AUDIO_PERMISSION:
                permissionToRecordAccepted  = grantResults[0] == PackageManager.PERMISSION_GRANTED;
                break;
        }
        if (!permissionToRecordAccepted ) finish();

    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event_creation);

        titleInput = findViewById(R.id.title_input);
        dateInput = findViewById(R.id.date_input);
        descriptionInput = findViewById(R.id.description_input);
        urlInput = findViewById(R.id.url_input);
        micRecord = findViewById(R.id.record_button);

        submitButton = findViewById(R.id.submit_button);
        setupSubmitButton();
        pictureImage = findViewById(R.id.picture_preview);
        cameraButton = findViewById(R.id.camera_button);
        setupCameraButton();

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
                intent.putExtra("image", imageFilePath);

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

    private void setupCameraButton() {
        cameraButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                createCameraIntent();
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK) {
            imageBitmap = BitmapFactory.decodeFile(imageFilePath);
            pictureImage.setImageBitmap(imageBitmap);
        }
    }

    private void getInputs() {
        title = titleInput.getText().toString();
        description = descriptionInput.getText().toString();
        date = dateInput.getText().toString();
        url = urlInput.getText().toString();

    }

    private void createCameraIntent() {
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (intent.resolveActivity(getPackageManager()) != null) {
            // Create the File where the photo should go

            File photoFile = null;
            try {
                photoFile = createImageFile();
            } catch (IOException ex) {
                // Error occurred while creating the File

            }
            // Continue only if the File was successfully created
            if (photoFile != null) {
                Uri photoURI = FileProvider.getUriForFile(this,
                        "cs420.cs.edu.wm.scheduleapplication.android.fileprovider",
                        photoFile);
                intent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI);
                startActivityForResult(intent, REQUEST_IMAGE_CAPTURE);
            }

        }
    }


    private File createImageFile() throws IOException {
        // Create an image file name
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String imageFileName = "JPEG_" + timeStamp + "_";
        File storageDir = getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        File image = File.createTempFile(
                imageFileName,  // prefix
                ".jpg",         // suffix
                storageDir      // directory
        );

        // Save a file: path for use with ACTION_VIEW intents
        imageFilePath = image.getAbsolutePath();
        return image;
    }




    private void onDelete( ) {
        assert true;
    }
}
