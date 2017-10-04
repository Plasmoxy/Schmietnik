package schmietnik;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import javax.swing.text.html.HTMLDocument.Iterator;

// multitrack note string to int[<midiTrack>][<midiNoteValue] array parser by Plasmoxy
// Notes available from C0 to G10
// syntax : <noteLetter><shift><octave>
// shift -> "-" for no shift, "#" for up, "b" for down
// example : C-6 C-5 Cb2 G#4
// 10th octave is A !! 

public class NoteParser
{
	
	// Parses note using the 3 character format
	private static int parseNote(String note) throws NoteParseException
	{
		int midiValue = 0;
		
		// follows the format
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
		case '-' : return -1 ; // an empty note, just straight return empty note -1 code
		case '.' : return -2 ; // extender, return -2 extender code
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
		
		
		midiValue = noteNum + octaveNum*12 + shiftNum; // thanks to revolutionary midi system, now we can actually have symetrical note logic
		
		if ( ( midiValue < 0 || midiValue > 127 )) { // in case some genius wanted to use more than 128 key piano
			throw new NoteParseException();
		}
		
		return midiValue;
		
	}
	
	
	// Parses the whole text data 
	public static int[][] niceParse(String str) throws NoteParseException 
	{	
		
		
		List<String> strTracks = new ArrayList<String>(); // initialize temporary arraylist to store track strings
		List<List<String>> strNotes = new ArrayList<List<String>>(); // the main 2D ArrayList with string notes
		
		// --- Here we split the whole data to tracks by newline ( and carriage ) ---
		if (str.contains("\n")) // see if there's newline
		{
			System.out.println("matches");
			strTracks = Arrays.asList(str.split("\\r?\\n")); // AMAZINGLY split to tracks
		} else { // if not then its only one track
			strTracks = new ArrayList<String>();
			strTracks.add(str);
		}
		
		strTracks.removeAll(Collections.singleton("\r")); // get rid of carriage return bitch String if its stuck somewhere
		
		// --- Here we split each track by space so we can have individual notes
		
		for (String track : strTracks)
		{
			if (track.contains(" "))
			{
				strNotes.add(Arrays.asList(track.split(" ")));
			} else { // if only one note
				List<String> singleNoteList = new ArrayList<String>();
				singleNoteList.add(track);
				strNotes.add(singleNoteList);
			}
		}
		
		strTracks = null; // dereference this because the inner Lists are reprogrammed to strNotes
		
		// -- Here we get rid of some bullshit ---
		for (List<String> trak : strNotes)
		{
			trak.removeAll(Collections.singleton("")); // every track String can have empty Strings because it splits by spaces and there can be 2 spaces next to each other
			trak.removeAll(Collections.singleton("\t")); // also get rid of tabs
		}
		
		
		// -- And here we parse the strNotes 2D String array to 2D int array notes which is our return type
		
		int[][] notes = new int[strNotes.size()][]; // int array with unallocated int arrays inside
		
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
