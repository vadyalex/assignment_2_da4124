package com.vady.da4124;

import com.vady.da4124.parser.GraphMLParser;
import grail.interfaces.DirectedGraphInterface;
import grail.interfaces.NodeInterface;
import grail.properties.GraphProperties;
import static org.junit.Assert.*;
import org.junit.Test;

import java.io.File;
import java.util.List;


public class AppTest {

    @Test
    public void testParser() {
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

}
