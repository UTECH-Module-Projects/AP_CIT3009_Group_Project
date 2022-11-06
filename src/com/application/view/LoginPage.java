package com.application.view;

import net.miginfocom.swing.MigLayout;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;

public class LoginPage {
    private JFrame frame;
    private JLabel Id, email, titl, titl2, titl3, titl4, lock, loginpage, clock;
    private JTextField emailTxt, IdTxt;
    private JButton login, cancel;
    private MyPanel panel1, bgPage;




    public LoginPage(){
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
    public void initializeComponents() {

        titl = new JLabel("Jan's Wholesale & Retail", JLabel.CENTER);
        titl.setForeground(Color.white);
        titl4 = new JLabel("Login to Your Account");
        titl4.setForeground(Color.white);
        Dimension size = titl4.getPreferredSize();
        titl4.setBounds(140, 100, size.width, size.height);


           /*titl.setOpaque(true);
           titl.setBackground(Color.black);
           titl.setForeground(Color.white);
           titl.setPreferredSize(new Dimension(1, 30));*/

        frame = new JFrame("Welcome to Jan's Wholesale & Retail");
        frame.setLayout(new MigLayout(" insets 60 60 20 60, fillx", "[][]", " [] [] [] []"));

        //Icon
        ImageIcon img = new ImageIcon("C:\\Users\\johns\\IdeaProjects\\WholesaleAP\\src\\Image\\Jan.png");
        frame.setIconImage(img.getImage());

        //labels
        email = new JLabel("Email: ");
        email.setForeground(Color.white);
        Id = new JLabel("ID Number:");
        Id.setForeground(Color.white);


        //buttons
        login = new JButton("Login");
        cancel = new JButton("Cancel");

        //text box
        emailTxt = new JTextField(20);
        emailTxt.setText("Enter Email");
        emailTxt.addFocusListener(new FocusListener() {
            @Override
            public void focusGained(FocusEvent e) {
                if(emailTxt.getText().equals("Enter Email")){
                    emailTxt.setText("");
                }
            }

            @Override
            public void focusLost(FocusEvent e) {
                if (emailTxt.getText().equals("")){
                    emailTxt.setText("Enter Email");
                }

            }
        });
        // emailTxt.setBorder(new EmptyBorder(20, 3, 1, 3));

        // emailTxt.setBorder(null);
        IdTxt = new JTextField(20);
        IdTxt.setText("Enter ID Number");
        IdTxt.addFocusListener(new FocusListener() {
            @Override
            public void focusGained(FocusEvent e) {
                if(IdTxt.getText().equals("Enter ID Number")){
                    IdTxt.setText("");
                }
            }

            @Override
            public void focusLost(FocusEvent e) {
                if (IdTxt.getText().equals("")){
                    IdTxt.setText("Enter ID Number");
                }

            }
        });



          /* ImageIcon circle = new ImageIcon("C:\\Users\\johns\\IdeaProjects\\WholesaleAP\\src\\Image\\loginpage.png");
           loginpage = new JLabel(circle);
           loginpage.setSize(new Dimension(800,600));*/

           /*loginpage= new JLabel();
           loginpage.setIcon(new ImageIcon("C:\\Users\\johns\\IdeaProjects\\WholesaleAP\\src\\Image\\loginpage.png"));
          // loginpage.setLayout(new BorderLayout());
           loginpage.setBounds(0,0,800,600);*/

          /* //Image
           lock = new JLabel();
           lock.setIcon(new ImageIcon("C:\\Users\\johns\\IdeaProjects\\WholesaleAP\\src\\Image\\Person.png"));*/

        //Font
        Font myFont = new Font("Garamond", Font.ITALIC | Font.BOLD, 10);
        Font newFont = myFont.deriveFont(50F);

        Font myFont2 = new Font("Serif", Font.ITALIC | Font.BOLD, 8);
        Font nextFont = myFont2.deriveFont(18F);

        Font myFont3 = new Font("Monospaced", Font.BOLD, 10);
        Font nextFont2 = myFont3.deriveFont(25F);

        Font myFont4 = new Font("Monospaced", Font.BOLD, 10);
        Font nextFont3= myFont4.deriveFont(15F);

        titl.setFont(newFont);
        titl4.setFont(nextFont2);
        email.setFont(nextFont3);
        Id.setFont(nextFont3);

        cancel.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

            }
        });

        login.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JOptionPane.showMessageDialog(frame, "Login Successful", "Login", JOptionPane.INFORMATION_MESSAGE);
            }
        });
    }



    public void addComponentsToFrame(){
         /*lock.setSize(new Dimension(10, 20));
           lock.setBounds(18, 20, 8, 10);
           // Title
           frame.add(titl, "center, wrap, span, growx, dock north");

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
           frame.add(cancel, "center, split 2 ");*/

        frame.getContentPane().setBackground(new java.awt.Color(36, 36, 36));

        frame.add(titl, "center, span, grow, split, north");
          /* frame.add(titl2, "center, span, grow, split, north");
           frame.add(titl3, "center, span, grow, split, north, wrap");*/
        frame.add(titl4, "center, grow, wrap");

        //email frame
        frame.add(email, "  gaptop 40, wrap ");
        frame.add(emailTxt, "pushx, growx, wrap 15");

        //Id frame
        frame.add(Id, " wrap");
        frame.add(IdTxt, "pushx, growx, wrap 15");

        //buttons
        frame.add(login, "gaptop 20, center, split , span ");
        frame.add(cancel, "center, split ");

        //frame.add(panel1);

    }

    public void setWindowProperties(){
        //frame settings
        frame.setResizable(true);
        frame.setVisible(true);
        frame.setLocationRelativeTo(null);

        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setSize(800, 500);
    }


    public static void main (String[] args){
        new LoginPage();
    }
}
