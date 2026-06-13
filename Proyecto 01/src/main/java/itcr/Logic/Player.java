/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package itcr.Logic;

import java.io.Serializable;
import java.util.ArrayList;

/**
 *
 * @author Samantha
 */
//Representation of a player in a Chess game
public class Player implements Serializable {
    private String name;
    private ChessColor color;
    private ArrayList<Piece> capturedPieces;
    private Piece king;

    public Player(String name, ChessColor color) {
        this.name = name;
        this.color = color;
        this.capturedPieces = new ArrayList<>();
    }

    public String getName() {
        return name;
    }

    public ChessColor getColor() {
        return color;
    }

    public void assignKing(Piece king) {
        this.king = king;
    }
    
    public void addCapturedPiece(Piece piece){
    capturedPieces.add(piece);
}

}




