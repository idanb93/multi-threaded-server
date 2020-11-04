import org.jetbrains.annotations.NotNull;

import java.util.*;
import java.util.stream.Collectors;


public class Matrix {

    static int n = 1;
    static HashSet<Index> shortestPath = new HashSet<>();
    static HashSet<Index> currentPath = new HashSet<>();
    static List<HashSet<Index>> paths = new ArrayList<>();

    int[][] primitiveMatrix;
    public int row, col;

    public Matrix() {

        Random r = new Random();
        Random r2 = new Random();

        row = 7;
        col = 7;

        primitiveMatrix = new int[row][col];
        for (int i = 0; i < primitiveMatrix.length; i++) {
            for (int j = 0; j < primitiveMatrix[0].length; j++) {
                int n = r2.nextInt(5) + 1;
                if (r.nextInt(2) == 1 && ((i % n) == 0)) {
                    primitiveMatrix[i][j] = 1;
                } else {
                    primitiveMatrix[i][j] = 0;
                }
//                primitiveMatrix[i][j] = r.nextInt(2);
            }
        }
        for (int[] row : primitiveMatrix) {
            String s = Arrays.toString(row);
            System.out.println(s);
        }
        System.out.println("\n");
    }

    public Matrix(int[][] source) {
        primitiveMatrix = source;
    }

    public void setVal(Index index, int val) {
        this.primitiveMatrix[index.row][index.column] = val;
    }

    public void printMatrix() {
        for (int[] row : primitiveMatrix) {
            String s = Arrays.toString(row);
            System.out.println(s);
        }
    }

    public final int[][] getPrimitiveMatrix() {
        return primitiveMatrix;
    }

    @Override
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        for (int[] row : primitiveMatrix) {
            stringBuilder.append(Arrays.toString(row));
            stringBuilder.append("\n");
        }
        return stringBuilder.toString();
    }

    @NotNull
    public Collection<Index> getAdjacentIndices(@NotNull final Index index) {
        Collection<Index> neighbors = new ArrayList<>();
        try {
            int n = primitiveMatrix[index.row + 1][index.column];
            neighbors.add(new Index(index.row + 1, index.column));//index below
        } catch (ArrayIndexOutOfBoundsException ignored) {
        }
        try {
            int n = primitiveMatrix[index.row][index.column + 1];
            neighbors.add(new Index(index.row, index.column + 1));//index right
        } catch (ArrayIndexOutOfBoundsException ignored) {
        }
        try {
            int n = primitiveMatrix[index.row - 1][index.column];
            neighbors.add(new Index(index.row - 1, index.column));//index top
        } catch (ArrayIndexOutOfBoundsException ignored) {
        }
        try {
            int n = primitiveMatrix[index.row][index.column - 1];
            neighbors.add(new Index(index.row, index.column - 1));//index left
        } catch (ArrayIndexOutOfBoundsException ignored) {
        }
        try {
            int n = primitiveMatrix[index.row - 1][index.column - 1];
            neighbors.add(new Index(index.row - 1, index.column - 1));//index top-left TASK 1
        } catch (ArrayIndexOutOfBoundsException ignored) {
        }
        try {
            int n = primitiveMatrix[index.row - 1][index.column + 1];
            neighbors.add(new Index(index.row - 1, index.column + 1));//index top-right TASK 1
        } catch (ArrayIndexOutOfBoundsException ignored) {
        }
        try {
            int n = primitiveMatrix[index.row + 1][index.column - 1];
            neighbors.add(new Index(index.row + 1, index.column - 1));//index below-left TASK 1
        } catch (ArrayIndexOutOfBoundsException ignored) {
        }
        try {
            int n = primitiveMatrix[index.row + 1][index.column + 1];
            neighbors.add(new Index(index.row + 1, index.column + 1));//index below-right TASK 1
        } catch (ArrayIndexOutOfBoundsException ignored) {
        }

        return neighbors;
    }

    public int getValue(@NotNull final Index index) {
        return primitiveMatrix[index.row][index.column];
    }

    public int getValue(@NotNull int i, int j) {
        return primitiveMatrix[i][j];
    }


    public Collection<Index> getReachables(Index index) {
        ArrayList<Index> filteredIndices = new ArrayList<>();
        this.getAdjacentIndices(index).stream().filter(i -> getValue(i) == 1).map(neighbor -> filteredIndices.add(neighbor)).collect(Collectors.toList());
        return filteredIndices;
    }


    //TASK TWO:
    public static boolean verifyPath(Collection<Index> localPathList) {
        boolean flag = true;
        int it = 0;
        Index prev = null;
        int dist_r = 0;
        int dist_c = 0;

        for (Index temp : localPathList) {
            if (it == 0)
                prev = temp;
            if (it == localPathList.size())
                break;

            it++;
            dist_r = (temp.row - prev.row);
            dist_c = (temp.column - prev.column);

            if (dist_c >= 2 || dist_r >= 2)
                flag = false;
            prev = temp;
        }
        return flag;
    }

    public static List<HashSet<Index>> printAllPaths(int[][] mat, Index src, Index dest, int task) {
        List<HashSet<Index>> fin = new ArrayList<>();
        if (mat[src.row][src.column] == 0 || mat[dest.row][dest.column] == 0) {
            if (mat[src.row][src.column] == 0)
                System.out.println("bad source index(value=0)");
            if (mat[dest.row][dest.column] == 0)
                System.out.println("bad destination index(value=0)");

            return null;
        }
        boolean[][] isVisited = new boolean[mat.length][mat[0].length];
        Collection<Index> temp_paths = new ArrayList<>();

        temp_paths.add(src);

        printAllPathsUtil(mat, src, dest, isVisited, temp_paths, task);
        if (task == 2) {
            for (HashSet h : paths) {
                //System.out.println(h);
                fin.add(h);
            }

            return fin;
        }

        return null;
    }


    private static void printAllPathsUtil(int[][] mat, Index u, Index d, boolean[][] isVisited, Collection<Index> localPathList, int task) {

        if (u.equals(d)) {
            if (verifyPath(localPathList)) {

                if (task == 2) {
                    currentPath.clear();
                    System.out.println(n + ". " + localPathList);
                    for (Index i : localPathList)
                        currentPath.add(i);
                    paths.add(currentPath);
                    n++;
                }
                if (task == 3) {
                    if (shortestPath.size() == 0) {
                        shortestPath.clear();
                        for (Index i : localPathList)
                            shortestPath.add(i);
                    } else if (shortestPath.size() > localPathList.size()) {
                        shortestPath.clear();
                        for (Index i : localPathList)
                            shortestPath.add(i);
                    }
                }
            }
            return;
        }

        // Mark the current node
        isVisited[u.row][u.column] = true;


        Index index;
        for (int i = 0; i < mat.length; i++) {
            for (int j = 0; j < mat[0].length; j++) {
                if (!isVisited[i][j] && mat[i][j] == 1) {
                    index = new Index(i, j);
                    localPathList.add(index);
                    printAllPathsUtil(mat, index, d, isVisited, localPathList, task);

                    localPathList.remove(index);

                }
            }
        }


        // Mark the current node
        isVisited[u.row][u.column] = false;
    }
    public static List<HashSet<Index>> getAllPaths(int[][] mat, Index src, Index dest){
        List<HashSet<Index>> fin = new ArrayList<>();
        printAllPaths(mat, src, dest, 2);

        for(HashSet h: paths){
            fin.add(h);
        }
        return fin;
    }


    //TASK THREE:
    public Collection<Index> getShortestPath(int[][] mat, Index src, Index dest) {
        Collection<Index> collection = new ArrayList<>();

        printAllPaths(mat, src, dest, 3);
        for (Index index : shortestPath) {
            collection.add(index);
        }

        return collection;
    }


    public static void main(String[] args) {

        int[][] source = {
                {1, 1, 1},
                {1, 0, 1},
                {1, 1, 1}
        };


        int source2[][] = {
                {1, 1, 1, 1},
                {1, 0, 0, 1},
                {1, 1, 1, 1},
                {1, 0, 0, 1}
        };


        Index src = new Index(0, 0);
        Index dest_source = new Index(2, 2);
        Index dest_source2 = new Index(3, 3);

        Matrix matrix = new Matrix(source);
        Matrix m2 = new Matrix(source2);
        m2.printMatrix();

        System.out.println("\nTASK 2:");
        getAllPaths(source2,src,dest_source2);


        System.out.println("\nTASK 3:");
        System.out.println("from "+src+" to "+dest_source2);
        System.out.println(m2.getShortestPath(source2, src, dest_source2));

    }

}