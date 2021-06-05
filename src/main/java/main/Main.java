/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package main;

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
        //Questions.q2();
        //Questions.q3();
        //Questions.q4();
        //Questions.q5();
        //Questions.q6();
        //Questions.q7();
    }

    public static void displayRes(ArrayList<ArrayList<Integer>> p_res) {
        ArrayList<Double> var = new ArrayList<>();
        ArrayList<Double> moyenne = new ArrayList<>();
        ArrayList<Integer> min = new ArrayList<>();

        int nbOfPblm = p_res.size();
        for (int i = 0; i < nbOfPblm; i++) {
            ArrayList<Integer> currentProblemRes = p_res.get(i);

            //Calc moyenne and min
            int resPerPblm = currentProblemRes.size();
            for (int j = 0; j < resPerPblm; j++) {
                int currentResValue = currentProblemRes.get(j);
                double doubleCurrentResValue = currentResValue;

                if (i == 0) {
                    moyenne.add(doubleCurrentResValue);
                    min.add(currentResValue);
                } else {
                    double currentMoyenne = moyenne.get(j);
                    moyenne.set(j, currentMoyenne + doubleCurrentResValue);

                    if (min.get(j) > currentResValue) {
                        min.set(j, currentResValue);
                    }
                }
            }
        }

        for (int i = 0; i < moyenne.size(); i++) {
            double currentMoyenne = moyenne.get(i);
            moyenne.set(i, currentMoyenne / nbOfPblm);
        }

        for (int i = 0; i < nbOfPblm; i++) {
            ArrayList<Integer> currentProblemRes = p_res.get(i);

            //Calc moyenne and min
            int resPerPblm = currentProblemRes.size();
            for (int j = 0; j < resPerPblm; j++) {
                int currentResValue = currentProblemRes.get(j);
                double doubleCurrentResValue = currentResValue;
                double diff = doubleCurrentResValue - moyenne.get(j);

                if (i == 0) {
                    var.add(diff * diff);
                } else {
                    var.set(j, var.get(j) + (diff * diff));
                }
            }
        }

        for (int i = 0; i < var.size(); i++) {
            var.set(i, var.get(i) / nbOfPblm);
        }

        String eachResVar = "";
        String eachResAVG = "";
        String eachResMin = "";
        int index = 0;
        while (index < moyenne.size()) {
            int currentMin = min.get(index);
            double currentAVG = moyenne.get(index);
            double currentVar = var.get(index);

            eachResMin += currentMin;
            eachResVar += currentVar;
            eachResAVG += currentAVG;

            if (index != moyenne.size() - 1) {
                eachResAVG += " ";
                eachResMin += " ";
                eachResVar += " ";
            }

            index++;
        }

        System.out.println(
                "MIN: " + eachResMin);
        System.out.println(
                "AVG: " + eachResAVG);
        System.out.println(
                "VAR: " + eachResVar);
    }
}
