// IExampleAIDL.aidl
package com.cs478.services;

import com.cs478.services.ExampleObject;


//package com.cs478.exampleservice.ExampleObject;

// Declare any non-default types here with import statements

interface IExampleAIDL {

    ExampleObject getExampleObjectAt(int i);

    void getObjectAt(int i, out ExampleObject obj);

    int getNumberAt(int i);

    String getString1At(int i);

    String getString2At(int i);
}