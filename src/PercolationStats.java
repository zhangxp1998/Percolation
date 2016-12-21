import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.StdStats;

public class PercolationStats
{
	private static final double Z_STAR = 1.96;

	private double[] data;

	private double mean;
	private double stddev;

	public PercolationStats(int n, int trials) // perform trials independent
												// experiments on an n-by-n grid
	{
		if (n <= 0 || trials <= 0)
		{
			throw new IllegalArgumentException();
		}

		data = new double[trials];
		for (int i = 0; i < trials; i++)
		{
			Percolation p = new Percolation(n);
			int count = 0;
			while (!p.percolates())
			{
				int row = StdRandom.uniform(1, n + 1);
				int column = StdRandom.uniform(1, n + 1);
				if (p.isOpen(row, column))
					continue;
				p.open(row, column);
				count++;
			}
			data[i] = count * 1.0 / n / n;
		}

		mean = StdStats.mean(data);
		stddev = StdStats.stddev(data);
	}

	public double mean() // sample mean of percolation threshold
	{
		return mean;
	}

	public double stddev() // sample standard deviation of percolation threshold
	{
		return stddev;
	}

	public double confidenceLo() // low endpoint of 95% confidence interval
	{
		return mean - Z_STAR * stddev / Math.sqrt(data.length);
	}

	public double confidenceHi() // high endpoint of 95% confidence interval
	{
		return mean + Z_STAR * stddev / Math.sqrt(data.length);
	}

	public static void main(String[] args) // test client (described below)
	{
		assert args.length == 2;
		final int n = Integer.parseInt(args[0]);
		final int T = Integer.parseInt(args[1]);

		PercolationStats stats = new PercolationStats(n, T);
		System.out.printf("MEAN:\t%f\n", stats.mean());
		System.out.printf("STDDEV:\t%f\n", stats.stddev());
		System.out.printf("ZINTERVAL:\t%f, %f\n", stats.confidenceLo(), stats.confidenceHi());
	}
}