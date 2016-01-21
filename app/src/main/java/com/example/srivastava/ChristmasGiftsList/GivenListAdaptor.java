package com.example.srivastava.ChristmasGiftsList;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.parse.ParseObject;
import com.squareup.picasso.Picasso;

import java.util.List;

/* Sets the gift image,title and price of the retrieved gifts to the view

 */
public class GivenListAdaptor extends ArrayAdapter<ParseObject> {
    View mview;
    Context mcontext;
    int mresource;
    List<ParseObject> usersList;

    public GivenListAdaptor(Context context, int resource, List<ParseObject> objects) {
        super(context, resource, objects);
        mcontext=context;
        mresource=resource;
        usersList=objects;
    }
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        mview=convertView;
        if(convertView == null){
            LayoutInflater inflate= (LayoutInflater) mcontext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView= inflate.inflate(mresource,parent,false);
        }
        final ParseObject c=usersList.get(position);
        TextView txtUserName=(TextView) convertView.findViewById(R.id.textView_giftname);
        txtUserName.setText(c.getString("GiftName"));
        TextView txtMessage=(TextView) convertView.findViewById(R.id.textView_giftprice);
        txtMessage.setText(c.getString("Price"));
        ImageView img=(ImageView) convertView.findViewById(R.id.imageView_gift);
        Picasso.with(mcontext).load(c.getString("ImageURL")).into(img);
        final ParseObject object=usersList.get(position);
        return convertView;
    }
}
