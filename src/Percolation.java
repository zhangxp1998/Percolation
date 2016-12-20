import java.util.BitSet;

import edu.princeton.cs.algs4.WeightedQuickUnionUF;

public class Percolation
{
	private final int N;
	private BitSet grid;
	private int last;

	private WeightedQuickUnionUF union;

	public Percolation(int n) // create n-by-n grid, with all sites blocked
	{
		if (n <= 0)
		{
			throw new IllegalArgumentException();
		}

		this.N = n;
		union = new WeightedQuickUnionUF(n * n + 2);

		last = N * N + 1;
		grid = new BitSet(N * N + 2);
		// sz = new int[grid.length];

		// Arrays.fill(grid, -1);

		grid.set(0);
		// sz[0] = 1;
		// 0 is the virtual top cell, root of all first row cells
		// Arrays.fill(grid, 1, N + 1, 0);

		grid.set(last);
		// sz[last] = 1;
		// Last is the virtual bottom cell, root of all bottom row cells
		// Arrays.fill(grid, toIndex(N - 1, 0), last, last);
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

	// private void union(int a, int b)
	// {
	// assert a >= 0 && a < grid.length;
	// assert b >= 0 && b < grid.length;
	//
	// int i = root(a);
	// int j = root(b);
	//
	// // Make the smaller one a subtree of the other
	// if (sz[i] > sz[j])
	// {
	// grid[j] = i;
	// sz[i] += sz[j];
	// } else
	// {
	// grid[i] = j;
	// sz[j] += sz[i];
	// }
	// }

	public void open(int row, int col) // open site (row, col) if it is not open
										// already
	{
		if (row < 1 || row > N || col < 1 || col > N)
		{
			throw new IndexOutOfBoundsException();
		}
		int i = toIndex(row, col);
		grid.set(i);

		if (row == 1)
		{
			// union(0, i);
			union.union(0, i);
		}
		if (row == N)
		{
			// union(last, i);
			union.union(last, i);
		}

		// Connect nearby open sites

		// Up
		if (row > 1 && isOpen(row - 1, col))
		{
			union.union(i, toIndex(row - 1, col));
		}

		// Down
		if (row < N && isOpen(row + 1, col))
		{
			union.union(i, toIndex(row + 1, col));
		}

		// Left
		if (col > 1 && isOpen(row, col - 1))
		{
			union.union(i, toIndex(row, col - 1));
		}

		// Right
		if (col < N && isOpen(row, col + 1))
		{
			union.union(i, toIndex(row, col + 1));
		}
	}

	public boolean isOpen(int row, int col) // is site (row, col) open?
	{
		if (row < 1 || row > N || col < 1 || col > N)
		{
			throw new IndexOutOfBoundsException();
		}
		return grid.get(toIndex(row, col));
	}

	// private int root(int i)
	// {
	// assert i >= 0 || i < grid.length;
	//
	// // Eventually this will use O(log N) space.
	// Stack<Integer> path = new Stack<Integer>();
	// while (grid[i] != i)
	// {
	// path.push(i);// Record the path as we go
	// i = grid[i];
	// }
	//
	// // Completely flattens the tree
	// while (!path.isEmpty())
	// {
	// grid[path.pop()] = i;
	// }
	// return i;
	// }

	private boolean isConnected(int a, int b)
	{
		return union.connected(a, b);
		// return root(a) == root(b);
	}

	public boolean isFull(int row, int col) // is site (row, col) full?
	{
		return isOpen(row, col) && isConnected(0, toIndex(row, col));
	}

	public boolean percolates() // does the system percolate?
	{
		return isConnected(0, last);
	}

	public static void main(String[] args) // test client (optional)
	{

	}
}