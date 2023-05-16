package dos.EXL.Types;

import java.util.List;


import org.javatuples.Pair;
import org.objectweb.asm.ClassWriter;
import org.objectweb.asm.MethodVisitor;

import dos.EXL.Types.Lines.CodeBlock;
import dos.EXL.Validator.Functions.ValFunctionMake;
import dos.Util.DescriptionMaker;
import dos.Util.Maybe;
import dos.Util.ValueRecords;

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

    public MethodVisitor toASM(ClassWriter cw, ValueRecords base){
        MethodVisitor mv = cw.visitMethod(0, Name, DescriptionMaker.makeFuncASM(type, params,base), null, null);
        return mv;
    }

    public Maybe<Error> validate(ValueRecords records){
        return ValFunctionMake.validate(Name, tags, params, type, body, records);
    }

}
