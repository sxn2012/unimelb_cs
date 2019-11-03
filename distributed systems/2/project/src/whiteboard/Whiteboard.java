package whiteboard;

import java.awt.*;

import javax.swing.*;

import javax.imageio.ImageIO;

import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionAdapter;
import java.awt.image.BufferedImage;
import java.beans.XMLDecoder;
import java.beans.XMLEncoder;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.net.URL;
import java.rmi.RemoteException;
import java.util.HashMap;
import java.util.List;
import java.awt.event.ActionEvent;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import javax.swing.colorchooser.AbstractColorChooserPanel;
import rmi.ServercomInter;

public class Whiteboard extends JFrame {
    private int mode;
    public char userId;
    private static int normal = 0;
    private static int server = 1;
    private static int client = 2;
    private Boolean isinnightmode = false;

    public ServercomInter servercomInter;

    private JFrame frmBoard;
    public Canvas canvas;
    //	private JScrollPane scrollPane;
    private JButton addOval;
    private JButton addRectangle;
    private JButton addSquare;
    private JButton addLine;
    private JButton addText;
    private JTable table;
    private TableModel tableModel;
    private JButton addCircle;
    private JButton addPencil;
    private JButton addEraser;
    private JMenu mnSaveAs;
    private JMenu mnOpen;
    private JMenu mnClose;
    /**
     * Launch the application.
     */
    private int x1 = -999;
    private int x2 = -999;
    private int y1 = -999;
    private int y2 = -999;
    private int locx = -999;
    private int locy = -999;
    private int erasersize = 0;
    private int drawoval = 0;
    private int drawcircle = 0;
    private int drawrect = 0;
    private int drawsquare = 0;
    private int drawline = 0;
    private int drawtext = 0;
    public static Color pencilcolor = null;
    public static Color pencilcolorchoosed = Color.black;
    private boolean IsManager;
    private File file_=null;
//	public static void main(String[] args) {
//		EventQueue.invokeLater(new Runnable() {
//			public void run() {
//				try {
//					Whiteboard window = new Whiteboard();
//					window.frmBoard.setVisible(true);
//				} catch (Exception e) {
//					e.printStackTrace();
//				}
//			}
//		});
//	}

    /**
     * Create the application.
     */
    public Whiteboard(JPanel jPanel, ServercomInter servercomInter, boolean manager) {
        this.servercomInter = servercomInter;
        this.IsManager=manager;
        initialize ( jPanel );
        
    }
    /**
     * Initialize the contents of the frame.
     */
    private void initialize(JPanel jPanel) {
//		frmBoard = new JFrame();
//		frmBoard.setTitle("Board");
//		frmBoard.setBounds(100, 100, 581, 556);
//		frmBoard.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
//		frmBoard.getContentPane().setLayout(null);
//		frmBoard.setResizable(false);
        jPanel.setLayout ( new BorderLayout ( 1, 1 ) );
        JPanel buttons = new JPanel ();
        buttons.setLayout ( new GridLayout ( 13, 1, 0, 0 ) );


        tableModel = new TableModel ();
        table = new JTable ( tableModel );
        table.setAutoResizeMode ( JTable.AUTO_RESIZE_ALL_COLUMNS );
//		scrollPane = new JScrollPane(table);
//		scrollPane.setPreferredSize(new Dimension(380, 400));
//		scrollPane.setBounds(0, 0, 575, 96);
//		frmBoard.getContentPane().add(scrollPane);
//		jPanel.add(scrollPane);

        MouseMotionAdapter I1 = new MouseMotionAdapter () {
            public void mouseDragged(MouseEvent e) {
                x2 = e.getX ();
                y2 = e.getY ();
                Point p1;
                Point p2 = new Point ( x2, y2 );
                System.out.println ("x1:"+x1+" y1:"+y1);
                if (x1 != -999 && y1 != -999) {
                    p1 = new Point ( x1, y1 );
                    p2 = new Point ( x2, y2 );
//                    if (p1 != p2) {
                    if(Math.abs ( p1.x-p2.x )>10 || Math.abs ( p1.y-p2.y )>10){
                        DLineModel model = new DLineModel ( p1, p2 );
                        model.setColor ( pencilcolor );
//                        canvas.recolorShape ( pencilcolor );
                        String id = canvas.addShape ( model, null );
                        canvas.repaint ();
                        new Thread (  ) {
                            public void run() {
                                try {
                                    servercomInter.pubishAddDraw (model, pencilcolor, id);
                                } catch (RemoteException ex) {
                                    ex.printStackTrace ();
                                }
                            }
                        }.start ();
                        x1 = x2;
                        y1 = y2;
                    }
                } else {
                    x1 = x2;
                    y1 = y2;
                }


            }
        };

        MouseMotionAdapter I2 = new MouseMotionAdapter () {
            public void mouseDragged(MouseEvent e) {
                int x2 = e.getX ();
                int y2 = e.getY ();
                Point p1;
                Point p2 = new Point ( x2, y2 );
                System.out.println ("x1:"+x1+" y1:"+y1);
                if (x1 != -999 && y1 != -999) {
                    p1 = new Point ( x1, y1 );
                    p2 = new Point ( x2, y2 );
                    if (Math.abs ( p1.x-p2.x )>10 || Math.abs ( p1.y-p2.y )>10) {
                        DLineModel model = new DLineModel ( p1, p2, erasersize );
                        model.setColor ( Color.WHITE );
//                        canvas.recolorShape ( Color.WHITE );
                        String id = canvas.addShape ( model, null );
                        canvas.repaint ();

                        new Thread (  ) {
                            public void run() {
                                try {
                                    servercomInter.pubishAddDraw (model, pencilcolor, id);
                                } catch (RemoteException ex) {
                                    ex.printStackTrace ();
                                }
                            }
                        }.start ();


                        x1 = x2;
                        y1 = y2;
                    }
                } else {
                    x1 = x2;
                    y1 = y2;
                }


//                System.out.println ("x:"+e.getX ()+" y:"+e.getY ());
////                DRectModel model = new DRectModel ( x, y, erasersize, erasersize, Color.WHITE );
//                DOvalModel model = new DOvalModel ( x, y, erasersize, erasersize, Color.WHITE );
//                canvas.addShape ( model, null );
//
//                canvas.repaint ();
//
//                try {
//                	 servercomInter.pubishAddDraw (model, pencilcolor);
//				} catch (RemoteException ex) {
//					ex.printStackTrace ();
//				}
            }

        };

        MouseListener I3 = new MouseListener () {
            @Override
            public void mouseReleased(MouseEvent e) {
                x1 = -999;
                y1 = -999;

                canvas.removeMouseMotionListener ( I1 );
                canvas.removeMouseMotionListener ( I2 );
                canvas.drag ();
                pencilcolor = null;
            }

            @Override
            public void mouseClicked(MouseEvent e) {
                // TODO Auto-generated method stub

            }

            @Override
            public void mouseEntered(MouseEvent e) {
                // TODO Auto-generated method stub

            }

            @Override
            public void mouseExited(MouseEvent e) {
                // TODO Auto-generated method stub

            }

            @Override
            public void mousePressed(MouseEvent e) {
                // TODO Auto-generated method stub

            }


        };

        MouseListener I4_Oval = new MouseListener () {
            @Override
            public void mouseReleased(MouseEvent e) {

            }

            @Override
            public void mouseClicked(MouseEvent e) {
                // TODO Auto-generated method stub
                if (drawoval != 0) return;
                drawoval = 1;
                locx = e.getX ();
                locy = e.getY ();
                DOvalModel model = new DOvalModel ( locx, locy, 80, 50, Color.BLACK );
                Color color = pencilcolorchoosed;
//                while (color == null || color.equals ( Color.white )) {
//                    JOptionPane.showMessageDialog ( null, "please choose a valid color!", "Error", JOptionPane.INFORMATION_MESSAGE );
//                    color = JColorChooser.showDialog ( jPanel, "Set Color", model.getColor () );
//                }
                int ret = JOptionPane.showConfirmDialog ( null, "Filled Shape?", "Please choose an option", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE );
                if (ret == JOptionPane.YES_OPTION)
                    model.setHollow ( false );
                else
                    model.setHollow ( true );

                String id = canvas.addShape ( model, null );
                canvas.recolorShape ( color );
                canvas.repaint ();
//                drawModel ( model, color, null );
                try {
//                    System.out.println ("("+model.getX ()+","+model.getY ()+")");
                    servercomInter.pubishAddModel ( id, model, color );
                } catch (RemoteException ex) {
                    ex.printStackTrace ();
                }


            }

            @Override
            public void mouseEntered(MouseEvent e) {
                // TODO Auto-generated method stub

            }

            @Override
            public void mouseExited(MouseEvent e) {
                // TODO Auto-generated method stub

            }

            @Override
            public void mousePressed(MouseEvent e) {
                // TODO Auto-generated method stub

            }


        };

        MouseListener I4_Circle = new MouseListener () {
            @Override
            public void mouseReleased(MouseEvent e) {

            }

            @Override
            public void mouseClicked(MouseEvent e) {
                // TODO Auto-generated method stub
                if (drawcircle != 0) return;
                drawcircle = 1;
                locx = e.getX ();
                locy = e.getY ();
                DOvalModel model = new DOvalModel ( locx, locy, 50, 50, Color.BLACK );
                Color color = pencilcolorchoosed;
//                while (color == null || color.equals ( Color.white )) {
//                    JOptionPane.showMessageDialog ( null, "please choose a valid color!", "Error", JOptionPane.INFORMATION_MESSAGE );
//                    color = JColorChooser.showDialog ( jPanel, "Set Color", model.getColor () );
//                }
                int ret = JOptionPane.showConfirmDialog ( null, "Filled Shape?", "Please choose an option", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE );
                if (ret == JOptionPane.YES_OPTION)
                    model.setHollow ( false );
                else
                    model.setHollow ( true );
                String id = canvas.addShape ( model, null );
                canvas.recolorShape ( color );
                canvas.repaint ();
//                drawModel ( model, color, null );
                try {
                    servercomInter.pubishAddModel ( id, model, color );
                } catch (RemoteException ex) {
                    ex.printStackTrace ();
                }
            }

            @Override
            public void mouseEntered(MouseEvent e) {
                // TODO Auto-generated method stub

            }

            @Override
            public void mouseExited(MouseEvent e) {
                // TODO Auto-generated method stub

            }

            @Override
            public void mousePressed(MouseEvent e) {
                // TODO Auto-generated method stub

            }


        };

        MouseListener I4_Rect = new MouseListener () {
            @Override
            public void mouseReleased(MouseEvent e) {

            }

            @Override
            public void mouseClicked(MouseEvent e) {
                // TODO Auto-generated method stub
                if (drawrect != 0) return;
                drawrect = 1;
                locx = e.getX ();
                locy = e.getY ();
                DRectModel model = new DRectModel ( locx, locy, 50, 80, Color.BLACK );
                Color color = pencilcolorchoosed;
//                while (color == null || color.equals ( Color.white )) {
//                    JOptionPane.showMessageDialog ( null, "please choose a valid color!", "Error", JOptionPane.INFORMATION_MESSAGE );
//                    color = JColorChooser.showDialog ( jPanel, "Set Color", model.getColor () );
//                }
                int ret = JOptionPane.showConfirmDialog ( null, "Filled Shape?", "Please choose an option", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE );
                if (ret == JOptionPane.YES_OPTION)
                    model.setHollow ( false );
                else
                    model.setHollow ( true );
                String id = canvas.addShape ( model, null );
                canvas.recolorShape ( color );
                canvas.repaint ();
//                drawModel ( model, color, null );
                try {
                    servercomInter.pubishAddModel ( id, model, color );
                } catch (RemoteException ex) {
                    ex.printStackTrace ();
                }
            }

            @Override
            public void mouseEntered(MouseEvent e) {
                // TODO Auto-generated method stub

            }

            @Override
            public void mouseExited(MouseEvent e) {
                // TODO Auto-generated method stub

            }

            @Override
            public void mousePressed(MouseEvent e) {
                // TODO Auto-generated method stub

            }


        };

        MouseListener I4_Square = new MouseListener () {
            @Override
            public void mouseReleased(MouseEvent e) {

            }

            @Override
            public void mouseClicked(MouseEvent e) {
                // TODO Auto-generated method stub
                if (drawsquare != 0) return;
                drawsquare = 1;
                locx = e.getX ();
                locy = e.getY ();
                DRectModel model = new DRectModel ( locx, locy, 50, 50, Color.BLACK );
                Color color = pencilcolorchoosed;
//                while (color == null || color.equals ( Color.white )) {
//                    JOptionPane.showMessageDialog ( null, "please choose a valid color!", "Error", JOptionPane.INFORMATION_MESSAGE );
//                    color = JColorChooser.showDialog ( jPanel, "Set Color", model.getColor () );
//                }
                int ret = JOptionPane.showConfirmDialog ( null, "Filled Shape?", "Please choose an option", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE );
                if (ret == JOptionPane.YES_OPTION)
                    model.setHollow ( false );
                else
                    model.setHollow ( true );
                String id = canvas.addShape ( model, null );
                canvas.recolorShape ( color );
                canvas.repaint ();
//                drawModel ( model, color, null );
                try {
                    servercomInter.pubishAddModel ( id, model, color );
                } catch (RemoteException ex) {
                    ex.printStackTrace ();
                }
            }

            @Override
            public void mouseEntered(MouseEvent e) {
                // TODO Auto-generated method stub

            }

            @Override
            public void mouseExited(MouseEvent e) {
                // TODO Auto-generated method stub

            }

            @Override
            public void mousePressed(MouseEvent e) {
                // TODO Auto-generated method stub

            }


        };

        MouseListener I4_Line = new MouseListener () {
            @Override
            public void mouseReleased(MouseEvent e) {

            }

            @Override
            public void mouseClicked(MouseEvent e) {
                // TODO Auto-generated method stub
                if (drawline != 0) return;
                drawline = 1;
                locx = e.getX ();
                locy = e.getY ();
                Point p1 = new Point ( locx - 15, locy - 15 );
                Point p2 = new Point ( locx + 15, locy + 15 );
                DLineModel model = new DLineModel ( p1, p2 );
                Color color = pencilcolorchoosed;
//                while (color == null || color.equals ( Color.white )) {
//                    JOptionPane.showMessageDialog ( null, "please choose a valid color!", "Error", JOptionPane.INFORMATION_MESSAGE );
//                    color = JColorChooser.showDialog ( jPanel, "Set Color", model.getColor () );
//                }
                String id = canvas.addShape ( model, null );
                canvas.recolorShape ( color );
                canvas.repaint ();
//                drawModel ( model, color, null );
                try {
                    servercomInter.pubishAddModel ( id, model, color );
                } catch (RemoteException ex) {
                    ex.printStackTrace ();
                }
            }

            @Override
            public void mouseEntered(MouseEvent e) {
                // TODO Auto-generated method stub

            }

            @Override
            public void mouseExited(MouseEvent e) {
                // TODO Auto-generated method stub

            }

            @Override
            public void mousePressed(MouseEvent e) {
                // TODO Auto-generated method stub

            }


        };

        MouseListener I4_Text = new MouseListener () {
            @Override
            public void mouseReleased(MouseEvent e) {

            }

            @Override
            public void mouseClicked(MouseEvent e) {
                // TODO Auto-generated method stub
                if (drawtext != 0) return;
                drawtext = 1;
                locx = e.getX ();
                locy = e.getY ();
                SetText st = new SetText ( canvas, locx, locy, servercomInter );

            }

            @Override
            public void mouseEntered(MouseEvent e) {
                // TODO Auto-generated method stub

            }

            @Override
            public void mouseExited(MouseEvent e) {
                // TODO Auto-generated method stub

            }

            @Override
            public void mousePressed(MouseEvent e) {
                // TODO Auto-generated method stub

            }


        };


        URL imageoval = ClassLoader.getSystemResource("oval.jpg");
        Icon iconoval = new ImageIcon ( imageoval );
        addOval = new JButton ( "", iconoval );
        addOval.setFont ( new Font ( "Times New Roman", Font.PLAIN, 12 ) );
        addOval.addActionListener ( new ActionListener () {
            public void actionPerformed(ActionEvent e) {
                drawoval = 0;
                canvas.removeMouseMotionListener ( I1 );
                canvas.removeMouseMotionListener ( I2 );
                canvas.removeMouseListener ( I3 );
                canvas.removeMouseListener ( I4_Line );
                canvas.removeMouseListener ( I4_Text );
                canvas.removeMouseListener ( I4_Square );
                canvas.removeMouseListener ( I4_Rect );
                canvas.removeMouseListener ( I4_Circle );
                canvas.addMouseListener ( I4_Oval );
            }
        } );
        addOval.setSize ( 30, 30 );
        buttons.add ( addOval );


        URL imagecircle = ClassLoader.getSystemResource("circle.jpg");
        Icon iconcircle = new ImageIcon ( imagecircle);
        addCircle = new JButton ( "", iconcircle );
        addCircle.addActionListener ( new ActionListener () {
            public void actionPerformed(ActionEvent e) {
                drawcircle = 0;
                canvas.removeMouseMotionListener ( I1 );
                canvas.removeMouseMotionListener ( I2 );
                canvas.removeMouseListener ( I3 );
                canvas.removeMouseListener ( I4_Line );
                canvas.removeMouseListener ( I4_Text );
                canvas.removeMouseListener ( I4_Square );
                canvas.removeMouseListener ( I4_Rect );
                canvas.removeMouseListener ( I4_Oval );
                canvas.addMouseListener ( I4_Circle );
            }
        } );
        addCircle.setFont ( new Font ( "Times New Roman", Font.PLAIN, 12 ) );
        addCircle.setSize ( 30, 30 );
        buttons.add ( addCircle );


        URL imagerect = ClassLoader.getSystemResource("rect.jpg");
        Icon iconrect = new ImageIcon ( imagerect );
        addRectangle = new JButton ( "", iconrect );
        addRectangle.addActionListener ( new ActionListener () {
            public void actionPerformed(ActionEvent e) {
                drawrect = 0;
                canvas.removeMouseMotionListener ( I1 );
                canvas.removeMouseMotionListener ( I2 );
                canvas.removeMouseListener ( I3 );
                canvas.removeMouseListener ( I4_Line );
                canvas.removeMouseListener ( I4_Text );
                canvas.removeMouseListener ( I4_Square );
                canvas.removeMouseListener ( I4_Oval );
                canvas.removeMouseListener ( I4_Circle );
                canvas.addMouseListener ( I4_Rect );
            }
        } );
        addRectangle.setFont ( new Font ( "Times New Roman", Font.PLAIN, 12 ) );
        addRectangle.setSize ( 30, 30 );
        buttons.add ( addRectangle );


        URL imagersquare = ClassLoader.getSystemResource("square.jpg");
        Icon iconsquare = new ImageIcon ( imagersquare );
        addSquare = new JButton ( "", iconsquare );
        addSquare.addActionListener ( new ActionListener () {
            public void actionPerformed(ActionEvent e) {
                drawsquare = 0;
                canvas.removeMouseMotionListener ( I1 );
                canvas.removeMouseMotionListener ( I2 );
                canvas.removeMouseListener ( I3 );
                canvas.removeMouseListener ( I4_Line );
                canvas.removeMouseListener ( I4_Text );
                canvas.removeMouseListener ( I4_Oval );
                canvas.removeMouseListener ( I4_Rect );
                canvas.removeMouseListener ( I4_Circle );
                canvas.addMouseListener ( I4_Square );
            }
        } );
        addSquare.setFont ( new Font ( "Times New Roman", Font.PLAIN, 12 ) );
        addSquare.setSize ( 30, 30 );
        buttons.add ( addSquare );

        URL imageline = ClassLoader.getSystemResource("line.jpg");
        Icon iconline = new ImageIcon ( imageline );
        addLine = new JButton ( "", iconline );
        addLine.addActionListener ( new ActionListener () {
            public void actionPerformed(ActionEvent e) {
                drawline = 0;
                canvas.removeMouseMotionListener ( I1 );
                canvas.removeMouseMotionListener ( I2 );
                canvas.removeMouseListener ( I3 );
                canvas.removeMouseListener ( I4_Oval );
                canvas.removeMouseListener ( I4_Text );
                canvas.removeMouseListener ( I4_Square );
                canvas.removeMouseListener ( I4_Rect );
                canvas.removeMouseListener ( I4_Circle );
                canvas.addMouseListener ( I4_Line );
            }
        } );
        addLine.setFont ( new Font ( "Times New Roman", Font.PLAIN, 12 ) );
        addLine.setSize ( 30, 30 );
        buttons.add ( addLine );

        URL imagepen = ClassLoader.getSystemResource("pencil.jpg");
        Icon iconpencil = new ImageIcon ( imagepen);
        addPencil = new JButton ( "", iconpencil );
        addPencil.setFont ( new Font ( "Times New Roman", Font.PLAIN, 12 ) );
        addPencil.addActionListener ( new ActionListener () {
            public void actionPerformed(ActionEvent e) {
                canvas.removeMouseMotionListener ( I2 );
                canvas.removeMouseListener ( I4_Line );
                canvas.removeMouseListener ( I4_Text );
                canvas.removeMouseListener ( I4_Oval );
                canvas.removeMouseListener ( I4_Rect );
                canvas.removeMouseListener ( I4_Circle );
                canvas.removeMouseListener ( I4_Square );
                pencilcolor = null;
                pencilcolor = pencilcolorchoosed;
//                while (pencilcolor == null || pencilcolor.equals ( Color.white )) {
//                    JOptionPane.showMessageDialog ( null, "please choose a valid color!", "Error", JOptionPane.INFORMATION_MESSAGE );
//                    pencilcolor = JColorChooser.showDialog ( jPanel, "Set Color", Color.BLACK );
//                }
                canvas.addMouseMotionListener ( I1 );
                canvas.addMouseListener ( I3 );
                canvas.removeDrag ();
            }
        } );
        addPencil.setSize ( 30, 30 );
        buttons.add ( addPencil );


        URL imagetext = ClassLoader.getSystemResource("text.jpg");
        Icon icontext = new ImageIcon ( imagetext );
        addText = new JButton ( "", icontext );
        addText.addActionListener ( new ActionListener () {
            public void actionPerformed(ActionEvent e) {
                drawtext = 0;
                canvas.removeMouseMotionListener ( I1 );
                canvas.removeMouseMotionListener ( I2 );
                canvas.removeMouseListener ( I3 );
                canvas.removeMouseListener ( I4_Line );
                canvas.removeMouseListener ( I4_Oval );
                canvas.removeMouseListener ( I4_Square );
                canvas.removeMouseListener ( I4_Rect );
                canvas.removeMouseListener ( I4_Circle );
                canvas.addMouseListener ( I4_Text );


            }
        } );
        addText.setFont ( new Font ( "Times New Roman", Font.PLAIN, 12 ) );
        addText.setSize ( 30, 30 );
        buttons.add ( addText );

        URL imagesunny = ClassLoader.getSystemResource("sunny.png");
        Icon iconmode=new ImageIcon(imagesunny);
        URL imagemoon = ClassLoader.getSystemResource("moon.png");
        Icon iconmoden=new ImageIcon(imagemoon);


        JButton changemodebtn = new JButton("", iconmode);
        changemodebtn.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e)
            {
                canvas.changenightmode();
                if(!isinnightmode){
                    changemodebtn.setIcon(iconmoden);
                    isinnightmode=true;
                }
                else{
                    changemodebtn.setIcon(iconmode);
                    isinnightmode=false;
                }
                repaint();
            }
        });
        changemodebtn.setFont(new Font("Times New Roman", Font.PLAIN, 12));
        changemodebtn.setBounds(0, 189, 33, 23);
        buttons.add(changemodebtn);


        URL imageeraser = ClassLoader.getSystemResource("eraser.jpg");
        Icon iconeraser = new ImageIcon ( imageeraser );
        addEraser = new JButton ( "" ,iconeraser);
        addEraser.addActionListener ( new ActionListener () {
            public void actionPerformed(ActionEvent e) {
                canvas.removeMouseMotionListener ( I1 );
                canvas.removeMouseListener ( I4_Line );
                canvas.removeMouseListener ( I4_Text );
                canvas.removeMouseListener ( I4_Oval );
                canvas.removeMouseListener ( I4_Rect );
                canvas.removeMouseListener ( I4_Circle );
                canvas.removeMouseListener ( I4_Square );
                Object[] Esizes = {"very small", "small", "medium", "big", "very big"};
                int ret = JOptionPane.showOptionDialog ( null, "Please select the size of eraser", "option", JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE, null, Esizes, Esizes[0] );
                switch (ret) {
                    case 0:
                        erasersize = 10;
                        break;
                    case 1:
                        erasersize = 25;
                        break;
                    case 2:
                        erasersize = 50;
                        break;
                    case 3:
                        erasersize = 65;
                        break;
                    case 4:
                        erasersize = 85;
                        break;
                }

                canvas.addMouseMotionListener ( I2 );
                canvas.addMouseListener ( I3 );
                canvas.removeDrag ();
            }
        } );
        addEraser.setFont ( new Font ( "Times New Roman", Font.PLAIN, 12 ) );
        addEraser.setSize ( 30, 30 );
        buttons.add ( addEraser );

//		JMenuBar menuBar = new JMenuBar();
//		frmBoard.setJMenuBar(menuBar);

//		JMenu mnFile = new JMenu("File");
//		menuBar.add(mnFile);
//		//mnFile.addSeparator();
        URL imagenew = ClassLoader.getSystemResource("new.jpg");
        Icon iconnew = new ImageIcon ( imagenew );
        JButton mntmNew = new JButton ( "",iconnew );
        mntmNew.setSize ( 30, 30 );
//		mnFile.add(mntmNew);
        mntmNew.addActionListener ( new ActionListener () {
            @Override
            public void actionPerformed(ActionEvent e) {
            	try {
                    servercomInter.removeallModel ();
                } catch (RemoteException ex) {
                    ex.printStackTrace ();
                }
                canvas.setNull ();
                repaint ();
                file_=null;
            }
        } );
        buttons.add ( mntmNew );
        if(!IsManager) mntmNew.setEnabled(false);
//		mnFile.addSeparator();

        URL imageopen = ClassLoader.getSystemResource("open.jpg");
        Icon iconopen = new ImageIcon ( imageopen );
        JButton mntmOpen = new JButton ( "",iconopen );
        mntmOpen.setSize ( 30, 30 );
//		mnFile.add(mntmOpen);
        mntmOpen.addActionListener ( new ActionListener () {
            public void actionPerformed(ActionEvent e) {
                JFileChooser fc = new JFileChooser ();
                fc.setDialogTitle ( "Open" );
                int result = fc.showOpenDialog ( frmBoard );

                if (result == JFileChooser.APPROVE_OPTION) {
                	try {
                        servercomInter.removeallModel ();
                    } catch (RemoteException ex) {
                        ex.printStackTrace ();
                    }
                    canvas.setNull ();
                    repaint ();
                    File f = fc.getSelectedFile ();
                    //open(f);
                    //clear ();
                    BoardThread bt = new BoardThread ();
                    bt.init ( 2, f, canvas );
                    bt.start ();
                    file_=f;
                }
            }
        } );
        buttons.add ( mntmOpen );
        if(!IsManager) mntmOpen.setEnabled(false);

//		mnFile.addSeparator();
        URL imagesave = ClassLoader.getSystemResource("save.jpg");
        Icon iconsave = new ImageIcon ( imagesave );
        JButton mntmSave = new JButton ( "",iconsave );
        mntmSave.setSize ( 30, 30 );
//		mnFile.add(mntmSave);
        mntmSave.addActionListener ( new ActionListener () {
            public void actionPerformed(ActionEvent e) {
            	if(file_==null)
            	{
            		JFileChooser fc = new JFileChooser ();
            		fc.setDialogTitle ( "Save" );
            		int result = fc.showSaveDialog ( frmBoard );

            		if (result == JFileChooser.APPROVE_OPTION) 
            		{

            			File f = fc.getSelectedFile ();
            			//save(f);
            			BoardThread bt = new BoardThread ();
            			bt.init ( 1, f, canvas );
            			bt.start ();
            			file_=f;
            		}
            	}
				else
				{
					BoardThread bt=new BoardThread();
		            bt.init(1, file_,canvas);
		            bt.start();
				}
            }
            	
        } );
        buttons.add ( mntmSave );
        if(!IsManager) mntmSave.setEnabled(false);

//		mnFile.addSeparator();
        URL imagesaveas = ClassLoader.getSystemResource("saveas.jpg");
        Icon iconsaveas = new ImageIcon ( imagesaveas);
        JButton mntmSaveAs = new JButton ( "",iconsaveas );
        mntmSaveAs.setSize ( 30, 30 );
//		mnFile.add(mntmSaveAs);
        mntmSaveAs.addActionListener ( new ActionListener () {
            public void actionPerformed(ActionEvent e) {
            	Object[] Choices = {"Save As Image", "Save As Another File"};
                int ret = JOptionPane.showOptionDialog ( null, "What type of file would you like to save?", "option", JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE, null, Choices, Choices[0] );
                if(ret==0)
                {
                	JFileChooser fc = new JFileChooser ();
                	fc.setDialogTitle ( "Save As Image" );
                	int result = fc.showSaveDialog ( frmBoard );

                	if (result == JFileChooser.APPROVE_OPTION) 
                	{

                		File f = fc.getSelectedFile ();
                		if (f.getName ().matches ( "^.+\\.jpg$" )) 
                		{
                			BoardThread bt = new BoardThread ();
                			bt.init ( 3, f, canvas );
                			bt.start ();
                		} 
                		else
                			JOptionPane.showMessageDialog ( null, "only jpg format is accepted!", "Error", JOptionPane.INFORMATION_MESSAGE );
                	}
                
                }
                else
                {
                	JFileChooser fc=new JFileChooser();
    				fc.setDialogTitle("Save As");
    				int result = fc.showSaveDialog(frmBoard);

    		        if (result == JFileChooser.APPROVE_OPTION) 
    		        {
    		           
    		            File f = fc.getSelectedFile();
    		            //save(f);
    		            BoardThread bt=new BoardThread();
    		            bt.init(1, f,canvas);
    		            bt.start();
    		        }
                }
            }
        } );
        buttons.add ( mntmSaveAs );
        if(!IsManager) mntmSaveAs.setEnabled(false);
        JButton colorchoose = new JButton();
        colorchoose.setOpaque(true);
        colorchoose.setBorderPainted(false);
        colorchoose.setBackground(Color.BLACK);
        JColorChooser chooser = new JColorChooser();
        AbstractColorChooserPanel panels[] = chooser.getChooserPanels();
        for (int i = 0; i < panels.length; i ++) {
            chooser.removeChooserPanel(panels[i]);
        }
        chooser.addChooserPanel(new colorchooser());
        JDialog color = JColorChooser.createDialog(colorchoose, "Set Color", true,chooser,new ActionListener() {
            public void actionPerformed(ActionEvent e)
            {
                System.out.println(chooser.getColor());
                colorchoose.setBackground(chooser.getColor());
                pencilcolorchoosed=chooser.getColor();
            }
        },null);
        colorchoose.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e)
            {
                color.setVisible(true);
                
            }
        });

        colorchoose.setFont(new Font("Times New Roman", Font.PLAIN, 12));
        colorchoose.setBounds(0, 210, 33, 23);
        buttons.add(colorchoose);
//        JPanel frame = new JPanel();
//        frame.add(new JButton("dasdfsfsdfsdf"));
//        frame.setLocation(0,0);
//        frame.setSize(500,500);
//        buttons.add(frame);
//        frame.setVisible(false);

//		mnFile.addSeparator();
        URL imageclose = ClassLoader.getSystemResource("close.jpg");
        Icon iconclose = new ImageIcon ( imageclose);
        JButton mntmClose = new JButton ( "",iconclose );
        mntmClose.setSize ( 30, 30 );
//		mnFile.add(mntmClose);
        mntmClose.addActionListener ( new ActionListener () {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.exit ( 0 );
            }
        } );
        buttons.add ( mntmClose );
        if(!IsManager) mntmClose.setEnabled(false);
        buttons.setPreferredSize ( new Dimension ( 60, 390 ) );
        jPanel.add ( buttons, BorderLayout.WEST );
        canvas = new Canvas ( this );
        jPanel.add ( canvas, BorderLayout.CENTER );

    }

    public void drawModel(DShapeModel model, Color color, String id) {
        new Thread() {
            public void run(){
                if (canvas.addShape ( model, id )!=null){
//                    canvas.recolorShape ( color );
                    canvas.repaint ();
                }
            }
        }.start ();


    }

    public void drawPenEraser(DShapeModel model, Color pencilcolor, String id) {
        new Thread() {
            public void run() {
                canvas.addShape ( model, id );
//                canvas.recolorShape ( pencilcolor );
                canvas.repaint ();
            }
        }.start ();

    }

    public void drawText(String modelId, DShapeModel model, Color color, String font, String text) {
        new Thread (  ) {
            public void run() {
                canvas.addShape(model,modelId);
//                canvas.recolorShape(color);
//                canvas.setFont(font);
//                canvas.setText(text);
                canvas.repaint();
            }
        }.start ();


    }

    public int getMode() {
        return mode;
    }

    public void updateTable(DShape selectedShape) {
        table.clearSelection ();
        if (selectedShape != null) {
            int index = tableModel.getRow ( selectedShape.getModel () );
            table.setRowSelectionInterval ( index, index );
        }
    }

    public void add(DShape shape) {
        tableModel.add ( shape.getModel () );

    }

    public void delete(DShape shape) {
        tableModel.delete ( shape.getModel () );

    }


    public void clear() {
        tableModel.clear ();
    }

    public void save(File file) {
        try {
            XMLEncoder xmlout = new XMLEncoder ( new BufferedOutputStream ( new FileOutputStream ( file ) ) );

            ConcurrentHashMap <String, DShape> shapes = canvas.getShapes ();
            DShapeModel[] models = new DShapeModel[shapes.size ()];

            int i = 0;
            for (Map.Entry <String, DShape> entry : shapes.entrySet ()) {
                System.out.println ( entry.getKey () + "：" + entry.getValue () );
                models[i++] = entry.getValue ().getModel ();
            }

            xmlout.writeObject ( models );
            xmlout.close ();
            JOptionPane.showMessageDialog ( null, "Successfully Save!", "Message", JOptionPane.INFORMATION_MESSAGE );

        } catch (Exception e) {
            JOptionPane.showMessageDialog ( null, e.toString (), "Error", JOptionPane.INFORMATION_MESSAGE );
        }
    }

    public void savetodb(File file) {
        try {
            XMLEncoder xmlout = new XMLEncoder ( new BufferedOutputStream ( new FileOutputStream ( file ) ) );

            ConcurrentHashMap <String, DShape> shapes = canvas.getShapes ();
            DShapeModel[] models = new DShapeModel[shapes.size ()];

            int i = 0;
            for (Map.Entry <String, DShape> entry : shapes.entrySet ()) {
                System.out.println ( entry.getKey () + "：" + entry.getValue () );
                models[i++] = entry.getValue ().getModel ();
            }

            xmlout.writeObject ( models );
            xmlout.close ();
            JOptionPane.showMessageDialog ( null, "Successfully Save!", "Message", JOptionPane.INFORMATION_MESSAGE );

        } catch (Exception e) {
            JOptionPane.showMessageDialog ( null, e.toString (), "Error", JOptionPane.INFORMATION_MESSAGE );
        }
    }

    public void saveImage(File file) {

        Dimension imagesize = canvas.getSize ();
        BufferedImage image = new BufferedImage ( imagesize.width, imagesize.height, BufferedImage.TYPE_INT_RGB );
        Graphics2D graphics = image.createGraphics ();
        canvas.paint ( graphics );
        graphics.dispose ();
        File f = file;
        try {
            if (!f.exists ())
                f.createNewFile ();
            ImageIO.write ( image, "jpg", f );
            JOptionPane.showMessageDialog ( null, "Successfully Save Image!", "Message", JOptionPane.INFORMATION_MESSAGE );
        } catch (Exception e) {
            JOptionPane.showMessageDialog ( null, e.toString (), "Error", JOptionPane.INFORMATION_MESSAGE );
        }

    }

    public void open(File file) {
        try {
            XMLDecoder xmlin = new XMLDecoder ( new FileInputStream ( file ) );
            DShapeModel[] models = (DShapeModel[]) xmlin.readObject ();
            xmlin.close ();


            clear ();

            for (int i = 0; i < models.length; i++) {
                //TODO: 重新打开之后model要输入id
                canvas.addShape ( models[i], null );
            }
            JOptionPane.showMessageDialog ( null, "Successfully Open!", "Message", JOptionPane.INFORMATION_MESSAGE );

        } catch (Exception e) {
            JOptionPane.showMessageDialog ( null, e.toString (), "Error", JOptionPane.INFORMATION_MESSAGE );

        }
    }

    public char getUserId() {
        return userId;
    }

    public void setUserId(char userId) {
        this.userId = userId;
    }
}
