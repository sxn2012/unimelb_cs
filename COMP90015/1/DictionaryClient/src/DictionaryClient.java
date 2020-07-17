/*
 * Author: Xinnan SHEN
 * Student ID: 1051380
 * Date: 28/08/2019
 * 
 */
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JTextField;
import java.awt.TextArea;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.Font;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class DictionaryClient {

	private JFrame frmDictionaryclient;
	private JTextField textWord;
	private TextArea textMeaning;
	private JLabel lblWord,lblMeaning;
	private JButton btnConfirm,btnClear,btnQuery,btnAdd,btnRemove;
	private static int act=-1;//action of client
	private static String server_address_;
	private static int port_;
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) 
	{
		try
		{
			if(args.length!=2)
				throw new Exception("please start the client as: java ¨Cjar DictionaryClient.jar <server-address> <server-port>");
			String s1=args[0];
			String s2=args[1];
			server_address_=s1;
			port_=Integer.parseInt(s2);
			if(port_<=0||port_>65535)
				throw new Exception("port is invalid!");
		}
		catch(Exception e)
		{
			System.out.println(e.toString());
			return;
		}
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try 
				{
					DictionaryClient window = new DictionaryClient();
					window.frmDictionaryclient.setVisible(true);
				} 
				catch (Exception e) 
				{
					System.out.println(e.toString());
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public DictionaryClient() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() 
	{
		frmDictionaryclient = new JFrame();
		frmDictionaryclient.setTitle("DictionaryClient");
		frmDictionaryclient.setBounds(100, 100, 309, 275);
		frmDictionaryclient.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frmDictionaryclient.getContentPane().setLayout(null);
		frmDictionaryclient.setResizable(false);
		
		
		textWord = new JTextField();
		textWord.setBounds(74, 46, 198, 21);
		frmDictionaryclient.getContentPane().add(textWord);
		textWord.setColumns(10);
		
		textMeaning = new TextArea();
		textMeaning.setBounds(74, 99, 198, 85);
		frmDictionaryclient.getContentPane().add(textMeaning);
		
		lblWord = new JLabel("Word:");
		lblWord.setFont(new Font("Times New Roman", Font.PLAIN, 14));
		lblWord.setBounds(28, 49, 44, 15);
		frmDictionaryclient.getContentPane().add(lblWord);
		
		lblMeaning = new JLabel("Meaning:");
		lblMeaning.setFont(new Font("Times New Roman", Font.PLAIN, 14));
		lblMeaning.setBounds(10, 100, 62, 15);
		frmDictionaryclient.getContentPane().add(lblMeaning);
		
		textWord.setEditable(false);
		textMeaning.setEditable(false);
		
		btnConfirm = new JButton("Confirm");
		btnConfirm.addActionListener(new ActionListener() 
		{
			public void actionPerformed(ActionEvent e) 
			{
				textWord.setEditable(false);
				textMeaning.setEditable(false);
				if(act==1)//query word
				{
					
					String s="1,"+textWord.getText();
					ClientThread ct=new ClientThread(s,server_address_,port_,textMeaning);
					ct.start();
				}
				else if(act==2)//add word
				{
					
					String s="2,"+textWord.getText()+","+textMeaning.getText();
					ClientThread ct=new ClientThread(s,server_address_,port_,textMeaning);
					ct.start();
				}
				else if(act==3)//remove word
				{
					String s="3,"+textWord.getText();
					ClientThread ct=new ClientThread(s,server_address_,port_,textMeaning);
					ct.start();
					
				}
				else
				{
					JOptionPane.showMessageDialog(null, "Please choose an action", "Information", JOptionPane.INFORMATION_MESSAGE);
				}
			}
		});
		btnConfirm.setFont(new Font("Times New Roman", Font.PLAIN, 14));
		btnConfirm.setBounds(28, 192, 98, 23);
		frmDictionaryclient.getContentPane().add(btnConfirm);
		
		btnClear = new JButton("Clear");
		btnClear.addActionListener(new ActionListener() 
		{
			public void actionPerformed(ActionEvent e) 
			{
				
				textWord.setText(null);
				textMeaning.setText(null);
			}
		});
		btnClear.setFont(new Font("Times New Roman", Font.PLAIN, 14));
		btnClear.setBounds(157, 192, 86, 23);
		frmDictionaryclient.getContentPane().add(btnClear);
		
		btnConfirm.setVisible(false);
		btnClear.setVisible(false);
		
		btnQuery = new JButton("Query");
		btnQuery.addActionListener(new ActionListener() 
		{
			public void actionPerformed(ActionEvent e) 
			{
				btnConfirm.setVisible(true);
				btnClear.setVisible(true);
				textWord.setText(null);
				textMeaning.setText(null);
				textWord.setEditable(true);
				textMeaning.setEditable(false);
				act=1;//set action as querying
			}
		});
		btnQuery.setFont(new Font("Times New Roman", Font.PLAIN, 14));
		btnQuery.setBounds(10, 10, 72, 23);
		frmDictionaryclient.getContentPane().add(btnQuery);
		
		btnAdd = new JButton("Add");
		btnAdd.addActionListener(new ActionListener() 
		{
			public void actionPerformed(ActionEvent e) 
			{
				btnConfirm.setVisible(true);
				btnClear.setVisible(true);
				textWord.setText(null);
				textMeaning.setText(null);
				textWord.setEditable(true);
				textMeaning.setEditable(true);
				act=2;//set action as adding
			}
		});
		btnAdd.setFont(new Font("Times New Roman", Font.PLAIN, 14));
		btnAdd.setBounds(102, 10, 71, 23);
		frmDictionaryclient.getContentPane().add(btnAdd);
		
		btnRemove = new JButton("Remove");
		btnRemove.addActionListener(new ActionListener() 
		{
			public void actionPerformed(ActionEvent e) 
			{
				btnConfirm.setVisible(true);
				btnClear.setVisible(true);
				textWord.setText(null);
				textMeaning.setText(null);
				textWord.setEditable(true);
				textMeaning.setEditable(false);
				act=3;//set action as removing
			}
		});
		btnRemove.setFont(new Font("Times New Roman", Font.PLAIN, 14));
		btnRemove.setBounds(186, 10, 86, 23);
		frmDictionaryclient.getContentPane().add(btnRemove);
		
		
	}
	
	
}
