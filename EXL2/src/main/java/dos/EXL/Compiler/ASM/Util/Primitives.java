package dos.EXL.Compiler.ASM.Util;

public enum Primitives {
    Int, Float, Double, Long, Short, Char, Boolean, Object;

    public static Primitives getPrimitive(String s){
        switch(s){
            case "int":
                return Int;
            case "long":
                return Long;
            case "float":
                return Float;
            case "double":
                return Double;
            case "char":
                return Char;
            case "boolean":
                return Boolean;
            default:
                return Object;
        }
    }

    public static Primitives Higher(Primitives left, Primitives right){
        return left.ordinal() > right.ordinal() ? left : right;
    }
}
