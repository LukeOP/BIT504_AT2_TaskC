import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import javax.swing.JPanel;
import javax.swing.Timer;
import java.awt.Graphics;
import java.awt.Graphics2D;

import java.awt.Stroke;

public class PongPanel extends JPanel implements ActionListener, KeyListener {
	
	private final static Color BACKGROUND_COLOUR = Color.BLACK;
	private final static int TIMER_DELAY = 5;
    private static final int BALL_MOVEMENT_SPEED = 2;
    private final static int FONT_XPADDING = 100;
    private final static int FONT_YPADDING = 100;
    private final static int FONT_SIZE = 50;
    private final static String FONT_FAMILY = "Serif";

    private final static int FONT_XPADDING_WIN = 100;
    private final static int FONT_YPADDING_WIN = 300;
    private final static int FONT_SIZE_WIN = 50;
    private final static String FONT_FAMILY_WIN = "Serif";
    private final static String WIN_TEXT = "YOU WIN!";
    
    private final static int POINTS_TO_WIN = 9;
    int player1Score = 0, player2Score = 0;
    Player gameWinner;
	
	GameState gameState = GameState.Initialising;
	Ball ball;
	Paddle paddle1;
	Paddle paddle2;
	
	public PongPanel() {
		setBackground(BACKGROUND_COLOUR);
	     Timer timer = new Timer(TIMER_DELAY, this);
	         timer.start();
	         addKeyListener(this);
	         setFocusable(true);
	}

	private void createObjects() {
		 ball = new Ball(getWidth(), getHeight());
		 paddle1 = new Paddle(Player.One, getWidth(), getHeight());
		 paddle2 = new Paddle(Player.Two, getWidth(), getHeight());
	}
	

	 
	private void update() {
		switch(gameState) {
			case Initialising:{
				createObjects();
				gameState = GameState.Playing;
				ball.setxVelocity(BALL_MOVEMENT_SPEED);
				ball.setyVelocity(BALL_MOVEMENT_SPEED);
				break;
			}
			case Playing:{
				moveObject(paddle1);
				moveObject(paddle2);
				moveObject(ball);
				checkWallBounce();
				checkPaddleBounce();
				checkWin();
				break;
			}
			case GameOver: {
				
			}
		}
	}
	
	private void addScore(Player player) {
		if (player == Player.One) {
			player1Score += 1;
		}
		if (player == Player.Two) {
			player2Score += 1;
		}
	}
	
	private void checkWin() {
		if (player1Score == POINTS_TO_WIN) {
			gameWinner = Player.One;
			gameState = GameState.GameOver;
		}
		if (player2Score == POINTS_TO_WIN) {
			gameWinner = Player.Two;
			gameState = GameState.GameOver;
		}
	}
	
	private void paintSprite(Graphics g, Sprite sprite) {
	      g.setColor(sprite.getColour());
	      g.fillRect(sprite.getxPosition(), sprite.getyPosition(), sprite.getWidth(), sprite.getHeight());
	}
	
	private void moveObject(Sprite sprite) {
	    sprite.setXPosition(sprite.getxPosition() + sprite.getxVelocity(), getWidth());
		sprite.setYPosition(sprite.getyPosition() + sprite.getyVelocity(), getWidth());
	}
	
	private void checkWallBounce() {
		if(ball.getxPosition() <= 0) {
			addScore(Player.Two);
			resetBall();
		}
		else if(ball.getxPosition() >= getWidth() - ball.getWidth()) {
			addScore(Player.One);
			resetBall();
		}
		if(ball.getyPosition() <= 0 || ball.getyPosition() >= getHeight() - ball.getHeight()) {
			ball.setyVelocity(ball.getyVelocity() * -1);
		}
	}
	 private void checkPaddleBounce() {
	      if(ball.getxVelocity() < 0 && ball.getRectangle().intersects(paddle1.getRectangle())) {
	          ball.setxVelocity(BALL_MOVEMENT_SPEED);
	      }
	      if(ball.getxVelocity() > 0 && ball.getRectangle().intersects(paddle2.getRectangle())) {
	          ball.setxVelocity(-BALL_MOVEMENT_SPEED);
	      }
	 }
	
	private void resetBall() {
		ball.resetToInitialPosition();
	}
	
	
	@Override
	public void keyTyped(KeyEvent event) {
		// TODO Auto-generated method stub
		
	}

	@Override
    public void keyPressed(KeyEvent event) {
        if(event.getKeyCode() == KeyEvent.VK_W) {
            paddle1.setyVelocity(-1);
       } else if(event.getKeyCode() == KeyEvent.VK_S) {
            paddle1.setyVelocity(1);
        }
        if(event.getKeyCode() == KeyEvent.VK_UP) {
            paddle2.setyVelocity(-1);
       } else if(event.getKeyCode() == KeyEvent.VK_DOWN) {
            paddle2.setyVelocity(1);
        }
    }

   @Override
   public void keyReleased(KeyEvent event) {
       if(event.getKeyCode() == KeyEvent.VK_W || event.getKeyCode() == KeyEvent.VK_S) {
           paddle1.setyVelocity(0);
       }
       if(event.getKeyCode() == KeyEvent.VK_UP || event.getKeyCode() == KeyEvent.VK_DOWN) {
           paddle2.setyVelocity(0);
       }
   }

	@Override
	public void actionPerformed(ActionEvent event) {
		update();
		repaint();
		
	}
	
	 private void paintScores(Graphics g) {
         Font scoreFont = new Font(FONT_FAMILY, Font.BOLD, FONT_SIZE);
         String leftScore = Integer.toString(player1Score);
         String rightScore = Integer.toString(player2Score);
         g.setFont(scoreFont);
         g.drawString(leftScore, FONT_XPADDING, FONT_YPADDING);
        g.drawString(rightScore, getWidth()-FONT_XPADDING, FONT_YPADDING);
    }
	 
	 private void paintWinner(Graphics g) {
	     Font winFont = new Font(FONT_FAMILY_WIN, Font.BOLD, FONT_SIZE_WIN);
	     g.setFont(winFont);
	     if(gameWinner == Player.One) {
	    	g.drawString(WIN_TEXT, FONT_XPADDING_WIN, FONT_YPADDING_WIN); 
	     }
	     else if(gameWinner == Player.Two){
	    	 g.drawString(WIN_TEXT, getWidth() -FONT_XPADDING_WIN-200, FONT_YPADDING_WIN);
	     }
	}

@Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        paintDottedLine(g);
        if(gameState != GameState.Initialising) {
            paintSprite(g, ball);
            paintSprite(g, paddle1);
            paintSprite(g, paddle2);
            paintScores(g);
			paintWinner(g);
        }
    }

	 private void paintDottedLine(Graphics g) {
	      Graphics2D g2d = (Graphics2D) g.create();
	         Stroke dashed = new BasicStroke(3, BasicStroke.CAP_BUTT, BasicStroke.JOIN_BEVEL, 0, new float[]{9}, 0);
	         g2d.setStroke(dashed);
	         g2d.setPaint(Color.WHITE);
	         g2d.drawLine(getWidth() / 2, 0, getWidth() / 2, getHeight());
	         g2d.dispose();
	 }
}
