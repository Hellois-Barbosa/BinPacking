/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package POJO;

import java.util.ArrayList;

/**
 *
 * @author Helloïs BARBOSA
 */
public class Bin {

    private final int size;
    private ArrayList<Piece> pieces;
    private int freeSpace;

    public Bin(int size) {
        this.size = size;
        this.pieces = new ArrayList<>();
        this.freeSpace = size;
    }

    public Bin(int size, ArrayList<Piece> pieces) throws Exception {
        this.size = size;
        this.pieces = pieces;

        int nbPieces = pieces.size();
        int piecesSize = 0;
        for (int i = 0; i < nbPieces; i++) {
            piecesSize += pieces.get(i).getSize();
        }
        this.freeSpace = size - piecesSize;

        if (this.freeSpace < 0) {
            throw new Exception("The bin is too small to contain all pieces");
        }
    }

    public int getFreeSpace() {
        return freeSpace;
    }

    public int getNbOfPieces() {
        return this.pieces.size();
    }

    public boolean addPiece(Piece p) {
        if (p.getSize() <= this.freeSpace) {
            this.freeSpace -= p.getSize();
            this.pieces.add(p);
            return true;
        } else {
            return false;
        }
    }
    
    public void removePiece(int index) {
        Piece removedPiece = this.pieces.remove(index);
        this.freeSpace += removedPiece.getSize();
    }

    public Piece getPiece(int index) {
        return this.pieces.get(index);
    }    
    

    @Override
    public String toString() {
        return String.join("\n",
                super.toString() + " {",
                "   size: " + this.size,
                "   nbOfPieces: " + this.pieces.size(),
                "   freeSpace: " + this.freeSpace,
                "}"
        );
    }

    public String toStringList() {
        String str = super.toString() + " [";
        int nbOfPiece = this.pieces.size();
        for (int i = 0; i < nbOfPiece; i++) {
            str += this.pieces.get(i);
            if (i != nbOfPiece - 1) {
                str += ",";
            }
        }
        str += "]";
        return str;
    }

}
