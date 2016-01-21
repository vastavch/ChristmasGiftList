package com.example.srivastava.ChristmasGiftsList;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ListView;
import android.widget.Toast;

import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;

import java.util.List;

public class AddGift extends AppCompatActivity {
    ListView lv;
    String objId;
    IndPerson ip;
    ParseObject object;
/* Fetches the list of gifts,whose price is less than available balance in Gifts Table stroed at parse.com
    orders them in ascending order of their price.
    If the obtained gift's list is not empty list and sets it to the adapter.
 */
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_gift);

        ip= (IndPerson) getIntent().getParcelableExtra("indperson");
        int rem = Integer.parseInt(ip.getBal());
        ParseQuery<ParseObject> query = ParseQuery.getQuery("Gifts");
        query.whereLessThanOrEqualTo("price", rem);
        query.orderByAscending("price");
        lv = (ListView) findViewById(R.id.listView_addgift);
        query.findInBackground(new FindCallback<ParseObject>() {
            @Override
            public void done(List<ParseObject> objects, ParseException e) {
                if(e==null)
                {

                    if(objects.size()>0) {

                        AddGiftAdapter adapter = new AddGiftAdapter(AddGift.this, R.layout.addgiftlist, objects, ip, AddGift.this);
                        lv.setAdapter(adapter);
                    }

                }
                else
                {
                    Toast.makeText(AddGift.this, e.getMessage(), Toast.LENGTH_SHORT).show();

                }
            }
        });
    }

    public void go() {
        GiftList.shudReload=true;
        finish();
    }
}