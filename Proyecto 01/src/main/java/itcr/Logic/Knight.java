/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package itcr.Logic;

import Exceptions.PieceNotFoundException;
import java.io.Serializable;
import static java.lang.Math.abs;

/**
 *
 * @author lito
 */
//Representation of the Knight piece from Chess
//Jumps and moves in an L-shape
public class Knight extends Piece implements Serializable {
    
    public Knight(ChessColor color, int x, int y) {
        super(color, x, y);
    }

    @Override
    public boolean isMoveAllowed(Board board, int targetX, int targetY) throws PieceNotFoundException {
        // Verificar si el movimiento está dentro del tablero
        if (targetX < 0 || targetX >= board.getFiles() || targetY < 0 || targetY >= board.getRanks()) {
            return false;
        }

        // Calcular la diferencia en las coordenadas
        int dx = abs(this.x - targetX);
        int dy = abs(this.y - targetY);

        // Verificar si el movimiento es en forma de "L"
        boolean isLShapeMove = (dx == 2 && dy == 1) || (dx == 1 && dy == 2);

        if (!isLShapeMove) {
            return false;
        }

        // Verificar si la casilla de destino está ocupada por una pieza del mismo color
        if (!board.isSquareEmpty(targetX, targetY)) {
            Piece targetPiece = board.getPieceInSquare(targetX, targetY);
            if (this.color.equals(targetPiece.color)) {
                return false; // Movimiento inválido, hay una pieza del mismo color en el destino
            }
        }

        // Si todos los chequeos son válidos, el movimiento está permitido
        return true;
    }
}