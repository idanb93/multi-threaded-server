import java.io.*;
import java.net.Socket;
import java.util.*;

public class Client {
    public static void main(String[] args) throws IOException, ClassNotFoundException, InterruptedException {

        Socket socket = new Socket("127.0.0.1", 8010);
        System.out.println("client::Socket\n");

        InputStream inputStream = socket.getInputStream();
        OutputStream outputStream = socket.getOutputStream();
        ObjectOutputStream toServer = new ObjectOutputStream(outputStream);
        ObjectInputStream fromServer = new ObjectInputStream(inputStream);

        int[][] source1 = {
                {1, 1, 1, 1, 0},
                {1, 0, 0, 1, 0},
                {1, 0, 1, 1, 0},
                {1, 0, 0, 0, 1},
                {1, 1, 1, 1, 1}
        };


        int source2[][] = {
                {1, 1, 1, 1},
                {1, 0, 0, 1},
                {1, 1, 1, 1},
                {1, 0, 0, 1}
        };
        Matrix matrix = new Matrix(source2);

        // sending #1 matrix
        int[][] source = {
                {1, 1, 0},
                {1, 1, 1},
                {1, 0, 1}
        };

        toServer.writeObject("matrix");
        toServer.writeObject(source2);
        matrix.printMatrix();

        // sending #3 index for connectedComponents
        toServer.writeObject("connectedComponents");
        toServer.writeObject(new Index(0, 0));
        // receiving #1 connectedComponents
        List<HashSet<Index>> all_reachables = new ArrayList<>((List<HashSet<Index>>) fromServer.readObject());
        System.out.println();
        System.out.println("client::Task 1(Groups Of Connected Components):: " + all_reachables);

        // task 3

        toServer.writeObject("getShortestPath");
        toServer.writeObject(source2);
        toServer.writeObject(new Index(0, 0));
        toServer.writeObject(new Index(3, 3));
        Collection<Index> ShortestPath =
                new ArrayList<>((Collection<Index>) fromServer.readObject());
        System.out.println("client::Task 3(Shortest Path):: " + ShortestPath + "\n");

        toServer.writeObject("stop");

        System.out.println("client::Close all streams!!!!");
        fromServer.close();
        toServer.close();
        socket.close();
        System.out.println("client::Close socket!!!!");

    }
}