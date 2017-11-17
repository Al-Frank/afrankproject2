import java.awt.Color;
import java.awt.Graphics;

public class enemyFire extends GraphicsObject {

    int width;
    int height;
    Color e_fire_color;
    int xx;
    int yy;
    int speed_y;

    public enemyFire(double x, double y) {
        super(x, y);
        this.xx = (int) x;
        this.yy = (int) y;
        this.width = 5;
        this.height = 15;
        this.e_fire_color = new Color(250, 0, 50);
        this.speed_y = 4;
    }

    public void draw(Graphics g) {

        g.setColor(this.e_fire_color);
        g.fillRect(xx, yy, this.width, this.height);
    }
    public void update(int pic_width, int pic_height, int frame) {

        this.yy += this.speed_y;

        super.update(pic_width, pic_height, frame);
    }
    }

