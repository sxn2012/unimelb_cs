package whiteboard;

import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Shape;


public class DText extends DShape{

	private double initialSize = 1.0;
	private Font computedFont;  
	
	public DText()
	{
		super();
		computedFont = null;
	}
	
	public DText(DShapeModel model, String modelId)
	{
		super(model, modelId);
		computedFont = null;
	}
	
	public String getText() { 
        return getModel().getText(); 
    } 
	
	public void setText(String s) { 
        getModel().setText(s); 
    } 

	public String getFont(){
		return getModel().getFont();
	}
	
	public void setFont(String s){
		getModel().setFont(s);
	}
	
	@Override
	public void draw(Graphics g, boolean selected) {
		DTextModel model = (DTextModel) getModel();
		Shape clip = g.getClip(); 
        g.setClip(clip.getBounds().createIntersection(getBounds())); 
        Font font = computeFont(g); 
        g.setColor(getColor()); 
        g.setFont(font); 
        g.drawString(model.getText(), getBounds().x, getBounds().y + getBounds().height - 20); 
        g.setClip(clip); 
        if(selected) drawKnobs(g);
		
	}

	private Font computeFont(Graphics g) {
		
		double size = initialSize;
		
			while (true) {
				computedFont = new Font(getFont(), Font.PLAIN, (int) size); 
	            if (computedFont.getLineMetrics(getText(), ((Graphics2D) g).getFontRenderContext()).getHeight() > getModel().getBounds().getHeight()) {
	            	break;
	            }
	           
	            size = (size * 1.1) + 1;
	        }
			computedFont = new Font(getFont(), Font.PLAIN, (int) size);

		return computedFont;
           
	}

	
	
	public DTextModel getModel() { 
        return (DTextModel) model; 
    }

	@Override
	public void modelChanged(DShapeModel model) {
		// TODO Auto-generated method stub
		
	}
	
}
