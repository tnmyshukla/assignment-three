package com.assignment.models;

import com.assignment.exceptions.InvalidInputException;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Graph class stores all the functionalities and attributes of graph.
 */
public class Graph {
  /**
   * Message when invalid node id is provided.
   */
  private final static String MESSAGE = "node id does not exist";
  /**
   * Stores directed graph of parents.
   */

  private Map<Integer, List<Integer>> parentsMap = new ConcurrentHashMap<>();
  /**
   * Stores directed graph of children.
   */

  private  Map<Integer, List<Integer>> childrenMap = new ConcurrentHashMap<>();
  /**
   * Stores details of each node.
   */
  private final List<Node> nodes = new ArrayList<>();

  public Map<Integer, List<Integer>> getChildrenMap() {
    return childrenMap;
  }

  public Map<Integer, List<Integer>> getParentsMap() {
    return parentsMap;
  }

  public List<Node> getNodes() {
    return nodes;
  }
  public void setParentsMap(final Map<Integer, List<Integer>> map){
    this.parentsMap=map;
  }
  public void setChildrenMap(Map<Integer, List<Integer>> childrenMap) {
    this.childrenMap = childrenMap;
  }

  /**
   * Constructor to initialize parent,child and nodes arraylist.
   */
  public Graph() {

  }



  /**
   * This method returns immediate parents list
   *
   * @param nodeId for which parents are required
   * @return list of parents
   */
  public List<Integer> getImmediateParents(final int nodeId) {
    if (!(parentsMap.containsKey(nodeId) || childrenMap.containsKey(nodeId))) {
      throw new InvalidInputException(MESSAGE);
    }
    return parentsMap.get(nodeId);
  }

  /**
   * This method returns immediate children list
   *
   * @param nodeId for which children are required
   * @return list of parents
   */
  public List<Integer> getImmediateChildren(final int nodeId) {
    if (!(parentsMap.containsKey(nodeId) || childrenMap.containsKey(nodeId))) {
      throw new InvalidInputException(MESSAGE);
    }
    return childrenMap.get(nodeId);
  }

  private void dfs(final int nodeId, final List<Boolean> visited, final Map<Integer, List<Integer>> neighbor, final List<Integer> res) {
    visited.set(nodeId, true);
    res.add(nodeId);
    if (neighbor.get(nodeId) == null) {
      neighbor.put(nodeId, new ArrayList<>());
    }
    for (final Integer c : neighbor.get(nodeId)) {
      if (!visited.get(c)) {
        dfs(c, visited, neighbor, res);
      }
    }
  }

  /**
   * This method returns the list of ancestors.
   *
   * @param nodeId for which ancestors are required
   * @return list of ancestors
   */
  public List<Integer> getAncestors(final int nodeId) {
    if (!(parentsMap.containsKey(nodeId) || childrenMap.containsKey(nodeId))) {
      throw new InvalidInputException(MESSAGE);
    }
    final ArrayList<Integer> res = new ArrayList<>();
    final ArrayList<Boolean> visited = new ArrayList<>(Collections.nCopies(10_000, false));

    dfs(nodeId, visited, parentsMap, res);
    final Integer x = nodeId;
    res.remove(x);
    return res;
  }

  /**
   * This method returns the list of descendants.
   *
   * @param nodeId for which descendants are required
   * @return list of descendants
   */
  public List<Integer> getDescendants(final int nodeId) {
    if (!(parentsMap.containsKey(nodeId) || childrenMap.containsKey(nodeId))) {
      throw new InvalidInputException(MESSAGE);
    }
    final ArrayList<Integer> res = new ArrayList<>();
    final ArrayList<Boolean> visited = new ArrayList<>(Collections.nCopies(10_000, false));

    dfs(nodeId, visited, childrenMap, res);
    final Integer x = nodeId;
    res.remove(x);
    return res;
  }

  /**
   * This method is used to delete dependency between parent and child.
   *
   * @param par parent node id
   * @param chi child node id
   */
  public void deleteDependency(final int par, final int chi) {
    if (!(parentsMap.containsKey(par) || childrenMap.containsKey(par))) {
      throw new InvalidInputException(MESSAGE);
    }
    if (!(parentsMap.containsKey(chi) || childrenMap.containsKey(chi))) {
      throw new InvalidInputException(MESSAGE);
    }
    final Integer p = par;
    final Integer c = chi;
    parentsMap.get(chi).remove(p);
    childrenMap.get(par).remove(c);
  }

  /**
   * This method is used to delete ode and all of its dependencies
   *
   * @param nodeId to be deleted
   */
  public void deleteNode(final int nodeId) {
    if (!(parentsMap.containsKey(nodeId) || childrenMap.containsKey(nodeId))) {
      throw new InvalidInputException(MESSAGE);
    }
    for (final int c : parentsMap.get(nodeId)) {
      final Integer obj = nodeId;
      childrenMap.get(c).remove(obj);
    }
    parentsMap.get(nodeId).clear();
    for (final int c : childrenMap.get(nodeId)) {
      final Integer obj = nodeId;
      parentsMap.get(c).remove(obj);
    }
    childrenMap.get(nodeId).clear();
  }

  @SuppressWarnings("PMD.OnlyOneReturn")
  private boolean checkCycle(final int nodeId, final List<Boolean> visited, final List<Boolean> recStack) {
    if (recStack.get(nodeId)) {
      return true;
    }
    if (visited.get(nodeId)) {
      return false;
    }
    visited.set(nodeId, true);
    recStack.set(nodeId, true);
    for (final int c : parentsMap.get(nodeId)) {
      if (checkCycle(c, visited, recStack)) {
        return true;
      }
    }
    recStack.set(nodeId, false);
    return false;
  }

  /**
   * This method adds a dependency between a given parent and child node.
   *
   * @param par parent node
   * @param chi child node
   * @return whether dependencies possible or not
   */
  @SuppressWarnings("PMD.OnlyOneReturn")
  public boolean addDependency(final int par, final int chi) {
    if (!(parentsMap.containsKey(par) || childrenMap.containsKey(par))) {
      throw new InvalidInputException(MESSAGE);
    }
    if (!(parentsMap.containsKey(chi) || childrenMap.containsKey(chi))) {
      throw new InvalidInputException(MESSAGE);
    }
    parentsMap.get(chi).add(par);
    final ArrayList<Boolean> visited = new ArrayList<>(Collections.nCopies(10_000, false));
    final ArrayList<Boolean> recStack = new ArrayList<>(Collections.nCopies(10_000, false));
    if (checkCycle(par, visited, recStack)) {
      parentsMap.get(chi).remove(Integer.valueOf(par));
      return false;
    } else {
      childrenMap.get(par).add(chi);
      return true;
    }
  }

  /**
   * This method adds a new node to graph
   *
   * @param nodeId node Id of the node to be created
   * @return
   */
  @SuppressWarnings("PMD.OnlyOneReturn")
  public boolean addNode(final int nodeId) {

    if (parentsMap.containsKey(nodeId)) {
      return false;
    }
    parentsMap.put(nodeId, new ArrayList<>());
    childrenMap.put(nodeId, new ArrayList<>());
    return true;
  }
}