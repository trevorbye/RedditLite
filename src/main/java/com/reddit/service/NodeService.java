package com.reddit.service;

import com.reddit.model.NodeEntity;
import org.springframework.data.domain.Pageable;

import java.util.List;


public interface NodeService {

    List<NodeEntity> commentsByPost(int postId);

    NodeEntity save(NodeEntity persisted);

    NodeEntity findByNodeId(int nodeId);

    NodeEntity findByParentId(int parentId);

    List<NodeEntity> findTopComment(String username, Pageable pageable);

    NodeEntity findByBodyAndUsername(String body, String username);

    void delete(Integer id);

    boolean exists(Integer id);
}
