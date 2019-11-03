package whiteboard;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.Rectangle;
import java.io.Serializable;
import java.util.ArrayList;

public abstract class DShape implements ModelListener, Serializable {

    protected String modelId;
    protected DShapeModel model;
    protected Rectangle bounds;
    protected ArrayList <Point> knobs;
    protected boolean needKnobs;
    protected boolean hollow;


    public DShape() {

        this.model = null;
        knobs = null;
        needKnobs = false;

    }

    public DShape(DShapeModel model, String modelId) {
        this.modelId = modelId;
        this.model = model;
        knobs = null;
        needKnobs = false;
        this.hollow = model.getHollow ();
    }


    public DShapeModel bounds() {
        return model;

    }

    public Rectangle getBounds() {
        return model.getBounds ();
    }

    public void setColor(Color color) {
        model.setColor ( color );
    }

    public Color getColor() {
        return model.getColor ();
    }

    public void moveBy(int dx, int dy) {
        needKnobs = true;
        model.moveBy ( dx, dy );

    }

    public void moveTo(int x, int y) {
        needKnobs = true;
        model.moveTo ( x, y );

    }

    public void resize(Point pivotKnob, Point movingKnob) {
        needKnobs = true;
        model.resize ( pivotKnob, movingKnob );

    }


    public boolean containsPoint(Point p) {
        Rectangle b = getBounds ();

        if (b.contains ( p )) {
            return true;
        }

        return false;
    }

    public ArrayList <Point> getKnobs() {

        if (knobs == null || needKnobs) {
            knobs = new ArrayList <Point> ();
            Rectangle bounds = model.getBounds ();
            for (int i = 0; i < 2; i++) {
                for (int j = 0; j < 2; j++) {
                    knobs.add ( new Point ( bounds.x + bounds.width * i, bounds.y + bounds.height * j ) );
                }

            }
        }

        return knobs;
    }

    public void drawKnobs(Graphics g) {
        if(Canvas.nightmode){
            g.setColor ( Color.WHITE );
        }
        else {
            g.setColor(Color.BLACK);
        }
        for (int i = 0; i < getKnobs ().size (); i++) {
            Point p = getKnobs ().get ( i );
            g.fillRect ( p.x - 4, p.y - 4, 9, 9 );
        }
    }

    public void setModel(DShapeModel m) {
        this.model = m;
        this.bounds = m.getBounds ();
    }

    public void removeKnobs() {
        for (int i = 0; i < knobs.size (); i++) {
            knobs.remove ( i );
        }
    }

    public String toString() {
        return "I am located at" + model.getX () + " " + model.getY () + "and color" + model.getColor ().toString ();
    }

    abstract public void draw(Graphics g, boolean selected);

    abstract public DShapeModel getModel();

    public String getModelId() {
        return modelId;
    }

    public void setModelId(String modelId) {
        this.modelId = modelId;
    }
}
