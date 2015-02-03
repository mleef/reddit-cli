package com.marcleef.redditcli.Directory;

/**
 * Subclass for generic container node (directory)
 * Created by marc_leef on 2/2/15.
 */
public class ContainerNode extends Node {
    public ContainerNode(String n, Node p) {
        super(n, p);
    }


    @Override
    public String getData() {
        return this.getName() + " is a directory";
    }
}
