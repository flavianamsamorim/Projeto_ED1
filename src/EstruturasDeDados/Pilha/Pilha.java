package EstruturasDeDados.Pilha;

import EstruturasDeDados.Exception.Exception;

public class Pilha <T> implements InterfacePilha <T>
{
	private int sizeMax;
	private Object[] array;
	private int top;
	
	
	public Pilha(int size) {
		this.top = -1;
		this.sizeMax = size;
		this.array = new Object[size];
	}

	
	public void push(T valor) throws Exception	
	{

	    if( isFull() ) {
	        throw new Exception("\nERRO: pilha cheia [valor="+valor+"]!!!\n" );
	    }

	    top = top + 1;
	    array[ top ] = valor;
	}

	
	@SuppressWarnings("unchecked")
	public T pop() throws Exception
	{

	    if( isEmpty() ) {
	        throw new Exception( "\nERRO: pilha vazia!!!\n" );
	    }

        T retorno = (T) array[ top ];
        top = top - 1;

	    return retorno;
	}

	
	@SuppressWarnings("unchecked")
	public T peek() throws Exception
	{

	    if( isEmpty() ) {
	        throw new Exception( "\nERRO: pilha vazia!!!\n" );
	    }
	    
	    T retorno = (T) array[ top ];

	    return retorno;
	}
	

	public boolean isFull()
	{
	    if( top == sizeMax-1) {
	        return true;
	    }
	    else { 
	    	return false;
	    }
	}
	
	
	public boolean isEmpty()
	{
	    if( top == -1) {
	        return true;
	    }
	    else { 
	    	return false;
	    }
	}
	
	
	public void show()
	{
	    int i;
		
	    for(i=0; i <= top; i++) {
	        System.out.println("posicao " + i + " = " + array[i] + "\n");
	    }
		System.out.println("topo index = " + top + "\n");
	}

    public int size() {
        return top + 1;
    }
    
}
