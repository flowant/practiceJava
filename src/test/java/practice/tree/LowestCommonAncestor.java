package practice.tree;

import org.junit.Assert;
import org.junit.Test;

import java.util.HashSet;
import java.util.LinkedList;
import java.util.Queue;


/*
    1. travel ancestors of the first child, add all ancestors into a global Set.
    2. travel ancestors of the 2nd, add all ancestors into a local Set. make intersection with the global set.
       and save intersection into the global set.
    3. loop 2 before the last child.
    4. travel ancestors of the last child. if global set contains a ancestor then return it.
    5. return -1 when there is no common ancestor.
    6. return -2 when input validation fail.
    7. 2D Array is used for a input family tree.
    8. varargs integer are used for children.
*/
public class LowestCommonAncestor {

    public int findLCA(int[][] familyTree, int... children) {
        if (children.length < 2)
            return -1;

        for (int child : children) {
            if (child < 0 || child >= familyTree.length)
                return -1;
        }

        Queue<Integer> travelQ = new LinkedList<>();
        HashSet<Integer> globalSet = new HashSet<>();
        HashSet<Integer> localSet = new HashSet<>();

        for (int i = 0; i < children.length; i++) {
            travelQ.add(children[i]);
            while (travelQ.isEmpty() == false) {
                int person = travelQ.remove();
                for (int parent : familyTree[person]) {
                    if (parent < 0 || parent >= familyTree.length) {
                        continue;
                    }

                    if (i != children.length - 1) { // before the last child
                        localSet.add(parent);
                    } else if (globalSet.contains(parent)) { // the last child
                        return parent;
                    }

                    travelQ.add(parent);
                }
            }

            if (i == 0) {
                globalSet.addAll(localSet);
            } else if (i != children.length - 1) {
                globalSet.retainAll(localSet);
            }

            if (i < children.length - 2)
                localSet.clear();
        }

        return -1;
    }

    @Test
    public void testFindLCS() {
        // [Person's Vertex Index][0] = Mother's Vertex index
        // [                   ][1] = Father's Vertex index

        int[][] familyTree =
                {{4, 5}, {6, 5}, {6, 7}, {8, 9}, {-1, -1},
                {10, 11}, {-1, 11}, {12, 13}, {-1, 15}, {12, 13},
                {-1, -1}, {14, -1}, {14, 15}, {-1, 100}, {-1, 100},
                {-1, -1}};

        Assert.assertEquals(-1, findLCA(familyTree, 1));
        Assert.assertEquals(-1, findLCA(familyTree, -1, 1));
        Assert.assertEquals(-1, findLCA(familyTree, 1, -1));
        Assert.assertEquals(-1, findLCA(familyTree, familyTree.length, 0));
        Assert.assertEquals(-1, findLCA(familyTree, 0, familyTree.length));
        Assert.assertEquals(-1, findLCA(familyTree, 0, familyTree.length + 1));

        Assert.assertEquals(5, findLCA(familyTree, 0, 1));
        Assert.assertEquals(14, findLCA(familyTree, 1, 3));
        Assert.assertEquals(14, findLCA(familyTree, 0, 3));
        Assert.assertEquals(12, findLCA(familyTree, 3, 9));
        Assert.assertEquals(-1, findLCA(familyTree, 14, 15));
        Assert.assertEquals(-1, findLCA(familyTree, 14, 3));
        Assert.assertEquals(11, findLCA(familyTree, 0, 1, 2));
        Assert.assertEquals(14, findLCA(familyTree, 0, 1, 3));
        Assert.assertEquals(14, findLCA(familyTree, 0, 1, 2, 3));
        System.out.println(findLCA(familyTree, 0, 1, 2, 3));
    }

}