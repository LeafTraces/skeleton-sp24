import deque.Deque61B;
import deque.LinkedListDeque61B;
import org.junit.jupiter.api.Test;

import static com.google.common.truth.Truth.assertThat;

public class LinkedListDeque61BTest {
    @Test
    public void testToString() {
        Deque61B<String> lld = new LinkedListDeque61B<>();
        lld.addLast("front");
        lld.addLast("middle");
        lld.addLast("back");

        System.out.println(lld);
        // 应该在控制台看到: [front, middle, back]

        assertThat(lld.toString()).isEqualTo("[front, middle, back]");
    }
}
