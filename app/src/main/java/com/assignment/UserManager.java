package com.assignment;

import com.assignment.exceptions.InvalidInputException;
import com.assignment.models.Graph;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.concurrent.ConcurrentHashMap;

/**
 * This is user manager class to take all the inputs from user and print the outputs to console.
 */
public class UserManager {
  /**
   * Stores directed graph of parents.
   */

  private final Map<Integer, List<Integer>> parentsMap = new ConcurrentHashMap<>();
  /**
   * Stores directed graph of children.
   */

  private final Map<Integer, List<Integer>> childrenMap = new ConcurrentHashMap<>();
  /**
   * This function displays all the functionalities
   * that can be used and input codes[1..9] associated with
   * each of them.
   */
  /**
   * String constants that can be used to print messages without redundancy.
   */
  private static final String MESSAGE1 = "Enter node id";
  /**
   * String constants that can be used to print messages without redundancy.
   */
  private static final String MESSAGE2 = "Node id must be between 0 to 9999";
  /**
   * String constants that can be used to print messages without redundancy.
   */
  private static final String MESSAGE3 = "Invalid input enter an integer";

  @SuppressWarnings("PMD.SystemPrintln")
  /**
   * Function to display menu and associated code for functionalities[1..9]
   */
  public void displayMenu() {
    System.out.println("Select from given options[1..8]");
    System.out.println("1.Get immediate parents");
    System.out.println("2.Get immediate children");
    System.out.println("3.Get Ancestors");
    System.out.println("4.Get Descendants");
    System.out.println("5.Delete dependency from tree");
    System.out.println("6.Delete node from tree");
    System.out.println("7.Add a new dependency");
    System.out.println("8.Add a new node to tree");
    System.out.println("9.Exit");
  }

  /**
   * This method takes input of node id for which parents are required.
   *
   * @param graph instance of graph class
   */
  @SuppressWarnings("PMD.SystemPrintln")
  public void printImmediateParents(final Graph graph) {
    try (Scanner scanner = new Scanner(System.in)) {
      System.out.println(MESSAGE1);
      int nodeId;
      if (scanner.hasNextInt()) {
        nodeId = scanner.nextInt();
        if (nodeId < 0 || nodeId > 9999) {
          System.out.println(MESSAGE2);
        } else {
          System.out.println("parents:" + graph.getImmediateParents(nodeId));
        }
      } else {

        throw new InvalidInputException(MESSAGE3);
      }

    }
  }

  /**
   * This method is used to find immediate children of the node id.
   *
   * @param graph instance of Graph class
   */
  @SuppressWarnings("PMD.SystemPrintln")
  public void printImmediateChildren(final Graph graph) {
    try (Scanner scanner = new Scanner(System.in)) {
      int nodeId;
      System.out.println(MESSAGE1);
      if (scanner.hasNextInt()) {
        nodeId = scanner.nextInt();
        if (nodeId < 0 || nodeId > 9999) {
          System.out.println(MESSAGE2);
        } else {
          System.out.println("parents:" + graph.getImmediateChildren(nodeId));
        }
      } else {
        throw new InvalidInputException(MESSAGE3);
      }
    }
  }

  /**
   * This method is used to find all ancestors of the node id.
   *
   * @param graph instance of Graph class
   */
  @SuppressWarnings("PMD.SystemPrintln")
  public void printAncestors(final Graph graph) {
    try (Scanner scanner = new Scanner(System.in)) {
      int nodeId;
      System.out.println(MESSAGE1);
      if (scanner.hasNextInt()) {
        nodeId = scanner.nextInt();
        if (nodeId < 0 || nodeId > 9999) {
          System.out.println(MESSAGE2);
        } else {
          System.out.println("parents:" + graph.getAncestors(nodeId));
        }
      } else {
        throw new InvalidInputException(MESSAGE3);
      }
    }
  }

  /**
   * This method is used to find descendants of the node id.
   *
   * @param graph instance of Graph class
   */
  @SuppressWarnings("PMD.SystemPrintln")
  public void printDescendants(final Graph graph) {
    try (Scanner scanner = new Scanner(System.in)) {
      int nodeId;
      System.out.println(MESSAGE1);
      if (scanner.hasNextInt()) {
        nodeId = scanner.nextInt();
        if (nodeId < 0 || nodeId > 9999) {
          System.out.println(MESSAGE2);
        } else {
          System.out.println("children:" + graph.getDescendants(nodeId));
        }
      } else {
        throw new InvalidInputException(MESSAGE3);
      }
    }
  }

  /**
   * This method is used to delete dependency between child and parent node .
   *
   * @param graph instance of Graph class
   */
  @SuppressWarnings("PMD.SystemPrintln")
  public void deleteDependency(final Graph graph) {
    try (Scanner scanner = new Scanner(System.in)) {
      System.out.println("Enter node id of parent");
      int parent;
      int child;
      if (scanner.hasNextInt()) {
        parent = scanner.nextInt();
        if (parent < 0 || parent > 9999) {
          System.out.println(MESSAGE2);
        } else {
          System.out.println("Enter node id of child");
          if (scanner.hasNextInt()) {
            child = scanner.nextInt();
            if (child < 0 || child > 9999) {
              System.out.println(MESSAGE2);
            } else {
              graph.deleteDependency(parent, child);
              System.out.println("Deleted dependency");
            }
          } else {
            throw new InvalidInputException(MESSAGE3);
          }
        }
      } else {
        throw new InvalidInputException(MESSAGE3);
      }
    }
  }

  /**
   * This method is used to delete a particular node id.
   *
   * @param graph instance of Graph class
   */
  @SuppressWarnings("PMD.SystemPrintln")
  public void deleteNode(final Graph graph) {
    System.out.println("Enter node id of node to be deleted");
    try (Scanner scanner = new Scanner(System.in)) {
      int nodeId;
      System.out.println(MESSAGE1);
      if (scanner.hasNextInt()) {
        nodeId = scanner.nextInt();
        if (nodeId < 0 || nodeId > 9999) {
          System.out.println(MESSAGE2);
        } else {
          graph.deleteNode(nodeId);
          System.out.println("Node deleted");
        }
      } else {
        throw new InvalidInputException(MESSAGE3);
      }
    }
  }

  /**
   * This method is used to add dependency between parent and child node.
   *
   * @param graph instance of Graph class
   */
  @SuppressWarnings("PMD.SystemPrintln")
  public void addDependency(final Graph graph) {
    try (Scanner scanner = new Scanner(System.in)) {
      System.out.println("Enter node id of parent");
      int parent;
      int child;
      if (scanner.hasNextInt()) {
        parent = scanner.nextInt();
        if (parent < 0 || parent > 9999) {
          System.out.println(MESSAGE2);
        } else {
          System.out.println("Enter node id of child");
          if (scanner.hasNextInt()) {
            child = scanner.nextInt();
            if (child < 0 || child > 9999) {
              System.out.println(MESSAGE2);
            } else {
              graph.addDependency(parent, child);
              System.out.println("Deleted dependency");
            }
          } else {
            throw new InvalidInputException(MESSAGE3);
          }
        }
      } else {
        throw new InvalidInputException(MESSAGE3);
      }
    }
  }

  /**
   * This method is used to add a new node to graph with no dependencies.
   */
  @SuppressWarnings("PMD.SystemPrintln")
  public void addNode(final Graph graph) {
    try (Scanner scanner = new Scanner(System.in)) {
      int nodeId;
      System.out.println(MESSAGE1);
      if (scanner.hasNextInt()) {
        nodeId = scanner.nextInt();
        if (nodeId < 0 || nodeId > 9999) {
          System.out.println(MESSAGE2);
        } else {
          if (graph.addNode(nodeId)) {

            System.out.println("Added");
          } else {
            System.out.println("Node already exists");
          }
        }
      } else {
        throw new InvalidInputException(MESSAGE3);
      }
    }
  }
  /**
   * This method takes initial input of graph.
   */
  @SuppressWarnings({"PMD.SystemPrintln", "PMD.AvoidInstantiatingObjectsInLoops"})

  public void inputGraph(final Graph graph) {
    System.out.println("Enter number of edges");
    try (Scanner scanner = new Scanner(System.in)) {
      final int n;
      if (scanner.hasNextInt()) {
        n = scanner.nextInt();
      } else {
        throw new InvalidInputException("Invalid input for number of nodes");
      }
      int par;
      int chi;
      for (int i = 0; i < n; i++) {
        System.out.println("Enter parent");
        if (scanner.hasNextInt()) {
          par = scanner.nextInt();
        } else {

          throw new InvalidInputException("Invalid node");
        }
        if (par < 0 || par > 9999) {
          throw new InvalidInputException("Invalid nodeId for parent");
        }

        System.out.println("Enter child");
        if (scanner.hasNextInt()) {
          chi = scanner.nextInt();
        } else {
          throw new InvalidInputException("Invalid node");
        }
        if (chi < 0 || chi > 9999) {
          throw new InvalidInputException("Invalid nodeId for child");
        }
        childrenMap.computeIfAbsent(par, k -> new ArrayList<>());
        parentsMap.computeIfAbsent(chi, k -> new ArrayList<>());
        parentsMap.get(chi).add(par);
        childrenMap.get(par).add(chi);
      }
      graph.setParentsMap(parentsMap);
      graph.setChildrenMap(childrenMap);
    }
  }

  /**
   * This method is used to manage input and output.
   */
  @SuppressWarnings("PMD.SystemPrintln")
  public void startManager() {
    final Graph graph = new Graph();
    inputGraph(graph);
    try (Scanner scanner = new Scanner(System.in)) {
      while (true) {
        displayMenu();
        int choice;
        if (scanner.hasNextInt()) {
          choice = scanner.nextInt();
        } else {
          choice = -1;
          System.out.println("Invalid Input Enter again");
        }
        switch (choice) {
          case 1:
            printImmediateParents(graph);
            break;
          case 2:
            printImmediateChildren(graph);
            break;
          case 3:
            printAncestors(graph);
            break;
          case 4:
            printDescendants(graph);
            break;
          case 5:
            deleteDependency(graph);
            break;
          case 6:
            deleteNode(graph);
            break;
          case 7:
            addDependency(graph);
            break;
          case 8:
            addNode(graph);
            break;
          case 9:
            return;

          default:
            System.out.println("Invalid input ... enter again");
            break;
        }
      }
    }
  }
}