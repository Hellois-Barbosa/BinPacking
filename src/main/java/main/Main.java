/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package main;

import SolutionBuilder.OneBinByPiece;

/**
 *
 * @author Helloïs BARBOSA
 */
public class Main {

    /**
     * @param args the command line arguments
     * @throws java.lang.Exception
     */
    public static void main(String[] args) throws Exception {
        //Problem problem = new Problem("binpack1d_001.txt");
        //Problem problem = new Problem("pieceGreaterThanBinCapacity.txt");
        
        //Questions.q2();
        
        //Questions.q3();
        
        Questions.q4();

        /*
        String str = "";
        for (int i = 0; i < 13; i++) {
            System.out.println("PROBLEM i=" + i);
            str = "sample_";
            if (i < 10) {
                str += "0";
            }
            str += i + ".txt";
            
            System.out.println("Problem file name: " + str);
            
            System.out.println("\n");
            
            Problem problem = new Problem(str);
            System.out.println(problem);
            System.out.println("Best solution: " + problem.getBestSolution().getNumberOfBins());

            System.out.println("\n\n\nEXCHANGE ITEMS SOLUTION");

            Solution exchangedItemSolution = problem.exchangeItems();
            System.out.println(exchangedItemSolution);

            System.out.println("\n\n\nMOVE ITEM SOLUTION");

            Solution moveItemSolution = problem.movePiece();
            System.out.println(moveItemSolution);

            System.out.println("\n\n\n");
            
        }
         */
    }
}
