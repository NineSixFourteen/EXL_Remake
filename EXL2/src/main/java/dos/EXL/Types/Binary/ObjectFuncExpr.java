package dos.EXL.Types.Binary;

import dos.EXL.Types.Expression;
import dos.EXL.Types.MyError;
import dos.EXL.Types.Unary.FunctionExpr;
import dos.Util.DescriptionMaker;
import dos.Util.Maybe;
import dos.Util.ValueRecords;
import dos.Util.InfoClasses.FunctionData;
import dos.Util.Result;
import dos.Util.Results;

public class ObjectFuncExpr implements Expression{

    Expression object;
    FunctionExpr func; 

    public ObjectFuncExpr(Expression left, FunctionExpr fe){
        object = left;
        func = fe;
    }
    
    @Override
    public void accept() {
        
    }

    @Override
    public String makeString() {
        return object.makeString() + "." + func.makeString();
    }

    @Override
    public Maybe<MyError> validate(ValueRecords records) {
        var leftType = object.getType(records);
        if(leftType.hasError()){
            return new Maybe<>(leftType.getError());
        }
        var classData = records.getImportInfo(leftType.getValue());
        var partialM = DescriptionMaker.partial(func.getParams(),records); 
        if(partialM.hasError()){
            return new Maybe<>(partialM.getError());
        }
        var potentialFuncs =  classData.getFunctionsFromName(func.getName());
        for(FunctionData fd : potentialFuncs){
            if(fd.getDesc().substring(0, fd.getDesc().length() -1).equals(partialM.getValue())){
                return new Maybe<>();
            }
        }
        return new Maybe<>(new MyError("Could not find function description matching " + object.makeString() + " in " + leftType.getValue()));
    }

    @Override
    public void toASM() {

    }

    @Override
    public Result<String> getType(ValueRecords records) {
        var val = validate(records);
        if(val.hasValue()){
            return Results.makeError(val.getValue());
        }
        return func.getType(records);
       
    }
}
