/*
 * Author: Xinnan SHEN
 * Student ID: 1051380
 * Date: 28/08/2019
 * 
 */
import java.awt.EventQueue;

import javax.swing.JFrame;
import java.awt.TextArea;
import javax.swing.JLabel;
import java.awt.Font;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class DictionaryServerWindow {

	public JFrame frmDictionaryServer;
	public TextArea ReqHistory;
	public JLabel lblRequestHistory;
	public JButton btnClear;
	/**
	 * Create the application.
	 */
	public DictionaryServerWindow() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frmDictionaryServer = new JFrame();
		frmDictionaryServer.setTitle("DictionaryServer");
		frmDictionaryServer.setBounds(100, 100, 337, 230);
		frmDictionaryServer.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frmDictionaryServer.getContentPane().setLayout(null);
		frmDictionaryServer.setResizable(false);
		
		ReqHistory = new TextArea();
		ReqHistory.setEditable(false);
		ReqHistory.setBounds(10, 35, 301, 149);
		frmDictionaryServer.getContentPane().add(ReqHistory);
		
		lblRequestHistory = new JLabel("Request History");
		lblRequestHistory.setFont(new Font("Times New Roman", Font.PLAIN, 14));
		lblRequestHistory.setBounds(135, 14, 107, 15);
		frmDictionaryServer.getContentPane().add(lblRequestHistory);
		
		btnClear = new JButton("Clear");
		btnClear.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) 
			{
				ReqHistory.setText(null);
			}
		});
		btnClear.setFont(new Font("Times New Roman", Font.PLAIN, 14));
		btnClear.setBounds(21, 10, 72, 23);
		frmDictionaryServer.getContentPane().add(btnClear);
	}

}
