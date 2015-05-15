/***
 * 
 * @author Beauclair BILONG NGOUE - 3iL
 *
 */

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.HashSet;
import java.util.TreeSet;

public class GereDico {

	public HashSet<MotDico> listeMot;
	
	public GereDico() {
		listeMot = new HashSet<>();
	}
	
	public void chargerFichier(String nomFichier) throws JeuException {	
		
		File dataDir = new File(System.getProperty("user.dir"), Param.DATA_DIRNAME);		
		if (!dataDir.exists()) throw new JeuException("Le dossier data est introuvable.");
		File dico = new File(dataDir, nomFichier);
		if (!dico.exists()) throw new JeuException("Le dictionnaire est introuvable.");	
		
		try (BufferedReader br = new BufferedReader(new FileReader(dico))) {
			String saisie;			
			while ((saisie = br.readLine()) != null) {
				if (!saisie.contains("-") && saisie.length() >= Param.LONGUEUR_MOT_MIN && saisie.length() <= Param.LONGUEUR_MOT_MAX) {
					listeMot.add(new MotDico(saisie));
				}
			}
		} catch (Exception e) {
			throw new JeuException("Un probl�me est survenu lors de la lecture du dictionnaire");
		}
	}
	
	public boolean existe(String chaine) {
		for (MotDico m : listeMot) { 
			if (m.egal(chaine)) return true; 
		}	
		return false;
	}
	
	public TreeSet<String> chercheMot(String listeChars) {
		MotDico motTest = new MotDico(listeChars);
		TreeSet<String> listeMotCherche = new TreeSet<>();	
		for (MotDico m : listeMot) {
			if (m.correspond(motTest)) listeMotCherche.add(m.getMot());
		}
		return listeMotCherche;
	}
}
