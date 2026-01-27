import jh61b.utils.Reflection;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.lang.reflect.Field;
import java.util.List;

import static com.google.common.truth.Truth.assertThat;
import static com.google.common.truth.Truth.assertWithMessage;

public class ArrayDeque61BTest {

     @Test
     @DisplayName("ArrayDeque61B has no fields besides backing array and primitives")
     void noNonTrivialFields() {
         List<Field> badFields = Reflection.getFields(ArrayDeque61B.class)
                 .filter(f -> !(f.getType().isPrimitive() || f.getType().equals(Object[].class) || f.isSynthetic()))
                 .toList();

         assertWithMessage("Found fields that are not array or primitives").that(badFields).isEmpty();
     }

     @Test
    void TestGet(){
         Deque61B<Integer> lld = new ArrayDeque61B<>();

         lld.addFirst(3);
         lld.addLast(5);

         assertThat(lld.get(0).equals(3)).isTrue();
         assertThat(lld.get(1).equals(5)).isTrue();
         assertThat(lld.get(2) == null).isTrue();
     }

     @Test
     void TestEmpty(){
         Deque61B<String> lld2 = new ArrayDeque61B<>();

         assertThat(lld2.isEmpty()).isTrue();

         lld2.addFirst("A");
         assertThat(lld2.isEmpty()).isFalse();
     }

    @Test
    public void testAddLastCircular() {
        ArrayDeque61B<Integer> ad = new ArrayDeque61B<>();

        // 假设 nextLast 初始在 7 (数组末尾)
        // 1. 连续 addLast，强制 nextLast 向右跨越 7 到达 0, 1...
        // 为了模拟这种情况，我们可以先填满，或者根据你的初始设定来测
        // 这里我们简单粗暴地填 8 个数 (填满，不扩容)

        for (int i = 0; i < 8; i++) {
            ad.addLast(i);
        }

        // 应该没有报错，且顺序正确
        assertThat(ad.size()).isEqualTo(8);
        assertThat(ad.toList()).containsExactly(0, 1, 2, 3, 4, 5, 6, 7).inOrder();
    }

    @Test
    public void TestRemoveFirstAndLast(){
         ArrayDeque61B<String> ad = new ArrayDeque61B<>();

        // 准备: [A, B, C]
        ad.addLast("A");
        ad.addLast("B");
        ad.addLast("C");

        // 测试 removeLast -> 应该返回 "C"
        assertThat(ad.removeLast()).isEqualTo("C");
        assertThat(ad.size()).isEqualTo(2);

        // 测试 removeFirst -> 应该返回 "A"
        assertThat(ad.removeFirst()).isEqualTo("A");
        assertThat(ad.size()).isEqualTo(1);

        // 此时应该只剩 "B"
        assertThat(ad.get(0)).isEqualTo("B");
    }
}
