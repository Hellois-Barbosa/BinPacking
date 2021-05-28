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
//    private ArrayList<Solution> solutions;
    private Solution solution;
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

//        this.solutions = new ArrayList<>();
        this.solution = this.solutionBuilder.build(new ArrayList<>(this.pieces), this.binsSize);

//        this.solutions.add(initSolution);
//        this.bestSolution = initSolution;

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

    public Solution getSolution() {
        return this.solution;
    }

    public Solution simulatedAnnealing(Solution p_init_solution, double p_init_temperature, double p_min_temperature, int p_max_step, double p_decreasing_factor) {
        //Initialize parameters
        Solution bestSolution = p_init_solution.clone();

        Solution currentSolution = bestSolution;
        double currentTemperature = p_init_temperature;

        // Continues annealing until reaching minimum temprature
        while (currentTemperature > p_min_temperature) {

//            int badSolutionAccepted = 0;
//            int betterSolutionFind = 0;
//            int newBetterThanCurrent = 0;
//            int newLessInterestingThanAll = 0;
            for (int currentStep = 0; currentStep < p_max_step; currentStep++) {

                //Generate a neighbourhood
                Solution newSolution = generateNeighbourhood(currentSolution);

                //Reassigns best solution according to the best solution
                //Get absolute value: the fitness differences must be positive
                //Sometime, according to the fitness strategie, the fitness must be maximized or minized
                int fitnessDiff = Math.abs(newSolution.getFitness() - currentSolution.getFitness());

                //Compare the current solution and the new one
                if (currentSolution.getFitnessStrategy().isNewSolutionIsBetter(currentSolution.getFitness(), newSolution.getFitness())) {

                    //The new one is better than the current solution
                    currentSolution = newSolution;

                    //Reassigns best solution if current fitness is better than the "best fitness"
                    if (currentSolution.getFitnessStrategy().isNewSolutionIsBetter(bestSolution.getFitness(), currentSolution.getFitness())) {
//                        betterSolutionFind++;
                        bestSolution = currentSolution;
                    }
//                    else {
//                        newBetterThanCurrent++;
//                    }
                } else {
                    //Draw a random value in [0;1]
                    double p = Math.random();

                    //The new solution is less good than the current solution, let sometimes accept a badly one solution
                    if (p < Math.exp(-fitnessDiff / currentTemperature)) {
//                        badSolutionAccepted++;
                        currentSolution = newSolution;
                    }
//                    else {
//                        newLessInterestingThanAll++;
//                    }
                }
            }

//            System.out.println("\nOn a trouvé une meilleur solution " + betterSolutionFind + " fois");
//            System.out.println("On a accepté " + badSolutionAccepted + " moin(s) bonne(s) solution(s)");
//            System.out.println("On a amélioré la solution courrante " + newBetterThanCurrent + " fois");
//            System.out.println("On a eu " + newLessInterestingThanAll + " solution(s) trop mauvaise(s) (test acceptation de la température non passé)");
            //Decreases temperature, cooling phase
            currentTemperature *= p_decreasing_factor;
//            System.out.println("Decrease temperature: " + currentTemperature);
        }
        return bestSolution;
    }

    public static Solution tabuSearch(Solution p_init_solution, int p_max_step, int p_neighbours_per_step, int p_max_tabu_size) {
        //Initialize parameters
        Solution bestSolution = p_init_solution.clone();
        Solution currentSolution = p_init_solution.clone();
//        System.out.println("INIT SOLUTION fitness: " + p_init_solution.getFitness());
        ArrayList<Move> tabuList = new ArrayList<>();

        for (int i = 0; i < p_max_step; i++) {
//            System.out.println("STEP " + i);

            //Generate p_neighbour_per_step neighbourhood
            ArrayList<Move> neighbourhoodSolutions = new ArrayList<>();
            for (int j = 0; j < p_neighbours_per_step; j++) {
                Move move = generateNeighbourhoodMoves(currentSolution);
                boolean moveIsInTabuList = false;
                for (int k = 0; k < tabuList.size(); k++) {
                    if (tabuList.get(k).equals(move)) {
                        moveIsInTabuList = true;
                        //Draw again a new move
                        j--;
                        break;
                    }
                }
                if (!moveIsInTabuList) {
                    neighbourhoodSolutions.add(move);
                }
            }

            //Select the best move among all generated neigbourhood solutions
            Move bestMove = null;
            int bestDiffMoveFitness = 0;
            for (int j = 0; j < neighbourhoodSolutions.size(); j++) {
//                System.out.println("\n--------->Generated neighbour " + j + "<---------");
                Move move = neighbourhoodSolutions.get(j);

                if (bestMove == null) {
                    bestMove = move;
                } else {
                    int newFitnessDiff = -1;
                    if (move instanceof BinMove) {
                        BinMove binMove = (BinMove) move;
                        Bin originBin = currentSolution.getBins().get(binMove.getOriginBinIndex()).clone();
                        Bin finalBin = currentSolution.getBins().get(binMove.getFinalBinIndex()).clone();
                        int fitnessBeforeMove = currentSolution.getFitnessStrategy().calculate(originBin, finalBin);

                        Bin.movePieceBetweenBin(binMove.getOriginPieceIndex(), originBin, finalBin);

                        int newFitness = currentSolution.getFitnessStrategy().calculate(originBin, finalBin);

                        newFitnessDiff = newFitness - fitnessBeforeMove;

//                        System.out.println("FITNESS BEFORE MOVE: " + fitnessBeforeMove);
//                        System.out.println("FITNESS AFTER MOVE: " + newFitness);
//                        System.out.println("FITNESS DIFF MOVE: " + newFitnessDiff);

                    } else if (move instanceof BinExchange) {
                        BinExchange binExchange = (BinExchange) move;
                        Bin originBin = currentSolution.getBins().get(binExchange.getFirstMove().getOriginBinIndex()).clone();
                        Bin finalBin = currentSolution.getBins().get(binExchange.getFirstMove().getFinalBinIndex()).clone();

                        int fitnessBeforeExchange = currentSolution.getFitnessStrategy().calculate(originBin, finalBin);

                        Bin.exchangePiecesBetweenBin(binExchange.getFirstMove().getOriginPieceIndex(), originBin, binExchange.getSecondMove().getOriginPieceIndex(), finalBin);

                        int newFitness = currentSolution.getFitnessStrategy().calculate(originBin, finalBin);

                        newFitnessDiff = newFitness - fitnessBeforeExchange;

//                        System.out.println("FITNESS BEFORE EXCHANGE: " + fitnessBeforeExchange);
//                        System.out.println("FITNESS AFTER EXCHANGE: " + newFitness);
//                        System.out.println("FITNESS DIFF EXCHANGE: " + newFitnessDiff);
                    }

                    if (currentSolution.getFitnessStrategy().isNewSolutionIsBetter(bestDiffMoveFitness, newFitnessDiff)) {
                        bestDiffMoveFitness = newFitnessDiff;
                        bestMove = move;
                    }
                }
            }

            //If genereted solution is better than the current solution add reverse move in tabu list
            int nbOfBin = currentSolution.getNumberOfBins();
            int bestGeneratedSolFitness = currentSolution.getFitness() + bestDiffMoveFitness;
//            System.out.println("\nCurrent solution fitness: " + currentSolution.getFitness() + " (with " + nbOfBin + " bin(s))");
//            System.out.println("Best generated solution fitness: " + bestGeneratedSolFitness);
//            System.out.println("Best solution fitness: " + bestSolution.getFitness());

            if (currentSolution.getFitnessStrategy().isNewSolutionIsBetter(currentSolution.getFitness(), bestGeneratedSolFitness)) {
//                System.out.println("Generated solution is better than the current solution");
                if (bestMove instanceof BinMove) {
                    BinMove move = (BinMove) bestMove;
                    currentSolution.moveBinPiece(move.getOriginPieceIndex(), move.getOriginBinIndex(), move.getFinalBinIndex());
//                    System.out.println("Best move is BINMOVE");
                } else if (bestMove instanceof BinExchange) {
                    BinExchange move = (BinExchange) bestMove;
//                    System.out.println("Best move is BINEXCHANGE");
                    currentSolution.exchangeBinPieces(move.getFirstMove().getOriginPieceIndex(), move.getFirstMove().getOriginBinIndex(), move.getSecondMove().getOriginPieceIndex(), move.getSecondMove().getOriginBinIndex());
                }

                //If there is less number of bin than before, it is useless to add move to tabu list because of it is impossible to move a piece in a removed bin again
                if (nbOfBin == currentSolution.getNumberOfBins()) {
                    if(tabuList.size() >= p_max_tabu_size) {
                        tabuList.remove(0);
//                        System.out.println("Tabu list was empty: remove");
                    }
                    tabuList.add(bestMove.reverseMove());
//                    System.out.println("add reverse move to tabu list");
                }
            }

            if (currentSolution.getFitnessStrategy().isNewSolutionIsBetter(bestSolution.getFitness(), currentSolution.getFitness())) {
//                System.out.println("Best solution was updated");
                bestSolution = currentSolution.clone();
            }

            //boolean isTabuListContainsGeneratedSolution = tabuList.contains(generatedSolution);
//                System.out.println("IN TABU LIST : " + Solution.listContains(tabuList, generatedSolution));
//                //Get now the best solution
//                if (!Solution.listContains(tabuList, generatedSolution)) {
//                    if (bestNeighbourSolution != null) {
//                        System.out.println("best = new");
//                        System.out.println(bestNeighbourSolution.equals(generatedSolution));
//                        System.out.println("Best fitness: " + bestNeighbourSolution.getFitness());
////                        System.out.println("New fitness: " + generatedSolution.getFitness() + "\n");
//                        System.out.println("New solution is better than best ? " + bestNeighbourSolution.getFitnessStrategy().isNewSolutionIsBetter(bestNeighbourSolution.getFitness(), generatedSolution.getFitness()) + "\n");
//                        if (bestNeighbourSolution.getFitnessStrategy().isNewSolutionIsBetter(bestNeighbourSolution.getFitness(), generatedSolution.getFitness())) {
//                            bestNeighbourSolution = generatedSolution.clone();
//                        }
//                    } else {
//                        bestNeighbourSolution = generatedSolution.clone();
//                        System.out.println("BEST neighbourhood is now define with fitness of " + bestNeighbourSolution.getFitness());
//                    }
//                }
//        }
//        Solution best = null;
//        for (int k = 0; k < neighbourhoodSolutions.size(); k++) {
//            if (best != null) {
//                System.out.println("best = new");
//                System.out.println(best.equals(neighbourhoodSolutions.get(k)));
//                System.out.println("Best fitness: " + best.getFitness());
//                System.out.println("New fitness: " + neighbourhoodSolutions.get(k).getFitness());
//                System.out.println("Best solution is better ? " + best.getFitnessStrategy().isNewSolutionIsBetter(best.getFitness(), neighbourhoodSolutions.get(k).getFitness()) + "\n");
//                if (best.getFitnessStrategy().isNewSolutionIsBetter(best.getFitness(), neighbourhoodSolutions.get(k).getFitness())) {
//                    best = neighbourhoodSolutions.get(k).clone();
//                }
//            } else {
//                best = neighbourhoodSolutions.get(k).clone();
        }

//            int fitnessDiff = Math.abs(newSolution.getFitness() - currentSolution.getFitness());
//
//            //Compare the current solution and the new one
//            if (currentSolution.getFitnessStrategy().isNewSolutionIsBetter(bestSolution.getFitness(), newSolution.getFitness())) {
//                bestSolution = newSolution;
//            }
        return bestSolution;
    }

    private Solution generateNeighbourhood(Solution p_source_solution) {
        Solution neighbourhoodSolution;
        double random = Math.random();
        if (random > 0.5) {
            neighbourhoodSolution = p_source_solution.exchangeItems(false);
        } else {
            neighbourhoodSolution = p_source_solution.movePiece(false);
        }
        return neighbourhoodSolution;
    }

    private static Move generateNeighbourhoodMoves(Solution p_source_solution) {
        Move moves = null;
        double random = Math.random();
        if (random > 0.5) {
            moves = p_source_solution.exchangeItemsMoves();
        } else {
            moves = p_source_solution.movePieceMoves();
        }
        return moves;
    }

    @Override
    public String toString() {
        return String.join("\n",
                super.toString() + " {",
                "   binsSize: " + this.binsSize,
                "   minBinToUse: " + this.minBinToUse,
                "   nbOfPieces: " + this.pieces.size(),
                "   solution: " + this.solution.toString(),
                "}"
        );
    }

}
