package com.amin.runonce;

import java.awt.*;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.StringSelection;
import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;

/**
 * created By LoardGod on 5/6/2019 1:03 AM
 */

public class TestMouse {
    public static void main(String[] args) throws AWTException, InterruptedException {
        Robot robot = new Robot();
        String text = "Hello World";
        StringSelection stringSelection = new StringSelection(text);
        Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
        clipboard.setContents(stringSelection, stringSelection);
//                        robot.keyPress(KeyEvent.VK_CONTROL);
//                        robot.keyPress(KeyEvent.VK_A);
//                        robot.keyRelease(KeyEvent.VK_A);
//                        robot.keyPress(KeyEvent.VK_V);
//                        robot.keyRelease(KeyEvent.VK_V);
//                        robot.keyRelease(KeyEvent.VK_CONTROL);

        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();


        for (int i = 0; i < 1000; i++) {
            robot.mouseWheel(-1000);
            robot.mouseMove(0, 0);
            robot.mouseMove(355, 535);
            robot.delay(100);
// first click

            robot.mousePress(InputEvent.BUTTON1_DOWN_MASK);
            robot.mouseRelease(InputEvent.BUTTON1_DOWN_MASK);
            robot.delay(5000);

        }
        System.exit(0);
    }
}
