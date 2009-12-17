package com.vady.da4124.paint;

import com.vady.da4124.parser.GraphMLParser;
import com.vady.da4124.layout.*;
import com.vady.da4124.layout.GridGraphLayout;

import java.awt.*;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Line2D;
import java.awt.geom.GeneralPath;
import java.awt.geom.Point2D;
import java.io.File;


import grail.interfaces.DirectedGraphInterface;
import grail.interfaces.NodeInterface;
import grail.interfaces.DirectedEdgeInterface;
import grail.interfaces.DirectedNodeInterface;
import grail.iterators.NodeIterator;
import grail.iterators.EdgeIterator;
import grail.properties.GraphProperties;
import org.apache.log4j.Logger;


public class Scene {

    private static final Logger LOGGER = Logger.getLogger(Scene.class);

    private static final Integer NODE_SIZE = 50;

    private GraphMLParser graphMLParser = new GraphMLParser(); // TODO maybe use factory in the future to load another file formats (GML, etc..)
    //private GraphLayout layout = new GridGraphLayout();
    private GraphLayout layout = new TreeGraphLayout();

    private DirectedGraphInterface graph;


    public Scene(String graphFile) {
        graph = (DirectedGraphInterface) graphMLParser.load(new File(graphFile)).get(0); // TODO program draws only first graph(!) in graphml file!

        layout.computeLayout(graph);
    }


    public void draw(Graphics graphics) {
        Graphics2D g2 = (Graphics2D) graphics;

        drawNodes(g2);

        drawEdges(g2);

    }


    private void drawNodes(Graphics2D g2) {
        NodeIterator nodeIterator = graph.nodes();
        while(nodeIterator.hasNext()) {
            nodeIterator.next();
            NodeInterface node = nodeIterator.getNode();

            drawNode(g2, node);
        }
    }


    private void drawNode(Graphics2D g2, NodeInterface node) {
        Shape nodeShape = null;

        Integer x = ((Double) node.getProperty(GraphProperties.X)).intValue();
        Integer y = ((Double) node.getProperty(GraphProperties.Y)).intValue();

        if (x == null || y == null) {
            LOGGER.fatal("NO X,Y COORDINATE FOR NODE!");
        } else {
            nodeShape = new Ellipse2D.Double(x, y, NODE_SIZE, NODE_SIZE);
        }

        g2.draw(nodeShape);


        String id = (String) node.getProperty(GraphProperties.LABEL);
        g2.drawString(id, x, y);
    }


    private void drawEdges(Graphics2D g2) {
    EdgeIterator edgeIterator = graph.edges();
        while(edgeIterator.hasNext()) {
            edgeIterator.next();
            DirectedEdgeInterface edge = (DirectedEdgeInterface) edgeIterator.getEdge();

            drawEdge(g2, edge);
        }
    }


    private void drawEdge(Graphics2D g2, DirectedEdgeInterface edge) {
        DirectedNodeInterface nodeFrom = edge.getFrom();

        Integer x1 = ((Double) nodeFrom.getProperty(GraphProperties.X)).intValue();
        Integer y1 = ((Double) nodeFrom.getProperty(GraphProperties.Y)).intValue();

        DirectedNodeInterface nodeTo = edge.getTo();

        Integer x2 = ((Double) nodeTo.getProperty(GraphProperties.X)).intValue();
        Integer y2 = ((Double) nodeTo.getProperty(GraphProperties.Y)).intValue();

        int shift = NODE_SIZE / 2;

        g2.drawLine(x1+shift, y1+shift, x2+shift, y2+shift);
    }

    private GeneralPath getPath(Rectangle r1, Rectangle r2) {
        int barb = 20;
        double phi = Math.toRadians(20);

        double x1 = r1.getCenterX();
        double y1 = r1.getCenterY();
        double x2 = r2.getCenterX();
        double y2 = r2.getCenterY();
        double theta = Math.atan2(y2 - y1, x2 - x1);

        Point2D.Double p1 = getPoint(theta, r1);
        Point2D.Double p2 = getPoint(theta+Math.PI, r2);
        GeneralPath path = new GeneralPath(new Line2D.Float(p1, p2));

        // Add an arrow head at p2.
        double x = p2.x + barb*Math.cos(theta+Math.PI-phi);
        double y = p2.y + barb*Math.sin(theta+Math.PI-phi);

        path.moveTo((float)x, (float)y);
        path.lineTo((float)p2.x, (float)p2.y);

        x = p2.x + barb*Math.cos(theta+Math.PI+phi);
        y = p2.y + barb*Math.sin(theta+Math.PI+phi);

        path.lineTo((float)x, (float)y);

        return path;
    }

    private Point2D.Double getPoint(double theta, Rectangle r) {
        double cx = r.getCenterX();
        double cy = r.getCenterY();
        double w = r.width/2;
        double h = r.height/2;
        double d = Point2D.distance(cx, cy, cx+w, cy+h);
        double x = cx + d*Math.cos(theta);
        double y = cy + d*Math.sin(theta);
        Point2D.Double p = new Point2D.Double();
        int outcode = r.outcode(x, y);
        switch(outcode) {
            case Rectangle.OUT_TOP:
                p.x = cx - h*((x-cx)/(y-cy));
                p.y = cy - h;
                break;
            case Rectangle.OUT_LEFT:
                p.x = cx - w;
                p.y = cy - w*((y-cy)/(x-cx));
                break;
            case Rectangle.OUT_BOTTOM:
                p.x = cx + h*((x-cx)/(y-cy));
                p.y = cy + h;
                break;
            case Rectangle.OUT_RIGHT:
                p.x = cx + w;
                p.y = cy + w*((y-cy)/(x-cx));
                break;
        }
        return p;
    }

}
