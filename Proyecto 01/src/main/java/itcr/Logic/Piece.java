/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package itcr.Logic;

import java.io.Serializable;

/**
 *
 * @author Samantha
 */
//Abstract representation of a piece from Chess
public abstract class Piece implements Serializable {
    protected int x, y;
    protected boolean firstMove;
    protected ChessColor color;
    
    public Piece(ChessColor color, int x, int y) {
        this.color = color;
        this.x = x;
        this.y = y;
        firstMove = true;
    }
    
    public int getX() {
        return x;
    }
    
    public int getY() {
        return y;
    }
    
    public boolean isFirstMove() {
        return firstMove;
    }
    
    public ChessColor getColor() {
        return color;
    }
    
    //Updates the piece's position information and changes state
    public void move(Board board, int x, int y) throws Exception {
        this.x = x;
        this.y = y;
        firstMove = false;
    }
    
    //Returns whether the movement to a given position is a legal move
    //in a Chess game given a board's current state
    public abstract boolean isMoveAllowed(Board board, int x, int y) throws Exception;
}
