package dos.EXL.Types;

import java.util.List;


import org.javatuples.Pair;
import org.objectweb.asm.ClassWriter;
import org.objectweb.asm.MethodVisitor;

import dos.EXL.Types.Lines.CodeBlock;
import dos.EXL.Validator.Functions.ValFunctionMake;
import dos.Util.DescriptionMaker;
import dos.Util.Maybe;
import dos.Util.Result;
import dos.Util.Results;
import dos.Util.Interaces.MethodInterface;
import dos.Util.Interaces.VisitInterface;
import dos.Util.Interaces.DataInterface;
import static org.objectweb.asm.Opcodes.*;

import dos.Util.Data.ImportsData;
import dos.Util.Data.Records;
import dos.Util.Data.SelfData;
import dos.Util.Data.VariableData;

public class Function {
    
    String Name;
    List<Tag> tags;
    List<Pair<String, String>> params;  
    String type;
    CodeBlock body; 

    public Function(String n, List<Tag> t, String ty, CodeBlock c, List<Pair<String,String>> p){
        Name = n;
        tags = t;
        type = ty;
        params = p;
        body = c;
    }

    public String makeString(){
        StringBuilder sb = new StringBuilder();
        for(Tag t : tags){
            sb.append(t.name().toLowerCase()).append(" ");
        }
        sb.append(type).append(" ").append(Name).append("(");
        if(params.size() > 0){
            for(int i = 0; i < params.size();i++){
                sb.append(params.get(i).getValue1()).append(" ").append(params.get(i).getValue0()).append(", ");
            }
            sb.delete(sb.length() - 2, sb.length()).append(")").append("{\n");
        } else {
            sb.append("){\n");
        }
        for(Line l : body.getLines()){
            sb.append(l.makeString(1));
        }
        sb.append("}");
        return sb.toString();
    }

    public Result<MethodVisitor> toASM(ClassWriter cw, Records records){
        var maybeDesc = DescriptionMaker.makeFuncASM(type, params, records.getImports());
        if(maybeDesc.hasError())
            return Results.makeError(maybeDesc.getError());
        MethodVisitor mv = cw.visitMethod(0, Name, maybeDesc.getValue(), null, null);
        DataInterface visitor = new DataInterface(Name, new ImportsData(), new SelfData(), new VariableData() );
        MethodInterface method = new MethodInterface(visitor, new VisitInterface(mv));
        for(Line l : body.getLines()){
            l.toASM(method);
        }
        return Results.makeResult(mv);
    }

    public Maybe<MyError> validate(DataInterface visitor, int line){
        return ValFunctionMake.validate(Name, tags, params, type, body, visitor);
    }

}
