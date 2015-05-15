/***
 * 
 * @author Beauclair BILONG NGOUE - 3iL
 *
 */

public class MotDico {

	private String mot;
	private char[] chars;
	private int[] occurences;
	
	public MotDico(String mot) {
		chars = new char[Param.LONGUEUR_TIRAGE];
		occurences = new int[Param.LONGUEUR_TIRAGE];	
		setMot(mot);
		decompose();
	}
	
	public void setMot(String mot) { 
		this.mot = mot.toUpperCase(); 
	}
	
	public String getMot() { 
		return mot; 
	}
	
	public void setChars(char[] chars) { 
		this.chars = chars;
	}
	
	public char[] getChars() { 
		return chars; 
	}
	
	public void setOccurences(int[] occurences) { 
		this.occurences = occurences; 
	}
	
	public int[] getOccurences() { 
		return occurences; 
	}

	public void affiche() { 
		System.out.println(mot); 
	}
	
	public boolean egal(String mot) {
	    if (mot.equals(this.mot)) return true; 
	    return false;
	}
	
	public void decompose() {
		char[] motArray = mot.toCharArray();
		int nbLettre=0;
		
		for (int i=0; i<motArray.length; i++) {
			boolean existe = false;
			for (int j=0; j<chars.length; j++) {
				if (motArray[i] == chars[j]) {
					occurences[j] = occurences[j]+1;
					existe = true;
				}
			}	
			if (!existe) {	
				chars[nbLettre] = motArray[i]; 
				occurences[nbLettre] = 1;
				nbLettre++;
			}
		}
	}
	
	public boolean correspond(MotDico m) {
		for (int i=0; i<chars.length; i++) {
			if (chars[i] != '\0') {
				boolean existe = false;
				int j = 0;
				while (!existe && j<m.chars.length) {
					if (chars[i] == m.chars[j]) existe = true;
					else j++;			
				}
				if (!existe) return false;
				else if (occurences[i] > m.occurences[j]) return false;
			}
		}
		return true;
	}
}
