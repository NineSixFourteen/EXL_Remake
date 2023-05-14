package dos.Parser.Factorys.ExpressionFactorys;

import java.util.List;

import dos.Types.ArrayExpr;
import dos.Types.Expression;
import dos.Types.Unary.BracketExpr;
import dos.Types.Unary.ObjectDeclareExpr;

public class SymbolExpressions {
    
    public Expression BracExpr(Expression expr){
        return new BracketExpr(expr);
    }

    public Expression ArrayExpr(List<Expression> exprs){
        return new ArrayExpr(exprs);
    }

    public Expression objectExpr(String name, List<Expression> exprs){
        return new ObjectDeclareExpr(name, exprs);
    }


}
