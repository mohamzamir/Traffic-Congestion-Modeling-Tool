import big.data.DataSourceException;

import java.util.List;
import java.util.Scanner;

/**
 * Driver class for the application
 */
public class IslandDesigner {
    // Store scanner object
    private static Scanner sc = new Scanner(System.in);
    // Store IslandNetwork
    private static IslandNetwork network;


    /**
     * main method for run the program
     */
    public static void main(String[] args) {

        System.out.println("Welcome to the Island Designer, because, when you're trying to stay above water, Seas get degrees!");
        try {
            System.out.print("please enter an url: ");
            String url = sc.nextLine();
            network=IslandNetwork.loadFromFile(url);
        }catch (DataSourceException e)
        {
            System.out.println(e.getMessage());
            return;
        }

        boolean isExit=false;
        printMenu();
        while (!isExit)
        {
            System.out.print("Please select an option: ");
            String input = sc.nextLine();
            switch (input.toUpperCase()) {
                case "D":
                    depthFirstSearch();
                    break;
                case "F":
                    maximumFlow();
                    break;
                case "S":
                    shortestPath();
                    break;
                case "Q":
                    isExit=true;
                    break;
                default:
                    System.out.println("Invalid input!");
                    break;
            }
        }
        System.out.println("You can go your own way! Goodbye!");
    }

    /**
     * Method for shortest path user input option
     */
    private static void shortestPath() {
        System.out.print("Please enter a starting node: ");
        String starting = sc.nextLine();
        System.out.print("Please enter a destination node: ");
        String destination = sc.nextLine();
        try {
            network.djikstra(starting,destination);
        } catch (NoRouteException | NullPointerException e) {
            System.out.println(e.getMessage());
        }
    }

    /**
     * Method for maximum flow user input option
     */
    private static void maximumFlow() {
        System.out.print("Please enter a starting city: ");
        String startingCity = sc.nextLine();
        System.out.print("Please enter a destination: ");
        String destination = sc.nextLine();
        try {
            network.maxFlow(startingCity,destination);
        }catch (NullPointerException e)
        {
            System.out.println(e.getMessage());
        }

    }

    /**
     * Method for depthFirstSearch user input option
     */
    private static void depthFirstSearch() {
        System.out.print("Please enter a starting city: ");
        String name = sc.nextLine();
        try {
            List<String> paths = network.dfs(name);
            System.out.println("DFS Starting From "+name+":");
            for (int i = 0; i < paths.size(); i++) {
                String path = paths.get(i);
                System.out.print(path);
                if (i<paths.size()-1)
                    System.out.print(", ");
            }
            System.out.println();
        } catch (NoRouteException e) {
            System.out.println(e.getMessage());
        }
    }

    /**
     * print menu
     */
    private static void printMenu()
    {
        System.out.println("Menu:");
        System.out.println("\tD) Destinations reachable (Depth First Search)");
        System.out.println("\tF) Maximum Flow");
        System.out.println("\tS) Shortest Path");
        System.out.println("\tQ) Quit");
    }
}
