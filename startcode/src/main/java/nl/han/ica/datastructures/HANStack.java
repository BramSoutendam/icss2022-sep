package nl.han.ica.datastructures;

public class HANStack <genericType> implements nl.han.ica.datastructures.IHANStack<genericType> {
    HANLinkedList<genericType> stackContent;

    public HANStack(){ stackContent = new HANLinkedList<>();}
    @Override
    public void push(genericType value) {
        stackContent.addFirst(value);
    }

    @Override
    public genericType pop() {
        genericType topValue = stackContent.getFirst();
        stackContent.removeFirst();
        return topValue;
    }

    @Override
    public genericType peek() {
        return stackContent.getFirst();
    }
}
