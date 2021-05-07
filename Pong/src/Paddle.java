import java.awt.Color;

public class Paddle extends Sprite {

    private static final int PADDLE_WIDTH = 10;
    private static final int PADDLE_HEIGHT = 100;
    private static final Color PADDLE_COLOUR = Color.WHITE;
    private static final int DISTANCE_FROM_EDGE = 40;

    public Paddle(Player player, int panelWidth, int panelHeight) {
       setWidth(PADDLE_WIDTH);
       setHeight(PADDLE_HEIGHT);
       setColour(PADDLE_COLOUR);
       int xPos = 0;
       
       if (player == Player.One) {
           xPos = DISTANCE_FROM_EDGE;
           setInitialPosition(xPos, PADDLE_HEIGHT + (PADDLE_HEIGHT / 2));
       }
       if (player == Player.Two) {
           xPos = panelWidth - DISTANCE_FROM_EDGE - getWidth();
           setInitialPosition(xPos, panelHeight - (PADDLE_HEIGHT + (PADDLE_HEIGHT / 2)));
       }
       //setInitialPosition(xPos, panelHeight / 2 - (getHeight() / 2));
       //setInitialPosition(xPos, panelHeight - (PADDLE_HEIGHT + (PADDLE_HEIGHT / 2)));
       resetToInitialPosition();
       
}
}
