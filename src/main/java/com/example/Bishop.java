//Arrune Nimalan
//Bishop
//This piece can move any number of open squares diagonally and cannot hop over other pieces. 
//It is restricted to the same color tile it starts the game on.

package com.example;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import javax.imageio.ImageIO;

//you will need to implement two functions in this file.
public class Bishop extends Piece {
    public Bishop(boolean isWhite, String img_file){
        super(isWhite, img_file);
    }

    //return a list of every square that is "controlled" by this piece. A square is controlled
    //if the piece capture into it legally.
    //Pre-condition: Board is an 8x8 array that is not null
    //Post-condition: An ArrayList of squares the piece controls no matter who occupies it
    public ArrayList<Square> getControlledSquares(Square[][] board, Square start) {
        ArrayList<Square> controlled = new ArrayList<Square>();
        int currRow = start.getRow(); 
        int currCol = start.getCol();
        boolean pathBlocked = false;
        
        //upRight
        for (int i = 1; i <= 7; i++)
        {
            int newRow = currRow - i; 
            int newCol = currCol + i;
            if ((newRow >= 0 && newRow <=7 && newCol >= 0 && newCol <= 7))
            {
                Square upRight = board[newRow][newCol];
                if (!pathBlocked)
                {
                    controlled.add(upRight);
                    if (upRight.isOccupied())
                    {
                        pathBlocked = true;
                    }
                } 
            }  
        }  

        //upLeft
        pathBlocked = false;
        for (int i = 1; i <= 7; i++)
        {
            int newRow = currRow - i;
            int newCol = currCol - i;
            if (newRow >= 0 && newRow <= 7 && newCol >= 0 && newCol <= 7)
            {
                Square upLeft = board[newRow][newCol];
                if (!pathBlocked)
                {
                    controlled.add(upLeft);
                    if (upLeft.isOccupied())
                    {
                        pathBlocked = true;
                    }
                } 
            }
        }

        //downRight
        pathBlocked = false;
        for (int i = 1; i <= 7; i++)
        {
            int newRow = currRow + i;
            int newCol = currCol + i;
            if (newRow >= 0 && newRow <= 7 && newCol >= 0 && newCol <= 7)
            {
                Square downRight = board[newRow][newCol];
                if (!pathBlocked)
                {
                    controlled.add(downRight);
                    if(downRight.isOccupied())
                    {
                        pathBlocked = true;
                    }
                }
            }
        }

        //downLeft
        pathBlocked = false;
        for (int i = 1; i <= 7; i++)
        {
            int newRow = currRow + i;
            int newCol = currCol - i;
            if (newRow >= 0 && newRow <= 7 && newCol >= 0 && newCol <= 7)
            {
                Square downLeft = board[newRow][newCol];
                if (!pathBlocked)
                {
                    controlled.add(downLeft);
                    if (downLeft.isOccupied())
                    {
                        pathBlocked = true;
                    }
                }
            }
        }
        return controlled;
    }


    //Piece: Bishop
    /*Rules: The bishop can only move diagonally (forward or back) 
    //and cannot jump over other pieces. The bishop never changes the color square it is on. 
    There are two bishops on the board at the start.*/
    //Precondition: The Board object (b) must not be null and will be a 64 square board. The start square must be within board b (0-7).
    //Postcondition: Returns an ArrayList of possible moves the piece could move to (based on the rules of the piece movement)
    public ArrayList<Square> getLegalMoves(Board b, Square start){
    	ArrayList<Square> moves = new ArrayList<>();
        int currRow = start.getRow(); 
        int currCol = start.getCol();
        boolean pathBlocked = false;
        
        //upRight
        for (int i = 1; i <= 7; i++)
        {
            int newRow = currRow - i; 
            int newCol = currCol + i;
            if ((newRow >= 0 && newRow <=7 && newCol >= 0 && newCol <= 7))
            {
                Square upRight = b.getSquareArray()[newRow][newCol];
                if (!pathBlocked)
                {
                    if (!upRight.isOccupied())
                    {
                        moves.add(upRight);
                    }
                    else 
                    {
                        if(upRight.getOccupyingPiece().getColor() != this.getColor())
                        {
                            moves.add(upRight);
                            pathBlocked = true;
                        }
                        else 
                        {
                            pathBlocked = true;
                        }
                    }

                } 
            }  
        }  

        //upLeft
        pathBlocked = false;
        for (int i = 1; i <= 7; i++)
        {
            int newRow = currRow - i;
            int newCol = currCol - i;
            if (newRow >= 0 && newRow <= 7 && newCol >= 0 && newCol <= 7)
            {
                Square upLeft = b.getSquareArray()[newRow][newCol];
                if (!pathBlocked)
                {
                    if (!upLeft.isOccupied())
                    {
                        moves.add(upLeft);
                    }
                    else 
                    {
                        if(upLeft.getOccupyingPiece().getColor() != this.getColor())
                        {
                            moves.add(upLeft);
                            pathBlocked = true;
                        }
                        pathBlocked = true;
                    }
                } 
            }
        }

        //downRight
        pathBlocked = false;
        for (int i = 1; i <= 7; i++)
        {
            int newRow = currRow + i;
            int newCol = currCol + i;
            if (newRow >= 0 && newRow <= 7 && newCol >= 0 && newCol <= 7)
            {
                Square downRight = b.getSquareArray()[newRow][newCol];
                if (!pathBlocked)
                {
                    if(!downRight.isOccupied())
                    {
                        moves.add(downRight);
                    }
                    else 
                    {
                        if(downRight.getOccupyingPiece().getColor() != this.getColor())
                        {
                            moves.add(downRight);
                            pathBlocked = true;
                        }
                        pathBlocked = true;
                    }
                }
            }
        }

        //downLeft
        pathBlocked = false;
        for (int i = 1; i <= 7; i++)
        {
            int newRow = currRow + i;
            int newCol = currCol - i;
            if (newRow >= 0 && newRow <= 7 && newCol >= 0 && newCol <= 7)
            {
                Square downLeft = b.getSquareArray()[newRow][newCol];
                if (!pathBlocked)
                {
                    if (!downLeft.isOccupied())
                    {
                        moves.add(downLeft);
                    }
                    else 
                    {
                        if(downLeft.getOccupyingPiece().getColor() != this.getColor())
                        {
                            moves.add(downLeft);
                            pathBlocked = true;
                        }
                        pathBlocked = true;
                    }
                }
            }
        }
        return moves;
    }
    //toString Overriding
    public String toString()
    {
        return "A" + super.toString() + "bishop";
    }
}