
public interface UnionFind
{
	/**
	 * Returns the number of components.
	 *
	 * @return the number of components (between {@code 1} and {@code n})
	 */
	public int count();

	/**
	 * Returns the component identifier for the component containing site {@code p}.
	 *
	 * @param p
	 *            the integer representing one site
	 * @return the component identifier for the component containing site {@code p}
	 * @throws IllegalArgumentException
	 *             unless {@code 0 <= p < n}
	 */
	public int find(int p);

	/**
	 * Returns true if the the two sites are in the same component.
	 *
	 * @param p
	 *            the integer representing one site
	 * @param q
	 *            the integer representing the other site
	 * @return {@code true} if the two sites {@code p} and {@code q} are in the same
	 *         component; {@code false} otherwise
	 * @throws IllegalArgumentException
	 *             unless both {@code 0 <= p < n} and {@code 0 <= q < n}
	 */
	default public boolean connected(int p, int q)
	{
		return find(p) == find(q);
	}

	/**
	 * Merges the component containing site {@code p} with the the component
	 * containing site {@code q}.
	 *
	 * @param p
	 *            the integer representing one site
	 * @param q
	 *            the integer representing the other site
	 * @throws IllegalArgumentException
	 *             unless both {@code 0 <= p < n} and {@code 0 <= q < n}
	 */
	public void union(int p, int q);
}
