package com.amin.ui.dialogs;

import com.jfoenix.controls.JFXSnackbar;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.layout.Pane;

public  class SnackBar {
        public static void showSnack(Pane snackbarContainer, String msg) {
            JFXSnackbar jfxSnackbar = new JFXSnackbar(snackbarContainer);
            EventHandler eh = new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent event) {
                    jfxSnackbar.unregisterSnackbarContainer(snackbarContainer);
                    ;
                }
            };
            jfxSnackbar.getStyleClass().add("jfx-snackbar-content");
            jfxSnackbar.setPrefWidth(200);
            jfxSnackbar.show(msg, "got it", 3000, eh);
        }


    public static void showSnack(Pane snackbarContainer, String msg, EventHandler<ActionEvent> eventHandler, String actionText, long longtimr) {
        JFXSnackbar jfxSnackbar = new JFXSnackbar(snackbarContainer);
        jfxSnackbar.getStyleClass().add("jfx-snackbar-content");
        EventHandler eh = new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                jfxSnackbar.unregisterSnackbarContainer(snackbarContainer);
                eventHandler.handle(event);
            }
        };
        jfxSnackbar.setPrefWidth(320);
        jfxSnackbar.show(msg, actionText, longtimr, eh);

    }





        public static void showSnackwithAction(Pane snackbarContainer, String msg, long timeout) {
            JFXSnackbar jfxSnackbar = new JFXSnackbar(snackbarContainer);
            EventHandler eh = new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent event) {
                    jfxSnackbar.unregisterSnackbarContainer(snackbarContainer);

                }
            };

            jfxSnackbar.getStyleClass().add("jfx-snackbar-content");

            jfxSnackbar.show(msg, "Get it", timeout, eh);
        }

        public static void showSnackwithAction(Pane snackbarContainer, EventHandler<ActionEvent> eventHandler, String msg, long timeout) {
            JFXSnackbar jfxSnackbar = new JFXSnackbar(snackbarContainer);

            jfxSnackbar.getStyleClass().add("jfx-snackbar-content");

            jfxSnackbar.show(msg, "Got it", timeout, eventHandler);
        }
     static    JFXSnackbar jfxSnackbar;

        public static void showSnackwithActionKNN(Pane snackbarContainer, EventHandler<ActionEvent> eventHandler, String msg, long timeout) {
            jfxSnackbar  = new JFXSnackbar(snackbarContainer);
            jfxSnackbar.getStyleClass().add("jfx-snackbar-content");

            jfxSnackbar.show(msg, "KNN", timeout, eventHandler);
        }


        public static void showSnack(Pane snackbarContainer, String msg, long timeout) {
            JFXSnackbar jfxSnackbar = new JFXSnackbar(snackbarContainer);

            jfxSnackbar.getStyleClass().add("jfx-snackbar-content");
            jfxSnackbar.show(msg, timeout);

        }
    }