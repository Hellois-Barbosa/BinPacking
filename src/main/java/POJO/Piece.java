/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package POJO;

/**
 *
 * @author Helloïs BARBOSA
 */
public class Piece {

    private final int size;

    public Piece(int size) {
        this.size = size;
    }

    public int getSize() {
        return size;
    }

    @Override
    public String toString() {
        return super.toString() + " { size: " + this.size + " }";
    }

    @Override
    public Piece clone() {
        return new Piece(this.size);
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }

        if (!(obj instanceof Piece)) {
            return false;
        }

        Piece pieceToCompare = (Piece) obj;

        return Integer.compare(this.getSize(), pieceToCompare.getSize()) == 0;
    }

}
