/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package POJO;

import SolutionFitness.NearlyFullBins;
import SolutionFitness.SolutionFitness;
import java.util.ArrayList;

/**
 *
 * @author Helloïs BARBOSA
 */
public class Solution {

    private final ArrayList<Bin> bins;

    private final SolutionFitness fitnessStrategy;

    private int fitness;

    public Solution(ArrayList<Bin> bins) {
        this.bins = bins;
        this.fitnessStrategy = new NearlyFullBins();
        this.fitness = this.calcSolutionFitness();
    }

    public int getNumberOfBins() {
        return this.bins.size();
    }

    public int getFitness() {
        return this.fitness;
    }

    public ArrayList<Bin> getBins() {
        return bins;
    }

    public SolutionFitness getFitnessStrategy() {
        return fitnessStrategy;
    }

    private int calcSolutionFitness() {
        return this.fitnessStrategy.calculate(this);
    }

    public Solution movePiece(boolean p_log_on) {
        //Clone the current solution
        Solution clonedSolution = this.clone();

        //Set up exchange variables
        boolean moveDone = false;
        int nbRetry = 0;
        int retryLimit = 1000;

        while (!moveDone && nbRetry < retryLimit) {
            //Draw a random bin index
            int randomBinIndex = (int) (Math.random() * clonedSolution.getBins().size());
            int finalRandomBinIndex = randomBinIndex;

            //Draw a second random bin index different to the first one
            while (randomBinIndex == finalRandomBinIndex) {
                finalRandomBinIndex = (int) (Math.random() * clonedSolution.getBins().size());
            }

            //Get selected bin
            Bin bin = clonedSolution.getBins().get(randomBinIndex);

            //Draw random piece index
            int randomPieceIndex = (int) (Math.random() * bin.getNbOfPieces());

            if (clonedSolution.moveBinPiece(randomPieceIndex, randomBinIndex, finalRandomBinIndex)) {
                moveDone = true;

                if (p_log_on) {
                    System.out.println("\nMove done after " + (nbRetry + 1) + " retry !");
                    System.out.println("Piece n°" + randomPieceIndex + " of bin n°" + randomBinIndex);
                    System.out.println("Added in bin n°" + finalRandomBinIndex + "\n");
                }
            } else {
                //If is not possible retry
                if (p_log_on) {
                    System.out.println("Retry n°" + nbRetry);
                }
                nbRetry++;
            }
        }
        if (nbRetry == retryLimit) {
            if (p_log_on) {
                System.out.println("Ended without found any solution");
            }
        }
        return clonedSolution;
    }

    public Move movePieceMoves() {
        //Clone the current solution
        Solution clonedSolution = this.clone();

        //Varaiable to return
        BinMove binMove = null;

        //Set up exchange variables
        boolean moveDone = false;
        int nbRetry = 0;
        int retryLimit = 1000;

        while (!moveDone && nbRetry < retryLimit) {
            //Draw a random bin index
            int randomBinIndex = (int) (Math.random() * clonedSolution.getBins().size());
            int finalRandomBinIndex = randomBinIndex;

            //Draw a second random bin index different to the first one
            while (randomBinIndex == finalRandomBinIndex) {
                finalRandomBinIndex = (int) (Math.random() * clonedSolution.getBins().size());
            }

            //Get selected bin
            Bin bin = clonedSolution.getBins().get(randomBinIndex);
            Bin finalBin = clonedSolution.getBins().get(finalRandomBinIndex);

            //Draw random piece index
            int randomPieceIndex = (int) (Math.random() * bin.getNbOfPieces());

            if (clonedSolution.canMoveBinPiece(randomPieceIndex, randomBinIndex, finalRandomBinIndex)) {
                moveDone = true;
                binMove = new BinMove(randomPieceIndex, randomBinIndex, finalRandomBinIndex, bin.getNbOfPieces(), finalBin.getNbOfPieces());
            } else {
                nbRetry++;
            }
        }
        return binMove;
    }

    public Solution exchangeItems(boolean p_log_on) {
        //Clone the current solution
        Solution clonedSolution = this.clone();

        //Set up exchange variables
        boolean exchangeDone = false;
        int nbRetry = 0;
        int retryLimit = 1000;

        //Run while exhange is not doing or retry number is not out of bounds
        while (!exchangeDone && nbRetry < retryLimit) {
            //Draw a first random bin index
            int firstRandomBinIndex = (int) (Math.random() * clonedSolution.getBins().size());
            int secondRandomBinIndex = firstRandomBinIndex;

            //Draw a second random bin index different to the first one
            while (firstRandomBinIndex == secondRandomBinIndex) {
                secondRandomBinIndex = (int) (Math.random() * clonedSolution.getBins().size());
            }

            //Get selected bins
            Bin firstBin = clonedSolution.getBins().get(firstRandomBinIndex);
            Bin secondBin = clonedSolution.getBins().get(secondRandomBinIndex);

            //Draw random pieces indexes
            int firstRandomPieceIndex = (int) (Math.random() * firstBin.getNbOfPieces());
            int secondRandomPieceIndex = (int) (Math.random() * secondBin.getNbOfPieces());

            //Retrieve the good ones from previous selected bins
            Piece firstPiece = firstBin.getPiece(firstRandomPieceIndex);
            Piece secondPiece = secondBin.getPiece(secondRandomPieceIndex);

            int firstPieceSize = firstPiece.getSize();
            int secondPieceSize = secondPiece.getSize();

            //Check if exchange is possible according to the new free space in each bins
            int newFirstBinFreeSpace = firstBin.getFreeSpace() + firstPiece.getSize();
            int newSecondBinFreeSpace = secondBin.getFreeSpace() + secondPiece.getSize();
            if (firstPieceSize != secondPieceSize && secondPieceSize <= newFirstBinFreeSpace && firstPieceSize <= newSecondBinFreeSpace) {
                exchangeBinPieces(firstRandomPieceIndex, firstRandomBinIndex, secondRandomPieceIndex, secondRandomBinIndex);
                exchangeDone = true;

                if (p_log_on) {
                    System.out.println("\nExchange done after " + (nbRetry + 1) + " retry !");
                    System.out.println("Piece n°" + firstRandomPieceIndex + " of bins n°" + firstRandomBinIndex + " (size: " + firstPieceSize + ")");
                    System.out.println("Exchanged with");
                    System.out.println("Piece n°" + secondRandomPieceIndex + " of bins n°" + secondRandomBinIndex + " (size: " + secondPieceSize + ")\n");
                }
            } else {
                //If is not possible retry
                if (p_log_on) {
                    System.out.println("Retry n°" + nbRetry);
                }
                nbRetry++;
            }
        }
        return clonedSolution;
    }

    public Move exchangeItemsMoves() {
        //Clone the current solution
        Solution clonedSolution = this.clone();

        //Return variables
        BinExchange binExchange = null;

        //Set up exchange variables
        boolean exchangeDone = false;
        int nbRetry = 0;
        int retryLimit = 1000;

        //Run while exhange is not doing or retry number is not out of bounds
        while (!exchangeDone && nbRetry < retryLimit) {
            //Draw a first random bin index
            int firstRandomBinIndex = (int) (Math.random() * clonedSolution.getBins().size());
            int secondRandomBinIndex = firstRandomBinIndex;

            //Draw a second random bin index different to the first one
            while (firstRandomBinIndex == secondRandomBinIndex) {
                secondRandomBinIndex = (int) (Math.random() * clonedSolution.getBins().size());
            }

            //Get selected bins
            Bin firstBin = clonedSolution.getBins().get(firstRandomBinIndex);
            Bin secondBin = clonedSolution.getBins().get(secondRandomBinIndex);

            //Draw random pieces indexes
            int firstRandomPieceIndex = (int) (Math.random() * firstBin.getNbOfPieces());
            int secondRandomPieceIndex = (int) (Math.random() * secondBin.getNbOfPieces());

            //Retrieve the good ones from previous selected bins
            Piece firstPiece = firstBin.getPiece(firstRandomPieceIndex);
            Piece secondPiece = secondBin.getPiece(secondRandomPieceIndex);

            int firstPieceSize = firstPiece.getSize();
            int secondPieceSize = secondPiece.getSize();

            //Check if exchange is possible according to the new free space in each bins
            int newFirstBinFreeSpace = firstBin.getFreeSpace() + firstPiece.getSize();
            int newSecondBinFreeSpace = secondBin.getFreeSpace() + secondPiece.getSize();
            if (firstPieceSize != secondPieceSize && secondPieceSize <= newFirstBinFreeSpace && firstPieceSize <= newSecondBinFreeSpace) {
                binExchange = new BinExchange(new BinMove(firstRandomPieceIndex, firstRandomBinIndex, secondRandomBinIndex, firstBin.getNbOfPieces(), secondBin.getNbOfPieces()), new BinMove(secondRandomPieceIndex, secondRandomBinIndex, firstRandomBinIndex, secondBin.getNbOfPieces(), firstBin.getNbOfPieces()));
                exchangeDone = true;
            } else {
                nbRetry++;
            }
        }
        return binExchange;
    }

    public boolean moveBinPiece(int p_piece_index, int p_piece_bin_index, int p_final_bin_index) {
        //Get the piece bin
        Bin firstBin = this.bins.get(p_piece_bin_index);

        //Get the final bin
        Bin finalBin = this.bins.get(p_final_bin_index);

        boolean moveSuccessfuly = Bin.movePieceBetweenBin(p_piece_index, firstBin, finalBin);

        //Remove empty bin if the last piece was moved
        if (firstBin.isEmpty()) {
            this.bins.remove(p_piece_bin_index);
        }

        this.fitness = this.calcSolutionFitness();

        //Send if move works well or not
        return moveSuccessfuly;
    }

    public void exchangeBinPieces(int p_piece_A_index, int p_bin_index_A, int p_piece_B_index, int p_bin_index_B) {
        //Retrieve bins
        Bin firstBin = this.bins.get(p_bin_index_A);
        Bin finalBin = this.bins.get(p_bin_index_B);

        Bin.exchangePiecesBetweenBin(p_piece_A_index, firstBin, p_piece_B_index, finalBin);

        this.fitness = this.calcSolutionFitness();
    }

    private boolean canMoveBinPiece(int p_piece_index, int p_piece_bin_index, int p_final_bin_index) {
        //Get the piece bin
        Bin firstBin = this.bins.get(p_piece_bin_index);

        //Get piece reference
        Piece pieceToMove = firstBin.getPiece(p_piece_index);

        //Test if the piece can be added to the final bin
        return this.bins.get(p_final_bin_index).canAdd(pieceToMove);
    }

    public static boolean listContains(ArrayList<Solution> p_solutions, Solution p_solution_to_find) {
        boolean isContains = false;
        for (int i = 0; i < p_solutions.size(); i++) {
            if (p_solution_to_find.equals(p_solutions.get(i))) {
                isContains = true;
                break;
            }
        }
        return isContains;
    }

    public Solution clone() {
        ArrayList<Bin> copy = new ArrayList<>();
        this.bins.stream().forEach((bin) -> {
            copy.add(bin.clone());
        });
        return new Solution(copy);
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

    @Override
    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }

        if (!(obj instanceof Solution)) {
            return false;
        }

        Solution solutionToCompare = (Solution) obj;

        return this.bins.equals(solutionToCompare.bins) && this.fitnessStrategy.getClass() == solutionToCompare.fitnessStrategy.getClass() && this.fitness == solutionToCompare.fitness;
    }

}
