package com.marcleef.redditcli.Directory;

import com.marcleef.redditcli.Exceptions.NodeNotFoundException;

import java.util.ArrayList;

/**
 * Node super class
 * Created by marc_leef on 2/2/15.
 */
public class Node {
    private String name;
    private Node parent;
    private ArrayList<Node> links;
    private String path;

    /**
     * Superclass constructor
     * @param n Name of new node
     * @param p Parent of new node
     * @return Moderated node ContainerNode
     */
    public Node(String n, Node p) {
        name = n;
        parent = p;
        links = new ArrayList<Node>();
        path = parent != null ? parent.getPath() + "/" + name : "/" + name;
    }

    public String getName() {
        return name;
    }

    public String getPath() {
        return path;
    }

    public Node getParent() { return parent; }

    public void addBranch(Node n) {
        links.add(n);
    }

    /**
     * Returns string of all branch names from current node.
     *
     * @return String concatenation of all branch names, seperated by tab.
     */
    public String getBranches() {
        String result = "";
        for(Node n : links) {
            result += n.getName() + "\t";
        }
        if(result == "") {
            return null;
        }
        return result;
    }

    /**
     * Returns new container node that has been populated with user moderated subreddits.
     * @param n Name of node to find
     * @return Node whose name matches parameter
     */
    public Node getBranchByName(String n) throws NodeNotFoundException{
        for(Node node : links) {
            if(node.getName().equals(n)) {
                return node;
            }
        }
        throw (new NodeNotFoundException(n));
    }

    /**
     * Returns node data (subclasses override)
     *
     * @return Node data
     */
    public String getData() {
        return null;
    }
}
