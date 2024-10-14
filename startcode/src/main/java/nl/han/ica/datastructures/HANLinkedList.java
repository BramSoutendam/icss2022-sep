package nl.han.ica.datastructures;

import java.util.Iterator;

public class HANLinkedList<genericType> implements nl.han.ica.datastructures.IHANLinkedList<genericType> {
    private ListNode head;
    int size = 0;

    @Override
    public void addFirst(genericType value) {
        ListNode newNode = new ListNode<>(value, head);
        head = newNode;
        size++;
    }

    @Override
    public void clear() {
        size = 0;
        head = new ListNode<>(null, null);
    }

    @Override
    public void insert(int pos, genericType value) {
        ListNode currNode = head;
        if(pos<=size){
            for(int i = 0; i < pos;i++){
                currNode = currNode.next;
            }
            size++;
            ListNode additionNode = new ListNode<>(value, currNode.next);
            currNode.setNext(additionNode);
        }else {
            throw new ArrayIndexOutOfBoundsException();
        }
    }

    @Override
    public void delete(int pos) {
        ListNode deleteableNode = head;
        ListNode rightbeforeDeletableNode = null;
        if(pos<size){
            size--;
            for(int i = 0; i < pos;i++){
                if( i == pos){
                    rightbeforeDeletableNode = deleteableNode;
                }
                deleteableNode = deleteableNode.next;
            }
            if(rightbeforeDeletableNode != null){
                rightbeforeDeletableNode.setNext(deleteableNode.next);
            }else{
                head = deleteableNode.next;
            }
        }else {
            throw new ArrayIndexOutOfBoundsException();
        }
    }

    @Override
    public genericType get(int pos) {
        ListNode curNode = head;
        if(pos<size){
            for(int i = 0; i < pos;i++){
                curNode = curNode.next;
            }
            return (genericType) curNode.innards;
        }
        throw new  ArrayIndexOutOfBoundsException();
    }

    @Override
    public void removeFirst() {
        delete(0);
    }

    @Override
    public genericType getFirst() {
        return (genericType) head.innards;
    }

    @Override
    public int getSize() {
        return size;
    }

    private static class ListNode<genericType> {
        private genericType innards;
        private ListNode next;

        ListNode(genericType innards, ListNode<genericType> next) {
            this.innards = innards;
            this.next = next;
        }

        public void setNext(ListNode next) {
            this.next = next;
        }
    }
}
