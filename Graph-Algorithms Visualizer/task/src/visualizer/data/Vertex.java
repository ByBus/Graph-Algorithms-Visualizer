package visualizer.data;

import javax.swing.*;
import java.awt.*;
import java.util.Objects;

public class Vertex extends JPanel{
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
        setOpaque(false);
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

    public int getXCentered() {
        return x + diameter / 2;
    }

    public int getYCentered() {
        return y + diameter / 2;
    }

    public String getIndex() {
        return index;
    }

    @Override //Paints before ui-elements
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.setColor(Color.LIGHT_GRAY);
        g.fillOval(1,  1, diameter - 3, diameter - 3);
        Graphics2D g2 = (Graphics2D) g;
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2.setColor(Color.DARK_GRAY);
        g2.setStroke(new BasicStroke(3));
        g2.drawOval(1, 1, diameter - 3, diameter - 3);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Vertex vertex = (Vertex) o;
        return Objects.equals(index, vertex.index);
    }

    @Override
    public int hashCode() {
        return Objects.hash(index);
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