package com.vady.da4124.parser;

import org.xml.sax.helpers.DefaultHandler;
import org.xml.sax.SAXException;
import org.xml.sax.Attributes;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import java.io.IOException;
import java.io.File;
import java.util.HashSet;
import java.util.ArrayList;
import java.util.List;

import grail.interfaces.*;
import grail.setbased.SetBasedDirectedGraph;
import grail.properties.GraphProperties;


public class GraphMLParser extends DefaultHandler {

    private List graphs;

    private DirectedGraphInterface graph;

    public java.util.List load(File file) {

        graphs = new ArrayList();

		//get a factory
		SAXParserFactory spf = SAXParserFactory.newInstance();
		try {

			//get a new instance of parser
			SAXParser sp = spf.newSAXParser();

			//parse the file and also register this class for call backs
			sp.parse(file, this);

		}catch(SAXException se) { // TODO add logger
			se.printStackTrace();
		}catch(ParserConfigurationException pce) {
			pce.printStackTrace();
		}catch (IOException ie) {
			ie.printStackTrace();
		}

        return graphs;
	}

    //Event Handlers
	public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException {

        if(qName.equalsIgnoreCase("graph")) {

            String edgeDefault = attributes.getValue("edgedefault");

//            if (edgeDefault.compareToIgnoreCase("directed") == 0) { TODO create directed or undirected graphs!
                graph = new SetBasedDirectedGraph();
  //          } else {
    //            graph = new SetBasedUndirectedGraph();
      //      }

            String id = attributes.getValue("id");
            if (id != null) {
                graph.setProperty(GraphProperties.LABEL, id);
            }
		}


        if(qName.equalsIgnoreCase("node")) { // TODO make order independent!
            String id = attributes.getValue("id");
            if (id != null) {
                DirectedNodeInterface node = graph.createNode(id);
                node.setProperty(GraphProperties.LABEL, id);

                graph.addNode(node);
            }
        }

        if(qName.equalsIgnoreCase("edge")) { // TODO make order independent!
            DirectedEdgeInterface edge = null;

            String id = attributes.getValue("id");


            String source = attributes.getValue("source");
            DirectedNodeInterface sourceNode = (DirectedNodeInterface) graph.getNode(source);

            if (sourceNode == null) { // TODO add logger
                System.out.println("Fatal error!");
                System.out.println("    Can't create edge " + id);
                System.out.println("    Because node does not exist! " + source);
            }


            String target = attributes.getValue("target");
            DirectedNodeInterface targetNode = (DirectedNodeInterface) graph.getNode(target);

            if (targetNode == null) { // TODO add logger
                System.out.println("Fatal error!");
                System.out.println("    Can't create edge " + id);
                System.out.println("    Because node does not exist! " + target);
            }


            if (id != null) {
                edge = graph.createEdge(id, sourceNode, targetNode);
            } else {
                edge = graph.createEdge(source + "-->" + target, sourceNode, targetNode);
            }


            if (edge != null) {
                graph.addEdge(edge);
            }
        }

	}


/*
	public void characters(char[] ch, int start, int length) throws SAXException {
		tempVal = new String(ch, start, length);
        System.out.println(tempVal);
	}
*/

	public void endElement(String uri, String localName, String qName) throws SAXException {

		if(qName.equalsIgnoreCase("graph")) {
			//add it to the list
		    graphs.add(graph);
            graph = null;
		}

	}

}
