package whiteboard;

import java.awt.Point;
import java.awt.Rectangle; 
 



public class DLineModel extends DShapeModel { 
   

	
	
	public DLineModel() { 
        super(); 
        
        this.bounds = new Rectangle(0,0,0,0);
    } 
    
    public DLineModel(Point p1, Point p2){
    	super();

    	this.bounds = new Rectangle(p1.x, p1.y, p2.x - p1.x, p2.y - p1.y);

    }

    public DLineModel(Point p1, Point p2, int stroke){
        super();

        this.bounds = new Rectangle(p1.x, p1.y, p2.x - p1.x, p2.y - p1.y);
        this.stroke = stroke;
    }
     
  
    public void moveBy(int dx, int dy) { 
    	
        
         
        super.moveBy(dx, dy); 
        this.bounds = new Rectangle(bounds.x+dx, bounds.y+dy, bounds.width, bounds.height);
    } 
    
    public void setBounds(Rectangle bounds){
		super.setBounds(bounds);
	}
    
    public Rectangle getBounds() { 
        return bounds.getBounds(); 
    } 
     
    
    public void setPoints(Point p1, Point p2) { 
        p1 = new Point(p1); 
        p2 = new Point(p2);
        super.setPoints(p1, p2);
       
    } 
     
    
     
     
    public void resize(Point pivotKnob, Point movingKnob) { 

        this.bounds=new Rectangle(pivotKnob.x, pivotKnob.y, movingKnob.x - pivotKnob.x, movingKnob.y - pivotKnob.y);
        super.resizeLine (pivotKnob, movingKnob);
        //this.bounds = super.getBounds();
        
    } 
     
}