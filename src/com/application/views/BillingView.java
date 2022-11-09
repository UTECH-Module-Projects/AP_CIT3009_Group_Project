import java.awt.Button;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.RowFilter;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
//import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;
import javax.swing.table.TableRowSorter;

import com.formdev.flatlaf.FlatDarkLaf;

import net.miginfocom.swing.MigLayout;

public class BillingView extends JFrame implements ActionListener{
	private JPanel searchP;
	private JTable stockTable;
	private JPanel cartPanel;
	private TableRowSorter<TableModel> rowSorter;
	private int searchIDColumnIndex = 0;
	private int searchNameColumnIndex = 1;
	//id, name, stock, price
	private String data[][]= {
			{"1", "Grapes", "5", "500"},
			{"2", "Gelato", "15", "300"},
			{"3", "Gum", "50", "500"},
			{"4", "Red Wine", "10", "5000"}
	};
	
	private String[] column = {"ID","Name","Quantity","Price"};
	
	//for search panel
	private JLabel idSLabel;
	private JLabel nameSLabel;
	private JLabel searchL;
	private JTextField idTF;
	private JTextField nameTF;
	
	String[] options = {"ID No.", "Product Name"};
	private JComboBox searchHeader = new JComboBox(options);
	private JTextField cbTF;
	
	//for cart
	private JTextArea cartOutput;
	private Button billBtn;
	private Button clearBtn;
	private Button undoBtn;
	private Button redoBtn;
	
	
	BillingView() {
		searchP = new JPanel(new MigLayout());
		stockTable = new JTable(data, column);
		cartPanel = new JPanel(new MigLayout());
		rowSorter = new TableRowSorter<>(stockTable.getModel());
		
		setSearchP();
		setCartP();
		setTable();
		
		initializeWindow();
	}
	
	public void setSearchP() {
		searchHeader.setBounds(50, 50,90,20);   
		cbTF = new JTextField();
		
		idSLabel = new JLabel("Product ID: ");
		nameSLabel = new JLabel("Product Name: ");
		searchL = new JLabel("Search");
		
		idTF = new JTextField();
		nameTF = new JTextField();
		
		//row sorting
		stockTable.setRowSorter(rowSorter);
		
		//cbSorter();
		
		
		//cbTF.setSelectedItem(options[0]);
		//search by name
		//searchNameColumnIndex = 1;
		nameTF.getDocument().addDocumentListener(new DocumentListener(){

            @Override
            public void insertUpdate(DocumentEvent e) {
                String text = nameTF.getText();

                if (text.trim().length() == 0) {
                    rowSorter.setRowFilter(null);
                } else {
                	// regex ?i will do case INsensitive matches , opposite is ?-i
                    rowSorter.setRowFilter(RowFilter.regexFilter("(?i)" + text, searchNameColumnIndex));
                }
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
                String text = nameTF.getText();

                if (text.trim().length() == 0) {
                    rowSorter.setRowFilter(null);
                } else {
                    rowSorter.setRowFilter(RowFilter.regexFilter("(?i)" + text, searchNameColumnIndex));
                }
            }

            @Override
            public void changedUpdate(DocumentEvent e) {
                throw new UnsupportedOperationException("Not supported yet."); 
                //To change body of generated methods, choose Tools | Templates.
            }

        });
		
		//search by id
		searchIDColumnIndex = 0;
		idTF.getDocument().addDocumentListener(new DocumentListener(){

            @Override
            public void insertUpdate(DocumentEvent e) {
                String text = idTF.getText();

                if (text.trim().length() == 0) {
                    rowSorter.setRowFilter(null);
                } else {
                	// regex ?i will do case INsensitive matches , opposite is ?-i
                    rowSorter.setRowFilter(RowFilter.regexFilter("(?i)" + text, searchIDColumnIndex));
                }
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
                String text = idTF.getText();

                if (text.trim().length() == 0) {
                    rowSorter.setRowFilter(null);
                } else {
                    rowSorter.setRowFilter(RowFilter.regexFilter("(?i)" + text, searchIDColumnIndex));
                }
            }

            @Override
            public void changedUpdate(DocumentEvent e) {
                throw new UnsupportedOperationException("Not supported yet."); 
                //To change body of generated methods, choose Tools | Templates.
            }

        });
		
		searchP.add(searchHeader);
		searchP.add(cbTF, "wrap");
		searchP.add(searchL);
		searchP.add(nameSLabel);
		searchP.add(nameTF);
		searchP.add(idSLabel);
		searchP.add(idTF);
	}
	
	
	//doesn't work combo box options sorter
	public void cbSorter() {
		/*
		 * cbTF.addActionListener(new ActionListener() {
		 * 
		 * @Override public void actionPerformed(ActionEvent e) { String sItem =
		 * (String)searchHeader.getSelectedItem(); if (sItem.equals("Product Name")) {
		 * //name searchNameColumnIndex = 1; //combobox search for name
		 * cbTF.getDocument().addDocumentListener(new DocumentListener(){
		 * 
		 * @Override public void insertUpdate(DocumentEvent e) { String text =
		 * cbTF.getText();
		 * 
		 * if (text.trim().length() == 0) { rowSorter.setRowFilter(null); } else { //
		 * regex ?i will do case INsensitive matches , opposite is ?-i
		 * rowSorter.setRowFilter(RowFilter.regexFilter("(?i)" + text,
		 * searchNameColumnIndex)); } }
		 * 
		 * @Override public void removeUpdate(DocumentEvent e) { String text =
		 * cbTF.getText();
		 * 
		 * if (text.trim().length() == 0) { rowSorter.setRowFilter(null); } else {
		 * rowSorter.setRowFilter(RowFilter.regexFilter("(?i)" + text,
		 * searchNameColumnIndex)); } }
		 * 
		 * @Override public void changedUpdate(DocumentEvent e) { throw new
		 * UnsupportedOperationException("Not supported yet."); //To change body of
		 * generated methods, choose Tools | Templates. }
		 * 
		 * }); } else if (sItem.equals("ID No.")) { //id no searchIDColumnIndex = 0;
		 * //combobox search for name cbTF.getDocument().addDocumentListener(new
		 * DocumentListener(){
		 * 
		 * @Override public void insertUpdate(DocumentEvent e) { String text =
		 * cbTF.getText();
		 * 
		 * if (text.trim().length() == 0) { rowSorter.setRowFilter(null); } else { //
		 * regex ?i will do case INsensitive matches , opposite is ?-i
		 * rowSorter.setRowFilter(RowFilter.regexFilter("(?i)" + text,
		 * searchIDColumnIndex)); } }
		 * 
		 * @Override public void removeUpdate(DocumentEvent e) { String text =
		 * cbTF.getText();
		 * 
		 * if (text.trim().length() == 0) { rowSorter.setRowFilter(null); } else {
		 * rowSorter.setRowFilter(RowFilter.regexFilter("(?i)" + text,
		 * searchIDColumnIndex)); } }
		 * 
		 * @Override public void changedUpdate(DocumentEvent e) { throw new
		 * UnsupportedOperationException("Not supported yet."); //To change body of
		 * generated methods, choose Tools | Templates. }
		 * 
		 * }); } } });
		 */
	}
	
	public void setCartP() {
		billBtn = new Button("Bill");
		clearBtn = new Button("Clear");
		undoBtn = new Button("Undo");
		redoBtn = new Button("Redo");
		
		clearBtn.addActionListener(this);
		billBtn.addActionListener(this);
		undoBtn.addActionListener(this);
		redoBtn.addActionListener(this);
		
		cartOutput = new JTextArea("Cart Output");
		cartOutput.setEditable(true);
		cartOutput.setColumns(4);
		cartOutput.setPreferredSize(new Dimension(200, 200)); 
		
		cartPanel.add(cartOutput);
		
		cartPanel.add(billBtn);
		cartPanel.add(clearBtn);
		cartPanel.add(undoBtn);
		cartPanel.add(redoBtn);
	}
	
	public void setTable() {
		//add actionlistener
		//doesnt work
		stockTable.addMouseListener(new MouseListener() {
			public void mouseClicked(MouseEvent me) {
				if (me.getClickCount()== 2)  {
					JTable target = (JTable)me.getSource();
			    	//int row = target.getSelectedRow();
			    	//int column = target.getSelectedColumn();
					String value = target.getValueAt(target.getSelectedRow(), 0).toString();
			    	
			    	cartOutput.insert(value, 0);
				}
			}

			@Override
			public void mousePressed(MouseEvent e) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void mouseReleased(MouseEvent e) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void mouseEntered(MouseEvent e) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void mouseExited(MouseEvent e) {
				// TODO Auto-generated method stub
				
			}
		});

	}
	
	public void initializeWindow() {
		this.setLayout(new MigLayout("debug, fillx", "[grow][][]"));
        this.setSize(900, 500);
		this.setVisible(true);
		
		//column row width height
		this.add(searchP, "span 2");
		this.add(stockTable, "span 2, cell 0 1");
		this.add(cartPanel, "cell 2 0, growx");
		
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}
	
	public static void main(String[] args) {
		FlatDarkLaf.setup();
		new BillingView();
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		if (e.getSource() == clearBtn) {
			//clear text area
		} 
		if (e.getSource() == billBtn) {
			//save invoice here
		} 
		if (e.getSource() == undoBtn) {
			//undo text area
		} 
		if (e.getSource() == redoBtn) {
			//redo text area
		} 
		
	}

}
