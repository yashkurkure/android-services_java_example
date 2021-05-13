package com.cs478.exampleclient;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ComponentName;

import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.content.pm.ResolveInfo;
import android.os.Bundle;
import android.os.IBinder;
import android.os.RemoteException;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.cs478.services.IExampleAIDL;
import com.cs478.services.ExampleObject;

public class MainActivity extends AppCompatActivity
{

    private static final String TAG = "MainActivityClient";

    Button b0;
    Button b1;
    Button b2;
    Button b3;
    Button b4;

    TextView number;
    TextView string1;
    TextView string2;



    private IExampleAIDL mIExampleAIDL;
    private boolean mIsBound = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        b0 = findViewById(R.id.button0);
        b1 = findViewById(R.id.button1);
        b2 = findViewById(R.id.button2);
        b3 = findViewById(R.id.button3);
        b4 = findViewById(R.id.button4);


        number = findViewById(R.id.numTv);
        string1 = findViewById(R.id.str1Tv);
        string2 = findViewById(R.id.str2Tv);


        b0.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                execListenerForIndex(0);

            }
        });

        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                execListenerForIndex(1);

            }
        });

        b2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                execListenerForIndex(2);

            }
        });

        b3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                execListenerForIndex(3);

            }
        });

        b4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                execListenerForIndex(4);

            }
        });

    }


    private void execListenerForIndex(int i)
    {
        try{

            if(mIsBound)
            {
                //Initial Approach [without using in,out,inout in aidl file] WORKS
                ExampleObject eObj = mIExampleAIDL.getExampleObjectAt(i);
                number.setText(String.valueOf(eObj.getNumber()));
                string1.setText(eObj.getString1());
                string2.setText(eObj.getString2());

                //Approach 2 [using out and inout in aidl method] WORKS
                //ExampleObject eObj = new ExampleObject(-1,"","");
                //mIExampleAIDL.getObjectAt(i, eObj);
                //number.setText(String.valueOf(eObj.getNumber()));
                //string1.setText(eObj.getString1());
                //string2.setText(eObj.getString2());

                //e2 trying only primitives WORKS
                //number.setText(String.valueOf(mIExampleAIDL.getNumberAt(i)));
                //string1.setText(mIExampleAIDL.getString1At(i));
                //string2.setText(mIExampleAIDL.getString2At(i));
            }

            else
            {
                Log.i(TAG, "Service was not bound!");
                number.setText("NaN" + i);
                string1.setText("NaN" + i);
                string2.setText("NaN" + i);
            }

        }
        catch (RemoteException e)
        {
            Log.e(TAG, e.toString());
        }

    }

    private final ServiceConnection mConnection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {

            mIExampleAIDL = IExampleAIDL.Stub.asInterface(service);

            mIsBound = true;

        }

        @Override
        public void onServiceDisconnected(ComponentName name) {

            mIExampleAIDL = null;

            mIsBound = false;

        }
    };


    @Override
    protected void onStart() {
        super.onStart();
        checkBindingAndBind();
    }

    protected void checkBindingAndBind()
    {
        if(!mIsBound)
        {
            boolean b = false;

            Intent i = new Intent(IExampleAIDL.class.getName());
            ResolveInfo info = getPackageManager().resolveService(i,0);
            i.setComponent(new ComponentName(info.serviceInfo.packageName, info.serviceInfo.name));

            //Intent i = new Intent(getBaseContext(), IExampleAIDL.class);


            //ResolveInfo info = getPackageManager().resolveService(i, 0);
            //i.setComponent(new ComponentName(info.serviceInfo.packageName, info.serviceInfo.name));

            b = bindService(i, this.mConnection, Context.BIND_AUTO_CREATE);

            if (b) {
                Log.i(TAG, "bindService() succeeded!");
            } else {
                Log.i(TAG, "bindService() failed!");
            }

        }


        }

    @Override
    protected void onStop() {
        super.onStop();
        if(mIsBound)
        {
            unbindService(this.mConnection);
        }
    }
}