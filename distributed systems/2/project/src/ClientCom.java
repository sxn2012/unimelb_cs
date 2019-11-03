import rmi.ClientcomInter;
import whiteboard.DShape;
import whiteboard.DShapeModel;
import whiteboard.Whiteboard;

import java.awt.*;
import java.rmi.*;
import java.rmi.server.UnicastRemoteObject;

public class ClientCom extends UnicastRemoteObject implements ClientcomInter {

    private String name;
    private Startclient ui;
    private Whiteboard whiteboard;
    public ClientCom(String n) throws RemoteException {
        name=n;
    }

    public void tell(String st) throws RemoteException{
        System.out.println(st);
        ui.writeMsg(st);
    }
    public String getName() throws RemoteException{
        return name;
    }

    @Override
    public void addModel(String modelId, DShapeModel model, Color color) throws RemoteException {
        System.out.println ("执行addModel");
        whiteboard.drawModel ( model,color, modelId );
    }

    @Override
    public void moveModel(String modelId, int dx, int dy) throws RemoteException {
        System.out.println ("执行moveModel");
        whiteboard.canvas.drawMove ( modelId,dx,dy );
    }

    @Override
    public void addDraw(DShapeModel model, Color pencilcolor, String id) throws RemoteException {
        System.out.println ("执行addDraw");
        whiteboard.drawPenEraser ( model, pencilcolor, id);
    }

    @Override
    public void drawDistortion(String modelId, Point pivotKnob, Point movingKnob) throws RemoteException {
        System.out.println ("执行drawDistortion");
        whiteboard.canvas.drawDistortion ( modelId, pivotKnob, movingKnob);
    }

    @Override
    public void drawText(String modelId, DShapeModel model, Color color, String font, String text) throws RemoteException {
        whiteboard.drawText ( modelId, model, color, font, text);
    }

    
    
    
    @Override
    public void removeallModel() throws RemoteException
    {
    	whiteboard.canvas.setNull();
    }
    

    public void setGUI(Startclient t){
        ui=t ;
    }

    public void setWhiteboard(Whiteboard whiteboard) {
        this.whiteboard = whiteboard;
    }


}