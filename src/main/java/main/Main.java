/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package main;

import POJO.Bin;
import POJO.Piece;
import POJO.Problem;
import java.util.ArrayList;

/**
 *
 * @author Helloïs BARBOSA
 */
public class Main {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws Exception {
        // TODO code application logic here
        Piece p1 = new Piece(5);
        Piece p2 = new Piece(3);
        Piece p3 = new Piece(4);
        
        ArrayList<Piece> pieces = new ArrayList<>();
        pieces.add(p1);
        pieces.add(p2);
        Bin bin = new Bin(9, pieces);
        
        System.out.println(bin);
        
//        Problem problem = new Problem("binpack1d_001.txt");
        //Problem problem = new Problem("pieceGreaterThanBinCapacity.txt");
        Problem problem = new Problem("easySample.txt");
        System.out.println(problem);

    }
    
}
