import java.awt.Color;
import java.awt.Graphics;

public class enemyFire extends GraphicsObject {

    int width;
    int height;
    Color e_fire_color;
    int xx;
    int yy;

    public enemyFire(double x, double y, int width, int height, Color e_fire_color) {
        super(x, y);
        this.xx = (int) x;
        this.yy = (int) y;
        this.width = width;
        this.height = height;
        this.e_fire_color = e_fire_color;
    }

    public void draw(Graphics g) {

        g.setColor(this.e_fire_color);
        g.fillRect(xx, yy, this.width, this.height);
    }

    //Makes ammo bounce off of walls
    public void update(int pic_width, int pic_height, int frame) {
        if (this.x - this.width - this.height < 0 || this.x + this.height > pic_width) {
            this.speed_x = -this.speed_x;
        }
        super.update(pic_width, pic_height, frame);
    }
}
