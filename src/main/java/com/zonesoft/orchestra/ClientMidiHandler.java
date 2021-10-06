package com.zonesoft.orchestra;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import javax.sound.midi.InvalidMidiDataException;
import javax.sound.midi.MidiEvent;
import javax.sound.midi.MidiSystem;
import javax.sound.midi.MidiUnavailableException;
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
	public static final ResourceLocation LOCATION = new ResourceLocation("orchestra:audio/sound.sf2");
	private static Map<UUID, Integer> map = new HashMap<UUID, Integer>();

	public static void init() {
		long start = System.nanoTime();
		try {
			sequencer = MidiSystem.getSequencer();
		} catch (MidiUnavailableException e) {
			e.printStackTrace();
			return;
		}
		try {
			IResource res = Minecraft.getInstance().getResourceManager().getResource(LOCATION);
			if (res != null) {
				try (InputStream stream = new BufferedInputStream(res.getInputStream())) {
					Synthesizer syn = MidiSystem.getSynthesizer();
					syn.open();
					syn.loadAllInstruments(MidiSystem.getSoundbank(stream));
				} catch (MidiUnavailableException | InvalidMidiDataException e) {
					e.printStackTrace();
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		long bank = System.nanoTime();
		System.out.println("bank:" + (bank - start));
		try {
			Sequence seq = new Sequence(Sequence.PPQ, 4);
			Track track = seq.createTrack();
			sequencer.open();
			sequencer.setSequence(seq);
			sequencer.setTempoInBPM(2400);
			track.add(new MidiEvent(new ShortMessage(ShortMessage.NOTE_ON, 0, 48, 65), 500000000));
			sequencer.start();
		} catch (MidiUnavailableException | InvalidMidiDataException e) {
			e.printStackTrace();
		}
		System.out.println("end:" + (System.nanoTime() - bank));
	}

	private static void onMessage(int track, int command, int channel, int pitch, int keystroke) {
		System.out.println(track + "|" + command + "|" + channel + "|" + pitch + "|" + keystroke);
		try {
			sequencer.getSequence().getTracks()[track].add(
					new MidiEvent(new ShortMessage(command, channel, pitch, keystroke), sequencer.getTickPosition()));
		} catch (InvalidMidiDataException e) {
			e.printStackTrace();
		}
	}

	public static void onMessage(UUID player, int command, int channel, int pitch, int keystroke) {
		Minecraft mc = Minecraft.getInstance();
		if (player.equals(mc.player.getUniqueID())) {
			onMessage(0, command, channel, pitch, keystroke);
		} else if (map.containsKey(player)) {
			onMessage(map.get(player), command, channel, pitch, keystroke);
		} else {
			for (int i = 0;; i++) {
				if (!map.containsValue(i)) {
					map.put(player, i);
					onMessage(i, command, channel, pitch, keystroke);
					break;
				}
			}
		}
	}

	public static void reset() {
		long start = System.nanoTime();
		try {
			sequencer.setTickPosition(0);
			Sequence seq = new Sequence(Sequence.PPQ, 4);
			Track track = seq.createTrack();
			track.add(new MidiEvent(new ShortMessage(ShortMessage.NOTE_ON, 0, 48, 65), 500000000));
			sequencer.setSequence(seq);
			sequencer.setTempoInBPM(3600);
//			sequencer.open();
			sequencer.start();
		} catch (InvalidMidiDataException e) {
			e.printStackTrace();
		}
		System.out.println("reset:" + (System.nanoTime() - start));
	}
}
