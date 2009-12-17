package com.vady.da4124.layout;

import grail.interfaces.DirectedGraphInterface;
import grail.interfaces.NodeInterface;
import grail.interfaces.DirectedNodeInterface;
import grail.action.Action;
import grail.walker.Walker;
import grail.walker.BfsWalker;
import grail.iterators.NodeIterator;
import grail.properties.GraphProperties;


public class TreeGraphLayout implements GraphLayout {

    public void computeLayout(DirectedGraphInterface graph) {

        computeYCoordinate(graph);

    }

    private void computeYCoordinate(DirectedGraphInterface graph) {


    }

}
