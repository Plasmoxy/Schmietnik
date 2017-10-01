package pmxymidi;

public class NoteParseException extends Exception
{
	public NoteParseException()
	{
		super("NoteParser : The note syntax is incorrect ! Can't parse. ");
	}
}
