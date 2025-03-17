import java.util.Scanner;

public class Percolation extends WeightedQuickUnionWithPathCompression {
	private final int[][] grid; // informs whether a site is open or closed
	private final int head_site;
	private final int tail_site;
	private int open_sites_count;

	/*
	 * creates n-by-n grid, with all sites initially blocked
	 * */
	public Percolation(int n) {
		// 2 extra sites for head(above row 0) & tail (below n-1 row)
		super((n * n) + 2);
		head_site = n * n;
		tail_site = head_site + 1;
		open_sites_count = 0;
		grid = new int[n][n];
	}

	// opens the site (row, col) if it is not open already
	public void open(int row, int col) {
		// ensure indices are within array bounds
		if (row < 0 || col < 0 || row >= grid.length || col >= grid.length)
			throw new IllegalArgumentException();

		// return early if site is already open
		if (isOpen(row, col)) return;

		// open the site
		grid[row][col] = 1;
		open_sites_count += 1;

		// connect it with the existing open sites in its neighborhood
		int cell_index = (grid.length * row) + col;
		if ((row - 1) >= 0 && isOpen(row - 1, col)) {
			int upper_cell_index = (grid.length * (row - 1)) + col;
			union(roots[cell_index], roots[upper_cell_index]);
		}
		if ((row + 1) < grid.length && isOpen(row + 1, col)) {
			int lower_cell_index = (grid.length * (row + 1)) + col;
			union(roots[cell_index], roots[lower_cell_index]);
		}
		if ((col - 1) >= 0 && isOpen(row, col - 1)) {
			int left_cell_index = (grid.length * row) + col - 1;
			union(roots[cell_index], roots[left_cell_index]);
		}
		if ((col + 1) < grid.length && isOpen(row, col + 1)) {
			int right_cell_index = (grid.length * row) + col + 1;
			union(roots[cell_index], roots[right_cell_index]);
		}

		// connect it with the virtual sites
		if (row == 0) {
			union(roots[cell_index], head_site);
		} else if (row == grid.length - 1) {
			union(roots[cell_index], tail_site);
		}
	}

	// is the site (row, col) open?
	public boolean isOpen(int row, int col) {
		if (row < 0 || col < 0 || row >= grid.length || col >= grid.length)
			throw new IllegalArgumentException();
		return grid[row][col] == 1;
	}

	// is the site (row, col) full?
	public boolean isFull(int row, int col) {
		if (row < 0 || col < 0 || row >= grid.length || col >= grid.length)
			throw new IllegalArgumentException();
		int cell_index = (grid.length * row) + col;
		return find(cell_index, head_site);
	}

	// returns the number of open sites
	public int numberOfOpenSites() {
		return open_sites_count;
	}

	// does the system percolate?
	public boolean percolates() {
		return find(head_site, tail_site);
	}

	// test client (optional)
	public static void main(String[] args) {
		final Scanner in = new Scanner(System.in);
		System.out.println("Enter the grid size(n)");
		final int n = in.nextInt();
		final Percolation percolation = new Percolation(n);

		System.out.println("=> To open a site, use command 'open [row] [column]' where row and column are the grid indices ranging from 0 to (n-1)");
		System.out.println("=> To check whether a site is open or not, use command 'check_open [row] [column]' where row and column are the grid indices ranging from 0 to (n-1)");
		System.out.println("=> To check whether a site is fully open or not, use command 'check_full_open [row] [column]' where row and column are the grid indices ranging from 0 to (n-1)");
		System.out.println("=> To get the number of open sites, use command 'count_open_sites'");
		System.out.println("=> To check whether the grid percolates, use command 'percolates'");

		while (in.hasNext()) {
			final StringBuilder message = new StringBuilder();
			final String[] inputs = in.nextLine().split(" ");
			switch (inputs[0]) {
				case "open":
					percolation.open(Integer.parseInt(inputs[1]), Integer.parseInt(inputs[2]));
					message.append("done");
					break;
				case "check_open":
					if (percolation.isOpen(Integer.parseInt(inputs[1]), Integer.parseInt(inputs[2]))) {
						message.append("Site is open");
					} else {
						message.append("Site is NOT open");
					}
					break;
				case "check_full_open":
					if (percolation.isFull(Integer.parseInt(inputs[1]), Integer.parseInt(inputs[2]))) {
						message.append("Site is full");
					} else {
						message.append("Site is NOT full");
					}
					break;
				case "count_open_sites":
					message.append("Currently ").append(percolation.numberOfOpenSites()).append(" sites are open");
					break;
				case "percolates":
					message.append((percolation.percolates() ? "yes" : "no"));
					break;
				default:
					message.append("Unknown operation!");
					break;
			}
			System.out.println(message);
		}
		in.close();
	}
}
