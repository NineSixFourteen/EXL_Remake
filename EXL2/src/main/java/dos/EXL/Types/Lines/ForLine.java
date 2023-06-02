package dos.EXL.Types.Lines;
import dos.EXL.Types.Expression;
import dos.EXL.Types.Line;
import dos.EXL.Validator.Misc.CodeBlockValid;
import dos.Util.IndentMaker;
import dos.EXL.Compiler.ASM.Util.ASMPass;
import dos.Util.Maybe;
import dos.Util.InfoClasses.ValueRecords;
import dos.EXL.Types.MyError;
import dos.EXL.Types.Errors.ErrorFactory;


public class ForLine implements Line {

    public ForLine(DeclarLine d, Expression b, Line l, CodeBlock ls){
        dec = d;
        bool = b;
        line = l;
        body = ls;
    }

    DeclarLine dec;
    Expression bool;
    Line line;
    CodeBlock body;

    @Override
    public void accept() {
    }

    @Override
    public String makeString(int indent) {
        String res = IndentMaker.indent(indent);
        res += "for( ";
        res += dec.makeString(0) + "; ";
        res += bool.makeString() + "; ";
        res += line.makeString(0) + "){\n";
        indent++;
        for(Line l : body.lines){
            res += l.makeString(indent); 
        }
        res += IndentMaker.indent(indent - 1) + "}\n";
        return res;
    } 
    
    @Override
    public Maybe<MyError> validate(ValueRecords records) {
        var decV = dec.validate(records);
        if(decV.hasValue()){
            return decV;
        }
        var boolT = bool.getType(records);// get Type also validates it
        if(boolT.hasError()){
            return new Maybe<>(boolT.getError());
        }
        if(!boolT.getValue().equals("boolean")){
            return new Maybe<>(ErrorFactory.makeLogic("Must be a boolean expression in the second segment of a for," + bool.makeString() + " is of type " + boolT.getValue(),11));
        }
        var lineV = line.validate(records);
        if(lineV.hasValue()){
            return lineV;
        }
        var bodyV = CodeBlockValid.validate(body,records);
        if(bodyV.hasValue()){
            return bodyV;
        }
        return new Maybe<>();
    }

    @Override
    public void toASM(ASMPass pass) {

    } 
    
    
}
