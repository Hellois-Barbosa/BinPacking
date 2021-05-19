/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package POJO;

import java.util.ArrayList;
import com.google.gson.Gson;

/**
 *
 * @author Helloïs BARBOSA
 */
public class Solution {

    private ArrayList<Bin> bins;

    public Solution() {
    }

    public Solution(ArrayList<Bin> bins) {
        this.bins = bins;
    }

    public int getNumberOfBins() {
        return this.bins.size();
    }
    
    public Solution movePiece() {
        //Clone the current solution
        Gson gson = new Gson();
        Solution result = gson.fromJson(gson.toJson(this), Solution.class);
        
        //Set up exchange variables
        boolean moveDone = false;
        int nbRetry = 0;
        int retryLimit = 1000;
        
        while(!moveDone && nbRetry < retryLimit) {
            //Draw a random bin index
            int randomBinIndex = (int) (Math.random() * result.bins.size());
            int finalRandomBinIndex = randomBinIndex;

            //Draw a second random bin index different to the first one
            while (randomBinIndex == finalRandomBinIndex) {
                finalRandomBinIndex = (int) (Math.random() * result.bins.size());
            }
            
            //Get selected bin
            Bin bin = result.bins.get(randomBinIndex);
            
            //Draw random piece index
            int randomPieceIndex = (int) (Math.random() * this.bins.get(randomBinIndex).getNbOfPieces());
            
            if(moveBinPiece(randomPieceIndex, randomBinIndex, finalRandomBinIndex)) {
                moveDone = true;
                System.out.println("Move done after " + nbRetry + " retry !");
                System.out.println("Piece n° " + randomPieceIndex + " of bin n° " + randomBinIndex);
                System.out.println("Added in bin n° " + finalRandomBinIndex);
            } else {
                //If is not possible retry
                System.out.println("Retry r: " + nbRetry);
                nbRetry++;
            }
        }
        
        return result;
    }

    public Solution exchangeItems() {
        //Clone the current solution
        Gson gson = new Gson();
        Solution result = gson.fromJson(gson.toJson(this), Solution.class);

        //Set up exchange variables
        boolean exchangeDone = false;
        int nbRetry = 0;
        int retryLimit = 1000;

        //Run while exhange is not doing or retry number is not out of bounds
        while (!exchangeDone && nbRetry < retryLimit) {
            //Draw a first random bin index
            int firstRandomBinIndex = (int) (Math.random() * result.bins.size());
            int secondRandomBinIndex = firstRandomBinIndex;

            //Draw a second random bin index different to the first one
            while (firstRandomBinIndex == secondRandomBinIndex) {
                secondRandomBinIndex = (int) (Math.random() * result.bins.size());
            }
            
            //Get selected bins
            Bin firstBin = result.bins.get(firstRandomBinIndex);
            Bin secondBin = result.bins.get(secondRandomBinIndex);
            
            //Draw random pieces indexes
            int firstRandomPieceIndex = (int) (Math.random() * firstBin.getNbOfPieces());
            int secondRandomPieceIndex = (int) (Math.random() * secondBin.getNbOfPieces());
            
             //Retrieve the good ones from previous selected bins
            Piece firstPiece = firstBin.getPiece(firstRandomPieceIndex);
            Piece secondPiece = secondBin.getPiece(secondRandomPieceIndex);

            //Check if exchange is possible according to the new free space in each bins
            int newFirstBinFreeSpace = firstBin.getFreeSpace() + firstPiece.getSize();
            int newSecondBinFreeSpace = secondBin.getFreeSpace() + secondPiece.getSize();
            if (secondPiece.getSize() <= newFirstBinFreeSpace && firstPiece.getSize() <= newSecondBinFreeSpace) {
                moveBinPiece(firstRandomPieceIndex, firstRandomBinIndex, secondRandomBinIndex);
                moveBinPiece(secondRandomPieceIndex, secondRandomBinIndex, firstRandomBinIndex);
                
                exchangeDone = true;
                System.out.println("Exchange done after " + nbRetry + " retry !");
                System.out.println("Piece n° " + firstRandomPieceIndex + " of bins n° " + firstRandomBinIndex + "(size: " + firstPiece.getSize() + ")");
                System.out.println("Exchanged with");
                System.out.println("Piece n° " + secondRandomPieceIndex + " of bins n° " + secondRandomBinIndex + "(size: " + secondPiece.getSize() + ")");
                
            } else {
                //If is not possible retry
                System.out.println("Retry r: " + nbRetry);
                nbRetry++;
            }
         
//            //Retrieve the good ones from previous selected bins
//            Piece firstPiece = firstBin.getPiece(firstRandomPieceIndex);
//            Piece secondPiece = secondBin.getPiece(secondRandomPieceIndex);
//
//            //Check if exchange is possible according to the new free space in each bins
//            int newFirstBinFreeSpace = firstBin.getFreeSpace() + firstPiece.getSize();
//            int newSecondBinFreeSpace = secondBin.getFreeSpace() + secondPiece.getSize();
//            if (secondPiece.getSize() <= newFirstBinFreeSpace && firstPiece.getSize() <= newSecondBinFreeSpace) {
//                //Exchange pieces
//                firstBin.removePiece(firstRandomPieceIndex);
//                secondBin.removePiece(secondRandomPieceIndex);
//
//                firstBin.addPiece(firstPiece);
//                secondBin.addPiece(secondPiece);
//
//                exchangeDone = true;
//                System.out.println("Exchange done after " + nbRetry + " retry !");
//                System.out.println("Piece n° " + firstRandomPieceIndex + " of bins n° " + firstRandomBinIndex);
//                System.out.println("Exchanged with");
//                System.out.println("Piece n° " + secondRandomPieceIndex + " of bins n° " + secondRandomBinIndex);
//            } else {
//                //If is not possible retry
//                System.out.println("Retry r: " + nbRetry);
//                nbRetry++;
//            }
        }

        //How to get the good piece, if the piece you would like to exchange had a size of the bin it is not relevant to retrieve it
        return result;
    }
    
    private boolean moveBinPiece(int p_piece_index, int p_piece_bin_index, int p_final_bin_index) {
        //Get the piece bin
        Bin firstBin = this.bins.get(p_piece_bin_index);
        
        //Get piece reference
        Piece pieceToMove = firstBin.getPiece(p_piece_index);
        
        //Remove the piece to move from the first bin
        firstBin.removePiece(p_piece_index);
        
        //Add the piece to the final bin
        return this.bins.get(p_final_bin_index).addPiece(pieceToMove);      
    }

    @Override
    public String toString() {
        String binsStr = "";
        int nbOfBins = this.bins.size();
        for (int i = 0; i < nbOfBins; i++) {
            binsStr += "n°" + i + ": " + this.bins.get(i).toStringList();
            if (i != nbOfBins - 1) {
                binsStr += "\n";
            }
        }
        return String.join("\n",
                super.toString() + " {",
                "   bins {",
                binsStr,
                "   }",
                "}"
        );
    }
}
