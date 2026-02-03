import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Solution {
    static int WHITE = 1;
    static int GRAY = 2;
    static int BLACK = 3;

    boolean isPossible;
    Map<Integer, Integer> color;
    Map<Integer, List<Integer>> adjacencyList;
    List<Integer> topologicalOrder;

    private void init(int numCourses) {
        this.isPossible = true;
        this.color = new HashMap<>();
        this.adjacencyList = new HashMap<>();
        this.topologicalOrder = new ArrayList<>();

        // By default all vertces are white
        for (int i = 0; i < numCourses; i++) {
            this.color.put(i, WHITE);
        }
    }
    
    private void dfs(int node) {
        // Donot recurse further if there is a cycle already
        if (!this.isPossible) {
            return;
        }
        // Start the recursion
        this.color.put(node, GRAY);

        /**
         * Traverse the neighbouring vertices
         */
        for (Integer neighbour : this.adjacencyList.getOrDefault(node, new ArrayList<>())) {
            if (this.color.get(neighbour) == WHITE) {
                this.dfs(neighbour);
            } else if (this.color.get(neighbour) == GRAY) {
                // An edge with a gray vertex represents a cycle
                this.isPossible = false;
            }
        }
        /**
         * recursion ends, we mark it as black
         */
        this.color.put(node, BLACK);
        this.topologicalOrder.add(node);
    }
    

    public int[] findOrder(int numCourses, int[][] prerequisites) {
        
        this.init(numCourses);
        
        /**
         * Create the adjacency list representation of the graph
         */
        for (int i = 0; i < prerequisites.length; i++) {
            int dest = prerequisites[i][0];
            int src = prerequisites[i][1];
            List<Integer> lst = adjacencyList.getOrDefault(src, new ArrayList<>());
            lst.add(dest);
            adjacencyList.put(src, lst);
        }
        
        /**
         * If the number of nodes is unprocessed(WHITE), then call dfs on it
         */
        for (int i = 0; i < numCourses; i++) {
            if (this.color.get(i) == WHITE) {
                this.dfs(i);
            }
        }
        
        int[] order;
        if (this.isPossible) {
            order = new int[numCourses];
            for (int i = 0; i < numCourses; i++) {
                order[i] = this.topologicalOrder.get(numCourses - i - 1);
            }            
        } else {
            order = new int[0];
        }
        return order;
    }

}
