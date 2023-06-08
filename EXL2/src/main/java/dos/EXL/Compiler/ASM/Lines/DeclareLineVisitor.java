package dos.EXL.Compiler.ASM.Lines;

import dos.EXL.Types.Expression;
import dos.Util.Interaces.DataInterface;

public class DeclareLineVisitor {

    public static void visit(String name, String type, Expression value, DataInterface visitor){
        switch (type) {
            case "int":
                visitor.addVariable(name, type);
            case "boolean":
            case "long":
            case "double":
            case "float":
            case "char":
            case "short":
                break;
            case "String":
            default:
                break;
        }
    }
    
}
