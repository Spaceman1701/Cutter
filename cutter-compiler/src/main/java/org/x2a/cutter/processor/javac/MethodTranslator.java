package org.x2a.cutter.processor.javac;

import com.sun.source.tree.Tree;
import com.sun.tools.javac.model.JavacElements;
import com.sun.tools.javac.tree.JCTree;
import com.sun.tools.javac.tree.JCTree.*;
import com.sun.tools.javac.tree.TreeMaker;
import com.sun.tools.javac.tree.TreeTranslator;
import com.sun.tools.javac.util.List;
import com.sun.tools.javac.util.Name;

import java.util.ArrayList;


public class MethodTranslator extends TreeTranslator {

    private final TreeMaker treeMaker;
    private final JavacElements elements;
    private final TreeFactory factory;

    public MethodTranslator(TreeMaker maker, JavacElements elements, TreeFactory factory) {
        this.treeMaker = maker;
        this.elements = elements;
        this.factory = factory;
    }


    @Override
    public void visitClassDef(JCTree.JCClassDecl classDec) {
        super.visitClassDef(classDec);
        for (JCTree tree : classDec.getMembers()) {
            if (tree.getKind() == Tree.Kind.METHOD) {
               JCTree.JCMethodDecl methodDecl = (JCTree.JCMethodDecl) tree;
               if (methodDecl.getName().toString().contains("AMethod")) {
                   Name oldName = methodDecl.name;
                   methodDecl.name = elements.getName("AMethod2");


                   JCTree.JCMethodDecl newMethod = getNewMethodDeclaration(methodDecl.mods,
                           oldName, methodDecl.restype, methodDecl.typarams, methodDecl.params, methodDecl.thrown,
                           methodDecl.body, methodDecl.defaultValue);
                   classDec.defs = classDec.defs.append(newMethod);
               }
            }
        }
        result = classDec;
    }

    private JCTree.JCMethodDecl getNewMethodDeclaration(JCTree.JCModifiers mods,
                                                        Name name,
                                                        JCTree.JCExpression returnType,
                                                        List<JCTree.JCTypeParameter> parameterTypes,
                                                        List<JCTree.JCVariableDecl> params,
                                                        List<JCTree.JCExpression> thrown,
                                                        JCTree.JCBlock body,
                                                        JCTree.JCExpression defaultValue) {
        List<JCStatement> statements = factory.List();
        statements = statements.append(toStatement(newMethodCall(treeMaker.Ident(elements.getName("AMethod2")))));
        JCBlock newBody = treeMaker.Block(0, statements);
        return treeMaker.MethodDef(mods, name, returnType, parameterTypes, params, thrown, newBody, defaultValue);
    }

    private JCMethodInvocation newMethodCall(JCExpression fn) {
        return treeMaker.Apply(List.nil(), fn, List.nil());
    }

    private JCBlock newBlock(List<JCStatement> stms) {
        return treeMaker.Block(0, stms);
    }

    private JCStatement toStatement(JCExpression expression) {
        return treeMaker.Exec(expression);
    }
}
