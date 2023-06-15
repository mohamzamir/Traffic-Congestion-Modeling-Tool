# Traffic Congestion Modeling Tool

 ## Project Overview
 The Traffic Congestion Modeling Tool is a Java Simulator that is designed to calculate the path a person takes given an input list of cities and roads, along with their carrying capacities in terms of cars per minute. This tool is useful in modeling traffic flows and congestion in various cities.

 The solution is optimized to calculate the maximum flow between two cities using Depth First Search (DFS) and the Ford-Fulkerson algorithms. Dijkstra’s Algorithm is also used to efficiently determine the shortest path.

 ## Dependencies
 Java Development Kit (JDK 11 or later) <br>
 Apache Maven (3.6.3 or later) <br>

 ## Installation
 Clone the repo
 ```bash
 git clone https://github.com/mohamzamir/Traffic-Congestion-Modeling-Tool
 ```

 ## Usage
 To use the Traffic Congestion Modeling Tool, run the Java simulator with a list of cities and a list of roads from various cities, along with their carrying capacities in terms of cars per minute.

 ## Implementation
 The Java Simulator is built to manage and calculate traffic flows. It accepts a list of cities and roads as input, along with the maximum capacity of each road.

 The maximum flow between two cities is calculated using the DFS and Ford-Fulkerson algorithms, providing an optimized solution for high-density traffic conditions. Dijkstra’s Algorithm is implemented to determine the shortest path between two cities, further improving the tool's efficiency.
 
 ## Program Sample
 
 Welcome to the Island Designer, because, when you're trying to stay above water, Seas get degrees!

```bash
please enter an url:
```

```bash
Map loaded.

Cities:     //Alphabetical Order
---------------------
Bones Beach
Composting Fields
Fire Hazard
Fishingville
Gatsby
Kingkongoma
Lawn City
North Spoon
Small Pear
South Spoon
Stream Foot
University

Road                                  Capacity
----------------------------------------------
Composting Fields to Small Pear            12
Lawn City to Small Pear                    30
Hipster to Small Pear                      14
Hipster to Small Pear                      16
Lawn City to Hipster                       16
Gatsby to Composting Fields                10
Fishingville to Lawn City                  17
Fishingville to Gatsby                     11
Bones Beach to Hipster                     12
Bones Beach to Lawn City     	            8
Fire Hazard to Bones Beach                 13
Kingkongoma to Fire Hazard                  7
Kingkongoma to Lawn City                   20
University to Kingkongoma                   6
University to Fishingville                 18
Stream Foot to University                   6
Steam Foot to Kingkongoma                  11
North Spoon to Stream Foot                 15
South Spoon to Stream Foot                 20

Menu:
    D) Destinations reachable (Depth First Search)
    F) Maximum Flow
    S) Shortest Path 
    Q) Quit
    
Please select an option: D
Please enter a starting city: University
DFS Starting From University:
Fishingville, Gatsby, Composting Fields, Small Pear, Lawn City, Hipster, Kingkongoma, Fire Hazard, BonesBeach
Please select an option: D
Please enter a starting node: South Spoon
DFS results (destinations reachable):
Stream Foot, Kingkongoma, Lawn City, Small Pear, Fire Hazard, Bones Beach, Hipster, Fishingville, Gatsby, Composting Fields
Please select an option: F
Please enter a starting city: University
Please enter a destination: Hipster
Routing: 
University->Fishingville->Lawn City->Hipster: 16
University->Kingkongoma->Fire Hazard->Bones Beach->Hipster: 6
Maximum Flow: 22
Please select an option: F
Please enter a starting city: University
Please enter a destination: South Spoon
No route available!
Please select an option: S 
Please enter a starting node: Kingkongoma
Please enter a destination node: Small Pear
Path: Kingkongoma->Fire Hazard->Bones Beach->Hipster->Small Pear
Cost: 46
Please select an option: Q

You can go your own way! Goodbye!

```


 ## License
 Copyright [2023] [Amir Hamza]

 Licensed under the Apache License, Version 2.0 (the "License");
 you may not use this file except in compliance with the License.
 You may obtain a copy of the License at

     http://www.apache.org/licenses/LICENSE-2.0

 Unless required by applicable law or agreed to in writing, software
 distributed under the License is distributed on an "AS IS" BASIS,
 WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 See the License for the specific language governing permissions and
 limitations under the License.
