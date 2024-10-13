public class HANStack <genericType> implements nl.han.ica.datastructures.IHANStack {
    HANLinkedList stackContent;

    @Override
    public void push(Object value) {
        stackContent.addFirst(value);
    }

    @Override
    public Object pop() {
        Object topValue = stackContent.getFirst();
        stackContent.removeFirst();
        return topValue;
    }

    @Override
    public Object peek() {
        return stackContent.getFirst();
    }
}
