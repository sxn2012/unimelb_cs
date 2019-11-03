import rmi.ClientcomInter;
import rmi.ServercomInter;
import whiteboard.DShape;
import whiteboard.DShapeModel;

import javax.swing.*;
import java.awt.*;
import java.rmi.*;
import java.rmi.server.UnicastRemoteObject;
import java.util.*;

public class Servercom extends UnicastRemoteObject implements ServercomInter {
    private Vector v=new Vector();
    private Vector uId = new Vector (  );
    public Servercom() throws RemoteException{}
    private Vector history=new Vector();
    private Vector modelhistory=new Vector();

    public char login(ClientcomInter a) throws RemoteException{
        System.out.println(a.getName() + "  got connected....");
        int allowed =JOptionPane.showConfirmDialog( Startclient.frame, a.getName()+" want to connect, do you allow?");
        if(0==allowed)
        {
        	publish(a.getName()+ " has just connected");
            a.tell("You have Connected successfully");
        }
        else
        {
            a.tell("You are not allowed to connect");
            return 0;
        }
        v.add(a);
        String s = "abcdefghijklmnopqrstuvwxyz";
        char[] c = s.toCharArray();
        Random random = new Random();
        char userid = c[random.nextInt(c.length)];
        while (uId.contains ( userid )) {
            userid = c[random.nextInt(c.length)];
        }
        uId.add ( userid );
        System.out.println ("userid:"+userid);
        return userid;
    }
    public boolean creatorlogin(ClientcomInter a) throws RemoteException{
        v.add(a);
        publish( "create board successfully");
        return true;
    }

    public boolean logout(ClientcomInter a) throws RemoteException{
        System.out.println(a.getName() + "  disconnected....");
        a.tell("You have disconnected.");
        v.remove(a);
        publish(a.getName()+ " has just left");
        //JOptionPane.showMessageDialog(null,a.getName()+ " has just left");
        return true;
    }




    public void publish(String s) throws RemoteException{
        System.out.println(s);
        history.add(s);
        for(int i=0;i<v.size();i++){
            try{
                ClientcomInter tmp=(ClientcomInter)v.get(i);
                tmp.tell(s);
            }catch(Exception e){
                //problem with the client not connected.
                //Better to remove it
            }
        }
    }

    public void resumehistory(ClientcomInter a) throws RemoteException
    {
        for(int i=0;i<history.size();i++){
            try{
                a.tell((String) history.get(i));
            }catch(Exception e){
                //problem with the client not connected.
                //Better to remove it
            }
        }

    }

    public void resumemodelhistory(ClientcomInter a) throws RemoteException
    {
        for(int i=0;i<modelhistory.size();i++){
            try{
                System.out.println(modelhistory.get(i).getClass());
                if(modelhistory.get(i).getClass()==Models.class)
                {
                    Models newmodel = ((Models) modelhistory.get(i));
                    a.addModel(newmodel.modelId,newmodel.model,newmodel.color);
                }
                else if(modelhistory.get(i).getClass()==MoveModels.class)
                {
                    MoveModels newmodel = ((MoveModels) modelhistory.get(i));
                    a.moveModel(newmodel.modelId,newmodel.dx,newmodel.dy);
                }
                else if(modelhistory.get(i).getClass()==Draw.class)
                {
                    Draw newmodel = ((Draw) modelhistory.get(i));
                    a.addDraw(newmodel.model,newmodel.pencilcolor,newmodel.modelId);
                }
                else if(modelhistory.get(i).getClass()==Distortion.class)
                {
                    Distortion newmodel = ((Distortion) modelhistory.get(i));
                    a.drawDistortion(newmodel.modelId,newmodel.pivotKnob,newmodel.movingKnob);
                }
                else if(modelhistory.get(i).getClass()==AddText.class)
                {
                    AddText newmodel = ((AddText) modelhistory.get(i));
                    a.drawText(newmodel.modelId,newmodel.model,newmodel.color,newmodel.font,newmodel.text);
                }


            }catch(Exception e){
                //problem with the client not connected.
                //Better to remove it
            }
        }

    }

    public class Models {
        public String modelId;
        public DShapeModel model;
        public Color color;

        public Models(String modelId, DShapeModel model, Color color) {
            this.modelId = modelId;
            this.model = model;
            this.color =color;
        }

        public String getModelId() { return this.modelId; }
        public DShapeModel getModel() { return this.model; }
        public Color getColor() { return this.color; }
    }

    public class MoveModels {
        public String modelId;
        public int dx;
        public int dy;

        public MoveModels(String modelId, int dx, int dy) {
            this.modelId = modelId;
            this.dx = dx;
            this.dy =dy;
        }
    }

    public class Draw {
        public DShapeModel model;
        public Color pencilcolor;
        public String modelId;

        public Draw(DShapeModel model, Color pencilcolor, String id) {
            this.model = model;
            this.pencilcolor = pencilcolor;
            this.modelId = id;
        }
    }

    public class Distortion {
        public String modelId;
        public Point pivotKnob;
        public Point movingKnob;

        public Distortion(String modelId, Point pivotKnob, Point movingKnob){
            this.modelId = modelId;
            this.pivotKnob = pivotKnob;
            this.movingKnob=movingKnob;
        }
    }

    public class AddText {
        public String modelId;
        public DShapeModel model;
        public Color color;
        public String font;
        public String text;

        public AddText(String modelId, DShapeModel model, Color color, String font, String text){
            this.modelId = modelId;
            this.model = model;
            this.color=color;
            this.font=font;
            this.text=text;
        }
    }


    public void pubishAddModel(String modelId, DShapeModel model, Color color) throws RemoteException {
        modelhistory.add(new Models(modelId,model,color));
        for(int i=0;i<v.size();i++){
            try{
                ClientcomInter tmp=(ClientcomInter)v.get(i);
                tmp.addModel (modelId, model,color);

            }catch(Exception e){
                //problem with the client not connected.
                //Better to remove it
            }
        }
    }

    @Override
    public void pubishMoveModel(String modelId, int dx, int dy) throws RemoteException {
        modelhistory.add(new MoveModels(modelId,dx, dy));
        for(int i=0;i<v.size();i++){
            try{
                ClientcomInter tmp=(ClientcomInter)v.get(i);
                tmp.moveModel (modelId,dx, dy);
            }catch(Exception e){
                //problem with the client not connected.
                //Better to remove it
            }
        }
    }

    public void pubishAddDraw(DShapeModel model, Color pencilcolor, String id) throws RemoteException {
        modelhistory.add(new Draw(model, pencilcolor, id));
        for(int i=0;i<v.size();i++){
            try{
                ClientcomInter tmp=(ClientcomInter)v.get(i);
                tmp.addDraw (model, pencilcolor, id);
            }catch(Exception e){
                //problem with the client not connected.
                //Better to remove it
            }
        }
    }

    public void pubishDistortion(String modelId, Point pivotKnob, Point movingKnob) throws RemoteException {
        modelhistory.add(new Distortion(modelId, pivotKnob, movingKnob));
        for(int i=0;i<v.size();i++){
            try{
                ClientcomInter tmp=(ClientcomInter)v.get(i);
                tmp.drawDistortion (modelId, pivotKnob, movingKnob);
            }catch(Exception e){
                //problem with the client not connected.
                //Better to remove it
            }
        }
    }

    @Override
    public void pubishAddText(String modelId, DShapeModel model, Color color, String font, String text) throws RemoteException {
        modelhistory.add(new AddText(modelId, model, color, font, text));
        for(int i=0;i<v.size();i++){
            try{
                ClientcomInter tmp=(ClientcomInter)v.get(i);
                tmp.drawText (modelId, model, color, font, text);
            }catch(Exception e){
                //problem with the client not connected.
                //Better to remove it
            }
        }
    }


    @Override
    public void removeallModel() throws RemoteException
    {
    	for(int i=0;i<v.size();i++){
            try{
                ClientcomInter tmp=(ClientcomInter)v.get(i);
                tmp.removeallModel();
                modelhistory.clear();
            }catch(Exception e){
                //problem with the client not connected.
                //Better to remove it
            }
        }
    }


    public Vector getConnected() throws RemoteException{
        return v;
    }


}