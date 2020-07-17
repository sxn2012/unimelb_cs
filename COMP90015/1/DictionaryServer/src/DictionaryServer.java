/*
 * Author: Xinnan SHEN
 * Student ID: 1051380
 * Date: 28/08/2019
 * 
 */
import java.awt.EventQueue;
import java.io.*;
import java.net.*;
import java.awt.EventQueue;

import javax.swing.JFrame;
public class DictionaryServer 
{
	public static String[]dict=new String[10000];
	public static void main(String[] args)
	{
		
		try
		{
			if(args.length!=2)
				throw new Exception("please start the server as: java ¨Cjar DictionaryServer.jar <port> <dictionary-file>");
			String s1=args[0];
			String s2=args[1];
			int port_=Integer.parseInt(s1);
			if(port_<=0||port_>65535)
				throw new Exception("port is invalid!");
			File file=new File(s2);
			BufferedReader br=new BufferedReader(new FileReader(file));
			String temp=null;
			
			int i=0;
			temp=br.readLine();//read the header of the workbook
			do
			{
				temp=br.readLine();
				dict[i++]=temp;
        	
			}
			while(temp!=null);
			br.close();
			EventQueue.invokeLater(new Runnable() {
				public void run() 
				{
					try 
					{
						DictionaryServerWindow window = new DictionaryServerWindow();
						window.frmDictionaryServer.setVisible(true);
						StartThread st=new StartThread(port_,window);
						st.start();
					} 
					catch (Exception e) 
					{
						System.out.println(e.toString());
					}
				}
			});
			
		}
		catch (Exception e) 
		{
			
			System.out.println(e.toString());
		} 
		
	}

}
