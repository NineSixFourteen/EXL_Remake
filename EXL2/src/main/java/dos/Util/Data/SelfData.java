package dos.Util.Data;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import org.javatuples.Pair;

import dos.EXL.Types.MyError;
import dos.EXL.Types.Errors.ErrorFactory;
import dos.Util.DescriptionMaker;
import dos.Util.Maybe;
import dos.Util.Result;
import dos.Util.Results;

public class SelfData {

    private List<Variable> fields; 
    private List<Pair<String, FunctionData>> functions; 

    public SelfData(){
        fields = new ArrayList<>();
        functions = new ArrayList<>();
    }

    public Result<Variable> getField(String name){
        var fieldInt = IntStream.range(0, fields.size())
        .filter(i -> name.equals(fields.get(i).getName()))
        .findFirst();
        if(fieldInt.isEmpty()){
            return Results.makeError(ErrorFactory.makeLogic("Can not find variable " + name, 7));
        }
        int f = fieldInt.getAsInt();
        return Results.makeResult(fields.get(f));
    }

    public List<Pair<String, FunctionData>> getFunctions() {
        return functions;
    }

    public List<String> getDescFromName(String funcName){
        return functions.stream()
                        .filter(x -> x.getValue0().equals(funcName))
                        .map( x -> x.getValue1().getDesc())
                        .toList();
    }

    public Maybe<MyError> addField(String name,String type){
        fields.add(new Variable(name, type, -1, -1, -1));
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

    public List<FunctionData> getFuncData(String name) {
        return functions.stream().filter(x -> x.getValue0().equals(name)).map( x -> x.getValue1()).toList();
    }

    public Result<String> getFieldTy(String name) {
       var field = fields.stream()
                            .filter(fie -> fie.getName().equals(name))
                            .findFirst();
        if(field.isEmpty())
            return Results.makeError(ErrorFactory.makeLogic("Field " + name + " was not found", 0));
        return Results.makeResult(field.get().getType());
    }

    public boolean isFuncStatic(String name, String desc) {
        return false;
    }

    public Result<String> getFuncType(String name, String partialDescription, ImportsData imports) {
        List<FunctionData> datas = functions.stream()
                                    .filter(func -> func.getValue0().equals(name))
                                    .map(func -> func.getValue1()).toList();
        var function = datas.stream()
                    .filter(
                        data -> data.getDesc()
                            .substring(
                                0,
                                data.getDesc().lastIndexOf(")") + 1
                            ).equals(partialDescription)
                        )
                    .findFirst();
        if(function.isEmpty())
            return Results.makeError(ErrorFactory.makeLogic("No function called " + name + " with the description " + partialDescription + " was found " , 20));
        var desc = function.get().getDesc();
        var type = DescriptionMaker.fromASM(desc.substring(desc.lastIndexOf(")") + 1),imports );
        return type;
    }
  
    
}
