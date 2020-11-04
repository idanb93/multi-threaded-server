import java.io.*;
import java.util.*;

public class MatrixIHandler implements IHandler {

    private Matrix matrix;
    private Index start, end;
    private ConnectedComponents connectedComponents;

    public MatrixIHandler() {
        this.resetParams();
    }
    private void resetParams(){
        this.matrix = null;
        this.start = null;
        this.end = null;
    }

    @Override
    public void handle(InputStream inClient, OutputStream outClient) throws Exception {
        System.out.println("server::start handle");

        ObjectOutputStream objectOutputStream=new ObjectOutputStream(outClient);
        ObjectInputStream objectInputStream = new ObjectInputStream(inClient);

        this.resetParams();

        boolean dowork = true;
        while (dowork) {
            switch (objectInputStream.readObject().toString()) {
                case "stop":{
                    dowork= false;
                    break;
                }
                case "matrix": {
                    int[][] primitiveMatrix = (int[][]) objectInputStream.readObject();
                    this.matrix = new Matrix(primitiveMatrix);
                    this.matrix.printMatrix();
                    break;
                }
                case "connectedComponents": {
                    //Matrix matrix = new Matrix();
                    Index start_index = (Index) objectInputStream.readObject();
                    ConnectedComponents connectedComponents = new ConnectedComponents(this.matrix);
                    List<HashSet<Index>> all_reachables = new ArrayList<>();
                    if(connectedComponents != null){
                        all_reachables.addAll(connectedComponents.ComponentsGroups(start_index));
                    }
                    System.out.println("server::getConnectedComponents:: " + all_reachables);
                    objectOutputStream.writeObject(all_reachables);
                    break;
                }
                case "start Index": {
                    this.start = (Index) objectInputStream.readObject();
                    break;
                }
                case "end Index": {
                    this.end = (Index) objectInputStream.readObject();
                    break;
                }
                case "getAdjacent": {
                    // receiving index for getAdjacentIndices
                    Index indexAdjacentIndices = (Index) objectInputStream.readObject();
                    Collection<Index> adjacentIndices = new ArrayList<>();
                    if (this.matrix != null){
                        adjacentIndices.addAll(this.matrix.getAdjacentIndices(indexAdjacentIndices));
                    }
                    // sending getAdjacentIndices
                    System.out.println("server::getAdjacentIndices:: " + adjacentIndices);
                    objectOutputStream.writeObject(adjacentIndices);
                    break;
                }
                case "getAdjacentReachable": {

                    // receiving index for getReachables
                    Index indexReachables = (Index) objectInputStream.readObject();
                    Collection<Index> reachables = new ArrayList<>();
                    if (this.matrix != null){
                        reachables.addAll(this.matrix.getReachables(indexReachables));
                    }
                    // sending getReachables
                    System.out.println("server::getReachables:: " + reachables);
                    objectOutputStream.writeObject(reachables);
                    break;
                }
                case "getConnectedComponents":{
                    //int mat[][] = (int[][])objectInputStream.readObject();
                    Index index = (Index) objectInputStream.readObject();
                    List<HashSet<Index>> list;
                    //System.out.println("this.connectedComponents"+this.connectedComponents);
                    if(this.connectedComponents != null){
                        list =  this.connectedComponents.ComponentsGroups(index);
                        System.out.println("server::getConnectedComponents:: " + list);


                    }
                    break;
                }

                case "printAllPaths":{
                    int matrix[][] = (int[][]) objectInputStream.readObject();

                    //Matrix matrix23 = new Matrix(matrix);
                    //matrix23.printMatrix();

                    Index src = (Index) objectInputStream.readObject();
                    Index dest = (Index) objectInputStream.readObject();
                    List<HashSet<Index>> total = new ArrayList<>();
                    if(this.matrix != null){
                        total.addAll(this.matrix.printAllPaths(matrix,src,dest,2));
                    }
                    System.out.println("server::printAllPaths:: "+ total);
                    objectOutputStream.writeObject(total);
                    break;
                }
                case "getShortestPath":{

                    int matrix[][] = (int[][]) objectInputStream.readObject();
                    Index src = (Index) objectInputStream.readObject();
                    Index dest = (Index) objectInputStream.readObject();

                    Collection<Index> path = new ArrayList<>();
                    if(this.matrix != null){
                        path.addAll(this.matrix.getShortestPath(matrix, src,dest));
                    }
                    System.out.println("server::getShortestPath:: "+ path);
                    objectOutputStream.writeObject(path);
                    break;
                }
            }
        }
    }
}