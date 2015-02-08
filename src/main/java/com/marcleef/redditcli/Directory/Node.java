package com.marcleef.redditcli.Directory;

import com.marcleef.redditcli.Action.Retrieval;
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
    private String cdID;

    /**
     * Superclass constructor
     * @param n Name of new node
     * @param p Parent of new node
     * @return Moderated node ContainerNode
     */
    public Node(String n, Node p) {
        cdID = n;
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

    public void addBranches(Node... nodes) {
        for(Node n : nodes) {
            links.add(n);
        }
    }

    /**
     * Returns string of all branch names from current node.
     *
     * @return String concatenation of all branch names, seperated by tab.
     */
    public String getBranchText() {
        String result = "";
        for(Node n : links) {
            result += n.getName() + "\n";
        }
        if(result == "") {
            return null;
        }
        return result.substring(0, result.length() - 1);
    }

    /**
     * Returns string of all branch names from current node.
     *
     * @return String concatenation of all branch names, seperated by tab.
     */
    public ArrayList<Node> getBranches() {
        return links;
    }

    /**
     * Returns new container node that has been populated with user moderated subreddits.
     * @param n Name of node to find
     * @return Node whose name matches parameter
     */
    public Node getBranchByName(String n) throws NodeNotFoundException{
        for(Node node : links) {
            if(node.getCdID().equals(n)) {
                return node;
            }

        }
        throw (new NodeNotFoundException(n + " not found in current directory"));
    }

    /**
     * Returns node data (subclasses override)
     *
     * @return Node data
     */
    public String getData() {
        return null;
    }

    public boolean needsToBePopulated() {
        return true;
    }

    public void populate(Retrieval retrieval) {

    }

    public String getCdID() {
        return cdID;
    }
}
