import java.util.HashMap;

/**
 * This class represent the City
 */
public class City implements Comparable<City> {
    // Store neighbors
    private HashMap<String, Integer> neighbors;
    // Store city name
    private String name;
    // Store discovered information
    private boolean discovered;
    // Store visited information
    private boolean visited;
    // Store temporary neighbors information for max flow
    private HashMap<String, Integer> tempNeighbors;


    /**
     * constructor for initialize fields
     * @param name city name
     */
    public City(String name) {
        neighbors = new HashMap<>();
        tempNeighbors = new HashMap<>();
        this.name = name;
    }

    /**
     * getter method for neighbors
     * @return neighbors
     */
    public HashMap<String, Integer> getNeighbors() {
        return neighbors;
    }

    /**
     * setter method for neighbors
     */
    public void setNeighbors(HashMap<String, Integer> neighbors) {
        this.neighbors = neighbors;
    }

    /**
     * getter method for name
     * @return name
     */
    public String getName() {
        return name;
    }

    /**
     * setter method for name
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * getter method for discovered
     * @return discovered
     */
    public boolean isDiscovered() {
        return discovered;
    }

    /**
     * setter method for discovered
     */
    public void setDiscovered(boolean discovered) {
        this.discovered = discovered;
    }

    /**
     * getter method for visited
     * @return visited
     */
    public boolean isVisited() {
        return visited;
    }

    /**
     * setter method for visited
     */
    public void setVisited(boolean visited) {
        this.visited = visited;
    }

    /**
     * getter method for tempNeighbors
     * @return tempNeighbors
     */
    public HashMap<String, Integer> getTempNeighbors() {
        return tempNeighbors;
    }

    /**
     * setter method for tempNeighbors
     */
    public void setTempNeighbors(HashMap<String, Integer> tempNeighbors) {
        this.tempNeighbors = tempNeighbors;
    }

    /**
     * add neighbor to neighbors
     */
    public void addNeighbour(String name, int capacity) {
        neighbors.put(name, capacity);
        addTempNeighbour(name,capacity);
    }

    /**
     * add neighbor to tempNeighbors
     */
    public void addTempNeighbour(String name, int capacity) {
        tempNeighbors.put(name, capacity);
    }

    /**
     * compare method for compare two city
     * @param o other city
     * @return compare using city name
     */
    @Override
    public int compareTo(City o) {
        return name.compareTo(o.name);
    }

    /**
     * for printing purpose
     * @return city name
     */
    public String toString() {
        return name;
    }
}
