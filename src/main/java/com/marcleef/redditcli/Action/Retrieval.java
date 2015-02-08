package com.marcleef.redditcli.Action;

import com.github.jreddit.action.ProfileActions;
import com.github.jreddit.entity.*;
import com.github.jreddit.exception.RedditError;
import com.github.jreddit.exception.RetrievalFailedException;
import com.github.jreddit.retrieval.Comments;
import com.github.jreddit.retrieval.Subreddits;
import com.github.jreddit.retrieval.Submissions;
import com.github.jreddit.retrieval.params.CommentSort;
import com.github.jreddit.retrieval.params.SubmissionSort;
import com.github.jreddit.utils.restclient.RestClient;
import com.marcleef.redditcli.Directory.ContainerNode;
import com.marcleef.redditcli.Directory.FileNode;
import java.util.List;
import java.util.ArrayList;
import com.marcleef.redditcli.Directory.*;
/**
 * Interfaces with jreddit to populate directory with Reddit entities.
 * Created by marc_leef on 2/2/15.
 */
public class Retrieval {
    private static User user;
    private static ProfileActions profActions;
    private static RestClient restClient;
    private static Subreddits subredditActions;
    private static Submissions submission;
    private static Comments comments;

    /**
     * Constructor, handles interaction with jreddit.
     *
     * @return Home node ContainerNode
     */
    public Retrieval(RestClient rc, User usr, ProfileActions profAct) {
        restClient = rc;
        user = usr;
        profActions = profAct;
        subredditActions = new Subreddits(restClient, user);
        submission = new Submissions(restClient, user);
        comments = new Comments(restClient, user);
    }
    /**
     * Returns new container node that has been populated with user info and subscribed subreddits.
     *
     * @return Home node ContainerNode
     */
    public static ContainerNode buildHomeDirectory() throws RetrievalFailedException, RedditError {
        ContainerNode home = new ContainerNode("home", null);
        ContainerNode subreddits = new ContainerNode("subreddits", home);

        // Build user subreddit trees
        subreddits.addBranch(buildUserSubscribedSubreddits(subreddits));
        subreddits.addBranch(buildUserModeratedSubreddits(subreddits));
        //subreddits.addBranch(buildUserContributedToSubreddits(subreddits));
        try {
            UserInfo ui = profActions.getUserInformation();
            FileNode userInfo = new FileNode("user_info.txt", home, ui.toString());
            home.addBranch(userInfo);
            home.addBranch(subreddits);
        }
        catch (Exception e) {
            System.out.println("Couldn't Get User Information: " + e.getMessage());
        }
        return home;
    }

    /**
     * Returns new container node that has been populated with user subscribed subreddits.
     *
     * @return Subscribed node ContainerNode
     */
    public static ContainerNode buildUserSubscribedSubreddits(Node parent) throws RetrievalFailedException, RedditError {
        ContainerNode subscribed = new ContainerNode("subscribed", parent);
        List<Subreddit> subs = subredditActions.parse("/subreddits/mine/subscriber.json?&limit=100");
        for(Subreddit s : subs) {
            subscribed.addBranch(new SubredditNode(s.getDisplayName(), subscribed, s));
        }
        return subscribed;
    }

    /**
     * Returns new container node that has been populated with user moderated subreddits.
     *
     * @return Moderated node ContainerNode
     */
    public static ContainerNode buildUserModeratedSubreddits(Node parent) throws RetrievalFailedException, RedditError {
        ContainerNode moderated = new ContainerNode("moderated", parent);
        List<Subreddit> subs = subredditActions.parse("/subreddits/mine/moderator.json?&limit=100");
        for(Subreddit s : subs) {
            moderated.addBranch(new SubredditNode(s.getDisplayName(), moderated, s));
        }
        return moderated;
    }

    /**
     * Returns new container node that has been populated with user contributed subreddits.
     *
     * @return Conttributed node ContainerNode
     */
    public static ContainerNode buildUserContributedToSubreddits(Node parent) throws RetrievalFailedException, RedditError {
        ContainerNode contributed = new ContainerNode("contributed", parent);
        List<Subreddit> subs = subredditActions.parse("/subreddits/mine/contributor.json?&limit=100");
        for(Subreddit s : subs) {
            contributed.addBranch(new SubredditNode(s.getDisplayName(), contributed, s));
        }
        return contributed;
    }

    public static ArrayList<SubmissionNode> buildSubmissions(SubredditNode parent) throws RetrievalFailedException, RedditError {
        Subreddit sub = parent.subreddit;
        ArrayList<SubmissionNode> result = new ArrayList<SubmissionNode>();
        List<Submission> submissionsSubreddit = submission.ofSubreddit(sub.getDisplayName(), SubmissionSort.TOP, -1, 25, null, null, true);
        for(Submission subm : submissionsSubreddit) {
            result.add(new SubmissionNode(subm.getIdentifier(), parent, subm));
        }

        return result;
    }

    public static ArrayList<CommentNode> buildComments(SubmissionNode parent) throws RetrievalFailedException, RedditError {
        Submission sub = parent.submission;
        ArrayList<CommentNode> result = new ArrayList<CommentNode>();
        List<Comment> commentsSubmission = comments.ofSubmission(sub.getIdentifier(), null, 0, 8, 100, CommentSort.TOP);
        for(Comment c : commentsSubmission) {
            result.add(new CommentNode(sub.getIdentifier(), parent, c));
        }
        return null;
    }

}
