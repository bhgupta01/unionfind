/**
 * Path compression improves the complexity of union & connected operations
 * from N to lgN
 */
public class QuickUnionWithPathCompression extends QuickUnion {
    public QuickUnionWithPathCompression(int N) {
        super(N);
    }

    protected int root(int i) {
        if (i < 0 || i >= roots.length) return -1;
        if (i == roots[i]) return i;
        // path compression, point to one's grandparent
        int j = roots[i];
        if (j < 0) return j;
        roots[i] = roots[j];
        return root(roots[i]);
    } // lgN array accesses
}
