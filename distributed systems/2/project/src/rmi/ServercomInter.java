package rmi;

import whiteboard.DShape;
import whiteboard.DShapeModel;

import java.awt.*;
import java.rmi.*;
import java.util.*;

public interface ServercomInter extends Remote{
    public char login(ClientcomInter a)throws RemoteException ;
    public void publish(String s)throws RemoteException ;
    public Vector getConnected() throws RemoteException ;
    public boolean logout(ClientcomInter a) throws RemoteException;
    public boolean creatorlogin(ClientcomInter a) throws RemoteException;
    public void resumehistory(ClientcomInter a) throws RemoteException;
    public void pubishAddModel(String modelId, DShapeModel model, Color color) throws RemoteException;
    public void pubishMoveModel(String modelId, int x, int y) throws RemoteException;
    public void pubishAddDraw(DShapeModel model, Color pencilcolor, String id) throws RemoteException;
    public void pubishDistortion(String modelId, Point pivotKnob, Point movingKnob) throws RemoteException;
    public void pubishAddText(String modelId, DShapeModel model, Color color, String font, String text) throws RemoteException;
    public void removeallModel() throws RemoteException;
    public void resumemodelhistory(ClientcomInter a) throws RemoteException;
}
