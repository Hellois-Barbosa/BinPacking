/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package SolutionBuilder;

import POJO.Piece;
import java.util.ArrayList;
import java.util.Collections;

/**
 *
 * @author Helloïs BARBOSA
 */
public class FirstFitRandom extends FirstFit {

    @Override
    void sortList(ArrayList<Piece> p_pieces) {
        Collections.shuffle(p_pieces);
    }

}
