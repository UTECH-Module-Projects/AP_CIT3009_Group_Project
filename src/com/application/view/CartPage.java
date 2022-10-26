package com.application.view;
import net.miginfocom.swing.MigLayout;
import org.hibernate.type.descriptor.JdbcBindingLogging;

import javax.swing.*;
import java.awt.*;
public class CartPage {

    private JFrame frame;
    private JLabel item, description, quantity, price, total, shopBag, titl;
    private JButton checkOut;

    public CartPage(){
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
        titl = new JLabel("JAN'S WHOLESALE & RETAIL", JLabel.CENTER);
        titl.setOpaque(true);
        titl.setBackground(Color.black);
        titl.setForeground(Color.white);
        titl.setPreferredSize(new Dimension(1, 30));

        frame = new JFrame("Welcome to Jan's Wholesale & Retail");
        frame.setLayout(new MigLayout("debug", "[] [] []", " [] [] [] []"));

        //item
        item = new JLabel("Item");

        //Desc
        description = new JLabel("Description");

        //Quantity
        quantity = new JLabel("Quantity");

        //Price
        price = new JLabel("Price");

        //Total
        total = new JLabel("Total");

        //Shopping bag header
        shopBag = new JLabel("Shopping Bag");

        //Button
        checkOut = new JButton("CHECKOUT");

        //Icon
        ImageIcon img = new ImageIcon("C:\\Users\\johns\\IdeaProjects\\WholesaleAP\\src\\Image\\Jan.png");
        frame.setIconImage(img.getImage());

        Font myFont = new Font("Serif", Font.ITALIC | Font.BOLD, 10);
        Font newFont = myFont.deriveFont(20F);

        titl.setFont(newFont);
    }

    public void addComponentsToFrame(){

        // Title
        frame.add(titl, "center, wrap 15, span 3, growx, dock north");
/*
        frame.add(shopBag, "left, span 3, wrap");
      //  frame.add(new JSeparator());

        frame.add(item, "left, sg 1, split 2");
        frame.add(description, "left, sg 1");
        frame.add(quantity, "left, sg 1");
        frame.add(total, "left, sg 1");
        //frame.add()

        frame.add(price, "left, wrap");

        frame.add(checkOut,"right, span 2");*/

    }

    public void setWindowProperties(){
        frame.setResizable(true);
        frame.setVisible(true);
        frame.setLocationRelativeTo(null);
        frame.pack();
        frame.setSize(500, 300);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    public static void main(String [] args){
        new CartPage();
    }
}
