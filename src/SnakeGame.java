import javax.swing.*;

public class SnakeGame extends JFrame {
    public Board boardObject ;
SnakeGame(){
   boardObject = new Board();
add(boardObject);
 pack();
setResizable(false);
setVisible(true);
}

    public static void main(String []args) {

    SnakeGame snakeGameObject=new SnakeGame();
    }
}