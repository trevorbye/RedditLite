package com.reddit.dao;

import com.reddit.model.LikeTrackerEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LikeTrackerDao extends CrudRepository<LikeTrackerEntity, Integer>{

    @SuppressWarnings("unchecked")
    LikeTrackerEntity save(LikeTrackerEntity likeTrackerEntity);

    LikeTrackerEntity findByUsernameAndNodeId(String username, int nodeId);

    LikeTrackerEntity findByUsernameAndPostId(String username, int postId);

}
