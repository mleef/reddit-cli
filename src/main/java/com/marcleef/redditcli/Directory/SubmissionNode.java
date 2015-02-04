package com.marcleef.redditcli.Directory;

import com.github.jreddit.entity.Submission;

/**
 * Created by marc_leef on 2/2/15.
 */
public class SubmissionNode extends Node {
    public Submission submission;
    public SubmissionNode(String n, Node p, Submission sub) {
        super(n, p);
        submission = sub;
    }
}
