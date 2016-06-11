import edu.princeton.cs.algs4.WeightedQuickUnionUF;

public class Percolation {
    private int                  N;
    private boolean[]            grid;
    private WeightedQuickUnionUF qf;
    private int                  virtualTop;
    private int                  virtualBottom;
    private WeightedQuickUnionUF qf2;

    public Percolation(int N) {
        if (N <= 0) {
            throw new IllegalArgumentException("you put in a wrong number");
        }
        this.N = N;
        this.virtualTop = N * N;
        this.virtualBottom = N * N + 1;
        this.grid = new boolean[N * N];
        this.qf = new WeightedQuickUnionUF((N * N) + 2);
        this.qf2 = new WeightedQuickUnionUF((N * N) + 2);
    }

    public void open(int i, int j) {
        validate(i, j);
        int lessi = i - 1;
        int lessj = j - 1;
        int index = makeLocation(lessi, lessj);
        connectThings(i, j);
        if (!isOpen(i, j) && i == 1) {
            grid[index] = true;
            qf.union(virtualTop, index);
            qf2.union(virtualTop, index);
            connectThings(i, j);
        } 
        else if ( i == N && !isOpen(i, j)) {
            grid[index] = true;
            qf2.union(index, virtualBottom);
            connectThings(i, j);
        } 
        else if (!isOpen(i, j)) {
            grid[index] = true;
            connectThings(i, j);
        }
        if (qf.connected(virtualTop, index) && qf2.connected(index, virtualBottom)){
            qf.union(virtualTop, virtualBottom);
        }
    }

    public boolean isOpen(int i, int j) {
        validate(i, j);
        i = i - 1;
        j = j - 1;
        int index = makeLocation(i, j);
        boolean open = false;
        if (grid[index] == false)
            open = false;
        else
            open = true;
        return open;
    }

    public boolean isFull(int i, int j) {
        validate(i, j);
        int lessi = i - 1;
        int lessj = j - 1;
        int index = makeLocation(lessi, lessj);
        boolean full = false;
        if (qf.connected(virtualTop, index)){
            full = true;
        }
        return full;
    }

    public boolean percolates() {
        boolean percolates = false;
        if (qf.connected(virtualTop, virtualBottom))
            percolates = true;
        return percolates;
    }
    
   

    
    private void validate(int i, int j) {
        if (i <= 0 || j <= 0 || i > N || j > N ) {
            throw new IllegalArgumentException("you put in a wrong number");
        }
    }
    
    private boolean validateTrue(int i, int j) {
        if (i <= 0 || j <= 0 || i > N || j > N ) {
            return false;
        }else return true;
    }
    private int makeLocation(int i, int j) {

        return (i * N) + j;
    }
    private void connectThings(int i, int j){
        int[][] n = neighbors(i, j);
        int index = makeLocation(i-1, j-1);
        for (int x = 0; x < n.length; x++) {
            if (!validateTrue(n[x][0], n[x][1])) {
                continue;
            }         
            else if (n[x][0]==N && isOpen(n[x][0], n[x][1])){
                qf.union(index, makeLocation(n[x][0] - 1, n[x][1] - 1));
                qf2.union(index, makeLocation(n[x][0] - 1, n[x][1] - 1));
            }
            
            else if (isOpen(n[x][0], n[x][1])) {
                qf.union(index, makeLocation(n[x][0] - 1, n[x][1] - 1));
                qf2.union(index, makeLocation(n[x][0] - 1, n[x][1] - 1));
            }
        }
    }
    
    private int[][] neighbors(int i, int j) {
        int[][] neighbors;
        int length = 4;
        neighbors = new int[length][];
        int[] right = { i, j - 1 };
        int[] left = { i, j + 1 };
        int[] bottom = { i + 1, j };
        int[] top = { i - 1, j };
        neighbors[0] = right;
        neighbors[1] = left;
        neighbors[2] = bottom;
        neighbors[3] = top;
        return neighbors;
    }

    public static void main(String[] args) {
  
    }

}
