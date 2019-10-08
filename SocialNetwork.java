/* *****************************************************************************
 *  
 *  Compilation:  javac-algs4 SocialNetwork.java
 *  Execution:    java-algs4 SocialNetwork
 *  Dependencies: algs4.jar from the below link (algs4)
 *
 *  This program takes the social network size as a command-line argument.
 *  It creates a social network and also displays the timestamp when
 *  every member is a friend of every other member(directly or indirectly)
 *
 *  Refer 
 *  https://introcs.cs.princeton.edu/java/home/
 *  for input files and test utils.
 *
 * *****************************************************************************/
import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdOut;
import java.util.Date;

public class SocialNetwork {
    private int[] parent;   // parent[i] = parent of i
    private int[] size;     // size[i] = number of members in subtree rooted at i
    private int count;      // number of components

    public SocialNetwork(int n) {
        count = n;
        parent = new int[n];
        size = new int[n];
        for (int i = 0; i < n; i++) {
            parent[i] = i;
            size[i] = 1;
        }
    }

    /**
     * Returns the number of components.
     */
    public int count() {
        return count;
    }
  
    /**
     * Returns the component identifier for the component containing member p
     */
    public int find(int p) {
        validate(p);
        while (p != parent[p])
            p = parent[p];
        return p;
    }

    // validate that p is a valid index
    private void validate(int p) {
        int n = parent.length;
        if (p < 0 || p >= n) {
            throw new IllegalArgumentException("index " + p + " is not between 0 and " + (n-1));  
        }
    }

    /**
     * Returns true if the the two members are in the same component
     * (friends or friend of friend)
     */
    public boolean connected(int p, int q) {
        return find(p) == find(q);
    }

    /**
     * Merges the component containing member p with the 
     * the component containing member q
     */
    public void makeFriend(int p, int q) {
        int rootP = find(p);
        int rootQ = find(q);
        if (rootP == rootQ) return;

        // make smaller root point to larger one
        if (size[rootP] < size[rootQ]) {
            parent[rootP] = rootQ;
            size[rootQ] += size[rootP];
            if (size[rootQ] == count)
                StdOut.println("Time at which everyone is everyone else's friend is"
                        +new SimpleDateFormat("yyyy.MM.dd.HH.mm.ss").format(new Date()));
        }
        else {
            parent[rootQ] = rootP;
            size[rootP] += size[rootQ];
            if (size[rootP] == count)
                StdOut.println("Time at which everyone is everyone else's friend is"
                        +new SimpleDateFormat("yyyy.MM.dd.HH.mm.ss").format(new Date()));
        }
        count--;
    }


    /**
     * Test Client
     * Reads in a sequence of pairs of integers (between 0 and n-1) from standard input, 
     * where each integer represents a member;
     * if the members are in different components, merge the two components
     * and print the pair to standard output.
     */
    public static void main(String[] args) {
        int n = StdIn.readInt();
        SocialNetwork sf = new SocialNetwork(n);
        while (!StdIn.isEmpty()) {
            int p = StdIn.readInt();
            int q = StdIn.readInt();
            if (uf.connected(p, q)) continue;
            uf.makeFriend(p, q);
            StdOut.println(p + " " + q);
        }
        StdOut.println(uf.count() + " components");
    }

}
