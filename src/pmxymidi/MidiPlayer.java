package pmxymidi;

import javax.sound.midi.InvalidMidiDataException;
import javax.sound.midi.MetaEventListener;
import javax.sound.midi.MetaMessage;
import javax.sound.midi.MidiSystem;
import javax.sound.midi.MidiUnavailableException;
import javax.sound.midi.Sequence;
import javax.sound.midi.Sequencer;

/*
 * Plasmoxy 2017
 * 
 * Simple Midi player that takes a sequence and tempo with nice play and stop
 * methods.
 * 
 */

public class MidiPlayer
{
	
	private Sequence seq;
	private Sequencer sqcr;
	private float tempo;
	
	private final int finishDelay = 3000;
	
	public MidiPlayer(Sequence constr_seq, float constr_tempo) throws MidiUnavailableException, InvalidMidiDataException {
		
		seq = constr_seq;
		tempo = constr_tempo;
		
		sqcr = MidiSystem.getSequencer();
		
		sqcr.setSequence(seq);
		
		sqcr.addMetaEventListener(new MetaEventListener() {

            @Override
            public void meta(MetaMessage metaMsg) {
                if (metaMsg.getType() == 0x2F) {
                	try {Thread.sleep(finishDelay);}catch(InterruptedException e) {}
                    sqcr.close();
                }
            }
        });
		
		sqcr.open();
		
		// need thread sleep because it takes tame to initialize, otherwise it sounds weird
		try { Thread.sleep(100); } catch(InterruptedException e) {}
		
	}
	
	public void play() throws MidiUnavailableException {
		sqcr.setTempoInBPM(tempo);
		sqcr.start();
	}
	
	public void stop() {
		sqcr.stop();
	}
	
	public void setTempo(float bpm)
	{
		sqcr.setTempoInBPM(bpm);
	}

}
