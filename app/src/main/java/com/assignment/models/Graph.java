package com.assignment.models;

import com.assignment.exceptions.InvalidInputException;

import java.util.*;

/**
 * Graph class stores all the functionalities and attributes of graph.
 */
public class Graph {
    /**
     * Message when invalid node id is provided.
     */
    private final static String MESSAGE="node id does not exist";
    /**
     * Stores directed graph of parents.
     */
    @SuppressWarnings("PMD.UseConcurrentHashMap")
    private final Map<Integer,ArrayList<Integer>>parent= new HashMap<>();
    /**
     * Stores directed graph of children.
     */
    @SuppressWarnings("PMD.UseConcurrentHashMap")
    private final Map<Integer,ArrayList<Integer>>child= new HashMap<>();
    /**
     * Stores details of each node.
     */
    private final List<Node>nodes=new ArrayList<>();

    public Map<Integer, ArrayList<Integer>> getChild() {
        return child;
    }

    public Map<Integer, ArrayList<Integer>> getParent() {
        return parent;
    }

    public List<Node> getNodes() {
        return nodes;
    }

    /**
     * Constructor to initialize parent,child and nodes arraylist.
     */
    public Graph(){
        for (int i = 0; i < 10_000; i++) {
//            parent.put(i, new ArrayList<>());
//            child.put(i, new ArrayList<>());
            nodes.add(new Node(i));
        }
    }

    /**
     * This method takes initial input of graph.
     */
    @SuppressWarnings({"PMD.SystemPrintln","PMD.AvoidInstantiatingObjectsInLoops"})

    public void inputGraph(){
        System.out.println("Enter number of edges");
        try (Scanner scanner = new Scanner(System.in)) {
            final int n;
            if(scanner.hasNextInt()){
                n= scanner.nextInt();
            }else{
                throw new InvalidInputException("Invalid input for number of nodes");
            }
            int par;
            int chi;
            for (int i = 0; i < n; i++) {
                System.out.println("Enter parent");
                if(scanner.hasNextInt()){
                    par= scanner.nextInt();
                }else{

                    throw new InvalidInputException("Invalid node");
                }
                if (par < 0 || par > 9999) {
                    throw new InvalidInputException("Invalid nodeId for parent");
                }

                System.out.println("Enter child");
                if(scanner.hasNextInt()){
                    chi= scanner.nextInt();
                }
                else{
                    throw new InvalidInputException("Invalid node");
                }
                if (chi < 0 || chi > 9999) {
                    throw new InvalidInputException("Invalid nodeId for child");
                }
                child.computeIfAbsent(par, k -> new ArrayList<>());
                parent.computeIfAbsent(chi, k -> new ArrayList<>());
                parent.get(chi).add(par);
                child.get(par).add(chi);
            }
        }
    }

    /**
     * This method returns immediate parents list
     * @param nodeId for which parents are required
     * @return list of parents
     */
    public List<Integer>getImmediateParents(final int nodeId){
        if(!(parent.containsKey(nodeId) || child.containsKey(nodeId))){
            throw new InvalidInputException(MESSAGE);
        }
        return parent.get(nodeId);
    }

    /**
     * This method returns immediate children list
     * @param nodeId for which children are required
     * @return list of parents
     */
    public List<Integer>getImmediateChildren(final int nodeId){
        if(!(parent.containsKey(nodeId) || child.containsKey(nodeId))){
            throw new InvalidInputException(MESSAGE);
        }
        return child.get(nodeId);
    }
    
    private void dfs(final int nodeId,final List<Boolean>visited,final Map<Integer,ArrayList<Integer>>neighbor,final List<Integer>res){
        visited.set(nodeId,true);
        res.add(nodeId);
        if(neighbor.get(nodeId)==null){
            neighbor.put(nodeId,new ArrayList<>());
        }
        for (final Integer c: neighbor.get(nodeId)) {
            if(!visited.get(c)){
                dfs(c,visited,neighbor,res);
            }
        }
    }

    /**
     * This method returns the list of ancestors.
     * @param nodeId for which ancestors are required
     * @return  list of ancestors
     */
    public List<Integer>getAncestors(final int nodeId){
        if(!(parent.containsKey(nodeId) || child.containsKey(nodeId))){
            throw new InvalidInputException(MESSAGE);
        }
            final ArrayList<Integer>res=new ArrayList<>();
            final ArrayList<Boolean>visited=new ArrayList<>(Collections.nCopies(10_000, false));

        dfs(nodeId,visited,parent,res);
        final Integer x= nodeId;
        res.remove(x);
        return res;
    }

    /**
     * This method returns the list of descendants.
     * @param nodeId for which descendants are required
     * @return list of descendants
     */
    public List<Integer>getDescendants(final int nodeId){
        if(!(parent.containsKey(nodeId) || child.containsKey(nodeId))){
            throw new InvalidInputException(MESSAGE);
        }
        final ArrayList<Integer>res=new ArrayList<>();
        final ArrayList<Boolean>visited=new ArrayList<>(Collections.nCopies(10_000, false));

        dfs(nodeId,visited,child,res);
        final Integer x= nodeId;
        res.remove(x);
        return res;
    }

    /**
     * This method is used to delete dependency between parent and child.
     * @param par parent node id
     * @param chi child node id
     */
    public void deleteDependency(final int par,final int chi){
        if(!(parent.containsKey(par) || child.containsKey(par))){
            throw new InvalidInputException(MESSAGE);
        }
        if(!(parent.containsKey(chi) || child.containsKey(chi))){
            throw new InvalidInputException(MESSAGE);
        }
        final Integer p= par;
        final Integer c= chi;
        parent.get(chi).remove(p);
        child.get(par).remove(c);
    }

    /**
     * This method is used to delete ode and all of its dependencies
     * @param nodeId to be deleted
     */
    public void deleteNode(final int nodeId){
        if(!(parent.containsKey(nodeId) || child.containsKey(nodeId))){
            throw new InvalidInputException(MESSAGE);
        }
        for(final int c:parent.get(nodeId)){
            final Integer obj= nodeId;
            child.get(c).remove(obj);
        }
        parent.get(nodeId).clear();
        for(final int c:child.get(nodeId)){
            final Integer obj= nodeId;
            parent.get(c).remove(obj);
        }
        child.get(nodeId).clear();
    }
    @SuppressWarnings("PMD.OnlyOneReturn")
    private boolean checkCycle(final int nodeId,final List<Boolean>visited,final List<Boolean>recStack){
        if(recStack.get(nodeId)){
            return true;
        }
        if(visited.get(nodeId)){
            return false;
        }
        visited.set(nodeId,true);
        recStack.set(nodeId,true);
        for(final int c:parent.get(nodeId)){
            if(checkCycle(c,visited,recStack)){
                return true;
            }
        }
        recStack.set(nodeId,false);
        return false;
    }

    /**
     * This method adds a dependency between a given parent and child node.
     * @param par parent node
     * @param chi child node
     * @return whether dependencies possible or not
     */
    @SuppressWarnings("PMD.OnlyOneReturn")
    public boolean addDependency(final int par,final int chi){
        if(!(parent.containsKey(par) || child.containsKey(par))){
            throw new InvalidInputException(MESSAGE);
        }
        if(!(parent.containsKey(chi) || child.containsKey(chi))){
            throw new InvalidInputException(MESSAGE);
        }
        parent.get(chi).add(par);
        final ArrayList<Boolean>visited=new ArrayList<>(Collections.nCopies(10_000, false));
        final ArrayList<Boolean>recStack=new ArrayList<>(Collections.nCopies(10_000, false));
        if(checkCycle(par,visited,recStack)){
            parent.get(chi).remove(Integer.valueOf(par));
            return false;
        }else{
            child.get(par).add(chi);
            return true;
        }
    }

    /**
     * This method adds a new node to graph
     * @param nodeId node Id of the node to be created
     * @return
     */
    @SuppressWarnings("PMD.OnlyOneReturn")
    public boolean addNode(final int nodeId){

        if(parent.containsKey(nodeId)){
            return false;
        }
        parent.put(nodeId,new ArrayList<>());
        child.put(nodeId,new ArrayList<>());
        return true;
    }
}