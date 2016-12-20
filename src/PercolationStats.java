import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.StdStats;

public class PercolationStats
{
	private static final double Z_STAR = 1.96;

	private double[] data;
	private final int N;

	private double mean;
	private double stddev;

	private static void openRandomSpot(Percolation p, int N)
	{
		int y = StdRandom.uniform(1, N + 1);
		int x = StdRandom.uniform(1, N + 1);

		for (int i = y; i <= N; i++)
		{
			for (int j = x; j <= N; j++)
			{
				if (!p.isOpen(i, j))
				{
					p.open(i, j);
					return;
				}
			}
		}

		for (int i = y; i >= 1; i--)
		{
			for (int j = x; j >= 1; j--)
			{
				if (!p.isOpen(i, j))
				{
					p.open(i, j);
					return;
				}
			}
		}

		assert false;
	}

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
				openRandomSpot(p, n);
				count++;
			}
			data[i] = count * 1.0 / n / n;
		}
		this.N = trials;
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
		return mean - Z_STAR * stddev / Math.sqrt(N);
	}

	public double confidenceHi() // high endpoint of 95% confidence interval
	{
		return mean + Z_STAR * stddev / Math.sqrt(N);
	}

	public static void main(String[] args) // test client (described below)
	{
		assert args.length == 2;
		final int n = Integer.parseInt(args[0]);
		final int T = Integer.parseInt(args[1]);

		PercolationStats stats = new PercolationStats(n, T);
		System.out.printf("%f\n", stats.mean());
		System.out.printf("%f\n", stats.stddev());
		System.out.printf("%f, %f\n", stats.confidenceLo(), stats.confidenceHi());
	}
}