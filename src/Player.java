import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;


public class Player extends GraphicsObject {

    double x;
    double y;
    int xx;
    int yy;
    double speed_x;
    double speed_y;
    int width;
    int height;
    Color color;

    public Player(double x, double y, int width, int height){
        super(x, y);
        xx = (int) x;
        yy = (int) y;
        this.width = width;
        this.height= height;
        this.color = new Color(225, 100, 255);

        this.speed_x = 0;
        this.speed_y = 0;

    }
    public void draw (Graphics g){
        g.setColor(this.color);
        g.fillRect(270, 350, this.width, this.height);
    }

    public void update(int pic_width, int pic_height, int frame) {

        //if ((this.x + this.width) == 600 || this.x == 0){
            //this.speed_x = -speed_x;
            //this.y += 5;
        //}

        this.x += this.speed_x;
        this.y += this.speed_y;
    }
}
