/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package itcr.Logic;

import Exceptions.PieceNotFoundException;
import java.io.Serializable;

/**
 *
 * @author Samantha
 */
//Representation of the board in a Chess game
public class Board implements Serializable {
    private static final int RANKS = 8;
    private static final int FILES = 8;
    private Piece[][] squares;

    public Board() {
        squares = new Piece[RANKS][FILES];
    }
    
    public int getRanks() {
        return RANKS;
    }
    
    public int getFiles() {
        return FILES;
    }
    
    public boolean isSquareEmpty(int x, int y) {
        return squares[x][y] == null;
    }
    
    public Piece getPieceInSquare(int x, int y) throws PieceNotFoundException {
        if (squares[x][y] == null) {
            String squareNotation = getSquareNotation(x, y);
            throw new PieceNotFoundException(squareNotation + " is empty.");
        }
        return squares[x][y];
    }
    
    public void addPiece(Piece piece, int x, int y) {
        squares[x][y] = piece;
    }
    
    public void emptySquare(int x, int y) {
        squares[x][y] = null;
    }
    
    public void clear() {
        squares = new Piece[RANKS][FILES];
    }
    
    public String getSquareNotation(int x, int y) {
        return String.valueOf((char) ('a' + x)) + (FILES - y);
    }
    
    @Override
    public String toString() {
        String result = "";
        for (int j = 0; j < FILES; j++) {
            for (int i = 0; i < RANKS; i++) {
                Piece p = squares[i][j];
                String character =
                        p instanceof Pawn? "P" : 
                        p instanceof King? "K" :
                        p instanceof Queen? "Q" :
                        p instanceof Bishop? "B" :
                        p instanceof Rook? "R" :
                        p instanceof Knight? "N" : " ";
                if (p instanceof Piece) {
                    character = p.getColor() == ChessColor.WHITE? character.toLowerCase() : character;
                }
                result += "[" + character + "] ";
            }
            result += "\n\n";
        }
        return result;
    }
}
