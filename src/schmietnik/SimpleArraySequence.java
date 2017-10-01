package schmietnik;


/*
 * Plasmoxy 2017
 * 
 * Simple midi sequence extension that takes an array with tracks which have
 * arrays with notes inside. Meant to be used polymorphicaly.
 */
import java.util.ArrayList;
import javax.sound.midi.InvalidMidiDataException;
import javax.sound.midi.MidiEvent;
import javax.sound.midi.Sequence;
import javax.sound.midi.ShortMessage;
import javax.sound.midi.Track;

public class SimpleArraySequence extends Sequence
{
	
	public ArrayList<Track> tracks; // not used yet
	
	private static final int defaultVelocity = 100;
	private static final int defaultChannel = 1;
	private static final int noteOnMidiMessage = 144;
	private static final int noteOffMidiMessage = 128;
	
	public SimpleArraySequence(int[][] notes, int velocity, int channel) throws InvalidMidiDataException
	{
		super(PPQ, 4);
		
		tracks = new ArrayList<Track>();
		
		for (int[] trackNotes : notes)
		{
			Track track = createTrack();
			tracks.add(track);
			
			// notei = current note index
			for (int notei = 0; notei < trackNotes.length ; notei++)
			{
				
				if (trackNotes[notei] == -1 || trackNotes[notei] == -2) continue; // if its empty or extender then skip this shit
				
				int noteLength = 1; // one cell, default length, reset for each note
				
				if (trackNotes[notei] != -2) // if the note is not an extender
				{
					// scan for extenders after the note
					int scani = notei+1;
					
					// for every extender right after the note, the duration will increase for one
					// it will also check if the notes array has next element available, if not then it fails the condition
					// ( max index of element is one less than array length)
					while ( ( scani < trackNotes.length ? trackNotes[scani] : 0 ) == -2)
					{
						noteLength++;
						scani++;
					}
				}
				
				ShortMessage noteonmsg = new ShortMessage();
				noteonmsg.setMessage(noteOnMidiMessage, channel, trackNotes[notei], velocity);
				MidiEvent noteon = new MidiEvent(noteonmsg, notei);
				track.add(noteon);
				
				ShortMessage noteoffmsg = new ShortMessage();
				noteoffmsg.setMessage(noteOffMidiMessage, channel, trackNotes[notei], velocity);
				MidiEvent noteoff = new MidiEvent(noteoffmsg, notei + noteLength); // the length will be applied in noteoff event
				track.add(noteoff);
				
			}
		}
		
	}
	
	public SimpleArraySequence(int[][] notes) throws InvalidMidiDataException
	{
		this(notes, defaultVelocity, defaultChannel);
	}
	
	
	public SimpleArraySequence(int[][] notes, int velocity) throws InvalidMidiDataException
	{
		this(notes, velocity, defaultChannel);
	}
	
	

}
