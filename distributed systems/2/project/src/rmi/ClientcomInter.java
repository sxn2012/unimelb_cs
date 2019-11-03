package rmi;

import whiteboard.DShape;
import whiteboard.DShapeModel;

import java.awt.*;
import java.rmi.*;

public interface ClientcomInter extends Remote{
    public void tell(String name)throws RemoteException ;
    public String getName()throws RemoteException ;
    public void addModel(String modelId, DShapeModel model, Color color)throws RemoteException;
    public void moveModel(String modelId, int x, int y) throws RemoteException;
    public void addDraw(DShapeModel model, Color pencilcolor, String id) throws RemoteException;
    public void drawDistortion(String modelId, Point pivotKnob, Point movingKnob) throws RemoteException;
    public void drawText(String modelId, DShapeModel model, Color color, String font, String text) throws RemoteException;
    public void removeallModel() throws RemoteException;
}