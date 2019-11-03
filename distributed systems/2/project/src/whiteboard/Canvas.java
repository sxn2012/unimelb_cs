package whiteboard;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionAdapter;
import java.rmi.RemoteException;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentLinkedQueue;

import javax.swing.JPanel;

public class Canvas extends JPanel {
    public int count = 0;
    static public boolean nightmode=false;
    private DShape selected;
    private Point pivotKnob;
    private Point movingKnob;
    private ConcurrentLinkedQueue<String> order;
    private ConcurrentHashMap <String, DShape> shapes;
    private ArrayList <Point> knobs;
    private int x = 0;
    private int y = 0;
    Whiteboard board;
    private MouseMotionAdapter dragListener = null;
    private MouseListener releaseListener = null;
    private int dragFlag = 0;
    private boolean isMove = false;
    private boolean isDistor = false;
    private String tmpId;
    private Point tmpPivot;
    private Point tmpMoving;
    private int tmpNewX;
    private int tmpNewY;
    Color lessblack = new Color(1,1,1);


    public void changenightmode()
    {
        if(!nightmode)
        {
            setBackground(Color.black);
            nightmode=true;
        }
        else
        {
            setBackground(Color.WHITE);
            nightmode=false;
        }
    }

    public Canvas(Whiteboard board) {
        this.board = board;
        this.setBackground ( Color.WHITE );
        canvasClicked ();
        this.dragListener = new MouseMotionAdapter () {
            public void mouseDragged(MouseEvent e) {
                if (board.getMode () != 2) {
                    int dx = e.getX () - x;
                    int dy = e.getY () - y;

                    int newX = e.getX ();
                    int newY = e.getY ();
                    if (newX != x || newY != y) {
                    	if (movingKnob != null) {
                            movingKnob.x += dx;
                            movingKnob.y += dy;
                            selected.resize ( pivotKnob, movingKnob );
                            isDistor = true;
                            tmpPivot = pivotKnob;
                            tmpMoving = movingKnob;
                            tmpId = selected.getModelId ();
                            repaint ();
//                            try {
//                                System.out.println ("drawDistortion model:"+selected.getModelId ()+"从("+pivotKnob.x+","+pivotKnob.y+") "+"移动至:("+movingKnob.x+","+movingKnob.y+")");
//                                board.servercomInter.pubishDistortion ( selected.getModelId (), pivotKnob, movingKnob );
//                            } catch (RemoteException ex) {
//                                ex.printStackTrace ();
//                            }
                        } else {
                            if (selected != null) {
                                selected.moveTo ( newX, newY );

                                board.updateTable ( selected );

                                isMove = true;
                                tmpId = selected.getModelId ();
                                tmpNewX = newX;
                                tmpNewY = newY;
                                repaint ();
//                                try {
//                                    System.out.println ("model:"+selected.getModelId ()+"移动至:("+newX+","+newY+")");
//                                    board.servercomInter.pubishMoveModel ( selected.getModelId (), newX, newY );
//                                } catch (RemoteException ex) {
//                                    ex.printStackTrace ();
//                                }
                            }
                        }
                    }
                    x = e.getX ();
                    y = e.getY ();
                }
            }
        };
        this.releaseListener = new MouseListener () {
            @Override
            public void mouseReleased(MouseEvent e) {
                if (isDistor) {
                    try {
                        System.out.println ("broadcast drawDistortion model:"+tmpId+" 移动至:("+tmpMoving.x+","+tmpMoving.y+")");
                        board.servercomInter.pubishDistortion ( tmpId, tmpPivot, tmpMoving );
                    } catch (RemoteException ex) {
                        ex.printStackTrace ();
                    }
                }
                if (isMove){
                    try {
                        System.out.println ("model:"+tmpId+"移动至:("+tmpNewX+","+tmpNewY+")");
                        board.servercomInter.pubishMoveModel ( tmpId, tmpNewX, tmpNewY );
                    } catch (RemoteException ex) {
                        ex.printStackTrace ();
                    }
                }
                isMove = false;
                isDistor = false;
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
        drag ();
//			setPreferredSize(new Dimension(800, 400));
        setBackground ( Color.WHITE );
        order = new ConcurrentLinkedQueue <> (  );
        shapes = new ConcurrentHashMap <> ();
        selected = null;
        movingKnob = null;
        setVisible ( true );

    }


    public void canvasClicked() {
        addMouseListener ( new MouseAdapter () {
            public void mousePressed(MouseEvent e) {
                if (board.getMode () != 2) {
                    Point pt = e.getPoint ();
                    x = e.getX ();
                    y = e.getY ();
                    movingKnob = null;
                    pivotKnob = null;

                    if (selected != null) {
//                        if (!selected.getColor ().equals ( Color.WHITE ))
                        getSelection ( pt );
                        if (movingKnob == null && !selected.containsPoint ( pt )) {
                            selected = null;
                        }
                    }

//                    if (movingKnob == null) {
//                        if (selected)
//                        selected = null;
//                        for (Map.Entry <String, DShape> entry : shapes.entrySet ()) {
////							System.out.println(entry.getKey()+"："+entry.getValue());
//                            if (entry.getValue ().containsPoint ( pt ) && !entry.getValue ().getColor ().equals ( Color.WHITE )) {
//                                selected = entry.getValue ();
//                            }
//                        }
//	                    for(int i = 0; i < shapes.size(); i++){
//	                    	if(shapes.get(i).containsPoint(pt)&&!shapes.get(i).getColor().equals(Color.WHITE))
//	                            selected = shapes.get(i);
//	                    }

//                    }
                    repaint ();
                }

            }

        } );

    }

    public void drag() {
        if (this.dragFlag == 0) {
            this.addMouseMotionListener ( this.dragListener );
            this.addMouseListener ( this.releaseListener );
            this.dragFlag=1;
        }

    }

    public void removeDrag(){
        if (this.dragFlag == 1) {
            this.removeMouseMotionListener ( this.dragListener );
            this.removeMouseListener ( this.releaseListener );
            this.dragFlag = 0;
        }
    }

    public void drawMove(String modelId, int x, int y) {
        new Thread (  ) {
            public void run() {
//                if (!order.contains ( modelId )) {
                    System.out.println ( "model:" + modelId + "移动至:(" + x + "," + y + ")" );
                    DShape dShape = shapes.get ( modelId );
                    dShape.moveTo ( x, y );
                    board.updateTable ( dShape );
                    repaint ();
//                }
            }
        }.start ();
    }

    public void drawDistortion(String modelId, Point pivotKnob, Point movingKnob) {
        new Thread (  ) {
            public void run() {
//                if (!order.contains ( modelId )){
                    System.out.println ( "drawDistortion model:" + modelId + "从(" + pivotKnob.x + "," + pivotKnob.y + ") " + "移动至:(" + movingKnob.x + "," + movingKnob.y + ")" );
                    DShape dShape = shapes.get ( modelId );
                    dShape.resize ( pivotKnob, movingKnob );
                    repaint ();
//                }
            }
        }.start ();
    }

    public void getSelection(Point pt) {

        knobs = selected.getKnobs ();
        for (int i = 0; i < knobs.size (); i++) {
            Rectangle knob = new Rectangle ( knobs.get ( i ).x - 4, knobs.get ( i ).y - 4, 9, 9 );
            if (knob.contains ( pt )) {
                int j = 0;
                movingKnob = new Point ( knobs.get ( i ) );
                System.out.println ("knobs:"+knobs.toString ());
                if (knobs.size () == 2) {
                    if (i == 0)
                        j = 1;
                    else if (i == 1)
                        j = 0;
                } else {
                    if (i == 0) j = 3;
                    else if (i == 1) j = 2;
                    else if (i == 2) j = 1;
                    else if (i == 3) j = 0;
                }
                pivotKnob = new Point ( knobs.get ( (j) ) );
                break;
            }
        }
    }


    public void recolorShape(Color color) {
        if (selected != null) {
            selected.setColor ( color );
            repaint ();

        }
    }

    protected void paintComponent(Graphics g) {
        super.paintComponent ( g );
        String index;
        Iterator iter = order.iterator();

        while (iter.hasNext()) {
            index = (String) iter.next();
            System.out.println("paintComponent");

            DShape shape = shapes.get ( index );
//            System.out.println (selected);
//            System.out.println (shape);
            System.out.println(shape.getColor());
            if(nightmode)
            {
                if(shape.getColor().equals(Color.BLACK)){
                    System.out.println("daole");
                    shape.setColor(Color.gray);
                    shape.draw ( g, (selected == shape) );
                    shape.setColor(Color.BLACK);
                }
                else if(shape.getColor().equals(Color.WHITE)){
                    shape.setColor(lessblack);
                    shape.draw ( g, (selected == shape) );
                    shape.setColor(Color.WHITE);
                }
                else{
                    shape.draw ( g, (selected == shape) );
                }
            }
            else {
                if(shape.getColor().equals(Color.gray))
                {
                    shape.setColor(Color.BLACK);
                    shape.draw ( g, (selected == shape) );
                    shape.setColor(Color.gray);
                }
                else if(shape.getColor().equals(lessblack))
                {
                    shape.setColor(Color.WHITE);
                    shape.draw ( g, (selected == shape) );
                    shape.setColor(lessblack);
                }
                else {
                    shape.draw ( g, (selected == shape) );
                }
            }

        }
//        for (Map.Entry <String, DShape> entry : shapes.entrySet ()) {
//            System.out.println(entry.getKey()+"："+entry.getValue());
//            DShape shape = entry.getValue ();
//            shape.draw ( g, (selected == shape) );
//        }

//	        for(int i = 0; i < shapes.size(); i++){
//	        	DShape shape = shapes.get(i);
//	        	shape.draw(g, (selected == shape));
//	        }

    }

    public String addShape(DShapeModel model, String id) {
        if (id == null) {
            System.out.println("id is null");
            id = Integer.toString ( board.userId ) + Integer.toString ( count++ );
            if (board.getMode () != 2) {
                DShape shape = null;
                if (model instanceof DOvalModel)
                    shape = new DOval ( model, id );
                else if (model instanceof DTextModel)
                    shape = new DText ( model, id );
                else if (model instanceof DRectModel)
                    shape = new DRect ( model, id );
                else if (model instanceof DLineModel)
                    shape = new DLine ( model, id );

                shapes.put ( id, shape );
                order.add ( id );
                selected = shape;
                board.add ( shape );

                repaint ();
            }
            return id;
        }
        if (!order.contains ( id )){
            System.out.println("id not contained in order");
            if (board.getMode () != 2) {
                System.out.println("id not contained in order 2");
                DShape shape = null;
                if (model instanceof DOvalModel)
                    shape = new DOval ( model, id );
                else if (model instanceof DTextModel)
                    shape = new DText ( model, id );
                else if (model instanceof DRectModel)
                    shape = new DRect ( model, id );
                else if (model instanceof DLineModel)
                    shape = new DLine ( model, id );

                shapes.put ( id, shape );
                order.add ( id );
                board.add ( shape );
                repaint (  );
            }
        }
        System.out.println("id will return null");
        return null;
    }

    public void removeShape() {

        if (selected ()) {
            shapes.remove ( selected );
            board.delete ( selected );
            repaint ();
        }

    }


    public DShape getSelected() {
        return selected;
    }

    public ConcurrentHashMap <String, DShape> getShapes() {
        return shapes;
    }

    public ConcurrentLinkedQueue <String> getOrder() {
        return order;
    }

    public void setText(String text) {
        if (selected ()) {
            ((DText) selected).setText ( text );
            repaint ();
        }

    }

    public void setFont(String fontName) {
        if (selected ()) {
            ((DText) selected).setFont ( fontName );
            repaint ();
        }
    }

    public void setNull() {
        order.clear ();
        shapes.clear ();
        board.clear ();
        repaint ();
    }


    public boolean selected() {
        if (selected != null) return true;
        else return false;
    }

}



