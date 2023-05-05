package techcourse.jcf.mission;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

/**
 * @author 우가
 * @version 1.0.0
 * @Created by 우가 on 2023/02/23
 */
@SuppressWarnings("NonAsciiCharacters")
@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
@DisplayName("LinkedList 학습 테스트")
public class LinkedListTest {

    @Test
    public void linkedList() {
        SimpleList values = new SimpleLinkedList();
        values.add("first");
        values.add("second");

        assertThat(values.add("third")).isTrue(); // 세 번째 값을 추가한다.
        assertThat(values.size()).isEqualTo(3); // list의 크기를 구한다.
        assertThat(values.get(0)).isEqualTo("first"); // 첫 번째 값을 찾는다.
        assertThat(values.contains("first")).isTrue(); // "first" 값이 포함되어 있는지를 확인한다.
        assertThat(values.remove(0)).isEqualTo("first"); // 첫 번째 값을 삭제한다.
        assertThat(values.size()).isEqualTo(2); // 값이 삭제 됐는지 확인한다.
    }

    @Test
    void 문자열을_추가할_경우_true를_반환한다() {
        SimpleList list = new SimpleLinkedList();

        assertThat(list.add("test")).isTrue();
    }

    @Test
    void 인덱스를_지정해_문자열을_추가할수_있다() {
        SimpleList list = new SimpleLinkedList();

        assertDoesNotThrow(() -> list.add(10, "test"));
    }

    @Test
    void 사이즈가_안맞을_경우_set호출시_예외를던진다() {
        SimpleList list = new SimpleLinkedList();

        assertThatThrownBy(() -> list.set(10, "test"))
                .isInstanceOf(IndexOutOfBoundsException.class)
                .hasMessage("인덱스를 확인해주세요.");
    }

    @Test
    void set호출시_이전_인덱스의_값을_반환한다() {
        SimpleList list = new SimpleLinkedList();
        list.add("1");
        assertThat(list.set(0, "test")).isEqualTo("1");
    }

    @Test
    void get호출시_해당index가_없는경우_예외를_던진다() {
        SimpleList list = new SimpleLinkedList();
        list.add("1");

        assertThatThrownBy(() -> list.get(1))
                .isInstanceOf(IndexOutOfBoundsException.class)
                .hasMessage("인덱스를 확인해주세요.");
    }

    @Test
    void get호출시_해당index의_값을_반환한다() {
        SimpleList list = new SimpleLinkedList();
        list.add("1");

        assertThat(list.get(0)).isEqualTo("1");
    }

    @ParameterizedTest
    @CsvSource({"0,false", "1,true"})
    void 값중에_해당값을_가지고_있으면true_않으면_false를_반환한다(final String actual, final boolean expected) {
        SimpleList list = new SimpleLinkedList();
        list.add("1");

        assertThat(list.contains(actual)).isEqualTo(expected);
    }

    @Test
    void 값중에_해당값을_조회할때_값이_있으면_앞에서_가까운_index를_반환한다() {
        SimpleList list = new SimpleLinkedList();
        list.add("1");
        list.add("1");
        list.add("2");
        list.add("1");
        list.add("2");

        assertThat(list.indexOf("2")).isEqualTo(2);
    }

    @Test
    void 값중에_해당값을_조회할때_값이_없으면_마이너스일을_반환한다() {
        SimpleList list = new SimpleLinkedList();
        list.add("1");
        list.add("1");
        list.add("2");
        list.add("1");
        list.add("2");

        assertThat(list.indexOf("3")).isEqualTo(-1);
    }

    @Test
    void 크기를_반환한다() {
        SimpleList list = new SimpleLinkedList();
        list.add("1");
        list.add("1");
        list.add("2");
        list.add("1");
        list.add("2");

        assertThat(list.size()).isEqualTo(5);
    }

    @Test
    void 비어있으면true_아니면false를_반환한다() {
        SimpleList listA = new SimpleLinkedList();
        listA.add("1");

        SimpleList listB = new SimpleLinkedList();

        assertThat(listA.isEmpty()).isFalse();
        assertThat(listB.isEmpty()).isTrue();
    }

    @Test
    void 기댓값의_삭제에_성공하면_true_실패하면_false를_반환한다() {
        SimpleList listA = new SimpleLinkedList();
        listA.add("1");
        listA.add("1");
        listA.add("2");
//
//        SimpleList listB = new SimpleLinkedList();
//        listB.add("1");
//        listB.add("2");
//        listB.add("1");

        assertThat(listA.remove("2")).isTrue();
        assertThat(listA.size()).isEqualTo(2);
        assertThat(listA.remove("3")).isFalse();

//        assertThat(listB.remove("2")).isTrue();
//        assertThat(listB.size()).isEqualTo(2);
//        assertThat(listB.remove("3")).isFalse();
    }

    @Test
    void 해당인덱스의_삭제에_성공하면_true_실패하면_false를_반환한다() {
        SimpleList listA = new SimpleLinkedList();
        listA.add("1");
        listA.add("1");
        listA.add("2");

        assertThat(listA.remove(2)).isEqualTo("2");
        assertThat(listA.size()).isEqualTo(2);
        assertThatThrownBy(() -> listA.remove(2))
                .isInstanceOf(IndexOutOfBoundsException.class)
                .hasMessage("인덱스를 확인해주세요.");
    }

    @Test
    void 초기길이로_초기화된다() {
        SimpleList listA = new SimpleLinkedList();
        listA.add("1");
        listA.add("1");
        listA.add("2");

        listA.clear();

        assertThat(listA.size()).isEqualTo(0);
    }

    private class SimpleLinkedList implements SimpleList {

        Node first;
        int size = 0;

        public SimpleLinkedList() {
            this.first = new Node();
        }

        @Override
        public boolean add(final String value) {
            Node newNode = new Node(value);
            first.addNextNode(newNode);
            size++;
            return true;
        }

        @Override
        public void add(final int index, final String value) {
        }

        @Override
        public String set(final int index, final String value) {
            if (size <= index) {
                throw new IndexOutOfBoundsException("인덱스를 확인해주세요.");
            }

            Node node = findNode(index);
            String temp = node.getValue();
            node.setValue(value);
            return temp;
        }

        @Override
        public String get(final int index) {
            if (size <= index) {
                throw new IndexOutOfBoundsException("인덱스를 확인해주세요.");
            }
            Node node = findNode(index);
            return node.getValue();
        }

        private Node findNode(final int index) {
            Node node = first;
            for (int i = 0; i <= index; i++) {
                node = node.nextNode();
            }
            return node;
        }

        @Override
        public boolean contains(final String value) {
            return first.findNode(value);
        }

        @Override
        public int indexOf(final String value) {
            Node node = first.nextNode();
            for (int i = 0; i <= size; i++) {
                if (i == size) {
                    return -1;
                }
                if (node.isEqualToValue(value)) {
                    return i;
                }
                node = node.nextNode();
            }
            return -1;
        }

        @Override
        public int size() {
            return size;
        }

        @Override
        public boolean isEmpty() {
            return size == 0;
        }

        @Override
        public boolean remove(final String value) {
            int index = indexOf(value);
            if (index == -1) {
                return false;
            }
            remove(index);
            return true;
        }

        @Override
        public String remove(final int index) {

            if (size <= index) {
                throw new IndexOutOfBoundsException("인덱스를 확인해주세요.");
            }

            Node preNode = first;
            for (int i = 0; i < index; i++) {
                preNode = preNode.nextNode();
            }

            Node currentNode = preNode.nextNode();
            String value = currentNode.getValue();

            if (currentNode.hasNotNext()) {
                preNode.changeNext(new Node());
                size--;
                return value;
            }

            Node postNode = currentNode.nextNode();
            preNode.changeNext(postNode);
            size--;
            return currentNode.getValue();
        }

        @Override
        public void clear() {
            first = new Node();
            size = 0;
        }
    }

    // arrayList -> [ , , , , ] -> [ , , , , , , , , , ]
    // linkedList -> []-[]-[]-[]-[]-[]-[]-[]

    class Node {

        Node next;
        String value;

        public Node() {
            this.next = null;
            this.value = null;
        }

        public Node(final String value) {
            this.next = null;
            this.value = value;
        }

        public void addNextNode(final Node nextNode) {
            if (this.next == null) {
                this.next = nextNode;
                return;
            }
            next.addNextNode(nextNode);
        }

        public boolean findNode(final String value) {
            if (value.equals(this.value)) {
                return true;
            }
            if (next != null) {
                return next.findNode(value);
            }
            return false;
        }

        public String getValue() {
            return this.value;
        }

        public void changeNext(final Node posNode) {
            this.next = posNode;
        }

        public Node nextNode() {
            return next;
        }

        public Node findPreNode(final Node postNode) {
            if (next.equals(postNode)) {
                return this;
            }
            next.findPreNode(postNode);
            throw new IndexOutOfBoundsException("인덱스를 확인해주세요.");
        }

        public Node add(final String value) {
            Node newNode = new Node(value);
            this.next = newNode;
            return newNode;
        }

        public void setValue(final String value) {
            this.value = value;
        }

        public boolean isEqualToValue(final String value) {
            return this.value.equals(value);
        }

        public boolean hasNotNext() {
            return next == null;
        }
    }
}
