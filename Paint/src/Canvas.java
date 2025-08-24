import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.List;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;

import javax.swing.BorderFactory;
import javax.swing.JPanel;

public class Canvas extends JPanel{

    private final static int STROKE_SIZE = 8;

    // holds all the paths created in the canvas
    private ArrayList<ArrayList<ColorPoint>> allPaths;

    // used to draw a line between points
    private ArrayList<ColorPoint> currentPath;

    // color of the dots
    private Color color;

    // location of the dots
    private int x, y;

    // canvas width & height
    private int canvasWidth, canvasHeight;

    public Canvas(int targetWidth, int targetHeight){
        super();
        setPreferredSize(new Dimension(targetWidth, targetHeight));
        setOpaque(true);
        setBackground(Color.WHITE);
        setBorder(BorderFactory.createLineBorder(Color.BLACK));

        // init width
        allPaths = new ArrayList<>(25);
        canvasWidth = targetWidth;
        canvasHeight = targetHeight;

        MouseAdapter ma = new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e){
                // get current mouse location
                x=e.getX();
                y=e.getY();

                // draw in current mouse location
                Graphics g = getGraphics();
                g.setColor(color);
                g.fillRect(x, y, STROKE_SIZE, STROKE_SIZE);
                g.dispose();

                // start current path
                currentPath = new ArrayList<>(25);
                currentPath.add(new ColorPoint(x, y, color));
            }

            @Override
            public void mouseReleased(MouseEvent e){
                // add current path to all paths
                allPaths.add(currentPath);

                // reset the current path
                currentPath = null;
            }

            @Override
            public void mouseDragged(MouseEvent e){
                // get current location
                x=e.getX();
                y=e.getY();

                Graphics2D g2d = (Graphics2D) getGraphics();
                g2d.setColor(color);

                if(!currentPath.isEmpty()){
                    ColorPoint prevPoint = currentPath.get(currentPath.size() - 1);
                    g2d.setStroke(new BasicStroke(STROKE_SIZE));
                    

                    // connect the current point with prev point to create a line
                    g2d.drawLine(prevPoint.getX(), prevPoint.getY(), x, y);
                }

                g2d.dispose();

                // add new point to the path
                ColorPoint nexPoint = new ColorPoint(x, y, color);
                currentPath.add(nexPoint);
            }
        };

        addMouseListener(ma);
        addMouseMotionListener(ma);
    }

    public void setColor(Color color){
        this.color = color;
    }

    public void resetCanvas(){
        // clear all rectangles
        Graphics g = getGraphics();
        g.clearRect(0, 0, canvasWidth, canvasHeight);
        g.dispose();

        // reset the path
        allPaths = new ArrayList<>(25);
        currentPath = null;

        repaint();
        revalidate();
    }

    @Override
    public void paintComponent(Graphics g){
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;

        // redraws all of the paths created so far
        for(ArrayList<ColorPoint> path: allPaths){
            ColorPoint from = null;
            for(ColorPoint point: path){
                g2d.setColor(point.getColor());

                // edge case: when a single dot is created
                if(path.size()==1){
                    g2d.fillRect(point.getX(), point.getY(), STROKE_SIZE, STROKE_SIZE);
                }
                
                if(from!=null){
                    g2d.setStroke(new BasicStroke(STROKE_SIZE));
                    g2d.drawLine(from.getX(), from.getY(), point.getX(), point.getY());
                }
                from = point;
            }
        }
    }

}
