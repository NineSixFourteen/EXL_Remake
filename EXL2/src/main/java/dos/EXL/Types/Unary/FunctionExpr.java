package dos.EXL.Types.Unary;

import java.util.List;

import org.objectweb.asm.Label;
import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.Opcodes;

import dos.EXL.Compiler.ASM.Interaces.DataInterface;
import dos.EXL.Compiler.ASM.Interaces.MethodInterface;
import dos.EXL.Compiler.ASM.Util.Primitives;
import dos.EXL.Types.Expression;
import dos.EXL.Types.MyError;
import dos.EXL.Types.Binary.Boolean.BoolExpr;
import dos.EXL.Types.Errors.ErrorFactory;
import dos.Util.DescriptionMaker;
import dos.Util.Maybe;
import dos.Util.Result;
import dos.Util.Results;
public class FunctionExpr implements BoolExpr{
    
    public FunctionExpr(String n, List<Expression> p){
        name = n;
        params = p; 
    }

    String name;
    List<Expression> params; 
    
    @Override
    public void accept() {
        
    }

    @Override
    public String makeString() {
        String res = name + "(";
        for(Expression exp : params){
            res += exp.makeString() + ", ";
        }
        if(params.size() > 0) 
            res = res.substring(0, res.length() -2);
        res += ")";
        return res;
    }

    @Override
    public Maybe<MyError> validate(DataInterface visitor, int line){
        var descriptions = visitor.getDescriptionsFromName(name);
        if(descriptions.size() == 0)
            return new Maybe<>(ErrorFactory.makeLogic("Unable to find any functions with the name " + name, 12));
        var descriptionMaybe = DescriptionMaker.partial(params, visitor,line);
        if(descriptionMaybe.hasError())
            return new Maybe<MyError>(descriptionMaybe.getError());
        var type = visitor.getType(name, descriptionMaybe.getValue());
        if(type.hasError())
            return new Maybe<>(type.getError());
        for(Expression param : params){
            var x = param.validate(visitor,line);
            if(x.hasValue())
                return x;
        }
        return new Maybe<>();
    }

    @Override
    public void toASM(MethodInterface visitor,Primitives type) {
        for(Expression param : params){
            visitor.push(param, Primitives.getPrimitive(param.getType(visitor.getData(),visitor.getLineNumber()).getValue()));
        }
        DataInterface data = visitor.getData(); 
        visitor.doFunc(
            data.isStatic(name, DescriptionMaker.partial(params, visitor.getData(),visitor.getLineNumber()).getValue()),
            data.getName(),
            name,
            getDesc(data,visitor.getLineNumber()).getValue());
    }

    public Result<String> getDesc(DataInterface visitor,int line){
        var val = validate(visitor,line);
        if(val.hasValue())
            return Results.makeError(val.getValue()); 
        var descriptionMaybe = DescriptionMaker.partial(params, visitor,line);
        var type = visitor.getType(name, descriptionMaybe.getValue());
        return Results.makeResult(descriptionMaybe.getValue() + DescriptionMaker.toASM(type.getValue(), visitor.getImports()).getValue()); 
    }

    @Override
    public Result<String> getType(DataInterface visitor, int line) {
        var val = validate(visitor,line);
        if(val.hasValue())
            return Results.makeError(val.getValue()); 
        var descriptionMaybe = DescriptionMaker.partial(params, visitor,line);
        var type = visitor.getType(name, descriptionMaybe.getValue());
        return type;
    }

    public String getName() {
        return name;
    };

    public List<Expression> getParams() {
        return params;
    }

    public Result<String> getPDesc(DataInterface visitor, int line) {
        return DescriptionMaker.partial(params, visitor,line);

    }

    @Override
    public void pushInverse(MethodInterface visit, Label start, Label end) {
        toASM(visit, Primitives.Boolean);
        MethodVisitor visitor = visit.getVisitor();
        visitor.visitJumpInsn(Opcodes.IF_ICMPNE, end);
    }

    @Override
    public void push(MethodInterface visit, Label start, Label end) {
        toASM(visit, Primitives.Boolean);
        MethodVisitor visitor = visit.getVisitor();
        visitor.visitJumpInsn(Opcodes.IF_ICMPEQ, start);
    }

    @Override
    public boolean isOr() {
        return false;
    }
    
}
