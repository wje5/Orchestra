package com.zonesoft.orchestra;

import java.io.IOException;
import java.io.InputStream;
import java.util.UUID;

import javax.sound.midi.InvalidMidiDataException;
import javax.sound.midi.MidiDevice;
import javax.sound.midi.MidiDevice.Info;
import javax.sound.midi.MidiEvent;
import javax.sound.midi.MidiSystem;
import javax.sound.midi.MidiUnavailableException;
import javax.sound.midi.Receiver;
import javax.sound.midi.Sequence;
import javax.sound.midi.Sequencer;
import javax.sound.midi.ShortMessage;
import javax.sound.midi.Synthesizer;
import javax.sound.midi.Track;

import net.minecraft.client.Minecraft;
import net.minecraft.resources.IResource;
import net.minecraft.util.ResourceLocation;

public class ClientMidiHandler {
	private static Sequencer sequencer;
	private static Track track;
	public static final ResourceLocation LOCATION = new ResourceLocation("orchestra:audio/sound.sf2");

	public static void init() {
		try {
			sequencer = MidiSystem.getSequencer();
		} catch (MidiUnavailableException e) {
			e.printStackTrace();
			return;
		}
		InputStream stream = null;
		try {
			IResource res = Minecraft.getInstance().getResourceManager().getResource(LOCATION);
			if (res != null) {
				stream = res.getInputStream();
				for (Info i : MidiSystem.getMidiDeviceInfo()) {
					MidiDevice d = null;
					try {
						d = MidiSystem.getMidiDevice(i);
						if (d instanceof Synthesizer) {
							Receiver receiver = d.getReceiver();
							d.open();
							((Synthesizer) d).loadAllInstruments(MidiSystem.getSoundbank(stream));
							sequencer.getTransmitter().setReceiver(receiver);
						}
					} catch (MidiUnavailableException | InvalidMidiDataException | IOException e) {
						e.printStackTrace();
						continue;
					}
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		try {
			Sequence seq = new Sequence(Sequence.PPQ, 4);
			track = seq.createTrack();
			sequencer.open();
			sequencer.setSequence(seq);
			sequencer.setTempoInBPM(2400);
			track.add(new MidiEvent(new ShortMessage(ShortMessage.NOTE_ON, 0, 48, 65), 500000000));
			sequencer.start();
		} catch (MidiUnavailableException | InvalidMidiDataException e) {
			e.printStackTrace();
		}
	}

	private static void onMessage(int command, int channel, int pitch, int keystroke) {
		if (track != null) {
			try {
				track.add(new MidiEvent(new ShortMessage(command, channel, pitch, keystroke),
						sequencer.getTickPosition()));
			} catch (InvalidMidiDataException e) {
				e.printStackTrace();
			}
		}
	}

	public static void onMessage(UUID player, int command, int pitch, int keystroke) {
		onMessage(command, 0, pitch, keystroke);// XXX
	}
}
