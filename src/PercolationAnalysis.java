
public class PercolationAnalysis
{
	private static final int[] sizes = { 10, 25, 50, 100, 250, 500 };

	public static void main(String[] args)
	{
		System.out.println("========================fast========================");
		for (int n : sizes)
		{
			PercolationStats ps = new PercolationStats(n, 30, PercolationStats::fast);
			System.out.println(n + " : " + ps.meanTime() + " : " + ps.mean());
		}
		System.out.println();
		System.out.println();
		System.out.println("========================slow========================");
		for (int n : sizes)
		{
			PercolationStats ps = new PercolationStats(n, 30, PercolationStats::slow);
			System.out.println(n + " : " + ps.meanTime() + " : " + ps.mean());
		}
	}
}
