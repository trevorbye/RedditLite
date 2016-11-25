package com.reddit.service;

import com.reddit.dao.NodeDao;
import com.reddit.model.NodeEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class NodeServiceImpl implements NodeService{

    @Autowired
    private NodeDao nodeDao;

    @Override
    public List<NodeEntity> commentsByPost(int postId) {
        return nodeDao.findByPostId(postId);
    }

    @Override
    public NodeEntity save(NodeEntity persisted) {
        return nodeDao.save(persisted);
    }

    @Override
    public NodeEntity findByNodeId(int nodeId) {
        return nodeDao.findByNodeId(nodeId);
    }

    @Override
    public NodeEntity findByParentId(int parentId) {
        return nodeDao.findByParentId(parentId);
    }

    @Override
    public List<NodeEntity> findTopComment(String username, Pageable pageable) {
        return nodeDao.findByUsernameOrderByCmmntsDesc(username, pageable);
    }

    @Override
    public NodeEntity findByBodyAndUsername(String body, String username) {
        return nodeDao.findByBodyAndUsername(body, username);
    }

    @Override
    public void delete(Integer id) {
        nodeDao.delete(id);
    }

    @Override
    public boolean exists(Integer id) {
        return nodeDao.exists(id);
    }
}
