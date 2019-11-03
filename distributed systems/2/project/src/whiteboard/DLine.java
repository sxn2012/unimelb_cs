package whiteboard;

import java.awt.*;
import java.util.*; 
 
import static java.awt.BasicStroke.JOIN_ROUND;

public class DLine extends DShape {

    public int stroke = 8;
 
	public DLine()
	{
		super();
	}
   
    public DLine(DShapeModel model, String modelId)
    {
        super(model, modelId);
        this.stroke = model.stroke;
    }

//    public DLine(DLineModel model, String modelId, int stroke)
//    {
//        super(model, modelId);
//        this.stroke = model.stroke;
//    }
 

  
    public void draw(Graphics g, boolean selected) {
        DLineModel model= getModel();
        Rectangle bounds = model.getBounds();
        Boolean isDiagonal = model.getDiagonal();
        g.setColor(getColor());
//        System.out.println ("draw diagonal"+isDiagonal);
        if (isDiagonal) {
            ((Graphics2D) g).setStroke(new BasicStroke(stroke, JOIN_ROUND, JOIN_ROUND));
            //g.drawLine(model.getPoint1().x, model.getPoint1().y, model.getPoint2().x, model.getPoint2().y);
            g.drawLine(bounds.x, bounds.y, bounds.x+bounds.width, bounds.y+bounds.height);
            if(selected) {
                drawKnobs(g);
            }
        } else {
            ((Graphics2D) g).setStroke(new BasicStroke(stroke, JOIN_ROUND, JOIN_ROUND));
            //g.drawLine(model.getPoint1().x, model.getPoint1().y, model.getPoint2().x, model.getPoint2().y);
            g.drawLine(bounds.x, bounds.y+bounds.height, bounds.x+bounds.width, bounds.y);
            if(selected) {
                drawKnobs(g);
            }
        }

    } 
 
  
    public DLineModel getModel() { 
        return (DLineModel) model; 
    } 
     
    public ArrayList<Point> getKnobs() { 
       
            knobs = new ArrayList<Point>(); 
            DLineModel line = (DLineModel) model; 
            //knobs.add(new Point(line.getPoint1())); 
            knobs.add(new Point(model.getBounds().x,model.getBounds().y));
            //knobs.add(new Point(line.getPoint2())); 
            knobs.add(new Point(model.getBounds().x+model.getBounds().width, model.getBounds().y+model.getBounds().height));
            needKnobs = false; 
        
        return knobs; 
    }
    
    



	@Override
	public void modelChanged(DShapeModel model) {
		// TODO Auto-generated method stub
		
	} 
 
}
