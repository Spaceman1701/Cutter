package org.x2a.cutter.processor.javac;

import com.sun.source.tree.BlockTree;
import com.sun.source.tree.MethodTree;
import com.sun.tools.javac.code.Symbol;
import com.sun.tools.javac.model.JavacElements;
import com.sun.tools.javac.processing.JavacProcessingEnvironment;
import com.sun.tools.javac.tree.JCTree;
import com.sun.tools.javac.tree.JCTree.*;
import com.sun.tools.javac.tree.TreeMaker;
import com.sun.tools.javac.util.Context;
import com.sun.tools.javac.util.List;
import com.sun.tools.javac.util.Name;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

/**
 * Basically a javadoc'd version of the TreeMaker
 */
public class TreeFactory { //TODO: This class should handle more things and it should handle them better

    private final TreeMaker maker;
    private final JavacElements elements;

    public TreeFactory(final JavacProcessingEnvironment environment) {
        Context context = environment.getContext();
        this.maker = TreeMaker.instance(context);
        this.elements = JavacElements.instance(context);
    }

    /**
     * Creates a name in this context for the given string
     */
    public Name getName(String name) {
        return elements.getName(name);
    }

    /**
     * Creates an identity expression
     */
    public JCIdent Ident(Name name) {
        return maker.Ident(name);
    }

    public JCIdent Ident(String name) {
        return Ident(getName(name));
    }

    /**
     * Creates a basic statement for the given expression
     */
    public JCExpressionStatement Exec(JCExpression expression) {
        return maker.Exec(expression);
    }

    /**
     * Creates a method declaration
     * @param mods Modifiers (public/static/default)
     * @param name Method name
     * @param returnType Return type
     * @param typeParameters Generic type parameters
     * @param params method parameters
     * @param thrown list of thrown exceptions
     * @param body method body
     * @param defaultValue default value
     * @return the method declaration
     */
    public JCMethodDecl createMethod(JCModifiers mods,
                                     Name name,
                                     JCExpression returnType,
                                     List<JCTree.JCTypeParameter> typeParameters,
                                     List<JCTree.JCVariableDecl> params,
                                     List<JCTree.JCExpression> thrown,
                                     JCBlock body,
                                     JCExpression defaultValue) {
        return maker.MethodDef(mods, name, returnType, typeParameters, params, thrown, body, defaultValue);
    }

    /**
     *
     * @param typeArgs generic type arguments
     * @param method the method identity expression
     * @param args method arguments
     * @return the method invocation expression
     */
    public JCMethodInvocation createMethodInvocation(List<JCExpression> typeArgs, JCExpression method, List<JCExpression> args) {
        return maker.Apply(typeArgs, method, args);
    }

    /**
     * returns an empty list
     */
    public <T> List<T> List() {
        return List.<T>nil();
    }

    /**
     * Creates a JCBlock from a list of statements
     * @param flags not sure yet
     * @param statements list of statements
     * @return a new block
     */
    public JCBlock Block(long flags, List<JCStatement> statements) {
        return maker.Block(flags, statements);
    }
}
