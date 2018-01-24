import java.awt.*;
import java.awt.image.BufferedImage;

public class Milk {
	
	private int x, y, speed, bottleType;
	private int width = 60, height = 96;
	private double poisoned;
	private boolean poison, drop;
	private double poisonChance = 0.5;
	private BufferedImage[] bottleImgs = {RejectorPanel.bottle, RejectorPanel.bottle2, RejectorPanel.bottle3, RejectorPanel.bottle4, RejectorPanel.bottle5,
			RejectorPanel.bottle6, RejectorPanel.bottle7, RejectorPanel.bottle8, RejectorPanel.bottle9};
	private BufferedImage[] poisonImgs = {RejectorPanel.poisonBottle, RejectorPanel.poisonBottle2, RejectorPanel.poisonBottle3, RejectorPanel.poisonBottle4,
			RejectorPanel.poisonBottle5, RejectorPanel.poisonBottle6, RejectorPanel.poisonBottle7, RejectorPanel.poisonBottle8, RejectorPanel.poisonBottle9};
	private BufferedImage[] milkImgs = {RejectorPanel.milkBottle, RejectorPanel.milkBottle2, RejectorPanel.milkBottle3, RejectorPanel.milkBottle4,
			RejectorPanel.milkBottle5, RejectorPanel.milkBottle6, RejectorPanel.milkBottle7, RejectorPanel.milkBottle8, RejectorPanel.milkBottle9};
	
	public Milk(int speed) {
		bottleType = (int) (Math.random() * 9);
		poisoned = Math.random();
		if (poisoned > poisonChance) poison = false;
		else poison = true;
		x = 750;
		y = 195;
		drop = false;
		this.speed = speed;
	}
	
	public void setSpeed(int speed) {
		this.speed = speed;
	}
	
	public int getSpeed() {
		return speed;
	}
	
	public void move() {
		if (drop) y += speed + 5;
		else x -= speed;
	}
	
	public void drop(boolean drop) {
		this.drop = drop;
	}
	
	public int getX() {
		return x;
	}
	
	public int getY() {
		return y;
	}
	
	public boolean poison() {
		return poison;
	}
	
	public Rectangle getArea() {
		return new Rectangle(x, y, width, height);
	}
	
	public void draw(Graphics g) {
		if (poison)
			if (x > 510 - width){
				int stripWidth = 510 - x;
				g.drawImage(poisonImgs[bottleType], x, y, x + stripWidth, y + height, 0, 0, stripWidth, height, null);
			} else if (x < 350) {
				int stripWidth = (x + width) - 350;
				g.drawImage(poisonImgs[bottleType], (x + width) - stripWidth, y, x + width, y + height, width - stripWidth, 0, width, height, null);
			} else g.drawImage(poisonImgs[bottleType], x, y, null);
		else
			if (x > 510 - width){
				int stripWidth = 510 - x;
				g.drawImage(milkImgs[bottleType], x, y, x + stripWidth, y + height, 0, 0, stripWidth, height, null);
			} else if (x < 350) {
				int stripWidth = (x + width) - 350;
				g.drawImage(milkImgs[bottleType], (x + width) - stripWidth, y, x + width, y + height, width - stripWidth, 0, width, height, null);
			} else g.drawImage(milkImgs[bottleType], x, y, null);
	}
	
	public void drawBottle(Graphics g) {
		g.drawImage(bottleImgs[bottleType], x, y, null);
	}
	
}
