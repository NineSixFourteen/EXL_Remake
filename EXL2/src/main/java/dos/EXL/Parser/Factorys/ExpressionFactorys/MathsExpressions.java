package dos.EXL.Parser.Factorys.ExpressionFactorys;

import dos.EXL.Compiler.ASM.Util.Primitives;
import dos.EXL.Types.Expression;
import dos.EXL.Types.Binary.Maths.AddExpr;
import dos.EXL.Types.Binary.Maths.DivExpr;
import dos.EXL.Types.Binary.Maths.ModExpr;
import dos.EXL.Types.Binary.Maths.MulExpr;
import dos.EXL.Types.Binary.Maths.SubExpr;

public class MathsExpressions {

    public Expression addExpr(Expression left, Expression right){
        return new AddExpr(left, right);
    }

    public Expression subExpr(Expression left, Expression right){
        return new SubExpr(left, right);
    }

    public Expression divExpr(Expression left, Expression right){
        return new DivExpr(left, right);
    }

    public Expression mulExpr(Expression left, Expression right){
        return new MulExpr(left, right);
    }

    public Expression modExpr(Expression left, Expression right){
        return new ModExpr(left, right);
    }
    
}
