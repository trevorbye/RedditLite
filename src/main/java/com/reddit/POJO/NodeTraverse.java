package com.reddit.POJO;


import com.reddit.model.NodeEntity;

import java.util.ArrayList;
import java.util.List;

public class NodeTraverse {

    //prevent instantiation; class only provides static methods
    private NodeTraverse() {
    }

    //checks if a node has any child nodes in the list of comments
    private static boolean hasChildren(List<NodeEntity> commentList, NodeEntity node) {
        boolean hasChildren = false;

        for (NodeEntity record : commentList) {
            if (record.getParentId() == node.getNodeId()) {
                hasChildren = true;
                break;
            }
        }

        return hasChildren;
    }

    //this list would be put into the List<NodeEntity> in the PostEntity object
    //list of first-level post comments
    public static List<NodeEntity> firstLevelNodes(List<NodeEntity> commentList, int postId) {
        List<NodeEntity> list = new ArrayList<>();

        for (NodeEntity node : commentList) {
            if (node.getParentId() == postId) {
                node.setShow(true);
                node.setErrorResponse(null);
                list.add(node);
            }
        }

        return list;
    }


    //recursive method starts on a single node and traverses down through each list of nodes
    //commentList is the global list of nodes with matching post id; remains the same regardless of depth

    public static void traverseNodes(List<NodeEntity> commentList, NodeEntity node) {
        boolean hasChildren = hasChildren(commentList, node);

        if (hasChildren) {
            for (NodeEntity comment: commentList) {
                if (comment.getParentId() == node.getNodeId()) {
                    comment.setShow(true);
                    comment.setErrorResponse(null);
                    node.getNodeList().add(comment);
                }
            }
        }

        if (hasChildren) {
            for (NodeEntity recursiveNode : node.getNodeList()) {
                traverseNodes(commentList, recursiveNode);
            }
        }
    }
}
