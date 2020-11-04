import java.util.*;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.ReentrantReadWriteLock;

public class ConnectedComponents {
    private Matrix matrix;

    public ConnectedComponents(Matrix matrix) {
        this.matrix = matrix;
    }

    public List<HashSet<Index>> ComponentsGroups(Index index) {

        // creating a group that will contain ALL the connected components for the given index.
        List<HashSet<Index>> group = new ArrayList<>();
        // creating a hashset that will contain one group of connected components per iteration.
        HashSet<Index> hashSet;// = new HashSet<>();

        int row = this.matrix.primitiveMatrix.length;
        int col = this.matrix.primitiveMatrix[0].length;

        // running from a given index in order to search from different origins
        for (int i = index.row; i < row; i++) {
            for (int j = index.column; j < col; j++) {
                if (this.matrix.getValue(new Index(i, j)) == 1) {
                    // adding the new found "1" to the current group and send to the recursive function
                    // in order to locate all of its connected components
                    hashSet = getComponents(new HashSet<>(), i, j);
                    // adding all of the group to List<HasheSet<Index>>
                    group.add(hashSet);
                }
            }
        }
        return group;
    }

    public HashSet<Index> getComponents(HashSet<Index> current, int i, int j) {
        Index index = new Index(i, j);
        int set_val = -1;
        if (this.matrix.getValue(index) != 1)   // end of recursive
            return current;

        // getting all the neighboring "1"s
        Collection<Index> collection = this.matrix.getReachables(index);
        // adding the current index to the current HashSet<Index>
        current.add(index);
        // changing the value in the matrix in order to mark as visited
        this.matrix.setVal(index, set_val);

        for (Index temp : collection) {
            // recursive call to get all of the group's connected components
            return getComponents(current, temp.row, temp.column);
        }

        return current;
    }

    public static void main(String[] args) {
        ThreadPoolExecutor executor = new ThreadPoolExecutor(3, 5, 3, TimeUnit.SECONDS, new LinkedBlockingQueue<>());
        ConnectedComponents connectedComponents = new ConnectedComponents(new Matrix());

        final ReentrantReadWriteLock rfreadWriteLock = new ReentrantReadWriteLock();

        for (int i = 0; i < 4; i++) {
            Runnable runnable = () -> {

                rfreadWriteLock.writeLock().lock();

                List<HashSet<Index>> theList = new ArrayList<>();
                theList.clear();
                try {
                    Random random = new Random();
                    int rand_row = random.nextInt(6);
                    int rand_col = random.nextInt(6);
                    Index index = new Index(0, 0);

                    theList = connectedComponents.ComponentsGroups(index);

                    String threadId = Thread.currentThread().getName();
                    System.out.println("The Size Of The Connected Component List is: " + theList.size());
                    System.out.print(threadId + " The Connected Components For Index (" + rand_row + "," + rand_col + ") Are:\n");
                    for (HashSet h : theList) {

                        System.out.println(h);
                    }

                } catch (Exception e) {
                    e.printStackTrace();

                } finally {
                    rfreadWriteLock.writeLock().unlock();
                    System.out.println("unlocking\n");

                }

            };
            executor.execute(runnable);
        }
    }

}
