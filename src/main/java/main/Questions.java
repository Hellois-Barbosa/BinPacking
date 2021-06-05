/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package main;

import POJO.Problem;
import POJO.Solution;
import SolutionBuilder.FirstFitDecreasing;
import SolutionBuilder.FirstFitRandom;
import SolutionBuilder.OnePiecePerBin;
import SolutionBuilder.SolutionBuilder;

/**
 *
 * @author Helloïs BARBOSA
 */
public class Questions {

    public static void q2() throws Exception {
        System.out.println("\n----------> START Q2 <----------");

        String str;
        for (int i = 0; i < 13; i++) {
            str = "sample_";
            if (i < 10) {
                str += "0";
            }
            str += i + ".txt";

            System.out.println("\nProblem file name: " + str);
            Problem currentProblem = new Problem(str, new FirstFitDecreasing());

            System.out.println(currentProblem.getSolution().toString());

        }

        System.out.println("\n----------> END Q2 <----------\n");
    }

    public static void q3() throws Exception {
        System.out.println("\n----------> START Q3 <----------\n");

        SolutionBuilder ortoolsBuildStrategy = new FirstFitDecreasing();

        System.out.println("Q3-a:");
        long execTime = System.currentTimeMillis();
        Ortools.solve(new Problem("sample_00.txt", ortoolsBuildStrategy));
        execTime = System.currentTimeMillis() - execTime;
        System.out.println("Solved in " + execTime + " ms");

        System.out.println("\nQ3-b:");
        System.out.println("WARNING: La résolution du problème du fichier sample_00bis.txt va démarrer et prendra environ 10 minutes !");
        execTime = System.currentTimeMillis();
        Ortools.solve(new Problem("sample_00bis.txt", ortoolsBuildStrategy));
        execTime = System.currentTimeMillis() - execTime;
        System.out.println("Executed in " + execTime + "ms");

        System.out.println("\n----------> END Q3 <----------\n");
    }

    public static void q4() throws Exception {
        System.out.println("\n----------> START Q4 <----------\n");
        Problem p;

        System.out.println("Q4-a:");
        p = new Problem("sample_00.txt", new OnePiecePerBin());
        System.out.println(p.getSolution().toString());

        System.out.println("\nQ4-b:");
        p = new Problem("sample_00.txt", new FirstFitRandom());
        System.out.println(p.getSolution().toString());

        System.out.println("\n----------> END Q4 <----------\n");
    }

    public static void q5() throws Exception {
        System.out.println("\n----------> START Q5 <----------\n");

        Problem p = new Problem("sample_00.txt", new FirstFitDecreasing());
        Solution s = p.getSolution();

        System.out.println("Q5-a:");

        System.out.println("Initial solution:");
        System.out.println(s.toString() + "\n");

        Solution newSolution = s.movePiece(true);
        System.out.println("Solution after move:");
        System.out.println(newSolution.toString());

        System.out.println("\nQ5-b:");

        System.out.println("Initial solution:");
        System.out.println(s.toString() + "\n");

        newSolution = s.exchangeItems(true);
        System.out.println("Solution after exchange:");
        System.out.println(newSolution.toString());

        System.out.println("\n----------> END Q5 <----------\n");
    }

    public static void q6() throws Exception {
        System.out.println("\n----------> START Q6 <----------");

        String str;
        for (int i = 0; i < 13; i++) {
            str = "sample_";
            if (i < 10) {
                str += "0";
            }
            str += i + ".txt";

            System.out.println("\nProblem file name: " + str);

            Problem currentProblem = new Problem(str, new FirstFitRandom());

            long execTime = System.currentTimeMillis();
            Solution result = currentProblem.simulatedAnnealing(currentProblem.getSolution(), 100, 0.0001, 100, 0.95);
            execTime = System.currentTimeMillis() - execTime;

            System.out.println("Solved in " + execTime + " ms");
            System.out.println("Initial solution bins: " + currentProblem.getSolution().getNumberOfBins());
            System.out.println("Final solution bins: " + result.getNumberOfBins());
        }

        System.out.println("\n----------> END Q6 <----------\n");
    }

    public static void q7() throws Exception {
        System.out.println("\n----------> START Q7 <----------");

        String str;
        for (int i = 0; i < 13; i++) {
            str = "sample_";
            if (i < 10) {
                str += "0";
            }
            str += i + ".txt";

            System.out.println("\nProblem file name: " + str);

            Problem currentProblem = new Problem(str, new FirstFitRandom());

            long execTime = System.currentTimeMillis();
            Solution result = Problem.tabuSearch(currentProblem.getSolution(), 500, 200, 3);
            execTime = System.currentTimeMillis() - execTime;

            System.out.println("Solved in " + execTime + " ms");
            System.out.println("Initial solution bins: " + currentProblem.getSolution().getNumberOfBins());
            System.out.println("Final solution bins: " + result.getNumberOfBins());
        }

        System.out.println("\n----------> END Q7 <----------\n");
    }
}
