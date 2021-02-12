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
public class Solution {

    private ArrayList<Bin> bins;

    public Solution() {
    }

    public Solution(ArrayList<Bin> bins) {
        this.bins = bins;
    }

    public static Solution buildNaiveSolution(ArrayList<Piece> p_pieces, int p_binSize) {
        //Initialization
        int nbOfPieces = p_pieces.size();
        ArrayList<Bin> bins = new ArrayList<>();
        bins.add(new Bin(p_binSize));

        //Continue as long as there are pieces left
        while (nbOfPieces > 0) {

            //Try to put each piece into existing bins
            for (int i = 0; i < nbOfPieces; i++) {
                boolean pieceAdded = false;
                Piece currentPiece = p_pieces.get(i);

                int nbOfBins = bins.size();
                for (int j = 0; j < nbOfBins; j++) {
                    Bin currentBin = bins.get(j);

                    //Add piece if there is space left
                    if (currentBin.addPiece(currentPiece)) {
                        pieceAdded = true;
                        break;
                    }
                }

                //If piece was not added add a new Bin and put it in
                if (!pieceAdded) {
                    bins.add(new Bin(p_binSize));
                    bins.get(bins.size() - 1).addPiece(currentPiece);
                }

                //Remove the piece from pieces list and reduce number of pieces left
                p_pieces.remove(i);
                nbOfPieces--;
            }
        }
         System.out.println("NbOfBins: " + bins.size());
            for (int i = 0; i < bins.size(); i++) {
                System.out.println("\nBIN i=" + i);
                Bin currentBin = bins.get(i);
                System.out.println("Size left: " + currentBin.getFreeSpace());
                System.out.println("NbOfPiece: " + currentBin.getNbOfPieces());
            }
        return new Solution(bins);
    }
}
