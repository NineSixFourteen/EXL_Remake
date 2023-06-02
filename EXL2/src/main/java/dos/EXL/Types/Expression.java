package dos.EXL.Types;

import dos.Util.Maybe;
import dos.Util.Result;
import dos.Util.InfoClasses.ValueRecords;

public interface Expression {
    
    public void accept();

    public String makeString();

    public Maybe<MyError> validate(ValueRecords records);

    public void toASM();

    public Result<String> getType(ValueRecords records);
}
