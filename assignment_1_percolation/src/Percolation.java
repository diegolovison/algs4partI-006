public class Percolation {

    private WeightedQuickUnionUF quickUnionUF;
    private int N;
    private boolean[] grid;

    // create N-by-N grid, with all sites blocked
    public Percolation(int N) {

        this.N = N;
        this.grid = new boolean[N*N+2];

        // open the virtual nodes
        this.grid[0] = true;
        this.grid[N*N+1] = true;

        // N+2 = virtual-top / virtual-bottom trick
        quickUnionUF = new WeightedQuickUnionUF(2*N*N+2);
    }                

    // open site (row i, column j) if it is not already
    public void open(int i, int j) {

        if (!isOpen(i, j)) {

            int inverseI = 2*N-(i-1);

            int xy = this.xyTo1D(i, j);
            int inverseXy = this.xyTo1D(inverseI, j);
            
            this.grid[xy] = true;

            // first matrix
            int xyTop = i == 1 ? 0 : this.xyTo1D(i-1, j);
            int xyRight = j == N ? -1 : this.xyTo1D(i, j+1);
            int xyBottom = this.xyTo1D(i+1, j);
            int xyLeft = j == 1 ? -1 : this.xyTo1D(i, j-1);

            if (grid[xyTop]) {
                quickUnionUF.union(xy, xyTop);

                // inverse
                int inverseXyTop = inverseI == 2*N ? 2*N*N+1 : this.xyTo1D(inverseI+1, j);
                quickUnionUF.union(inverseXy, inverseXyTop);
            }

            if (xyRight >= 0 && grid[xyRight]) {
                quickUnionUF.union(xy, xyRight);

                // inverse
                int inverseXyRight = j == N ? -1 : this.xyTo1D(inverseI, j+1);
                if (inverseXyRight >= 0) {
                    quickUnionUF.union(inverseXy, inverseXyRight);
                }
            }

            if (i == N || grid[xyBottom]) {
                quickUnionUF.union(xyBottom, xy);

                int inverseXyBottom = this.xyTo1D(inverseI-1, j);
                quickUnionUF.union(inverseXy, inverseXyBottom);
            }

            if (xyLeft >= 0 && grid[xyLeft]) {
                quickUnionUF.union(xy, xyLeft);

                // inverse
                int inverseXyLeft = j == 1 ? -1 : this.xyTo1D(inverseI, j-1);
                if (inverseXyLeft >= 0) {
                    quickUnionUF.union(inverseXy, inverseXyLeft);
                }
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
        return quickUnionUF.connected(0, 2*N*N+1);
    }

    // mapping 2D coordinates to 1D coordinates
    private int xyTo1D(int x, int y) {

        return y + ((x-1) * N);
    }

}