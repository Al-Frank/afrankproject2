import java.awt.Color;
import java.awt.Graphics;

public class Aliens extends GraphicsObject {

    double x;
    double y;
    int xx;
    int yy;
    int width;
    int height;
    Color color;
    double speed_x;
    double speed_y;

    public Aliens(double x, double y, int width, int height){
        super(x, y);
        int xx = (int) x;
        int yy = (int) y;
        Color color = Color.GREEN;
        double speed_x = 3;
        double speed_y = 0;
    }

    public void draw(Graphics g) {
        int size = 40;
        int displacement = size + 40;
        this.xx = 100;
        this.yy = 10;
        for (int column = 0; column < 5; column ++){
            for (int row = 0; row < 3; row ++) {
                g.setColor(Color.green);
                g.fillRect(this.xx + (displacement * column), this.yy + (displacement * row), size, size);

            }

        }

    }
    public void update(int pic_width, int pic_height, int frame) {
        if ((this.x + this.width) == 600 || this.x == 0) {
            this.speed_x = -speed_x;
            this.y += 5;
        }

        this.x += this.speed_x;
        this.y += this.speed_y;
    }
}
