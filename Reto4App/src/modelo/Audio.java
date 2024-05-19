package modelo;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;

public abstract class Audio extends Album implements Reproducible {

	private Clip audio;
	@SuppressWarnings("unused")
	private boolean paused;
	private long clipTimePosition;
	protected int audioID;
	protected String nombreMultimedia;
	protected String duracion;
	protected String imagenMultimedia;
	protected String tipoMultimedia;
	protected int reproducciones;
	protected int numeroMeGustas;

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

	public int getNumeroMeGustas() {
		return numeroMeGustas;
	}

	public void setNumeroMeGustas(int numeroMeGustas) {
		this.numeroMeGustas = numeroMeGustas;
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

	public String getDuracion() {
		return duracion;
	}

	public void setDuracion(String duracion) {
		this.duracion = duracion;
	}

	public String getImagenMultimedia() {
		return imagenMultimedia;
	}

	public void setImagenMultimedia(String imagenMultimedia) {
		this.imagenMultimedia = imagenMultimedia;
	}

	@Override
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

	@Override
	public void play() {
		if (audio != null) {
			audio.start();
		}

	}

	@Override
	public void pause() {
		if (audio != null && audio.isRunning()) {
			paused = true;
			clipTimePosition = audio.getMicrosecondPosition();
			audio.stop();
		}
	}

	@Override
	public void resume() {
		if (audio != null && audio.isRunning()) {

			clipTimePosition = 0;
			audio.setMicrosecondPosition(clipTimePosition);
			audio.start();
			paused = false;

		}
	}
}