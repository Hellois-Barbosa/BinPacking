/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package SolutionFitness;

/**
 *
 * @author Helloïs BARBOSA
 */
public interface ExtremizedFunction {
    
    public boolean isNewSolutionIsBetter(int p_previous_solution_fitness, int p_new_solution_fitness);
    
}
