package com.marcleef.redditcli.Directory;

import com.github.jreddit.entity.Comment;

/**
 * Created by marc_leef on 2/2/15.
 */
public class CommentNode extends Node {
    public Comment comment;
    public CommentNode(String n, Node p, Comment com) {
        super(n, p);
        comment = com;
    }

    @Override
    public String getName() {
        return String.format("[%s]--[%s]" + comment.getAuthor(), comment.getIdentifier(), comment.getScore());
    }

    @Override
    public String getCdID() {
        return comment.getIdentifier();
    }

}
