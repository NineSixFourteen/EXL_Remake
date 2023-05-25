package dos.Util.InfoClasses;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.javatuples.Pair;

import dos.Util.Maybe;
import dos.Util.Result;
import dos.Util.Results;

public class ClassData {

    List<FunctionData> constructors; 
    List<Pair<String, FunctionData>> functions; 
    HashMap<String, String> fields; // Key = Field Name, Value = Field Type 

    public ClassData(){
        functions = new ArrayList<>();
        fields = new HashMap<>();
    }

    public List<FunctionData> getFunctionsFromName(String name){
        List<FunctionData> funcs = new ArrayList<>();
        for(Pair<String, FunctionData> func : functions){
            if(func.getValue0().equals(name)){
                funcs.add(func.getValue1());
            }
        } 
        return funcs;
    }

    public List<FunctionData> getConstructors(){
        return constructors;
    }

    public Result<String,Error> getFieldType(String name){
        String s = fields.get(name);
        return s != null ? Results.makeResult(s): 
                    Results.makeError(new Error("Could not find type for field in " + name));
    }

    public void addFunc(String name, FunctionData data){
        functions.add(new Pair<String,FunctionData>(name,data));
    }

    public void addField(String name, String type){
        fields.put(name, type);
    }



}
