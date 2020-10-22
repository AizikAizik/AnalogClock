/*
This is an Analog clock Application made with Java Swing and Java 2D API
 */
package com.Clock;

import javax.swing.*;
import java.util.Date;

public class Clock extends JFrame {

     int hour;
     int minutes;
     int seconds;
     ClockDial clockDial;

     //constructor
    public Clock(){
        setSize(510,530); // size of the frame
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        clockDial = new ClockDial();
        getContentPane().add(clockDial); // add panel to the frame
        Date date = new Date();
    }

    public static void main(String[] args) {

    }
}

class ClockDial extends  JPanel{
    Clock parent;
}
