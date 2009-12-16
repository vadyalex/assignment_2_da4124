package com.vady.da4124.parser;

import grail.interfaces.DirectedEdgeInterface;
import grail.interfaces.DirectedGraphInterface;
import grail.interfaces.DirectedNodeInterface;
import grail.interfaces.NodeInterface;
import grail.properties.GraphProperties;
import grail.setbased.SetBasedDirectedGraph;
import org.apache.log4j.Logger;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import java.io.File;
import java.util.LinkedList;
import java.util.List;


public class GraphMLParser extends DefaultHandler {

    private static final Logger LOGGER = Logger.getLogger(GraphMLParser.class);

    private static final String TAG_GRAPH = "graph";
    private static final String TAG_NODE = "node";
    private static final String TAG_EDGE = "edge";
    private static final String ATTRIBUTE_ID = "id";
    private static final String ATTRIBUTE_EDGE_SOURCE = "source";
    private static final String ATTRIBUTE_EDGE_TARGET = "target";

    private List graphs;
    private DirectedGraphInterface graph;

    public List load(File file) {

        LOGGER.info("Parsing graphml file " + file.getAbsolutePath());

        if (graphs == null) {
            graphs = new LinkedList();
        } else {
            graphs.clear();
        }

        SAXParserFactory saxParserFactory = SAXParserFactory.newInstance();
        try {
            SAXParser saxParser = saxParserFactory.newSAXParser();

            // parse the graphml file and also register this class for call backs
            saxParser.parse(file, this);

        } catch (Exception e) {
            LOGGER.error(e);
        }

        LOGGER.info("Done.");
        LOGGER.info("Loaded " + graphs.size() + " graphs.");

        return graphs;
    }

    // Event Handlers
    public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException {

        LOGGER.debug("TAG OPENS. Start processing..");

        if (qName.equalsIgnoreCase(TAG_GRAPH)) {
            LOGGER.debug("Processing tag.." + TAG_GRAPH);

            graph = new SetBasedDirectedGraph();

            String id = attributes.getValue(ATTRIBUTE_ID);
            if (id != null) {
                graph.setProperty(GraphProperties.LABEL, id);
            }
        }


        if (qName.equalsIgnoreCase(TAG_NODE)) {
            LOGGER.debug("Processing tag.." + TAG_NODE);

            String id = attributes.getValue(ATTRIBUTE_ID);
            createNode(id);
        }

        if (qName.equalsIgnoreCase(TAG_EDGE)) {
            LOGGER.debug("Processing tag.." + TAG_EDGE);

            DirectedEdgeInterface edge;

            String id = attributes.getValue(ATTRIBUTE_ID);

            String source = attributes.getValue(ATTRIBUTE_EDGE_SOURCE);
            DirectedNodeInterface sourceNode = (DirectedNodeInterface) getNode(source);

            String target = attributes.getValue(ATTRIBUTE_EDGE_TARGET);
            DirectedNodeInterface targetNode = (DirectedNodeInterface) getNode(target);

            if (id != null) {
                edge = graph.createEdge(id, sourceNode, targetNode);
            } else {
                edge = graph.createEdge(source + "-->" + target, sourceNode, targetNode);
            }

            if (edge != null) {
                graph.addEdge(edge);
            }

        }

        LOGGER.debug("TAG OPENS. Processing done.");
    }

    public void endElement(String uri, String localName, String qName) throws SAXException {

        LOGGER.debug("TAG CLOSES");

        if (qName.equalsIgnoreCase(TAG_GRAPH)) {
            // add it to the list
            graphs.add(graph);
            graph = null;
        }

        LOGGER.debug("TAG CLOSES. Processing done.");
    }

    private NodeInterface getNode(Object key) {
        NodeInterface node = graph.getNode(key);

        if (node == null) {
            LOGGER.debug("Node does not exist. Creating..");
            node = createNode(key);
        }

        return node;
    }

    private NodeInterface createNode(Object key) {
        NodeInterface node = null;

        if (key != null) {
            LOGGER.debug("Creating node with key: '" + key + "'");

            node = graph.createNode(key);
            node.setProperty(GraphProperties.LABEL, key);

            LOGGER.debug("Adding node to graph.");

            graph.addNode((DirectedNodeInterface) node);

            LOGGER.debug("Done.");
        } else {
            LOGGER.error("Cannt create node! No node 'id' attribute detected!!");
        }

        return node;
    }

}
