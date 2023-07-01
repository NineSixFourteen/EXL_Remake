package dos.EXL.Filer.Program;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.IntStream;
import dos.EXL.Filer.Imports.ImportsData;
import dos.EXL.Filer.Program.Function.FunctionData;
import dos.EXL.Filer.Program.Function.Variable;
import dos.EXL.Types.MyError;
import dos.EXL.Types.Tag;
import dos.EXL.Types.Errors.ErrorFactory;
import dos.Util.DescriptionMaker;
import dos.Util.Maybe;
import dos.Util.Result;
import dos.Util.Results;

public class SelfData {

    private String name;
    private List<Variable> fields; 
    private List<FunctionData> functions; 

    public SelfData(String n){
        name = n;
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

    public List<FunctionData> getFunctions() {
        return functions;
    }

    public List<String> getDescFromName(String funcName){
        return functions.stream()
                .filter(x -> x.getName().equals(funcName))
                .map( x -> x.getDesc())
                .toList();
    }

    public Result<FunctionData> getFunction(String key){
        Optional<FunctionData> func = functions.stream()
                                            .filter(fun -> fun.is(key))
                                            .findFirst();
        if(func.isEmpty())
            return Results.makeError(ErrorFactory.makeLogic("Unable to find a function with the key" + key, 0));
        return Results.makeResult(func.get());
    }

    public Maybe<MyError> addField(String name,String type){
        fields.add(new Variable(name, type, -1, -1, -1));
        return new Maybe<>();
    }

    public Maybe<MyError> addFunction(String name, String desc, String type, List<Tag> list) {
        List<String> descs = getDescFromName(name);
        for(String des : descs){
            if(des == desc){
                return new Maybe<>(ErrorFactory.makeLogic("Function with this name and desciption already exists",21));
            }
        }
        functions.add(new FunctionData(name,type,desc,list));
        return new Maybe<>();
    }

    public List<FunctionData> getFuncData(String name) {
        return functions.stream().filter(x -> x.getName().equals(name)).toList();
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
        String key = name + desc.substring(0, desc.lastIndexOf(")") + 1);
        var function = getFunction(key);
        if(function.hasError())
            return false;
        return function.getValue().getTags().stream().filter(tag -> tag == Tag.Static).findFirst().isPresent();
    }

    public Result<String> getFuncType(String name, String partialDescription, ImportsData imports) {
        List<FunctionData> datas = functions.stream()
                                    .filter(func -> func.getName().equals(name))
                                    .toList();
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

    public String getName() {
        return name;
    }
}
