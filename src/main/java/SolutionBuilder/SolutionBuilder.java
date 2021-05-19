/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package SolutionBuilder;

import POJO.Piece;
import POJO.Solution;
import java.util.ArrayList;

/**
 *
 * @author Helloïs BARBOSA
 */
public interface SolutionBuilder {
    
    /**
     * @param p_pieces bunch of piece to process
     * @param p_bin_size bin size for the problem
     * @return The associated heuristic solution
     */
    public Solution build(ArrayList<Piece> p_pieces, int p_bin_size);
    
}
