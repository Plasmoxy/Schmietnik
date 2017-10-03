package schmietnik;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.swing.text.html.HTMLDocument.Iterator;

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
	
	private static void debugStrList(List<List<String>> a)
	{
		for (List<String> l : a)
		{
			for (String s : l)
			{
				System.out.print(s);
				System.out.print("|");
			}
			System.out.print(":");
		}
		
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
		case '.' : return -2 ;
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
	
	
	public static int[][] niceParse(String str) throws NoteParseException 
	{	
		
		List<String> strTracks;
		
		if (str.contains("\n"))
		{
			strTracks = Arrays.asList(str.split("\n")); // split to tracks
		} else {
			strTracks = new ArrayList<String>(); // in case of only one track
			strTracks.add(str);
		}
		
		strTracks.remove(null);
		strTracks.remove("\n");
		
		List<List<String>> strNotes = new ArrayList<List<String>>();
		
		for (String track : strTracks)
		{
			if (track.contains(" "))
			{
				strNotes.add(Arrays.asList(track.split(" ")));
			} else {
				List<String> currentTrackRef = new ArrayList<String>(); // create object, tempotary reference
				currentTrackRef.add(track); // add the string to it
				strNotes.add(currentTrackRef); // add the object
			}
		}
		
		strNotes.remove(null);
		for (List<String> l : strNotes)
		{
			l.remove(" ");
		}
		
		debugStrList(strNotes);
		
		int[][] notes = new int[strNotes.size()][];
		
		try {
			
			for ( int tracki = 0; tracki < strNotes.size(); tracki++)
			{
				notes[tracki] = new int[strNotes.get(tracki).size()]; // allocate int array for every track
				
				for ( int notei = 0; notei < strNotes.get(tracki).size(); notei++)
				{
					notes[tracki][notei] = parseNote(strNotes.get(tracki).get(notei));
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
