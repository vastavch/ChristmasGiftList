package com.example.srivastava.ChristmasGiftsList;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.parse.ParseObject;

import java.util.List;

/*Sets the person name ,total spent and alocated budget received from ChristmasListActivity.
total spent and alocated are displayed in red if alocated budget is spent.
Clicking on a person would lead to his detialed gift list.

 */
public class PersosnListAdaptor extends ArrayAdapter<ParseObject> {
    View mview;
    Context mcontext;
    int mresource;
    List<ParseObject> usersList;
    public PersosnListAdaptor(Context context, int resource, List objects) {
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
        TextView txtUserName=(TextView) convertView.findViewById(R.id.textView_name);
        //ParseUser user= (ParseUser) c.get("createdBy");
        txtUserName.setText(c.getString("Name"));
        final TextView txtMessage=(TextView) convertView.findViewById(R.id.textView_budget);
        if(Integer.parseInt(c.getString("Remaining"))==0)
        {
            txtMessage.setTextColor(Color.RED);
        }
        else
        {
            txtMessage.setTextColor(Color.GREEN);
        }
        txtMessage.setText("$"+(Integer.parseInt(c.getString("Budget")) - Integer.parseInt(c.getString("Remaining"))) + "/$" + c.getString("Budget"));
        TextView txtDateTime=(TextView) convertView.findViewById(R.id.textView_gifts);

        txtDateTime.setText("brought "+c.getString("NumofGifts") + " gifts");
        ImageView img=(ImageView) convertView.findViewById(R.id.imgvw);
        img.setImageResource(R.drawable.person);
        final ParseObject object=usersList.get(position);
        convertView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                IndPerson indPerson=new IndPerson(c.getObjectId(),c.getString("Name"),c.getString("Budget"),c.getString("Remaining"),c.getString("NumofGifts"));
                Intent inent = new Intent(v.getContext(), GiftList.class);
                inent.putExtra("person",indPerson);

                mcontext.startActivity(inent);
            }
        });
        return convertView;

    }

}
