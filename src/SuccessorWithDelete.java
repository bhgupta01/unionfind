/*
 * Successor with delete. Given a set of n integers S={0,1,...,n−1} and a sequence of requests of the following form:
 * Remove x from S
 * Find the successor of x: the smallest y in S such that y≥x. Design a data type so that all operations (except construction) take logarithmic time or better in the worst case.
 * e.g. {4,3,8,6,5,9,2,1,0,7}
 * */

import java.util.Arrays;
import java.util.Scanner;

public class SuccessorWithDelete extends QuickUnionWithPathCompression {
	public SuccessorWithDelete(int N) {
		super(N);
	}

	public void remove(int x) {
		union(x, x + 1);
	}

	public int successor(int x) {
		return root(x);
	}

	protected void print() {
		System.out.println(Arrays.toString(roots));
	}

	public static void main(String[] args) {
		final Scanner in = new Scanner(System.in);

		final int n = in.nextInt();
		final SuccessorWithDelete swd = new SuccessorWithDelete(n);
		while (in.hasNextInt()) {
			final int x = in.nextInt();
			swd.remove(x);
			swd.print();
			System.out.println("Successor of " + x + " is " + swd.successor(x));
		}
		in.close();
	}
}
