package pmxymidi;

import java.util.Arrays;

// note string to int[<midiTrack>][<midiNoteValue] array parser by Plasmoxy
// Notes available from C0 to G10
// syntax : <noteLetter><shift><octave>
// shift -> "-" for no shift, "#" for up, "b" for down
// example : C-6 C-5 Cb2 G#4
// 10th octave is A !! 

public class NoteParser
{
	
	private static void debugArray(Object[] o)
	{
		System.out.println(Arrays.deepToString(o));
	}
	
	public static int parseNote(String note) throws NoteParseException
	{
		int midiValue = 0;
		
		char letter = note.charAt(0);
		char shift = note.charAt(1);
		char octave = note.charAt(2);
		
		int noteNum = 0;
		int octaveNum = 0;
		int shiftNum = 0;
		
		switch (letter)
		{
		case 'C' : noteNum = 0 ;break;
		case 'D' : noteNum = 2 ;break;
		case 'E' : noteNum = 4 ;break;
		case 'F' : noteNum = 5 ;break;
		case 'G' : noteNum = 7 ;break;
		case 'A' : noteNum = 9 ;break;
		case 'B' : noteNum = 11 ;break;
		case '-' : return -1 ;
		default :
			throw new NoteParseException();
		}
		
		if (octave == 'A') // the exceptional tenth octave
			octaveNum = 10;
		else 
			octaveNum = Character.getNumericValue(octave);
		
		switch (shift)
		{
		case '#': shiftNum = 1 ;break;
		case 'b': shiftNum = -1 ;break;
		case '-': shiftNum = 0 ;break;
		default:
			throw new NoteParseException();
		}
		
		
		midiValue = noteNum + octaveNum*12 + shiftNum;
		
		if ( ( midiValue < 0 || midiValue > 127 )) {
			throw new NoteParseException();
		}
		
		return midiValue;
		
	}
	
	
	public static int[][] niceParse(String str) throws NoteParseException {
		
		String[] strTracks;
		
		if (str.contains("\n"))
		{
			strTracks = str.split("\n");
		} else {
			strTracks = new String[1];
			strTracks[0] = str;
		}
		
		String[][] strNotes = new String[strTracks.length][];
		
		for ( int i = 0; i < strTracks.length; i++)
		{
			if (strTracks[i].contains(" "))
			{
				strNotes[i] = strTracks[i].split(" ");
			} else {
				strNotes[i] = new String[0];
				strNotes[i][0] = strTracks[i];
			}
		}
		
		int[][] notes = new int[strTracks.length][];
		
		try {
			
			for ( int tracki = 0; tracki < strTracks.length; tracki++)
			{
				notes[tracki] = new int[strNotes[tracki].length]; // allocate int array for every track
				for ( int notei = 0; notei < strNotes[tracki].length; notei++)
				{
					notes[tracki][notei] = parseNote(strNotes[tracki][notei]);
				}
			}
			
		} catch(Exception e) {
			System.out.println("NoteParser caught an exception : ");
			e.printStackTrace();
			throw new NoteParseException();
		}
		
		return notes;
		
	}
	
}
