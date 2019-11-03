import chat.ClientChat;
import chat.ServerChat;
import rmi.ClientcomInter;
import rmi.ServercomInter;
import util.Document;
import whiteboard.Canvas;
import whiteboard.Whiteboard;

import javax.swing.*;
import javax.swing.border.*;

import java.awt.*;
import java.awt.event.*;
import java.io.FileDescriptor;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.rmi.Naming;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.regex.Pattern;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.util.Enumeration;




public class Startclient {
    static ClientCom client;
    static ServercomInter server;
    static ClientChat clientChat;
    static ServerChat serverChat;
    static Whiteboard whiteboard;
    static public ExecutorService threadPool = Executors.newFixedThreadPool(10);
    static Boolean issuper = false;

    ConnectThread tmpConnectThread = null;
    static private final String IPV4_REGEX = "^(([0-9]\\.)|([1-9]\\d{1}\\.)|(1\\d{2}\\.)|(2[0-4]\\d{1}\\.)|(25[0-4]\\.)){3}(([0-9])|([1-9]\\d{1})|(1\\d{2})|(2[0-4]\\d{1})|(25[0-4]))$";
    static private Pattern IPV4_PATTERN = Pattern.compile(IPV4_REGEX);
    static private final String PORT_REGEX = "^([1-9]|[1-9]\\d{1,3}|[1-5]\\d{4}|6[0-4]\\d{3}|65[0-4]d{2}|655[0-2]d{1}|6553[0-5]|[1-6][0-5][0-5][0-3][0-5])$";    static private Pattern PORT_PATTERN = Pattern.compile(PORT_REGEX);


    private static void shutdown()
    {
        Runtime.getRuntime().addShutdownHook(new Thread(() -> {

            try {
                clientChat.threadPool.shutdownNow();
                threadPool.shutdownNow();
                if(!Startclient.issuper) {
                    clientChat.logout();
                    server.logout(client);

                }
                //server.logout(client);
                System.out.println("exit successfully");
                Runtime.getRuntime().halt(0);
                System.exit(-1);
            } catch (Exception e1) {
                e1.printStackTrace();
            }

        }));
    }

    public static String getServerIp() {
        String localip = null;// 本地IP，如果没有配置外网IP则返回它
        String netip = null;// 外网IP
        try {
            Enumeration netInterfaces = NetworkInterface.getNetworkInterfaces();
            InetAddress ip = null;
            boolean finded = false;// 是否找到外网IP
            while (netInterfaces.hasMoreElements() && !finded) {
                NetworkInterface ni = (NetworkInterface) netInterfaces.nextElement();
                Enumeration address = ni.getInetAddresses();
                while (address.hasMoreElements()) {
                    ip = (InetAddress) address.nextElement();
                    if (!ip.isSiteLocalAddress() && !ip.isLoopbackAddress() && ip.getHostAddress().indexOf(":") == -1) {// 外网IP
                        netip = ip.getHostAddress();
                        finded = true;
                        break;
                    } else if (ip.isSiteLocalAddress() && !ip.isLoopbackAddress() && ip.getHostAddress().indexOf(":") == -1) {// 内网IP
                        localip = ip.getHostAddress();
                    }
                }
            }
        } catch (SocketException e) {
            e.printStackTrace();
        }

        if (netip != null && !"".equals(netip)) {
            return netip;
        } else {
            return localip;
        }
    }

    static boolean isValidIPV4(final String s) {
        return IPV4_PATTERN.matcher(s).matches();
    }
    
    static boolean isValidPort(final String s){
    	return PORT_PATTERN.matcher(s).matches();
    }
    
/*
    public void doConnect(JButton connect) {
        Canvas.nightmode = false;
        if (this.connect.getText().equals("Connect")) {
            if (name.getText().length() < 2) {
                JOptionPane.showMessageDialog(frame, "You need to type a name");
                return;
            }
            if (ip.getText().length() < 2) {
                JOptionPane.showMessageDialog(frame, "You need to type an IP");
                return;
            }
            if(!isValidPort(port.getText()))
            {
            	JOptionPane.showMessageDialog(frame, "You need to type a valid port number");
                return;
            }
            if (!isValidIPV4(ip.getText())) {
                JOptionPane.showMessageDialog(frame, "You need to type a valid ip address");
                return;
            }
            try {
                
                meddle.setVisible(true);
                client = new ClientCom(name.getText());
                client.setGUI(this);
                server = (ServercomInter) Naming.lookup("rmi://" + ip.getText() + "/myabc");
                char loginsuccess = server.login(client);
                if (loginsuccess != 0) {
                    createnewboard.setVisible(false);
                    updateUsers(server.getConnected());
                    this.connect.setText("Disconnect");
                    name.setEditable(false);
                    ip.setEditable(false);
                    port.setEditable(false);
                    whiteboard = new Whiteboard(whiteBoard, server, false);
                    whiteboard.setUserId(loginsuccess);
                    clientChat = new ClientChat(chat, whiteBoard, tx, name.getText(), ip.getText(), Integer.parseInt(port.getText()), connect, threadPool);
                    client.setWhiteboard(whiteboard);
                    server.resumemodelhistory(client);
                    clientChat.left.setVisible(true);
                    clientChat.right.setVisible(true);
                    tx.setVisible(true);
                    frame.setSize(1400, 600);
                    Toolkit toolkit = Toolkit.getDefaultToolkit();
                    int x = (int)(toolkit.getScreenSize().getWidth()-frame.getWidth()-200)/2;
                    int y = (int)(toolkit.getScreenSize().getHeight()-frame.getHeight()-200)/2;
                    frame.setLocation(x, y);
                } else {
                    JOptionPane.showMessageDialog(frame, "You are not allowed or cannot connect to the service.");

                }
            } catch (Exception e) {
                e.printStackTrace();
                JOptionPane.showMessageDialog(frame, "ERROR, we wouldn't connect....");
            }
        } else {
            try {


                meddle.setVisible(false);
                tx.setVisible(false);

                clientChat.logout();
                server.logout(client);
                chat.removeAll();
                whiteBoard.removeAll();

                clientChat = null;
                client.setWhiteboard(null);
                tx.setText("");
                name.setEditable(true);
                ip.setEditable(true);
                port.setEditable(true);
                //server.logout(client);
                frame.setSize(1400, 100);

            } catch (Exception e) {
                e.printStackTrace();
                JOptionPane.showMessageDialog(frame, "ERROR, cannot discoonnect bconnect....");
            }

            updateUsers(null);
            this.connect.setText("Connect");
            //Better to implement Logout ....
        }
    }
*/
    /*
    public void createBoad() {
        try {
            if (name.getText().length() < 2) {
                JOptionPane.showMessageDialog(frame, "You need to type a name.");
                return;
            }
            java.rmi.registry.LocateRegistry.createRegistry(1099);
            ServercomInter b = new Servercom();
            Naming.rebind("rmi://localhost/myabc", b);
            System.out.println("[System] Chat Server is ready.");
            System.out.println( getServerIp());

            try {
                client = new ClientCom(name.getText());
                client.setGUI(this);
                server = (ServercomInter) Naming.lookup("rmi://localhost/myabc");
                Boolean loginsuccess = server.creatorlogin(client);
                if (loginsuccess) {
                    updateUsers(server.getConnected());
                    connect.setText("Disconnect");
                    server.publish("ip address: "+getServerIp());
                    whiteboard = new Whiteboard(whiteBoard, server, true);
                    whiteboard.setUserId('0');
                    serverChat = new ServerChat(chat, name.getText(), Integer.parseInt(port.getText()), threadPool);
                    client.setWhiteboard(whiteboard);
                }
                frame.setSize(1400, 600);
                Toolkit toolkit = Toolkit.getDefaultToolkit();
                int x = (int)(toolkit.getScreenSize().getWidth()-frame.getWidth()-200)/2;
                int y = (int)(toolkit.getScreenSize().getHeight()-frame.getHeight()-200)/2;
                frame.setLocation(x, y);
                meddle.setVisible(true);
            } catch (Exception e) {
                e.printStackTrace();
                JOptionPane.showMessageDialog(frame, "ERROR, we wouldn't create board");
            }
            issuper = true;
            createnewboard.setVisible(false);
            top.setVisible(false);
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(frame, "ERROR, we cannot create board");
        }

    }
*/
    public void sendText() {
        if (connect.getText().equals("Connect")) {
            JOptionPane.showMessageDialog(frame, "You need to connect first.");
            return;
        }
        String st = tf.getText();
        try {
            st = "[" + client.getName() + "] " + st;
        } catch (Exception e) {
            e.printStackTrace();
        }
        tf.setText("");
        //Remove if you are going to implement for remote invocation
        try {
            server.publish(st);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void writeMsg(String st) {
        try {
            updateUsers(server.getConnected());
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(frame, "ERROR, updateuserlist");
        }
        SimpleDateFormat sdf = new SimpleDateFormat();
        sdf.applyPattern("yyyy-MM-dd HH:mm:ss");
        Date date = new Date();
        st = sdf.format(date) + "  " + st;
        if (tx.getText().length() != 0)
            tx.setText(tx.getText() + "\n" + st);
        else
            tx.setText(st);

    }

    public void updateUsers(Vector v) {
        DefaultListModel listModel = new DefaultListModel();
        if (v != null) for (int i = 0; i < v.size(); i++) {
            try {
                String tmp = ((ClientcomInter) v.get(i)).getName();
                listModel.addElement(tmp);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        lst.setModel(listModel);
    }

    public static void main(String[] args) {
        System.setErr(new PrintStream(new FileOutputStream(new FileDescriptor())));
        shutdown();
        Startclient c = new Startclient();

    }


    //User Interface code.
    public Startclient() {

        frame = new JFrame("WhiteBoard");


        main = new JPanel();

        top = new JPanel();
        meddle = new JPanel();
        bottom = new JPanel();

        whiteBoard = new JPanel();
        cn = new JPanel();

        ip = new JTextField("127.0.0.1");
        tf = new JTextField();
        name = new JTextField("anomousy");
        port = new JTextField("8000");
        tx = new JTextArea();
        tx.setEditable(false);
        tx.setBackground(frame.getBackground());
        JScrollPane jp = new JScrollPane(tx);


        jp.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);

        jp.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);

        connect = new JButton("Connect");
        createnewboard = new JButton("createnewboard");
        JButton bt = new JButton("Send");
        lst = new JList();
        chat = new JPanel();

        jp.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);


        top.setLayout(new GridLayout(1, 0, 5, 5));
        top.add(createnewboard);
        top.add(new JLabel("Your name: "));
        top.add(name);
        top.add(new JLabel("Server Address: "));
        top.add(ip);
        top.add(new JLabel("Port Number: "));
        top.add(port);
        top.add(connect);


        whiteBoard.setLayout(new BorderLayout(0, 0));
        whiteBoard.setPreferredSize(new Dimension(1200, 600));
        cn.setLayout(new BorderLayout(5, 5));
//        cn.add(new JScrollPane(tx), BorderLayout.CENTER);
//        cn.add(lst, BorderLayout.EAST);
        cn.add(chat, BorderLayout.WEST);
        bottom.setLayout(new BorderLayout(10, 10));
        //bottom.add(tx, BorderLayout.CENTER);
        bottom.add(jp, BorderLayout.CENTER);
        bottom.setPreferredSize(new Dimension(100, 100));
        bottom.setVisible(true);

        meddle.setLayout(new BorderLayout(5, 0));
        meddle.add(whiteBoard, BorderLayout.CENTER);
        meddle.add(cn, BorderLayout.EAST);
        meddle.add(bottom, BorderLayout.SOUTH);
        meddle.setVisible(false);


//        bottom.add(tf, BorderLayout.CENTER);
//        bottom.add(bt, BorderLayout.EAST);

        main.setLayout(new BorderLayout(0, 0));
        main.add(top, BorderLayout.NORTH);
        main.add(meddle, BorderLayout.CENTER);
//        main.add(cn, BorderLayout.CENTER);
//        main.add(bottom, BorderLayout.SOUTH);
        main.setBorder(new EmptyBorder(10, 5, 10, 10));
        Startclient temp=this;
        //Events
        connect.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if(tmpConnectThread!=null) {
                    tmpConnectThread.interrupt ();
                }
                ConnectThread ct = new ConnectThread(temp, connect);
                tmpConnectThread = ct;
                threadPool.execute(ct);
                try {
                    this.wait (1000);
                } catch (InterruptedException ex) {
                    ex.printStackTrace ();
                }
            }
        });
        bt.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                sendText();
            }
        });
        tf.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                sendText();
            }
        });
        
        createnewboard.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                CreateThread ct=new CreateThread(temp);
                threadPool.execute ( ct );

            }
        });
        frame.setContentPane(main);
        frame.setSize(1400, 100);
        Toolkit toolkit = Toolkit.getDefaultToolkit();
        int x = (int)(toolkit.getScreenSize().getWidth()-frame.getWidth()-200)/2;
        int y = (int)(toolkit.getScreenSize().getHeight()-frame.getHeight()-200)/2;
        frame.setLocation(x, y);
        frame.setVisible(true);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

//        frame.addWindowListener(new WindowAdapter() {
//
//            public void windowClosing(WindowEvent e) {
//                super.windowClosing(e);
//                try {
//                    clientChat.logout();
//                    server.logout(client);
//                } catch (Exception e1) {
//                    System.out.println("dasds");
//                }
//                System.exit(0);
//            }
//
//        });
    }

    JTextArea tx;
    JTextField tf, ip, name, port;
    JButton connect;
    JButton createnewboard;
    JList lst;
    JPanel top;
    JPanel meddle;
    JPanel cn;
    JPanel bottom;
    JPanel whiteBoard;
    JPanel main;
    JPanel chat;
    static public JFrame frame;
}

