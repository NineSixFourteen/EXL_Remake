package dos.EXL.Types.Unary;

import java.util.List;
import java.util.stream.Collectors;

import org.javatuples.Pair;

import dos.EXL.Types.Expression;
import dos.Util.DescriptionMaker;
import dos.Util.Maybe;
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
        var des = DescriptionMaker.makeFuncASM(records.getFullImport(objName).getValue(), 
                                                params.stream().map(x -> new Pair<>(x.getType(records),"")).collect(Collectors.toList()
                                                ), records);
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
    public String getType(ValueRecords records) {
        var x = records.getFullImport(objName);
        return x.hasError() ? x.getError().getMessage() : x.getValue();
    }

}
