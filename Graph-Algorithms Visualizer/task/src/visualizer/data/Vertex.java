package visualizer.data;

import javax.swing.*;
import java.awt.*;

public class Vertex extends JPanel {
    private int x;
    private int y;
    private final String index;
    private final int diameter;

    public Vertex(int x, int y, int radius, String index) {
        this.x = x;
        this.y = y;
        this.diameter = radius * 2;
        this.index = index;
        this.setName("Vertex " + index);
        init();
    }

    private void init() {
        setBounds(x, y, diameter, diameter);
        setLayout(new BorderLayout());
        JLabel label = new JLabel(String.valueOf(index));
        Font font = new Font("Courier", Font.BOLD, diameter / 2);
        label.setName("VertexLabel " + index);
        label.setHorizontalAlignment(SwingConstants.CENTER);
        label.setVerticalAlignment(SwingConstants.CENTER);
        label.setFont(font);
        label.setForeground(Color.DARK_GRAY);
        add(label);
    }

    @Override //Paints before ui-elements
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.setColor(Color.LIGHT_GRAY);
        g.fillOval(0,  0, diameter, diameter);
    }

    @Override
    public String toString() {
        return "Vertex{" +
                "№=" + index +
                ", x=" + x +
                ", y=" + y +
                '}';
    }
}