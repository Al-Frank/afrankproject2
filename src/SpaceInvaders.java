// utility
import java.util.ArrayList;
import java.util.Random;

// graphics
import java.awt.Color;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.Graphics;

// events
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

// swing
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.Timer;

public class SpaceInvaders extends JPanel implements ActionListener, KeyListener, Runnable {

    private final int canvasWidth;
    private final int canvasHeight;
    private final Color backgroundColor;


    private final int framesPerSecond = 25;
    private final int msPerFrame = 1000 / framesPerSecond;
    private Timer timer;
    private int frame = 0;

    private Player steveBuscemi;
    private ArrayList<Aliens> aliensList;
    private Aliens Bill;

    private ArrayList<enemyFire> enemyFireList;
    private ArrayList<playerFire> playerFireList;


    // FIXME list your game objects here

    /* Constructor for a Space Invaders game
     */
    public SpaceInvaders() {
        // fix the window size and background color
        this.canvasWidth = 600;
        this.canvasHeight = 400;
        this.backgroundColor = Color.BLACK;

        setPreferredSize(new Dimension(this.canvasWidth, this.canvasHeight));

        // set the drawing timer
        this.timer = new Timer(msPerFrame, this);

        // FIXME initialize your game objects
        this.steveBuscemi = new Player(200, 300, 40, 40);


        aliensList = new ArrayList<Aliens>();
        playerFireList = new ArrayList<playerFire>();

        int displacement = 60;
        for (int column = 0; column < 5; column++) {
            for (int row = 0; row < 3; row++) {
                this.aliensList.add(new Aliens(100 + (column*displacement), 10 + (row*displacement)));

            }
        }
        this.enemyFireList = new ArrayList<enemyFire>();
        this.playerFireList = new ArrayList<playerFire>();


    }



    /* Start the game
     */
    @Override
    public void run() {
        // show this window
        display();

        // set a timer to redraw the screen regularly
        this.timer.start();
    }

    /* Create the window and display it
     */
    private void display() {
        JFrame jframe = new JFrame();
        jframe.addKeyListener(this);
        jframe.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        jframe.setContentPane(this);
        jframe.pack();
        jframe.setVisible(true);
    }

    /* Run all timer-based events
     *
     * @param e  An object describing the timer
     */
    @Override
    public void actionPerformed(ActionEvent e) {
        // update the game objects
        update();
        // draw every object (this calls paintComponent)
        repaint(0, 0, this.canvasWidth, this.canvasHeight);
        // increment the frame counter
        this.frame++;
    }

    /* Paint/Draw the canvas.
     *
     * This function overrides the paint function in JPanel. This function is
     * automatically called when the panel is made visible.
     *
     * @param g The Graphics for the JPanel
     */
    @Override
    protected void paintComponent(Graphics g) {
        // clear the canvas before painting
        clearCanvas(g);
        if (hasWonGame()) {
            paintWinScreen(g);
        } else if (hasLostGame()) {
            paintLoseScreen(g);
        } else {
            paintGameScreen(g);
        }
    }

    /* Clear the canvas
     *
     * @param g The Graphics representing the canvas
     */
    private void clearCanvas(Graphics g) {
        Color oldColor = g.getColor();
        g.setColor(this.backgroundColor);
        g.fillRect(0, 0, this.canvasWidth, this.canvasHeight);
        g.setColor(oldColor);
    }

    /* Respond to key release events
     *
     * A key release is when you let go of a key
     *
     * @param e  An object describing what key was released
     */
    public void keyReleased(KeyEvent e) {
        // you can leave this function empty
    }

    /* Respond to key type events
     *
     * A key type is when you press then let go of a key
     *
     * @param e  An object describing what key was typed
     */
    public void keyTyped(KeyEvent e) {
        // you can leave this function empty
    }

    /* Respond to key press events
     *
     * A key type is when you press then let go of a key
     *
     * @param e  An object describing what key was typed
     */
    public void keyPressed(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_LEFT) {
           this.steveBuscemi.x -= 10;
            // FIXME what happens when left arrow is pressed
        } else if (e.getKeyCode() == KeyEvent.VK_RIGHT) {
            this.steveBuscemi.x += 10;
            // FIXME what happens when right arrow is pressed
        } else if (e.getKeyCode() == KeyEvent.VK_SPACE) {
            //add projectiles to the list
            playerFireList.add(new playerFire(steveBuscemi.x + 15, steveBuscemi.y, 10, 15, Color.CYAN));
            // FIXME what happens when space bar is pressed
        }
    }

    /* Update the game objects
     */
    private void update() {

        if (hasLostGame() || hasWonGame()){
            return;

        }


        this.steveBuscemi.update(canvasWidth, canvasHeight, this.frame);

        for (int l = 0; l < playerFireList.size(); l++){
            for (int m = 0; m < aliensList.size(); m++){
                if (this.playerFireList.get(l).yy >= this.aliensList.get(m).y
                        && this.playerFireList.get(l).yy <= this.aliensList.get(m).y + this.aliensList.get(m).size){
                    if (this.playerFireList.get(l).xx >= this.aliensList.get(m).x - this.playerFireList.get(l).width
                            && this.playerFireList.get(l).xx <= this.aliensList.get(m).x + this.aliensList.get(m).size + this.playerFireList.get(l).width){

                        aliensList.remove(this.aliensList.get(m));
                        this.playerFireList.get(l).xx = 10000;

                    }
                }
            }
        }

        //generate random nums for which alien shoots and when it shoots
        Random alienNum = new Random();
        Random shootTime = new Random();

        if (frame % ( 20 + shootTime.nextInt(27)) == 0){
            int alienSpec = alienNum.nextInt(aliensList.size());

            this.enemyFireList.add(new enemyFire(aliensList.get(alienSpec).x + 15, aliensList.get(alienSpec).y + aliensList.get(alienSpec).size));
        }
        for (int h = 0; h < enemyFireList.size(); h++){
            this.enemyFireList.get(h).update(canvasWidth, canvasHeight, this.frame);
        }

        for (int i = 0; i < aliensList.size(); i++){
            this.aliensList.get(i).update(canvasWidth, canvasHeight, this.frame);
        }

        for (int n = 0; n < playerFireList.size(); n++){
            this.playerFireList.get(n).update(canvasWidth, canvasHeight, this.frame);
        }
        for (int t = 0; t < enemyFireList.size(); t++){
            this.enemyFireList.get(t).update(canvasWidth, canvasHeight, this.frame);
        }


        // FIXME update game objects here
    }

    /* Check if the player has lost the game
     *
     * @returns  true if the player has lost, false otherwise
     */
    private boolean hasLostGame() {
        for (int f = 0; f < enemyFireList.size(); f++){
            if (enemyFireList.get(f).yy + enemyFireList.get(f).height >= this.steveBuscemi.y
                    && enemyFireList.get(f).yy <= this.steveBuscemi.y + this.steveBuscemi.height){
                if (enemyFireList.get(f).xx >= this.steveBuscemi.x - enemyFireList.get(f).width
                        && enemyFireList.get(f).xx <= this.steveBuscemi.x + this.steveBuscemi.width){
                    return true;

                }
            }


        }
        return false;
    }

    /* Check if the player has won the game
     *
     * @returns  true if the player has won, false otherwise
     */
    private boolean hasWonGame() {
        if (frame > 5 && aliensList.size() < 1){
            return true;
        }
        return false; // FIXME delete this when ready
    }

    /* Paint the screen during normal gameplay
     *
     * @param g The Graphics for the JPanel
     */
    private void paintGameScreen(Graphics g) {
        this.steveBuscemi.draw(g);

        for (int i = 0; i < aliensList.size(); i++){
            this.aliensList.get(i).draw(g);
        }
        for (int n = 0; n < playerFireList.size(); n++){
            this.playerFireList.get(n).draw(g);
        }

        for (int t = 0; t < enemyFireList.size(); t++){
            this.enemyFireList.get(t).draw(g);
        }
        // FIXME draw game objects here
        //for projectiles in list, draw each projectile
    }

    /* Paint the screen when the player has won
     *
     * @param g The Graphics for the JPanel
     */
    private void paintWinScreen(Graphics g) {
        // FIXME draw the win screen here
    }

    /* Paint the screen when the player has lost
     *
     * @param g The Graphics for the JPanel
     */
    private void paintLoseScreen(Graphics g) {
        if (hasLostGame()){


        }
        // FIXME draw the game over screen here
    }

    public static void main(String[] args) {
        SpaceInvaders invaders = new SpaceInvaders();
        EventQueue.invokeLater(invaders);
    }
}
