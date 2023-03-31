package dos.Types.Lines;

import dos.Types.Expression;
import dos.Types.Line;

public class ReturnLine implements Line {


    public Expression val; 

    public ReturnLine(Expression e){
        val = e;
    }

    @Override
    public void accept() {

    }

}

