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

    public void addBranch(Node n) {
        links.add(n);
    }

    public String getBranches() {
        String result = "";
        for(Node n : links) {
            result += n.getName() + "\t";
        }

        return result;
    }

    public Node getBranchByName(String n) throws NodeNotFoundException{
        for(Node node : links) {
            if(node.getName().equals(n)) {
                return node;
            }
        }
        throw (new NodeNotFoundException(n));
    }

    public String getData() {
        return null;
    }
}
