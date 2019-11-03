import java.awt.Toolkit;
import java.rmi.Naming;

import javax.swing.JOptionPane;

import chat.ServerChat;
import rmi.ServercomInter;
import whiteboard.Whiteboard;

public class CreateThread extends Thread
{
	private Startclient sc;
	CreateThread(Startclient s_c)
	{
		sc=s_c;
	}
	
	
	
	 public void run() {
	        try {
	            if (sc.name.getText().length() < 2) {
	                JOptionPane.showMessageDialog(sc.frame, "You need to type a name.");
	                return;
	            }
	            java.rmi.registry.LocateRegistry.createRegistry(1099);
	            ServercomInter b = new Servercom();
	            Naming.rebind("rmi://localhost/myabc", b);
	            System.out.println("[System] Chat Server is ready.");
	            System.out.println( sc.getServerIp());

	            try {

	                sc.client = new ClientCom(sc.name.getText());

	                sc.client.setGUI(sc);
	                sc.server = (ServercomInter) Naming.lookup("rmi://localhost/myabc");
	                Boolean loginsuccess = sc.server.creatorlogin(sc.client);
	                if (loginsuccess) {
	                    sc.updateUsers(sc.server.getConnected());
	                    sc.connect.setText("Disconnect");
	                    sc.server.publish("ip address: "+sc.getServerIp());
	                    sc.whiteboard = new Whiteboard(sc.whiteBoard, sc.server, true);
	                    sc.whiteboard.setUserId('0');
	                    sc.serverChat = new ServerChat(sc.chat, sc.name.getText(), Integer.parseInt(sc.port.getText()), sc.threadPool);
	                    sc.client.setWhiteboard(sc.whiteboard);
	                }
	                sc.frame.setSize(1400, 600);
	                Toolkit toolkit = Toolkit.getDefaultToolkit();
	                int x = (int)(toolkit.getScreenSize().getWidth()-sc.frame.getWidth()-200)/2;
	                int y = (int)(toolkit.getScreenSize().getHeight()-sc.frame.getHeight()-200)/2;
	                sc.frame.setLocation(x, y);
	                sc.meddle.setVisible(true);
	            } catch (Exception e) {
	                e.printStackTrace();
	                JOptionPane.showMessageDialog(sc.frame, "ERROR, we wouldn't create board");
	            }
	            sc.issuper = true;
				Startclient.issuper=true;
	            sc.createnewboard.setVisible(false);
	            sc.top.setVisible(false);
	        } catch (Exception e) {
	            e.printStackTrace();
	            JOptionPane.showMessageDialog(sc.frame, "ERROR, we cannot create board");
	        }

	    }

}
