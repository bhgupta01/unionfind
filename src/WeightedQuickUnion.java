public class WeightedQuickUnion extends QuickUnion {
    protected final int[] weights;
    public WeightedQuickUnion(int N) {
        super(N);
        weights = new int[N];
        for(int i = 0; i < N; i++) weights[i] = 1;
    } // N array accesses

    public void union(int p, int q) {
        int rootP = root(p);
        int rootQ = root(q);
        if (rootP == rootQ) return;

        if (weights[rootP] > weights[rootQ]) {
            roots[rootQ] = rootP;
            weights[rootP] += weights[rootQ];
        } else {
            roots[rootP] = rootQ;
            weights[rootQ] += weights[rootP];
        }
    } // lgN array accesses (includes the cost of finding roots)
}
