/***
 * 
 * @author Beauclair BILONG NGOUE - 3iL
 *
 */

import java.util.HashMap;
import java.util.Map;

public class Param {
	public static String DATA_DIRNAME = "data";
	public static String DICO_FILENAME = "DicoSansAccent.txt";
	public static String SCORE_FILENAME = "MeilleursScores.bin";
	public static String IMG_DIRNAME = "images";
	public static int NB_SCORES = 10; //nombre de scores que l'on stocke
	public static int LONGUEUR_PSEUDO_MIN = 3;
	public static int LONGUEUR_PSEUDO_MAX = 8;
	public static int LONGUEUR_MOT_MIN = 4; //longueur min des mots pris dans le dictionnaire
	public static int LONGUEUR_MOT_MAX = 9; //longueur max des mots pris dans le dictionnaire
	public static int NB_MAX_PARTIES = 5;
	public static int LONGUEUR_TIRAGE = 9;
	public static int DELAI = 60; //en secondes
	public static int DELAI_ATTENTION = 2; //on divise par ce nombre (2=il reste la moiti� du temps)
	public static int DELAI_DANGER = 10; //on divise par ce nombre (10=il reste un dixi�me du temps)
	public static int MALUS_ATTENTION = 1; //malus de point au mot lorsque le timer entre dans la zone "attention"
	public static int MALUS_DANGER = 2; //malus de point au mot lorsque le timer entre la zone "danger" (ne se cumule pas avec MALUS_ATTENTION)
	public static int PRIX_BONUS = 3; //3=le score diminue de 3 points pour l'achat d'une lettre bonus
	public static int MULTIPLICATEUR_BONUS = 3; //3=la lettre bonus vaut le triple de point
	public static int pointsLettreA = 1;
	public static int pointsLettreB = 3;
	public static int pointsLettreC = 3;
	public static int pointsLettreD = 2;
	public static int pointsLettreE = 1;
	public static int pointsLettreF = 4;
	public static int pointsLettreG = 2;
	public static int pointsLettreH = 4;
	public static int pointsLettreI = 1;
	public static int pointsLettreJ = 8;
	public static int pointsLettreK = 10;
	public static int pointsLettreL = 1;
	public static int pointsLettreM = 2;
	public static int pointsLettreN = 1;
	public static int pointsLettreO = 1;
	public static int pointsLettreP = 3;
	public static int pointsLettreQ = 8;
	public static int pointsLettreR = 1;
	public static int pointsLettreS = 1;
	public static int pointsLettreT = 1;
	public static int pointsLettreU = 1;
	public static int pointsLettreV = 4;
	public static int pointsLettreW = 10;
	public static int pointsLettreX = 10;
	public static int pointsLettreY = 10;
	public static int pointsLettreZ = 10;
	public static String[] voyelles = {"A", "A", "A", "A", "A", "A", "A", "A", "A", "E", "E", "E",
		"E", "E", "E", "E", "E", "E", "E", "E", "E", "E", "E", "E", "I", "I", "I", "I", "I", "I", "I", "I",
	    "O", "O", "O", "O", "O", "O", "U", "U", "U", "U", "U", "U", "Y"};
	public static String[] consonnes = {"B", "B", "C", "C", "D", "D", "D", "F", "F", "G", "G", "H", "H",
		"J", "K", "L", "L", "L", "L", "L", "M", "M", "M", "N", "N", "N", "N", "N", "N", "P", "P", "Q", "R",
		"R", "R", "R", "R", "R", "S", "S", "S", "S", "S", "S", "T", "T", "T", "T", "T", "T", "V", "V", "W",
		"X", "Z"};
	public static Map<Character, Integer> points;
	static {
		points = new HashMap<>();
		points.put('A', Param.pointsLettreA);
		points.put('B', Param.pointsLettreB);
		points.put('C', Param.pointsLettreC);
		points.put('D', Param.pointsLettreD);
		points.put('E', Param.pointsLettreE);
		points.put('F', Param.pointsLettreF);
		points.put('G', Param.pointsLettreG);
		points.put('H', Param.pointsLettreH);
		points.put('I', Param.pointsLettreI);
		points.put('J', Param.pointsLettreJ);
		points.put('K', Param.pointsLettreK);
		points.put('L', Param.pointsLettreL);
		points.put('M', Param.pointsLettreM);
		points.put('N', Param.pointsLettreN);
		points.put('O', Param.pointsLettreO);
		points.put('P', Param.pointsLettreP);
		points.put('Q', Param.pointsLettreQ);
		points.put('R', Param.pointsLettreR);
		points.put('S', Param.pointsLettreS);
		points.put('T', Param.pointsLettreT);
		points.put('U', Param.pointsLettreU);
		points.put('V', Param.pointsLettreV);
		points.put('W', Param.pointsLettreW);
		points.put('X', Param.pointsLettreX);
		points.put('Y', Param.pointsLettreY);
		points.put('Z', Param.pointsLettreZ);	
	}
}