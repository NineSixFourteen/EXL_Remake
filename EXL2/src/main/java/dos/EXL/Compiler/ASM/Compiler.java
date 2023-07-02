package dos.EXL.Compiler.ASM;


import org.objectweb.asm.ClassWriter;
import org.objectweb.asm.FieldVisitor;
import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.Opcodes;

import dos.EXL.Compiler.ASM.Interaces.MethodInterface;
import dos.EXL.Compiler.ASM.Interaces.VisitInterface;
import dos.EXL.Filer.Imports.ImportsData;
import dos.EXL.Filer.Program.ProgramData;
import dos.EXL.Filer.Program.Function.Variable;
import dos.EXL.Types.Function;
import dos.EXL.Types.Program;
import dos.EXL.Types.Tag;
import dos.EXL.Types.Lines.Field;
import dos.Util.DescriptionMaker;

import static org.objectweb.asm.Opcodes.*;

import java.util.List;
public class Compiler {

    private ProgramData PD; 
    private Program Prog;
    private ClassWriter cw;
    

    public Compiler(ProgramData PD, Program prog){
        this.PD = PD;
        this.Prog = prog;
        cw = new ClassWriter(0);  
    }

    public ClassWriter compile(){
        cw.visit(49, ACC_PUBLIC, Prog.getName() , null, "java/lang/Object", null); //TODO
        cw.visitSource("Hello.exl",null);
        createFields();
        compileCons();
        compileMain();
        compileFields();
        compileMethods();
        cw.visitEnd();
        return cw;
    }

    private void compileMain() {
        var main = Prog.getMain();
        if(main.hasValue()){
            Function Main = main.getValue();
            var mw = cw.visitMethod(Opcodes.ACC_PUBLIC + Opcodes.ACC_STATIC, "main", "([Ljava/lang/String;)V", null, null);
            var method = new MethodInterface(PD.getDataInterface("main()").getValue(), new VisitInterface(mw));
            method.compile(Main.getBody(),Main.getParams());
            mw.visitInsn(RETURN);
            method.end();
        }

    }

    private void compileCons() {
        for(Function f : Prog.getConstructors()){
            compileConstructor(f);
        }
    }

    private void compileConstructor(Function f) {
        MethodVisitor m = cw.visitMethod(0, "<init>", "()V", null, null);
        m.visitVarInsn(ALOAD, 0);
        m.visitMethodInsn(INVOKESPECIAL, "java/lang/Object", "<init>", "()V", false);
        var data = PD.getDataInterface(f.getKey(PD.getImports()).getValue()).getValue();
        data.addVariable(new Variable("", "Object", 0, 10000, 0)); // add object 0 to construct
        var method = new MethodInterface(data, new VisitInterface(m));
        method.compile(f.getBody(),f.getParams());
        m.visitInsn(RETURN);
        method.end();
    }

    private void compileMethods() {
        for(Function f : Prog.getFunctions()){
            compileFunc(f);
        }
    }

    private void compileFunc(Function f) {
        ImportsData imports = PD.getImports();
        var desc = f.getDesc(imports).getValue();
        var mw = cw.visitMethod(getOpCode(f.getTags()), f.getName(), desc, null, null);
        var method = new MethodInterface(PD.getDataInterface(f.getKey(imports).getValue()).getValue(), new VisitInterface(mw));
        method.compile(f.getBody(), f.getParams());
        method.end();
    }

    private int getOpCode(List<Tag> tags) {
        int code = 0;
        for(Tag t : tags){
            switch(t){
                case Private:
                    code += Opcodes.ACC_PRIVATE;
                    break;
                case Public:
                    code += Opcodes.ACC_PUBLIC;
                    break;
                case Static:
                    code += Opcodes.ACC_STATIC;
                    break;
            }
        }
        return code;
    }

    private void createFields() {
        for(Field f : Prog.getFields()){
            var s = DescriptionMaker.toASM(f.getType(),PD.getImports());
            FieldVisitor fieldVisitor = cw.visitField(Opcodes.ACC_PUBLIC | Opcodes.ACC_STATIC,f.getName(), s.getValue(), null, null);
            fieldVisitor.visitEnd();
        }
    }

    private void compileFields() {
        var FieldMethod = cw.visitMethod(Opcodes.ACC_STATIC, "<clinit>", "()V", null, null);
        FieldMethod.visitCode();
        var method = new MethodInterface(PD.getDataInterface("fields").getValue(), new VisitInterface(FieldMethod));
        for(Field f : Prog.getFields()){
            compileField(f, method);
        }
        FieldMethod.visitInsn(RETURN);
        method.end();
    }

    private void compileField(Field f, MethodInterface meth) {
        f.toASM(meth);
        var s = DescriptionMaker.toASM(f.getType(), PD.getImports());
        meth.getVisitor().visitFieldInsn(Opcodes.PUTSTATIC, meth.getData().getName() ,  f.getName(), s.getValue());
    }
    
}
