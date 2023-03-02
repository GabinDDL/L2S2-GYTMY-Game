package com.gytmy.sound;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

import javax.sound.sampled.Clip;
import javax.swing.JLabel;
import javax.swing.JProgressBar;

/**
 * This class counts playing time in the form of HH:mm:ss
 * It also updates the time slider
 * 
 * @see https://www.codejava.net/coding/java-audio-player-sample-application-in-swing
 *
 */
public class PlayingTimer extends Thread {
	private DateFormat dateFormater = new SimpleDateFormat("mm:ss");
	private boolean isRunning = false;
	private boolean isReset = false;
	private long startTime;
	private long pauseTime;

	private JLabel labelRecordTime;
	private JProgressBar timeProgress;
	private Clip audioClip;

	public void setAudioClip(Clip audioClip) {
		this.audioClip = audioClip;
	}

	public PlayingTimer(JLabel labelRecordTime, JProgressBar timeProgress) {
		this.labelRecordTime = labelRecordTime;
		this.timeProgress = timeProgress;
	}

	public void run() {
		isRunning = true;

		startTime = System.currentTimeMillis();

		while (isRunning) {
			try {
				Thread.sleep(100);
				if (audioClip != null && audioClip.isRunning()) {
					labelRecordTime.setText(toTimeString());
					int currentSecond = (int) audioClip.getMicrosecondPosition() / 1_000_000;
					timeProgress.setValue(currentSecond);
				}
			} catch (InterruptedException ex) {
				if (isReset) {
					timeProgress.setValue(0);
					labelRecordTime.setText("00:00");
					isRunning = false;
					break;
				}
			}
		}
	}

	/**
	 * Reset counting to "00:00:00"
	 */
	public void reset() {
		isReset = true;
		isRunning = false;
	}

	/**
	 * Generate a String for time counter in the format of "HH:mm:ss"
	 * 
	 * @return the time counter
	 */
	private String toTimeString() {
		long now = System.currentTimeMillis();
		Date current = new Date(now - startTime - pauseTime);
		dateFormater.setTimeZone(TimeZone.getTimeZone("GMT"));
		String timeCounter = dateFormater.format(current);
		return timeCounter;
	}
}