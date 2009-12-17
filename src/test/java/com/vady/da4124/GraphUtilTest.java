package com.vady.da4124;

import org.junit.Test;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertEquals;
import com.vady.da4124.parser.GraphMLParser;
import com.vady.da4124.util.GraphUtil;

import java.io.File;
import java.util.List;

import grail.interfaces.DirectedGraphInterface;
import grail.interfaces.DirectedNodeInterface;
import junit.framework.Assert;


public class GraphUtilTest {

    @Test
    public void testLevel() {
        File file = new File("tree18.graphml");


        GraphMLParser graphMLParser = new GraphMLParser();
        List graphs = graphMLParser.load(file);

        assertNotNull(graphs);
        assertFalse(graphs.isEmpty());
        assertEquals(1, graphs.size());

        DirectedGraphInterface graph = (DirectedGraphInterface) graphs.get(0);


        Assert.assertEquals(0, GraphUtil.getLevel(graph, (DirectedNodeInterface) graph.getNode("n0")));
        Assert.assertEquals(1, GraphUtil.getLevel(graph, (DirectedNodeInterface) graph.getNode("n1")));
        Assert.assertEquals(4, GraphUtil.getLevel(graph, (DirectedNodeInterface) graph.getNode("n17")));
    }


    @Test
    public void testLevels() {
        File file = new File("tree18.graphml");


        GraphMLParser graphMLParser = new GraphMLParser();
        List graphs = graphMLParser.load(file);

        assertNotNull(graphs);
        assertFalse(graphs.isEmpty());
        assertEquals(1, graphs.size());

        DirectedGraphInterface graph = (DirectedGraphInterface) graphs.get(0);

        Assert.assertEquals(1, GraphUtil.getLevelNodes(graph, 0).size());
        Assert.assertEquals(4, GraphUtil.getLevelNodes(graph, 1).size());
        Assert.assertEquals(4, GraphUtil.getLevelNodes(graph, 4).size());

    }

    @Test
    public void testGraphDepth() {
        File file = new File("tree18.graphml");


        GraphMLParser graphMLParser = new GraphMLParser();
        List graphs = graphMLParser.load(file);

        assertNotNull(graphs);
        assertFalse(graphs.isEmpty());
        assertEquals(1, graphs.size());

        DirectedGraphInterface graph = (DirectedGraphInterface) graphs.get(0);

        Assert.assertEquals(5, GraphUtil.treeGraphDepth(graph));
    }

}
