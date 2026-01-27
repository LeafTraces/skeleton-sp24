import org.junit.jupiter.api.*;
import java.util.Comparator;
import deque.MaxArrayDeque61B;
import static com.google.common.truth.Truth.assertThat;
import static com.google.common.truth.Truth.assertWithMessage;

public class MaxArrayDeque61BTest {
    private static class StringLengthComparator implements Comparator<String> {
        public int compare(String a, String b) {
            return a.length() - b.length();
        }
    }

    @Test
    public void basicTest() {
        MaxArrayDeque61B<String> mad = new MaxArrayDeque61B<>(new stringLengthComparator());
        mad.addFirst("");
        mad.addFirst("2");
        mad.addFirst("fury road");
        assertThat(mad.max()).isEqualTo("fury road");
    }

    // --- 准备一些有趣的比较器 (Comparators) ---

    // 1. 按字符串长度比较 (长度长的算大)
    private static class stringLengthComparator implements Comparator<String> {
        public int compare(String a, String b) {
            return a.length() - b.length();
        }
    }

    // 2. 倒序比较器 (数字越小越"大")
    private static class ReverseIntegerComparator implements Comparator<Integer> {
        public int compare(Integer a, Integer b) {
            return b - a; // 如果 b > a，返回正数，说明 b "大"
        }
    }

    // --- 开始测试 ---

    /**
     * 测试 1: 最基础的整数最大值 (使用 Java 自带的 naturalOrder)
     * 场景: [1, 5, 2, 3] -> max 应该是 5
     */
    @Test
    public void testIntegerNaturalOrder() {
        // 使用 Integer 自带的自然排序 (从小到大)
        MaxArrayDeque61B<Integer> mad = new MaxArrayDeque61B<Integer>(Comparator.naturalOrder());

        mad.addLast(1);
        mad.addLast(5);
        mad.addLast(2);
        mad.addLast(3);

        // 调用无参 max()，应该使用构造函数里的自然排序
        assertThat(mad.max()).isEqualTo(5);
    }

    /**
     * 测试 2: 测试字符串长度比较器
     * 场景: ["hi", "hello", "a"] -> max 应该是 "hello" (因为它最长)
     */
    @Test
    public void testStringLengthComparator() {
        MaxArrayDeque61B<String> mad = new MaxArrayDeque61B<>(new stringLengthComparator());

        mad.addFirst("hi");
        mad.addFirst("hello");
        mad.addFirst("a");

        // "hello" 长度是 5，最长
        assertThat(mad.max()).isEqualTo("hello");
    }

    /**
     * 测试 3: 这里的“大”是字典序 (Alphabetical Order)
     * 场景: ["apple", "zebra", "banana"] -> max 应该是 "zebra"
     */
    @Test
    public void testStringLexicographical() {
        MaxArrayDeque61B<String> mad = new MaxArrayDeque61B<String>(Comparator.naturalOrder());

        mad.addLast("apple");
        mad.addLast("zebra");
        mad.addLast("banana");

        assertThat(mad.max()).isEqualTo("zebra");
    }

    /**
     * 测试 4: 混合双打 (Override)
     * 场景:
     * - 构造时告诉它是 "自然排序" (5 最大)
     * - 但调用时传入 "倒序比较器" (1 最大)
     * 验证 max(Comparator c) 是否生效
     */
    @Test
    public void testComparatorOverride() {
        MaxArrayDeque61B<Integer> mad = new MaxArrayDeque61B<Integer>(Comparator.naturalOrder());

        mad.addLast(1);
        mad.addLast(5);
        mad.addLast(10);

        // 1. 使用默认 (自然排序) -> 10 最大
        assertThat(mad.max()).isEqualTo(10);

        // 2. 使用临时传入的 (倒序) -> 1 最大
        assertThat(mad.max(new ReverseIntegerComparator())).isEqualTo(1);
    }

    /**
     * 测试 5: 边界条件 - 空队列
     * 场景: 没有任何元素，max 应该返回 null
     */
    @Test
    public void testEmptyMax() {
        MaxArrayDeque61B<Integer> mad = new MaxArrayDeque61B<Integer>(Comparator.naturalOrder());

        // 确保 size 为 0
        assertThat(mad.isEmpty()).isTrue();

        // 无参 max()
        assertThat(mad.max()).isNull();

        // 有参 max(c)
        assertThat(mad.max(Comparator.reverseOrder())).isNull();
    }

    /**
     * 测试 6: 边界条件 - 只有一个元素
     */
    @Test
    public void testSingleElement() {
        MaxArrayDeque61B<Double> mad = new MaxArrayDeque61B<Double>(Comparator.naturalOrder());
        mad.addFirst(3.14);

        assertThat(mad.max()).isEqualTo(3.14);
    }

    /**
     * 测试 7: 负数测试
     * 场景: [-5, -1, -10] -> 自然排序最大的是 -1
     */
    @Test
    public void testNegativeNumbers() {
        MaxArrayDeque61B<Integer> mad = new MaxArrayDeque61B<Integer>(Comparator.naturalOrder());
        mad.addLast(-5);
        mad.addLast(-1);
        mad.addLast(-10);

        assertThat(mad.max()).isEqualTo(-1);
    }
}
