package com.vady.da4124.layout;

import grail.interfaces.DirectedGraphInterface;
import grail.interfaces.DirectedNodeInterface;
import grail.iterators.NodeIterator;
import grail.properties.GraphProperties;
import grail.algorithms.FindRootNode;

import org.apache.log4j.Logger;
import com.vady.da4124.util.GraphUtil;

import java.util.Map;
import java.util.HashMap;
import java.awt.*;


public class TreeGraphLayout implements GraphLayout {

    private static final Logger LOGGER = Logger.getLogger(TreeGraphLayout.class);

    private static final int Y_STEP = 55;
    private static final int X_STEP = 55;


    protected Map<DirectedNodeInterface, Integer> basePositions = new HashMap<DirectedNodeInterface, Integer>();

    protected Point currentPoint = new Point();


    public void computeLayout(DirectedGraphInterface graph) {
        currentPoint = new Point(0, 0);

        DirectedNodeInterface root = FindRootNode.findRootNode(graph);

        calculateDimensionX(graph, root);

        currentPoint.x += this.basePositions.get(root)/2 + X_STEP;

        buildTree(graph, root, this.currentPoint.x);
    }

    protected void buildTree(DirectedGraphInterface graph, DirectedNodeInterface node, int x) {

            currentPoint.y += Y_STEP;
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

                lastX = lastX + sizeXofChild + X_STEP;
            }

            this.currentPoint.y -= Y_STEP;
    }

    protected int calculateDimensionX(DirectedGraphInterface graph, DirectedNodeInterface root) {

        int size = 0;

        int childrenCount = root.outDegree();
        if (childrenCount != 0) {
            NodeIterator it = graph.getSuccessors(root);
            while(it.hasNext()) {
                it.next();
                size += calculateDimensionX(graph, (DirectedNodeInterface) it.getNode()) + X_STEP;
            }
        }
        size = Math.max(0, size - X_STEP);
        basePositions.put(root, size);

        return size;
    }


}
