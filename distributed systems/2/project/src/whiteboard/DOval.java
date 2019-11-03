package whiteboard;

import java.awt.BasicStroke;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;


public class DOval extends DShape{
	
	public DOval()
	{
		super();
	}
	 
	public DOval(DShapeModel model, String modelId)
	{
		super(model, modelId);
		
	}

	public void draw(Graphics g, boolean selected){
		
		Rectangle bounds = model.getBounds(); 
		g.setColor(model.getColor());
		
		 ((Graphics2D) g).setStroke(new BasicStroke(2));
		if(!hollow)
			g.fillOval(bounds.x, bounds.y, bounds.width, bounds.height);
		else
			g.drawOval(bounds.x, bounds.y, bounds.width, bounds.height);
		if(selected)
			drawKnobs(g);
	
	
	}

	@Override
	public void modelChanged(DShapeModel model) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public DShapeModel getModel() {
		return (DOvalModel) model; 
	}

}
