/******************************************************************************
 *  
 *  Compilation:  javac-algs4 PercolationStats.java
 *  Execution:    java-algs4 PercolationStats grid_size number_of_trials
 *  Dependencies: Percolation.java
 *
 *  This program takes the grid size and number of trials for MonteCarlo simulation 
 *  as command-line arguments.
 *
 *  Refer 
 *  https://coursera.cs.princeton.edu/algs4/assignments/percolation/specification.php
 *
 ******************************************************************************/

import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.StdStats;
import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdOut;

public class PercolationStats {

    /* private member to track the percolation threshold values for all trials */
    private static final double CONFIDENCE_95 = 1.96;
    private final double[] trialVal;
    private double mean;
    private double stddev;

    /* perform independent trials on an n X n grid */
    public PercolationStats(int n, int trials) {
        if (n <= 0 || trials <= 0) 
            throw new IllegalArgumentException("Bad argument grid size(n) or number of trials\n");
        this.trialVal = new double[trials];
        for (int i = 0; i < trials; i++) {
            Percolation p = new Percolation(n);
            while (!p.percolates()) {
                int row = StdRandom.uniform(1, n+1);
                int col = StdRandom.uniform(1, n+1);
                if (!p.isOpen(row, col))
                    p.open(row, col);
            }
            this.trialVal[i] = ((double) p.numberOfOpenSites() / (double) (n * n));
        }
        this.mean = 0d;
        this.stddev = 0d;
    }

    /* sample mean of percolation threshold */
    public double mean() {
        this.mean = StdStats.mean(this.trialVal);
        return this.mean;
    }

    /* sample std deviation of percolation threshold */
    public double stddev() {
        this.stddev = StdStats.stddev(this.trialVal);
        return this.stddev;
    }

    /* low endpoint of 95% confidence interval */
    public double confidenceLo() {
        double m = (this.mean == 0) ? this.mean() : this.mean;
        double s = (this.stddev == 0) ? this.stddev() : this.stddev;
        return (m - ((CONFIDENCE_95 * s) / Math.sqrt(this.trialVal.length)));
    }

    /* high endpoint of 95% confidence interval */
    public double confidenceHi() {
        double m = (this.mean == 0) ? this.mean() : this.mean;
        double s = (this.stddev == 0) ? this.stddev() : this.stddev;
        return (m + ((CONFIDENCE_95 * s) / Math.sqrt(this.trialVal.length)));
        
    }

    /* test client */
    public static void main(String[] args)
    {
        int n = StdIn.readInt();
        int t = StdIn.readInt();
        PercolationStats ps = new PercolationStats(n, t);
        StdOut.println("mean = "+ps.mean());
        StdOut.println("stddev = "+ps.stddev());
        StdOut.println("95% confidence interval = ["+ps.confidenceLo()+", "+ps.confidenceHi()+"]");
    }

}
