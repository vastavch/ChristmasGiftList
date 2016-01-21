package com.example.srivastava.ChristmasGiftsList;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.Toast;

import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.SaveCallback;

import java.util.regex.Pattern;
/*takes persons  name and budget and validatres whether name is not empty and budget contains only numbers and '.'
If yes,it stores the data in the parse.com 'Person' table and displays the toast message stating person added successfully.
 */
public class AddPerson extends AppCompatActivity {
EditText name,budget;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_person);
    name= (EditText) findViewById(R.id.editText_name);
        budget= (EditText) findViewById(R.id.editText_budget);


    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        //noinspection SimplifiableIfStatement
        if(id==R.id.correct){
            if(validateInfo())
            {
                ParseObject person=new ParseObject("Person");
                person.put("Name",name.getText().toString());
                person.put("Budget",budget.getText().toString());
                person.put("NumofGifts","0");
                person.put("Remaining", budget.getText().toString());
                person.saveInBackground(new SaveCallback() {
                    @Override
                    public void done(ParseException e) {
                        if (e == null) {
                            Toast.makeText(AddPerson.this, "Person Added Successfully", Toast.LENGTH_SHORT).show();
                            finish();
                        } else {
                            Toast.makeText(AddPerson.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    }

                });

            }
            else
            {
                Toast.makeText(AddPerson.this, "Enter Valid Details", Toast.LENGTH_SHORT).show();
            }

        }
        return true;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.addpersonmenu, menu);
        return true;
    }
    /* validates whether name is not empty and budget contians only numbers and decimal

     */
    public boolean validateInfo()
    {
        if(!(name.getText().toString().equals("")||name.getText().toString().equals(null)))
        {

            final Pattern sPattern = Pattern.compile("^[0-9]+$");
            return sPattern.matcher(budget.getText().toString()).matches();
        }
        else
            return false;

    }
}
