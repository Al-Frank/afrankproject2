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

    private ArrayList<enemyFire> enemyFireList;
    private ArrayList<playerFire> playerFireList;

    //HashMap<KeyEvent, Boolean> keyMap;

    /* Constructor for a Space Invaders game
     */
    public SpaceInvaders() {
        // fix the window size and background color
        //keyMap = new HashMap<KeyEvent, Boolean>();

        this.canvasWidth = 600;
        this.canvasHeight = 400;
        this.backgroundColor = Color.BLACK;

        setPreferredSize(new Dimension(this.canvasWidth, this.canvasHeight));

        // set the drawing timer
        this.timer = new Timer(msPerFrame, this);

        this.steveBuscemi = new Player(200, 350, 30, 30);


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
      //  keyMap.put(e, false);
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

        if (e.getKeyCode() == KeyEvent.VK_SPACE) {
            //add projectiles to the list

            if (playerFireList.size() < 15) {
                playerFireList.add(new playerFire(steveBuscemi.x + 15, steveBuscemi.y, 10, 15, Color.CYAN));

            }
        }

        if (e.getKeyCode() == KeyEvent.VK_LEFT) {
            if (this.steveBuscemi.x > 9) {
                this.steveBuscemi.x -= 10;
            }

        } else if (e.getKeyCode() == KeyEvent.VK_RIGHT) {
            if (this.steveBuscemi.x + this.steveBuscemi.width < 591) {
                this.steveBuscemi.x += 10;
            }
        }

       // keyMap.put(e, true);


    }

    /*public boolean isKeyPressed(KeyEvent e){
        if (keyMap.containsKey(e)){
            return keyMap.get(e);
        }
        else{
            keyMap.put(e, false);
            return false;
        }
    }

    /* Update the game objects
     */

    private void update() {
     /*

        if (isKeyPressed(KeyEvent.VK_LEFT)){
            System.out.println("left is pressed");
        }
        */

        if (hasLostGame() || hasWonGame()) {
            return;
        }

        this.steveBuscemi.update(canvasWidth, canvasHeight, this.frame);

        for (int l = 0; l < playerFireList.size(); l++) {
            for (int m = 0; m < aliensList.size(); m++) {


                if (this.playerFireList.get(l).yy >= this.aliensList.get(m).y
                        && this.playerFireList.get(l).yy <= this.aliensList.get(m).y + this.aliensList.get(m).size) {
                    if (this.playerFireList.get(l).xx >= this.aliensList.get(m).x - this.playerFireList.get(l).width
                            && this.playerFireList.get(l).xx <= this.aliensList.get(m).x + this.aliensList.get(m).size + this.playerFireList.get(l).width) {

                        aliensList.remove(this.aliensList.get(m));
                        this.playerFireList.get(l).xx = 10000;
                    }
                }
            }
        }

        //remove player projectiles when they go offscreen
        for (int r = 0; r < playerFireList.size(); r++) {

            if (playerFireList.get(r).yy - playerFireList.get(r).height < 1) {
                playerFireList.remove(playerFireList.get(r));
            }

        }

        //if projectiles collide, send both away
        if (playerFireList.size() > 0 && enemyFireList.size() > 0 && aliensList.size() > 0) {
            for (int r = 0; r < playerFireList.size(); r++) {
                for (int s = 0; s < enemyFireList.size(); s++) {

                    if (this.playerFireList.get(r).yy >= this.enemyFireList.get(s).yy
                            && this.playerFireList.get(r).yy <= this.enemyFireList.get(s).yy + this.enemyFireList.get(s).height) {

                        if (this.playerFireList.get(r).xx >= this.enemyFireList.get(s).xx - this.playerFireList.get(r).width
                                && this.playerFireList.get(r).xx <= this.enemyFireList.get(s).xx + this.enemyFireList.get(s).width + this.playerFireList.get(r).width) {

                            enemyFireList.get(s).xx = 10000;
                            playerFireList.get(r).xx = 10000;


                        }
                    }
                }
            }
        }

            //generate random nums for which alien shoots and when it shoots
            Random alienNum = new Random();
            Random shootTime = new Random();
            //make aliens fire at random
            if (frame % (20 + shootTime.nextInt(27)) == 0) {
                int alienSpec = alienNum.nextInt(aliensList.size());

                this.enemyFireList.add(new enemyFire(aliensList.get(alienSpec).x + 15, aliensList.get(alienSpec).y + aliensList.get(alienSpec).size));
            }

            //update the stuff
            for (int h = 0; h < enemyFireList.size(); h++) {

                this.enemyFireList.get(h).update(canvasWidth, canvasHeight, this.frame);
            }

            for (int i = 0; i < aliensList.size(); i++) {

                this.aliensList.get(i).update(canvasWidth, canvasHeight, this.frame);
            }

            for (int n = 0; n < playerFireList.size(); n++) {

                this.playerFireList.get(n).update(canvasWidth, canvasHeight, this.frame);
            }
            for (int t = 0; t < enemyFireList.size(); t++) {

                this.enemyFireList.get(t).update(canvasWidth, canvasHeight, this.frame);
            }


        }
    //}



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
        for (int f = 0; f< aliensList.size(); f++){
            if (aliensList.get(f).y + aliensList.get(f).size >= this.canvasHeight){
                return true;
            }
        }

        for (int f = 0; f < aliensList.size(); f++){
            if (aliensList.get(f).y + aliensList.get(f).size >= this.steveBuscemi.y
                    && aliensList.get(f).y <= this.steveBuscemi.y + this.steveBuscemi.height){
                if (aliensList.get(f).x >= this.steveBuscemi.x - aliensList.get(f).size
                        && aliensList.get(f).x <= this.steveBuscemi.x + this.steveBuscemi.width){
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
        //if the game has started and there's no more aliens. I don't care if you win in less than 2 frames, that's fake.
        if (frame > 2 && aliensList.size() < 1){
            return true;
        }
        return false;
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

    }

    /* Paint the screen when the player has won
     *
     * @param g The Graphics for the JPanel
     */
    private void paintWinScreen(Graphics g) {
        if (hasWonGame()){
            timer.stop();
            g.setColor(Color.cyan);
            g.fillRect(0,0,canvasWidth, canvasHeight);
            g.setColor(Color.BLACK);

            //change to fillRect

            g.fillRect(100, 20, 20, 80);
            g.fillRect(120, 100, 20, 80);
            g.fillRect(140, 20, 20, 80);
            g.fillRect(110,100, 10, 10);
            g.fillRect(140, 100, 10, 10);
            //O
            g.fillRect(210,70, 20, 100);
            g.fillRect(290, 70, 20, 100);
            g.fillRect(220, 60, 80, 10);
            g.fillRect(220, 170, 80, 10);
            //U
            g.fillRect(380, 60, 20, 110);
            g.fillRect(390, 170, 50, 10);
            g.fillRect(440, 60, 20, 120);
            //W
            g.fillRect(45, 210, 30, 180);
            g.fillRect(75, 360, 30, 30);
            g.fillRect(105, 210, 30, 180);
            g.fillRect(135, 360, 30, 30);
            g.fillRect(165, 210, 15, 165);
            g.fillRect(180, 210, 15, 150);
            //I
            g.fillRect(245, 210, 30, 30);
            g.fillRect(245, 270, 30, 120);
            //N
            g.fillRect(325, 270, 30, 120);
            g.fillRect(355, 270, 30, 30);
            g.fillRect(385, 270, 15, 120);
            g.fillRect(400, 285, 15, 105);
            //!
            g.fillRect(465, 210, 30, 120);
            g.fillRect(465, 360, 30, 30);
            //oh boy

            // FIXME draw the win screen here
        }

    }

    /* Paint the screen when the player has lost
     *
     * @param g The Graphics for the JPanel
     */
    private void paintLoseScreen(Graphics g) {
        if (hasLostGame()){
            timer.stop();
            g.setColor(Color.PINK);
            g.fillRect(0,0,canvasWidth,canvasHeight);
            g.setColor(Color.BLACK);
            g.fillRect(50, 25, 25, 150);
            g.fillRect(75, 25, 75, 25);
            g.fillRect(75, 95, 50, 25);
            g.fillRect(75, 150, 75, 25);
            //P
            g.fillRect(200, 25, 25, 150);
            g.fillRect(225, 25, 25, 25);
            g.fillRect(225, 75, 25, 25);
            g.fillRect(250, 25, 25, 75);
            g.fillRect(275, 35, 10, 55);
            //I
            g.fillRect(335, 25, 40, 150);
            //C
            g.fillRect(440, 50, 10, 100);
            g.fillRect(450, 25, 25, 150);
            g.fillRect(450, 25, 100, 25);
            g.fillRect(450, 150, 100, 25);
            //F
            g.fillRect(50, 225, 25, 150);
            g.fillRect(75, 225, 75, 25);
            g.fillRect(75, 295, 50, 25);
            //A
            g.fillRect(200, 250, 25, 125);
            g.fillRect(225, 225, 10, 100);
            g.fillRect(235, 225, 15, 25);
            g.fillRect(235, 310, 15, 15);
            g.fillRect(250, 225, 10, 100);
            g.fillRect(260, 250, 25, 125);
            g.fillRect(215, 225, 10, 25);
            g.fillRect(260, 225, 10, 25);
            //I
            g.fillRect(335, 225, 40, 150);
            //L
            g.fillRect(440, 225, 25, 150);
            g.fillRect(465, 350, 85, 25);

            // FIXME draw the game over screen here

        }

    }

    public static void main(String[] args) {
        SpaceInvaders invaders = new SpaceInvaders();
        EventQueue.invokeLater(invaders);
    }
}
