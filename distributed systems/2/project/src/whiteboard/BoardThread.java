package whiteboard;

import java.awt.Dimension;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.beans.XMLDecoder;
import java.beans.XMLEncoder;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentLinkedQueue;

import javax.imageio.ImageIO;
import javax.swing.JOptionPane;



public class BoardThread extends Thread
{
	private Canvas canvas;
	private int action;
	private File file; 
	BoardThread()
	{
		
	}
	public void run()
	{
		if(action==1) save(file);
		else if(action==2) open(file);
		else if(action==3) saveImage(file);
	}
	public void init(int a,File f,Canvas c)
	{
		action=a;
		file=f;
		canvas=c;
	}
	
	private static String byte2Hex(byte[] bytes)
	{
		 StringBuffer stringBuffer = new StringBuffer();
		 String temp = null;
		 for (int i=0;i<bytes.length;i++){
		  temp = Integer.toHexString(bytes[i] & 0xFF);
		  if (temp.length()==1){
		  //1得到一位的进行补0操作
		  stringBuffer.append("0");
		  }
		  stringBuffer.append(temp);
		 }
		 return stringBuffer.toString();
	}
	
	public synchronized void save(File file)
	{
		try 
		{
			XMLEncoder xmlout =new XMLEncoder(new BufferedOutputStream(new FileOutputStream(file)));

			ConcurrentHashMap <String, DShape> shapes = canvas.getShapes();
			ConcurrentLinkedQueue <String> order = canvas.getOrder ();
	    	DShapeModel[] models = new DShapeModel[shapes.size()];

	    	int i=0;
			Iterator iter = order.iterator();
			while (iter.hasNext()) {
				String index = (String) iter.next();
//				System.out.println(index+"："+entry.getValue());
				models[i++] = shapes.get ( index ).getModel ();
			}
//			for(Map.Entry <String, DShape> entry : shapes.entrySet()) {
//				System.out.println(entry.getKey()+"："+entry.getValue());
//				models[i++] = entry.getValue ().getModel ();
//			}
//	    	for(int i = 0; i < models.length; i ++){
//	    		models[i] = shapes.get(i).getModel();
//
//	    	}
	    	
			xmlout.writeObject(models);
			xmlout.close();
			
			byte[] bytesArray = new byte[(int) file.length()];
			FileInputStream fis = new FileInputStream(file);
			fis.read(bytesArray); //read file into bytes[]
			fis.close();
			String filestr=byte2Hex(bytesArray);
			
			MessageDigest md = MessageDigest.getInstance("SHA-256");  
			md.update(filestr.getBytes("UTF-8"));
	        
			FileOutputStream fo=new FileOutputStream(file.getPath()+".hash");
			BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(fo));
			bw.write(byte2Hex(md.digest(bytesArray)));
			bw.close();
	        fo.close();
			JOptionPane.showMessageDialog(null, "Successfully Save!", "Message", JOptionPane.INFORMATION_MESSAGE);
			
		} 
		catch (Exception e) 
		{
			JOptionPane.showMessageDialog(null, e.toString(), "Error", JOptionPane.INFORMATION_MESSAGE);
		}
	}
	
	public synchronized void saveImage(File file)
	{
		Dimension imagesize = canvas.getSize();
		BufferedImage image = new BufferedImage(imagesize.width,imagesize.height,BufferedImage.TYPE_INT_RGB);	
		Graphics2D graphics = image.createGraphics();
	    canvas.paint(graphics);
	    graphics.dispose();
	    File f=file;
		try
	    {	if( !f.exists() )
				f.createNewFile();
			
			ImageIO.write(image, "jpg",f);
			JOptionPane.showMessageDialog(null, "Successfully Save Image!", "Message", JOptionPane.INFORMATION_MESSAGE);
	    }
		catch(Exception e)
		{
			JOptionPane.showMessageDialog(null, e.toString(), "Error", JOptionPane.INFORMATION_MESSAGE);
		}
	}
	
	public synchronized void open(File file)
	{
		try 
		{
			
			byte[] bytesArray = new byte[(int) file.length()];
			FileInputStream fis = new FileInputStream(file);
			fis.read(bytesArray); //read file into bytes[]
			fis.close();
			String filestr=byte2Hex(bytesArray);
			MessageDigest md = MessageDigest.getInstance("SHA-256");  
			md.update(filestr.getBytes("UTF-8"));
			String hashcode=byte2Hex(md.digest(bytesArray));
			FileInputStream fin=new FileInputStream(file.getPath()+".hash");
			BufferedReader br = new BufferedReader(new InputStreamReader(fin));
			String str=br.readLine();
			br.close();
			System.out.println(str);
			System.out.println(hashcode);
			if(!str.equals(hashcode))
				throw new Exception("the file has been destroyed!");
			
			XMLDecoder xmlin = new XMLDecoder(new FileInputStream(file));
			DShapeModel[] models = (DShapeModel[]) xmlin.readObject();
			xmlin.close();
			
			
			//clear();
			
			for(int i = 0; i < models.length; i++)
			{
				String id=canvas.addShape(models[i],null);
				canvas.board.servercomInter.pubishAddModel(id, models[i], models[i].getColor());
			}
			JOptionPane.showMessageDialog(null, "Successfully Open!", "Message", JOptionPane.INFORMATION_MESSAGE);	
			
		} 
		catch (Exception e) 
		{
			JOptionPane.showMessageDialog(null, e.toString(), "Error", JOptionPane.INFORMATION_MESSAGE);
			
		}    	
	}
	
}
