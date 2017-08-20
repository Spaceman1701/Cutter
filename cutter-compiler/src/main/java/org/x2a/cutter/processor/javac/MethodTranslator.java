package org.x2a.cutter.processor.javac;

import com.sun.source.tree.Tree;
import com.sun.tools.javac.model.JavacElements;
import com.sun.tools.javac.tree.JCTree;
import com.sun.tools.javac.tree.JCTree.*;
import com.sun.tools.javac.tree.TreeMaker;
import com.sun.tools.javac.tree.TreeTranslator;
import com.sun.tools.javac.util.List;
import com.sun.tools.javac.util.Name;
import org.x2a.cutter.annotation.Cut;

public class MethodTranslator extends TreeTranslator {

    private final TreeFactory factory;

    public MethodTranslator(TreeFactory factory) {
        this.factory = factory;
    }


    @Override
    public void visitClassDef(JCTree.JCClassDecl classDec) {
        super.visitClassDef(classDec);
        for (JCTree tree : classDec.getMembers()) {
            if (tree.getKind() == Tree.Kind.METHOD) {
               JCTree.JCMethodDecl methodDecl = (JCTree.JCMethodDecl) tree;
               if (Utils.isAnnotatedWith(methodDecl, Cut.class)) {
                   Name oldName = methodDecl.name;
                   methodDecl.name = factory.getName("__wrapped__" + oldName.toString());


                   JCBlock newBody = createMethodBody(methodDecl.name);
                   JCMethodDecl newMethod =
                           factory.createMethod(methodDecl.mods, oldName, methodDecl.restype, methodDecl.typarams,
                                   methodDecl.params, methodDecl.thrown, newBody, methodDecl.defaultValue);
                   classDec.defs = classDec.defs.append(newMethod);
               }
            }
        }
        result = classDec;
    }

    private JCBlock createMethodBody(Name name) {
        List<JCStatement> statements = factory.List();
        JCMethodInvocation methodInvocation =
                factory.createMethodInvocation(List.nil(), factory.Ident(name), List.nil());
        statements = statements.append(factory.Exec(methodInvocation));
        return factory.Block(0, statements);
    }
}
