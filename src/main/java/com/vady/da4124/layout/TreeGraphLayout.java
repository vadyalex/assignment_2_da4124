package com.vady.da4124.layout;

import grail.interfaces.DirectedGraphInterface;
import grail.interfaces.DirectedNodeInterface;
import grail.iterators.NodeIterator;
import grail.properties.GraphProperties;
import grail.algorithms.FindRootNode;

import org.apache.log4j.Logger;

import java.util.Map;
import java.util.HashMap;
import java.awt.*;


public class TreeGraphLayout implements GraphLayout {

    private static final Logger LOGGER = Logger.getLogger(TreeGraphLayout.class);

    private int stepY = 55;

    private int stepX = 55;


    protected Map<DirectedNodeInterface, Integer> basePositions = new HashMap<DirectedNodeInterface, Integer>();

    protected Point currentPoint = new Point();


    public void computeLayout(DirectedGraphInterface graph) {
        currentPoint = new Point(0, 0);

        DirectedNodeInterface root = FindRootNode.findRootNode(graph);

        calculateDimensionX(graph, root);

        currentPoint.x += this.basePositions.get(root)/2 + stepX;

        buildTree(graph, root, this.currentPoint.x);
    }

    protected void buildTree(DirectedGraphInterface graph, DirectedNodeInterface node, int x) {

            currentPoint.y += stepY;
            currentPoint.x = x;

            node.setProperty(GraphProperties.X, currentPoint.getX());
            node.setProperty(GraphProperties.Y, currentPoint.getY());

            int sizeXofCurrent = basePositions.get(node);

            int lastX = x - sizeXofCurrent / 2;

            int sizeXofChild;
            int startXofChild;

            NodeIterator it = graph.getSuccessors(node);
            while(it.hasNext()) {
                it.next();
                DirectedNodeInterface element = (DirectedNodeInterface) it.getNode();

                sizeXofChild = this.basePositions.get(element);
                startXofChild = lastX + sizeXofChild / 2;

                buildTree(graph, element, startXofChild);

                lastX = lastX + sizeXofChild + stepX;
            }

            this.currentPoint.y -= stepY;
    }

    protected int calculateDimensionX(DirectedGraphInterface graph, DirectedNodeInterface root) {

        int size = 0;

        int childrenCount = root.outDegree();
        if (childrenCount != 0) {
            NodeIterator it = graph.getSuccessors(root);
            while(it.hasNext()) {
                it.next();
                size += calculateDimensionX(graph, (DirectedNodeInterface) it.getNode()) + stepX;
            }
        }
        size = Math.max(0, size - stepX);
        basePositions.put(root, size);

        return size;
    }


    public int getStepY() {
        return stepY;
    }

    public void setStepY(int stepY) {
        this.stepY = stepY;
    }

    public int getStepX() {
        return stepX;
    }

    public void setStepX(int stepX) {
        this.stepX = stepX;
    }

}
