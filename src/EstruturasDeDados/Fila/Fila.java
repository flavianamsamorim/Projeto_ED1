package EstruturasDeDados.Fila;

public class Fila <T> implements InterfaceFila <T>
{
	// Classe Interna Node
	class Node
	{
		// Atributos de Node
        T data;    
        Node next;    
                
        // Construtor de Node
        public Node(T data) 
        {        	
            this.data = data;    
            this.next = null;
        }    
    } 
	
	// Atributos
    private Node head;
    private Node tail;
    private long size;
    private long capacity;
    
    // Construtor
	public Fila() {
		this.head = null;
		this.tail = null;
		this.size = 0;
		this.capacity = -1;
	}

    // Construtor
	public Fila(long capacity) {
		this.head = null;
		this.tail = null;
		this.size = 0;
		this.capacity = capacity;
	}
	
	
	public long getSize() {
		return size;
	}
	
	public long getCapacity() {
		return capacity;
	}
		

	@Override
	public void add(T dado) throws Exception 
	{
		// Adicionar no fim
	    if( isFull() ) {
	    	throw new Exception("Cheio!");
	    }
	    
	    Node novo = new Node(dado);    

	    // verifica se lista está vazia
	    if( head == null ) { 
	        head = novo;       	// novo será o primeiro elemento
	        tail = novo;		// novo será o último elemento
	    }
	    else
	    {
			// Anexa
	    	tail.next = novo;
	        tail = novo;
	    }
	    
	    size++;
	}


	@Override
	public T remove() throws Exception 
	{
		// Remover do inicio
		Node p = head;
		T dadoRetorno = null;

		if( isEmpty() ) {
			throw new Exception("Vazio!");
	    }
		else
		{
			dadoRetorno = head.data;
			
			if (head == tail) 
			{
				System.out.println("Remove unico elemento\n");
                head = null;
                tail = null;
            } 
			else {
				System.out.println("Remove primeiro elemento, mas há mais outros\n");
                head = head.next;
			}
			
			p.next = null; // isola elemento removido
			
			size--;
		}

		return dadoRetorno;
	}


	@Override
	public T peek() throws Exception 
	{
		// Consulta do inicio
		if( head == null ) {
	        System.out.println("Lista Vazia!!! \n");
	        return null;
	    }
		else {
			return head.data;
		}
	}


	@Override
	public boolean isEmpty() 
	{
		return (head == null);
	}


	@Override
	public boolean isFull() 
	{
		if(capacity == -1) {
			return false;
		} 
		else 
		{
			if(size == capacity) {
				return true;
			}
			else {
				return false;
			}
		}
	}
	
	
	@Override
	public void show()
	{
	    Node p = head;

		if(p == null) {
			System.out.println("LISTA VAZIA \n");
		}
		else 
	    {
	        while( p != null )
	        {
	            System.out.println("Dado: " + p.data );
	            p = p.next;
	        }
	    }
		
		System.out.println("size = " + size + "\n");
	}

	@Override
    public void clear() {
        head = null;
        tail = null;
        size = 0;
    }

    public T poll() {
        if (isEmpty()) {
            return null; // Retorna null se a fila estiver vazia
        }
        
        T dadoRetorno = head.data; // Obtém o dado do primeiro elemento
        head = head.next; // Move o ponteiro head para o próximo elemento
    
        if (head == null) { // Se a fila ficou vazia, também devemos atualizar tail
            tail = null;
        }
    
        size--; // Atualiza o tamanho da fila
        return dadoRetorno; // Retorna o dado removido
    }
    


}
