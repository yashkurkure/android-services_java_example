# Android Service and Client Applications

This repo has 2 android-apps: ExampleClient and ExampleService

The goal of these apps is to understand how andoid services work.

## File Links and Description for ExampleService App


| File | Description |
| --- | --- |
| [MyService.java](https://github.com/yashkurkure/android-services_java_example/blob/main/ExampleService/app/src/main/java/com/cs478/exampleservice/MyService.java) | Contains the main service class.<br>The service does not have an "Activity", therefore it only extends "Service".<br>This file contains all the driver code for the android service such as:<br>1. Creating the Service.<br>2. Creating the notification and notification channel.<br>3. Defining the API of the Service.<br>4. Sending the Proxy/Binder object to the client. |
| [ExampleObject.java](https://github.com/yashkurkure/android-services_java_example/blob/main/ExampleService/app/src/main/java/com/cs478/services/ExampleObject.java) | ExampleObject is a class that implements Parcelable.<br>In simple words, Parcelable is the android version of Java Serializable except Parcelable is faster.<br>Objects of this class will be passed between the service and the client using the API that is defined in the AIDL file. |
| [IExampleAIDL.aidl](https://github.com/yashkurkure/android-services_java_example/blob/main/ExampleService/app/src/main/aidl/com/cs478/services/IExampleAIDL.aidl) | This file defines an interface, whose methos are the API calls of the Service.|
| [IExampleAIDL.aidl](https://github.com/yashkurkure/android-services_java_example/blob/main/ExampleService/app/src/main/aidl/com/cs478/services/ExampleObject.aidl) | This file defines the custom parcelable object for Android Studio, allowing it to recognize <br>the object when it generates necessary files using IExampleAIDL.aidl |

For detialed explalinations on how the service works and what .aild files are, scroll down.

## File Links and Description for ExampleClient App

*Under Construction* `:hammer:` `:wrench:`