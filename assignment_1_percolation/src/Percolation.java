public class Percolation {

    private WeightedQuickUnionUF quickUnionUF;
    private int N;
    private boolean[][] opened;

    // create N-by-N grid, with all sites blocked
    public Percolation(int N) {

        if (N <= 0) {
            throw new IllegalArgumentException();
        }

        this.N = N;
        this.opened = new boolean[N][N];

        // N+2 = virtual-top / virtual-bottom trick
        quickUnionUF = new WeightedQuickUnionUF(2*N*N+2);
    }                

    // open site (row i, column j) if it is not already
    public void open(int i, int j) {

        check(i, j);

        if (!isOpen(i, j)) {

            opened[i - 1][j - 1] = true;

            // virtual top
            if (i == 1) {
                quickUnionUF.union(xyTo1D(i, j), virtualTop());
                quickUnionUF.union(inverseXyTo1D(i, j), virtualBottom());
            }

            // virtual bottom
            if (i == N) {
                quickUnionUF.union(xyTo1D(i, j), xyTo1D(i + 1, j));
                quickUnionUF.union(inverseXyTo1D(i, j), inverseXyTo1D(i + 1, j));
            }

            // top
            if (i > 1 && isOpen(i - 1, j)) {
                quickUnionUF.union(xyTo1D(i, j), xyTo1D(i - 1, j));
                quickUnionUF.union(inverseXyTo1D(i, j), inverseXyTo1D(i - 1, j));
            }

            // right
            if (j < N && isOpen(i, j + 1)) {
                quickUnionUF.union(xyTo1D(i, j), xyTo1D(i, j + 1));
                quickUnionUF.union(inverseXyTo1D(i, j), inverseXyTo1D(i, j + 1));
            }

            // bottom
            if (i < N && isOpen(i + 1, j)) {
                quickUnionUF.union(xyTo1D(i, j), xyTo1D(i + 1, j));
                quickUnionUF.union(inverseXyTo1D(i, j), inverseXyTo1D(i + 1, j));
            }

            // left
            if (j > 1 && isOpen(i, j - 1)) {
                quickUnionUF.union(xyTo1D(i, j), xyTo1D(i, j - 1));
                quickUnionUF.union(inverseXyTo1D(i, j), inverseXyTo1D(i, j - 1));
            }
        }
    }   

    // is site (row i, column j) open?        
    public boolean isOpen(int i, int j) {

        check(i, j);

        return this.opened[i - 1][j - 1];
    }      

    // is site (row i, column j) full?
    public boolean isFull(int i, int j) {

        check(i, j);

        return quickUnionUF.connected(virtualTop(), xyTo1D(i, j));
    }      

    // does the system percolate?
    public boolean percolates() {
        return quickUnionUF.connected(virtualTop(), virtualBottom());
    }

    private void check(int i, int j) {

        if (i < 1 || i > N) throw new IndexOutOfBoundsException();
        if (j < 1 || j > N) throw new IndexOutOfBoundsException();
    }

    private int virtualTop() {
        return 0;
    }

    private int virtualBottom() {
        return 2*N*N+1;
    }

    // mapping 2D coordinates to 1D coordinates
    private int xyTo1D(int x, int y) {

        return y + ((x-1) * N);
    }

    private int inverseXyTo1D(int x, int y) {

        return this.xyTo1D(2*N-(x-1), y);
    }

}