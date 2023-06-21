package dos.EXL.Parser.Factorys.ExpressionFactorys;

import dos.EXL.Types.Expression;
import dos.EXL.Types.Binary.Boolean.AndExpr;
import dos.EXL.Types.Binary.Boolean.BoolExpr;
import dos.EXL.Types.Binary.Boolean.EqExpr;
import dos.EXL.Types.Binary.Boolean.GThanEqExpr;
import dos.EXL.Types.Binary.Boolean.GThanExpr;
import dos.EXL.Types.Binary.Boolean.LThanEqExpr;
import dos.EXL.Types.Binary.Boolean.LThanExpr;
import dos.EXL.Types.Binary.Boolean.NotEqExpr;
import dos.EXL.Types.Binary.Boolean.OrExpr;
import dos.EXL.Types.Unary.NotExpr;

public class LogicExpressions {

    public BoolExpr LTExpr(Expression left, Expression right){
        return new LThanExpr(left, right);
    }

    public BoolExpr LTEQExpr(Expression left, Expression right){
        return new LThanEqExpr(left, right);
    }

    public BoolExpr GTExpr(Expression left, Expression right){
        return new GThanExpr(left, right);
    }

    public BoolExpr GTEQExpr(Expression left, Expression right){
        return new GThanEqExpr(left, right);
    }

    public BoolExpr EQExpr(Expression left, Expression right){
        return new EqExpr(left, right);
    }

    public BoolExpr NEQExpr(Expression left, Expression right){
        return new NotEqExpr(left, right);
    }

    public BoolExpr NotExpr(Expression expr){
        return new NotExpr(expr);
    }
    
    public BoolExpr ORExpr(Expression left, Expression right){
        return new OrExpr(left, right);
    }

    public BoolExpr ANDExpr(Expression left, Expression right){
        return new AndExpr(left, right);
    }
    
}
