import java.util.Scanner;

/*
* Union-find with specific canonical element. Add a method find() to the
* union-find data type so that find(i) returns the largest element in the
* connected component containing i. The operations, union(), connected(), and
* find() should all take logarithmic time or better. For example, if one of the
* connected components is {1,2,6,9}, then the find() method should return 9 for
* each of the four elements in the connected components.
* */
public class UnionFindWithSpecificCanonicalElement extends QuickUnionWithPathCompression {
    public UnionFindWithSpecificCanonicalElement(int N) {
        super(N);
    }

    public void union(int p, int q) {
        int rootP = root(p);
        int rootQ = root(q);
        if (rootP == rootQ) return;

        // Keep the largest value always at root
        if (rootP > rootQ) {
            roots[rootQ] = rootP;
        } else {
            roots[rootP] = rootQ;
        }
    } // lgN array accesses due to path compression

    public int find(int i) {
        return root(i);
    } // lgN array accesses due to path compression

    public static void main(String[] args) {
        final Scanner in = new Scanner(System.in);
        System.out.println("Enter count of objects (n)");
        final int n = in.nextInt();
        final UnionFindWithSpecificCanonicalElement snc = new UnionFindWithSpecificCanonicalElement(n);

        System.out.println("=> For union operation, type command 'union p q' where p and q are the object numbers ranging from 0 to (n-1)");
        System.out.println("=> For connected query, type command 'connected p q' where p and q are the object numbers ranging from 0 to (n-1)");
        System.out.println("=> For find query, type command 'find p' where p is an object number ranging from 0 to (n-1)");

        while (in.hasNext()) {
            final StringBuilder message = new StringBuilder();
            final String[] inputs = in.nextLine().split(" ");
            switch (inputs[0]) {
                case "union":
                    message.append(inputs[1]).append(" and ").append(inputs[2]);
                    snc.union(Integer.parseInt(inputs[1]), Integer.parseInt(inputs[2]));
                    message.append(" are now connected.");
                    break;
                case "connected":
                    message.append(inputs[1]).append(" and ").append(inputs[2]);
                    if (snc.find(Integer.parseInt(inputs[1]), Integer.parseInt(inputs[2]))) {
                        message.append(" are connected.");
                    } else {
                        message.append(" are not connected.");
                    }
                    break;
                case "find":
                    message.append("Largest element in connected component set containing ").append(inputs[1]).append(" is ").append(snc.find(Integer.parseInt(inputs[1])));
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
