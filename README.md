# Algorithm-Courswork-2020
In this program, I should find the maximum possible flow in a flow network which the user
created. I used ford Fulkerson algorithm to find the maximum flow. To this process, we need
to find the path in the actual graph and residual graphs which is one of the outputs in the
Ford Fulkerson algorithm. Therefore, we should pass the graphs to the breadth-first search
algorithm to find paths in the graph.

I used three two-dimensional arrays in this program. First one is to get the vertices in a
graph and set capacities to each edge. Then in the Ford Fulkerson algorithm, I used two 2d
arrays to define the residual graphs of the flow network and to present the paths with the
distributed flows. In the breadth-first search algorithm, I used a linked array list as a queue.
Each time value gets from the queue and passes it to the parent array if that node isn’t
visited node or it has any capacity. Then get the path and calculate bottleneck capacity. After
the last residual graph finished, calculate all the flows and give the maximum possible flow.
 
