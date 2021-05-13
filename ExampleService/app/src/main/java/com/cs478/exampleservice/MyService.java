package com.cs478.exampleservice;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.Service;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.IBinder;
import android.os.Parcel;
import android.os.RemoteException;

import androidx.annotation.Nullable;
import androidx.core.app.NotificationCompat;

import com.cs478.services.ExampleObject;
import com.cs478.services.IExampleAIDL;

import java.util.ArrayList;

/**
 * This is the service class
 * */


public class MyService extends Service {
    public MyService() {
    }

    //Initialized and 5 Example Objects loaded in OnCreate()
    ArrayList<ExampleObject> list;

    @Override
    public void onCreate() {
        super.onCreate();

        //initialize the list of objects
        list  = new ArrayList<ExampleObject>();

        //Checking if I can get resources: String appName = this.getResources().getString(R.string.app_name);
        Bitmap bitmap = BitmapFactory.decodeResource(this.getResources(),R.drawable.ic_launcher_background );

        //add example objects
        list.add(new ExampleObject(0,"I am object 0", "I say hello"));
        //list.add(new ExampleObject(0,"I am object 0", appName));
        list.add(new ExampleObject(1,"I am object 1", "I go with the flow"));
        list.add(new ExampleObject(2,"I am object 2", "I like jello"));
        list.add(new ExampleObject(3,"I am object 3", "I love stackoverflow"));
        list.add(new ExampleObject(4,"I am object 4", "I use android studio"));

        this.createNotificationChannel();

        notification = new NotificationCompat.Builder(getApplicationContext(), CHANNEL_ID)
                .setOngoing(true).setContentTitle("Example Service Running")
                .setContentText("Not Clickable!")
                .build();


        startForeground(NOTIFICATION_ID, notification);

    }


    private final IExampleAIDL.Stub mBinder = new IExampleAIDL.Stub() {
        @Override
        public ExampleObject getExampleObjectAt(int i) throws RemoteException {

            if(i<0 || i>= list.size()) return new ExampleObject(-1, "I am a special object", " My wisdom tells me you have a bug!!");

            return list.get(i);
        }

        @Override
        public void getObjectAt(int i, ExampleObject obj) throws RemoteException {

            obj.setNumber(list.get(i).getNumber());
            obj.setString1(list.get(i).getString1());
            obj.setString2(list.get(i).getString2());

        }


        @Override
        public int getNumberAt(int i) throws RemoteException {
            if(i<0 || i>= list.size()) return -1;

            return list.get(i).getNumber();
        }

        @Override
        public String getString1At(int i) throws RemoteException {
            if(i<0 || i>= list.size()) return "invalid";

            return list.get(i).getString1();
        }

        @Override
        public String getString2At(int i) throws RemoteException {
            if(i<0 || i>= list.size()) return "invalid";

            return list.get(i).getString2();
        }
    };


    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return mBinder;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        return START_NOT_STICKY;
    }


    //--------------------------------NOTIFICATION FOR FOREGROUND SERVICE---------------------------------------------------------

    //Notification stuff: required for a foreground service such as this
    private static final int NOTIFICATION_ID = 1;
    private Notification notification;
    private static String CHANNEL_ID = "Example channel id" ;

    private void createNotificationChannel()
    {
        CharSequence name = "Example Service Notification";
        String notification = "I contain 5 example objects";
        int importance = NotificationManager.IMPORTANCE_DEFAULT;

        NotificationChannel channel = new NotificationChannel(CHANNEL_ID, name, importance);

        NotificationManager notificationManager = getSystemService(NotificationManager.class);
        notificationManager.createNotificationChannel(channel);


    }

}



