package whiteboard;

import javax.swing.*;
import javax.swing.colorchooser.AbstractColorChooserPanel;
import java.awt.*;
import java.awt.event.ActionEvent;

public class colorchooser extends AbstractColorChooserPanel {
    public void buildChooser() {
        setLayout(new GridLayout(4, 4));
        makeAddButton("Red", Color.red);
        makeAddButton("Green", Color.green);
        makeAddButton("Blue", Color.blue);
        makeAddButton("yellow", Color.yellow);
        makeAddButton("cyan", Color.cyan);
        makeAddButton("pink", Color.pink);
        makeAddButton("orange", Color.orange);
        makeAddButton("cyan",Color.cyan);
        makeAddButton("magenta",Color.magenta);
        makeAddButton("black", Color.black);
        makeAddButton("a", new Color(100,100,100));
        makeAddButton("b", new Color(100,150,200));
        makeAddButton("c", new Color(100,200,200));
        makeAddButton("d", new Color(150,200,200));
        makeAddButton("e", new Color(150,100,200));
        makeAddButton("f", new Color(200,100,100));
        makeAddButton("g", new Color(200,150,100));
        makeAddButton("h", new Color(200,150,150));
        makeAddButton("i", new Color(200,100,150));
        makeAddButton("g", new Color(150,100,100));
    }

    public void updateChooser() {
    }

    public String getDisplayName() {
        return "boardcolor";
    }

    public Icon getSmallDisplayIcon() {
        return null;
    }
    public Icon getLargeDisplayIcon() {
        return null;
    }
    private void makeAddButton(String name, Color color) {
        JButton button = new JButton(name);
        button.setBackground(color);
        button.setAction(setColorAction);
        button.setOpaque(true);
        button.setBorderPainted(false);
        add(button);
    }

    Action setColorAction = new AbstractAction() {
        public void actionPerformed(ActionEvent evt) {
            JButton button = (JButton) evt.getSource();

            getColorSelectionModel().setSelectedColor(button.getBackground());
        }
    };
}

