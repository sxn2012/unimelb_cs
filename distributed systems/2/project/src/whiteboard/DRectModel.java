package whiteboard;

import java.awt.Color;

public class DRectModel extends DShapeModel{

	public DRectModel(int x, int y, int width, int height, Color color){
		super(x, y, width, height, color);
	} 
	public DRectModel(){
		super(0, 0, 0, 0, Color.WHITE);
	} 
}
