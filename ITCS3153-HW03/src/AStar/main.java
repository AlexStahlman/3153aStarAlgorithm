package AStar;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Scanner;

public class main {
    public static Node[][] grid = new Node[15][15];
    public static Node start;
    public static Node end;
    public static boolean walkable;

    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);
        while(true) {
            try{
                System.out.println("What is the X for start node? (0-14) or press any character to quit");
                int xstart = input.nextInt();
                System.out.println("What is the Y for start node? (0-14) or press any character to quit");
                int ystart = input.nextInt();
                System.out.println("What is the X for the end node? (0-14) or press any character to quit");
                int xend = input.nextInt();
                System.out.println("What is the Y for the end node? (0-14) or press any character to quit");
                int yend = input.nextInt();

                drawGrid();
                // make start node equal the node at the x and y from input
                if(grid[xstart][ystart].moveable == false){
                    System.out.println("Invalid start position! (You picked a blocker)");
                    return;
                }
                start = grid[xstart][ystart];
                //same thing but for end
                if(grid[xend][yend].moveable == false){
                    System.out.println("Invalid end position! (You picked a blocker)");
                    return;
                }
                end = grid[xend][yend];
                aStar(start, end);

            }
            catch(ArrayIndexOutOfBoundsException r){
                System.out.println("You can't do that! The max number input is 14!");
            }
            catch(Exception e){
                System.out.println("Thanks for searching!");
                return;
            }

        }



    }

        public static ArrayList<Node> getNeighbor(Node node){
        //we make neighbors, then we are finding the nodes based on current node
            /*
            so we have 'x' as what we need to look at and 'c' as our current node
            [x][x][x][ ][ ][ ][ ]
            [x][c][x][ ][ ][ ][ ]
            [x][x][x][ ][ ][ ][ ]
             */
            ArrayList<Node> neighbors = new ArrayList<>();
            int checkX;
            int checkY;
            for(int x = -1; x<=1; x++){
                for(int y = -1; y<=1; y++){
                    //x=0 and y=0 is just currentNode...
                    if (x==0 && y==0){
                        continue;
                    }
                    //current x/y plus the x/y in the loop
                    checkX = node.getX()+x;
                    checkY = node.getY()+y;
                    // if its in bounds, we add it to the neighbors array
                    if(checkX>=0 && checkY>=0 && checkX < grid.length && checkY <grid.length){
                        Node adding = new Node(checkX, checkY, true);
                        neighbors.add(adding);
                    }
                }
            }
            return neighbors;
        }
        public static int getDistance(Node nodeA, Node nodeB){
        //manhattan distance method, idk man i just work here what you want me to say
            int dstx = Math.abs(nodeA.getX()-nodeB.getX());
            int dsty = Math.abs(nodeA.getY()-nodeB.getY());
            if (dstx>dsty){
                return 14*dsty+10*(dstx-dsty);
            }
            if (dstx<dsty){
                return 14*dstx+10*(dsty-dstx);
            }
            return 0;
        }
        public static ArrayList<Node> tracePath(Node start, Node end){
            ArrayList<Node> path = new ArrayList<>();
            Node currentNode = end;
            //starting at the end, we retrace our steps from end to front
            while(currentNode != start){
                path.add(currentNode);
                currentNode = currentNode.parent;
            }
            // but its backwards so we reverse it
            Collections.reverse(path);
            return path;

        }
        public static void drawGrid(){
        // making the 15x15 grid, should be scaleable if I want to
            for(int i=0; i<15; i++){
                for(int x=0; x<15; x++){
                    double random = Math.random();
                    walkable = true;
                    //.1 of random is just like, 10%.. if walkable = false then we cant mess with it (See astar method)
                    if(random<.1){
                        walkable=false;
                    }
                    grid[i][x] = new Node(i, x, walkable);

                }
            }
        }

        public static void aStar(Node start, Node end) {
            ArrayList<Node> openSet = new ArrayList<>();
            ArrayList<Node> closedSet = new ArrayList<>();
            Node currentNode;
            // set f of the start node
            start.setF();
            openSet.add(start);
            //while there is nodes in the open set (Open = needing to be searched)
            while(!openSet.isEmpty()){
                currentNode = openSet.get(0);
                for(int i=1; i<openSet.size(); i++){
                    if(openSet.get(i).getF()<=currentNode.getF()){
                        currentNode = openSet.get(i);
                    }
                }
                //made currentNode = current open set node so we move it to closed set as we are looking at it now
                openSet.remove(currentNode);
                closedSet.add(currentNode);
                if(currentNode.equals(end)){
                    //if its the end its the end! we print everything needed
                    System.out.println("Start Node Was: "+ start.getX()+", "+start.getY());
                    System.out.println("End Node Was: "+ end.getX()+", "+end.getY());
                    System.out.println("Visited Node Were: " + tracePath(start, end));
                    System.out.println("The Entire Array Was: ");
                    for(int p=0; p<15; p++){
                        for(int t=0; t<15; t++){
                            System.out.print(grid[p][t] + " ");
                        }
                        System.out.println(" ");
                    }
                    System.out.println();
                    return;
                }
                //For every neighbor that the current node has, look through it
                //if its a blocker or already searched, ignore it
                for(Node neighbor: getNeighbor(currentNode)){
                    if(closedSet.contains(neighbor)|| neighbor.moveable == false){
                        continue;
                    }
                    if(neighbor.equals(end)){
                        end.setParent(currentNode);
                    }
                    // using manhattan cost shtuff called newMovementCost... Im kinda hungry for panda (the express kind)
                    int newMovementCost = currentNode.getG() + getDistance(currentNode, neighbor);
                    if(newMovementCost<neighbor.getG() || !openSet.contains(neighbor)){
                        neighbor.setG(newMovementCost);
                        neighbor.setH(getDistance(neighbor, end));
                        neighbor.setF();
                        neighbor.setParent(currentNode);
                        // if the open set doesnt have the current neighbor node, we add it to open so we can search it!
                        if(!openSet.contains(neighbor)) {
                            openSet.add(neighbor);
                        }
                    }

                }

            }

        }


}
