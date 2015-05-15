/***
 * 
 * @author Beauclair BILONG NGOUE - 3iL
 *
 */

import java.io.Serializable;
import java.util.Date;

public class Score implements Serializable, Comparable<Score> {

	private static final long serialVersionUID = 1L;
	private Integer score;
	private String pseudo;
	private Date date;
	
	public Score(Integer score, String pseudo) {
		setScore(score);
		setPseudo(pseudo);
		setDate(new Date());
	}

	public Integer getScore() {
		return score;
	}

	public void setScore(Integer score) {
		this.score = score;
	}

	public String getPseudo() {
		return pseudo;
	}

	public void setPseudo(String pseudo) {
		this.pseudo = pseudo;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	@Override
	public int compareTo(Score s) {
		if (score >= s.getScore()) return -1;
		return 1;
	}
}
