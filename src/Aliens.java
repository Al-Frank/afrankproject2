import java.awt.Color;
import java.awt.Graphics;
import java.util.ArrayList;

public class Aliens extends GraphicsObject {

    Color color;
    int size;

    public Aliens(double x, double y){
        super(x, y);
        this.color = Color.WHITE;
        this.speed_x = 2.5;
        this.speed_y = 0;
        this.size = 30;
    }

    public void draw(Graphics g) {
        g.setColor(this.color);
        g.fillRoundRect((int)this.x, (int)this.y, this.size, this.size, 4, 4);
        g.setColor(Color.black);

        g.fillRect((int)this.x + 4, (int)this.y + 2, 8, 3);


        g.fillOval((int)this.x + 5, (int)this.y + 8, 6, 6);
        g.fillOval((int)this.x + 20, (int)this.y + 6, 6, 9);
        g.fillRect((int)this.x + 5, (int)this.y + 20, 20, 5);

        }


    public void update(int pic_width, int pic_height, int frame) {
        if ((this.x + this.size) > pic_width || this.x < 0) {
            this.speed_x = -speed_x;
            this.y += 20;
        }
        super.update(pic_width, pic_height, frame);
    }
}
