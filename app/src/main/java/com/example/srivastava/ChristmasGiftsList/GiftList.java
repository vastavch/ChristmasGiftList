package com.example.srivastava.ChristmasGiftsList;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;
import android.widget.Toast;

import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;

import java.util.List;
/*Retrieves the gifts list given to the particular person from GiftsGiven table in Parse.com

 */
public class GiftList extends AppCompatActivity {
ParseObject object;
    ListView lv;
    IndPerson indperson;
    static boolean shudReload=false;
    String person_obj,bal,bud;
    String[] check;
    static boolean hasLeft=true;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gift_list);
        lv=(ListView) findViewById(R.id.listView_gftlst);
        //if(!shudReload)
        indperson= (IndPerson) getIntent().getParcelableExtra("person");
        //else
        //shudReload=false;
        loadGifts();

    }

    @Override
    protected void onResume() {
        super.onResume();
        loadGifts();
    }
/* retrieve the gifts given to a particular person and passes to the adapter.
If budget is reached hasLeft is made false.
 */
    public void loadGifts()
    {
        ParseQuery<ParseObject> query = ParseQuery.getQuery("GiftsGiven");
        query.whereEqualTo("GivenTo", ParseObject.createWithoutData("Person",indperson.getObjectId()));
        if(Integer.parseInt(indperson.getBal())<=0)
            hasLeft=false;
        query.findInBackground(new FindCallback<ParseObject>() {
            @Override
            public void done(List<ParseObject> objects, ParseException e) {
                if(e==null)
                {
                    Toast.makeText(GiftList.this, "no exception", Toast.LENGTH_SHORT).show();
                    if(objects.size()>0)
                    {

                        GivenListAdaptor adapter = new GivenListAdaptor(GiftList.this, R.layout.givengifts,objects);
                        lv.setAdapter(adapter);
                    }
                }
                else
                {

                    Toast.makeText(GiftList.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                }
            }
        });

    }
    /* If hasLeft is true,then user is navigated to AddGift Activity else, a prompt stating "no more budget"  is displayed

     */
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if(id==R.id.plus){
            if(hasLeft) {
                Intent intent = new Intent(GiftList.this, AddGift.class);
                intent.putExtra("indperson",indperson);
                startActivity(intent);
            }
            else
            {
                Toast.makeText(GiftList.this, "No more Budget", Toast.LENGTH_SHORT).show();
            }
            }
        return true;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.giftlist, menu);
        return true;


    }
}
