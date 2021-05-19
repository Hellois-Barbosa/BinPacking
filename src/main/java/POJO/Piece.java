/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package POJO;

/**
 *
 * @author Helloïs BARBOSA
 */
public class Piece {

    private final int size;

    public Piece(int size) {
        this.size = size;
    }

    public int getSize() {
        return size;
    }

    @Override
    public String toString() {
        return super.toString() + " { size: " + this.size + " }";
    }
    
    

}
