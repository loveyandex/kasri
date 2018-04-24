package com.listener;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.util.ArrayList;
import java.util.List;

public class JFram extends JFrame {
    public JFram(String title) throws HeadlessException {
        super(title);
        setSize(800,600);
        setLocation(333,333);
        List<TextField> textFields=new ArrayList<>();
        Button generate=new Button("generate");
        generate.setBounds(122,122,155,88);
        TextField te=new TextField();
        te.setBounds(0,0,155,88);
        te.setBackground(Color.LIGHT_GRAY);

        generate.addActionListener(new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JFram.this.getContentPane().removeAll();
                JFram.this.getContentPane().add(generate);
                JFram. this.getContentPane().add(te);
                for (int i = 0; i < Integer.parseInt(te.getText()); i++) {
                    TextField textField = new TextField();
                    textField.setSize(222,25);
                    textField.setLocation(333,222+25*i);
                    textFields.add(textField);
                    JFram.this.getContentPane().add(textField);
                }        textFields.get(0).setBackground(Color.RED);

            }
        });
        this.setLayout(null);
        this.getContentPane().add(generate);
        this.getContentPane().add(te);
        this.setVisible(true);


    }

    public static void main(String[] args) {
        JFram god = new JFram("god");

    }
}
