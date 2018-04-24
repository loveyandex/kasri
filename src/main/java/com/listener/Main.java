package com.listener;

/* w ww.j  a va2s .c om*/
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;

import javax.swing.AbstractButton;
import javax.swing.ButtonModel;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

public class Main {

    public static void main(String[] a) {
        final String DESELECTED_LABEL = "Deselected";
        final String SELECTED_LABEL = "Selected";

        JFrame frame = new JFrame("Selecting CheckBox");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        JCheckBox checkBox = new JCheckBox(DESELECTED_LABEL);

        ActionListener actionListener = new ActionListener() {
            public void actionPerformed(ActionEvent actionEvent) {
                AbstractButton abstractButton = (AbstractButton) actionEvent.getSource();
                boolean selected = abstractButton.getModel().isSelected();
                String newLabel = (selected ? SELECTED_LABEL : DESELECTED_LABEL);
                abstractButton.setText(newLabel);
            }
        };

        ChangeListener changeListener = new ChangeListener() {
            public void stateChanged(ChangeEvent changeEvent) {
                AbstractButton abstractButton = (AbstractButton) changeEvent
                        .getSource();
                ButtonModel buttonModel = abstractButton.getModel();
                boolean armed = buttonModel.isArmed();
                boolean pressed = buttonModel.isPressed();
                boolean selected = buttonModel.isSelected();
                System.out.println("Changed: " + armed + "/" + pressed + "/" + selected);
            }
        };

        ItemListener itemListener = new ItemListener() {
            public void itemStateChanged(ItemEvent itemEvent) {
                AbstractButton abstractButton = (AbstractButton) itemEvent.getSource();
                Color foreground = abstractButton.getForeground();
                Color background = abstractButton.getBackground();
                int state = itemEvent.getStateChange();
                if (state == ItemEvent.SELECTED) {
                    abstractButton.setForeground(background);
                    abstractButton.setBackground(foreground);
                }
            }
        };

        checkBox.getModel().addActionListener(actionListener);
        checkBox.getModel().addChangeListener(changeListener);
        checkBox.getModel().addItemListener(itemListener);

        frame.add(checkBox, BorderLayout.NORTH);
        frame.setSize(300, 100);
        frame.setVisible(true);
    }
}