package dos.Parser.Util;

import java.util.ArrayList;
import java.util.List;

import org.javatuples.Pair;

import dos.Tokenizer.Types.Token;
import dos.Types.Tag;
import dos.Util.Result;

public class TagGrabber {
    
    public static Result<Pair<List<Tag>,Integer>, Error>  getClassTags(List<Token> tokens, int point){
        Result<Pair<List<Tag>,Integer>, Error> res = new Result<>();
        List<Tag> tags = new ArrayList<>();
        boolean lookingTag = true;
        while(lookingTag){
            switch(tokens.get(point++).getType()){
                case Private:
                    tags.add(Tag.Private);
                    break;
                case Static:
                    tags.add(Tag.Static);
                    break;
                case Public:
                    tags.add(Tag.Public);
                    break;
                default:
                    lookingTag = false;
                    point--; 
            }
        }
        res.setValue(new Pair<List<Tag>,Integer>(tags, point));
        return res;
    }
}
