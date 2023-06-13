package dos.EXL.Types.Unary;

import java.util.ArrayList;
import java.util.List;

import org.javatuples.Pair;

import dos.EXL.Compiler.ASM.Util.Primitives;
import dos.EXL.Types.Expression;
import dos.EXL.Types.MyError;
import dos.EXL.Types.Errors.ErrorFactory;
import dos.Util.DescriptionMaker;
import dos.Util.Maybe;
import dos.Util.Result;
import dos.Util.Results;
import dos.Util.Interaces.MethodInterface;
import dos.Util.Interaces.DataInterface;

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
        if(params.size() > 0)
            res = res.substring(0, res.length() -2);
        res += ")";
        return res;
    }
    @Override
    public Maybe<MyError> validate(DataInterface visitor, int line) {
        var desc = visitor.getConstuctors(objName);
        if(desc.hasError()){
            return new Maybe<MyError>(desc.getError());
        }
        List<Pair<String,String>> paramTypes = new ArrayList<>();
        for(Expression e : params){
            var type = e.getType(visitor,line);
            if(type.hasError()){
                return new Maybe<>(type.getError());
            } else {
                paramTypes.add(new Pair<String,String>(type.getValue(), ""));
            }
        };
        var desM = DescriptionMaker.makeFuncASM(objName, paramTypes, visitor.getImports());
        if(desM.hasError()){
            return new Maybe<>(desM.getError());
        }
        String des = desM.getValue();
        var descs = desc.getValue();
        for(String de : descs){
            if(de.equals(des)){
                return new Maybe<>();
            }
        }
        return new Maybe<>(ErrorFactory.makeLogic("Could not find the constructor " + makeString() + " matching the description in " + objName, 6));
    }

    @Override
    public void toASM(MethodInterface visitor,Primitives type, int line) {

    }

    @Override
    public Result<String> getType(DataInterface visitor, int line) {
        var val = validate(visitor,line);
        if(val.hasValue()){
            return Results.makeError(val.getValue());
        }
        return Results.makeResult(objName);
    }

}
