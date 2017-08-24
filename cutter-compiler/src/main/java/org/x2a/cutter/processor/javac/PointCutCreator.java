package org.x2a.cutter.processor.javac;

import com.sun.tools.javac.code.Flags;
import com.sun.tools.javac.code.Type;
import com.sun.tools.javac.tree.JCTree;
import com.sun.tools.javac.tree.JCTree.*;
import com.sun.tools.javac.util.List;
import org.x2a.cutter.processor.javac.TreeFactory;
import org.x2a.cutter.processor.javac.Utils;

import javax.naming.Name;

/**
 * Creates the method for a cut method
 */
class PointCutCreator {

    private static final String POINT_CUT_VAR_NAME = "pointCut";

    private final TreeFactory factory;
    private final JCMethodDecl methodDecl;
    private final Name oldName;
    private final JCAnnotation annotation;

    private List<JCStatement> statements;

    PointCutCreator(TreeFactory factory, JCMethodDecl methodDecl, Name oldName, JCAnnotation annotation) {
        this.factory = factory;
        this.methodDecl = methodDecl;
        this.oldName = oldName;
        this.annotation = annotation;

        statements = factory.List();
    }

    private void appendStatement(JCStatement statement) {
        this.statements = statements.append(statement);
    }

    /*
    None void methods:
        PointCut pointCut = new PointCut();
        if (pointCut.before()) {
            return pointCut.after(__wrapped_methodName());
        }
        return pointCut.onSkip();

     void methods:
        PointCut pointCut = new PointCut();
        if (pointCut.before()) {
            __wrapped_methodName()
            pointCut.after(null);
        }
        pointCut.onSkip();
     */
    JCMethodDecl createMethod() {
        final JCBlock body;
        if (methodDecl.restype.type instanceof Type.JCVoidType) {
            body = createVoidMethodBody();
        } else {
            body = createReturningMethodBody();
        }
        return null;
    }

    private JCBlock createVoidMethodBody() {
        statements.append(createPointCutVar());
        return factory.Block(statements);
    }

    private JCBlock createReturningMethodBody() {
        statements.append(createPointCutVar());
        return factory.Block(0, statements);
    }

    private JCStatement createPointCutVar() {
        JCNewClass newClass = createPointCutNewClass();
        return factory.VariableDeclaration(factory.Modifiers(Flags.FINAL), factory.getName(POINT_CUT_VAR_NAME), newClass.clazz, newClass);
    }

    private JCNewClass createPointCutNewClass() {
        JCIdent clazz = Utils.getPointCut(annotation);
        return factory.NewClass(null, List.nil(), clazz, List.nil(), null);
    }



}
