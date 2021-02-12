/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package POJO;

import java.io.FileNotFoundException;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;

/**
 *
 * @author Helloïs BARBOSA
 */
public class Problem {

    private int binsSize = 0;
    private final ArrayList<Piece> pieces;
    private ArrayList<Solution> solutions;

    public Problem(String filePath) throws FileNotFoundException, URISyntaxException, Exception {
        //Parse file
        HashMap<String, Object> parsedFile = FileParser.parse(filePath);

        //Initialize pieces
        this.pieces = (ArrayList<Piece>) parsedFile.get("pieces");
        this.pieces.sort((o1, o2) -> o2.getSize() - o1.getSize());

        //Initialize others parameters
        this.binsSize = (int) parsedFile.get("binCapacity");
        this.solutions = new ArrayList<>();
        this.solutions.add(Solution.buildNaiveSolution(new ArrayList<Piece>(this.pieces), this.binsSize));
    }

    

    @Override
    public String toString() {
        return String.join("\n",
                super.toString() + " {",
                "   binsSize: " + this.binsSize,
                "   nbOfPieces: " + this.pieces.size(),
                "   nbOfSolutions: " + this.solutions.size(),
                "}"
        );
    }

}
