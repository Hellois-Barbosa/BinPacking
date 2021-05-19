/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package SolutionBuilder;

import POJO.Piece;
import java.util.ArrayList;

/**
 *
 * @author Helloïs BARBOSA
 */
public class FirstFitDecreasing extends FirstFit {

    @Override
    void sortList(ArrayList<Piece> p_pieces) {
        p_pieces.sort((o1, o2) -> o2.getSize() - o1.getSize());
    }

}
