package com.reddit.service;


import com.reddit.model.LikeTrackerEntity;

public interface LikeTrackerService {

    LikeTrackerEntity persistLikeCombination(LikeTrackerEntity likeTrackerEntity);
    LikeTrackerEntity findNodeLike(String username, int nodeId);
    LikeTrackerEntity findPostLike(String username, int postId);

}
