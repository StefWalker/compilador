/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Utilities;

import Triangle.AbstractSyntaxTrees.TypeDenoter;
import java.util.HashMap;

/**
 *
 * @author johel
 */
public class SelectData {
    private HashMap<String, String> data;
    private TypeDenoter type;
    
    public SelectData(TypeDenoter type){
        data = new HashMap();
        this.type = type;
    }
    public void addData(String literal){
        data.put(literal,literal);
    }
    
    public boolean exists(String literal){
        return data.containsKey(literal);
    }
    public TypeDenoter getType(){
        return type;
    }
}
