package whiteboard;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.BasicStroke;


public class DRect extends DShape{
	
	public DRect()
	{
		super();
	}
	
	public DRect(DShapeModel model, String modelId)
	{
		super(model, modelId);

	}
	
	public DRectModel getModel() { 
        return (DRectModel) model; 
    } 

	public void draw(Graphics g, boolean selected){
		
		Rectangle bounds = model.getBounds(); 
		g.setColor(model.getColor());
		
		((Graphics2D) g).setStroke(new BasicStroke(2));
		if(!hollow)
			g.fillRect(bounds.x, bounds.y, bounds.width, bounds.height);
		else
			g.drawRect(bounds.x, bounds.y, bounds.width, bounds.height);
		if(selected) {
			drawKnobs(g); 
			
		}
		
		
		
	}

	@Override
	public void modelChanged(DShapeModel model) {
		// TODO Auto-generated method stub
		
	}
	
}
