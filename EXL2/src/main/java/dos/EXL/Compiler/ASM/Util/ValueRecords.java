package dos.EXL.Compiler.Util;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.stream.IntStream;

import org.javatuples.Triplet;

public class ValueRecords {

    /* Class responsible for keeping tracks of what differn't "values" are. 
        Values would be 
            Variables(Name, Type, Memory Location)
            Imports Long and short version
            Fileds(Name, Type, Memory Location)
    */ 

    private List<String> varNames; 
    private List<String> varTypes; 
    private List<Integer> memoryLocation; 
    private List<Boolean> isField; 
    private HashMap<String, String> imports;

    public ValueRecords(){
        varNames = new ArrayList<>();
        varTypes = new ArrayList<>();
        memoryLocation = new ArrayList<>();
        isField = new ArrayList<>();
        imports = new HashMap<>();
    }

    // No check if exists as sould of been done during validation stage
    public Triplet<String,String,Integer> getVar(String name){
        var ind = IntStream.range(0, varNames.size())
        .filter(i -> name.equals(varNames.get(i)))
        .findFirst();
        int i = ind.getAsInt();
        return new Triplet<String,String,Integer>(varNames.get(i), varTypes.get(i), memoryLocation.get(i));
    }

    public boolean isField(String name){
        var ind = IntStream.range(0, varNames.size())
        .filter(i -> name.equals(varNames.get(i)))
        .findFirst();
        int i = ind.getAsInt();
        return isField.get(i);
    }

    public String getFullImport(String impor){
        return imports.get(impor);
    }
    
    
}
