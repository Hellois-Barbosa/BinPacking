/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package POJO;

import java.io.File;
import java.io.FileNotFoundException;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;

/**
 *
 * @author Helloïs BARBOSA
 */
public class FileParser {

    public static HashMap<String, Object> parse(String fileName) throws FileNotFoundException, URISyntaxException, Exception {
        //Get file location and open it
        File file = new File(FileParser.class.getResource("/" + fileName).toURI());
        Scanner sc = new Scanner(file);

        //Skip first line
        sc.nextLine();

        //Get bin capacity parameter
        int binCapacity = Integer.parseInt(sc.nextLine().split(" ")[0]);

        //Get all pieces (one for each line in file)
        ArrayList<Piece> pieces = new ArrayList<>();
        while (sc.hasNextLine()) {
            Piece newPiece = new Piece(Integer.parseInt(sc.nextLine()));
            
            //All pieces must be smaller than or equals to the bin capacity. Throw if there are not.
            if(newPiece.getSize() > binCapacity)
                throw new Exception("Piece's size must be smaller than or equals to the bin capacity (here: " + binCapacity + ")");
            
            pieces.add(newPiece);
        }        

        //Build result object
        HashMap<String, Object> result = new HashMap<>();
        result.put("binCapacity", binCapacity);
        result.put("pieces", pieces);
        return result;
    }
}
