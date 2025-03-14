public class WeightedQuickUnionWithPathCompression extends WeightedQuickUnion {
    public WeightedQuickUnionWithPathCompression(int N) {
        super(N);
    }

    protected int root(int i) {
        while(i != roots[i]) {
            // path compression, point to one's grandparent
            roots[i] = roots[roots[i]];
            i = roots[i];
        }
        return i;
    } // lgN array accesses
}
