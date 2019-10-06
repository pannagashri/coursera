/* *****************************************************************************
 *  
 *  Compilation:  javac-algs4 Percolation.java
 *  Execution:    java-algs4 Percolation
 *  Dependencies: Percolation.java
 *
 *  This program takes the grid size as a command-line argument.
 *
 *  You can also use PercolationVisualizer.java and 
 *  InteractivePercolationVisualizer.java to test.
 *
 *  Refer 
 *  https://coursera.cs.princeton.edu/algs4/assignments/percolation/specification.php
 *  for input files and test utils.
 *
 * *****************************************************************************/

import edu.princeton.cs.algs4.WeightedQuickUnionUF;
import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdOut;

public class Percolation {

    private final WeightedQuickUnionUF wquf;
    private final WeightedQuickUnionUF wqufPvt;
    private final int n;
    private final int top;
    private final int bottom;
    /* Site state can be 0 - blocked, 1 - open */
    private byte[] siteState;

    /* Constructor to init n x n grid with all sites blocked */
    public Percolation(int n) {
        if (n < 1) 
            throw new IllegalArgumentException("Value of n should be more than 0");
        this.n = n;
        /* Keeping two extra members for the virtual sites */
        this.wquf = new WeightedQuickUnionUF((n * n) + 2);
        this.wqufPvt = new WeightedQuickUnionUF((n * n) + 2);
        this.siteState = new byte[(n * n)];
        this.top = n * n;
        this.bottom = (n * n) + 1;
    }


    /* Helper method to convert two dimensional coordinates to 
     * one dimensional coordinates */
    private int convertTwoDtoOneD(int row, int col)
    {
        return ((row - 1) + ((col - 1) * this.n));
    }

    /* Helper method to check the state and connect two sites using call to union() */ 
    private void checkStateAndConnect(int row, int col, int p)
    {
        int q = this.convertTwoDtoOneD(row, col);
        if (this.siteState[q] == 1) {
            this.wquf.union(p, q);
            this.wqufPvt.union(p, q);
            }
    }


    /* Helper method to check for errors */
    private void errorChecks(int row, int col)
    {
        if (row < 1 || row > this.n || col < 1 || col > this.n)
            throw new IllegalArgumentException("Row or col is invalid: It cannot be <= 0 or > n\n");
    }


    /* Open the site (row, col) if not open already */
    public void open(int row, int col)
    {
        this.errorChecks(row, col);
        if (!isOpen(row, col)) {
            int p = this.convertTwoDtoOneD(row, col);
            this.siteState[p] = 1;
            /* If this is the topmost row, check and connect to top virtual site */
            if (row == 1 && !wquf.connected(p, top)) {
                wquf.union(p, top);
                wqufPvt.union(p, top);
            }

            /* If this is the bottom row, connect it to bottom virtual site */
            if (row == n && !wqufPvt.connected(p, bottom)) {
                wqufPvt.union(p, bottom);
            }

            if (row != 1)
                this.checkStateAndConnect(row-1, col, p);
            if (row != this.n)
                this.checkStateAndConnect(row+1, col, p);
            if (col != 1)
                this.checkStateAndConnect(row, col-1, p);
            if (col != this.n)
                this.checkStateAndConnect(row, col+1, p);
        }
    }

    /* Is the site (row, col) open? */
    public boolean isOpen(int row, int col)
    {
        this.errorChecks(row, col);
        if (this.siteState[this.convertTwoDtoOneD(row, col)] == 1)
            return true;
        return false;
    }

    /* Return the number of open sites */
    public int numberOfOpenSites()
    {
        int sum = 0;
        for (int i = 0; i < this.n*this.n; i++)
            if (this.siteState[i] == 1)
                sum++;
        return sum;
    }

    public boolean isFull(int row, int col)
    {
        this.errorChecks(row, col);
        return (this.isOpen(row, col) && this.wquf.connected(top, this.convertTwoDtoOneD(row, col)));
    }

    public boolean percolates()
    {
        return wqufPvt.connected(top, bottom);
    }

    /* Test client */
    public static void main(String[] args)
    {
        StdOut.println("Enter the grid size n\n");
        Percolation p = new Percolation(StdIn.readInt());
        /* Now let's open a few sites and check */
        p.open(1, 1);
        p.open(2, 2);
        p.open(1, 2);
        StdOut.println("isFull(1,2)?: "+p.isFull(1, 2));
        StdOut.println("Percolates?: "+p.percolates());
    }
}
