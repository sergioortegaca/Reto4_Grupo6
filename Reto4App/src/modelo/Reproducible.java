package modelo;

public interface Reproducible {
	void loadClip(String filePath);

	void play();

	void pause();

	void resume();
}