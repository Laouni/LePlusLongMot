/***
 * 
 * @author Beauclair BILONG NGOUE - 3iL
 *
 */

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Iterator;

import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.JScrollPane;

public class Fenetre implements ActionListener {

	public static void main(String[] args) {
		Fenetre window = new Fenetre();
		try {
			window.jeu = new Jeu(window);
			window.jeu.ModeDebut();
		} catch (Exception e) {
			window.afficherErreur(e.getMessage());
		}
	}
	
	private JFrame frame;

	Font fonteChoix = new Font("Calibri", Font.BOLD, 20);
	Font fonteTirage = new Font("Calibri", Font.BOLD, 20);
	Font fonteSolutionsLabel = new Font("Comic Sans MS", Font.BOLD, 16);
	Font fontePartie = new Font("Comic Sans MS", Font.BOLD, 30);
	Font fonteScore = new Font("Comic Sans MS", Font.BOLD, 40);
	Font fontePseudo = new Font("Comic Sans MS", Font.BOLD, 30);
	Font fonteMotsTrouvesLabel = new Font("Comic Sans MS", Font.BOLD, 16);
	Font fonteMotScore = new Font("Comic Sans MS", Font.BOLD, 40);
	Font fonteListeScores = new Font("Calibri", Font.BOLD, 16);
	
	//Menu
	JMenuBar menuBar;
	JMenu mnJeu;
	JMenuItem mntmNouveauJeu;
	JMenu mnScores;
	JMenuItem mntmAfficherLesScores;
	
	//North Panel
	JLabel pseudoLabel;
	JLabel scoreLabel;
	JLabel numPartieLabel;
	
	//East Panel
	JLabel solutionsLabel;
	JList<String> solutionsList;
	JLabel motsTrouvesLabel;
	JList<String> motsTrouvesList;
	
	//Timer
	JLabel timerLabel;
	JProgressBar timerProgressBar;
	
	//Commandes
	JButton bonusButton;
	JPanel gridPanel;	
	JButton commencerButton;
	JButton annulerButton;
	JButton proposerButton;
	JButton continuerButton;
	JButton voyelleButton;
	JButton consonneButton;
	
	//Choix + score 
	JLabel choixLabel;
	
	//tirage et choix
	JButton[] tirageTab = new JButton[Param.LONGUEUR_TIRAGE];
	JLabel[] choixTab = new JLabel[Param.LONGUEUR_TIRAGE];
	
	Jeu jeu;

	/**
	 * @wbp.parser.entryPoint
	 */
	public Fenetre() {
		
	}

	public void afficherErreur(String message) {
		JOptionPane.showMessageDialog(new JPanel(), message, "Erreur", JOptionPane.ERROR_MESSAGE);
	}

	public void creerInterface() {
		frame = new JFrame();
		frame.setIconImage(Toolkit.getDefaultToolkit().getImage(Param.IMG_DIRNAME+"\\iconeGame.png"));
		frame.setTitle("Le mot le plus long");
		int k = Param.LONGUEUR_TIRAGE*88;
		frame.setSize((k > 800) ? k : 800, 600);
		frame.setLocationRelativeTo(null);
		frame.setResizable(false);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
				
		//Menu
		menuBar = new JMenuBar(); 
		mnJeu = new JMenu("Jeu"); 
		menuBar.add(mnJeu);
		mntmNouveauJeu = new JMenuItem("Nouveau jeu");
		mnJeu.add(mntmNouveauJeu);
		mnScores = new JMenu("Scores"); 
		menuBar.add(mnScores);
		mntmAfficherLesScores = new JMenuItem("Afficher les scores"); 
		mnScores.add(mntmAfficherLesScores);
		
		frame.setJMenuBar(menuBar);
				
		frame.getContentPane().setLayout(new BorderLayout(0, 0));
		
		//North Panel
		JPanel northPanel = new JPanel();
		northPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 35, 5));	
		northPanel.setBackground(Color.black);
		northPanel.setOpaque(true);
		pseudoLabel = new JLabel("New label");
		pseudoLabel.setFont(fontePseudo);
		pseudoLabel.setForeground(Color.orange);
		scoreLabel = new JLabel("New label");
		scoreLabel.setFont(fonteScore);
		scoreLabel.setForeground(Color.orange);
		numPartieLabel = new JLabel("New label"); 
		numPartieLabel.setFont(fontePseudo);
		numPartieLabel.setForeground(Color.orange);
		northPanel.add(pseudoLabel);
		northPanel.add(scoreLabel);	
		northPanel.add(numPartieLabel);
		
		frame.getContentPane().add(northPanel, BorderLayout.NORTH);
		
		//East Panel
		JPanel eastPanel = new JPanel();
		eastPanel.setLayout(new BoxLayout(eastPanel, BoxLayout.Y_AXIS));	
		eastPanel.setPreferredSize(new Dimension(160, 80));
		eastPanel.setMaximumSize(new java.awt.Dimension(160, frame.getHeight()));	
		solutionsLabel = new JLabel("Solutions"); 
		solutionsLabel.setFont(fonteSolutionsLabel);
		motsTrouvesLabel = new JLabel("Mots trouv�s"); 
		motsTrouvesLabel.setFont(fonteMotsTrouvesLabel);		
		solutionsList = new JList<>();
		motsTrouvesList = new JList<>();		
		JScrollPane solutionsScrollPane = new JScrollPane(solutionsList); 
		solutionsScrollPane.setPreferredSize(new Dimension(150, 80));
		JScrollPane motsTrouvesScrollPane = new JScrollPane(motsTrouvesList); 
		motsTrouvesScrollPane.setPreferredSize(new Dimension(150, 80));	
		eastPanel.add(solutionsLabel);
		eastPanel.add(solutionsScrollPane);	
		eastPanel.add(motsTrouvesLabel);
		eastPanel.add(motsTrouvesScrollPane);
		
		frame.getContentPane().add(eastPanel, BorderLayout.EAST);
		
		//Center Panel
		JPanel centerPanel = new JPanel();
		centerPanel.setLayout(new BoxLayout(centerPanel, BoxLayout.Y_AXIS));
		
		JPanel panel_1 = new JPanel(); 
		panel_1.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));
		timerProgressBar = new JProgressBar();
		timerProgressBar.setForeground(Color.white);
		timerProgressBar.setPreferredSize(new Dimension(260, 20));
		panel_1.add(timerProgressBar);	
		timerLabel = new JLabel("New label"); 
		panel_1.add(timerLabel);
		
		JPanel panel_2 = new JPanel();
		panel_2.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));
		bonusButton = new JButton("J'ai de la chance !"); 
		panel_2.add(bonusButton);
		
		gridPanel = new JPanel(); 
		GridLayout tirageEtChoix = new GridLayout(2, Param.LONGUEUR_TIRAGE, 0, 0);
		tirageEtChoix.setHgap(10);
		tirageEtChoix.setVgap(10);
		gridPanel.setLayout(tirageEtChoix);
		
		JPanel panel_3 = new JPanel();
		panel_3.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));
		commencerButton = new JButton("Commencer");
		panel_3.add(commencerButton);
		voyelleButton = new JButton("Voyelle"); 
		voyelleButton.setMnemonic(KeyEvent.VK_V);
		panel_3.add(voyelleButton);
		consonneButton = new JButton("Consonne");
		consonneButton.setMnemonic(KeyEvent.VK_C);
		panel_3.add(consonneButton);
		annulerButton = new JButton("Annuler");
		panel_3.add(annulerButton);
		proposerButton = new JButton("Proposer"); 
		panel_3.add(proposerButton);
		continuerButton = new JButton("Continuer"); 
		panel_3.add(continuerButton);		
		
		JPanel panel_4 = new JPanel(); 
		panel_4.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));
		panel_4.setPreferredSize(new Dimension(400, 100));
		choixLabel = new JLabel(""); 
		choixLabel.setFont(fonteMotScore);
		panel_4.add(choixLabel);
				
		centerPanel.add(panel_1);
		centerPanel.add(panel_2);
		centerPanel.add(gridPanel);
		centerPanel.add(panel_3);
		centerPanel.add(panel_4);
		
		frame.getContentPane().add(centerPanel, BorderLayout.CENTER);
		
		//South Panel
		JPanel southPanel = new JPanel();
		southPanel.setPreferredSize(new Dimension(frame.getWidth(), 60));
		southPanel.setBackground(Color.black);
		frame.getContentPane().add(southPanel, BorderLayout.SOUTH);
		
		mntmNouveauJeu.addActionListener(this);
		mntmAfficherLesScores.addActionListener(this);		
		commencerButton.addActionListener(this);
		annulerButton.addActionListener(this);
		proposerButton.addActionListener(this);
		continuerButton.addActionListener(this);
		voyelleButton.addActionListener(this);
		consonneButton.addActionListener(this);
		bonusButton.addActionListener(this);

		for (int i=0; i<Param.LONGUEUR_TIRAGE; i++) {
			tirageTab[i] = new JButton();
			tirageTab[i].setFont(fonteTirage);
			choixTab[i] = new JLabel();
			choixTab[i].setFont(fonteChoix);
			choixTab[i].setHorizontalAlignment(JLabel.CENTER);
			choixTab[i].setOpaque(true);
			choixTab[i].setBackground(Color.gray);
		}
		
		for (JButton b : tirageTab) {
			gridPanel.add(b);
			b.addActionListener(this);		
		}
		
		for (JLabel l : choixTab) {
			gridPanel.add(l);
		}

		this.frame.setVisible(true);
	}
	
	
	public void activerLettresTirage() {
		for (int i=0; i<tirageTab.length; i++) setEtatLettreTirage(i, true);
	}
	
	public void actualiserTimer(int valeur) {
		timerProgressBar.setValue(valeur);
	}
	
	public void actualiserTimerLabel(String temps) {
		this.timerLabel.setText(temps);
	}
	
	public void afficherChoix(String mot, int score) {
		this.choixLabel.setText(mot+" : "+score+" pts");
	}
	
	public void afficherIndexBonus() {
		for (Integer i : jeu.getIndexBonus()) {
			choixTab[i].setBackground(Color.yellow);
		}
	}
	
	public void afficherListeMotsTrouves() {
		this.motsTrouvesList.setListData(jeu.getMotsTrouves().toArray(new String[jeu.getMotsTrouves().size()]));
	}
	
	public void afficherListeScores() {
		Iterator<Score> iter = jeu.getListeScores().iterator();
		SimpleDateFormat sdf = new SimpleDateFormat("dd MMM yyyy");
		JLabel messageLabel = new JLabel("", JLabel.CENTER);
		int place = 1;
		
		String message = "<html><table>";
		while (iter.hasNext()) {
			Score s = iter.next();
			message+="<tr>";
			message+="<td align=\"center\" style=\"padding-right:30\" >"+place+":"+"</td>"; 
			message+="<td align=\"center\" style=\"padding-right:20\" >"+s.getPseudo()+"</td>";
			message+="<td align=\"center\" style=\"padding-right:20\">"+s.getScore()+"</td>"; 
			message+="<td align=\"center\" >"+sdf.format(s.getDate())+"</td>";
			message+="</tr>";
			place++;
		}
		
		for (int j=0; j<Param.NB_SCORES-jeu.getListeScores().size(); j++) {
			message+="<tr>";
			message+="<td align=\"center\" style=\"padding-right:30\" >"+place+":"+"</td>";
			message+="<td align=\"center\" style=\"padding-right:20\">-</td>";
			message+="<td align=\"center\" style=\"padding-right:20\" >-</td>";
			message+="<td align=\"center\" >-</td>"; 
			message+="</tr>";
			place++;
		}
		
		message+="</table></html>";
		messageLabel.setText(message);
		messageLabel.setFont(fonteListeScores);
		JOptionPane.showMessageDialog(new JPanel(), messageLabel, "HighScores", JOptionPane.PLAIN_MESSAGE,
				new ImageIcon("images\\iconeScores.png"));
	}
	
	public void afficherListeSolutions() {
		this.solutionsLabel.setText("Solutions ("+jeu.getSolutions().size()+")");
		this.solutionsList.setListData(jeu.getSolutions().toArray(new String[jeu.getSolutions().size()]));
	}
	
	public void afficherPartie() {
		this.numPartieLabel.setText("Partie "+jeu.getNbPartie()+ " / "+Param.NB_MAX_PARTIES);
	}
	
	public void afficherPseudo() {
		this.pseudoLabel.setText(jeu.getPseudo());
	}
	
	public void afficherScore() {
		this.scoreLabel.setText(String.format("%02d", jeu.getScore()));
	}
	
	public void effacerChoix() {
		this.choixLabel.setText("");
	}
	
	public void reinitialiserTimer() {
		timerProgressBar.setMaximum(Param.DELAI);
		timerProgressBar.setMinimum(0);
		timerProgressBar.setValue(0);
		setEtatTimer(0);
		timerLabel.setText(String.format("%d:%02d", Param.DELAI/60, Param.DELAI%60));
	}
	
	public void reinitialiserTirage() {
		for (JButton jb : tirageTab) jb.setText("?");	
		for (JLabel jl : choixTab) { jl.setText("-"); jl.setBackground(Color.gray); }
		activerLettresTirage();
		setChoixLettresOff();
	}
	
	public void setChoixLettresOff() {
		for (int i=0; i<tirageTab.length; i++)
		this.tirageTab[i].removeActionListener(this);
	}
	
	public void setChoixLettresOn() {
		for (int i=0; i<tirageTab.length; i++)
		this.tirageTab[i].addActionListener(this);
	}
	
	public void etatBonusCommande(boolean bBonus) {
		bonusButton.setEnabled(bBonus);
	}
	
	public void etatCommandes(boolean bCommencer, boolean bVoyelle, boolean bConsonne,
			boolean bAnnuler, boolean bProposer, boolean bContinuer) {
		commencerButton.setEnabled(bCommencer);
		voyelleButton.setEnabled(bVoyelle);
		consonneButton.setEnabled(bConsonne);
		annulerButton.setEnabled(bAnnuler);
		proposerButton.setEnabled(bProposer);
		continuerButton.setEnabled(bContinuer);
	}
	
	public void setEtatTimer(int etat) {
		switch (etat) {
			case 0: 
				timerProgressBar.setBackground(Color.green);
				break;
			case 1:
				timerProgressBar.setBackground(Color.yellow);
				break;
			case 2:
				timerProgressBar.setBackground(Color.red);
				break;
			default: 
				timerProgressBar.setBackground(Color.green);
				break;
		}

	}
	
	public void setEtatLettreTirage(int index, boolean state) {
		this.tirageTab[index].setEnabled(state);		
	}
	
	public void setListeChoix(ArrayList<String> lettres) {
		for (int i=0; i<lettres.size(); i++)
		this.choixTab[i].setText(lettres.get(i));
	}
	
	public void setListeTirage(ArrayList<String> lettres) {
		for (int i=0; i<lettres.size(); i++) 
		this.tirageTab[i].setText(lettres.get(i));
	}
	
	public void viderListeMotsTrouves() {
		String[] listeVide = new String[0];
		this.motsTrouvesList.setListData(listeVide);
	}
	
	public void viderListeSolutions() {
		String[] listeVide = new String[0];
		this.solutionsList.setListData(listeVide);
		this.solutionsLabel.setText("Solutions");
	}
	
	public void reinitialiserLettresChoix() {
		for (int i=0; i<choixTab.length; i++) {
			choixTab[i].setText("-");
		}
	}
	
	public void showHighScores() {

	}
	
	public void actionPerformed(ActionEvent e){
		
		try {
			if (e.getSource() == this.commencerButton) {
				String pseudo = "";
				do {
					pseudo = (String)JOptionPane.showInputDialog(new JPanel(), 
							"Entrez votre pseudo (" + Param.LONGUEUR_PSEUDO_MIN + " � " + Param.LONGUEUR_PSEUDO_MAX + 
							" lettres)", "Choix du pseudo", 0, 
							new ImageIcon("images\\iconeGame.png"), null, null);
					if (pseudo != null) {
						if (pseudo.length() < Param.LONGUEUR_PSEUDO_MIN || pseudo.length() > Param.LONGUEUR_PSEUDO_MAX) {
							 JOptionPane.showMessageDialog(new JPanel(), "Pseudo invalide !", "Erreur", JOptionPane.ERROR_MESSAGE);
						}
					} else {
						pseudo = "";
					} 
				} while ((pseudo.length() < Param.LONGUEUR_PSEUDO_MIN || pseudo.length() > Param.LONGUEUR_PSEUDO_MAX) && pseudo != "") ;
				
	
				if (pseudo != "") {
					jeu.setPseudo(pseudo);
					jeu.ModePreparation();
				}
			}
			
			if (e.getSource() == annulerButton) {
				jeu.evenement("annuler");
			}
			
			if (e.getSource() == consonneButton) {
				jeu.evenement("consonne");
			}
			
			if (e.getSource() == voyelleButton) {
				jeu.evenement("voyelle");
			}
			
			if (e.getSource() == proposerButton) {
				jeu.evenement("proposer");
			}
			
			if (e.getSource() == mntmNouveauJeu) {
				jeu.evenement("nouveauJeu");
			}
			
			if (e.getSource() == mntmAfficherLesScores) {
				jeu.evenement("afficherListeScores");
			}
			
			if (e.getSource() == continuerButton) {
				jeu.evenement("continuer");
			}
			
			if (e.getSource() == bonusButton) {
				jeu.evenement("bonus");
			}
			
			for (int i=0; i<Param.LONGUEUR_TIRAGE; i++) {
				if (e.getSource() == tirageTab[i]) {
					jeu.evenementLettres(i, ((JButton)e.getSource()).getText());
				}
			}
		} catch (JeuException e1) {
			afficherErreur(e1.getMessage());
		}
	}
}
