public class QuickUnion {
    protected final int[] roots;
    public QuickUnion(int N) {
        roots = new int[N];
        for(int i = 0; i < N; i++) roots[i] = i;
    } // N array accesses

    public void union(int p, int q) {
        int rootP = root(p);
        int rootQ = root(q);
        if (rootP == -1) return;
        roots[rootP] = rootQ;
    } // N array accesses (worst case)

    public boolean find(int p, int q) {
        return root(p) == root(q);
    } // N array accesses (worst case)

    protected int root(int i) {
        while(i != roots[i]) i = roots[i];
        return i;
    } // N array accesses (worst case)
}
