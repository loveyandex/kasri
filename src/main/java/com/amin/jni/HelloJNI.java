package com.amin.jni;

public class HelloJNI {  // Save as HelloJNI.java
    static {
        System.loadLibrary("hello"); // Load native library hello.dll (Windows) or libhello.so (Unixes)
        //  at runtime
        // This library contains a native method called sayHello()
    }

    // Test Driver
    public static void main(String[] args) {
        new HelloJNI().sayHello();  // Create an instance and invoke the native method
    }

    // Declare an instance native method sayHello() which receives no parameter and returns void
    private native void sayHello();
}

//todo java -Djava.library.path=.\com\amin\jni com.amin.jni.HelloJNI
//todo gcc -I "%JAVA_HOME%\include" -I "%JAVA_HOME%\include\win32" -shared -o hello.dll HelloJNI.c