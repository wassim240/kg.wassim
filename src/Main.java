import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;

class CircleSectorFiller extends JPanel implements ActionListener {

    private int radius;
    private double startAngle;
    private double endAngle;
    private Timer timer;

    public CircleSectorFiller(int radius) {
        this.radius = radius;
        this.startAngle = Math.toRadians(45); // Initial start angle
        this.endAngle = Math.toRadians(135);  // Initial end angle

        // Create a timer to update the angles and repaint the panel
        int delay = 30; // Milliseconds
        timer = new Timer(delay, this);
        timer.start();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        int width = getWidth();
        int height = getHeight();

        BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g2d = image.createGraphics();

        g2d.setColor(Color.WHITE);
        g2d.fillRect(0, 0, width, height);

        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                int dx = x - width / 2;
                int dy = y - height / 2;
                double distanceFromCenter = Math.sqrt(dx * dx + dy * dy);
                double angle = Math.atan2(dy, dx);

                // Convert negative angles to positive
                if (angle < 0)
                    angle += 2 * Math.PI;

                // Check if the point is within the specified angle range
                if (distanceFromCenter <= radius && angle >= startAngle && angle <= endAngle) {
                    double ratio = distanceFromCenter / radius; // Interpolation ratio

                    int red = (int) (255 * ratio);
                    int blue = (int) (255 * (1 - ratio));
                    Color color = new Color(red, 0, blue);

                    g2d.setColor(color);
                    g2d.drawLine(x, y, x, y);
                }
            }
        }

        g2d.dispose();
        g.drawImage(image, 0, 0, this);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        // Update angles for animation
        startAngle += Math.toRadians(1);
        endAngle += Math.toRadians(1);

        // Repaint the panel
        repaint();
    }

    public static void main(String[] args) {
        int radius = 100;
        JFrame frame = new JFrame("Circle Sector Filler");
        CircleSectorFiller filler = new CircleSectorFiller(radius);
        frame.add(filler);
        frame.setSize(2 * radius, 2 * radius);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
    }
}
