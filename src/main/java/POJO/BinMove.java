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
public class BinMove implements Move {

    private int originPieceIndex;

    private int originBinIndex;

    private int finalBinIndex;

    private int piecesNbInOriginalBin;

    private int piecesNbInFinalBin;

    public BinMove(int originPieceIndex, int originBinIndex, int finalBinIndex, int piecesNbInOriginalBin, int piecesNbInFinalBin) {
        this.originPieceIndex = originPieceIndex;
        this.originBinIndex = originBinIndex;
        this.finalBinIndex = finalBinIndex;
        this.piecesNbInOriginalBin = piecesNbInOriginalBin;
        this.piecesNbInFinalBin = piecesNbInFinalBin;
    }

    public int getOriginPieceIndex() {
        return originPieceIndex;
    }

    public int getOriginBinIndex() {
        return originBinIndex;
    }

    public int getFinalBinIndex() {
        return finalBinIndex;
    }

    public int getPiecesNbInOriginalBin() {
        return piecesNbInOriginalBin;
    }

    public int getPiecesNbInFinalBin() {
        return piecesNbInFinalBin;
    }

    @Override
    public Move reverseMove() {
        return new BinMove(getPiecesNbInFinalBin(), getFinalBinIndex(), getOriginBinIndex(), getPiecesNbInFinalBin() + 1, getPiecesNbInOriginalBin() - 1);
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }

        if (!(obj instanceof BinMove)) {
            return false;
        }

        BinMove binMoveToCompare = (BinMove) obj;

        return binMoveToCompare.getOriginBinIndex() == this.getOriginBinIndex()
                && binMoveToCompare.getOriginPieceIndex() == this.getOriginPieceIndex()
                && binMoveToCompare.getFinalBinIndex() == this.getFinalBinIndex()
                && binMoveToCompare.getPiecesNbInFinalBin() == this.getPiecesNbInFinalBin()
                && binMoveToCompare.getPiecesNbInOriginalBin() == this.getPiecesNbInOriginalBin();
    }

}
