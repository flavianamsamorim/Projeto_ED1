package EstruturasDeDados.Pilha;

public interface InterfacePilha <T>{
    void push(T number);
    T pop();
    
    T peek();
    
    boolean isEmpty();
    boolean isFull();
    
    void show();	// opcional e auxiliar
}
