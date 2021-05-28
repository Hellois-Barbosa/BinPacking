/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package main;

import POJO.Problem;
import com.google.ortools.Loader;
import com.google.ortools.linearsolver.MPConstraint;
import com.google.ortools.linearsolver.MPObjective;
import com.google.ortools.linearsolver.MPSolver;
import com.google.ortools.linearsolver.MPVariable;

/**
 *
 * @author Helloïs BARBOSA
 */
public class Ortools {

    public static void solve(Problem p_problem_to_solve) throws Exception {
        //Here, number of bins is equal to the number of item because it is the maximum bin to use
        int itemsNumber = p_problem_to_solve.getPieces().size();
        int binsNumber = p_problem_to_solve.getSolution().getNumberOfBins();

        //Load ortools libraries
        Loader.loadNativeLibraries();

        //SCIP: to solve a mixed-integer programming problem (Solving constraint integer programs)
        //Create the linear solver with the SCIP backend.
        MPSolver solver = MPSolver.createSolver("SCIP");

        if (solver == null) {
            throw new Exception("Impossible to create a solver !");
        }

        /**
         * VARIABLES
         */

        // Create the variables x and y
        //x[i][j] = 1 if item i is placed in bin j or 0 else
        MPVariable[][] x = new MPVariable[itemsNumber][binsNumber];
        for (int i = 0; i < itemsNumber; ++i) {
            for (int j = 0; j < binsNumber; ++j) {
                x[i][j] = solver.makeIntVar(0, 1, "");
            }
        }

        //Then define an array of variable y with y[j] = 1 if bin j is used, 0 else.
        //At the begining, all bins were filled in with at least one item. At the end, some bins might be empty.
        MPVariable[] y = new MPVariable[binsNumber];
        for (int j = 0; j < binsNumber; ++j) {
            y[j] = solver.makeIntVar(0, 1, "");
        }
        
        //Display the number of variables before processing calculation
        System.out.println("NUMBER OF VARIABLES: " + solver.numVariables());
        
        /**
         * CONSTRAINTS
         */

        //Add out of bin size constraint (size constraint)
        for (int i = 0; i < itemsNumber; ++i) {
            MPConstraint sizeConstraint = solver.makeConstraint(1, 1, "");
            for (int j = 0; j < binsNumber; ++j) {
                sizeConstraint.setCoefficient(x[i][j], 1);
            }
        }

        //Add all items must be used constraint
        for (int j = 0; j < binsNumber; ++j) {
            MPConstraint itemUsedConstraint = solver.makeConstraint(0, Double.POSITIVE_INFINITY, "");
            itemUsedConstraint.setCoefficient(y[j], p_problem_to_solve.getBinsSize());
            for (int i = 0; i < itemsNumber; ++i) {
                itemUsedConstraint.setCoefficient(x[i][j], -p_problem_to_solve.getPieces().get(i).getSize());
            }
        }
        
        /**
         * OBJECTIVE FUNCTION
         */

        //Define objective function
        MPObjective objectiveFunction = solver.objective();
        for (int j = 0; j < binsNumber; ++j) {
            objectiveFunction.setCoefficient(y[j], 1);
        }
        objectiveFunction.setMinimization();
        
        /**
         * SOLVING
         */
        
        MPSolver.ResultStatus result = solver.solve();
        System.out.println("MINIMUM BIN USED: " + objectiveFunction.value());

    }

}
