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

    public int getPiecesSpace() {
        return this.size - this.freeSpace;
    }

    public boolean isEmpty() {
        if (this.getNbOfPieces() == 0) {
            return true;
        } else {
            return false;
        }
    }

    public int getFreeSpace() {
        return freeSpace;
    }

    public int getNbOfPieces() {
        return this.pieces.size();
    }

    public boolean canAdd(Piece p) {
        if (p.getSize() <= this.freeSpace) {
            return true;
        } else {
            return false;
        }
    }

    public boolean addPiece(Piece p) {
        if (canAdd(p)) {
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
        str += ", freespace: " + this.freeSpace;
        return str;
    }

    public Bin clone() {
        Bin newBin = new Bin(this.size);
        this.pieces.stream().forEach((piece) -> {
            newBin.addPiece(piece.clone());
        });
        return newBin;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }

        if (!(obj instanceof Bin)) {
            return false;
        }

        Bin binToCompare = (Bin) obj;

        return this.pieces.equals(binToCompare.pieces) && this.size == binToCompare.size;
    }

    public static boolean movePieceBetweenBin(int p_piece_index, Bin p_origin_bin, Bin p_final_bin) {
        //Get piece reference
        Piece pieceToMove = p_origin_bin.getPiece(p_piece_index);

        //Add the piece to the final bin if possible and keep the result
        boolean moveSuccessfuly = p_final_bin.addPiece(pieceToMove);

        //Remove the piece to move from the first bin if move is possible
        if (moveSuccessfuly) {
            p_origin_bin.removePiece(p_piece_index);
        }

        //Send if move works well or not
        return moveSuccessfuly;
    }

    public static void exchangePiecesBetweenBin(int p_piece_A_index, Bin p_bin_A, int p_piece_B_index, Bin p_bin_B) {
        //Retrieve pieces
        Piece pieceA = p_bin_A.getPiece(p_piece_A_index);
        Piece pieceB = p_bin_B.getPiece(p_piece_B_index);

        //Remove pieces from their original bins
        p_bin_A.removePiece(p_piece_A_index);
        p_bin_B.removePiece(p_piece_B_index);

        //Insert pieces in their new bins
        p_bin_A.addPiece(pieceB);
        p_bin_B.addPiece(pieceA);
    }

}
