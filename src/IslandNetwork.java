import big.data.DataSource;

import java.util.*;

/**
 * This class has all implementation (DFS, Djikstra, Max Flow)
 */
public class IslandNetwork {
    // Store city in hashmap
    private HashMap<String, City> graph;

    /**
     * constructor for initialize fields
     */
    public IslandNetwork() {
        graph = new HashMap<>();
    }

    /**
     * load and create IslandNetwork from file or url
     * @param url file or website link
     * @return created IslandNetwork
     */
    public static IslandNetwork loadFromFile(String url) {
        IslandNetwork network = new IslandNetwork();
        DataSource ds = DataSource.connectXML(url);
        ds.load();
        String cityNamesStr = ds.fetchString("cities");
        String[]
                cityNames = cityNamesStr.substring(1, cityNamesStr.length() - 1).replace("\"", "").split(",");
        List<String> cities = new ArrayList<>(List.of(cityNames));
        Collections.sort(cities);
        System.out.println("Map loaded.");
        System.out.println("Cities:");
        System.out.println("----------------------");
        for (String name : cities) {
            City city = new City(name.trim());
            network.addCity(city.getName(), city);
            System.out.println(name);
        }

        String roadNamesStr = ds.fetchString("roads");
        String[]
                roadNames = roadNamesStr.substring(1, roadNamesStr.length() - 1).split("\",\"");
        System.out.println("Road\t\tCapacity");
        System.out.println("--------------------------------");
        for (String road : roadNames) {
            String replace = road.replace('"', ' ').trim();
            String[] data = replace.split(",");
            String src = data[0];
            String dst = data[1];
            int capacity = Integer.parseInt(data[2]);
            try {
                network.getCity(src).addNeighbour(dst, capacity);
            }catch (NullPointerException e)
            {
                System.out.println(e.getMessage());
            }
            System.out.println(src + " to " + dst + "\t\t" + capacity);
        }
        return network;
    }

    /**
     * add city to graph
     * @param name city name
     * @param city city
     */
    public void addCity(String name, City city) {
        graph.put(name, city);
    }

    /**
     * get city from graph using it's name
     * @param name city name
     * @return city
     */

    public City getCity(String name) throws NullPointerException{
        City city = graph.get(name);
        if (city == null)
            throw new NullPointerException("No city found for "+name);
        return city;
    }

    /**
     * find maximum network flow from any node to any other node
     * @param from starting node
     * @param to destination node
     */
    public void maxFlow(String from, String to) throws NullPointerException{
        if (getCity(from) == null)
            throw new NullPointerException("No city found for "+from);
        if (getCity(to) == null)
            throw new NullPointerException("No city found for "+to);
        resetTempCapacity();
        resetDiscoveredVisited();
        List<List<String>> totalPaths = new ArrayList<>();
        maxFlowHelper(totalPaths, from, to);

        for (List<String> totalPath : totalPaths) {
            totalPath.add(0,from);
        }
        try {
            buildPath(totalPaths);
        } catch (NoRouteException | NullPointerException e) {
            System.out.println(e.getMessage());
        }
    }

    /**
     * helper method for print list
     * @param list given list
     */
    private void printList(List<String> list)
    {
        for (int i = 0; i < list.size(); i++) {
            System.out.print(list.get(i));
            if (i<list.size()-1)
                System.out.print("->");
        }
    }

    /**
     * helper method for max flow
     * @param allPaths total path between two node (using depth first search for find path)
     */
    private void buildPath(List<List<String>> allPaths) throws NoRouteException,NullPointerException {
        if (allPaths.isEmpty())
        {
            throw new NoRouteException("No route available!");
        }

        System.out.println("Routing:");
        List<List<String>> validPaths=new ArrayList<>();
        int totalFlow=0;
        for (List<String> allPath : allPaths) {
            int min = Integer.MAX_VALUE;
            for (int i = 1; i < allPath.size(); i++) {
                String currName = allPath.get(i);
                String prevName = allPath.get(i - 1);
                City city = getCity(prevName);
                int value = city.getTempNeighbors().get(currName);

                if (value < min)
                    min = value;
            }
            for (int i = 1; i < allPath.size(); i++) {
                String currName = allPath.get(i);
                String prevName = allPath.get(i - 1);
                City city = getCity(prevName);
                int value = city.getTempNeighbors().get(currName);
                city.getTempNeighbors().replace(currName, value - min);
            }
            validPaths.add(new ArrayList<>());
            validPaths.get(validPaths.size() - 1).add(allPath.get(0));
            boolean success = true;
            if (min<=0)
                continue;
            for (int i = 1; i < allPath.size(); i++) {
                String currName = allPath.get(i);
                String prevName = allPath.get(i - 1);
                City city = getCity(prevName);
                int value = city.getTempNeighbors().get(currName);

                if (value >= 0) {
                    validPaths.get(validPaths.size() - 1).add(currName);
                } else {
                    validPaths.remove(validPaths.size() - 1);
                    success = false;
                    break;
                }
            }
            if (success) {

                printList(validPaths.get(validPaths.size() - 1));
                System.out.println(": "+min);
                totalFlow+=min;
            }
        }
        System.out.println("Maximum Flow: " + totalFlow);
    }

    /**
     * another helper method for max flow
     * @param totalPaths total available path
     * @param from staring city name
     * @param dst destination city name
     */
    public void maxFlowHelper(List<List<String>> totalPaths, String from, String dst) {
        List<String> paths = new ArrayList<>();
        findPathRecursively(totalPaths,from,dst, paths);
    }

    /**
     * another helper method for max flow (find path using DFS)
     * @param totalPaths total available path
     * @param from staring city name
     * @param dst destination city name
     * @param paths current path
     */
    private void findPathRecursively(List<List<String>> totalPaths, String from, String dst, List<String> paths) {
        City city = getCity(from);
        city.setVisited(true);
        Set<String> neighboursSet = city.getNeighbors().keySet();
        List<String> neighbours = new ArrayList<>(neighboursSet);
        Collections.sort(neighbours);
        for (String v : neighbours) {
            City c = getCity(v);
            if (!c.isDiscovered()) {
                paths.add(v);
                if (v.equals(dst)) {
                    totalPaths.add(new ArrayList<>(paths));
                }
                else
                {
                    c.setDiscovered(true);
                }
                findPathRecursively(totalPaths, v, dst,paths);
                c.setDiscovered(false);
                paths.remove(v);
            }
        }
    }


    /**
     * Depth First Search method
     * @param from starting city name
     * @return paths
     */
    public List<String> dfs(String from) throws NoRouteException {
        resetDiscoveredVisited();
        List<String> paths = new ArrayList<>();
        try {
            dfsHelper(from, paths);
        }catch (NullPointerException e)
        {
            System.out.println(e.getMessage());
        }
        if (paths.isEmpty())
            throw new NoRouteException("Destination not reachable!");
        return paths;
    }

    /**
     * helper method for dfs
     * @param from starting city name
     * @param paths paths
     */
    private void dfsHelper(String from, List<String> paths) {
        City city = getCity(from);
        city.setVisited(true);
        Set<String> neighboursSet = city.getNeighbors().keySet();
        List<String> neighbours = new ArrayList<>(neighboursSet);
        Collections.sort(neighbours);
        for (String v : neighbours) {
            City c = getCity(v);
            if (!c.isDiscovered()) {
                c.setDiscovered(true);
                paths.add(v);
                dfsHelper(v, paths);
            }
        }
    }


    /**
     * find shortest path using djikstra
     * @param from starting city name
     * @param to destination city name
     */

    public void djikstra(String from, String to) throws NoRouteException {
        resetDiscoveredVisited();
        List<String> paths = new ArrayList<>();
        HashMap<String, Integer> distance = new HashMap<>();
        HashMap<String, String> parent = new HashMap<>();
        for (Map.Entry<String, City> entry : graph.entrySet()) {
            distance.put(entry.getKey(), Integer.MAX_VALUE);
            parent.put(entry.getKey(), "");
        }
        distance.replace(from, 0);
        try {
            djikstraHelper(from, distance, parent);
        }catch (NullPointerException e)
        {
            System.out.println(e.getMessage());
        }

        String dst = to;
        while (true) {
            String s = parent.get(dst);
            if (s==null)
                throw new NullPointerException("No city found for "+dst);
            if (s.isBlank()) {
                paths.clear();
                break;
            }
            if (s.equals(from)) {
                paths.add(0, from);
                break;
            }
            paths.add(0, s);
            dst = s;
        }

        if (paths.isEmpty()) {
            throw new NoRouteException("No shortest path available!");
        }
        paths.add(to);
        System.out.print("Path: ");
        printList(paths);
        System.out.println();
        System.out.println("Cost: "+findCost(paths));
    }
    /**
     * calculate total cost for shortest path
     * @param paths available paths
     * @return cost
     */
    private int findCost(List<String> paths)
    {
        int cost=0;
        for (int i = 1; i < paths.size(); i++) {
            City city = getCity(paths.get(i-1));
            int value = city.getNeighbors().get(paths.get(i));
            cost+=value;
        }
        return cost;
    }

    /**
     * helper method for djikstra
     * @param from starting city name
     * @param distance distance hashmap
     * @param parent parent hashmap
     */

    private void djikstraHelper(String from, HashMap<String, Integer> distance, HashMap<String, String> parent) {
        City city = getCity(from);
        HashMap<String, Integer> neighbors = city.getNeighbors();
        for (Map.Entry<String, Integer> entry : neighbors.entrySet()) {
            Integer val = distance.get(from);
            if (val + entry.getValue() < distance.get(entry.getKey())) {
                distance.replace(entry.getKey(), val + entry.getValue());
                parent.replace(entry.getKey(), from);
            }
        }
        city.setVisited(true);
        for (Map.Entry<String, Integer> entry : neighbors.entrySet()) {
            if (!getCity(entry.getKey()).isVisited())
                djikstraHelper(entry.getKey(), distance, parent);
        }
    }


    /**
     * getter method for graph
     * @return graph
     */
    public HashMap<String, City> getGraph() {
        return graph;
    }
    /**
     * setter method for graph
     */
    public void setGraph(HashMap<String, City> graph) {
        this.graph = graph;
    }

    /**
     * reset temp capacity
     */
    public void resetTempCapacity() {
        for (City value : graph.values()) {
            for (Map.Entry<String, Integer> e : value.getTempNeighbors().entrySet()) {
                value.getTempNeighbors().replace(e.getKey(), value.getNeighbors().get(e.getKey()));
            }
        }
    }

    /**
     * reset discovered and visited
     */
    public void resetDiscoveredVisited() {
        for (City value : graph.values()) {
            value.setVisited(false);
            value.setDiscovered(false);
        }
    }
}
