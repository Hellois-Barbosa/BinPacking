/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package SolutionBuilder;

import POJO.Bin;
import POJO.Piece;
import POJO.Solution;
import java.util.ArrayList;

/**
 *
 * @author Helloïs BARBOSA
 */
public abstract class FirstFit implements SolutionBuilder {
    
    abstract void sortList(ArrayList<Piece> p_pieces);

    @Override
    public Solution build(ArrayList<Piece> p_pieces, int p_bin_size) {
         //Initialization
        int nbOfPieces = p_pieces.size();
        
        //Sort list according to the method builder pattern children
        sortList(p_pieces);

        ArrayList<Bin> bins = new ArrayList<>();
        bins.add(new Bin(p_bin_size));

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
                bins.add(new Bin(p_bin_size));
                bins.get(nbOfBins).addPiece(currentPiece);
                nbOfBins++;
            }
        }
        return new Solution(bins);
    }
    
}
