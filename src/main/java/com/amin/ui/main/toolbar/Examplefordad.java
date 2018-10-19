package com.amin.ui.main.toolbar;

/**
 * is created by aMIN on 10/16/2018 at 9:58 PM
 */
public class Examplefordad {
    public Examplefordad() {

        new Thread(() -> {
            try {
                synchronized (this) {
                    this.wait();

                }


            } catch (InterruptedException e) {
                e.printStackTrace();

            }
        }).start();
    }

    public static void main(String[] args) throws InterruptedException {
        final Examplefordad examplefordad = new Examplefordad();

        final Examplefordad examplefordad1 = new Examplefordad();
        System.out.println("d");
        Thread.sleep(2323);
        synchronized (examplefordad) {
            examplefordad.notifyAll();
        }

        synchronized (examplefordad1) {
            examplefordad1.notifyAll();
        }

    }
}
