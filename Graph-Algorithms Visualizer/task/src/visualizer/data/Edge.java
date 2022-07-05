package visualizer.data;

import visualizer.MainFrame;

import javax.swing.*;
import java.awt.*;
import java.awt.geom.Line2D;

public class Edge extends JComponent {
    public final Vertex start;
    public final Vertex end;
    public final int weight;

    public Edge(Vertex start, Vertex end, int weight) {
        this.start = start;
        this.end = end;
        this.weight = weight;
        setOpaque(false);
        setName("Edge <" + start.getIndex() + " -> " + end.getIndex() +">");
        setLayout(null);
        setBounds(0, 0, MainFrame.WINDOW_WIDTH, MainFrame.WINDOW_HEIGHT);

        //add(createLabel());
    }

    public JLabel label() {
        JLabel label = new JLabel(String.valueOf(weight));
        Font font = new Font("Courier", Font.BOLD, 25);
        label.setName("EdgeLabel <" + start.getIndex() + " -> " + end.getIndex() +">");
        label.setHorizontalAlignment(SwingConstants.CENTER);
        label.setVerticalAlignment(SwingConstants.CENTER);
        label.setFont(font);
        label.setForeground(Color.DARK_GRAY);
        int centerX = Math.abs(start.getXCentered() + end.getXCentered()) / 2;
        int centerY = Math.abs(start.getYCentered() + end.getYCentered()) / 2;
        int labelWidth = label.getFontMetrics(font).stringWidth(label.getText()) + 8;
        int labelHeight = label.getFontMetrics(font).getHeight();
        label.setBounds(centerX - labelWidth / 2, centerY - labelHeight / 2, labelWidth, labelHeight);
        label.setBackground(UIManager.getColor ( "Panel.background" ));
        label.setBorder(BorderFactory.createLineBorder(Color.DARK_GRAY, 1));
        label.setOpaque(true);
        return label;
    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2.setStroke(new BasicStroke(3));
        g2.setColor(Color.DARK_GRAY);
        Line2D line = new Line2D.Float(
                start.getXCentered(),
                start.getYCentered(),
                end.getXCentered(),
                end.getYCentered());
        g2.draw(line);
    }

    public Edge reverse() {
       return new Edge(end, start, weight);
    }

    @Override
    public String toString() {
        return "Edge{" +
                "start vertex = "  + start +
                "\nend vertex = " + end +
                "\nstart=" +  start.getXCentered() + ", " +  start.getYCentered() +
                "\n, end=" + end.getXCentered() + ", " + end.getYCentered() +
                "\n, width=" + weight +
                '}';
    }
}
