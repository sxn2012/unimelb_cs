package whiteboard;

import java.awt.Color;
import java.awt.Point;
import java.awt.Rectangle;
import java.io.Serializable;

public class DShapeModel implements Serializable {

    private int x;
    private int y;
    private int width;
    private int height;
    //protected Point p1, p2;
    protected Color color;
    protected Rectangle bounds;
    private boolean hollow = false;
    public Boolean diagonal = true;
    public int stroke = 8;

    public DShapeModel(int x, int y, int width, int height, Color color) {
        this.bounds = new Rectangle ( x, y, width, height );
        this.color = color;

    }

    public DShapeModel(int x, int y) {
        this ( x, y, 0, 0, Color.WHITE );
    }

    public DShapeModel() {
        this.bounds = new Rectangle ( 0, 0, 0, 0 );
        if (Whiteboard.pencilcolor != null)
            this.color = Whiteboard.pencilcolor;
        else
            this.color = Color.WHITE;
    }

    public boolean getHollow() {
        return hollow;
    }

    public void setHollow(boolean h) {
        hollow = h;
    }

    public Rectangle getBounds() {
        return bounds;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public Color getColor() {
        if (color == null) {
            return Color.WHITE;
        }
        return color;

    }

    public void setX(int x) {
        bounds.x = x;

    }

    public void setY(int y) {
        bounds.y = y;

    }

    public void setWidth(int width) {
        bounds.width = width;

    }

    public void setHeight(int height) {
        bounds.height = height;

    }

    public void setColor(Color c) {
        this.color = c;

    }

    public void setBounds(int x, int y, int width, int height) {
        this.bounds = new Rectangle ( x, y, width, height );

    }

    public void setBounds(Rectangle bounds) {

        this.bounds = bounds;

    }

    public void setLocation(int x, int y) {
        bounds.x = x;
        bounds.y = y;
    }

	public Boolean getDiagonal() {
		return diagonal;
	}

	public void setDiagonal(Boolean diagonal) {
		this.diagonal = diagonal;
	}

	public void moveBy(int dx, int dy) {
//		System.out.println ("移动x:"+dx+" 移动y:"+dy);
        bounds.x += dx;
        bounds.y += dy;
    }

    public void moveTo(int x, int y) {
//		System.out.println ("移动到x:"+x+" 移动到y:"+y);
        bounds.x = x;
        bounds.y = y;
    }

    public void setPoints(Point p1, Point p2) {
        int x, y;

        if (p1.x < p2.x) {
            x = p1.x;
        } else {
            x = p2.x;
        }
        if (p1.y < p2.y) {
            y = p1.y;
        } else {
            y = p2.y;
        }

        int width = Math.abs ( p1.x - p2.x );
        int height = Math.abs ( p1.y - p2.y );

        setBounds ( new Rectangle ( x, y, width, height ) );
    }

    public void resize(Point pivotKnob, Point movingKnob) {

			if (pivotKnob.x < movingKnob.x) {
				x = pivotKnob.x;
			} else
				x = movingKnob.x;
			if (pivotKnob.y < movingKnob.y) {
				y = pivotKnob.y;
			} else
				y = movingKnob.y;
			if (this.bounds.getHeight () != this.bounds.getWidth ()) {
				int width = Math.abs ( pivotKnob.x - movingKnob.x );
				int height = Math.abs ( pivotKnob.y - movingKnob.y );
				if (width == height)
					width = height + 1;
				setBounds ( x, y, width, height );
			} else {
				int width = Math.abs ( pivotKnob.x - movingKnob.x );
				setBounds ( x, y, width, width );
			}



    }

	public void resizeLine(Point pivotKnob, Point movingKnob) {

		if (pivotKnob.x < movingKnob.x) {
			x = pivotKnob.x;
			this.diagonal = true;
		} else {
			x = movingKnob.x;
			this.diagonal = false;
		}

		if (pivotKnob.y < movingKnob.y) {
			y = pivotKnob.y;

		} else{
			y = movingKnob.y;
			this.diagonal = !this.diagonal;
		}

		if (this.bounds.getHeight () != this.bounds.getWidth ()) {
			int width = Math.abs ( pivotKnob.x - movingKnob.x );
			int height = Math.abs ( pivotKnob.y - movingKnob.y );
			if (width == height)
				width = height + 1;
			setBounds ( x, y, width, height );
		}



	}

    public String toString() {
        return "Shape is " + width + " by " + height + " and located at " + x + " " + y;
    }


}
