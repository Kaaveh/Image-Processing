//package drawHistogram;

import java.awt.BasicStroke;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JFrame;
import javax.swing.JPanel;

public class Histogram extends JPanel {
    private int SIZE = 256;
    // Red, Green, Blue   
    private int NUMBER_OF_COLOURS = 4;

    public final int RED = 0;
    public final int GREEN = 1;
    public final int BLUE = 2;
    public final int GRAY = 3;

    private int[][] colourBins;
    private volatile boolean loaded = false;
    private int maxY;

    public Histogram() {
        colourBins = new int[NUMBER_OF_COLOURS][];

        for (int i = 0; i < NUMBER_OF_COLOURS; i++) {
            colourBins[i] = new int[SIZE];
        }

    }

    public void load(String path) throws IOException {
        BufferedImage bi = ImageIO.read(new File(path));
        int width = bi.getWidth();
        int height = bi.getHeight();


            // Reset all the bins
        for (int i = 0; i < NUMBER_OF_COLOURS; i++) {
           for (int j = 0; j < SIZE; j++) {
                colourBins[i][j] = 0;
           }
        }

        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                Color c = new Color(bi.getRGB(x, y));

                colourBins[RED][c.getRed()]++;
                colourBins[GREEN][c.getGreen()]++;
                colourBins[BLUE][c.getBlue()]++;
                colourBins[GRAY][(c.getGreen()+c.getBlue()+c.getRed())/3]++;
            }
        }

        maxY = 0;

        for (int i = 0; i < NUMBER_OF_COLOURS; i++) {
            for (int j = 0; j < SIZE; j++) {
                if (maxY < colourBins[i][j]) {
                    maxY = colourBins[i][j];
                }
            }
        }

        loaded = true;
    }

    @Override
    public void paint(Graphics g) {
        if (loaded) {
            Graphics2D g2 = (Graphics2D)g;

            g2.setColor(Color.white);
            g2.fillRect(0, 0, getWidth(), getHeight());

            g2.setStroke(new BasicStroke(2));

            int xInterval = (int) ((double)getWidth() / ((double)SIZE+1));

            g2.setColor(Color.black);

            g.drawLine(0, getHeight() - 20, 1000 , getHeight() - 20);
            g.drawLine(xInterval+25, 0 , xInterval+25, getHeight());

            for (int i = 0; i < NUMBER_OF_COLOURS; i++) {

                // Set the graph
                if (i == RED) {
                    g2.setColor(Color.red);
                } else if (i == GREEN) {
                    g2.setColor(Color.GREEN);
                } else if (i == BLUE) {
                    g2.setColor(Color.blue);
                } else if (i == GRAY) {
                    g2.setColor(Color.GRAY);
                }

                // draw the graph for the spesific colour.
                for (int j = 10; j < SIZE + 9 ; j++) {
                    int value = (int) (((double)colourBins[i][j-10] / (double)maxY) * getHeight()) +20;
                    int value2 = (int) (((double)colourBins[i][j-9] / (double)maxY) * getHeight()) +20;

                    g2.drawLine(j * xInterval, getHeight() - value, (j+1)*xInterval, getHeight() - value2);
                }
            }
        } else {
            super.paint(g);
        }
    }

    public static void main(String[] args) {
        JFrame frame = new JFrame("Histogram");
        frame.setSize(1000, 700);
        frame.setLayout(new BorderLayout());
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        Histogram his = new Histogram();

        try {
            his.load("/home/kaaveh/Projects/Java/IDEA/Image Processing/resources/a.jpg");
        } catch (IOException e) {
            e.printStackTrace();
        }

        frame.add(his,BorderLayout.CENTER);
        frame.setVisible(true);
    }
}
