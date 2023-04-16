package dos.Parser.Builders.ExpressionFactorys;

import dos.Types.Expression;
import dos.Types.Binary.Maths.AddExpr;
import dos.Types.Binary.Maths.DivExpr;
import dos.Types.Binary.Maths.ModExpr;
import dos.Types.Binary.Maths.MulExpr;
import dos.Types.Binary.Maths.SubExpr;

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
