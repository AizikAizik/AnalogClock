/*
This is an Analog clock Application made with Java Swing and Java 2D API
 */
package com.Clock;

import javax.swing.*;
import java.awt.*;
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
        clockDial = new ClockDial(this);
        getContentPane().add(clockDial); // add panel to the frame
        Date date = new Date();
        String time = date.toString();
        hour = Integer.parseInt(time.substring(11,13)); //get the hour from the date object
        minutes = Integer.parseInt(time.substring(14,16)); //get the minutes from the date object
        seconds = Integer.parseInt(time.substring(17,19)); //get the seconds from the date object
        ClockEngine.setPriority(ClockEngine.getPriority() + 3); // set the Thread priority of the Clock Engine Thread in the Queue
        ClockEngine.start(); // start the Clock Engine Thread
    }

    Thread ClockEngine = new Thread(){
        int newsec;
        int newmin;

        public void run(){
            while (true){
                newsec = (seconds + 1) % 60;
                newmin = (minutes + (seconds + 1) / 60) % 60;
                hour = (hour + (minutes + (seconds + 1) / 60) / 60) % 12;
                seconds = newsec;
                minutes = newmin;
                try{
                    Thread.sleep(1000); // sleep thread for a second
                }catch (InterruptedException ie){ }

                //re-render the panel on the frame
                clockDial.repaint();
            }
        }
    };

    public static void main(String[] args) {
        new Clock().setVisible(true);
    }
}

class ClockDial extends  JPanel{
    Clock parent;

    //constructor
    public ClockDial(Clock clock){
        setSize(520,530); //set size dimensions of the Panel
        parent = clock;
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.setColor(Color.BLACK);
        g.fillRect(0, 0, getWidth(), getHeight());
        g.fillOval(5, 5, 480, 480);
    }
}
