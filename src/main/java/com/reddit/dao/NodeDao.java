package com.reddit.dao;

import com.reddit.model.NodeEntity;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface NodeDao extends CrudRepository<NodeEntity, Integer>{

    List<NodeEntity> findByPostId(int postId);

    NodeEntity findByNodeId(int nodeId);

    NodeEntity findByParentId(int parentId);

    @SuppressWarnings("unchecked")
    NodeEntity save(NodeEntity persisted);

    List<NodeEntity> findByUsernameOrderByCmmntsDesc(String username, Pageable pageable);

    NodeEntity findByBodyAndUsername(String body, String username);

    void delete(Integer id);

    boolean exists(Integer id);
}
