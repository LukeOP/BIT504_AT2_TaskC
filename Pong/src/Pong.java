import javax.swing.JFrame;

public class Pong extends JFrame {
	private final static int WINDOW_WIDTH = 600;
	private final static int WINDOW_HEIGHT = 800;
	private final static String WINDOW_TITLE = "Pong";
	
	
	public Pong() {
    	setTitle(WINDOW_TITLE);
    	setSize(WINDOW_HEIGHT, WINDOW_WIDTH);
    	setResizable(false);
    	add(new PongPanel());
    	setVisible(true);
    	setDefaultCloseOperation(EXIT_ON_CLOSE);
}

	 public static void main(String[] args) {      
         javax.swing.SwingUtilities.invokeLater(new Runnable() {
           public void run() {
                  new Pong();
           }
         });
     }
}

