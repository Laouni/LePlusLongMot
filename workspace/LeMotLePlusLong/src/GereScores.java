/***
 * 
 * @author Beauclair BILONG NGOUE - 3iL
 *
 */

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.TreeSet;

public class GereScores {

	private TreeSet<Score> listeScores;
	
	public GereScores() throws JeuException {
		listeScores = new TreeSet<>();
		chargeScore();
	}
	
	public TreeSet<Score> getListeScores() { 
		return listeScores; 
	}
	
	public void setListeScores(TreeSet<Score> listeScores) { 
		this.listeScores = listeScores;
	}
	
	public void ajouteScore(Score s) throws JeuException {
		listeScores.add(s);
		trimListeScores();
		sauveScore();
	}
	
	public void trimListeScores() {
		while (listeScores.size() > Param.NB_SCORES) {
			listeScores.pollLast();
		}
	}

	@SuppressWarnings("unchecked")
	public void chargeScore() throws JeuException {
		File dataDir = new File(System.getProperty("user.dir"), Param.DATA_DIRNAME);
		if (!dataDir.exists()) throw new JeuException("Le dossier data est introuvable.");
		File scoreFile = new File(dataDir, Param.SCORE_FILENAME);
		if (!scoreFile.exists()) throw new JeuException("Le fichier des scores est introuvable.");
		if (scoreFile.length()!=0) {
			try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(scoreFile))) {
				listeScores = (TreeSet<Score>) ois.readObject();
				trimListeScores();
			} catch (Exception e) {
				e.printStackTrace();
				throw new JeuException("Une erreur est survenue lors du chargement des scores.");
			}
		}
	}
	
	public void sauveScore() throws JeuException {
		File dataDir = new File(System.getProperty("user.dir"), Param.DATA_DIRNAME);
		if (!dataDir.exists()) throw new JeuException("Le dossier data est introuvable.");
		File scoreFile = new File(dataDir, Param.SCORE_FILENAME);
		if (!scoreFile.exists()) throw new JeuException("Le fichier des scores est introuvable.");
		
		try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(scoreFile))) {
			oos.writeObject(listeScores);
		} catch (Exception e) {
			throw new JeuException("Une erreur est survenue lors de la sauvegarde de votre score.");
		} 
	}
}
