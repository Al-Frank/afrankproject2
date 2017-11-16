import java.awt.Color;
import java.awt.Graphics;
import java.util.ArrayList;

public class Aliens extends GraphicsObject {

    Color color;
    int size;

    public Aliens(double x, double y){
        super(x, y);
        this.color = Color.GREEN;
        this.speed_x = 3;
        this.speed_y = 0;
        this.size = 30;
    }

    public void draw(Graphics g) {
        g.setColor(this.color);
        g.fillRect((int)this.x, (int)this.y, this.size, this.size);

        }


    public void update(int pic_width, int pic_height, int frame) {
        if ((this.x + this.size) > pic_width || this.x < 0) {
            this.speed_x = -speed_x;
            this.y += 5;
        }
        super.update(pic_width, pic_height, frame);
    }
}
