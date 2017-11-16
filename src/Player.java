import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;


public class Player extends GraphicsObject {

    int width;
    int height;
    Color color;

    public Player(double x, double y, int width, int height){
        super(x, y);
        this.width = width;
        this.height= height;
        this.color = new Color(225, 100, 255);

        this.speed_x = 0;
        this.speed_y = 0;

    }
    public void draw (Graphics g){
        g.setColor(this.color);
        g.fillRect((int) this.x, (int) this.y, this.width, this.height);
    }

    public void update(int pic_width, int pic_height, int frame) {

        this.x += this.speed_x;
        this.y += this.speed_y;
    }
}
