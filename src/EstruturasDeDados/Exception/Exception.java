package EstruturasDeDados.Exception;

public class Exception extends RuntimeException 
{
	private static final long serialVersionUID = 4652958143051993454L;

	private String description = "";
	
	public Exception(String description)
	{
        super(description);
        
        this.description = description;
    }
	
	public String getDescription() {
		return description;
	}

}