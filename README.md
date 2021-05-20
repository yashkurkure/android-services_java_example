# Android Service and Client Applications

This repo has 2 android-apps: ExampleClient and ExampleService

The goal of these apps is to understand how andoid services work.

## File Links and Description for ExampleService App:


| File | Description |
| --- | --- |
| [MyService.java](https://github.com/yashkurkure/android-services_java_example/blob/main/ExampleService/app/src/main/java/com/cs478/exampleservice/MyService.java) | Contains the main service class.<br>The service does not have an "Activity", therefore it only extends "Service".<br>This file contains all the driver code for the android service such as:<br>1. Creating the Service.<br>2. Creating the notification and notification channel.<br>3. Defining the API of the Service.<br>4. Sending the Proxy/Binder object to the client. |
| [ExampleObject.java](https://github.com/yashkurkure/android-services_java_example/blob/main/ExampleService/app/src/main/java/com/cs478/services/ExampleObject.java) | ExampleObject is a class that implements Parcelable.<br>In simple words, Parcelable is the android version of Java Serializable except Parcelable is faster.<br>Objects of this class will be passed between the service and the client using the API that is defined in the AIDL file. |
| [IExampleAIDL.aidl](https://github.com/yashkurkure/android-services_java_example/blob/main/ExampleService/app/src/main/aidl/com/cs478/services/IExampleAIDL.aidl) | This file defines an interface, whose methods are the API calls of the Service.|
| [IExampleObject.aidl](https://github.com/yashkurkure/android-services_java_example/blob/main/ExampleService/app/src/main/aidl/com/cs478/services/ExampleObject.aidl) | This file defines the custom parcelable object for Android Studio, allowing it to recognize <br>the object when it generates necessary files using IExampleAIDL.aidl |


## File Links and Description for ExampleClient App:

| File | Description |
| --- | --- |
| [MainActivity.java](https://github.com/yashkurkure/android-services_java_example/blob/main/ExampleClient/app/src/main/java/com/cs478/exampleclient/MainActivity.java) | Main activity of service app.<br>The service app starts with this activity. |
| [ExampleObject.java](https://github.com/yashkurkure/android-services_java_example/blob/main/ExampleClient/app/src/main/java/com/cs478/services/ExampleObject.java) | Similar to the functioning of Serializable, the client would need a copy of the class <br>whose object it is receiving from the service. |
| [IExampleAIDL.aidl](https://github.com/yashkurkure/android-services_java_example/blob/main/ExampleService/app/src/main/aidl/com/cs478/services/IExampleAIDL.aidl) | The client needs the copy of the interface from which the API class of the service was generated. |
| [IExampleObject.aidl](https://github.com/yashkurkure/android-services_java_example/blob/main/ExampleClient/app/src/main/aidl/com/cs478/services/ExampleObject.aidl) | The client needs a copy of this, because this is used by the interface defined in IExampleAIDL.aidl. |


## Some theory on how this app works:

### What is an .aidl file?

AIDL stands for **Android Interface Definition Language**. The aidl files that a you create defines an interface, which the Android SDK tools use to auto generate 
an abstract class which implements this interface. In Android, when two different process want to talk to each other, the objects of the generated abstract class allow this Inter Process Communication or IPC. The primary purpose of doing this is to simplyfiy the process of making the API of the a process, that other processes can call and use. 

Android Documentation on AIDL: [Android Interface Definition Language (AIDL)](https://developer.android.com/guide/components/aidl), [Using AIDL for bound services](https://developer.android.com/guide/components/bound-services)

### Service App Notes:
- This app defines a [**foreground service**](https://developer.android.com/guide/components/foreground-services).
- This is a [**bound service**](https://developer.android.com/guide/components/bound-services), meaning, it strats when client apps bind to it, and destroys itself when all clients unbind. This can be achived by returning the START_NOT_STICKY flag in the onStartCommand() method. There are many other flag and you can find them [here](https://developer.android.com/reference/android/app/Service#constants_1).
- What does this particular service do?
  - Starts whenever a client binds to it. Destroys itslef when all clients unbind.
  - It is a forground service, hence once it start a notification will be shown in the notification bar, indicating that the service is running.
  - It stores 5 example objects , and returns them when a client requests for an object. 
  - Does NOT run on its own thread. Take a look at the [InentService](https://developer.android.com/reference/android/app/IntentService) class if you want a service to have it's own thread.
- When a client binds to the service:
  - If it is the first client, the service will go through all the necessary [callbacks](https://developer.android.com/guide/components/services#LifecycleCallbacks) such as onStart(),onCreate() and onBind().
  - If the service is already running, then the *onStartCommand()* method in the service class will be called.
- What is the role of the .aidl here?
  - As I previously mentioned, the AIDL file is used to define the API of the service.
  - The methods of this API are defined using a Stub. The obejct that we get from this is the binder object (or the proxy object, in terms of the proxy design pattern).
  - This binder object is given to the client wheit binds to the service. (The onBind() method of the service class returns this binder object)
  - The client then uses this binder object to access the API of the service and make calls to it. This is the reason why the client must also have a copy of the .aidl files and the parcelable object that the service is using to communicate with its clients.


### Client App Notes:
- This is the client app that binds to ExampleService as soon as it starts.
- Before running this app make sure you have ExampleService already installed.
- The client uses the API of the binder object it receives in the *onSeriviceConnected()* method.
- The app has 5 buttons, and pressing each will retreive a certain ExamplObject from the Serivce.
- Pressing a button will direct the client app to make a call to the service using the binder object's API.
- If you examine the ExampleObject class, you will see it has one int and 2 strings. For each object in the service these values are differnt. 
- When a client retrieves a particular object from the serive, the activity will display the contents of the int and the string members of the ExampleObject it receives.

