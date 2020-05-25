package Algo;

import edu.princeton.cs.introcs.StdOut;
import edu.princeton.cs.introcs.Stopwatch;

import java.util.LinkedList;
import java.util.Random;
import java.util.Scanner;

//Name- P.C.C.Peiris
//UOW ID - w1714892

public class MaxFlow {
    int vertices; // number of vertices in the flow network

    public MaxFlow(int vertices) {
        this.vertices = vertices;
    }

    public MaxFlow() {
    }

    //pass residual graph to bfs algorithm to find paths
    boolean bfs(int residualGraph[][], int source, int sink, int parentArray[]) {
        // Create a visited array and mark all vertices as not visited

        boolean visitedArray[] = new boolean[vertices]; //create a boolean array called visitedArray
        for (int i = 0; i < vertices; ++i)
            visitedArray[i] = false; //define all the values in visited array to false


        LinkedList<Integer> queue = new LinkedList<Integer>(); //define a linked list to create a queue
        queue.add(source); //source node add to the list
        visitedArray[source] = true; //set source to true
        parentArray[source] = -1; //set source value to -1 because there is no parent to the source node

        // Breadth first search loop
        while (queue.size() != 0) {
            int u = queue.poll();

            for (int v = 0; v < vertices; v++) {
                if (visitedArray[v] == false && residualGraph[u][v] > 0) {
                    queue.add(v); // add the node to the queue linked list
                    parentArray[v] = u; //store path with parent nodes to each node
                    visitedArray[v] = true; //if each node had visited, set it to true
                }
            }
        }

        // return true if we reached to the sink node
        return (visitedArray[sink] == true);
    }

    //return the maximum possible flow between source and the sink
    int fordFulkerson(int[][] graph, int source, int sink) {
        int u, v;


        int residualGraph[][] = new int[vertices][vertices]; //define a residual graph 2d array
        int maximumFlowGraph[][] = new int[vertices][vertices]; // define a maximum flow 2d array

        for (u = 0; u < vertices; u++)
            for (v = 0; v < vertices; v++)
                residualGraph[u][v] = graph[u][v];

        // define the parent array which use to store parent nodes in the path
        int parentArray[] = new int[vertices];

        int max_flow = 0;  // Assign max flow to zero

        //find that there is  a path to the source from to the sink
        while (bfs(residualGraph, source, sink, parentArray)) {
            //set path flow to the max value
            int path_flow = Integer.MAX_VALUE;
            for (v = sink; v != source; v = parentArray[v]) {
                u = parentArray[v];
                // get the minimum value of the given path flow to get the bottle neck capacity
                path_flow = Math.min(path_flow, residualGraph[u][v]);
            }

            for (v = sink; v != source; v = parentArray[v]) {
                u = parentArray[v];
                residualGraph[u][v] -= path_flow; //subtract the capacities in the path
                residualGraph[v][u] += path_flow; // set the bottle neck capacities to reverse
                maximumFlowGraph[u][v] += path_flow; // set the bottle neck capacities to reverse to get the path with the distributed flows
            }

            //to print residualGraph
            System.out.println("\n" + "-------Residual graph----------");
            for (u = 0; u < vertices; u++) {
                for (v = 0; v < vertices; v++) {
                    System.out.print(residualGraph[u][v] + "  ");
                }
                System.out.println();
            }

            // update the path flow
            max_flow += path_flow;

        }
        // print the maximum flow path graph
        System.out.println("\n" + "-------Maximum Flow Path----------");
        for (u = 0; u < vertices; u++) {
            for (v = 0; v < vertices; v++) {
                System.out.print(maximumFlowGraph[u][v] + "  ");
            }
            System.out.println();
        }
        // Return the overall possible flow
        return max_flow;
    }

    private void maxFlowRandom() {


        Random random = new Random();
       // int rand_vertices = 50 + random.nextInt(100); //the range of nodes in flow network 6 -12
        Scanner sc = new Scanner(System.in);
        System.out.println("\n" + "Please enter number of vertices ");
        while (!sc.hasNextInt()) { //check for valid inputs
            System.out.println("Please enter a valid integer");
            sc.next();
        }
        int rand_vertices = sc.nextInt();
        System.out.println("Number of Vertices = " + rand_vertices);
        int network[][] = new int[rand_vertices][rand_vertices];
        System.out.println("* Diagonal entries are zero in an Adjacency Matrix ");
        System.out.println("* Program will automatically set diagonal entries to zero ");
        System.out.println("* Let's assume this is a complete directed graph");
        System.out.println("* Source node is 0 (s = 0) and Sink node(t) is number of vertices - 1" + "\n");

        System.out.println("-----------Adjacency Matrix--------------");
        for (int u = 0; u < rand_vertices; u++) {
            for (int v = 0; v < rand_vertices; v++) {
                network[u][v] = 5 + random.nextInt(50); //capacity is in the range between 5 and 20
                if (v == u) {
                    network[u][v] = 0;
                }
                System.out.print(" " + network[u][v] + " "); // print the Adjacency Matrix
            }
            System.out.println();
        }
        int sink_node = rand_vertices - 1;
        MaxFlow maxFlowRandom = new MaxFlow(rand_vertices);
        Stopwatch timer = new Stopwatch();

        System.out.println("\n" + "The maximum possible flow is " +
                maxFlowRandom.fordFulkerson(network, 0, sink_node));

        StdOut.println("elapsed time = " + timer.elapsedTime());
    }

    private void maxFlowUserInput() {
        Scanner sc = new Scanner(System.in);
        System.out.println("\n" + "Please enter number of vertices at least 6 ");
        while (!sc.hasNextInt()) { //check for valid inputs
            System.out.println("Please enter a valid integer");
            sc.next();
        }
        int input = sc.nextInt();
        //at least there are 6 nodes
        while (input < 6) {
            System.out.println("Please enter number of vertices at least 6");
            // check for valid inputs
            while (!sc.hasNextInt()) {
                System.out.println("Please enter a valid integer");
                sc.next();
            }
            input = sc.nextInt();
        }

        int network[][] = new int[input][input]; //Define a 2d array

        System.out.println("-----------Adjacency Matrix--------------" + "\n");
        System.out.println("* Diagonal entries are zero in an Adjacency Matrix ");
        System.out.println("* Program will automatically set diagonal entries to zero ");
        System.out.println("* Let's assume this is a complete directed graph");
        System.out.println("* Source node is 0 (s = 0)" + "\n");

        //set capacity in two dimensional array
        for (int u = 0; u < input; u++) {
            for (int v = 0; v < input; v++) {
                // set diagonal entries to zero
                if (v == u) {
                    network[u][v] = 0;
                } else {
                    System.out.println("Please enter capacity for each edge ");
                    System.out.println(u + "-->" + v);
                    // check for valid inputs
                    while (!sc.hasNextInt()) {
                        System.out.println("Please enter a valid integer");
                        sc.next();
                    }
                    int capacity = sc.nextInt();
                    network[u][v] = capacity;  // set a capacity to each edge
                }
            }
        }
        System.out.println("\n" + "Please define the sink node : ");
        while (!sc.hasNextInt()) { //check for valid inputs
            System.out.println("Please enter a valid integer");
            sc.next();
        }
        int sink_node = sc.nextInt(); //define the sink node
        while (sink_node == 0 || sink_node >= input) {  //sink node can't be zero or greater than or similar to number of vertices
            System.out.println("Please enter number between 1 and "+input);
            // check for valid inputs
            while (!sc.hasNextInt()) {
                System.out.println("Please enter a valid integer");
                sc.next();
            }
            sink_node = sc.nextInt();
        }

        MaxFlow maxFlowUserInput = new MaxFlow(input);

        //pass the flow network and sink node as parameters to  fordFulkerson and print the maximum possible flow
        System.out.println("The maximum possible flow is " +
                maxFlowUserInput.fordFulkerson(network, 0, sink_node) + "\n");

        int repeat = 1;

        System.out.println("Press 1 for edit this flow network");
        System.out.println("Press 0 for return to Main menu");

        //check for valid inputs
        while (!sc.hasNextInt()) {
            System.out.println("Please enter a valid integer");
            sc.next();
        }

        //modify the flow network
        int edit = sc.nextInt();
        while (repeat != 0) {
            if (edit == 1) {
                System.out.println("* If you want delete an edge please set it's capacity/weight to zero");
                System.out.println("* Please enter the edge you want to delete/ modify");

                System.out.println("u");
                while (!sc.hasNextInt()) { //check for valid inputs
                    System.out.println("Please enter a valid integer");
                    sc.next();
                }
                int uInput = sc.nextInt();
                while (uInput>=input) {
                    System.out.println("Please enter number between 1 and number less than "+input);
                    // check for valid inputs
                    while (!sc.hasNextInt()) {
                        System.out.println("Please enter a valid integer");
                        sc.next();
                    }
                    uInput = sc.nextInt();
                }
                System.out.println("v");
                while (!sc.hasNextInt()) { //check for valid inputs
                    System.out.println("Please enter a valid integer");
                    sc.next();
                }
                int vInput = sc.nextInt();
                while (vInput>=input) {
                    System.out.println("Please enter number between 1 and number less than "+input);
                    // check for valid inputs
                    while (!sc.hasNextInt()) {
                        System.out.println("Please enter a valid integer");
                        sc.next();
                    }
                    vInput = sc.nextInt();
                }
                System.out.println("Enter New capacity/weight: ");
                while (!sc.hasNextInt()) { //check for valid inputs
                    System.out.println("Please enter a valid integer");
                    sc.next();
                }
                int newCap = sc.nextInt();

                //assign values to the network
                network[uInput][vInput] = newCap;
                System.out.println(uInput + "-->" + vInput + " = " + newCap);

                //pass the new flow network and sink node as parameters to  fordFulkerson and print the maximum possible flow
                System.out.println("The maximum possible flow is " +
                        maxFlowUserInput.fordFulkerson(network, 0, sink_node) + "\n");

            } else if (edit == 0) {
                break;
            } else {
                System.out.println("Invalid Input!!! ");
            }
            System.out.println("Press 1 to another attempt");
            System.out.println("Press 0 to return to main menu");
            repeat = sc.nextInt();
        }


    }

    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);
        //Create an object to access methods
        MaxFlow maxFlow = new MaxFlow();
        boolean exit = false;
        while (!exit) {
            System.out.println("Press number '1' for Random Flow Network ");
            System.out.println("Press number '2' for Your own Flow Network ");
            System.out.println("Press number '3' for exit the program ");
            while (!sc.hasNextInt()) { //check for valid inputs
                System.out.println("Please enter a valid integer");
                sc.next();
            }
            int choice = sc.nextInt();
            switch (choice) {
                case 1:
                    maxFlow.maxFlowRandom(); // Random flow network
                    break;
                case 2:
                    maxFlow.maxFlowUserInput(); // flow network which user can create
                    break;
                case 3:
                    exit = true;
                    break;
            }
        }
    }

}
