/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package main;

import POJO.Bin;
import POJO.Piece;
import POJO.Problem;
import POJO.Solution;
import java.util.ArrayList;

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
        //Questions.q4();
        //Questions.q5();
        //Questions.q6();
        Questions.q7();

//        ArrayList<Piece> pc = new ArrayList<>();
//        pc.add(new Piece(1));
//        pc.add(new Piece(2));
//        pc.add(new Piece(3));
//        Bin A = new Bin(10, pc);
//        
//        ArrayList<Piece> pc2 = new ArrayList<>();
//        pc2.add(new Piece(4));
//        pc2.add(new Piece(5));
//        Bin B = new Bin(10, pc2);
//        
//        ArrayList<Piece> pc3 = new ArrayList<>();
//        pc3.add(new Piece(4));
//        pc3.add(new Piece(4));
//        Bin C = new Bin(10, pc3);
//        
//        ArrayList<Bin> bins = new ArrayList<>();
//        bins.add(A);
//        bins.add(B);
//        bins.add(C);
//        
//        Solution s = new Solution(bins);
//        System.out.println(Problem.tabuSearch(s, 100, 100, 1).toString());
    }
}
