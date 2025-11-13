package EstruturasDeDados.Fila;

public interface InterfaceFila <T>
{
    void add(T number) throws Exception;
    T remove() throws Exception;
    
    T poll();
    T peek() throws Exception;	
    boolean isEmpty();
    boolean isFull();
    
    void show(); 

    void clear(); // Adicione esta linha para suportar o método


}
