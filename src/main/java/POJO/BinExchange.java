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
public class BinExchange implements Move {
    
    private final BinMove firstMove;
    
    private final BinMove secondMove;
    
    public BinExchange(BinMove firstMove, BinMove secondMove) {
        this.firstMove = firstMove;
        this.secondMove = secondMove;
    }
    
    @Override
    public Move reverseMove() {
        return new BinExchange(this.secondMove, this.firstMove);
    }
    
    public BinMove getFirstMove() {
        return firstMove;
    }
    
    public BinMove getSecondMove() {
        return secondMove;
    }
    
    @Override
    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        
        if (!(obj instanceof BinExchange)) {
            return false;
        }
        
        BinExchange binExchangeToCompare = (BinExchange) obj;
        
        return (binExchangeToCompare.getFirstMove().equals(this.getFirstMove())
                && binExchangeToCompare.getSecondMove().equals(this.getSecondMove()))
                || (binExchangeToCompare.getFirstMove().equals(this.getSecondMove())
                && binExchangeToCompare.getSecondMove().equals(this.getFirstMove()));
    }
    
}
