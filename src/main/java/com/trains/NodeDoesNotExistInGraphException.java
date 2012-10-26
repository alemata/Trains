package com.trains;

public class NodeDoesNotExistInGraphException extends Exception {

    private String nodeId;

    public NodeDoesNotExistInGraphException(String nodeId) {
        super("Node '" + nodeId + "' does not exist in graph" );
        this.nodeId = nodeId;
    }

    public String getNodeId() {
        return nodeId;
    }
}
