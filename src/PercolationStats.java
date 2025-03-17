import edu.princeton.cs.algs4.StdRandom;

import java.util.Arrays;
import java.util.Scanner;

public class PercolationStats {
	private final double[] thresholds;

	// perform independent trials on an n-by-n grid
	public PercolationStats(int n, int trials) {
		if (n <= 0 || trials <= 0) throw new IllegalArgumentException();
		thresholds = new double[trials];
	}

	// sample mean of percolation threshold
	public double mean() {
		return Arrays.stream(thresholds).sum() / thresholds.length;
	}

	// sample standard deviation of percolation threshold
	public double stddev() {
		double mean = mean();
		double variance = Arrays.stream(thresholds)
				.reduce(0.0, (a, b) -> a + Math.pow(b - mean, 2)) / (thresholds.length - 1);
		return Math.sqrt(variance);
	}

	// low endpoint of 95% confidence interval
	public double confidenceLo() {
		return mean() - (confidence_intermediate());
	}

	// high endpoint of 95% confidence interval
	public double confidenceHi() {
		return mean() + (confidence_intermediate());
	}

	private double confidence_intermediate() {
		double root_t = Math.sqrt(thresholds.length);
		double s = Math.sqrt(stddev());
		return 1.96 * s / root_t;
	}

	// test client (see below)
	public static void main(String[] args) {
		final Scanner in = new Scanner(System.in);

		final int grid_size = Integer.parseInt(args[0]);
		final double total_sites = Math.pow(grid_size, 2);
		final int trials_count = Integer.parseInt(args[1]);

		final PercolationStats ps = new PercolationStats(grid_size, trials_count);
		for (int i = 0; i < trials_count; i++) {
			final Percolation p = new Percolation(grid_size);
			do {
				p.open(StdRandom.uniformInt(grid_size), StdRandom.uniformInt(grid_size));
			} while (!p.percolates());
			ps.thresholds[i] = p.numberOfOpenSites() / total_sites;
		}

		String sb = "mean                    = " + ps.mean() +
				"\nstddev                  = " + ps.stddev() +
				"\n95% confidence interval = [" + ps.confidenceLo() +
				", " + ps.confidenceHi() + "]";
		System.out.println(sb);
		in.close();
	}

}
