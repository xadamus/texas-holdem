package game;

import java.util.HashMap;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;

public class GameSpeaker
{
	public enum Sounds { BUTTON, BUTTON_PRESSED }
	
	private Clip button;
	private HashMap<Sounds, Clip> sounds = new HashMap<>();
	
	public GameSpeaker()
	{
		sounds.put(Sounds.BUTTON, loadClip("/resources/button.wav"));
		sounds.put(Sounds.BUTTON_PRESSED, loadClip("/resources/button_pressed.wav"));
	}
	
	private Clip loadClip(String filename)
	{
		Clip clip = null;
		try
		{
			AudioInputStream audioIn = AudioSystem.getAudioInputStream(this.getClass().getResource(filename));
			clip = AudioSystem.getClip();
			clip.open(audioIn);
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		
		return clip;
	}
	
	public void playClip(Sounds sound)
	{
		Clip clip = sounds.get(sound);
		stopClip(sound);
		clip.start();
	}
	
	public void stopClip(Sounds sound)
	{
		Clip clip = sounds.get(sound);
		if(clip.isRunning())
			clip.stop();
		clip.setFramePosition(0);
	}
}
