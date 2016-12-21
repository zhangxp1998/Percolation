import java.util.BitSet;

import edu.princeton.cs.algs4.WeightedQuickUnionUF;

public class Percolation
{

	private final int N;
	private BitSet grid;
	private int last;
	private WeightedQuickUnionUF uf;
	private WeightedQuickUnionUF bf;

	public Percolation(int n) // create n-by-n grid, with all sites blocked
	{
		if (n <= 0)
		{
			throw new IllegalArgumentException();
		}

		this.N = n;
		uf = new WeightedQuickUnionUF(n * n + 2);
		bf = new WeightedQuickUnionUF(n * n + 1);

		last = N * N + 1;
		grid = new BitSet(N * N + 2);
		// sz = new int[grid.length];

		// Arrays.fill(grid, -1);
		grid.set(0);

		grid.set(last);
	}

	private boolean inBound(int position)
	{
		return position >= 1 && position <= N;
	}

	private int toIndex(int row, int col)
	{
		assert row >= 1;
		assert col >= 1;
		assert row <= N;
		assert col <= N;

		// -1 because the input is 1 based
		return 1 + (row - 1) * N + (col - 1);
	}

	public void open(int row, int col) // open site (row, col) if it is not open
										// already
	{
		assert inBound(row);
		assert inBound(col);
		

		int i = toIndex(row, col);
		grid.set(i);

		if (row == 1)
		{
			uf.union(0, i);
			bf.union(0, i);
		}
		if (row == N)
		{
			uf.union(last, i);
		}

		// Connect nearby open sites
		// Up
		if (row > 1 && isOpen(row - 1, col))
		{
			uf.union(i, toIndex(row - 1, col));
			bf.union(i, toIndex(row - 1, col));
		}

		// Down
		if (row < N && isOpen(row + 1, col))
		{
			uf.union(i, toIndex(row + 1, col));
			bf.union(i, toIndex(row + 1, col));
		}

		// Left
		if (col > 1 && isOpen(row, col - 1))
		{
			uf.union(i, toIndex(row, col - 1));
			bf.union(i, toIndex(row, col - 1));
		}

		// Right
		if (col < N && isOpen(row, col + 1))
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

	private boolean isConnected(int a, int b)
	{
		return uf.connected(a, b);
	}

	public boolean isFull(int row, int col) // is site (row, col) full?
	{
		assert inBound(row);
		assert inBound(col);
		
		int i = toIndex(row, col);
		return isOpen(i) && bf.connected(0, i);
	}

	public boolean percolates() // does the system percolate?
	{
		return isConnected(0, last);
	}

	public static void main(String[] args) // test client (optional)
	{

	}
}