package modelo;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Time;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;

public abstract class Audio {

	private Clip audio;
	@SuppressWarnings("unused")
	private boolean paused;
	private long clipTimePosition;
	protected int audioID;
	protected String nombreMultimedia;
	protected Time duracion;
	protected String imagenMultimedia;
	protected String tipoMultimedia;
	protected int reproducciones;

	
	public String getTipoMultimedia() {
		return tipoMultimedia;
	}

	public void setTipoMultimedia(String tipoMultimedia) {
		this.tipoMultimedia = tipoMultimedia;
	}

	public int getReproducciones() {
		return reproducciones;
	}

	public void setReproducciones(int reproducciones) {
		this.reproducciones = reproducciones;
	}

	public int getAudioID() {
		return audioID;
	}

	public void setAudioID(int audioID) {
		this.audioID = audioID;
	}

	public String getNombreMultimedia() {
		return nombreMultimedia;
	}

	public void setNombreMultimedia(String nombreMultimedia) {
		this.nombreMultimedia = nombreMultimedia;
	}

	public Time getDuracion() {
		return duracion;
	}

	public void setDuracion(Time duracion) {
		this.duracion = duracion;
	}

	public String getImagenMultimedia() {
		return imagenMultimedia;
	}

	public void setImagenMultimedia(String imagenMultimedia) {
		this.imagenMultimedia = imagenMultimedia;
	}

	// Cancion audio = new Cancion();

	public void loadClip(String filePath) {
		try {
			InputStream audioInputStream = getClass().getResourceAsStream(filePath);
			if (audioInputStream == null) {
				System.err.println("No se pudo encontrar el archivo de audio: " + filePath);
				return;
			}
			AudioInputStream ais = AudioSystem.getAudioInputStream(new BufferedInputStream(audioInputStream));
			audio = AudioSystem.getClip();
			audio.open(ais);
		} catch (UnsupportedAudioFileException | IOException | LineUnavailableException e) {
			e.printStackTrace();
		}
	}

	public void play() {
		if (audio != null) {
			audio.start();
			
		}
		
	}

	public void pause() {
		if (audio != null && audio.isRunning()) {
			paused = true;
			clipTimePosition = audio.getMicrosecondPosition();
			audio.stop();
		}
	}

	public void resume() {
		if (audio != null && audio.isRunning()) {

			clipTimePosition = 0; // Reiniciar desde el principio
			audio.setMicrosecondPosition(clipTimePosition);
			audio.start();
			paused = false;

		}
	}
}