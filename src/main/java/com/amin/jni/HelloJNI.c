// Save as "HelloJNI.c"
#include <jni.h>        // JNI header provided by JDK
#include <stdio.h>      // C Standard IO Header
#include "com_amin_jni_HelloJNI.h"   // Generated
#include "headers/A.h"

#define FILE_NAME "com/text.txt"
// Implementation of the native method sayHello()
JNIEXPORT void JNICALL Java_com_amin_jni_HelloJNI_sayHello(JNIEnv *env, jobject thisObj) {
  FILE* file_ptr = fopen(FILE_NAME, "a");
          // write to file vs write to screen
  fprintf(file_ptr, "this is a test %d\n", 12); // write to file
  fclose(file_ptr);
   printf("Hello World!\n");
    add(4, 6);
    /*This calls add function written in myhead.h
      and therefore no compilation error.*/
    multiply(5, 5);

   return;
}