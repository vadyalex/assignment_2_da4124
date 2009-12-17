package com.vady.da4124.layout;

import grail.interfaces.DirectedGraphInterface;
import grail.interfaces.DirectedNodeInterface;
import grail.iterators.NodeIterator;
import grail.properties.GraphProperties;


public class GridGraphLayout implements GraphLayout {

    public void computeLayout(DirectedGraphInterface graph) {

        NodeIterator nodeIterator = graph.nodes();

        for (int i = 0; i < 5; i++) {
            for (int j = 0; j < 5; j++) {
                if (!nodeIterator.hasNext()) {
                    return;
                }

                nodeIterator.next();
                DirectedNodeInterface node = (DirectedNodeInterface) nodeIterator.getNode();

                node.setProperty(GraphProperties.X, new Double(j * 100) + 100);
                node.setProperty(GraphProperties.Y, new Double(i * 100) + 100);

            }
        }

    }

}