package com.vady.da4124;

import com.vady.da4124.parser.GraphMLParser;
import grail.interfaces.DirectedGraphInterface;
import grail.interfaces.NodeInterface;
import grail.interfaces.DirectedEdgeInterface;
import grail.properties.GraphProperties;
import static org.junit.Assert.*;
import static org.junit.Assert.assertEquals;
import org.junit.Test;

import java.io.File;
import java.util.List;


public class GraphMLParserTest {

    @Test
    public void testParserOnGraph10() {
        File file = new File("g.10.1.graphml");

        GraphMLParser graphMLParser = new GraphMLParser();
        List graphs = graphMLParser.load(file);

        assertNotNull(graphs);
        assertFalse(graphs.isEmpty());
        assertEquals(1, graphs.size());

        DirectedGraphInterface graph = (DirectedGraphInterface) graphs.get(0);

        assertEquals(10, graph.nodeCount());
        assertEquals(17, graph.edgeCount());

        NodeInterface node = graph.getNode("n6");
        assertNotNull(node);

        assertEquals("n6", node.getKey());
        assertEquals("n6", node.getProperty(GraphProperties.LABEL));

        node = graph.getNode("EGZ");
        assertNull(node);
    }

    @Test
    public void testParserOnGraph100() {
        File file = new File("g.100.10.graphml");


        GraphMLParser graphMLParser = new GraphMLParser();
        List graphs = graphMLParser.load(file);

        assertNotNull(graphs);
        assertFalse(graphs.isEmpty());
        assertEquals(1, graphs.size());

        DirectedGraphInterface graph = (DirectedGraphInterface) graphs.get(0);

        assertEquals(100, graph.nodeCount());
        assertEquals(163, graph.edgeCount());

        NodeInterface node1 = graph.getNode("n39");
        assertNotNull(node1);

        assertEquals("n39", node1.getKey());
        assertEquals("n39", node1.getProperty(GraphProperties.LABEL));

        NodeInterface node2 = graph.getNode("n99");
        assertNotNull(node1);

        assertEquals("n99", node2.getKey());
        assertEquals("n99", node2.getProperty(GraphProperties.LABEL));

        DirectedEdgeInterface edge = (DirectedEdgeInterface) graph.getEdge("e162");
        assertNotNull(edge);

        assertEquals(node1, edge.getFrom());
        assertEquals(node2, edge.getTo());
    }

    @Test
    public void testOnUnorderedGraph() { // edge definition goes earlier then node definition. should work!
        File file = new File("unordered.graphml");


        GraphMLParser graphMLParser = new GraphMLParser();
        List graphs = graphMLParser.load(file);

        assertNotNull(graphs);
        assertFalse(graphs.isEmpty());
        assertEquals(1, graphs.size());

        DirectedGraphInterface graph = (DirectedGraphInterface) graphs.get(0);

        assertNotNull(graph);
        assertEquals(10, graph.nodeCount());
        assertEquals(17, graph.edgeCount());

        NodeInterface node = graph.getNode("n0");
        assertNotNull(node);

        assertEquals("n0", node.getKey());
        assertEquals("n0", node.getProperty(GraphProperties.LABEL));
    }

}
