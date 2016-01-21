package com.example.srivastava.ChristmasGiftsList;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.parse.GetCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.SaveCallback;
import com.squareup.picasso.Picasso;

import java.util.List;
/* This sets the gifts list obtained from Gifts table from Parse.com and sets the image,description and price.
*  on clicking the particular gift,that gift is added to the persons list.
 */
public class AddGiftAdapter extends ArrayAdapter<ParseObject> {
    View mview;
    Context mcontext;
    int mresource;
    List<ParseObject> usersList;
    IndPerson po;
    AddGift act;

    public AddGiftAdapter(Context context, int resource, List<ParseObject> objects,IndPerson po,AddGift act) {
        super(context, resource, objects);
        mcontext=context;
        mresource=resource;
        usersList=objects;
        this.po=po;
        this.act=act;
    }
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        mview=convertView;
        if(convertView == null){
            LayoutInflater inflate= (LayoutInflater) mcontext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView= inflate.inflate(mresource,parent,false);
        }
        final ParseObject c=usersList.get(position);
        TextView txtUserName=(TextView) convertView.findViewById(R.id.text_imagename);
        //ParseUser user= (ParseUser) c.get("createdBy");
        txtUserName.setText(c.getString("gift"));
        TextView txtMessage=(TextView) convertView.findViewById(R.id.textView_imageprice);
        txtMessage.setText("$"+c.getNumber("price"));
        //TextView txtDateTime=(TextView) convertView.findViewById(R.id.textView_gifts);

        //txtDateTime.setText(c.getString("NumofGifts"));
        ImageView img=(ImageView) convertView.findViewById(R.id.image_gift);
        Picasso.with(mcontext).load(c.getString("imageUrl")).into(img);
        final ParseObject object=usersList.get(position);
        img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ParseObject object = new ParseObject("GiftsGiven");

                object.put("GiftName", c.getString("gift"));
                object.put("ImageURL", c.getString("imageUrl"));
                object.put("Price", c.getNumber("price")+"");
                object.put("GivenTo",ParseObject.createWithoutData("Person", po.getObjectId()) );
                object.saveInBackground(new SaveCallback() {
                    @Override
                    public void done(ParseException e) {
                        if (e == null) {
                            ParseQuery<ParseObject> parque = ParseQuery.getQuery("Person");
                            parque.whereEqualTo("objectId", po.getObjectId());
                            parque.getFirstInBackground(new GetCallback<ParseObject>() {
                                @Override
                                public void done(ParseObject parobj, ParseException e) {
                                    if (e == null) {
                                        if (parobj!=null) {


                                            parobj.put("Remaining", Integer.parseInt(po.getBal()) - (int) c.getNumber("price") + "");
                                            int num = Integer.parseInt(po.getNumofgifts());
                                            num++;
                                            parobj.put("NumofGifts", num + "");
                                            parobj.saveInBackground(new SaveCallback() {
                                                @Override
                                                public void done(ParseException e) {
                                                    if (e == null) {
                                                        Toast.makeText(act, "giftadded", Toast.LENGTH_SHORT).show();
                                                        act.go();
                                                    }         }
                                            });
                                        }

                                    }
                                }
                            });

                        }

                    }
                });


            }
        });
        return convertView;
    }
    }

