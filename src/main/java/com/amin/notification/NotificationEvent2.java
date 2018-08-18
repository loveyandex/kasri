/*
 * Copyright (c) 2015 by Gerrit Grunwald
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.amin.notification;

import javafx.event.Event;
import javafx.event.EventTarget;
import javafx.event.EventType;


/**
 * User: hansolo
 * Date: 29.04.14
 * Time: 10:03
 */
public class NotificationEvent2 extends Event {
    public static final EventType<NotificationEvent2> NOTIFICATION_PRESSED = new EventType(ANY, "NOTIFICATION_PRESSED");
    public static final EventType<NotificationEvent2> SHOW_NOTIFICATION    = new EventType(ANY, "SHOW_NOTIFICATION");
    public static final EventType<NotificationEvent2> HIDE_NOTIFICATION    = new EventType(ANY, "HIDE_NOTIFICATION");

    public final NotificationProgress NOTIFICATION;


    // ******************** Constructors **************************************
    public NotificationEvent2(final NotificationProgress NOTIFICATION,
                              final Object SOURCE, final EventTarget TARGET,
                              EventType<NotificationEvent2> TYPE) {
        super(SOURCE, TARGET, TYPE);
        this.NOTIFICATION = NOTIFICATION;
    }
}
