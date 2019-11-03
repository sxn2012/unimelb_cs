package chat;

import util.Document;
import util.httpURLConectionGET;

import java.awt.*;
import java.awt.event.*;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.InetAddress;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Random;
import java.util.concurrent.ExecutorService;
import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;

public class ClientChat {

    // Define member variables

    public static String serverIP;
    public static int serverPort1;
    private final ButtonGroup buttonGroup = new ButtonGroup();
    public Socket socket;
    JFrame frmtcp;
    /**
     * Client communication thread
     */
    private ClientThread clientThread;
    private JTextField textServerIP;
    private JTextField textServerPort;
    private JTextArea textAreaRecord;
    private JButton btnSend;
    private JButton btnConnect;
    private JTextField textUsername;
    private JList<String> listUsers;
    private JRadioButton rdbtnBrocast;
    private JRadioButton rdbtnPrivateChat;
    private final ButtonGroup buttonGroup2 = new ButtonGroup();
    private JTextArea textAreaMsg;
    private DefaultListModel<String> modelUsers;
    public JPanel left;
    public JPanel right;
    public static String username;
    public static JPanel whiteboard;
    private JRadioButton rdbtnen;
    private JRadioButton rdbtnch;
    private JButton btnTrans;
    private JButton btnEmoji;
    public ExecutorService threadPool;
    private JTextArea tx;
    /**
     * Launch the application.
     */
//    public static void main(String[] args) {
//        EventQueue.invokeLater(new Runnable() {
//            public void run() {
//                try {
//                    chat.ClientChat window = new chat.ClientChat();
//                } catch (Exception e) {
//                    e.printStackTrace();
//                }
//            }
//        });
//    }

    /**
     * Create the application.
     */
    
	public void logout() {
        try {
        	
            modelUsers.removeAllElements();
            clientThread.isLogged=false;
            //clientThread.stop();
            socket.close();
        }
        catch (IOException e){
            System.out.println("exit failed");
        }

    }



    public ClientChat(JPanel jPanel, JPanel wb, JTextArea ja, String name, String ip, int port, JButton connect, ExecutorService threadPool) {

        serverIP =ip;
        		//"localhost";

        username = name;
        serverPort1 = port;
        		//8000;
        whiteboard = wb;
        tx=ja;
        btnConnect=connect;
        try {
            socket = new Socket(serverIP, serverPort1);

        } catch (IOException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Cant Find Server");

        }
        this.threadPool = threadPool;

        initialize(jPanel);
        // Initialize member variables and random usernames
        modelUsers = new DefaultListModel<String>();
        listUsers.setModel(modelUsers);

    }

    public static Document Connect(String ip) {
        Document request = new Document();
        request.append("command", "Connect");
        request.append("ip", ip);
        return request;
    }

    public static Document beenpoped() {
        Document request = new Document();
        request.append("command", "beenpoped");
        return request;
    }

    public static Document Login_Out(String ip) {
        Document request = new Document();
        request.append("command", "Login_Out");
        request.append("ip", ip);
        return request;
    }

    public static Document Send_All(String text) {
        Document request = new Document();
        request.append("command", "Send_All");
        request.append("text", text);
        return request;
    }

    public static Document Send_One(String text, String user) {
        Document request = new Document();
        request.append("command", "Send_One");
        request.append("text", text);
        request.append("user", user);
        return request;
    }

    /**
     * Initialize the contents of the frame.
     */
    private void initialize(JPanel jPanel) {
//        frmtcp = new JFrame();
//        frmtcp.setResizable(false);
        // frmtcp.setTitle("TCP\u804A\u5929\u5BA4\u5BA2\u6237\u7AEF");
//        frmtcp.setBounds(100, 100, 715, 476);
//        frmtcp.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
//        frmtcp.getContentPane().setLayout(null);

        // btnConnect = new JButton("\u767B\u5F55");
        // btnConnect.setBounds(596, 20, 95, 27);
        // frmtcp.getContentPane().add(btnConnect);
        // Connect button event handler

        jPanel.setLayout(new BorderLayout(5, 5));
        left = new JPanel();
        left.setLayout(new BorderLayout(5, 5));

        // left north
        JScrollPane scrollPane = new JScrollPane(); //Information display
//        scrollPane.setBounds(14, 72, 518, 228);
        textAreaRecord = new JTextArea();
        textAreaRecord.setEditable(false);
        scrollPane.setViewportView(textAreaRecord);
        left.add(scrollPane, BorderLayout.CENTER);

        //left meddle
        JPanel bottom = new JPanel();
        bottom.setLayout(new GridLayout(3, 1, 5, 0));
        bottom.setPreferredSize(new Dimension(300, 150));
        JPanel bottomtop = new JPanel();
        bottomtop.setLayout(new GridLayout(1, 2, 5, 0));
        rdbtnBrocast = new JRadioButton("Send To All");
        buttonGroup.add(rdbtnBrocast);
//        rdbtnBrocast.setBounds(226, 309, 132, 22);
        bottomtop.add(rdbtnBrocast);
        rdbtnPrivateChat = new JRadioButton("Send To One");
        buttonGroup.add(rdbtnPrivateChat);
        rdbtnBrocast.setSelected(true);
        bottomtop.add(rdbtnPrivateChat);
//        rdbtnPrivateChat.setBounds(356, 309, 157, 22);
        bottom.add(bottomtop);

        JPanel bottomButton = new JPanel();
        //Add chat message input box
        JScrollPane scrollPane_1 = new JScrollPane();   //Information input box
//        scrollPane_1.setBounds(14, 332, 518, 73);
        bottom.add(scrollPane_1);
        textAreaMsg = new JTextArea();
        scrollPane_1.setViewportView(textAreaMsg);
         
        //Adding button
        rdbtnen = new JRadioButton("en");
        rdbtnch = new JRadioButton("ch");
        rdbtnch.setSelected(true);
        buttonGroup2.add(rdbtnen);
        buttonGroup2.add(rdbtnch);
        JPanel buttonG = new JPanel (  );
        buttonG.setLayout ( new BorderLayout ( 0,0 ) );
        buttonG.add ( rdbtnen, BorderLayout.NORTH );
        buttonG.add ( rdbtnch, BorderLayout.SOUTH );
        bottomButton.add(buttonG);
        btnTrans = new JButton("Trans(en->ch)");
        btnTrans.setEnabled(true);
        btnTrans.setPreferredSize ( new Dimension ( 100,20 ) );
        bottomButton.add(btnTrans);

        btnSend = new JButton("Send");
        btnSend.setEnabled(true);
        btnSend.setPreferredSize ( new Dimension ( 50,20 ) );
        bottomButton.add(btnSend);
//        btnSend.setBounds(444, 409, 88, 27);
        btnEmoji = new JButton("Emoji");
        btnEmoji.setEnabled(true);
        btnEmoji.setPreferredSize ( new Dimension ( 50,20 ) );
        bottomButton.add(btnEmoji);
        bottom.add(bottomButton);
        JTable table;
        //Define a two-dimensional array as tabular data
        Object[][] tableData =
                {
                        new Object[]{"(▼ _ ▼)" , " ┑(￣Д ￣)┍ " , "↖(▔＾▔)↗"},
                        new Object[]{" (●′ω`●) ", "（●>∀<●）" , " (≥﹏ ≤)"},
                        new Object[]{" °(°ˊДˋ°) °", " (つ﹏⊂) " , "  (￣ε ￣) "},
                        new Object[]{" (◍'౪`◍)ﾉﾞ", " (๑´ڡ`๑) " , "ℰ⋆‿⋆ℰ "},
                        new Object[]{"╰(*´︶`*)╯" , "(；′⌒`)" , "(/ω＼) "}
                };
        //Define one-dimensional data as column headings
        Object[] columnTitle = {"", "", ""};
        JWindow jWindow = new JWindow();
        //Create a JTable object with a two-dimensional array and a one-dimensional array
        table = new JTable(tableData, columnTitle);
        jWindow.add(table);
        jWindow.setSize(300, 250);

        table.getTableHeader().setVisible(false);
        table.setSelectionBackground(new Color(24, 242, 255));
        table.setAutoResizeMode(JTable.AUTO_RESIZE_SUBSEQUENT_COLUMNS);
        table.setFont(new Font("Menu.font", Font.PLAIN, 20));
        table.setRowHeight(50);
        DefaultTableCellRenderer r = new DefaultTableCellRenderer();
        r.setHorizontalAlignment(JLabel.CENTER);
        table.setDefaultRenderer(Object.class, r);
        //FitTableColumns(table);
        table.setEnabled(false);
        table.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                int row = ((JTable) e.getSource()).rowAtPoint(e.getPoint()); //Get row position
                int col = ((JTable) e.getSource()).columnAtPoint(e.getPoint()); //Get column position
                String cellVal = (String) (table.getValueAt(row, col)); //Get click cell data
                jWindow.setVisible(false);
                textAreaMsg.setText(textAreaMsg.getText() + cellVal);
            }
        });

        btnEmoji.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (jWindow.isVisible()) {
                    jWindow.setVisible(false);
                } else {
                    jWindow.setVisible(true);
                }
            }
        });

        rdbtnch.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                btnTrans.setText("Trans(en->ch)");

            }
        });

        rdbtnen.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                btnTrans.setText("Trans(ch->en)");

            }
        });
        btnTrans.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String out;

                boolean containEmoji=false;
                for(int i =0; i < 5; i++){

                    if(containEmoji){
                        break;
                    }
                    for(int j =0; j<3;j++){

                        //System.out.println(tableData[i][j].toString());
                        if(containEmoji){
                            break;
                        }
                        if(textAreaMsg.getText().contains(tableData[i][j].toString())){
                            containEmoji=true;
                        }
                    }
                }

                if(containEmoji){

                    JOptionPane.showMessageDialog(null, "Translation messages cannot contain emoticons");


                }else{
                    //if (rdbtnen.isSelected()){
                    //    out =  httpURLConectionGET.Translate("ch","en",textAreaMsg.getText());
                    //}else{
                    //    out =  httpURLConectionGET.Translate("en","ch",textAreaMsg.getText());
                    //}
                    //if(out.equals("error")){
                    //    JOptionPane.showMessageDialog(null, "Translation ERROR");

                    //}else{
                    //    textAreaMsg.setText(out);
                    //}
                	TransThread tt=new TransThread();
                	tt.init(rdbtnen, rdbtnch, textAreaMsg);
                	tt.start();
                }

            }
        });

        btnSend.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent arg0) {
                if (null != clientThread)
                    clientThread.sendChatMag();
            }
        });

        left.add(bottom, BorderLayout.SOUTH);

        jPanel.add(left, BorderLayout.CENTER);

        //right
        right = new JPanel();
        JScrollPane scrollPane_2 = new JScrollPane();
//        scrollPane_2.setBounds(0, 0, 149, 334);
        scrollPane_2.setPreferredSize(new Dimension(100, 500));
        right.add(scrollPane_2);
        listUsers = new JList<String>();
        listUsers.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        scrollPane_2.setViewportView(listUsers);
        jPanel.add(right, BorderLayout.EAST);

        jWindow.setVisible(false);

        jPanel.addComponentListener(new ComponentAdapter() {
            @Override
            public void componentMoved(ComponentEvent e) {
                super.componentMoved(e);
                Point xy = btnEmoji.getLocationOnScreen();
                jWindow.setLocation((int) xy.getX() + 25, (int) xy.getY() + 25);

            }
        });

        clientThread = new ClientThread();
        threadPool.execute ( clientThread );
//        clientThread.start();

    }

    /**
     * Add a message to a text boxtextAreaRecord
     *
     * @param msg，Message to add
     */
    private void addMsg(String msg) {
        // Add a message to the text area and add a line feed
        textAreaRecord.append(msg + "\n");
        // Automatically scroll to the last line of the text area
        textAreaRecord.setCaretPosition(textAreaRecord.getText().length());
    }

    class ClientThread extends Thread {


        /**
         * Basic data input stream
         */
        private DataInputStream dis;

        /**
         * Basic data output stream
         */
        private DataOutputStream dos;

        /**
         * Whether to log in
         */
        private boolean isLogged;

        /**
         * Connect to the server and log in
         */


        void sendChatMag() {
            String msgChat = null;

            if(textAreaMsg.getText().length()==0){

                JOptionPane.showMessageDialog(null, "enter something to send");
                return;

            }
            if (rdbtnBrocast.isSelected()) {

                // msgChat="TALKTO_ALL#"+textAreaMsg.getText();
                msgChat = Send_All(textAreaMsg.getText()).toJson();
                addMsg("I Say:" + textAreaMsg.getText());
                textAreaMsg.setText("");

            }

            if (rdbtnPrivateChat.isSelected()) {

                String toUsername = listUsers.getSelectedValue();

                if (toUsername == null) {

                    JOptionPane.showMessageDialog(null, "Please Select a person");

                }
                else if(toUsername.equals(username))
                {
                    JOptionPane.showMessageDialog(null, "You cannot sent anything to yourself");
                }

                else {
                    // msgChat="TALKTO#"+toUsername+"#"+textAreaMsg.getText();
                    msgChat = Send_One(textAreaMsg.getText(), toUsername).toJson();
                    addMsg("From me To " + toUsername + " :" + textAreaMsg.getText());
                    System.out.println(msgChat);
                    textAreaMsg.setText("");

                }


            }


            if (null != msgChat) {
                try {
                	
                    dos.writeUTF(msgChat);                    
                    dos.flush();
                } catch (Exception e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            }
        }


//        void logout()
//        {
//            try {
//
//
//              //  String msgLogout="LOGOUT";
//                String myIp = InetAddress.getLocalHost().getHostAddress();
//                int myPort = socket.getLocalPort();
//                String send_str = myIp+":"+myPort;
//                String loginStr = Login_Out(send_str).toJson();
//
//                dos.writeUTF(loginStr);
//                dos.flush();
//                isLogged=false;
//                socket.close();
//                modelUsers.clear();
//                btnSend.setEnabled(false);
//                btnConnect.setText("login");
//                addMsg("Has quit the chat room.");
//            } catch (IOException e) {
//                // TODO Auto-generated catch block
//                e.printStackTrace();
//            }
//        }
//


        public void login() {


            // Connect to the server to get the socket IO stream
            try {
                dis = new DataInputStream(socket.getInputStream());
                dos = new DataOutputStream(socket.getOutputStream());
                String myIp = InetAddress.getLocalHost().getHostAddress();
                int myPort = socket.getLocalPort();
                String send_str = username;
//                String send_str = myIp + ":" + myPort;
                // Get user name, build and send login message

                String loginStr = Connect(send_str).toJson();
                // String msgLogin = "LOGIN#" + username;
                
                dos.writeUTF(loginStr);
                dos.flush();
                // Read the information returned by the server to determine whether the login is successful
                String response = dis.readUTF();
                
                System.out.println(response);
                Document getresponse = Document.parse(response);
                String command = getresponse.getString("command");
                // Login failed
                if (command.equals("Login_False")) {
                    addMsg("Login Fail");
                    // Login failed, disconnect, end client thread
                    isLogged = false;
                    socket.close();
                    return;
                }
                // Login successful
                if (command.equals("Login_Success")) {
                    addMsg("Login Successful");
                    isLogged = true;
                    //btnConnect.setText("Exit");
                    btnSend.setEnabled(true);
                }
            } catch (Exception e2) {

                addMsg("There has some exception, Please Check Server");

                //e.printStackTrace();
            }


        }

        @Override
        public void run() {
            // Connect to the server and log in
            login();

            while (isLogged) {


                try {
                    String msg = dis.readUTF();
                    
                    //String[] parts = msg.split("#");
                    Document getresponse = Document.parse(msg);
                    String command = getresponse.getString("command");


                    System.out.println(msg);

                    switch (command) {
                        // Processing user list messages from the server

                        case "User_List":
                            System.out.println("In userlist");
                            ArrayList<String> revicewordname = (ArrayList<String>) getresponse.get("User_List");

                            System.out.println(revicewordname.toString());
                            modelUsers.removeAllElements();
                            for (int i = 0; i < revicewordname.size(); i++) {
                                modelUsers.addElement(revicewordname.get(i));
                            }

                            break;
                        // Processing new user login form messages from the server
                        case "New_Login":
                            String getnewUser = (String) getresponse.get("New_Login");

                            modelUsers.addElement(getnewUser);
                            break;

                        case "Login_out":
                            getnewUser = (String) getresponse.get("Login_out");
                            modelUsers.removeElement(getnewUser);
                            break;

                        case "Send_All":
                            getnewUser = (String) getresponse.get("sendname");
                            String getMsg = (String) getresponse.get("text");

                            addMsg("(" + getnewUser + ")To EveryOne: " + getMsg);
                            break;

                        case "Send_One":
                            getnewUser = (String) getresponse.get("user");
                            getMsg = (String) getresponse.get("text");

                            addMsg("(" + getnewUser + ")To Me: " + getMsg);
                            break;

                        case "beenpoped":
                            
                            getnewUser = (String) getresponse.get("user");
                            getMsg = (String) getresponse.get("text");
                            btnSend.setEnabled(false);
                            btnTrans.setEnabled(false);
                            btnEmoji.setEnabled(false);
                            rdbtnen.setEnabled(false);
                            rdbtnch.setEnabled(false);
                            rdbtnBrocast.setEnabled(false);
                            rdbtnPrivateChat.setEnabled(false);
                            textAreaMsg.setEnabled(false);
                            whiteboard.removeAll();
                            tx.setVisible(false);
                            left.setVisible(false);
                            right.setVisible(false);
                            modelUsers.removeAllElements();
                            //btnConnect.setText("connect");
                            //addMsg("(" + getnewUser + ")To Me: " + getMsg);
                            JOptionPane.showMessageDialog(null, "You have been kicked off by manager!");
                            
                            //System.exit(1);
                            return;

                        case "Send_Fail":

                            addMsg("(System)To Me: Not Found Users");
                            break;
                        default:
                            break;
                    }
                } catch (IOException e) {
                    // TODO Handle exception
                    if(isLogged)
                    {
                    	JOptionPane.showMessageDialog(null, "The manager has left the chat room!");
                    	
                    	System.exit(1);
                    }
                    isLogged = false;
                    
                }
            }
        }

    }

}
