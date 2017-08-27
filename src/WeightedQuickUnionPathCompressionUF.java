/******************************************************************************
 *  Compilation:  javac WeightedQuickUnionPathCompressionUF.java
 *  Execution:  java WeightedQuickUnionPathCompressionUF < input.txt
 *  Dependencies: StdIn.java StdOut.java
 *  Data files:   http://algs4.cs.princeton.edu/15uf/tinyUF.txt
 *                http://algs4.cs.princeton.edu/15uf/mediumUF.txt
 *                http://algs4.cs.princeton.edu/15uf/largeUF.txt
 *
 *  Weighted quick-union with path compression.
 *
 ******************************************************************************/

/**
 * The {@code WeightedQuickUnionPathCompressionUF} class represents a
 * union–find data structure. It supports the <em>union</em> and <em>find</em>
 * operations, along with methods for determining whether two sites are in the
 * same component and the total number of components.
 * <p>
 * This implementation uses weighted quick union (by size) with full path
 * compression. Initializing a data structure with <em>n</em> sites takes linear
 * time. Afterwards, <em>union</em>, <em>find</em>, and <em>connected</em> take
 * logarithmic time (in the worst case) and <em>count</em> takes constant time.
 * Moreover, the amortized time per <em>union</em>, <em>find</em>, and
 * <em>connected</em> operation has inverse Ackermann complexity.
 * <p>
 * For additional documentation, see
 * <a href="http://algs4.cs.princeton.edu/15uf">Section 1.5</a> of
 * <i>Algorithms, 4th Edition</i> by Robert Sedgewick and Kevin Wayne.
 * 
 * @author Robert Sedgewick
 * @author Kevin Wayne
 */
public class WeightedQuickUnionPathCompressionUF implements UnionFind
{
	private int[] parent; // parent[i] = parent of i
	private int[] size; // size[i] = number of sites in tree rooted at i
						// Note: not necessarily correct if i is not a root node
	private int count; // number of components

	/**
	 * Initializes an empty union–find data structure with {@code n} sites
	 * {@code 0} through {@code n-1}. Each site is initially in its own component.
	 *
	 * @param n
	 *            the number of sites
	 * @throws IllegalArgumentException
	 *             if {@code n < 0}
	 */
	public WeightedQuickUnionPathCompressionUF(int n)
	{
		count = n;
		parent = new int[n];
		size = new int[n];
		for (int i = 0; i < n; i++)
		{
			parent[i] = i;
			size[i] = 1;
		}
	}

	public int count()
	{
		return count;
	}

	public int find(int p)
	{
		validate(p);
		int root = p;
		while (root != parent[root])
			root = parent[root];
		while (p != root)
		{
			int newp = parent[p];
			parent[p] = root;
			p = newp;
		}
		return root;
	}

	// validate that p is a valid index
	private void validate(int p)
	{
		int n = parent.length;
		if (p < 0 || p >= n)
		{
			throw new IllegalArgumentException("index " + p + " is not between 0 and " + (n - 1));
		}
	}

	public void union(int p, int q)
	{
		int rootP = find(p);
		int rootQ = find(q);
		if (rootP == rootQ)
			return;

		// make smaller root point to larger one
		if (size[rootP] < size[rootQ])
		{
			parent[rootP] = rootQ;
			size[rootQ] += size[rootP];
		} else
		{
			parent[rootQ] = rootP;
			size[rootP] += size[rootQ];
		}
		count--;
	}
}
