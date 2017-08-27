import java.util.Arrays;
import java.util.Random;

public class PercolationStats
{
	private static final double Z_STAR = 1.96;
	private static final Random rand = new Random();

	private double[] data;
	// Time it took to run each experiment
	private double[] times;

	private double mean;
	private double stddev;

	private double timeStddev;

	// Total time in nanoseconds
	private double timeMean;

	public PercolationStats(int n, int trials) // perform trials independent
												// experiments on an n-by-n grid
	{
		if (n <= 0 || trials <= 0)
			throw new IllegalArgumentException();

		data = new double[trials];
		times = new double[trials];
		for (int i = 0; i < trials; i++)
		{
			long start = System.nanoTime();
			Percolation p = new Percolation(n);
			int count = 0;
			while (!p.percolates())
			{
				int row = rand.nextInt(n);
				int column = rand.nextInt(n);
				if (p.isOpen(row, column))
					continue;
				p.open(row, column);
				count++;
			}
			times[i] = System.nanoTime() - start;
			data[i] = count * 1.0 / n / n;
		}
		timeMean = Arrays.stream(times).average().getAsDouble();
		timeStddev = stddev(times, timeMean);

		mean = Arrays.stream(data).average().getAsDouble();
		stddev = stddev(data, mean);
	}

	private static double stddev(double[] arr, double mean)
	{
		double stddev = Arrays.stream(arr).map(d -> d - mean).map(d -> d * d).sum();
		stddev /= arr.length - 1;
		return Math.sqrt(stddev);
	}

	public double mean() // sample mean of percolation threshold
	{
		return mean;
	}

	public double stddev() // sample standard deviation of percolation threshold
	{
		return stddev;
	}

	public double totalTime()
	{
		return timeMean * times.length / 1e+9D;
	}

	public double meanTime()
	{
		return timeMean / 1e+9D;
	}

	public double stddevTime()
	{
		return timeStddev / 1e+9D;
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
		assert args.length >= 2;
		final int n = Integer.parseInt(args[0]);
		final int T = Integer.parseInt(args[1]);

		PercolationStats stats = new PercolationStats(n, T);
		System.out.printf("mean threshold=%f\n", stats.mean());
		System.out.printf("std dev=%f\n", stats.stddev());
		System.out.printf("time=%f\n", stats.totalTime());
		System.out.printf("stddev time=%f\n", stats.stddevTime());
	}
}