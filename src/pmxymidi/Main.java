package pmxymidi;

import java.io.FileInputStream;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;

public class Main
{
	
	static String readFile(String path) throws IOException 
	{
		byte[] encoded = Files.readAllBytes(Paths.get(path));
		return new String(encoded, StandardCharsets.UTF_8);
	}
	
	public static void main(String[] args) throws Exception
	{
		
		System.out.println("\n--- PmxyMIDI txt midi cell player by Plasmoxy xDDD --- < argument \"help\" for info >");
		
		String fileName = "notes.txt";
		int tempo = 80;
		int duration = 1;
		
		if (args.length >= 1)
		{
			if (args[0].equals("help"))
			{
				System.out.println("\n1. argument : file name with notes (or help) (default = notes.txt)\n"
						+ "2. argument : tempo (in BPM) (default = 80)\n"
						+ "3. argument : note duration (length in cells) ( default = 1 )\n\n"
						+ "Writing notes file guide :\n"
						+ "Each track is separated by newline, so one row per line.\n"
						+ "Simultaneous note cells are separated by spaces.\n"
						+ "Each cell consists of 3 characters :\n"
						+ " 1. note letter (CDEFGAB), for empty note use \"-\"\n"
						+ " 2. note shift ( # or b ), for no shift again just use \"-\"\n"
						+ " 3. note octave -> 0 to 10, for tenth octave use letter \"A\"\n"
						+ "\n"
						+ "Example :\n"
						+ "C-4 D-4 Eb4 E#6\n"
						+ "E-6 --- Bb0\n"
						+ "\nNow go and waste your life by making songs in this LOL xDDDD\n"
						);
				System.exit(0);
			}
			fileName = args[0];
			if (args.length >= 2)
			{
				try {
					tempo = Integer.valueOf(args[1]);
				} catch (NumberFormatException e) {
					System.out.println("Tempo must be a number !");
				}
			}
			if (args.length >= 3)
			{
				try {
					duration = Integer.valueOf(args[2]);
				} catch (NumberFormatException e) {
					System.out.println("Duration must be a number !");
				}
			}
		}
		
		String notesSource = null;
		try {
			notesSource = readFile(fileName);
		} catch (IOException e) {
			System.out.println("ERROR : File not found.");
			System.exit(-1);
		}
		
		int[][] notes = NoteParser.niceParse(notesSource);
		
		SimpleArraySequence seq = new SimpleArraySequence(notes, duration);
		MidiPlayer player = new MidiPlayer(seq, tempo);
		
		player.play();
		
		System.out.println();
		
	}
	
}
