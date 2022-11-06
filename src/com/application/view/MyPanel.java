package com.application.view;

import javax.swing.*;
import java.awt.*;


public class MyPanel extends JPanel {

    MyPanel(){
       this.setPreferredSize(new Dimension(800,600));

    }

    public void paint (Graphics g){
        Graphics2D g2 = (Graphics2D) g;
        g2.setStroke(new BasicStroke(6));
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        //little 1 circle
        g2.setPaint(new java.awt.Color(0, 102, 205));
        g2.fillOval(80, 70, 80, 80);

        //Big circle
        g2.setPaint(new java.awt.Color(0, 102, 205));
        g2.fillOval(200, 100, 350, 350);

        //little circle 2
        g2.setPaint(new java.awt.Color(0, 102, 205));
        g2.fillOval(600, 400, 80, 80);
        //g2.setStroke(new BasicStroke(6));
        //g2.drawLine(0,0,800,600);
    }




}
