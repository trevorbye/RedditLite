package com.reddit.service;

import com.reddit.dao.LikeTrackerDao;
import com.reddit.model.LikeTrackerEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class LikeTrackerServiceImpl implements LikeTrackerService{

    @Autowired
    private LikeTrackerDao likeTrackerDao;

    @Override
    public LikeTrackerEntity persistLikeCombination(LikeTrackerEntity likeTrackerEntity) {
        return likeTrackerDao.save(likeTrackerEntity);
    }

    @Override
    public LikeTrackerEntity findNodeLike(String username, int nodeId) {
        return likeTrackerDao.findByUsernameAndNodeId(username, nodeId);
    }

    @Override
    public LikeTrackerEntity findPostLike(String username, int postId) {
        return likeTrackerDao.findByUsernameAndPostId(username, postId);
    }
}
