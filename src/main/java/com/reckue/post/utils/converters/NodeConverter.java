package com.reckue.post.utils.converters;

import com.reckue.post.models.Node;
import com.reckue.post.transfers.NodeRequest;
import com.reckue.post.transfers.NodeResponse;

/**
 * Class for converting NodeRequest object to Node and Node object to NodeResponse.
 *
 * @author Viktor Grigoriev
 */
public class NodeConverter {

    /**
     * Converts from NodeRequest to Node.
     *
     * @param nodeRequest the object of class NodeRequest
     * @return the object of class Node
     */
    public static <T> Node<?> convert(NodeRequest<? extends T> nodeRequest) {
        if (nodeRequest == null) {
            throw new IllegalArgumentException("Null parameters are not allowed");
        }
        return Node.builder()
                .type(nodeRequest.getType())
                .node(nodeRequest.getNode())
                .postId(nodeRequest.getPostId())
                .source(nodeRequest.getSource())
                .userId(nodeRequest.getUserId())
                .published(nodeRequest.getPublished())
                .build();
    }

    /**
     * Converts from Node to NodeResponse.
     *
     * @param node the object of class Node
     * @return the object of class NodeResponse
     */
    public static <T> NodeResponse<?> convert(Node<? extends T> node) {
        if (node == null) {
            throw new IllegalArgumentException("Null parameters are not allowed");
        }

        return NodeResponse.builder()
                .id(node.getId())
                .type(node.getType())
                .node(node.getNode())
                .postId(node.getPostId())
                .source(node.getSource())
                .userId(node.getUserId())
                .published(node.getPublished())
                .status(node.getStatus())
                .build();
    }
}
