package com.application.view;

import net.miginfocom.swing.MigLayout;
import org.hibernate.type.descriptor.JdbcBindingLogging;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class gabbymess {

    private JFrame frame;
    private JLabel Id, email, titl, lock;
    private JTextField emailTxt, IdTxt;
    private JButton login, cancel;

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
            //registerListeners();
       }
       public void initializeComponents(){

           titl = new JLabel("JAN'S WHOLESALE & RETAIL", JLabel.CENTER);
           titl.setOpaque(true);
           titl.setBackground(Color.black);
           titl.setForeground(Color.white);
           titl.setPreferredSize(new Dimension(1, 30));

           frame = new JFrame("Welcome to Jan's Wholesale & Retail");
           frame.setLayout(new MigLayout("", "[][]", " [] [] [] []"));

           //labels
           email = new JLabel("Email: ");
           Id = new JLabel("ID Number:");

           //buttons
           login = new JButton("Login");
           cancel = new JButton("Cancel");

           //text box
           emailTxt = new JTextField(20);
           IdTxt = new JTextField(20);

           //Icon
           ImageIcon img = new ImageIcon("C:\\Users\\johns\\IdeaProjects\\WholesaleAP\\src\\Image\\Jan.png");
           frame.setIconImage(img.getImage());

           //Image
           lock = new JLabel();
           lock.setIcon(new ImageIcon("C:\\Users\\johns\\IdeaProjects\\WholesaleAP\\src\\Image\\Person.png"));

           //Font
           Font myFont = new Font("Serif", Font.ITALIC | Font.BOLD, 10);
           Font newFont = myFont.deriveFont(20F);

           titl.setFont(newFont);

           login.addActionListener(new ActionListener() {
               @Override
               public void actionPerformed(ActionEvent e) {
                   JOptionPane.showMessageDialog(frame, "Login Successful", "Login", JOptionPane.INFORMATION_MESSAGE);
               }
           });

       }

       public void addComponentsToFrame(){
           lock.setSize(new Dimension(10, 20));
           lock.setBounds(18, 20, 8, 10);
           // Title
           frame.add(titl, "center, wrap 15, span 3, growx, dock north");

           //Image
           frame.add(lock, "left, sg 1, span 1, wrap, dock west");

            //email frame
           frame.add(email, "  gapleft 30, sg 2, split 2, gaptop 20");
           frame.add(emailTxt, "pushx, growx, wrap 15");

           //Id frame
           frame.add(Id, "gapleft 30, sg 2, split 2");
           frame.add(IdTxt, "pushx, growx, wrap 15");

           //button
           frame.add(login, "center, split 2");
           frame.add(cancel, "center, split 2 ");

       }

       public void setWindowProperties(){
        //frame settings
           frame.setResizable(true);
           frame.setVisible(true);
           frame.setLocationRelativeTo(null);
           frame.pack();
           frame.setSize(500, 200);
           frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
       }


    public static void main (String[] args){
        new gabbymess();
        }
}
