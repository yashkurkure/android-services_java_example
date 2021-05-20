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

    /**
     * onCreate() called when the service is created
     * in this case whenever the first client binds to it.
     * */
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


    /**
     *
     * Notes: AIDL files define an interface which acts as an API for the clients that
     *          bind to this service. The studio takes the aidl file with an java interface and auto
     *          generates an abstract class that implements the interface.
     *
     *          The auto generated abstract class can be found in the java(generated) folder.
     *          This class will be named as "Default". With respect to this application you can
     *          find this class in IExampleAIDL inside the java(generated) folder in the
     *          appropriate package.
     *
     * Important: >If you make a change to the aidl file, you must clean and rebuild the
     *              project. After doing this, changes will be reflected in the generated abstract class.
     *              This would mean that you would have to update the stub accordingly to the new API.
     *
     *             >If this client deals with multiple clients, use synchronized block in the implementation
     *             of the methods.
     *
     *
     *
     *
     * >IExampleAIDL is the interface defined in the aidl files.
     *
     * >This stub uses implements the methods defined in the
     *  generated abstract class which implements the IMusicCentral interface.
     *
     * >mBinder is the IBinder object that is returned to the client in the onBind() method.
     * */
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

    /**
     *
     * Notes: For a class that extends service this method is compulsory to implement.
     *          This method returns the Binder object to the client, which the client
     *          can use to make calls to the service. The API for the calls is defined in
     *          the aidl files using an interface. For more on aidl files read the Notes for the
     *          Stub.
     *
     *          Keep in mind that no matter how many clients bind to the service, THIS service
     *          would always return the SAME IBinder object. Hence, to prevent race conditions
     *          when multiple clients bind, it is important to use synchronized blocks inside the
     *          implementation of the API methods (found in the stub).
     *
     *          If you want each client to be handled on a separate thread, you would have to use
     *          either use JobIntentService or define your own threading construct for the service.
     *
     *          Since, this service does not have a threading construct it will return the same binder
     *          object to all clients.
     *
     *
     *
     * onBind() called when a new client binds to the service.
     * It returns the IBinder object to the
     * */
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


    /**
     * createNotificationChannel()
     * Creates a notification channel for the notification of the foreground service.
     *
     * Notes: All foreground services need to be accompanied with a notification whenever they are running.
     *          This was made as a requirement for running a foreground service.
     *
     *
     *
     * */
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



