package doge;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.UnsupportedAudioFileException;

import org.lwjgl.openal.*;
import org.newdawn.slick.Music;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.openal.Audio;
import org.newdawn.slick.openal.AudioLoader;
import org.newdawn.slick.openal.WaveData;
import org.newdawn.slick.util.ResourceLoader;

public class AutoInitialization {

	public static Level[] initiliaze(Level[] levels) throws UnsupportedAudioFileException, IOException {
		Dialogue[] d1 = new Dialogue[1];
		d1[0] = new Dialogue(100, 50, "It sure is a beautiful day outside. I should probably mow my lawn.");
		Dialogue[] d2 = new Dialogue[2];
		d2[0] = new Dialogue(-650, 50, "Hmm, strange things are happening lately. Perhaps I should check Ponyville to see what's going on...");
		d2[1] = new Dialogue(-650, 75, "...Like these nuclear explosions in the distance. Is there a war of some sort?");
		Dialogue[] d3 = new Dialogue[1];
		d3[0] = new Dialogue();
		Prop[] p1 = new Prop[1];
		Prop[] p2 = new Prop[1];
		p2[0] = new Prop(-650, 75, 256, 256, Main.loadTexture("nuke", "jpg"), false, false, 0, 0, 0);
		Prop[] p3 = new Prop[1];
		Prop[] p5 = {new Train(5888, 896, 512, 128, Main.loadTexture("train", "jpg"))};
		levels[0] = new Level();
		Audio oggEffect15 = AudioLoader.getStreamingAudio("OGG", ResourceLoader.getResource("C:\\Users\\Wenfeng\\Desktop\\eclipse\\Java\\Doge Adventures\\src\\doge\\appleloosa.ogg"));
		levels[0].addLocation(new Location(Main.loadTexture("living room","jpg"), 1024, 1024, d1, p1, null, null, 0, null));
		levels[0].addLocation(new Location(Main.loadTexture("lawn","jpg"), 1024, 1024, d2, p2, new Key(-100, 800, false), null, 0, null));
		levels[0].addLocation(new Location(Main.loadTexture("battlefield 1","png"), 4096, 2048, d3, p3, null, new RoyalGuard(1200, 600), 0, null));//number of enemies needed is supposed to be 48
		levels[0].addLocation(new Location(Main.loadTexture("train station", "png"), 1024, 512, null, null, null, null, 0, null));//number of enemies needed is supposed to be 20
		levels[0].addLocation(new Location(Main.loadTexture("train background", "jpg"), 8192, 2048, null, p5, null, new LightBomber(6144, 900), 48, null));
		levels[0].addLocation(new Location(Main.loadTexture("appleloosa1", "png"), 2048, 1024, null, null, null, null, 0, oggEffect15));
		return levels;
	}
	
	public static int getBuffer(WaveData data) {
		int buffer = AL10.alGenBuffers();
		AL10.alBufferData(buffer, data.format, data.data, data.samplerate);
		data.dispose();
		int source = AL10.alGenSources();
		AL10.alSourcei(source, AL10.AL_BUFFER, buffer);
		return buffer;
	}
}
