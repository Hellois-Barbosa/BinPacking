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
public class OnePiecePerBin implements SolutionBuilder {

    @Override
    public Solution build(ArrayList<Piece> p_pieces, int p_bin_size) {
        //Initialization
        int nbOfPieces = p_pieces.size();
        ArrayList<Bin> bins = new ArrayList<>();

        for (int i = 0; i < nbOfPieces; i++) {
            bins.add(new Bin(p_bin_size));
            bins.get(i).addPiece(p_pieces.get(i));
        }
        return new Solution(bins);
    }

}
