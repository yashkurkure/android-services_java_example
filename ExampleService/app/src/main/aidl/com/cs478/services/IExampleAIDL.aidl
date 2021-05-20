// IExampleAIDL.aidl
package com.cs478.services;

import com.cs478.services.ExampleObject;


//package com.cs478.exampleservice.ExampleObject;

/**
*      * Notes: AIDL files define an interface which acts as an API for the clients that
       *          bind to this service. The studio takes the aidl file with and auto
       *          generates an abstract class that implements the interface.
       *
       *          The auto generated abstract class can be found in the java(generated) folder.
       *          This class will be named as "Default". In respect with this application you can
       *          find this class in IExampleAIDL.java inside the java(generated) folder in the
       *          appropriate package.
       *
       *          Important: If you make a change to the aidl file, you must clean and rebuild the
       *          project. After doing this changes will be reflected in the generated abstract class.
* */

interface IExampleAIDL {

    ExampleObject getExampleObjectAt(int i);

    void getObjectAt(int i, out ExampleObject obj);

    int getNumberAt(int i);

    String getString1At(int i);

    String getString2At(int i);
}