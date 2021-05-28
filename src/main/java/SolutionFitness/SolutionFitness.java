/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package SolutionFitness;

import POJO.Bin;
import POJO.Solution;

/**
 *
 * @author Helloïs BARBOSA
 */
public interface SolutionFitness {
    
    public int calculate(Solution p_solution);
    
    public int calculate(Bin p_bin_A, Bin p_bin_B);
    
    public boolean isNewSolutionIsBetter(int p_current_solution_fitness, int p_new_solution_fitness);
    
}
