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
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.FileProvider;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class EventCreationActivity extends AppCompatActivity {

    private Button submitButton;
    private Button cameraButton;
    private ImageButton recordButton;
    private ImageButton playButton;

    private MediaRecorder mRecorder;
    private MediaPlayer mPlayer;
    private boolean mRecording = true;
    private boolean mPlaying = true;

    private EditText titleInput;
    private EditText dateInput;
    private EditText descriptionInput;
    private EditText urlInput;
    private TextView recordingText;
    private TextView playingText;
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
        if (permissionToRecordAccepted ) {
            onRecord(mRecording);
            changeRecordingState();
        }

    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event_creation);

        titleInput = findViewById(R.id.title_input);
        dateInput = findViewById(R.id.date_input);
        descriptionInput = findViewById(R.id.description_input);
        urlInput = findViewById(R.id.url_input);
        recordButton = findViewById(R.id.record_button);
        setupRecordButton();
        recordingText = findViewById(R.id.recording_text);
        playingText = findViewById(R.id.playing_text);

        playButton = findViewById(R.id.play_button);
        setupPlayButton();

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
                intent.putExtra("audio", audioFilePath);

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

    private void setupRecordButton() {
        recordButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                ActivityCompat.requestPermissions(EventCreationActivity.this, permissions, REQUEST_RECORD_AUDIO_PERMISSION);
            }
        });
    }

    private void setupPlayButton() {
        playButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onPlay(mPlaying);
                if (mPlaying) {
                    playingText.setText("PLAYING...");
                } else {
                    playingText.setText("");
                }
                mPlaying = !mPlaying;
            }
        });
    }

    private void onRecord(boolean record) {
        if (record) {
            startRecording();
        } else {
            stopRecording();
        }

    }

    private void changeRecordingState() {
        if (mRecording) {
            recordingText.setText("RECORDING...");
        } else {
            recordingText.setText("");
        }
        mRecording = !mRecording;
    }

    private void onPlay(boolean start) {
        if (start) {
            startPlaying();
        } else {
            stopPlaying();
        }
    }

    private void startPlaying() {
        mPlayer = new MediaPlayer();
        try {
            mPlayer.setDataSource(audioFilePath);
            mPlayer.prepare();
            mPlayer.start();
        } catch (IOException e) {
            Log.e(TAG, "prepare() failed");
        }
    }

    private void stopPlaying() {
        mPlayer.release();
        mPlayer = null;
    }

    private void startRecording() {
        try {
            createAudioFile();
        } catch (IOException ex) {
            Log.e(TAG, "Create file failed");
        }

        if (audioFilePath != null) {
            mRecorder = new MediaRecorder();
            mRecorder.setAudioSource(MediaRecorder.AudioSource.MIC);
            mRecorder.setOutputFormat(MediaRecorder.OutputFormat.THREE_GPP);
            mRecorder.setOutputFile(audioFilePath);
            mRecorder.setAudioEncoder(MediaRecorder.AudioEncoder.AMR_NB);
        }

        try {
            mRecorder.prepare();
        } catch (IOException e) {
            Log.e(TAG, "prepare() failed");
        }

        mRecorder.start();
    }

    private void stopRecording() {
        mRecorder.stop();
        mRecorder.release();
        mRecorder = null;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        Bitmap imageBitmap;

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
                Log.e(TAG, "Create file failed");
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

    private File createAudioFile() throws IOException {
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String audioFileName = "3GP_" + timeStamp + "_";
        File storageDir = getExternalFilesDir(Environment.DIRECTORY_DOCUMENTS);
        File audio = File.createTempFile(
                audioFileName,  // prefix
                ".3gp",         // suffix
                storageDir      // directory
        );
        audioFilePath = audio.getAbsolutePath();
        return audio;
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }

}
