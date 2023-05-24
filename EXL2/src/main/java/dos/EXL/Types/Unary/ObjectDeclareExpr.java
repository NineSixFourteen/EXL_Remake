package dos.EXL.Types.Unary;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.javatuples.Pair;

import dos.EXL.Types.Expression;
import dos.Util.DescriptionMaker;
import dos.Util.Maybe;
import dos.Util.Result;
import dos.Util.Results;
import dos.Util.ValueRecords;

public class ObjectDeclareExpr implements Expression {

    String objName; 
    List<Expression> params; 

    public ObjectDeclareExpr(String name, List<Expression> exprs){
        objName = name;
        params = exprs;
    }

    @Override
    public void accept() {

    }

    @Override
    public String makeString() {
        String res = "new " + objName + "(";
        for(Expression exp : params){
            res += exp.makeString() + ", ";
        }
        res = res.substring(0, res.length() -2);
        res += ")";
        return res;
    }
    @Override
    public Maybe<Error> validate(ValueRecords records) {
        var desc = records.getConstuctors(objName);
        if(desc.hasError()){
            return new Maybe<Error>(desc.getError());
        }
        Error err; 
        List<Pair<String,String>> paramTypes = new ArrayList<>();
        for(Expression e : params){
            var type = e.getType(records);
            if(type.hasError()){
                err = type.getError();
            } else {
                paramTypes.add(new Pair<String,String>("", type.getValue()));
            }
        };
        var desM = DescriptionMaker.makeFuncASM(objName, paramTypes, records);
        if(desM.hasError()){
            return new Maybe<Error>(desM.getError());
        }
        String des = desM.getValue();
        var descs = desc.getValue();
        for(String de : descs){
            if(de.equals(des)){
                return new Maybe<>();
            }
        }
        return new Maybe<Error>(new Error("There is no constructor that has the description of " + des));
    }

    @Override
    public void toASM() {

    }

    @Override
    public Result<String,Error> getType(ValueRecords records) {
        var val = validate(records);
        if(val.hasValue()){
            return Results.makeError(val.getValue());
        }
        var x = records.getFullImport(objName);
        return x;
    }

}
