package com.application.view;

import net.miginfocom.swing.MigLayout;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.text.SimpleDateFormat;
import java.util.Calendar;

public class CartView {
    private JFrame frame;
    private JLabel item, description, quantity, price, total, shopBag, lock, border;
    private JLabel head1, head2, head3, phoneNum, date, time, InvoiceNum, custID, message, disco, tot, compEmail;
    private JTextField itemTxt;


    Calendar calendar;
    SimpleDateFormat timeFormat, dayFormat;
    JLabel timeLabel, dateLabel;
    private String timer, day;


    public CartView(){
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
        frame = new JFrame("Welcome to Jan's Wholesale & Retail");
        //frame.setLayout(new MigLayout("insets 15, debug, fill", "[][]", "[][][]"));
        frame.setLayout(new MigLayout(" insets 40 60 20 60, fillx", "[][]", " [] [] [] []"));

        //Address
        head1 = new JLabel("JAN'S WHOLESALE & RETAIL", JLabel.CENTER);
        head1.setForeground(Color.white);
        head2 = new JLabel("234 ColdLane Ave", JLabel.CENTER);
        head2.setForeground(Color.white);
        head3 = new JLabel(" Jacks Town 10", JLabel.CENTER);
        head3.setForeground(Color.white);
        phoneNum = new JLabel("Tel: +1(876)987-6544", JLabel.CENTER);
        phoneNum.setForeground(Color.white);
        compEmail = new JLabel("jansWR@gmail.com", JLabel.CENTER);
        compEmail.setForeground(Color.white);


        //date
        date = new JLabel("Date:");
        date.setForeground(Color.white);
        dayFormat = new SimpleDateFormat("MM/dd/yyyy");
        dateLabel = new JLabel();
        dateLabel.setForeground(Color.white);
        day = dayFormat.format(Calendar.getInstance().getTime());
        dateLabel.setText(day);
        Dimension size = date.getPreferredSize();
        date.setBounds(140, 100, size.width, size.height);

        //Time
        time = new JLabel("Time:");
        time.setForeground(Color.white);
        timeFormat = new SimpleDateFormat("hh:mm:ss a");
        timeLabel = new JLabel();
        timeLabel.setForeground(Color.white);
        timer = timeFormat.format(Calendar.getInstance().getTime());
        timeLabel.setText(timer);
        // setTime();

        InvoiceNum = new JLabel("Invoice Number: ");
        InvoiceNum.setForeground(Color.white);

        custID = new JLabel("Customer ID: ");
        custID.setForeground(Color.white);

        ImageIcon icon = new ImageIcon("C:\\Users\\johns\\IdeaProjects\\WholesaleAP\\src\\Image\\cart.png");
        shopBag = new JLabel("YOUR RECEIPT ", JLabel.CENTER);
        shopBag.setForeground(Color.white);
        shopBag.setIcon(icon);

        message = new JLabel("Thanks for shopping with Jan :)");
        message.setForeground(Color.white);
        message.setBounds(0, 300, 0,0 );

        quantity = new JLabel("QTY");
        quantity.setForeground(Color.white);


        item = new JLabel("ITEMS ");
        item.setForeground(Color.white);
        itemTxt = new JTextField(10);

        price = new JLabel("PRICE");
        price.setForeground(Color.white);

        disco = new JLabel("DISC%");
        disco.setForeground(Color.white);

        tot = new JLabel("TOTAL");
        tot.setForeground(Color.white);


        //Icon
        ImageIcon img = new ImageIcon("C:\\Users\\johns\\IdeaProjects\\WholesaleAP\\src\\Image\\Jan.png");
        frame.setIconImage(img.getImage());

        //Font
        Font myFont = new Font("Monospaced", Font.ITALIC | Font.BOLD, 10);
        Font newFont = myFont.deriveFont(18F);

        Font myFont2 = new Font("Monospaced", Font.ITALIC | Font.BOLD, 10);
        Font newFont2 = myFont2.deriveFont(15F);

        Font myFont3 = new Font("Monospaced", Font.ITALIC | Font.BOLD, 10);
        Font newFont3 = myFont3.deriveFont(13F);

        Font myFont4 = new Font("Monospaced", Font.ITALIC | Font.BOLD, 10);
        Font newFont4 = myFont4.deriveFont(30F);

        head1.setFont(newFont);
        head2.setFont(newFont3);
        head3.setFont(newFont3);
        compEmail.setFont(newFont3);
        phoneNum.setFont(newFont3);
        date.setFont(newFont2);
        time.setFont(newFont2);
        InvoiceNum.setFont(newFont2);
        custID.setFont(newFont2);
        shopBag.setFont(newFont4);

        item.setFont(newFont2);
        quantity.setFont(newFont2);
        price.setFont(newFont2);
        disco.setFont(newFont2);
        tot.setFont(newFont2);

        description = new JLabel("Description");



       /* border= new JLabel();
        Border LineBorder = BorderFactory.createLineBorder(Color.CYAN);

        border.setBorder(LineBorder);
        */

    }

   public void setTime() {
     while(true){
    timer = timeFormat.format(Calendar.getInstance().getTime());
    timeLabel.setText(timer);
        try{
            Thread.sleep(1000);
        }catch (InterruptedException e){
            e.printStackTrace();
        }

     }
    }

    public void addComponentsToFrame(){
/*
        //frame.add(title, " pushx, span, growx, wrap ");
        frame.add(title, "center, wrap, span, growx, dock north");
        frame.add(shopBag, " center , growx, gaptop 20, wrap");
        frame.add(item, "gaptop 15, span, split 2");
        frame.add(description, "span, growx");*/
        frame.getContentPane().setBackground(new java.awt.Color(36, 36, 36));
//heading
        frame.add(head1,"north");
        frame.add(head2,"north");
        frame.add(head3,"north");
        frame.add(phoneNum,"north");
        frame.add(compEmail,"north");

        //date
        frame.add(date,"split");
        frame.add(dateLabel, "split");

        //Customer ID Label
        frame.add(custID, " gapleft 66, wrap");


        //time
        frame.add(time,"split");
        frame.add(timeLabel ,"split");

        //Invoice label
        frame.add(InvoiceNum, " gapleft 60, wrap");

        //top labels
        frame.add(shopBag, " gapleft 20, split, gaptop 20, wrap");


        frame.add(item," gaptop 20, split");
        //frame.add(itemTxt, "pushx,  span");

        frame.add( quantity, "gapleft 100, split");

        frame.add(price, "gapleft 20, split");
        frame.add(disco, "gapleft 20");
        frame.add(tot, "gapleft 20");


        //frame.add(message,"south");
        //frame.add(border);
    }


    public void setWindowProperties(){
        frame.setResizable(true);
        frame.setVisible(true);
        frame.setLocationRelativeTo(null);

        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setSize(600, 700);

    }

    public static void main (String [] args){
        new CartView();
    }
}
