package dos.EXL.Compiler.ASM.Lines;

import org.objectweb.asm.Label;

import dos.EXL.Types.Expression;
import dos.Util.Interaces.MethodInterface;

public class DeclareLineVisitor {

    public static void visit(String name, String type, Expression value, MethodInterface visitor, Label scopeEnd){
        switch (type) {
            case "int":  
            case "boolean":
            case "long":
            case "double":
            case "float":
            case "char":
            case "short":
                visitor.declareVariable(name, type, scopeEnd);
                break;
            case "String":
            default:
                break;
        }
    }
    
}
