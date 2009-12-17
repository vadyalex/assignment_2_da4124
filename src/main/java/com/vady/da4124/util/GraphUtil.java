package com.vady.da4124.util;


// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3)
// Source File Name:   NonRecDFS.java

import grail.interfaces.AbstractNode;
import grail.interfaces.DirectedGraphInterface;
import grail.interfaces.DirectedNodeInterface;
import grail.iterators.NodeIterator;

import java.util.*;

public class GraphUtil {

    public static final GraphUtil instance = new GraphUtil();

    private static HashSet visited;
    private static List order;
    private static int dfs_count;
    private static Stack stack;

    private GraphUtil() {
        init();
    }

    protected void init() {
        visited = new HashSet();
        order = new ArrayList();
        dfs_count = 0;
        stack = new Stack();
    }

    protected void clear() {
        visited.clear();
        order.clear();
        dfs_count = 0;
        stack.clear();
    }

    public List invertDfs(DirectedGraphInterface graph, DirectedNodeInterface root) {
        clear();

        walkByPredecessors(graph, root);

        return order;
    }

    public List dfs(DirectedGraphInterface graph, DirectedNodeInterface root) {
        clear();

        walkBySuccessors(graph, root);

        return order;
    }

    public List invertDfs(DirectedGraphInterface graph) {
        clear();

        NodeIterator ni = graph.nodes();
        DirectedNodeInterface n = null;

        while (ni.hasNext()) {
            ni.next();
            n = (DirectedNodeInterface) ni.getNode();

            if (!visited.contains(n)) {
                walkBySuccessors(graph, n);
            }

        }

        return order;
    }

    private void walkByPredecessors(DirectedGraphInterface graph, DirectedNodeInterface first) {
        stack.push(first);

        while (!stack.isEmpty()) {
            AbstractNode next = (AbstractNode) stack.pop();

            if (visited.add(next)) {
                next.num = dfs_count++;
                order.add(next);

                for (NodeIterator it = graph.getPredecessors((DirectedNodeInterface) next); it.hasNext();) {
                    it.next();
                    DirectedNodeInterface s = (DirectedNodeInterface) it.getNode();

                    if (!visited.contains(s)) {
                        stack.push(s);
                    }
                }

            }
        }
    }

    private void walkBySuccessors(DirectedGraphInterface graph, DirectedNodeInterface first) {
        stack.push(first);

        while (!stack.isEmpty()) {
            AbstractNode next = (AbstractNode) stack.pop();

            if (visited.add(next)) {
                next.num = dfs_count++;
                order.add(next);

                for (NodeIterator it = graph.getSuccessors((DirectedNodeInterface) next); it.hasNext();) {
                    it.next();
                    DirectedNodeInterface s = (DirectedNodeInterface) it.getNode();

                    if (!visited.contains(s)) {
                        stack.push(s);
                    }
                }

            }
        }
    }

    public Set nodeLeafsSet(DirectedNodeInterface node) {
        Set leafs = new HashSet();

        walkOnSuccessors(leafs, node);

        return leafs;
    }

    public void walkOnSuccessors(Set result, DirectedNodeInterface node) {
        if (result.add(node)) {

            NodeIterator successors = node.getSuccessors();

            while (successors.hasNext()) {
                successors.next();

                walkOnSuccessors(result, (DirectedNodeInterface) successors.getNode());
            }

        }
    }

    public static int getLevel(DirectedGraphInterface graph, DirectedNodeInterface node) {
        return instance.invertDfs(graph, node).size()-1;
    }

    public static List<DirectedNodeInterface> getLevelNodes(DirectedGraphInterface graph, int level) {
        List result = new ArrayList();

        NodeIterator ni = graph.nodes();
        while(ni.hasNext()) {
            ni.next();
            DirectedNodeInterface node = (DirectedNodeInterface) ni.getNode();

            if (getLevel(graph, node) == level) {
                result.add(node);
            }
        }

        return result;
    }

    public static int treeGraphDepth(DirectedGraphInterface graph) {
        int depth = 0;

        NodeIterator ni = graph.nodes();
        while(ni.hasNext()) {
            ni.next();
            DirectedNodeInterface node = (DirectedNodeInterface) ni.getNode();

            int level = getLevel(graph, node);
            if (level > depth) {
                depth = level;
            }
        }

        return depth + 1;
    }
}
