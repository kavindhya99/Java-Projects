import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.util.Random;
public class gamePanel extends JPanel implements ActionListener{
    static final int SCREEN_WIDTH = 600;
    static final int SCREEN_HEIGHT = 600;
    static final int UNIT_SIZE = 30;
    static final int GAME_UNITS = 12000;
    static final int DELAY = 75;
    final int[] x = new int[12000];
    final int[] y = new int[12000];
    int bodyParts = 3;
    int appleEaten;
    int appleX;
    int appleY;
    char direction = 'R';
    boolean running = false;
    Timer timer;
    Random random=new Random();
    gamePanel() {
        this.setPreferredSize(new Dimension(600, 600));
        this.setBackground(Color.white);
        this.setFocusable(true);
        this.addKeyListener(new myKeyAdapter());
        startGame();
    }
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        draw(g);
    }
    public void draw(Graphics g) {
        if(running){
        for(int i = 0; i < 20; ++i) {
            g.drawLine(i * 30, 0, i * 30, 600);
            g.drawLine(0, i * 30, 600, i * 30);
        }
        g.setColor(Color.red);
        g.fillOval(this.appleX, this.appleY, 30, 30);
        for(int i = 0; i < this.bodyParts; ++i) {
            g.setColor(Color.orange);
            g.fillOval(this.x[i], this.y[i], 30, 30);
        }
        g.setColor(Color.blue);
        g.setFont(new Font("Times New Roman", 1, 40));
        FontMetrics metrics = this.getFontMetrics(g.getFont());
        g.drawString("SCORE: " + this.appleEaten, (600 - metrics.stringWidth("SCORE: " + this.appleEaten)) / 1, g.getFont().getSize());
    }
        else {
            this.gameOver(g);
        }
    }
    public void newApple() {
        this.appleX = this.random.nextInt(20) * 30;
        this.appleY = this.random.nextInt(20) * 30;
    }
    public void startGame() {
        this.newApple();
        this.running = true;
        this.timer = new Timer(DELAY, this);
        this.timer.start();
    }
    public void move() {
        for(int i = this.bodyParts; i > 0; --i) {
            this.x[i] = this.x[i - 1];
            this.y[i] = this.y[i - 1];
        }
        switch (this.direction) {
            case 'D':
                this.y[0] += 30;
                break;
            case 'L':
                this.x[0] -= 30;
                break;
            case 'R':
                this.x[0] += 30;
                break;
            case 'U':
                this.y[0] -= 30;
        }


    }
    @Override
    public void actionPerformed(ActionEvent e) {
        if (this.running) {
            this.move();
            checkApple();
            checkCollisions();
        }

        this.repaint();
    }
 public class myKeyAdapter extends KeyAdapter{
        @Override
     public void keyPressed(KeyEvent e)
        {
            switch(e.getKeyCode()){
                case KeyEvent.VK_LEFT:
                    if(direction!='R'){
                        direction='L';
                    }
                    break;

                 case KeyEvent.VK_RIGHT:
                     if(direction!='L'){
                         direction='R';
                     }
                   break;
                case KeyEvent.VK_UP:
                    if(direction!='D'){
                        direction='U';
                    }
                    break;
                case KeyEvent.VK_DOWN:
                    if(direction!='U'){
                        direction='D';
                    }
                    break;

        }}
 }
 public void checkApple(){
        if((x[0]==appleX)&&(y[0] ==appleY)){
            bodyParts++;
            appleEaten++;
            newApple();
        }
 }

    public void checkCollisions() {
        for(int i = this.bodyParts; i > 0; --i) {
            if (this.x[0] == this.x[i] && this.y[0] == this.y[i]) {
                this.running = false;
            }
        }

        if (this.x[0] < 0) {
            this.running = false;
        }

        if (this.x[0] > 600) {
            this.running = false;
        }

        if (this.y[0] < 0) {
            this.running = false;
        }

        if (this.y[0] > 600) {
            this.running = false;
        }

        if (!this.running) {
            this.timer.stop();
        }

    }
    public void gameOver(Graphics g) {
        g.setColor(Color.red);
        g.setFont(new Font("Times New Roman", 1, 40));
        FontMetrics metrics = this.getFontMetrics(g.getFont());
        g.drawString("SCORE: " + this.appleEaten, (600 - metrics.stringWidth("SCORE: " + this.appleEaten)) / 2, 200);
        g.setColor(Color.red);
        g.setFont(new Font("Times New Roman", 1, 75));
        FontMetrics metrics2 = this.getFontMetrics(g.getFont());
        g.drawString("GAME OVER", (600 - metrics2.stringWidth("GAME OVER")) / 2, 300);
    }


}