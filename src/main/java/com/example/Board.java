package com.example;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.Point;
import java.awt.Toolkit;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.net.URL;
import java.util.ArrayList;

import javax.swing.BorderFactory;
import javax.swing.JPanel;

//You will be implmenting a part of a function and a whole function in this document. Please follow the directions for the 
//suggested order of completion that should make testing easier.
@SuppressWarnings("serial")
public class Board extends JPanel implements MouseListener, MouseMotionListener {
	// Resource location constants for piece images
    private static final String path = "/src/main/java/com/example/Pictures/";
    private static final String RESOURCES_WBISHOP_PNG = path+"wbishop.png";
	private static final String RESOURCES_BBISHOP_PNG = path+"bbishop.png";
	private static final String RESOURCES_WKNIGHT_PNG = path+"wknight.png";
	private static final String RESOURCES_BKNIGHT_PNG = path+"bknight.png";
	private static final String RESOURCES_WROOK_PNG = path+"wrook.png";
	private static final String RESOURCES_BROOK_PNG = path+"brook.png";
	private static final String RESOURCES_WKING_PNG = path+"wking.png";
	private static final String RESOURCES_BKING_PNG = path+"bking.png";
	private static final String RESOURCES_BQUEEN_PNG = path+"bqueen.png";
	private static final String RESOURCES_WQUEEN_PNG = path+"wqueen.png";
	private static final String RESOURCES_WPAWN_PNG = path+"wpawn.png";
	private static final String RESOURCES_BPAWN_PNG = path+"bpawn.png";

    
	
	// Logical and graphical representations of board
	private final Square[][] board;
    private final GameWindow g;
 
    //contains true if it's white's turn.
    private boolean whiteTurn;

    //if the player is currently dragging a piece this variable contains it.
    Piece currPiece;
    private Square fromMoveSquare;
    
    //used to keep track of the x/y coordinates of the mouse.
    private int currX;
    private int currY;
    
    public Board(GameWindow g) {
        this.g = g;
        board = new Square[8][8];
        setLayout(new GridLayout(8, 8, 0, 0));

        this.addMouseListener(this);
        this.addMouseMotionListener(this);

    
        for (int row = 0; row < 8; row++){
            for (int col = 0; col < 8; col++){
                board[row][col] = new Square(this, (row + col) % 2 == 0, row, col);
                //Board b, boolean isWhite, int row, int col
                this.add(board[row][col]);
            }
        }

        initializePieces();
        

        this.setPreferredSize(new Dimension(400, 400));
        this.setMaximumSize(new Dimension(400, 400));
        this.setMinimumSize(this.getPreferredSize());
        this.setSize(new Dimension(400, 400));

        whiteTurn = true;

    }

    
	// Board set up
    void initializePieces() {
    	// White pieces
        board[7][0].put(new Rook(true, RESOURCES_WROOK_PNG));
    	board[7][1].put(new Knight(true, RESOURCES_WKNIGHT_PNG));
        board[7][2].put(new Bishop(true, RESOURCES_WBISHOP_PNG));
        board[7][3].put(new Queen(true, RESOURCES_WQUEEN_PNG));
        board[7][4].put(new King(true, RESOURCES_WKING_PNG));
        board[7][5].put(new Bishop(true, RESOURCES_WBISHOP_PNG));
        board[7][6].put(new Knight(true, RESOURCES_WKNIGHT_PNG));
        board[7][7].put(new Rook(true, RESOURCES_WROOK_PNG));
        for (int i = 0; i < 8; i++) {
            board[6][i].put(new Pawn(true, RESOURCES_WPAWN_PNG));
        }


        // Black pieces
        board[0][0].put(new Rook(false, RESOURCES_BROOK_PNG));
        board[0][1].put(new Knight(false, RESOURCES_BKNIGHT_PNG));
        board[0][2].put(new Bishop(false, RESOURCES_BBISHOP_PNG));
        board[0][3].put(new Queen(false, RESOURCES_BQUEEN_PNG));
        board[0][4].put(new King(false, RESOURCES_BKING_PNG));
        board[0][5].put(new Bishop(false, RESOURCES_BBISHOP_PNG));
        board[0][6].put(new Knight(false, RESOURCES_BKNIGHT_PNG));
        board[0][7].put(new Rook(false, RESOURCES_BROOK_PNG));
        for (int i = 0; i < 8; i++) {
            board[1][i].put(new Pawn(false, RESOURCES_BPAWN_PNG));
        }
    }

    public Square[][] getSquareArray() {
        return this.board;
    }

    public boolean getTurn() {
        return whiteTurn;
    }

    public void setCurrPiece(Piece p) {
        this.currPiece = p;
    }

    public Piece getCurrPiece() {
        return this.currPiece;
    }

    public boolean isInCheck() {
        boolean isInCheck = false;

        for (int r = 0; r < 8; r++){
            for (int c = 0; c < 8; c++) {
                Square s = board[r][c];

                if (s.isOccupied()) {
                    Piece p = s.getOccupyingPiece();
                    ArrayList<Square> controlled = p.getControlledSquares(board, s);

                    if (p.getColor() != whiteTurn){
                        for (int i = 0; i < controlled.size(); i++) {
                            Square s2 = controlled.get(i);

                            if (s2.isOccupied() && (s2.getOccupyingPiece() instanceof King) && (s2.getOccupyingPiece().getColor() == whiteTurn)) {
                                isInCheck = true;
                            }
                        }
                    }
                }
            }
        }

        return isInCheck;
    }

    @Override
    public void paintComponent(Graphics g) {
     Image backgroundImage = null; 
     URL imageUrl = null;
     if (currPiece != null) {
      imageUrl = getClass().getResource("/src/main/java/com/example/"+currPiece.getImage());
     }

     if (imageUrl != null) {
            // This is the cleanest way to get an AWT Image object from a URL
            backgroundImage = Toolkit.getDefaultToolkit().createImage(imageUrl);
        } 
    

        for (int x = 0; x < 8; x++) {
            for (int y = 0; y < 8; y++) {
                board[x][y].paintComponent(g);
            }
        }

        if (fromMoveSquare != null) fromMoveSquare.setBorder(BorderFactory.createMatteBorder(1,1,1,1,Color.BLUE));

    	if (currPiece != null) {
            if ((currPiece.getColor() && whiteTurn)
                    || (!currPiece.getColor()&& !whiteTurn)) {
                final Image img = currPiece.getImage();
                g.drawImage(img, currX, currY, null);
            }
        }
    }

    @Override
    public void mousePressed(MouseEvent e) {
        currX = e.getX();
        currY = e.getY();

        Square sq = (Square) this.getComponentAt(new Point(e.getX(), e.getY()));

        if (sq.isOccupied() && sq.getOccupyingPiece().getColor() == whiteTurn) {
            currPiece = sq.getOccupyingPiece();
            fromMoveSquare = sq;

            for (Square s : currPiece.getLegalMoves(this, fromMoveSquare)) {
                s.setBorder(BorderFactory.createMatteBorder(1, 1, 1, 1, Color.RED));
            }

            if (currPiece.getColor() != whiteTurn)
                return;
            sq.setDisplay(false);
        }
        repaint();
    }

    // Pre-condition: The mouse is released
    // Post-condition: If the user moved a piece to a valid square in a legal manner, the selected piece shall move to the selected square. The turn will also change if the move legal
    @Override
    public void mouseReleased(MouseEvent e) {
        Square endSquare = (Square) this.getComponentAt(new Point(e.getX(), e.getY()));
        
        if (fromMoveSquare != null && currPiece != null) { // Move only to square if there actually is a square and the user is actually holding a piece
            if (currPiece.getLegalMoves(this, fromMoveSquare).contains(endSquare)) { // Move only if the end square can legally be moved to by the piece 
                if (endSquare.isOccupied()) {
                    Piece p = endSquare.getOccupyingPiece();

                    // Moves the piece
                    endSquare.put(currPiece);
                    fromMoveSquare.removePiece();

                    if (isInCheck()) {
                        fromMoveSquare.put(currPiece);
                        endSquare.put(p);

                    } else {
                        whiteTurn = !whiteTurn;
                        enPassantUpdate();
                    }
                } else {
                    // Moves the piece
                    endSquare.put(currPiece);
                    fromMoveSquare.removePiece();

                    if (isInCheck()) {
                        fromMoveSquare.put(currPiece);
                        endSquare.removePiece();
                    }
                    else {
                        whiteTurn = !whiteTurn;
                        enPassantUpdate();
                        
                        if (currPiece instanceof Pawn) {
                            int vertical = -1;
                            if (!whiteTurn) vertical = 1;
                            
                            if (board[endSquare.getRow() + vertical][endSquare.getCol()].isOccupied()){
                                if (board[endSquare.getRow() + vertical][endSquare.getCol()].getOccupyingPiece() instanceof Pawn) {
                                    board[endSquare.getRow() + vertical][endSquare.getCol()].removePiece();
                                }
                            }
                        }
                    }
                }
            }
            // Unhides piece (for display if it wasn't moved)
            fromMoveSquare.setDisplay(true);
            currPiece = null;
        }

        // Clear all previous indicators 
        for (Square[] row : board) {
            for (Square s : row) s.setBorder(null);
        }
    
        repaint();
    }

    public void enPassantUpdate () {
        for (Square[] r : board) {
            for (Square c : r) {
                if (c.isOccupied()) {
                    c.getOccupyingPiece().updateEnPassantStatus();
                }
            }
        }
    }

    @Override
    public void mouseDragged(MouseEvent e) {
        currX = e.getX() - 24;
        currY = e.getY() - 24;

        repaint();
    }

    @Override
    public void mouseMoved(MouseEvent e) {
    }

    @Override
    public void mouseClicked(MouseEvent e) {
    }

    @Override
    public void mouseEntered(MouseEvent e) {
    }

    @Override
    public void mouseExited(MouseEvent e) {
    }

}