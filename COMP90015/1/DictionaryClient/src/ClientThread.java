/*
 * Author: Xinnan SHEN
 * Student ID: 1051380
 * Date: 28/08/2019
 * 
 */
import java.awt.*;
import java.io.*;
import java.net.*;

import javax.swing.JOptionPane;

public class ClientThread extends Thread
{
	private String sendstring="";//string that will be sent to server
	private String ip_addr="";
	private int port_num=0;//ip address and port number of server
	private TextArea ta;
	ClientThread(String s,String ip,int port,TextArea t)
	{
		sendstring=s;
		ip_addr=ip;
		port_num=port;
		ta=t;
	}
	
	public void run()
	{
		try 
		{
			Socket client=new Socket(ip_addr,port_num);
			DataInputStream is=new DataInputStream(client.getInputStream());
			DataOutputStream os=new DataOutputStream(client.getOutputStream());
			os.writeUTF(sendstring);
			String receivestring=is.readUTF();
			if(receivestring.contains("Error:")||receivestring.contains("Success:"))
				JOptionPane.showMessageDialog(null, receivestring, "Result", JOptionPane.INFORMATION_MESSAGE);
			else
				ta.setText(receivestring);
			client.close();
		} 
		catch (UnknownHostException e) 
		{
			JOptionPane.showMessageDialog(null, e.toString(), "Result", JOptionPane.INFORMATION_MESSAGE);
		} 
		catch (IOException e) 
		{
			
			JOptionPane.showMessageDialog(null, e.toString(), "Result", JOptionPane.INFORMATION_MESSAGE);
		}
	}
}
