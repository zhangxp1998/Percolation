import java.util.BitSet;
import java.util.Scanner;

public class Percolation
{

	private final int N;
	private BitSet grid;
	private int last;
	private WeightedQuickUnionPathCompression uf;
	private WeightedQuickUnionPathCompression bf;

	public Percolation(int n) // create n-by-n grid, with all sites blocked
	{
		if (n <= 0)
		{
			throw new IllegalArgumentException();
		}

		this.N = n;
		uf = new WeightedQuickUnionPathCompression(n * n + 2);
		bf = new WeightedQuickUnionPathCompression(n * n + 1);

		last = N * N + 1;
		grid = new BitSet(N * N + 2);

		// Open the virtual top and virtual bottom site
		grid.set(0);
		grid.set(last);
	}

	private boolean inBound(int position)
	{
		return position >= 0 && position < N;
	}

	private int toIndex(int row, int col)
	{
		assert (inBound(row));
		assert (inBound(col));

		// -1 because the input is 1 based
		return 1 + row * N + col;
	}

	public void open(int row, int col) // open site (row, col) if it is not open
										// already
	{
		assert inBound(row);
		assert inBound(col);

		int i = toIndex(row, col);
		grid.set(i);

		// If it is the first row, connect it with the virtual top
		if (row == 0)
		{
			uf.union(0, i);
			bf.union(0, i);
		}
		// If it is the last row, connect it wit the virtual bottom
		if (row == N - 1)
		{
			uf.union(last, i);
		}

		// Connect nearby open sites
		// Up
		if (row > 0 && isOpen(row - 1, col))
		{
			uf.union(i, toIndex(row - 1, col));
			bf.union(i, toIndex(row - 1, col));
		}

		// Down
		if (row < N - 1 && isOpen(row + 1, col))
		{
			uf.union(i, toIndex(row + 1, col));
			bf.union(i, toIndex(row + 1, col));
		}

		// Left
		if (col > 0 && isOpen(row, col - 1))
		{
			uf.union(i, toIndex(row, col - 1));
			bf.union(i, toIndex(row, col - 1));
		}

		// Right
		if (col < N - 1 && isOpen(row, col + 1))
		{
			uf.union(i, toIndex(row, col + 1));
			bf.union(i, toIndex(row, col + 1));
		}
	}

	private boolean isOpen(int index)
	{
		return grid.get(index);
	}

	public boolean isOpen(int row, int col) // is site (row, col) open?
	{
		assert inBound(row);
		assert inBound(col);

		return isOpen(toIndex(row, col));
	}

	public boolean isFull(int row, int col) // is site (row, col) full?
	{
		int i = toIndex(row, col);
		return isOpen(i) && bf.connected(0, i);
	}

	public boolean percolates() // does the system percolate?
	{
		return uf.connected(0, last);
	}

	public static void main(String[] args) // test client (optional)
	{
		Scanner in = new Scanner(System.in);
		final int N = Integer.parseInt(in.nextLine());
		Percolation p = new Percolation(N);
		while (in.hasNextLine())
		{
			int col = in.nextInt();
			int row = in.nextInt();
			in.nextLine();
			p.open(row, col);
		}
		in.close();
		System.out.println(p.percolates() ? "YES" : "NO");
	}
}