package dos.Parser.Builders.ExpressionFactorys;

import dos.Types.Expression;
import dos.Types.Binary.Boolean.AndExpr;
import dos.Types.Binary.Boolean.EqExpr;
import dos.Types.Binary.Boolean.GThanEqExpr;
import dos.Types.Binary.Boolean.GThanExpr;
import dos.Types.Binary.Boolean.LThanEqExpr;
import dos.Types.Binary.Boolean.LThanExpr;
import dos.Types.Binary.Boolean.NotEqExpr;
import dos.Types.Binary.Boolean.OrExpr;
import dos.Types.Unary.NotExpr;

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
