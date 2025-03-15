package EstruturasDeDados.Lista;

public interface InterfaceLista <E>
{	
    void addFirst(E value);
    void addLast(E value);
    boolean addAfter(E dado, E crit);
    E get(int index);
    E peekFirst();
    E peekLast();

    E search(E crit);
    
    E removeFirst();
    E removeLast();
    E remove(E crit); 

    boolean isEmpty();
    
}
