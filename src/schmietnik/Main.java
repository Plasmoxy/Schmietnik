package schmietnik;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;

import sun.util.logging.PlatformLogger;

public final class Main
{
	
	private static final String VERSION = "1.2";
	private static final String VERSION_FULL = "v" + VERSION + " Janna";
	
	private static final String readFile(String path) throws IOException // snippet method for reading files as strings
	{
		byte[] encoded = Files.readAllBytes(Paths.get(path));
		return new String(encoded, StandardCharsets.UTF_8);
	}
	
	public static void main(String[] args) throws Exception
	{
		// turn off platform logger so it doesn't show the warning
		sun.util.logging.PlatformLogger platformLogger = PlatformLogger.getLogger("java.util.prefs");
		platformLogger.setLevel(PlatformLogger.Level.OFF);
		
		System.out.println("\n--- Schmietnik txt midi cell player by Plasmoxy xDDD --- < argument \"help\" for info >");
		System.out.println("Version : " + VERSION_FULL);
		System.out.println();
		
		String fileName = "notes.txt";
		int tempo = 80;
		
		if (args.length >= 1)
		{
			if (args[0].equals("help"))
			{
				System.out.println("\n1. argument : file name with notes (or help) (default = notes.txt)\n"
						+ "2. argument : tempo (in BPM) (default = 80)\n"
						+ "Writing notes file guide :\n"
						+ "Each track is separated by newline, so one line per track.\n"
						+ "Simultaneous note cells are separated by spaces.\n"
						+ "Each cell consists of 3 characters :\n"
						+ " 1. note letter (CDEFGAB), for empty note use \"-\"\n"
						+ " 2. note shift ( # or b ), for no shift again just use \"-\"\n"
						+ " 3. note octave -> 0 to 10, for tenth octave use letter \"A\"\n"
						+ "\n"
						+ "Extender :\n"
						+ " -> If you wish to extend note ( so it has bigger duration ), you can use the extender cell \"...\"\n"
						+ " for example C-4 ... ... D-4 will play C-4 for duration of 3 cells and then it will play D-4."
						+ "Example :\n"
						+ "C-4 D-4 Eb4 E#6 ...\n"
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
		}
		
		String notesSource = null;
		try {
			notesSource = readFile(fileName);
		} catch (IOException e) {
			System.out.println("ERROR : File not found.");
			System.exit(-1);
		}
		
		int[][] notes = NoteParser.niceParse(notesSource);
		
		SimpleArraySequence seq = new SimpleArraySequence(notes);
		MidiPlayer player = new MidiPlayer(seq, tempo);
		
		player.play();
		
		System.out.println();
		
	}
	
}
