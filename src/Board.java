import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.net.URL;

public class Board extends JPanel implements ActionListener {
    int b_height = 400;
    int b_width = 400;
    int max_dots = 1600;
    int dots_size = 10;//pixel_size
    int dots = 3;
    int x[] = new int[max_dots];
    int y[] = new int[max_dots];
    int apple_x;
    int apple_y;
    Image body, head, apple;


    Timer timer;
    int DELAY = 150;//0.3sec time to take change fast snake is move

    boolean leftDirection = true;
    boolean rightDirection = false;
    boolean upDirection = false;
    boolean downDirection = false;
    boolean inGame = true;

    Board() {
        TAdapter tAdapterObject = new TAdapter();
        addKeyListener(tAdapterObject);
        setFocusable(true);
        setPreferredSize(new Dimension(b_width, b_height));
        setBackground(Color.black);
        initGame();
        loadImages();

    }

    //initialization game
    public void initGame() {
        // dots=3;
        x[0] = 250;
        y[0] = 250;
        for (int i = 1; i < dots; i++) {
            x[i] = x[0] + dots_size * i;//dots two print initially
            y[i] = y[0];


        }
        //initialization Apple position
        //        apple_x=150;//fixed apple size
//             apple_y=150;
        locateApple();//call apple random location
        timer = new Timer(DELAY, this);
        timer.start();
    }

    //load images from resources folder to image object
    public void loadImages() {

        ImageIcon bodyIcon = new ImageIcon("src/resources/dot.png");
        body = bodyIcon.getImage();
        ImageIcon headIcon = new ImageIcon("src/resources/head.png");
        head = headIcon.getImage();
        ImageIcon appleIcon = new ImageIcon("src/resources/apple.png");
        apple = appleIcon.getImage();
    }

    //draw images at snake and apples position
    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        doDrawing(g);
    }
    //method for draw images

    public void doDrawing(Graphics g) {
        if (inGame) {
            g.drawImage(apple, apple_x, apple_y, this);
            for (int i = 0; i < dots; i++) {
                if (i == 0) {
                    g.drawImage(head, x[0], y[0], this);
                } else
                    g.drawImage(body, x[i], y[i], this);

            }
        }
        else
        {
            gameOver(g);
            timer.stop();
        }
    }

    //random position generate apple
    public void locateApple(){
        apple_x=((int)(Math.random()*39))*dots_size;
        apple_y=((int)(Math.random()*39))*dots_size;

    }
//check collisions with border and body

    public void checkCollision() {
//collision with body
        for(int i=1;i<dots;i++){
    if(i>4&&x[0]==x[i]&&y[0]==y[i]){
       inGame=false;
    }
}
        //collision with border
        if(x[0]<0){
            inGame=false;
        }
        if(x[0]>=b_width){

            inGame=false;
        }
        if(y[0]<0){
            inGame=false;
        }
        if(y[0]>=b_height){
            inGame=false;
        }
    }
    //display game over msg
    public void gameOver(Graphics g){
        String msg="Game Over";
        int score=(dots-3)*100;
        String scoremsg="score:"+Integer.toString(score);
        Font small=new Font("helvetica",Font.BOLD,14);
        FontMetrics fontMetrics=getFontMetrics(small);
        g.setColor(Color.WHITE);
        g.setFont(small);
        g.drawString(msg,(b_width-fontMetrics.stringWidth(msg))/2,1*(b_height/4));
        g.drawString(scoremsg,(b_width-fontMetrics.stringWidth(scoremsg))/2,2*(b_height/4));
    }

    @Override
    public void actionPerformed(ActionEvent e) {
      if(inGame) {
          checkApple();
          checkCollision();
          ;
          move();
          repaint();
      }
    }
    //make snake move
    public void move(){
        for(int i=dots-1;i>0;i--){
        x[i]=x[i-1];
         y[i]=y[i-1];
        }
        if(leftDirection){
            x[0]-=dots_size;
        }
        if(rightDirection){
            x[0]+=dots_size;
        }
        if(upDirection){
            y[0]-=dots_size;
        }
        if(downDirection){
            y[0]+=dots_size;
        }
    }

    //snake eat apple
    public void checkApple(){
        if(apple_x==x[0]&&apple_y==y[0]){
            dots++;
            locateApple();
        }
    }
   // implement control
    private class TAdapter extends KeyAdapter  {
        @Override
        public void keyPressed(KeyEvent keyEvent){
           // this.keyEvent = keyEvent;
            int key=keyEvent.getKeyCode();
            if(key==keyEvent.VK_LEFT&&!rightDirection){
                leftDirection=true;
                upDirection=false;
                downDirection=false;

            }
            if(key==keyEvent.VK_RIGHT&&!leftDirection){
                rightDirection=true;
                upDirection=false;
                downDirection=false;

            }
            if(key==keyEvent.VK_UP&&!downDirection){
                leftDirection=false;
                upDirection=true;
                rightDirection=false;

            }
            if(key==keyEvent.VK_DOWN&&!upDirection){
                leftDirection=false;
                rightDirection=false;
                downDirection=true;

            }

        }
    }
}
