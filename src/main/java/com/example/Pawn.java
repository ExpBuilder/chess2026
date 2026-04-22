//Miguel M
// Pawn, moves forward one square and captures diagonally forward. Can move forward 2 spaces if it's the pawn's first move.
package com.example;

import java.util.ArrayList;

//you will need to implement two functions in this file.

public class Pawn extends Piece {
    private int movedDouble;

    public Pawn(boolean isWhite, String img_file) {
        super(isWhite, img_file);
        movedDouble = 0;
    }

    @Override
    public void updateEnPassantStatus() {
        movedDouble--;
        if (movedDouble < 0)
            movedDouble = 0;
    }

    @Override
    public int getEnPassantStatus() {
        return movedDouble;
    }

    @Override
    public String toString() {

        return "A " + super.toString() + " pawn";
    }

    // precon: a valid double square array, a valid square on the board
    // poscon: all the squares that the pawn controls on the board, as of the square
    // given
    @Override
    public ArrayList<Square> getControlledSquares(Square[][] board, Square start) {
        ArrayList<Square> controlledSquares = new ArrayList<Square>();

        boolean iswhite = this.getColor();

        int row = start.getRow();
        int col = start.getCol();
        if (iswhite) {
            if ((row - 1 >= 0) && (col - 1 >= 0)) {
                controlledSquares.add(board[row - 1][col - 1]); // Left
            }
            if ((row - 1 >= 0) && (col + 1 < 8)) {
                controlledSquares.add(board[row - 1][col + 1]); // Right
            }
        } else {
            if ((row + 1 < 8) && (col - 1 >= 0)) {
                controlledSquares.add(board[row + 1][col - 1]); // Left
            }
            if ((row + 1 < 8) && (col + 1 < 8)) {
                controlledSquares.add(board[row + 1][col + 1]); // Right
            }
        }

        return controlledSquares;
    }

    // RULES:
    // The pawn can move one piece forward at a time
    // If it is the pawns first move, it can move 2 spaces forward
    // If a piece is one space diagonally forward left or right to the pawn,
    // then it can move to that space and capture it if there is a pawn of the
    // opposite color there.

    // precon: a valid 8x8 chess board, and a valid square on that chess board
    // poscon: returns all the legal moves for the pawn on that square
    @Override
    public ArrayList<Square> getLegalMoves(Board b, Square start) {
        ArrayList<Square> moves = new ArrayList<Square>();
        boolean iswhite = this.getColor();
        int sr = start.getRow();
        int sc = start.getCol();

        Square[][] board = b.getSquareArray();

        // white
        if (iswhite) {
            // 1 move forward

            if (sr - 1 >= 0
                    &&
                    !(b.getSquareArray()[sr - 1][sc].isOccupied())) {
                moves.add(b.getSquareArray()[sr - 1][sc]);
            }

            // 2 move forward

            if (sr == 6
                    &&
                    !(b.getSquareArray()[sr - 1][sc].isOccupied())
                    &&
                    !(b.getSquareArray()[sr - 2][sc].isOccupied())) {
                moves.add(b.getSquareArray()[sr - 2][sc]);

                movedDouble = 2;
            }

            // captures

            // left capture
            if ((sr - 1 >= 0) && (sc - 1 >= 0)
                    &&
                    b.getSquareArray()[sr - 1][sc - 1].isOccupied()
                    &&
                    b.getSquareArray()[sr - 1][sc - 1].getOccupyingPiece().getColor() != iswhite) {
                moves.add(b.getSquareArray()[sr - 1][sc - 1]);
            }

            // right capture
            if ((sr - 1 >= 0) && (sc + 1 < 8)
                    &&
                    b.getSquareArray()[sr - 1][sc + 1].isOccupied()
                    &&
                    b.getSquareArray()[sr - 1][sc + 1].getOccupyingPiece().getColor() != iswhite) {
                moves.add(b.getSquareArray()[sr - 1][sc + 1]);
            }

            // En passant
            int[] dir = {-1, 1};
            for (int i = 0; i < 2; i++) {
                if (sr == 3) {
                    if (board[sr][sc+dir[i]].isOccupied()) {
                        Piece op = board[sr][sc+dir[i]].getOccupyingPiece();

                        if (op instanceof Pawn && (op.getEnPassantStatus() == 1)) {
                            moves.add(b.getSquareArray()[sr - 1][sc + dir[i]]);
                        }
                    }
                }
            }
            
        }
        // black
        else {
            // 1 move forward

            if (sr + 1 < 8
                    &&
                    !(b.getSquareArray()[sr + 1][sc].isOccupied())) {
                moves.add(b.getSquareArray()[sr + 1][sc]);
            }

            // 2 move forward

            if (sr == 1
                    &&
                    !(b.getSquareArray()[sr + 1][sc].isOccupied())
                    &&
                    !(b.getSquareArray()[sr + 2][sc].isOccupied())) {
                moves.add(b.getSquareArray()[sr + 2][sc]);
                movedDouble = 2;
            }

            // captures

            // left capture
            if ((sr + 1 < 8) && (sc - 1 >= 0)
                    &&
                    b.getSquareArray()[sr + 1][sc - 1].isOccupied()
                    &&
                    b.getSquareArray()[sr + 1][sc - 1].getOccupyingPiece().getColor() != iswhite) {
                moves.add(b.getSquareArray()[sr + 1][sc - 1]);
            }

            // right capture
            if ((sr + 1 < 8) && (sc + 1 < 8)
                    &&
                    b.getSquareArray()[sr + 1][sc + 1].isOccupied()
                    &&
                    b.getSquareArray()[sr + 1][sc + 1].getOccupyingPiece().getColor() != iswhite) {
                moves.add(b.getSquareArray()[sr + 1][sc + 1]);
            }

            // En passant
            int[] dir = {-1, 1};
            for (int i = 0; i < 2; i++) {
                if (sr == 4) {
                    if (board[sr][sc+dir[i]].isOccupied()) {
                        Piece op = board[sr][sc+dir[i]].getOccupyingPiece();

                        if (op instanceof Pawn && (op.getEnPassantStatus() == 1)) {
                            moves.add(b.getSquareArray()[sr + 1][sc + dir[i]]);
                        }
                    }
                }
            }
        }
        return moves;
    }
}
