import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.StdOut;

import edu.princeton.cs.algs4.StdStats;

public class PercolationStats {
    private int      N;
    private int      T;
    private double   mean    = 0;
    private double   stddev  = 0;
    private double   conLo   = 0;
    private double   conHigh = 0;
    private double[] timesToPerc;

    public PercolationStats(int N, int T) {
        if (N <= 0 || T <= 0) {
            throw new IllegalArgumentException("wrong number");
        }
        this.N = N;
        this.T = T;
        this.timesToPerc = new double[T];

        int counter = 0;

        while (counter < T) {
            Percolation p = new Percolation(N);
            int times = 0;

            while (!p.percolates()) {
                int n = StdRandom.uniform(1, N + 1);
                int n2 = StdRandom.uniform(1, N + 1);
                if (!p.isOpen(n, n2)) {
                    p.open(n, n2);
                    times++;
                }
            }
            double d = (double) (1.0 * times / (N * N));
            timesToPerc[counter] = d;
            counter++;

        }
    }

    public double mean() {
        mean = StdStats.mean(timesToPerc);
        return mean;
    }

    public double stddev() {
        stddev = StdStats.stddev(timesToPerc);
        return stddev;
    }

    public double confidenceLo() {
        conLo = mean() - ((1.96 * stddev()) / Math.sqrt(T));
        return conLo;
    }

    public double confidenceHi() {
        conHigh = mean() + ((1.96 * stddev()) / Math.sqrt(T));
        return conHigh;
    }

    public static void main(String[] args) {
        int one = Integer.parseInt(args[0]);
        int two = Integer.parseInt(args[1]);
        PercolationStats ps = new PercolationStats(one, two);
        StdOut.print("Standard Mean: \t\t" + ps.mean());
        StdOut.println();
        StdOut.print("Standard Deviation: \t" + ps.stddev());
        StdOut.println();
        StdOut.print("95% Confidence: \t" + ps.confidenceLo() + ", "
                + ps.confidenceHi());
        StdOut.println();

    }

}
