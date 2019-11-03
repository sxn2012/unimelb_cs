package chat;

import sun.tools.jps.Jps;
import util.Document;
import util.httpURLConectionGET;

import java.awt.*;
import java.awt.event.*;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.concurrent.ExecutorService;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;

import static java.lang.Thread.sleep;

public class ServerChat {

    // Define member variables
    /**
     * Client communication thread
     */
    //private ClientThread clientThread;
    public Socket socket;
    //    JFrame frmtcp;
    public Thread serverThread;
    private JTextField textServerIP;
    private JTextField textServerPort;
    private JTextArea textAreaRecord;
    private JButton btnSend;
    private JButton btnConnect;
    private JTextField textUsername;
    private JList<String> listUsers;
    private JLabel lblRoomInfo;
    private final ButtonGroup buttonGroup = new ButtonGroup();
    private final ButtonGroup buttonGroup2 = new ButtonGroup();
    private JRadioButton rdbtnBrocast;
    private JRadioButton rdbtnPrivateChat;
    private JTextArea textAreaMsg;
    private DefaultListModel<String> modelUsers;
    private JPanel left;
    private JPanel right;
    private JRadioButton rdbtnen;
    private JRadioButton rdbtnch;
    private JButton btnTrans;
    public ExecutorService threadPool;

    public static String serverIP;
    public static String username;
    public static int serverPort1;


    int getport;
    String gethost;
    // Add member variables for functional implementation
    /**
     * Server socket
     */
    private ServerSocket server;

    /**
     * Determine if the server is running
     */
    private boolean isRunning;

    /**
     * Client mapping，key -> String：Client name； value -> ClientHandler： Client processing thread
     */
    private HashMap<String, ClientHandler> clientHandlerMap = new HashMap<String, ClientHandler>();

    /**
     * Launch the application.
     */
    /**
     * Launch the application.Client mapping
     */
//    public static void main(String[] args) {
//
//        EventQueue.invokeLater(new Runnable() {
//            public void run() {
//                try {
//
//                    System.out.println( "\uD83D\uDE00");
//                    chat.ServerChat window = new chat.ServerChat();
//
//                    //window.frmtcp.setVisible(true);
//
//                } catch (Exception e) {
//                    e.printStackTrace();
//                }
//            }
//        });
//    }
    public ServerChat(JPanel jPanel, String name, int port, ExecutorService threadPool) {
        this.threadPool = threadPool;
        serverIP = "localhost";
        username = name;
        serverPort1 = port;
        		//8000;
        initialize(jPanel);
        modelUsers = new DefaultListModel<String>();
        listUsers.setModel(modelUsers);
        modelUsers.addElement("Manager: " + username);
    }

    /**
     * Initialize the contents of the frame.
     */
    private void initialize(JPanel jPanel) {

//        frmtcp = new JFrame();
//        frmtcp.setResizable(false);
//        // frmtcp.setTitle("TCP\u804A\u5929\u5BA4\u5BA2\u6237\u7AEF");
//        frmtcp.setBounds(100, 100, 715, 476);
//        frmtcp.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
//        frmtcp.getContentPane().setLayout(null);
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
        bottomtop.setLayout(new GridLayout(1, 2, 0, 0));
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
//        left.add(meddle,BorderLayout.SOUTH);

        //left bottom


//        bottom.setPreferredSize ( new Dimension ( 300,150 ) );
        JPanel bottomButton = new JPanel();
        bottomButton.setLayout ( new FlowLayout (  ) );
        //Add chat message input box
        JScrollPane scrollPane_1 = new JScrollPane();   //Information input box
//        scrollPane_1.setBounds(14, 332, 518, 73);
        bottom.add(scrollPane_1);
        textAreaMsg = new JTextArea();
        scrollPane_1.setViewportView(textAreaMsg);
        //add button
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
        JButton btnEmoji = new JButton("Emoji");
        btnEmoji.setEnabled(true);
        btnEmoji.setPreferredSize ( new Dimension ( 50,20 ) );
        bottomButton.add(btnEmoji);


        bottom.add(bottomButton);
//        btnEmoji.setBounds(344, 409, 88, 27);
//        jPanel.add(btnEmoji);

        // Initialize emojis
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
                if (null != serverThread) {

                    String msgChat = null;
                    if (rdbtnBrocast.isSelected()) {

                        if(textAreaMsg.getText().length()==0){

                            JOptionPane.showMessageDialog(null, "enter something to send");
                            return;

                        }

                        // msgChat="TALKTO_ALL#"+textAreaMsg.getText();
                        msgChat = Send_All(textAreaMsg.getText(), "Manager:" + username).toJson();

                        try {
                            broadcastMsg("Manager:" + username, msgChat);
                        } catch (Exception e) {
                            // e.printStackTrace();
                        }
                        addMsg("I Say: " + textAreaMsg.getText());
                        textAreaMsg.setText("");
                    }


                    if (rdbtnPrivateChat.isSelected()) {

                        String toUsername = listUsers.getSelectedValue();

                        if (toUsername == null) {

                            JOptionPane.showMessageDialog(null, "Please Select a person");

                        }
                        else if(toUsername.equals("Manager: " + username))
                        {
                            JOptionPane.showMessageDialog(null, "You cannot sent anything to yourself");
                        }
                        else {

                            // msgChat= Send_One(textAreaMsg.getText(),toUsername).toJson();
                            ClientHandler clientHandler = clientHandlerMap.get(toUsername);
                            if (null != clientHandler) {

                                String msgTalkTo = Send_One(textAreaMsg.getText(), "Manager: " + username).toJson();

                                try {
                                	
                                    clientHandler.dos.writeUTF(msgTalkTo);
                                    clientHandler.dos.flush();
                                } catch (Exception e) {
                                    //e.printStackTrace();
                                }

                            }
                            addMsg("From me To " + toUsername + " :" + textAreaMsg.getText());
                            textAreaMsg.setText("");
                        }
                    }
                }
            }
        });

        left.add(bottom, BorderLayout.SOUTH);

        jPanel.add(left, BorderLayout.CENTER);

        //right
        right = new JPanel();
        right.setLayout ( new BorderLayout(5, 5) );
        JScrollPane scrollPane_2 = new JScrollPane();
//        scrollPane_2.setBounds(0, 0, 149, 334);
        scrollPane_2.setPreferredSize(new Dimension(100, 500));
        right.add(scrollPane_2,BorderLayout.CENTER);
        JButton popThePerson = new JButton("kick");
        popThePerson.setEnabled(true);
        right.add(popThePerson,BorderLayout.SOUTH);
        listUsers = new JList<String>();
        listUsers.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        scrollPane_2.setViewportView(listUsers);
        jPanel.add(right, BorderLayout.EAST);

        popThePerson.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent arg0) {
                if (null != serverThread) {

                    String toUsername = listUsers.getSelectedValue();

                    if (toUsername == null) {

                        JOptionPane.showMessageDialog(null, "Please Select a person");

                    }
                    else if(toUsername.equals("Manager: " + username))
                    {
                        JOptionPane.showMessageDialog(null, "You cannot kick off yourself");
                    }
                    else {

                        // msgChat= Send_One(textAreaMsg.getText(),toUsername).toJson();
                        ClientHandler clientHandler = clientHandlerMap.get(toUsername);

                        if (null != clientHandler) {
                            String msgpop = beenpoped("You have been kicked off by manager", "Manager: " + username).toJson();

                            try {
                            	
                                clientHandler.dos.writeUTF(msgpop);
                                clientHandler.dos.flush();
                            } catch (Exception e) {
                                //e.printStackTrace();
                            }
                            try {

                                poptheperson(textAreaMsg.getText(), "Manager: " + username);
                            }
                            catch (IOException e){
                                addMsg("can't kick off the person");
                            }

                        }
                        String msgChat = null;
                        msgChat = Send_All(toUsername.toString()+" has been removed from the chat room","NOTICE").toJson();
                        try {
                            broadcastMsg("", msgChat);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                        addMsg("kick the person successful");
                        textAreaMsg.setText("");
                    }

                }
            }
        });

//         btnConnect = new JButton("\u767B\u5F55");
//         btnConnect.setBounds(596, 20, 95, 27);
//         jPanel.add(btnConnect);
        // Connect button event handler


//        JLabel lblNewLabel_2 = new JLabel("Select Model:");
//        meddle.add ( lblNewLabel_2 );
//        lblNewLabel_2.setBounds(14, 313, 172, 18);


//        JLabel label = new JLabel("Message");
//        label.setBounds(14, 55, 72, 18);
//        jPanel.add(label);


//        JLabel label_2 = new JLabel("Online");
//        label_2.setBounds(543, 55, 72, 18);
//        jPanel.add(label_2);


//        lblRoomInfo = new JLabel("Totol");
//        lblRoomInfo.setBounds(546, 413, 145, 18);
//        jPanel.add(lblRoomInfo);

        // Change Style
//        try {
//            String style = "com.sun.java.swing.plaf.windows.WindowsLookAndFeel";
//            UIManager.setLookAndFeel(style);
//            // Update form Style
//            SwingUtilities.updateComponentTreeUI(this.frmtcp);
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//        frmtcp.setVisible(true);


        jWindow.setVisible(false);

        jPanel.addComponentListener(new ComponentAdapter() {
            @Override
            public void componentMoved(ComponentEvent e) {
                super.componentMoved(e);
                Point xy = btnEmoji.getLocationOnScreen();
                jWindow.setLocation((int) xy.getX() + 25, (int) xy.getY() + 25);

            }
        });

        serverThread = new Thread(new ServerThread());
        threadPool.execute ( serverThread );
//        serverThread.start();


    }


    class ClientHandler implements Runnable {
        private Socket socket;
        private DataInputStream dis;
        private DataOutputStream dos;
        private boolean isConnected;
        private String username;

        public ClientHandler(Socket socket) {
            this.socket = socket;
            getport = socket.getPort();
            gethost = socket.getLocalAddress().getHostAddress().toString();
            try {

                this.dis = new DataInputStream(socket.getInputStream());
                this.dos = new DataOutputStream(socket.getOutputStream());
                isConnected = true;
            } catch (IOException e) {
                isConnected = false;
                e.printStackTrace();
            }
        }

        @Override
        public void run() {
            while (isRunning && isConnected) {
                try {
                    // Read messages sent by the client
                    String msg = dis.readUTF();
                    
                    System.out.println(msg);
                    //String[] parts = msg.split("#");


                    Document response = Document.parse(msg);
                    String command = response.getString("command");

                    switch (command) {
                        // Processing login message
                        case "Connect":
                            String loginUser = response.getString("ip");
                            // If the user name is logged in, a failure message is returned, otherwise a success message is returned
                            if (clientHandlerMap.containsKey(loginUser)) {
                                String send_str = Login_False().toJson();
                                
                                dos.writeUTF(send_str);
                            } else {


                                String send_str = Login_Success().toJson();
                                
                                dos.writeUTF(send_str);
                                // Add information for this client processing thread to the clientHandlerMap
                                clientHandlerMap.put(loginUser, this);
                                // Send information about existing users to new users
                                // StringBuffer msgUserList = new StringBuffer();

                                ArrayList<String> userlist = new ArrayList<String>();

                                for (String username : clientHandlerMap.keySet()) {

                                    userlist.add(username);

                                }

                                send_str = User_List(userlist).toJson();


                                System.out.println(send_str);

                                
                                //dos.writeUTF(msgUserList.toString());
                                dos.writeUTF(send_str);


                                // Broadcast newly logged in user information to other users
                                //String msgLogin = loginUser;
                                String msgLogin = New_Login(loginUser).toJson();

                                broadcastMsg(loginUser, msgLogin);
                                // Store login user name
                                this.username = loginUser;
                            }
                            break;

                        case "Send_All":

                            String getMsg = response.getString("text");

                            //String msgTalkToAll="TALKTO_ALL#"+username+"#"+getMsg;
                            String msgTalkToAll = Send_All(getMsg, username).toJson();
                            System.out.println(msg);
                            //String getMsg = loginUser;
                            addMsg("(" + username + ") says :" + getMsg);
                            broadcastMsg(username, msgTalkToAll);
                            break;

                        case "Send_One":
                            System.out.println("Send_One");
                            loginUser = response.getString("user");
                            String text = response.getString("text");
                            System.out.println("Send_One:" + loginUser);

                            if (loginUser.equals("Manager: " + username)) {

                                addMsg("(" + username + ") says :" + text);

                            } else {
                                ClientHandler clientHandler = clientHandlerMap.get(loginUser);
                                if (null != clientHandler) {

                                    //String msgTalkTo="TALKTO#"+username+"#"+ loginUser;
                                    String msgTalkTo = Send_One(text, username).toJson();

                                    try {
                                    	
                                        clientHandler.dos.writeUTF(msgTalkTo);
                                        clientHandler.dos.flush();

                                    } catch (IOException e) {
                                        clientHandlerMap.remove(loginUser);
                                        String send_str = Send_Fail().toJson();
                                        
                                        dos.writeUTF(send_str);

                                    }


                                }
                            }


                            break;


                        case "Login_Out":
                            loginUser = response.getString("ip");
                            clientHandlerMap.remove(username);

                            //String msgLogout="LOGOUT#"+username;
                            String msgLogout = New_Login(loginUser).toJson();

                            broadcastMsg(username, msgLogout);
                            isConnected = false;
                            socket.close();
                            break;

                        default:
                            break;
                    }
                } catch (Exception e) {

                    isConnected = false;
                    clientHandlerMap.remove(username);

                    //e.printStackTrace();
                }
            }
        }

        /**
         * Broadcast a message from one user to another
         *
         * @param fromUsername The user who sent the message
         * @param msg          Messages that need to be broadcast
         * @throws Exception 
         */
        private void broadcastMsg(String fromUsername, String msg) throws Exception {
            for (String toUserName : clientHandlerMap.keySet()) {
                if (fromUsername.equals(toUserName) == false) {
                    DataOutputStream dos = clientHandlerMap.get(toUserName).dos;
                    
                    dos.writeUTF(msg);
                    dos.flush();
                }
            }
        }
    }


    /**
     * Add a message to a text box textAreaRecord
     *
     * @param msg，Message to add
     */
    private void addMsg(String msg) {
        // Add a message to the text area and add a line feed
        textAreaRecord.append(msg + "\n");
        // Automatically scroll to the last line of the text area
        textAreaRecord.setCaretPosition(textAreaRecord.getText().length());
    }

    public static Document Connect(String ip) {
        Document request = new Document();
        request.append("command", "Connect");
        request.append("ip", ip);
        return request;
    }

    public static Document Login_Out(String ip) {
        Document request = new Document();
        request.append("command", "Login_Out");
        request.append("ip", ip);
        return request;
    }

    public static Document pop_the_person(String newuser) {
        Document request = new Document();
        request.append("Login_out", newuser);
        request.append("command", "Login_out");
        return request;
    }

    public static Document Send_All(String text) {
        Document request = new Document();
        request.append("command", "Send_All");
        request.append("text", text);
        return request;
    }

    public static Document beenpoped(String text, String user) {
        Document request = new Document();
        request.append("command", "beenpoped");
        request.append("text", text);
        request.append("user", user);
        return request;
    }


    public static Document Send_One(String text, String user) {
        Document request = new Document();
        request.append("command", "Send_One");
        request.append("text", text);
        request.append("user", user);
        return request;
    }

    public class ServerThread implements Runnable {

        /**
         * Start the service
         */
        private void startServer() {
            try {


                // Create socket address
                SocketAddress socketAddress = new InetSocketAddress(serverPort1);
                // Create a ServerSocket, binding socket address
                server = new ServerSocket();
                server.bind(socketAddress);
                // Modify the identity variable that determines whether the server is running
                isRunning = true;
                // Modify the start and stop button status
                //btnStart.setEnabled(false);
                // btnStop.setEnabled(true);
                addMsg("Start Server Successful");

                //String myinfo = socket.getLocalAddress()+ ":"+ socket.getLocalPort();

                Thread t = new Thread(() -> Synchronous_Userlist());
                t.start();


            } catch (IOException e) {

                addMsg("Fail, Please Check the port");
                e.printStackTrace();
                isRunning = false;
            }
        }

        /**
         * Thread body
         */
        @Override
        public void run() {
            System.out.println ("Start serverthread");
            startServer();
            // When the server is running, loop listens to the client's connection request
            while (isRunning) {
                try {
                    Socket socket = server.accept();
                    // Create a thread that interacts with the client

                    Thread thread = new Thread(new ClientHandler(socket));
                    threadPool.execute ( thread );
//                    thread.start();

                } catch (IOException e) {
                    System.out.println("Dont have connect");
                }
            }
        }

    }

    public void Synchronous_Userlist() {

        while (true) {

            try {
                sleep(10000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            modelUsers.removeAllElements();
            ArrayList<String> userlist = new ArrayList<String>();
            userlist.add("Manager: " + username);
            modelUsers.addElement("Manager: " + username);
            for (String username : clientHandlerMap.keySet()) {
                userlist.add(username);
                modelUsers.addElement(username);

            }


            String send_str = User_List(userlist).toJson();
            for (String toUserName : clientHandlerMap.keySet()) {
                DataOutputStream dos = clientHandlerMap.get(toUserName).dos;
                try {
                	
                    dos.writeUTF(send_str);
                    dos.flush();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

        }

    }

    public static Document Login_False() {
        Document request = new Document();
        request.append("command", "Login_False");
        return request;
    }

    public static Document Login_Success() {
        Document request = new Document();
        request.append("command", "Login_Success");
        return request;
    }

    public static Document User_List(ArrayList userlist) {
        Document request = new Document();
        request.append("User_List", userlist);
        request.append("command", "User_List");
        return request;
    }

    public static Document New_Login(String newuser) {
        Document request = new Document();
        request.append("New_Login", newuser);
        request.append("command", "New_Login");
        return request;
    }

    private void poptheperson(String fromUsername, String msg) throws IOException {
        for (String toUserName : clientHandlerMap.keySet()) {
            if (fromUsername.equals(toUserName) == true) {
                clientHandlerMap.remove(toUserName);
            }
        }
    }

    public static Document Send_Fail() {
        Document request = new Document();
        request.append("command", "Send_Fail");
        return request;
    }

    public static Document Send_All(String text, String sendname) {
        Document request = new Document();
        request.append("command", "Send_All");
        request.append("text", text);
        request.append("sendname", sendname);
        return request;
    }

    /**
     * Broadcast a message from one user to another
     *
     * @param fromUsername The user who sent the message
     * @param msg          Messages that need to be broadcast
     * @throws Exception 
     */
    public void broadcastMsg(String fromUsername, String msg) throws Exception {
        for (String toUserName : clientHandlerMap.keySet()) {
            if (fromUsername.equals(toUserName) == false) {
                DataOutputStream dos = clientHandlerMap.get(toUserName).dos;
                
                dos.writeUTF(msg);
                dos.flush();
            }
        }
    }


}



