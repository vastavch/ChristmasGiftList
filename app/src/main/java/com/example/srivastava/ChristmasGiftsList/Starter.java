package com.example.srivastava.ChristmasGiftsList;

import android.app.Application;

import com.parse.Parse;
import com.parse.ParseUser;
/* Used to connect the project to the database stored in parse.com

 */
public class Starter extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        Parse.enableLocalDatastore(this);
        //ParseObject.registerSubclass(Message.class);
        Parse.initialize(this, "APPLICATION ID", "CLIENT KEY");
        ParseUser.enableAutomaticUser();
        //ParseFacebookUtils.initialize(this);
       // ParseInstallation.getCurrentInstallation().saveInBackground();
    }
}
