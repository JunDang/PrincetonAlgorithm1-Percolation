import edu.princeton.cs.algs4.WeightedQuickUnionUF;
public class Percolation {
    private boolean [][] grid;
    private WeightedQuickUnionUF uf;
    private WeightedQuickUnionUF ufoverall;
    private int size;
    private int virtualTop;
    private int virtualBottom;
    
    public Percolation(int N) {
        if (N <= 0) {
            throw new java.lang.IllegalArgumentException();
            
        }
        grid =  new boolean[N][N];
        size = N;
        uf = new WeightedQuickUnionUF(N * N + 1);
        ufoverall = new WeightedQuickUnionUF(N * N + 2);
        virtualTop = size * size;
        virtualBottom = virtualTop +  1;
        for (int i = 0; i <= N-1; i++) {
            for (int j = 0; j <= N-1; j++) {
                grid[i][j] = false;
            }
        }
                        
    }            
    public void open(int ii, int jj) { 
        int i = ii - 1;
        int j = jj - 1;
                
        if (i < 0 || i >= size || j < 0 || j >= size) {
           throw new java.lang.IndexOutOfBoundsException();
        }
        if (grid[i][j]) {
           return;
        }
        grid[i][j] = true;
        int index = i * size + j;
        //connect top with virtualTop
        if (i == 0 && !(uf.connected(index, virtualTop))) {
            uf.union(index,  virtualTop);
            ufoverall.union(index, virtualTop);
        }
        //connect bottom with virtualBottom
        if (i == size - 1) {
            ufoverall.union(index,  virtualBottom);
            
        }
        
        if (i > 0 && grid[i-1][j]) {
            uf.union(index, (i-1) * size + j);
            ufoverall.union(index, (i-1) * size + j);
        }
        if ((i < size - 1) && grid[i+1][j]) {
            uf.union(index, (i + 1) * size + j);
            ufoverall.union(index, (i + 1) * size + j);
        }
        if (j > 0 && grid[i][j-1]) {
            uf.union(index, i * size + j - 1);
            ufoverall.union(index, i * size + j - 1);
                    
        }
        if ((j < size - 1) && grid[i][j+1]) {
            uf.union(index, i * size + j + 1);
            ufoverall.union(index, i * size + j + 1);
        }
    }
    public boolean isOpen(int i, int j) {
                
        if (i < 1 || i > size || j < 1 || j > size) {            
            throw new java.lang.IndexOutOfBoundsException();
        }
        return grid[i - 1][j - 1];
    }
    public boolean isFull(int i, int j) {
        if (i < 1 || i > size || j < 1 || j > size) {
            throw new java.lang.IndexOutOfBoundsException();
        }
       
        if (!isOpen(i, j)) {
            return false;
        }
        if (i == 1) {
            return true;
        }
        int index = (i - 1) * size + j - 1;
        return uf.connected(index, virtualTop);
   }
    public boolean percolates() {
        if (size == 1) {
            return grid[0][0];
        }
        return ufoverall.connected(virtualTop, virtualBottom);                        
    }
}            