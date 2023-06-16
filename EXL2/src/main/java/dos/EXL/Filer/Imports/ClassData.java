package dos.EXL.Filer.Imports;

import java.util.ArrayList;
import java.util.List;

import org.javatuples.Pair;

import dos.EXL.Filer.Program.Function.FunctionData;
import dos.EXL.Types.Errors.ErrorFactory;
import dos.Util.Result;
import dos.Util.Results;

public class ClassData {

    List<FunctionData> constructors; 
    List<FunctionData> functions; 
    List<Pair<String, String>> fields; // Key = Field Name, Value = Field Type 

    public ClassData(){
        functions = new ArrayList<>();
        fields = new ArrayList<>();
        constructors = new ArrayList<>();
    }

    public List<FunctionData> getFunctionsFromName(String name){
        List<FunctionData> funcs = new ArrayList<>();
        for(FunctionData func : functions){
            if(func.getName().equals(name)){
                funcs.add(func);
            }
        } 
        return funcs;
    }

    public List<FunctionData> getConstructors(){
        return constructors;
    }

    public Result<String> getFieldType(String name){
        var s = fields.stream().filter( x -> x.getValue0().equals(name)).map(x -> x.getValue1()).toList();
        return s.size() != 0 ? Results.makeResult(s.get(0)): 
                    Results.makeError(ErrorFactory.makeLogic("Could not find field in " + name,6));
    }

    public void addConstructor(FunctionData fd ){
        constructors.add(fd);
    }

    public void addFunc(FunctionData data){
        functions.add(data);
    }

    public void addField(String name,String type){
        fields.add(new Pair<>(name, type));
    }





}
