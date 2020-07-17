/*
 * Author: Xinnan SHEN
 * Student ID: 1051380
 * Date: 28/08/2019
 * 
 */
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class StartThread extends Thread
{
	private int port_;
	private DictionaryServerWindow window;
	StartThread(int p,DictionaryServerWindow w)
	{
		port_=p;
		window=w;
	}
	public void run()
	{
		ServerSocket server;
		try 
		{
			server = new ServerSocket(port_);
			while(true)
			{
				Socket client=server.accept();
				ServerThread st=new ServerThread(client,window);
				st.start();
			}
		} 
		catch (Exception e) 
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
