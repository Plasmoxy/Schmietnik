package schmietnik;

public class Tester
{
	public static void main(String[] args) throws Exception
	{
		
		int[][] arr = {
				{60, -2 ,-2, -2, -2, -2}	// lol works xD
		};
		
		SimpleArraySequence seq = new SimpleArraySequence(arr);
		MidiPlayer plr = new MidiPlayer(seq, 80);
		
		plr.play();
		
	}
}
