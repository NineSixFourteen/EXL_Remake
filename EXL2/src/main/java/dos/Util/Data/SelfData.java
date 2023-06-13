package dos.Util.Data;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import org.javatuples.Pair;

import dos.EXL.Types.MyError;
import dos.EXL.Types.Errors.ErrorFactory;
import dos.Util.Maybe;
import dos.Util.Result;
import dos.Util.Results;

public class SelfData {

    private List<String> fieldNames; 
    private List<String> fieldTypes;
    private List<Pair<String, FunctionData>> functions; 

    public SelfData(){
        fieldNames = new ArrayList<>();
        fieldTypes = new ArrayList<>();
        functions = new ArrayList<>();
    }

    public Result<Pair<String,String>> getField(String name){
        var fieldInt = IntStream.range(0, fieldNames.size())
        .filter(i -> name.equals(fieldNames.get(i)))
        .findFirst();
        if(fieldInt.isEmpty()){
            return Results.makeError(ErrorFactory.makeLogic("Can not find variable " + name, 7));
        }
        int f = fieldInt.getAsInt();
        return Results.makeResult(new Pair<>(fieldNames.get(f), fieldTypes.get(f)));
    }

    public List<Pair<String, FunctionData>> getFunctions() {
        return functions;
    }

    public List<String> getDescFromName(String funcName){
        return functions.stream().filter(x -> x.getValue0().equals(funcName)).map( x -> x.getValue1().getDesc()).collect(Collectors.toList());
    }

    public Maybe<MyError> addField(String name,String type){
        List<String> fields = this.fieldNames.stream().filter(x -> x.equals(name)).toList();
        if(fields.size() > 0)
            return new Maybe<>(ErrorFactory.makeLogic("Duplicate variable name used " + name, 22));
        if(!basicType(type)){

        }
        this.fieldNames.add(name);
        this.fieldTypes.add(type);
        return new Maybe<>();
    }

    public Maybe<MyError> addFunction(String name, String desc) {
        List<String> descs = getDescFromName(name);
        for(String des : descs){
            if(des == desc){
                return new Maybe<>(ErrorFactory.makeLogic("Function with this name and desciption already exists",21));
            }
        }
        functions.add(new Pair<>(name,new FunctionData(desc,List.of())));
        return new Maybe<>();
    }

    
    private boolean basicType(String type) {
        switch(type){
            case "String":
            case "long":
            case "double":
            case "short":
            case "int":
            case "float":
            case "char":
            case "boolean":
                return true;
            default:
                return false;
        }
    }


    public List<String> getFieldNames() {
        return fieldNames;
    }


    public List<FunctionData> getFuncData(String name) {
        return functions.stream().filter(x -> x.getValue0().equals(name)).map( x -> x.getValue1()).toList();
    }

    public Result<String> getFieldTy(String name, String fieldName) {
        return null;
    }

    public boolean isFuncStatic(String name, String desc) {
        return false;
    }

    public Result<String> getFuncType(String name, String partialDescription) {
        return null;
    }
  
    
}
