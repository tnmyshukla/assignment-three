package com.assignment.models;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * This class is definition of each node.
 */
public class Node {
  /**
   * nodeid that is unique for each node
   */
  private int nodeId;
  /**
   * Name of each node
   */
  private String nodeName;
  /**
   * Hashmap to store additional information for each node.
   */

  private Map<String, String> map = new ConcurrentHashMap<>();

  /**
   * Constructor of node class
   *
   * @param nodeId
   * @param nodeName
   * @param map
   */
  public Node(final int nodeId, final String nodeName, final Map<String, String> map) {
    this.map = map;
    this.nodeId = nodeId;
    this.nodeName = nodeName;
  }

  /**
   * Constructor taking only node id as input
   *
   * @param nodeId
   */
  public Node(final int nodeId) {
    this.nodeId = nodeId;
    this.nodeName = "No-name";

  }

  public int getNodeId() {
    return nodeId;
  }

  public String getNodeName() {
    return nodeName;
  }

  public Map<String, String> getMap() {
    return map;
  }

  public void setMap(final Map<String, String> map) {
    this.map = map;
  }

  public void setNodeName(final String nodeName) {
    this.nodeName = nodeName;
  }

  public void setNodeId(final int nodeId) {
    this.nodeId = nodeId;
  }
}
