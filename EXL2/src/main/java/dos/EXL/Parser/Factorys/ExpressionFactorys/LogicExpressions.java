package dos.EXL.Parser.Factorys.ExpressionFactorys;

import dos.EXL.Compiler.ASM.Util.Primitives;
import dos.EXL.Types.Expression;
import dos.EXL.Types.Binary.Boolean.AndExpr;
import dos.EXL.Types.Binary.Boolean.EqExpr;
import dos.EXL.Types.Binary.Boolean.GThanEqExpr;
import dos.EXL.Types.Binary.Boolean.GThanExpr;
import dos.EXL.Types.Binary.Boolean.LThanEqExpr;
import dos.EXL.Types.Binary.Boolean.LThanExpr;
import dos.EXL.Types.Binary.Boolean.NotEqExpr;
import dos.EXL.Types.Binary.Boolean.OrExpr;
import dos.EXL.Types.Unary.NotExpr;

public class LogicExpressions {

    public Expression LTExpr(Expression left, Expression right){
        return new LThanExpr(left, right);
    }

    public Expression LTEQExpr(Expression left, Expression right){
        return new LThanEqExpr(left, right);
    }

    public Expression GTExpr(Expression left, Expression right){
        return new GThanExpr(left, right);
    }

    public Expression GTEQExpr(Expression left, Expression right){
        return new GThanEqExpr(left, right);
    }

    public Expression EQExpr(Expression left, Expression right){
        return new EqExpr(left, right);
    }

    public Expression NEQExpr(Expression left, Expression right){
        return new NotEqExpr(left, right);
    }

    public Expression NotExpr(Expression expr){
        return new NotExpr(expr);
    }
    
    public Expression ORExpr(Expression left, Expression right){
        return new OrExpr(left, right);
    }

    public Expression ANDExpr(Expression left, Expression right){
        return new AndExpr(left, right);
    }
    
}
