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

public class ServerThread extends Thread
{
	
	private Socket client;
	private DictionaryServerWindow window;
	ServerThread(Socket sc,DictionaryServerWindow w)
	{
		
		client=sc;
		window=w;
	}
	
	public void run()
	{
		try 
		{
			
			DataInputStream is=new DataInputStream(client.getInputStream());
			DataOutputStream os=new DataOutputStream(client.getOutputStream());
			String receivestring=is.readUTF();
			if(receivestring!=null)
			{
				String action=receivestring.split(",", 2)[0];
				receivestring=receivestring.split(",", 2)[1];		
				if(action.equals("1"))//query
				{
					if(receivestring.matches("^([a-zA-Z-]+)$"))
					{
						window.ReqHistory.setText(window.ReqHistory.getText()+"quirying word: "+receivestring+"\r\n");
						String meaning=QueryWord(receivestring);
						if(meaning!=null)
						{
							os.writeUTF(meaning);
							window.ReqHistory.setText(window.ReqHistory.getText()+"quiry word: "+receivestring+" successful!\r\n");
						}
						else
						{
							os.writeUTF("Error:Word Not Found!");
							window.ReqHistory.setText(window.ReqHistory.getText()+"quiry word: "+receivestring+" failed!\r\n");
						}
					}
					else
					{
						os.writeUTF("Error:Input Invalid!");
						window.ReqHistory.setText(window.ReqHistory.getText()+"Error:Invalid Input of word to query!\r\n");
					}
				}
				else if(action.equals("2"))//add
				{
					String receive_word=receivestring.split(",", 2)[0];//word
					String receive_meaning=receivestring.split(",", 2)[1];//meaning
					String strword=receive_word+","+receive_meaning;
					if(receive_word.matches("^([a-zA-Z-]+)$")&&receive_meaning.matches("^[\\p{Print}\\s]+$"))
					{
						window.ReqHistory.setText(window.ReqHistory.getText()+"adding word: "+receive_word+" meaning: "+receive_meaning+"\r\n");
						if(AddWords(strword))
						{
							os.writeUTF("Success:Add Word Successfully!");
							window.ReqHistory.setText(window.ReqHistory.getText()+"add word: "+receive_word+" successful!\r\n");
						}
						else
						{
							os.writeUTF("Error:Word Already Exists!");
							window.ReqHistory.setText(window.ReqHistory.getText()+"add word: "+receive_word+" failed!\r\n");
						}
					}
					else
					{
						os.writeUTF("Error:Input Invalid!");
						window.ReqHistory.setText(window.ReqHistory.getText()+"Error:Invalid Input of word or meaning to add!\r\n");
					}
				}
				else if(action.equals("3"))//remove
				{
					if(receivestring.matches("^([a-zA-Z-]+)$"))
					{
						window.ReqHistory.setText(window.ReqHistory.getText()+"removing word: "+receivestring+"\r\n");
						if(RemoveWords(receivestring))
						{
							os.writeUTF("Success:Remove WordSuccessfully!");
							window.ReqHistory.setText(window.ReqHistory.getText()+"remove word: "+receivestring+" successful!\r\n");
						}
						else
						{
							os.writeUTF("Error:Word Not Found!");
							window.ReqHistory.setText(window.ReqHistory.getText()+"remove word: "+receivestring+" failed!\r\n");
						}
					}
					else
					{
						os.writeUTF("Error:Input Invalid!");
						window.ReqHistory.setText(window.ReqHistory.getText()+"Error:Invalid Input of word to remove!\r\n");
					}
				}
				else
				{
					os.writeUTF("Error:Invalid Action!");
					window.ReqHistory.setText(window.ReqHistory.getText()+"Error:Invalid Action!\r\n");
				}
			}
	
		} 
		
		catch (Exception e) 
		{
			JOptionPane.showMessageDialog(null, e.toString(), "Error", JOptionPane.INFORMATION_MESSAGE);
			//System.out.println(e.toString());
		}
	}
	
	public synchronized boolean AddWords(String word_meaning)
	{
		
		
		
		int i=0;
		for(i=0;i<DictionaryServer.dict.length;i++)	
		{
			if(DictionaryServer.dict[i]==null) break;
			if(word_meaning==null) return false;
			String temp_word=DictionaryServer.dict[i].split(",", 2)[0];//word
			String temp_add=word_meaning.split(",",2)[0];
			if(temp_word.trim().toLowerCase().equals(temp_add.toLowerCase()))
			{
				return false;
			}
		}
		DictionaryServer.dict[i]=word_meaning;
		return true;
		
	}
	
	public synchronized boolean RemoveWords(String word)
	{
		
		
		
		for(int i=0;i<DictionaryServer.dict.length;i++)	
		{
			if(DictionaryServer.dict[i]==null) break;
			String temp_word=DictionaryServer.dict[i].split(",", 2)[0];//word
			
		
			if(temp_word.trim().toLowerCase().equals(word.toLowerCase()))
			{
				for(int j=i;j<DictionaryServer.dict.length;j++)
					if(DictionaryServer.dict[j]==null) break;
					else
						DictionaryServer.dict[j]=DictionaryServer.dict[j+1];
				return true;	
				
			}
		}
		return false;
		
		
		
	}
	
	public synchronized String QueryWord(String word)
	{
		for(int i=0;i<DictionaryServer.dict.length;i++)	
		{
			if(DictionaryServer.dict[i]==null) break;
			String temp_word=DictionaryServer.dict[i].split(",", 2)[0];//word
			String temp_meaning=DictionaryServer.dict[i].split(",", 2)[1];//meaning
		
			if(temp_word.trim().toLowerCase().equals(word.trim().toLowerCase()))
			{
				return temp_meaning;
			}
		}
		return null;
	}
}
