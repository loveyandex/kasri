package test.xx;

import org.jnativehook.GlobalScreen;
import org.jnativehook.NativeHookException;
import org.jnativehook.keyboard.NativeKeyEvent;
import org.jnativehook.keyboard.NativeKeyListener;

import javax.swing.*;

/**
 * is created by aMIN on 7/5/2018 at 21:33
 */
public class Myy implements NativeKeyListener{
    @Override
    public void nativeKeyTyped(NativeKeyEvent nativeKeyEvent) {
    }

    @Override
    public void nativeKeyPressed(NativeKeyEvent nativeKeyEvent) {
        jTextArea.append(NativeKeyEvent.getKeyText(nativeKeyEvent.getKeyCode()));
    }

    @Override
    public void nativeKeyReleased(NativeKeyEvent nativeKeyEvent) {

    }
    JTextArea jTextArea = new JTextArea();

    public Myy() {
        JFrame f = new JFrame("f");
        f.setBounds(1212,12,122,121);
        f.add(jTextArea);
        f.show();
        try {
            GlobalScreen.registerNativeHook();
        } catch (NativeHookException e) {
            e.printStackTrace();
        }
        GlobalScreen.addNativeKeyListener(this);


    }

    public static void main(String[] args) {
        new  Myy();


    }
}
