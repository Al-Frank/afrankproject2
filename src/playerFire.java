import java.awt.Color;
import java.awt.Graphics;


public class playerFire extends GraphicsObject {

    int width;
    int height;
    Color p_fire_color;
    int xx;
    int yy;

    public playerFire(double x, double y, int width, int height, Color p_fire_color) {
        super(x, y);
        this.xx = (int) x;
        this.yy = (int) y;
        this.width = width;
        this.height = height;
        this.p_fire_color = p_fire_color;

    }

    public void draw(Graphics g) {

        g.setColor(this.p_fire_color);
        g.fillOval(xx, yy, this.width, this.height / 4);
        g.fillOval(xx, yy + (height / 4), width, height / 4);
        g.fillOval(xx, yy + (2 * (height / 4)), width, height / 4);
        g.fillOval(xx, yy + (3 * (height / 4)), width, height / 4);
    }

    //Makes ammo bounce off of walls
    public void update(int pic_width, int pic_height, int frame) {
        if (this.x - this.width - this.height < 0 || this.x + this.height > pic_width) {
            this.speed_x = -this.speed_x;
        }
        super.update(pic_width, pic_height, frame);
    }
}

