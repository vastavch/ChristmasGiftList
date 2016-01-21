package com.example.srivastava.ChristmasGiftsList;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;
import android.widget.Toast;

import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;

import java.util.List;

public class ChristmasListActivity extends AppCompatActivity {
    List<ParseObject> users;
    ListView lv;
/* Calls the AddPerson.class to add a person to the list

 */
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.addPerson) {
            Intent intent = new Intent(ChristmasListActivity.this, AddPerson.class);
            startActivity(intent);
        }
        return true;
    }
/* Inflates the menu

 */
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.christmasmenu, menu);
        return true;


    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_christmas_list);
        lv = (ListView) findViewById(R.id.listView_persons);
        loadData();

    }

    @Override
    protected void onResume() {
        super.onResume();
        loadData();
    }
/* get all the data from persons table stored on parse.com
and sets the data to the adapter.
 */
    public void loadData() {
        ParseQuery<ParseObject> query = ParseQuery.getQuery("Person");

        query.findInBackground(new FindCallback<ParseObject>() {
            @Override
            public void done(List<ParseObject> objects, ParseException e) {
                if (e == null) {
                    if (objects.size() > 0) {
                        PersosnListAdaptor adapter = new PersosnListAdaptor(ChristmasListActivity.this, R.layout.userslayout, objects);
                        lv.setAdapter(adapter);
                    }
                } else {
                    Toast.makeText(ChristmasListActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                }
            }
        });

    }
}