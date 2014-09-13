public class Percolation {

    private WeightedQuickUnionUF quickUnionUF;
    private int N;
    private boolean[] grid;

    // create N-by-N grid, with all sites blocked
    public Percolation(int N) {

        this.N = N;
        this.grid = new boolean[N*N+2];

        // N+2 = virtual-top / virtual-bottom trick
        quickUnionUF = new WeightedQuickUnionUF(2*N*N+2);
    }                

    // open site (row i, column j) if it is not already
    public void open(int i, int j) {

        if (!isOpen(i, j)) {

            int xy = this.xyTo1D(i, j);
            
            this.grid[xy] = true;

            int xyTop = i == 1 ? 0 : this.xyTo1D(i-1, j);
            int xyRight = j == N ? -1 : this.xyTo1D(i, j+1);
            int xyBottom = i == N ? N : this.xyTo1D(i+1, j);
            int xyLeft = j == 1 ? -1 : this.xyTo1D(i, j-1);

            if (xyTop == 0 || grid[xyTop]) {
                quickUnionUF.union(xy, xyTop);
            }

            if (xyRight >= 0 && grid[xyRight]) {
                quickUnionUF.union(xy, xyRight);
            }

            if (xyBottom == N || grid[xyBottom]) {
                quickUnionUF.union(xy, xyBottom);
            }

            if (i == N) {
                quickUnionUF.union(xy, xyBottom+N);
            }

            if (xyLeft >= 0 && grid[xyLeft]) {
                quickUnionUF.union(xy, xyLeft);
            }
        }
        
    }   

    // is site (row i, column j) open?        
    public boolean isOpen(int i, int j) {

        int xy = this.xyTo1D(i, j);

        return this.grid[xy];
    }      

    // is site (row i, column j) full?
    public boolean isFull(int i, int j) {

        return quickUnionUF.connected(xyTo1D(i, j), 0);
    }      

    // does the system percolate?
    public boolean percolates() {
        return quickUnionUF.connected(0, N*N+1);
    }

    // mapping 2D coordinates to 1D coordinates
    private int xyTo1D(int x, int y) {

        return y + ((x-1) * N);
    }

}