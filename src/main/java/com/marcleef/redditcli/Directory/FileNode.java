package com.marcleef.redditcli.Directory;

/**
 * Created by marc_leef on 2/2/15.
 */
public class FileNode extends Node {
    private String fileText;
    public FileNode(String n, Node p, String text) {
        super(n, p);
        fileText = text;
    }

    @Override
    public String getData() {
        return fileText;
    }
}
