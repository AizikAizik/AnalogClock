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
        setLocationRelativeTo(null);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setResizable(false); // make frame un resizable
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
        g.setColor(Color.WHITE);
        g.fillOval(5, 5, 480, 480);
        g.setColor(Color.BLACK);
        g.fillOval(10, 10, 470, 470);
        g.setColor(Color.GREEN); // color of objects inside the oval
        g.fillOval(237, 237, 15, 15);
        g.setFont(g.getFont().deriveFont(Font.BOLD, 32));

        // draw the numbers round the Oval canvas
        for (int i = 1; i <= 12; i++){
            //got formula from stack overflow
            g.drawString(Integer.toString(i),240-(i/12)*11+(int)(210*Math.sin(i*Math.PI/6)),253-(int)(210*Math.cos(i*Math.PI/6)));
        }

        double minsecDeg = (double) Math.PI / 30;  // minutes and seconds degrees
        double hourDeg = (double) Math.PI / 6; // hour degrees
        int tx;  // X axis
        int ty;  //Y axis
        int xPoints[] = new int[3];
        int yPoints[] = new int[3];

        //  Align the Seconds hand inside the Oval
        tx=245+(int)(210*Math.sin(parent.seconds*minsecDeg)); // pointer on X axis
        ty=245-(int)(210*Math.cos(parent.seconds*minsecDeg)); // pointer on Y axis
        g.drawLine(245,245,tx,ty); // draw seconds line

        //Align the minutes hand inside the Oval
        tx=245+(int)(190*Math.sin(parent.minutes*minsecDeg));
        ty=245-(int)(190*Math.cos(parent.minutes*minsecDeg));
        xPoints[0] = 245;
        xPoints[1] = tx + 2;
        xPoints[2] = tx - 2;
        yPoints[0] = 245;
        yPoints[1] = ty + 2;
        yPoints[2] = ty - 2;
        g.fillPolygon(xPoints, yPoints, 3); // draw all X AND Y points with 3 number of points

        //Align the hour hand inside the Oval
        tx=245+(int)(160*Math.sin(parent.hour*hourDeg+parent.minutes*Math.PI/360));
        ty=245-(int)(160*Math.cos(parent.hour*hourDeg+parent.minutes*Math.PI/360));
        xPoints[1] = tx + 4;
        xPoints[2] = tx - 4;
        yPoints[1 ] = ty - 4;
        yPoints[2] = ty + 4;
        g.fillPolygon(xPoints, yPoints, 3);
    }
}
