/*
 * Social network connectivity. Given a social network containing n members
 * and a log file containing m timestamps at which times pairs of members
 * formed friendships, design an algorithm to determine the earliest time at
 * which all members are connected (i.e., every member is a friend of a friend
 * of a friend ... of a friend).
 * Assume that the log file is sorted by timestamp and that friendship is an
 * equivalence relation. The running time of your algorithm should be mlogn or
 * better and use extra space proportional to n.
 * */

/*
 * Modify weighted quick union with path compression as below.
 * Use an extra variable max_weight initialized to value 1 in constructor.
 * Whenever two members are connected, compare the value of max_weight with
 * the weight of united network. Assign max_weight the weight of united network
 * if it's current value is smaller. For a given network of n members, the max
 * weight can be n when all the members are connected.
 * */

import java.util.Scanner;

public class SocialNetworkConnectivity extends WeightedQuickUnionWithPathCompression {
    private int max_weight;
    public SocialNetworkConnectivity(int N) {
        super(N);
        max_weight = 1;
    } // N array accesses

    public void union(int p, int q) {
        int rootP = root(p);
        int rootQ = root(q);
        if (rootP == rootQ) return;

        if (weights[rootP] > weights[rootQ]) {
            roots[rootQ] = rootP;
            weights[rootP] += weights[rootQ];
            if (weights[rootP] > max_weight) max_weight = weights[rootP];
        } else {
            roots[rootP] = rootQ;
            weights[rootQ] += weights[rootP];
            if (weights[rootQ] > max_weight) max_weight = weights[rootQ];
        }
    } // lgN array accesses

    public boolean all_connected() {
        return max_weight == roots.length;
    } // 1 array accesses

    public static void main(String[] args) {
        final Scanner in = new Scanner(System.in);

        final int n = in.nextInt();
        final SocialNetworkConnectivity snc = new SocialNetworkConnectivity(n);
        while (in.hasNextInt()) {
            final int p = in.nextInt();
            final int q = in.nextInt();
            snc.union(p,q);
            if (snc.all_connected()) {
                System.out.printf("With the friendship of %s and %s, all the members are now connected.%n", p, q);
                break;
            }
        }
        in.close();
    }
}
