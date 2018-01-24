import java.awt.*;
import javax.swing.*;


public class Rejector {
	
	public static final int width = 750;
	public static final int height = 500;
	
	@SuppressWarnings("unused")
	public static void main(String[] args) {
		JFrame gui = new RejectorJFrame("Rejecter", 0, 0, width, height);
	}
}

@SuppressWarnings("serial")
class RejectorJFrame extends JFrame {
	public RejectorJFrame(String title, int x, int y, int width, int height) {
		setTitle(title);
		setLocation(x, y);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		JPanel frameContent = new RejectorPanel();
		Container visibleArea = getContentPane();
		visibleArea.add(frameContent);
		frameContent.setPreferredSize(new Dimension(width, height));
		pack();
		frameContent.requestFocusInWindow();
		setVisible(true);
	}
}