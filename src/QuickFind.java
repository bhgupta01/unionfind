public class QuickFind {
    private final int[] objects;
    public QuickFind(int N) {
        objects = new int[N];
        for(int i = 0; i < N; i++) objects[i] = i;
    } // N array accesses

    public void union(int p, int q) {
        if (find(p, q)) return;
        int current = objects[p];
        for (int i = 0; i < objects.length; i++) {
            if (objects[i] == current) objects[i] = objects[q];
        }
    } // N array accesses

    public boolean find(int p, int q) {
        return objects[p] == objects[q];
    } // 1 array accesses
}
