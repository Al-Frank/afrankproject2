import java.awt.Color;
import java.awt.Graphics;
import java.util.ArrayList;

public class Aliens extends GraphicsObject {

    Color color;
    double speed_x;
    double speed_y;
    int size;

    public Aliens(double x, double y, int width, int height){
        super(x, y);
        Color color = Color.GREEN;
        double speed_x = 3;
        double speed_y = 0;
    }

    public void draw(Graphics g) {
        int size = 40;
        int displacement = size + 40;

        g.setColor(Color.green);
        g.fillRect((int)this.x, (int)this.y, size, size);

        }


    public void update(int pic_width, int pic_height, int frame) {
        if ((this.x + this.size) == 600 || this.x == 0) {
            this.speed_x = -speed_x;
            this.y += 5;
        }

        this.x += this.speed_x;
        this.y += this.speed_y;
    }
}
