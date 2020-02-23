package com.cmput301w20t23.newber.models;

public class Rating {
    private int upvotes;
    private int downvotes;

    public Rating(int upvotes, int downvotes) {
        this.upvotes = upvotes;
        this.downvotes = downvotes;
    }

    public int getUpvotes() {
        return upvotes;
    }

    public void setUpvotes(int upvotes) {
        this.upvotes = upvotes;
    }

    public int getDownvotes() {
        return downvotes;
    }

    public void setDownvotes(int downvotes) {
        this.downvotes = downvotes;
    }

    public double calculateRating() {
        // TODO: implement
        return 0;
    }
}
