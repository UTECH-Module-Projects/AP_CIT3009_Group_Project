package com.application.view;

import net.miginfocom.swing.MigLayout;

import javax.swing.*;

public class gabbymess {

    private JFrame frame;
    private JPanel panel;
    private JLabel Id, email, titl;
    private JTextField emailTxt, IdTxt;
    private JButton button;

    public gabbymess(){
           try {
               UIManager.setLookAndFeel("javax.swing.plaf.nimbus.NimbusLookAndFeel");
           } catch (UnsupportedLookAndFeelException | ClassNotFoundException | InstantiationException |
                   IllegalAccessException e) {
               throw new RuntimeException(e);
           }

           initializeComponents();
           addComponentsToFrame();
           setWindowProperties();
       }
       public void initializeComponents(){
           frame = new JFrame("Welcome to Jan Wholesale");
           frame.setLayout(new MigLayout("debug", "[]10[]", "[] [] [] []"));

           email = new JLabel("Email: ");
           Id = new JLabel("ID Number:");

           titl = new JLabel("Jan's Wholesale");

           button = new JButton("Submit");

           emailTxt = new JTextField(20);
           IdTxt = new JTextField(20);

       }

       public void addComponentsToFrame(){
        //Title
        frame.add(titl, "center, wrap");
            //email frame
           frame.add(email, "left, sg 1, split 2");
           frame.add(emailTxt, "pushx, growx , wrap");

           //Id frame
           frame.add(Id, "left, sg 1, split 2");
           frame.add(IdTxt, "pushx, growx, wrap");

           //button
           frame.add(button, "span, al center");


       }

       public void setWindowProperties(){

           //frame.add(panel);
           frame.setResizable(true);
           frame.setVisible(true);
           frame.setLocationRelativeTo(null);
           frame.pack();
           frame.setSize(500, 172);
           frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
       }



    public static void main (String[] args){
        new gabbymess();
        }
}
