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
//Representation of the Rook piece from Chess
//Moves either horizontally or vertically any amount of spaces
//Can perform the special move Castling with the King piece
public class Rook extends Piece implements Serializable {

    public Rook(ChessColor color, int x, int y) {
        super(color, x, y);
    }

    @Override
    public boolean isMoveAllowed(Board board, int targetX, int targetY) throws PieceNotFoundException {
        // Calcular la diferencia en las coordenadas
        int dx = abs(this.x - targetX);
        int dy = abs(this.y - targetY);

        // Verificar si se mueve en línea recta (horizontal o verticalmente)
        if (dx != 0 && dy != 0) {
            return false; // Movimiento inválido, no es ni en fila ni en columna
        }

        // Verificar si hay piezas en el camino
        if (dx > 0) { // Movimiento horizontal
            int step = (targetX - this.x) / dx; // Determinar la dirección del movimiento
            for (int i = 1; i < dx; i++) {
                if (!board.isSquareEmpty(this.x + i * step, this.y)) {
                    return false; // Hay una pieza en el camino
                }
            }
        } else { // Movimiento vertical
            int step = (targetY - this.y) / dy; // Determinar la dirección del movimiento
            for (int i = 1; i < dy; i++) {
                if (!board.isSquareEmpty(this.x, this.y + i * step)) {
                    return false; // Hay una pieza en el camino
                }
            }
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