import jh61b.utils.Reflection;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static com.google.common.truth.Truth.assertThat;
import static com.google.common.truth.Truth.assertWithMessage;

/** Performs some basic linked list tests. */
public class LinkedListDeque61BTest {

     @Test
     /** In this test, we have three different assert statements that verify that addFirst works correctly. */
     public void addFirstTestBasic() {
         Deque61B<String> lld1 = new LinkedListDeque61B<>();

         lld1.addFirst("back"); // after this call we expect: ["back"]
         assertThat(lld1.toList()).containsExactly("back").inOrder();

         lld1.addFirst("middle"); // after this call we expect: ["middle", "back"]
         assertThat(lld1.toList()).containsExactly("middle", "back").inOrder();

         lld1.addFirst("front"); // after this call we expect: ["front", "middle", "back"]
         assertThat(lld1.toList()).containsExactly("front", "middle", "back").inOrder();

         /* Note: The first two assertThat statements aren't really necessary. For example, it's hard
            to imagine a bug in your code that would lead to ["front"] and ["front", "middle"] failing,
            but not ["front", "middle", "back"].
          */
     }

     @Test
     /** In this test, we use only one assertThat statement. IMO this test is just as good as addFirstTestBasic.
      *  In other words, the tedious work of adding the extra assertThat statements isn't worth it. */
     public void addLastTestBasic() {
         Deque61B<String> lld1 = new LinkedListDeque61B<>();

         lld1.addLast("front"); // after this call we expect: ["front"]
         lld1.addLast("middle"); // after this call we expect: ["front", "middle"]
         lld1.addLast("back"); // after this call we expect: ["front", "middle", "back"]
         assertThat(lld1.toList()).containsExactly("front", "middle", "back").inOrder();
     }

     @Test
     /** This test performs interspersed addFirst and addLast calls. */
     public void addFirstAndAddLastTest() {
         Deque61B<Integer> lld1 = new LinkedListDeque61B<>();

         /* I've decided to add in comments the state after each call for the convenience of the
            person reading this test. Some programmers might consider this excessively verbose. */
         lld1.addLast(0);   // [0]
         lld1.addLast(1);   // [0, 1]
         lld1.addFirst(-1); // [-1, 0, 1]
         lld1.addLast(2);   // [-1, 0, 1, 2]
         lld1.addFirst(-2); // [-2, -1, 0, 1, 2]

         assertThat(lld1.toList()).containsExactly(-2, -1, 0, 1, 2).inOrder();
     }

    // Below, you'll write your own tests for LinkedListDeque61B.
    @Test
    public void testSizeAndIsEmpty(){
         Deque61B<String> lld = new LinkedListDeque61B<>();
         assertThat(lld.isEmpty()).isTrue();
         assertThat(lld.size()).isEqualTo(0);

         lld.addFirst("front");

         assertThat(lld.isEmpty()).isFalse();
         assertThat(lld.size()).isEqualTo(1);

        lld.addFirst("back");

        assertThat(lld.isEmpty()).isFalse();
        assertThat(lld.size()).isEqualTo(2);

        System.out.println("Current list: " + lld.toList());
    }

    @Test
    public void TestGet(){
         Deque61B<Integer> lld = new LinkedListDeque61B<>();
         assertThat(lld.get(1)).isNull();
         assertThat(lld.get(-1)).isNull();

        lld.addFirst(233);
        lld.addLast(111);

        assertThat(lld.get(0)).isEqualTo(233);
        assertThat(lld.get(1)).isEqualTo(111);

        assertThat(lld.get(2)).isNull();   // 【补充】索引 == size，这是高频 Bug 点
        assertThat(lld.get(100)).isNull();

        System.out.println("Current list: " + lld.toList());
    }

    @Test
    public void TestRecursiveGet(){
        Deque61B<Integer> lld = new LinkedListDeque61B<>();
        assertThat(lld.getRecursive(1)).isNull();
        assertThat(lld.getRecursive(-1)).isNull();

        lld.addFirst(233);
        lld.addLast(111);

        assertThat(lld.getRecursive(0)).isEqualTo(233);
        assertThat(lld.getRecursive(1)).isEqualTo(111);

        assertThat(lld.getRecursive(2)).isNull();   // 【补充】索引 == size，这是高频 Bug 点
        assertThat(lld.getRecursive(100)).isNull();

        System.out.println("Current list: " + lld.toList());
    }

    @Test
    public void testRemoveBasics() {
        LinkedListDeque61B<String> lld = new LinkedListDeque61B<>();

        // 准备数据: [A, B, C]
        lld.addLast("A");
        lld.addLast("B");
        lld.addLast("C");

        // 1. 测试 removeFirst
        // 应该移除 "A"，剩下 [B, C]
        String first = lld.removeFirst();
        assertThat(first).isEqualTo("A");
        assertThat(lld.size()).isEqualTo(2);
        assertThat(lld.toList()).containsExactly("B", "C").inOrder();

        // 2. 测试 removeLast
        // 应该移除 "C"，剩下 [B]
        String last = lld.removeLast();
        assertThat(last).isEqualTo("C");
        assertThat(lld.size()).isEqualTo(1);
        assertThat(lld.toList()).containsExactly("B").inOrder();

        // 3. 再移除剩下的 "B"
        assertThat(lld.removeFirst()).isEqualTo("B");
        assertThat(lld.size()).isEqualTo(0);
    }
}