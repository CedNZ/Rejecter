import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.io.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.Scanner;


@SuppressWarnings("serial")
public class RejectorPanel extends JPanel implements KeyListener, ActionListener {
	
	int width = Rejector.width;
	int height = Rejector.height;
	
	private int count, speed, bottleCount, levelCount, score, lives, namePos, selectPosNum, creditPage;
	private ArrayList<Milk> bottles;
	private Timer t, credTimer;
	private Rectangle xray = new Rectangle(350, 180, 160, 135);
	private Rectangle bin = new Rectangle(120, 190, 100, 105);
	/*private Rectangle play = new Rectangle(313, 108, 86, 69);
	private Rectangle high = new Rectangle(309, 185, 91, 71);
	private Rectangle cred = new Rectangle(307, 258, 91, 53);
	private Rectangle quit = new Rectangle(309, 313, 83, 40);*/
	private Rectangle select;
	private int[] selectPos = {110, 190, 255, 305};
	private boolean title, showMenu, showGameOver, showBin, showScores, credits, paused;
	private BufferedImage bgImage, title_screen, menu, game_over, life, rubbish, credits0, credits1, credits2, credits3, credits4, credits5, pause, scoreBG;
	public static BufferedImage bottle, poisonBottle, milkBottle, bottle2, poisonBottle2, milkBottle2, bottle3, poisonBottle3, milkBottle3,
		bottle4, poisonBottle4, milkBottle4, bottle5, poisonBottle5, milkBottle5, bottle6, poisonBottle6, milkBottle6,
		bottle7, poisonBottle7, milkBottle7, bottle8, poisonBottle8, milkBottle8, bottle9, poisonBottle9, milkBottle9;
	private Font scoreFont = new Font(Font.SANS_SERIF, Font.PLAIN, 20);
	private Font hscoreFont = new Font(Font.MONOSPACED, Font.BOLD, 30);
	private char[] name = {'N', 'a', 'm', 'e', ':'};
	private ArrayList<Score> scores;
	private FileReader fr;
	private FileWriter fw;
	private PrintWriter pw;
	private Scanner s;
	private String scoreFile = "scores";
	
	@SuppressWarnings("unchecked")
	public RejectorPanel() {
		setBackground(Color.cyan);
		addKeyListener(this);
		t = new Timer(50, this);
		credTimer = new Timer(30000, this);
		namePos = 0;
		title = true;
		init();
		
		try {
			bgImage = ImageIO.read(getClass().getResource("Background.jpg"));
			title_screen = ImageIO.read(getClass().getResource("Title.jpg"));
			game_over = ImageIO.read(getClass().getResource("Game_Over.jpg"));
			menu = ImageIO.read(getClass().getResource("Menu.jpg"));
			credits0 = ImageIO.read(getClass().getResource("Credits0.jpg"));
			credits1 = ImageIO.read(getClass().getResource("Credits1.jpg"));
			credits2 = ImageIO.read(getClass().getResource("Credits2.jpg"));
			credits3 = ImageIO.read(getClass().getResource("Credits3.jpg"));
			credits4 = ImageIO.read(getClass().getResource("Credits4.jpg"));
			credits5 = ImageIO.read(getClass().getResource("Credits5.jpg"));
			life = ImageIO.read(getClass().getResource("LifeBottle.png"));
			rubbish = ImageIO.read(getClass().getResource("Bin.jpg"));
			pause = ImageIO.read(getClass().getResource("Pause.png"));
			scoreBG = ImageIO.read(getClass().getResource("Score.png"));
			//bottle img 1
			bottle = ImageIO.read(getClass().getResource("Bottle.png"));
			milkBottle = ImageIO.read(getClass().getResource("MilkBottle.png"));
			poisonBottle = ImageIO.read(getClass().getResource("PoisonBottle.png"));
			//bottle img 2
			bottle2 = ImageIO.read(getClass().getResource("Bottle2.png"));
			milkBottle2 = ImageIO.read(getClass().getResource("MilkBottle2.png"));
			poisonBottle2 = ImageIO.read(getClass().getResource("PoisonBottle2.png"));
			//bottle img 3
			bottle3 = ImageIO.read(getClass().getResource("Bottle3.png"));
			milkBottle3 = ImageIO.read(getClass().getResource("MilkBottle3.png"));
			poisonBottle3 = ImageIO.read(getClass().getResource("PoisonBottle3.png"));
			//bottle img 4
			bottle4 = ImageIO.read(getClass().getResource("Bottle4.png"));
			milkBottle4 = ImageIO.read(getClass().getResource("MilkBottle4.png"));
			poisonBottle4 = ImageIO.read(getClass().getResource("PoisonBottle4.png"));
			//bottle img 5
			bottle5 = ImageIO.read(getClass().getResource("Bottle5.png"));
			milkBottle5 = ImageIO.read(getClass().getResource("MilkBottle5.png"));
			poisonBottle5 = ImageIO.read(getClass().getResource("PoisonBottle5.png"));
			//bottle img 6
			bottle6 = ImageIO.read(getClass().getResource("Bottle6.png"));
			milkBottle6 = ImageIO.read(getClass().getResource("MilkBottle6.png"));
			poisonBottle6 = ImageIO.read(getClass().getResource("PoisonBottle6.png"));
			//bottle img 7
			bottle7 = ImageIO.read(getClass().getResource("Bottle7.png"));
			milkBottle7 = ImageIO.read(getClass().getResource("MilkBottle7.png"));
			poisonBottle7 = ImageIO.read(getClass().getResource("PoisonBottle7.png"));
			//bottle img 8
			bottle8 = ImageIO.read(getClass().getResource("Bottle8.png"));
			milkBottle8 = ImageIO.read(getClass().getResource("MilkBottle8.png"));
			poisonBottle8 = ImageIO.read(getClass().getResource("PoisonBottle8.png"));
			//bottle img 9
			bottle9 = ImageIO.read(getClass().getResource("Bottle9.png"));
			milkBottle9 = ImageIO.read(getClass().getResource("MilkBottle9.png"));
			poisonBottle9 = ImageIO.read(getClass().getResource("PoisonBottle9.png"));
			fr = new FileReader(scoreFile);
		} catch (FileNotFoundException e) {
			try {
				pw = new PrintWriter(scoreFile);
				fr = new FileReader(scoreFile);
			} catch (FileNotFoundException e1) {
				e1.printStackTrace();
			}
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			scores = new ArrayList<Score>();
			s = new Scanner(fr);
			
			while (s.hasNext()) {
				try {
					scores.add(new Score(s.next(), s.nextInt()));
				} catch (Exception scannerEx) {
					System.out.println("Detected modified score\nIgnoring");
					s.nextLine();
				}
			}
			s.close();
			Collections.sort(scores);
			//Collections.reverse(scores);
		}
	}
	
	private void init() {
		bottles = new ArrayList<Milk>();
		count = 0;
		bottleCount = 0;
		levelCount = 10;
		score = 0;
		speed = 3;
		lives = 3;
		//title = true;
		showMenu = true;
		showGameOver = false;
		showScores = false;
		credits = false;
		showBin = false;
		paused = false;
		selectPosNum = 0;
		select = new Rectangle(305, selectPos[selectPosNum], 100, 60);
	}
	
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		if (title) {
			g.drawImage(title_screen, 0, 0, null);
			return;
		}
		if (showMenu) {
			g.drawImage(menu, 0, 0, null);
			g.setFont(scoreFont);
			g.drawString(new String(name), 280, 463);
			Graphics2D g2 = (Graphics2D) g;
			Stroke oldStroke = g2.getStroke();
			g2.setColor(Color.red);
			g2.setStroke(new BasicStroke(5.0f));
			g2.drawOval(select.x, select.y, select.width, select.height);
			g2.setStroke(oldStroke);
			return;
		}
		if (showScores) {
			/*g.setColor(Color.black);
			g.fillRect(0, 0, 750, 500);*/
			g.drawImage(scoreBG, 0, 0, null);
			g.setFont(hscoreFont);
			g.setColor(Color.red);
			int yScore = 100;
			for (Score hs : scores) {
				g.setColor(Color.black);
				g.drawString(hs.getName(), 239, yScore-1);
				g.drawString(hs.getScoreString(), 369, yScore-1);
				g.setColor(Color.red);
				g.drawString(hs.getName(), 240, yScore);
				g.drawString(hs.getScoreString(), 370, yScore);
				yScore += hscoreFont.getSize();
			}
			return;
		}
		if (credits) {
			switch (creditPage) { 
			case 0:
				g.drawImage(credits0, 0, 0, null);
				break;
			case 1:
				g.drawImage(credits1, 0, 0, null);
				break;
			case 2:
				g.drawImage(credits2, 0, 0, null);
				break;
			case 3:
				g.drawImage(credits3, 0, 0, null);
				break;
			case 4:
				g.drawImage(credits4, 0, 0, null);
				break;
			case 5:
				g.drawImage(credits5, 0, 0, null);
				break;
			}
			return;
		}
		if (showGameOver) {
			g.drawImage(game_over, 0, 0, null);
			g.setFont(scoreFont);
			g.drawString(new String(String.format("%15d", score)), 360, 300);
			g.drawString(new String(name), 365, 393);
			return;
		}
		g.drawImage(bgImage, 0, 0, null);
		if (showBin) {
			g.setColor(Color.orange);
			g.fillRect(bin.x, bin.y, bin.width, bin.height);
		}
		g.setColor(Color.black);
		for (Milk m : bottles) {
			m.drawBottle(g);
		}
		g.fillRect(xray.x, xray.y, xray.width, xray.height);
		for (Milk m : bottles) {
			if (xray.intersects(m.getArea())) m.draw(g);
		}
		g.drawImage(rubbish, bin.x - 17, bin.y + 214, null);
		if (paused) g.drawImage(pause, 667, 9, null);
		g.setFont(scoreFont);
		g.setColor(Color.red);
		g.drawString("Wave: " + (speed - 2), 530, 30);
		g.drawString("" + score, 350, 30);
		for (int i = 0; i < lives; i++)	
			g.drawImage(life, 20 + (i * 50), 20, null);
		Graphics2D g2 = (Graphics2D) g;
		Stroke oldStroke = g2.getStroke();
		g2.setColor(Color.gray);
		g2.setStroke(new BasicStroke(10.0f));
		g2.drawRect(xray.x, xray.y, xray.width + 5, xray.height + 5);
		g2.setStroke(oldStroke);
	}
	
	public void keyPressed(KeyEvent arg0) {
		if (title) {
			title = false;
			showMenu = true;
			repaint();
			return;
		} else if (showScores) {
			showMenu = true;
			showScores = false;
			repaint();
			return;
		} else if (credits) {
			creditPage++;
			credTimer.restart();
			if (creditPage > 5) {
				credTimer.stop();
				credits = false;
				showMenu = true;
			}
			repaint();
			return;
		} else if (showGameOver && arg0.getKeyCode() != KeyEvent.VK_SPACE) {
			init();
			repaint();
			return;
		}
		if (showMenu) {
			int temp = arg0.getKeyCode();
			if (temp == KeyEvent.VK_BACK_SPACE  && namePos > 0) {
				namePos--;
				name[namePos] = ' ';
			} else if (temp == KeyEvent.VK_ENTER) {
				if (selectPosNum == 0) {
					showMenu = false;
					t.start();
				}
				else if (selectPosNum == 1) {
					showMenu = false;
					showScores = true;
				}
				else if (selectPosNum == 2) {
					showMenu = false;
					creditPage = 0;
					credTimer.start();
					credits = true;
				}
				else if (selectPosNum == 3) System.exit(0);
			} else if (namePos < name.length) {
				char letter = arg0.getKeyChar();
				if (Character.isLetter(letter) || Character.isDigit(letter)) {
					if (name.length < 6) name = new char[10];
					name[namePos] = letter;
					namePos++;
				}
			}
			if (temp == KeyEvent.VK_UP) selectPosNum--;
			else if (temp == KeyEvent.VK_DOWN) selectPosNum++;
			if (selectPosNum < 0) selectPosNum = 3;
			else if (selectPosNum > 3) selectPosNum = 0;
			select.y = selectPos[selectPosNum];
			repaint();
			return;
		}
		int key = arg0.getKeyCode();
		switch (key) {
		case KeyEvent.VK_NUMPAD4:
			for (Milk m : bottles) 
				m.setSpeed(m.getSpeed() + 1);
			speed++;
			break;
		case KeyEvent.VK_NUMPAD6:
			for (Milk m : bottles) 
				m.setSpeed(m.getSpeed() - 1);
			speed--;
			break;
		case KeyEvent.VK_P:
			if (t.isRunning()) t.stop();
			else t.start();
			paused = !paused;
			repaint();
			break;
		case KeyEvent.VK_SPACE:
			drop();
			break;
		case KeyEvent.VK_H:
			showBin = !showBin;
			break;
		case KeyEvent.VK_L:
			lives++;
			break;
		}
	}
	
	public void drop() {
		for (Milk m : bottles) 
			if (bin.contains(m.getArea())) m.drop(true);
	}

	@SuppressWarnings("unchecked")
	public void actionPerformed(ActionEvent arg0) {
		if (arg0.getSource() == credTimer) {
			creditPage++;
			if (creditPage > 5) {
				credTimer.stop();
				credits = false;
				showMenu = true;
			}
			repaint();
			return;
		}
		if (lives < 0) {
			t.stop();
			scores.add(new Score(new String(name), score));
			Collections.sort(scores);
			while (scores.size() > 10) scores.remove(10);
			try {
				fw = new FileWriter(scoreFile);
				pw = new PrintWriter(fw);
				for (Score hs : scores) 
					pw.println(hs.toString());
			} catch (Exception e) {
				e.printStackTrace();
			} finally {
				pw.close();
			}
			showGameOver = true;
			repaint();
			return;
		}
		count++;
		if (count >= (int) (100 / speed) && bottleCount < levelCount) {
			bottles.add(new Milk(speed));
			count = 0;
			bottleCount++;
		}
		if (bottleCount >= levelCount && bottles.isEmpty()) {
			speed++;
			count = 0;
			bottleCount = 0;
		}
		for (Iterator<Milk> it = bottles.iterator(); it.hasNext(); ) {
			Milk bottle = it.next();
			bottle.move();
			if (bottle.getX() < -30) {
				if (bottle.poison()) {
					score -= speed * 5;
					lives--;
				} else score += speed * 10;
				it.remove();
			} else if (bottle.getY() > 350) {
				if (bottle.poison()) score += speed * 10;
				else {
					score -= speed * 5;
					lives--;
				}
				it.remove();
			}
		}
		repaint();
	}
	
	public void update(Graphics g) {
		paint(g);
	}
	
	public void keyReleased(KeyEvent arg0) {}
	public void keyTyped(KeyEvent arg0) {}
}

class Score implements Comparable{
	
	private int score;
	private String name;
	
	public Score(String name, int score) {
		this.name = name;
		this.score = score;
	}
	
	public int getScore() {
		return score;
	}
	
	public String getScoreString() {
		return new String(String.format("%10d", score));
	}
	
	public String getName() {
		return name;
	}
	
	public String toString() {
		return new String(String.format("%-10s%10d", name, score));
	}
	
	@Override
	public int compareTo(Object arg0) {
		Score s = (Score) arg0;
		return s.score - this.score;
	}
	
}