package com.example;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import javax.imageio.ImageIO;

//you will need to implement two functions in this file.
public class Piece {
    private final boolean color;
    private BufferedImage img;
    
    public Piece(boolean isWhite, String img_file) {
        this.color = isWhite;
         
        try {
            if (this.img == null) {
                this.img = ImageIO.read(new File(System.getProperty("user.dir")+img_file));
            }
          } catch (IOException e) {
            System.out.println("File not found: " + e.getMessage());
          }
    }
    
    

    
    public boolean getColor() {
        return color;
    }
    
    public Image getImage() {
        return img;
    }
    
    public void draw(Graphics g, Square currentSquare) {
        int x = currentSquare.getX();
        int y = currentSquare.getY();
        
        g.drawImage(this.img, x, y, null);
    }
    
    
    // FINISHED
    //return a list of every square that is "controlled" by this piece. A square is controlled
    //if the piece capture into it legally.
    public ArrayList<Square> getControlledSquares(Square[][] board, Square start) {
        int x = start.getCol();
        int y = start.getRow();

        ArrayList<Square> controlledSquares = new ArrayList<Square> ();

        int[] signs = {1, -1};
        int[][] pairs = {{1, 2}, {2,1}};

        for (int sign1 : signs) {
            for (int sign2 : signs) {
                for (int[] pair : pairs) {
                    int newX = x + sign2 * pair[1];
                    int newY = y + sign1 * pair[0];

                    if (newX >= 0 && newX <=7 && newY >= 0 && newY <=7) {
                        controlledSquares.add(board[newY][newX]);
                    }
                }
            }
        }
        
        return controlledSquares;
    }

    //FINISHED
    //implement the move function here
    //it's up to you how the piece moves, but at the very least the rules should be logical and it should never move off the board!
    //returns an arraylist of squares which are legal to move to
    //please note that your piece must have some sort of logic. Just being able to move to every square on the board is not
    //going to score any points.
    public ArrayList<Square> getLegalMoves(Board b, Square start){
    	ArrayList<Square> controlled = getControlledSquares(b.getSquareArray(), start);
        ArrayList<Square> valid = new ArrayList <Square> ();

        for (int i = 0; i < controlled.size(); i ++) {
            Square s = controlled.get(i);

            if ((!s.isOccupied()) || (s.getOccupyingPiece().getColor() != this.color)) {
                valid.add(s);
            }
        }

        return valid;
    }
}