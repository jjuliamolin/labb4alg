
import java.beans.beancontext.BeanContextChild;
import java.util.*;

import java.util.stream.Collectors;


public class PathFinder<V> {

    private DirectedGraph<V> graph;
    private long startTimeMillis;


    public PathFinder(DirectedGraph<V> graph) {
        this.graph = graph;
    }


    public class Result<V> {
        public final boolean success;
        public final V start;
        public final V goal;
        public final double cost;
        public final List<V> path;
        public final int visitedNodes;
        public final double elapsedTime;

        public Result(boolean success, V start, V goal, double cost, List<V> path, int visitedNodes) {
            this.success = success;
            this.start = start;
            this.goal = goal;
            this.cost = cost;
            this.path = path;
            this.visitedNodes = visitedNodes;
            this.elapsedTime = (System.currentTimeMillis() - startTimeMillis) / 1000.0;
        }

        public String toString() {
            String s = "";
            s += String.format("Visited nodes: %d\n", visitedNodes);
            s += String.format("Elapsed time: %.1f seconds\n", elapsedTime);
            if (success) {
                s += String.format("Total cost from %s -> %s: %s\n", start, goal, cost);
                s += "Path: " + path.stream().map(x -> x.toString()).collect(Collectors.joining(" -> "));
            } else {
                s += String.format("No path found from %s", start);
            }
            return s;
        }
    }


    public Result<V> search(String algorithm, V start, V goal) {
        startTimeMillis = System.currentTimeMillis();
        switch (algorithm) {
        case "random":   return searchRandom(start, goal);
        case "dijkstra": return searchDijkstra(start, goal);
        case "astar":    return searchAstar(start, goal);
        }
        throw new IllegalArgumentException("Unknown search algorithm: " + algorithm);
    }


    public Result<V> searchRandom(V start, V goal) {
        int visitedNodes = 0;
        LinkedList<V> path = new LinkedList<>();
        double cost = 0.0;
        Random random = new Random();

        V current = start;
        path.add(current);
        while (current != null) {
            visitedNodes++;
            if (current.equals(goal)) {
                return new Result<>(true, start, current, cost, path, visitedNodes);
            }

            List<DirectedEdge<V>> neighbours = graph.outgoingEdges(start);
            if (neighbours == null || neighbours.size() == 0) {
                break;
            } else {
                DirectedEdge<V> edge = neighbours.get(random.nextInt(neighbours.size()));
                cost += edge.weight();
                current = edge.to();
                path.add(current);
            }
        }
        return new Result<>(false, start, null, -1, null, visitedNodes);
    }




    public Result<V> searchDijkstra(V start, V goal) {
        /********************
         * TODO: Task 1
         ********************/

        int visitedNodes = 0;
        List<DirectedEdge<V>> neighbours;
        Set visited = new HashSet<V>();


        HashMap<V,Double> distTo = new HashMap<>(); //Key: V   Value: double      --> distTo("A") = 0.0 , distTo("B") = 3.0
        HashMap<V,DirectedEdge<V>> edgeTo = new HashMap<>(); // Key: V    Value: edge
        PriorityQueue<V> toBeVisited = new PriorityQueue<>(new Comparator<V>() {
            @Override
            public int compare(V node1, V node2) {
                Double dist1 = distTo.get(node1);
                Double dist2 = distTo.get(node2);
                return dist1.compareTo(dist2); //todo kolla att detta är rätt. eller ska det vara tvärtom ?
            }
        });

        /*initializing*/
        toBeVisited.add(start);
        neighbours = graph.outgoingEdges(start);
        V currentNode = start;
        distTo.put(start,0.0);



        while(!toBeVisited.isEmpty()){
            currentNode = toBeVisited.remove(); // raderar det elementet med prio (comparatorn avgör)

            if(!visited.contains(currentNode)) {
                visited.add(currentNode);

                //did we find the goal?
                if(currentNode.equals(goal)) { //TODO find path and return result
                    ArrayList<V> path= new ArrayList<>();
                    V node=currentNode;
                    while(!node.equals(start)){
                        path.add(node);
                        node = edgeTo.get(node).from();
                    }
                    System.out.println("success");
                    return new Result<V>(true, start, goal, distTo.get(goal),path, visited.size());

                }


                neighbours = graph.outgoingEdges(currentNode);
                for (DirectedEdge<V> edge : neighbours) {  /* RELAX */
                    V v = edge.from(); //currentNode
                    V w = edge.to();

                    DirectedEdge<V> incomingEdge = edgeTo.get(currentNode);
                    System.out.println(incomingEdge);
                    System.out.println(edge);
                  if(incomingEdge==null || !incomingEdge.equals(edge.reverse()))  {
                      //directed edges, need to reverse to compare.
                      //only check outgoingEdges.

                      if (distTo.containsKey(w)) { //om det redan finns en registrerad väg till nya noden
                          if (distTo.get(w) > distTo.get(v) + edge.weight()) { //om distTo nya noden är mindre än nuvarande värdet (kortare väg har hittats)
                              Double newDist = distTo.get(v) + edge.weight();
                              distTo.put(w, newDist); // uppdatera den nya (kortare vägen)
                              edgeTo.put(w,edge);
                              toBeVisited.add(w); //lägg till nästa node i PQ.
                          }

                      } else { //if distTo(w)==null
                          distTo.put(w, distTo.get(v) + edge.weight());
                          edgeTo.put(w,edge);
                          toBeVisited.add(w); //lägg till nästa node i PQ.
                      }
                  }

                }

            }

        }


        return new Result<>(false, start, null, -1, null, visitedNodes);
    }
    

    public Result<V> searchAstar(V start, V goal) {
        int visitedNodes = 0;
        /********************
         * TODO: Task 3
         ********************/
        return new Result<>(false, start, null, -1, null, visitedNodes);
    }

}
