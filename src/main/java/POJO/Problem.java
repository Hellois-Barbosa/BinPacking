/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package POJO;

import SolutionBuilder.SolutionBuilder;
import java.io.FileNotFoundException;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.HashMap;

/**
 *
 * @author Helloïs BARBOSA
 */
public class Problem {

    private final int binsSize;
    private final ArrayList<Piece> pieces;
    private ArrayList<Solution> solutions;
    private Solution bestSolution;
    private int minBinToUse;
    private SolutionBuilder solutionBuilder;

    public Problem(String filePath, SolutionBuilder solutionBuilder) throws FileNotFoundException, URISyntaxException, Exception {
        //Parse file
        HashMap<String, Object> parsedFile = FileParser.parse(filePath);

        //Initialize pieces
        this.pieces = (ArrayList<Piece>) parsedFile.get("pieces");
//        this.pieces.sort((o1, o2) -> o2.getSize() - o1.getSize());
               
        //Initialize others parameters
        this.binsSize = (int) parsedFile.get("binCapacity");
        
        //Get minimum bin required;
        this.minBinToUse = (this.pieces.stream().reduce(0, (acc, piece) -> acc + piece.getSize(), Integer::sum) / this.binsSize) + 1;
        
        this.solutionBuilder = solutionBuilder;
        
        this.solutions = new ArrayList<>();
        
        Solution initSolution = this.solutionBuilder.build(new ArrayList<>(this.pieces), this.binsSize);
        
        this.solutions.add(initSolution);
        this.bestSolution = initSolution;
        
        /*
        this.solutions.add(Solution.firstFitDecreasingHeuristic(new ArrayList<>(this.pieces), this.binsSize));
        Solution initSolution = Solution.firstFitRandomHeuristic(new ArrayList<>(this.pieces), this.binsSize);
        Solution initSolution = Solution.OneBinByPiece(new ArrayList<>(this.pieces), this.binsSize);
        */
        
    }

    public int getBinsSize() {
        return this.binsSize;
    }

    public ArrayList<Piece> getPieces() {
        return this.pieces;
    }   

    public Solution getBestSolution() {
        return this.bestSolution;
    }

    public Solution exchangeItems() {
        return this.solutions.get(0).exchangeItems();
    }
    
    public Solution movePiece() {
        return this.solutions.get(0).movePiece();
    }

    @Override
    public String toString() {
        return String.join("\n",
                super.toString() + " {",
                "   binsSize: " + this.binsSize,
                "   minBinToUse: " + this.minBinToUse,
                "   nbOfPieces: " + this.pieces.size(),
                "   nbOfSolutions: " + this.solutions.size(),
                "}"
        );
    }

}
