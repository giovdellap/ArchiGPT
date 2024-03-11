package com.example.marketinganalyzer.Models.Api;

public class RecommendationLikeResponse {
    private int likes;
    private int unLikes;

    public RecommendationLikeResponse(int likes, int unLikes) {
        this.likes = likes;
        this.unLikes = unLikes;
    }

    public int getLikes() {
        return likes;
    }

    public void setLikes(int likes) {
        this.likes = likes;
    }

    public int getUnLikes() {
        return unLikes;
    }

    public void setUnLikes(int unLikes) {
        this.unLikes = unLikes;
    }
}
