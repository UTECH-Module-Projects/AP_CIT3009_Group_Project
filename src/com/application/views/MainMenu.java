package com.application.views;

import java.awt.Button;
import java.awt.Dimension;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.ButtonGroup;
import javax.swing.ImageIcon;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JTabbedPane;
import javax.swing.JTextField;

import net.miginfocom.swing.MigLayout;

import com.formdev.flatlaf.FlatDarkLaf;
//import com.formdev.flatlaf.FlatLightLaf;

public class MainMenu extends JFrame implements ActionListener{
	private JPanel mainPanel;
	private JMenuBar menuBar;
	private JMenu view;
	private JMenuItem logout, cust, emp, stock, report;
	private Button custB, empButton, reportButton, stockButton;
	private BufferedImage custIcon, empIcon, reportIcon, stockIcon;
	private JLabel custLabel, empLabel, reportLabel, stockLabel;
	private JTabbedPane mainTP;
	private JPanel customerPanel, empPanel, stockPanel, reportPanel;
	private String tempId = "";
	
	MainMenu() {
		mainPanel = new JPanel();
		
		mainTP = new JTabbedPane(JTabbedPane.LEFT);
		
		menuBar = new JMenuBar();
		
		view = new JMenu("View");
		logout = new JMenuItem("Logout");
		logout.addActionListener(this);
		
		cust = new JMenuItem("Customer");
		emp = new JMenuItem("Employee");
		report = new JMenuItem("Generate Report");
		stock = new JMenuItem("Inventory/Stock");
		
		custB = new Button("Customer");
		empButton = new Button("Employee");
		reportButton = new Button("Generate Report");
		stockButton = new Button("Inventory");
		
		try {
			custIcon = ImageIO.read(new File("./src/com/application/views/images/customer_icon.png"));
			empIcon = ImageIO.read(new File("./src/com/application/views/images/white-person-icon.png"));
			stockIcon = ImageIO.read(new File("./src/com/application/views/images/stock-icon.png"));
			reportIcon = ImageIO.read(new File("./src/com/application/views/images/report-icon-sign.png"));
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		//iconGraphics = custIcon.createGraphics();
		
		custLabel = new JLabel(new ImageIcon(custIcon.getScaledInstance(100, 100, Image.SCALE_DEFAULT)));
		
		empLabel = new JLabel(new ImageIcon(empIcon.getScaledInstance(100, 100, Image.SCALE_DEFAULT)));
		
		reportLabel = new JLabel(new ImageIcon(reportIcon.getScaledInstance(100, 100, Image.SCALE_DEFAULT)));
		
		stockLabel = new JLabel(new ImageIcon(stockIcon.getScaledInstance(100, 100, Image.SCALE_DEFAULT)));
		
		initializeWindow();
		
	}
	
	public void initializeWindow() {
		this.setLayout(new MigLayout());
        this.setSize(900, 500);
        //this.setResizable(true);
        //JPanel mainPanel2 = new JPanel();
		
		view.add(logout); view.add(cust); view.add(emp); view.add(report); view.add(stock);   
		menuBar.add(view);
		
		//setMainPanel();
        
        this.add(menuBar);
        this.setJMenuBar(menuBar);
        //this.add(mainPanel);   
        
        setCustomerPanel();
        setEmpPanel();
        setReportPanel();
        setInventoryPanel();
        
        //mainTP.setPreferredSize(new Dimension(900, 500));
        
        mainTP.add("Customer",customerPanel);
        mainTP.add("Employee", empPanel);
        mainTP.add("Reports", reportPanel);
        mainTP.add("Inventory", stockPanel);
        
        this.add(mainTP);
        
        this.setTitle("Jans Wholesale Management System");
        
        this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        this.pack();
        this.setVisible(true);
	}

	public void setMainPanel() {
		mainPanel.setLayout(new MigLayout());
        mainPanel.setPreferredSize(new Dimension(900, 500));
        
        mainPanel.add(custLabel, "gap 10 10, pad 10 10 10 10, dock center");
        mainPanel.add(empLabel, "wrap 2, gap 10 10, pad 10 10 10 10, dock center"); 
        
        mainPanel.add(custB, "width 30, gap 10 10, , pad 10 10 10 10, dock center"); 
        mainPanel.add(empButton, "wrap 2, gap 10 10, pad 10 10 10 10, dock center");
        
        mainPanel.add(reportLabel, "gap 10 10, pad 10 10 10 10, dock center"); 
        mainPanel.add(stockLabel, "wrap 2, gap 10 10, , pad 10 10 10 10, dock center"); 
        mainPanel.add(reportButton, "gap 10 10, pad 10 10 10 10, dock center");
        mainPanel.add(stockButton, "gap 10 10, pad 10 10 10 10, dock center");
	}
	
	public void setCustomerPanel() {
		//switches panel to customer panel
		//new CustomerView();
		customerPanel = new JPanel();
        customerPanel.setPreferredSize(new Dimension(700, 400));
		
        JTabbedPane subPane = new JTabbedPane();
        
        //add customer
        JPanel addP = new JPanel();
        addP.setPreferredSize(new Dimension(700, 400));
        addP = getForm("customer");

        //remove customer
        JPanel removeP = new JPanel();
        removeP.setPreferredSize(new Dimension(700, 400));
        removeP = removePerson("Customer");
        
        //view customer
        JPanel viewP = new JPanel();
        viewP.setPreferredSize(new Dimension(700, 400));
        viewP = viewPersons("Customer");
        
        JPanel updateP = new JPanel();
        updateP.setPreferredSize(new Dimension(700, 400));
        
        
        subPane.add("Add Customer", addP);
        subPane.add("View Customer", viewP);
        subPane.add("Remove Customer", removeP);
        subPane.add("Update Customer", updateP);
        
        customerPanel.add(subPane);
	}
	
	public JPanel viewPersons(String pType) {
		JPanel vP = new JPanel();
		vP.setLayout(new MigLayout());
		//insert table here
		if (pType.equals("Customer")){
			//Search customer database 
			//gets info and displays to user
	
		} else if (pType.equals("Employee")) {
			//search and print from employee table
		}
		
		return vP;
		
	}
	
	public JPanel removePerson(String pType) {
		JPanel rP = new JPanel();
		rP.setLayout(new MigLayout());
		JLabel title = new JLabel("Remove " + pType);
		JLabel id = new JLabel("Enter " + pType + " ID No.");
		JTextField idTF = new JTextField();
		Button submit  = new Button("Submit");
		
		
		submit.addActionListener(new ActionListener() {
	        @Override
	        public void actionPerformed(ActionEvent e) {
	            tempId = idTF.getText(); 
	            if (pType.equals("Customer")){
	    			//Search customer database 
	    			//gets info and displays to user
	    	
	    		} else if (pType.equals("Employee")) {
	    			//search and print from employee table
	    		}
	        }
	    });
		
		rP.add(title, "wrap");
		rP.add(id);
		rP.add(idTF);
		rP.add(submit);
		
		return rP;
	}
	
	public JPanel getForm(String user) {
		JPanel addP = new JPanel();
		addP.setLayout(new MigLayout());
		
		Button saveB = new Button("Save and Next");
		
		//Mem date, mem exp date,,, id name, dob, address, phone number, email
        JLabel dob = new JLabel("D.O.B (mm/dd/yyyy)");
        JLabel id = new JLabel("ID No.");
        JLabel name = new JLabel("Full Name");
        JLabel address = new JLabel("Address");
        JLabel phoneNo = new JLabel("Telephone No.");
        JLabel email = new JLabel("Email");
        JLabel checkDomStat = new JLabel("Is a Member?");
        JLabel domYes = new JLabel("Yes");
        JLabel domNo = new JLabel("No");
                
        //customer specific
        JLabel dom = new JLabel("Membership Date (mm/dd/yyyy)");
        JLabel dome = new JLabel("Membership Expiry Date (mm/dd/yyyy)");
        
        //employee specific
        JLabel tOfEmps = new JLabel("Employee Type");
        JLabel tOfDeps = new JLabel("Department Type");
        
        
        
        JTextField nameTF = new JTextField(15);
        JTextField idTF = new JTextField(5);
        JTextField emailTF = new JTextField(15);
        JTextField addressTF = new JTextField();
        addressTF.setPreferredSize(new Dimension(250,70));
        JTextField phoneTF = new JTextField(10);
        JTextField domYearTF = new JTextField();
        JTextField domeYearTF = new JTextField();
        JTextField dobYearTF = new JTextField();
        
        JRadioButton yes = new JRadioButton();
        JRadioButton no = new JRadioButton();
        ButtonGroup memberStat = new ButtonGroup();
        memberStat.add(no);
        memberStat.add(yes);
        
        String[] dates
                = { "1", "2", "3", "4", "5",
                    "6", "7", "8", "9", "10",
                    "11", "12", "13", "14", "15",
                    "16", "17", "18", "19", "20",
                    "21", "22", "23", "24", "25",
                    "26", "27", "28", "29", "30",
                    "31" };
        
        String[] months
                = { "Jan", "Feb", "Mar", "Apr",
                    "May", "Jun", "July", "Aug",
                    "Sup", "Oct", "Nov", "Dec" };
        
        String[] typeEmps
        		= {"Manager", "Supervisor", "Line Worker"};
        
        String[] typeDeps
				= {"Management", "Inventory", "Accounting and Sales"};
        		
        JComboBox<String> typeEmp = new JComboBox<String>(typeEmps);
        JComboBox<String> typeDep = new JComboBox<String>(typeDeps);
        JComboBox<String> date = new JComboBox<String>(dates);
        JComboBox<String> month = new JComboBox<String>(months);
        JComboBox<String> date2 = new JComboBox<String>(dates);
        JComboBox<String> month2 = new JComboBox<String>(months);
        JComboBox<String> date3 = new JComboBox<String>(dates);
        JComboBox<String> month3 = new JComboBox<String>(months);
        
        //adding form to panel
        addP.add(id);
        addP.add(idTF, "wrap");
        
        addP.add(name);
        addP.add(nameTF, "wrap");
        
        addP.add(dob);
        addP.add(date);
        addP.add(month);
        addP.add(dobYearTF, "wrap");
        
        addP.add(email);
        addP.add(emailTF, "wrap");
        
        addP.add(phoneNo);
        addP.add(phoneTF, "wrap");
        
        addP.add(address);
        addP.add(addressTF, "wrap");
        
		
		if (user.equals("customer")) {
			addP.add(checkDomStat);
	        addP.add(domYes);
	        addP.add(yes);
	        addP.add(domNo);
	        addP.add(no, "wrap");
	        
	        yes.addActionListener(new ActionListener() {
	        	@Override
	            public void actionPerformed(ActionEvent e) {
	        		addP.add(dom);
	    	        addP.add(date2);
	    	        addP.add(month2);
	    	        addP.add(domYearTF, "wrap");
	    	        
	    	        addP.add(dome);
	    	        addP.add(date3);
	    	        addP.add(month3);
	    	        addP.add(domeYearTF, "wrap");
	    	        addP.add(saveB);
	            }
	        });
	        
	        no.addActionListener(new ActionListener() {
	        	@Override
	            public void actionPerformed(ActionEvent e) {
	    	        addP.add(saveB);
	            }
	        });
	        
		} else if (user.equals("employee")) {
			addP.add(tOfEmps);
	        addP.add(typeEmp, "wrap");
	     	        
	        addP.add(tOfDeps);
	        addP.add(typeDep, "wrap");
		}
		
		
		return addP;
		
	}
	
	public void setEmpPanel() {
		empPanel  = new JPanel(); 
        empPanel.setPreferredSize(new Dimension(700, 400));
        
        JTabbedPane subPane = new JTabbedPane();
        
        //add customer
        JPanel addP = new JPanel();
        addP.setPreferredSize(new Dimension(700, 400));
        addP = getForm("employee");

        //remove customer
        JPanel removeP = new JPanel();
        removeP.setPreferredSize(new Dimension(700, 400));
        removeP = removePerson("Employee");
        
        //view customer
        JPanel viewP = new JPanel();
        viewP.setPreferredSize(new Dimension(700, 400));
        viewP = viewPersons("Employee");
        
        JPanel updateP = new JPanel();
        updateP.setPreferredSize(new Dimension(700, 400));
        
        
        subPane.add("Add Employee", addP);
        subPane.add("View Employee", viewP);
        subPane.add("Remove Employee", removeP);
        subPane.add("Update Employee", updateP);
        
        empPanel.add(subPane);
	}
	
	public void setReportPanel() {
		reportPanel = new JPanel(); 
        reportPanel.setPreferredSize(new Dimension(700, 400));
	}
	
	public void setInventoryPanel() {
		stockPanel  = new JPanel(); 
        stockPanel.setPreferredSize(new Dimension(700, 400));
	}
					
	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		if (e.getSource() == logout) {
            int result = JOptionPane.showConfirmDialog(this,"Are you sure you want to Logout?", "Confirmation Logout",
                    JOptionPane.YES_NO_OPTION,
                    JOptionPane.QUESTION_MESSAGE);
             if(result == JOptionPane.YES_OPTION){
            	 this.setVisible(false);
             	dispose();
             }else if (result == JOptionPane.NO_OPTION){
                //label.setText("You selected: No");
             }else {
                //label.setText("None selected");
             }
		}
		
	}
	
	public static void main(String[] args) {
		FlatDarkLaf.setup();
		new MainMenu();
	}

}
