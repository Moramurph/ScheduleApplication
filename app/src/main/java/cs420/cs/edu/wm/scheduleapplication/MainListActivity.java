package cs420.cs.edu.wm.scheduleapplication;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

public class MainListActivity extends AppCompatActivity {

    private final String TAG = "MainListActivity";
    private ListView listView;
    private ArrayList<String> listItems = new ArrayList<String>();
    private ArrayList<Event> listEvents = new ArrayList<Event>();
    private ArrayAdapter<String> adapter;
    private final int REQUEST = 42;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_list);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        listView = findViewById(R.id.event_list);
        adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, listItems);
        listView.setAdapter(adapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {
                Toast.makeText(getApplicationContext(),
                        "Click ListItem Number " + position, Toast.LENGTH_LONG)
                        .show();

                Log.v(TAG, listEvents.get(position).getInformation());

                Intent i = new Intent(getApplicationContext(), EventInformationActivity.class);
                i.putExtra("info", listEvents.get(position).getInformation());
                i.putExtra("image", listEvents.get(position).getImage());

                startActivity(i);
            }
        });
        ///////////////////////////////////////////////////////////////////////

        FloatingActionButton fab = findViewById(R.id.create_button);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), EventCreationActivity.class);
                startActivityForResult(intent, REQUEST);

                Log.v(TAG, "Create Event button clicked, switching to Creation Page...");
                Context context = getApplicationContext();
                CharSequence text = "Create Event button clicked, switching to Creation Page...";
                int duration = Toast.LENGTH_SHORT;
                Toast.makeText(context, text, duration).show();
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        // Check which request we're responding to
        if (requestCode == REQUEST) {
            // Make sure the request was successful
            if (resultCode == RESULT_OK) {
                Event e = new Event(data.getExtras().getString("title"),
                        data.getExtras().getString("date"),
                        data.getExtras().getString("description"),
                        data.getExtras().getString("url"),
                        data.getExtras().getString("image"));
                e.initialize();
                listEvents.add(e);
                adapter.add(e.getTitle());
            }
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main_list, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private void onDelete( ) {
        assert true;
    }
}
