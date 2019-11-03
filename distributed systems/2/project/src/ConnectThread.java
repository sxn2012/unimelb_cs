import java.awt.Toolkit;
import java.rmi.Naming;

import javax.swing.JButton;
import javax.swing.JOptionPane;

import chat.ClientChat;
import rmi.ServercomInter;
import whiteboard.Canvas;
import whiteboard.Whiteboard;

public class ConnectThread extends Thread
{
	private Startclient sc;
	private JButton connect;
	ConnectThread(Startclient s_c,JButton c)
	{
		sc=s_c;
		connect=c;
	}
	
	
	public void run() {
        Canvas.nightmode = false;
        if (sc.connect.getText().equals("Connect")) {
            if (sc.name.getText().length() < 2) {
                JOptionPane.showMessageDialog(sc.frame, "You need to type a name");
                return;
            }
            if (sc.ip.getText().length() < 2) {
                JOptionPane.showMessageDialog(sc.frame, "You need to type an IP");
                return;
            }
            if(!sc.isValidPort(sc.port.getText()))
            {
            	JOptionPane.showMessageDialog(sc.frame, "You need to type a valid port number");
                return;
            }
            if (!sc.isValidIPV4(sc.ip.getText())) {
                JOptionPane.showMessageDialog(sc.frame, "You need to type a valid ip address");
                return;
            }
            try {
                
                sc.meddle.setVisible(true);
                sc.client = new ClientCom(sc.name.getText());
                sc.client.setGUI(sc);
                sc.server = (ServercomInter) Naming.lookup("rmi://" + sc.ip.getText() + "/myabc");
                char loginsuccess = sc.server.login(sc.client);
                if (loginsuccess != 0) {
                    Startclient.issuper=false;
                    sc.createnewboard.setVisible(false);
                    sc.updateUsers(sc.server.getConnected());
                    sc.connect.setText("Disconnect");
                    sc.name.setEditable(false);
                    sc.ip.setEditable(false);
                    sc.port.setEditable(false);
                    sc.whiteboard = new Whiteboard(sc.whiteBoard, sc.server, false);
                    sc.whiteboard.setUserId(loginsuccess);
                    sc.clientChat = new ClientChat(sc.chat, sc.whiteBoard, sc.tx, sc.name.getText(), sc.ip.getText(), Integer.parseInt(sc.port.getText()), connect, sc.threadPool);
                    sc.client.setWhiteboard(sc.whiteboard);
                    sc.server.resumemodelhistory(sc.client);
                    sc.clientChat.left.setVisible(true);
                    sc.clientChat.right.setVisible(true);
                    sc.tx.setVisible(true);
                    sc.frame.setSize(1400, 600);
                    Toolkit toolkit = Toolkit.getDefaultToolkit();
                    int x = (int)(toolkit.getScreenSize().getWidth()-sc.frame.getWidth()-200)/2;
                    int y = (int)(toolkit.getScreenSize().getHeight()-sc.frame.getHeight()-200)/2;
                    sc.frame.setLocation(x, y);
                } else {
                    JOptionPane.showMessageDialog(sc.frame, "You are not allowed or cannot connect to the service");

                }
            } catch (Exception e) {
                e.printStackTrace();
                JOptionPane.showMessageDialog(sc.frame, "ERROR, we wouldn't connect");
            }
        } else {
            try {


                sc.meddle.setVisible(false);
                sc.tx.setVisible(false);

                sc.clientChat.logout();
                sc.server.logout(sc.client);
                sc.chat.removeAll();
                sc.whiteBoard.removeAll();

                sc.clientChat = null;
                sc.client.setWhiteboard(null);
                sc.tx.setText("");
                sc.name.setEditable(true);
                sc.ip.setEditable(true);
                sc.port.setEditable(true);
                //server.logout(client);
                sc.frame.setSize(1400, 100);

            } catch (Exception e) {
                e.printStackTrace();
                JOptionPane.showMessageDialog(sc.frame, "ERROR, cannot discoonnect bconnect....");
            }

            sc.updateUsers(null);
            sc.connect.setText("Connect");
            //Better to implement Logout ....
        }
    }


}
