package test;

import java.awt.*;
import java.awt.event.KeyEvent;

/**
 * is created by aMIN on 8/6/2018 at 6:47 PM
 */
public class CAPS {
    public static void main(String[] args) {
        boolean isOn = Toolkit.getDefaultToolkit().getLockingKeyState(KeyEvent.VK_CAPS_LOCK);
        System.out.println(isOn);

        Toolkit.getDefaultToolkit().setLockingKeyState(KeyEvent.VK_CAPS_LOCK, true);
        final Toolkit defaultToolkit = Toolkit.getDefaultToolkit();
        boolean lockingKeyState = Toolkit.getDefaultToolkit().getLockingKeyState(KeyEvent.VK_CAPS_LOCK);
        System.out.println(lockingKeyState);

        KeyboardFocusManager.getCurrentKeyboardFocusManager().addKeyEventDispatcher(new KeyEventDispatcher()
        {
            @Override
            public boolean dispatchKeyEvent(KeyEvent e)
            {
                if (KeyEvent.VK_CAPS_LOCK == e.getKeyCode())
                {
                    boolean check = Toolkit.getDefaultToolkit().getLockingKeyState(KeyEvent.VK_CAPS_LOCK);
                    System.out.println("changing");}
                return false;
            }
        });

    }
}
