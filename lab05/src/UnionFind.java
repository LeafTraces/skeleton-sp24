import org.knowm.xchart.style.markers.Oval;


public class UnionFind {
    // TODO: Instance variables
    private int[] p;
    /* Creates a UnionFind data structure holding N items. Initially, all
       items are in disjoint sets. */
    public UnionFind(int N) {
        // TODO: YOUR CODE HERE
        p = new int[N];
        for (int i =0; i < N; i++){
            p[i] = -1;
        }
    }

    /* Returns the size of the set V belongs to. */
    public int sizeOf(int v) {
        // TODO: YOUR CODE HERE
        return Math.abs(p[find(v)]);
    }

    /* Returns the parent of V. If V is the root of a tree, returns the
       negative size of the tree for which V is the root. */
    public int parent(int v) {
        // TODO: YOUR CODE HERE
        return p[v];
    }

    /* Returns true if nodes/vertices V1 and V2 are connected. */
    public boolean connected(int v1, int v2) {
        // TODO: YOUR CODE HERE
        return find(v1) == find(v2);
    }

    /* Returns the root of the set V belongs to. Path-compression is employed
       allowing for fast search-time. If invalid items are passed into this
       function, throw an IllegalArgumentException. */
    public int find(int v) {
        // TODO: YOUR CODE HERE
        validate(v);

        if (p[v] < 0){
            return v;
        }
        p[v] = find(p[v]);
        return p[v];
    }

    /* Connects two items V1 and V2 together by connecting their respective
       sets. V1 and V2 can be any element, and a union-by-size heuristic is
       used. If the sizes of the sets are equal, tie break by connecting V1's
       root to V2's root. Union-ing an item with itself or items that are
       already connected should not change the structure. */
    public void union(int v1, int v2) {
        // TODO: YOUR CODE HERE
        int v1Size = sizeOf(v1), v2Size = sizeOf(v2);
        int root1 = find(v1), root2 = find(v2);
        if (root1 == root2){
            return;
        }

        if (v1Size <= v2Size){
            p[root2] += p[root1];
            p[root1] = root2;
        }
        else{
            p[root1] += p[root2];
            p[root2] = root1;
        }
    }

    private void validate(int v){
        if (v < 0 || v >p.length){
            throw new IllegalArgumentException("\"Index \" + v + \" is out of bounds!");
        }
    }
}
