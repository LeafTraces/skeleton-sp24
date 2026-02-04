import edu.princeton.cs.algs4.WeightedQuickUnionUF;


public class Percolation{
    // TODO: Add any necessary instance variables.
    private boolean[][] grid;   //记录格子开关
    private final int N;
    private int size;   //开了多少格子

    private WeightedQuickUnionUF uf;            //并查集，包含top和bottom
    private WeightedQuickUnionUF ufNoBottom;    //并查集，只包含top

    private int virTop;     //虚拟顶部节点
    private int virBottom;  //虚拟底部节点

    public Percolation(int N) {
        // TODO: Fill in this constructor.
        if (N <= 0){
            throw new IllegalArgumentException("N must be > 0");
        }
        this.grid = new boolean[N][N];
        this.N = N;
        this.size = 0;

        //初始化uf
        this.uf = new WeightedQuickUnionUF(N * N + 2);
        this.ufNoBottom = new WeightedQuickUnionUF(N * N + 1);

        this.virTop = N * N;
        this.virBottom = N * N + 1;
    }

    public void open(int row, int col) {
        // TODO: Fill in this method.
        validate(row, col);
        if (isOpen(row, col)){
            return;
        }

        grid[row][col] = true;
        size++;
        int pixel = xyTo1D(row, col);

        if (row == 0){
            uf.union(pixel, virTop);
            ufNoBottom.union(pixel, virTop);
        }
        if (row == N - 1){
            uf.union(pixel, virBottom);
        }

        if (row > 0 && isOpen(row - 1, col)){
            int neighbor_1 = xyTo1D(row - 1, col);
            uf.union(pixel, neighbor_1);
            ufNoBottom.union(pixel, neighbor_1);
        }
        if (row < N - 1 && isOpen(row + 1, col)){
            int neighbor_2 = xyTo1D(row + 1 , col);
            uf.union(pixel, neighbor_2);
            ufNoBottom.union(pixel, neighbor_2);
        }
        if (col > 0 && isOpen(row, col - 1)){
            int neighbor_3 = xyTo1D(row, col - 1);
            uf.union(pixel, neighbor_3);
            ufNoBottom.union(pixel, neighbor_3);
        }
        if (col < N - 1 && isOpen(row, col + 1)){
            int neighbor_4 = xyTo1D(row, col + 1);
            uf.union(pixel, neighbor_4);
            ufNoBottom.union(pixel, neighbor_4);
        }
    }

    public boolean isOpen(int row, int col) {
        // TODO: Fill in this method.
        validate(row, col);

        return grid[row][col];
    }

    public boolean isFull(int row, int col) {
        // TODO: Fill in this method.
        validate(row, col);

        int p = xyTo1D(row, col);
        return ufNoBottom.connected(p, virTop);
    }

    public int numberOfOpenSites() {
        // TODO: Fill in this method.
        return size;
    }

    public boolean percolates() {
        // TODO: Fill in this method.
        return uf.connected(virTop, virBottom);
    }

    // TODO: Add any useful helper methods (we highly recommend this!).
    // TODO: Remove all TODO comments before submitting.
    private int xyTo1D(int row, int col){
        return row * N + col;
    }

    private void validate(int row, int col) {
        if (row < 0 || row >= N || col < 0 || col >= N) {
            throw new IndexOutOfBoundsException("Index is out of bounds!");
        }
    }

}
