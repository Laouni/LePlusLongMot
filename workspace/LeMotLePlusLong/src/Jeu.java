/***
 * 
 * @author Beauclair BILONG NGOUE - 3iL
 *
 */

import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;
import java.util.TreeSet;

public class Jeu {
	
	private Fenetre IHM;
	private GereDico refDico;
	private GereScores refScore;
	
	@SuppressWarnings("unused")
	private ModeJeu MODE;
	
	private int malus;
	private int nbPartie;
	private int score;
	private int scoreMot;
	
	private Timer timer;
	private String pseudo;
	private ArrayList<String> lettresTirage;
	private ArrayList<String> lettresChoix;
	private ArrayList<String> motsTrouves;
	private TreeSet<String> solutions;
	private ArrayList<Integer> indexBonus;
	
	public Jeu(Fenetre ihm) throws JeuException {	
		IHM = ihm;
		verifierParametres();
		refDico = new GereDico();
		refScore = new GereScores();
		solutions = new TreeSet<>();
		refDico.chargerFichier(Param.DICO_FILENAME);
		lettresTirage = new ArrayList<>(Param.LONGUEUR_TIRAGE);
		lettresChoix = new ArrayList<>(Param.LONGUEUR_TIRAGE);
		motsTrouves = new ArrayList<>(Param.NB_MAX_PARTIES);
		indexBonus = new ArrayList<>(Param.LONGUEUR_TIRAGE);
	}
	
	public void verifierParametres() throws JeuException {
		if (Param.LONGUEUR_MOT_MIN > Param.LONGUEUR_MOT_MAX) throw new JeuException("Param�tres incorrects : LONGUEUR_MOT_MIN > LONGUEUR_MOT_MAX");
		if (Param.LONGUEUR_MOT_MAX > Param.LONGUEUR_TIRAGE) throw new JeuException ("Param�tres incorrects : LONGUEUR_MOT_MAX > LONGUEUR_TIRAGE");
		if (Param.NB_MAX_PARTIES == 0) throw new JeuException("Param�tres incorrects : NB_MAX_PARTIES = 0");
		if (Param.DELAI == 0) throw new JeuException("Param�tres incorrects : DELAI = 0");
		if (Param.LONGUEUR_PSEUDO_MIN > Param.LONGUEUR_PSEUDO_MAX) throw new JeuException("Param�tres incorrects : LONGUEUR_PSEUDO_MIN > LONGUEUR_PSEUDO_MAX");
		if (Param.DELAI_ATTENTION == 0) throw new JeuException("Param�tres incorrects : DELAI_ATTENTION = 0");
		if (Param.DELAI_DANGER == 0) throw new JeuException("Param�tres incorrects : DELAI_DANGER = 0");
		if (Param.DELAI_DANGER < Param.DELAI_ATTENTION) throw new JeuException("Param�tres incorrects : DELAI_DANGER < DELAI_ATTENTION");
		if (Param.DELAI_DANGER > Param.DELAI) throw new JeuException("Param�tres incorrects : DELAI_DANGER > DELAI");
		if (Param.LONGUEUR_TIRAGE == 0) throw new JeuException("Param�tres incorrects : LONGUEUR_TIRAGE = 0");
	}
	
	public void ModeDebut() {
		MODE = ModeJeu.DEBUT;
		IHM.creerInterface();
		IHM.reinitialiserTimer();
		ModeInitialisation();
	}
	
	public void ModeInitialisation() {
		MODE = ModeJeu.INITIALISATION;
		score = 0; nbPartie = 0; pseudo = "Pseudo"; lettresTirage.clear(); lettresChoix.clear(); motsTrouves.clear(); indexBonus.clear(); solutions.clear();
		IHM.etatBonusCommande(false);
		IHM.etatCommandes(true, false, false, false, false, false);
		IHM.etatBonusCommande(false);
		IHM.afficherScore();		
		IHM.afficherPartie();
		IHM.afficherPseudo();		
		IHM.effacerChoix();
		IHM.viderListeSolutions();
		IHM.viderListeMotsTrouves();
		IHM.reinitialiserTirage();
		IHM.reinitialiserTimer();
	}
	
	public void ModePreparation() {
		MODE = ModeJeu.PREPARATION; 
		malus = 0; scoreMot = 0; timer = new Timer(); nbPartie++; lettresTirage.clear(); lettresChoix.clear(); indexBonus.clear(); solutions.clear();
		IHM.etatBonusCommande(false);
		IHM.etatCommandes(false, true, true, false, false, false);
		IHM.afficherPseudo();
		IHM.afficherPartie();
		IHM.effacerChoix();
		IHM.viderListeSolutions();
		IHM.reinitialiserTirage();
		IHM.reinitialiserTimer();
	}
	
	public void ModeJeu() {		
		MODE = ModeJeu.JEU;
		ajouterBonus(); lancerTimer();
		if (score >= Param.PRIX_BONUS) IHM.etatBonusCommande(true);
		IHM.etatCommandes(false, false, false, true, false, false);
		IHM.afficherIndexBonus();
		IHM.setChoixLettresOn();	
	}
	
	public void ModeScore() {
		MODE = ModeJeu.SCORE;
		timer.cancel();
		calculerScoreMot();
		trouverSolutions();
		ajouterMotTrouve();
		score += scoreMot;		
		IHM.etatBonusCommande(false);
		IHM.etatCommandes(false, false, false, false, false, true);
		IHM.setChoixLettresOff();
		IHM.afficherScore();
		IHM.afficherListeSolutions();
		IHM.afficherListeMotsTrouves();		
	}
	
	public void afficherChoix() {
		if (lettresChoix.size() >= Param.LONGUEUR_MOT_MIN) {
			String mot = "";
			for (String i : lettresChoix) {
				mot += i;
			}			
			calculerScoreMot();
			IHM.afficherChoix(mot, scoreMot);		
		}
	}
	
	public void ajouterBonus() {
		indexBonus.add((int)(Math.random() * Param.LONGUEUR_MOT_MAX));
	}
	
	public int ajouterConsonne() {
		Double random = Math.random()*Param.consonnes.length;
		lettresTirage.add(Param.consonnes[random.intValue()]);
		IHM.setListeTirage(lettresTirage);
		return lettresTirage.size();
	}
	
	public void ajouterMotTrouve() {
		String mot = "";
		for (String i : lettresChoix) mot += i; 
		motsTrouves.add(mot + " : " + scoreMot);
	}
	
	public int ajouterVoyelle() {
		Double random = Math.random()*Param.voyelles.length;
		lettresTirage.add(Param.voyelles[random.intValue()]);
		IHM.setListeTirage(lettresTirage);
		return lettresTirage.size();
	}
	
	public void calculerScoreMot() {
		String mot = "";
		for (String i : lettresChoix) mot += i; 
		if (refDico.existe(mot)) {	
			scoreMot = getScore(mot);
			for (Integer index : indexBonus) {
				if (lettresChoix.size() > index) {
					scoreMot+=((Param.MULTIPLICATEUR_BONUS-1)*getScore(lettresChoix.get(index)));
				}
			}
			if (scoreMot > malus) { 
				scoreMot = scoreMot-malus; 
			} else {
				scoreMot = 0;
			}
		} else {
			scoreMot = 0;
		}
	}
	
	public int getScore(String mot) {
		int score = 0;
		for (int i=0; i<mot.length(); i++) {
			score += Param.points.get(mot.charAt(i));
		}
		return score;
	}
	
	public void enregistrerScore() throws JeuException {
		Score s = new Score(score, pseudo);
		refScore.ajouteScore(s);
	}
	
	public void lancerTimer() {
		timer.scheduleAtFixedRate(new RemindTask(), 0, 1000);
	}
	
	public void trouverSolutions() {
		String tirage = "";
		for (String s : lettresTirage) tirage+=s; 
		solutions = refDico.chercheMot(tirage);
	}
	
	public void evenement(String source) throws JeuException {
		if (source == "consonne") {
			if (ajouterConsonne() == Param.LONGUEUR_TIRAGE) ModeJeu();
		}
		
		if (source == "voyelle") {
			if (ajouterVoyelle() == Param.LONGUEUR_TIRAGE) ModeJeu();
		}
		
		if (source == "proposer") {
			ModeScore();
		}
		
		if (source == "continuer") {
			if (this.nbPartie == Param.NB_MAX_PARTIES) {
				enregistrerScore();
				ModeInitialisation();
			} else {
				ModePreparation();
			}
		}
		
		if (source == "annuler") {
			lettresChoix.clear(); scoreMot = 0;
			IHM.etatCommandes(false, false, false, true, false, false);
			IHM.activerLettresTirage();
			IHM.reinitialiserLettresChoix();
			IHM.effacerChoix();	
		}
		
		if (source == "bonus") {
			if (score >= Param.PRIX_BONUS) {
				score-=Param.PRIX_BONUS;
				int caseIndex;
				do {
					caseIndex = (int)(Math.random() * Param.LONGUEUR_TIRAGE);
				} while (indexBonus.contains(caseIndex));
				indexBonus.add(caseIndex);
				if (score < Param.PRIX_BONUS || indexBonus.size() == Param.LONGUEUR_TIRAGE) {
					IHM.etatBonusCommande(false);
				}
				afficherChoix();
				IHM.afficherScore();
				IHM.afficherIndexBonus();		
			}
		}	
		
		if (source == "afficherListeScores") {
			IHM.afficherListeScores();
		}
		
		if (source == "nouveauJeu") {
			timer.cancel();
			ModeInitialisation();
		}	
	}
	
	public void evenementLettres(int index, String lettre) {
		this.lettresChoix.add(lettre);
		IHM.setListeChoix(lettresChoix);
		IHM.setEtatLettreTirage(index, false);
		if (lettresChoix.size() >= Param.LONGUEUR_MOT_MIN) {
			IHM.etatCommandes(false, false, false, true, true, false);
			afficherChoix();
		}	
	}
	
	public int getScore() {
		return score;
	}
	
	public int getNbPartie() {
		return nbPartie;
	}
	
	public void setPseudo(String pseudo) {
		this.pseudo = pseudo;
	}
	
	public String getPseudo() {
		return pseudo;
	}
	
	public ArrayList<Integer> getIndexBonus() {
		return indexBonus;
	}
	
	public ArrayList<String> getMotsTrouves() {
		return motsTrouves;
	}
	
	public TreeSet<String> getSolutions() {
		return solutions;
	}
	
	public TreeSet<Score> getListeScores() {
		return refScore.getListeScores();
	}
	
	class RemindTask extends TimerTask {
		int secondesPasses = 0;
		int numSecondes = Param.DELAI;
		
        public void run() {        	
        	if (numSecondes == secondesPasses) ModeScore();
        	
        	int minutes = (numSecondes-secondesPasses) / 60;
        	int secondes = (numSecondes-secondesPasses) % 60;
        	
        	String str = String.format("%d:%02d", minutes, secondes);
            IHM.actualiserTimerLabel(str);
        	IHM.actualiserTimer(secondesPasses);
        	
        	if ((numSecondes-secondesPasses) == Param.DELAI / Param.DELAI_ATTENTION) {
        		malus = Param.MALUS_ATTENTION;
        		afficherChoix();
        		IHM.setEtatTimer(1);
        	} else if ((numSecondes-secondesPasses) == Param.DELAI / Param.DELAI_DANGER) {
        		malus = Param.MALUS_DANGER;
        		afficherChoix();
        		IHM.setEtatTimer(2);
        	}
        	
        	secondesPasses++;         
        }
    }
	
}
