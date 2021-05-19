/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package main;

import POJO.Problem;
import SolutionBuilder.FirstFitDecreasing;
import SolutionBuilder.FirstFitRandom;
import SolutionBuilder.OneBinByPiece;
import SolutionBuilder.SolutionBuilder;
/**
 *
 * @author Helloïs BARBOSA
 */
public class Questions {
    
    public static void q2() throws Exception {
         System.out.println("\n-----> START Q2 <-----\n");
         
         String str = "";
        for (int i = 0; i < 13; i++) {
            str = "sample_";
            if (i < 10) {
                str += "0";
            }
            str += i + ".txt";
            
            System.out.println("\nProblem file name: " + str);            
            Problem currentProblem = new Problem(str, new FirstFitDecreasing());
            
            System.out.println(currentProblem.getBestSolution().toString());
            
        }
         
         System.out.println("\n-----> END Q2 <-----\n");
    }

    public static void q3() throws Exception {
        System.out.println("\n-----> START Q3 <-----\n");
        
        SolutionBuilder ortoolsBuildStrategy = new FirstFitDecreasing();
        
        System.out.println("Q3-a:");
        Ortools.solve(new Problem("sample_00.txt", ortoolsBuildStrategy));

        System.out.println("Q3-b:");
        System.out.println("WARNING: La résolution du problème du fichier sample_00bis.txt va démarrer et prendra environ 10 minutes !");
        long execTime = System.currentTimeMillis();
        Ortools.solve(new Problem("sample_00bis.txt", ortoolsBuildStrategy));
        execTime = System.currentTimeMillis() - execTime;
        System.out.println("Executed in " + execTime + "ms");
        
        System.out.println("\n-----> END Q3 <-----\n");
    }

    public static void q4() throws Exception {
        System.out.println("\n-----> START Q4 <-----\n");
        Problem p;
        
        System.out.println("Q4-a:");
        p = new Problem("sample_00.txt", new OneBinByPiece());
        System.out.println(p.getBestSolution().toString());
        
        System.out.println("Q4-b:");
         p = new Problem("sample_00.txt", new FirstFitRandom());
         System.out.println(p.getBestSolution().toString());
         
        System.out.println("\n-----> END Q4 <-----\n");
    }
    
    public static void q5() throws Exception {
        System.out.println("\n-----> START Q5 <-----\n");
        
        System.out.println("Q5-a:");
        
        System.out.println("Q5-a:");
        
        System.out.println("\n-----> END Q4 <-----\n");
    }

}
