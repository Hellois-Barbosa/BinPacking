/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package SolutionFitness;

import POJO.Bin;
import POJO.Solution;

/**
 * This heuristic must be maximized
 *
 * @author Helloïs BARBOSA
 */
public class NearlyFullBins implements SolutionFitness {

    public NearlyFullBins() {
    }

    @Override
    public int calculate(Solution p_solution) {
        int fitness = 0;
        for (int i = 0; i < p_solution.getBins().size(); i++) {
            int binPiecesSpace = p_solution.getBins().get(i).getPiecesSpace();
            fitness += (binPiecesSpace * binPiecesSpace);
        }
        return fitness;
    }

    @Override
    public int calculate(Bin p_bin_A, Bin p_bin_B) {
        return (p_bin_A.getPiecesSpace()* p_bin_A.getPiecesSpace()) + (p_bin_B.getPiecesSpace() * p_bin_B.getPiecesSpace());
    }

    @Override
    public boolean isNewSolutionIsBetter(int p_current_solution_fitness, int p_new_solution_fitness) {
        return p_new_solution_fitness > p_current_solution_fitness;
    }

}
