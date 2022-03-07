package util;

public class Time {
	public static float timeStarted = System.nanoTime();
	
	// gives time since application started
	public static float getTime() { return (float)((System.nanoTime()- timeStarted) * 1E-9); }
	
	
}
