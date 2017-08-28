package org.x2a.cutter.processor.javac;

import com.sun.tools.javac.code.Symbol;
import com.sun.tools.javac.code.TypeTag;
import com.sun.tools.javac.model.JavacElements;
import com.sun.tools.javac.processing.JavacProcessingEnvironment;
import com.sun.tools.javac.tree.JCTree;
import com.sun.tools.javac.tree.JCTree.*;
import com.sun.tools.javac.tree.TreeMaker;
import com.sun.tools.javac.util.Context;
import com.sun.tools.javac.util.List;
import com.sun.tools.javac.util.Name;

/**
 * Basically a javadoc'd version of the TreeMaker -- also exposes some environment methods
 */
//FIXME: Refactor this
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

    public JCIdent Ident(Symbol symbol) {
        return maker.Ident(symbol);
    }

    public JCLiteral Null() {
        return maker.Literal(TypeTag.BOT, null);
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


    /**
     * Creates a new Class expression
     * @param encl not entirely sure.. maybe for inner classes
     * @param typeargs type arguments
     * @param clazz class expression (ex: class identity expression)
     * @param args constructor args
     * @param classDecl Not sure... maybe for anonymous class decs
     * @return
     */
    public JCNewClass NewClass(JCExpression encl, List<JCExpression> typeargs, JCExpression clazz, List<JCExpression> args, JCClassDecl classDecl) {
        return maker.NewClass(encl, typeargs, clazz, args, classDecl);
    }

    /**
     * Create a variable or field assignment
     * @param to the expression to which is being assigned
     * @param val the value to assign
     * @return the assignment expression
     */
    public JCAssign Assign(JCExpression to, JCExpression val) {
        return maker.Assign(to, val);
    }

    /**
     * Create a conditional expression
     * @param condition the condition expression
     * @param thenExpression the then expression
     * @param elseExpression the else expression
     * @return a conditional expression
     */
    public JCConditional Conditional(JCExpression condition, JCExpression thenExpression, JCExpression elseExpression) {
        return maker.Conditional(condition, thenExpression, elseExpression);
    }

    public JCIf If(JCExpression condition, JCStatement thenStmt, JCStatement elseStmt) {
        return maker.If(condition, thenStmt, elseStmt);
    }

    public JCSkip Skip() {
        return maker.Skip();
    }

    /**
     *
     * @param mods mods
     * @param name name
     * @param type var type
     * @param init init to
     * @return variable declaration
     */
    public JCVariableDecl VariableDeclaration(JCModifiers mods, Name name, JCExpression type, JCExpression init) {
        return maker.VarDef(mods, name, type, init);
    }

    /**
     * Create a field accessor (used for methods too)
     * @param selected the object from which the field is selected
     * @param name the field name
     */
    public JCFieldAccess FieldAccess(JCExpression selected, Name name) {
        return maker.Select(selected, name);
    }



    public JCNewArray ArrayInitalizer(JCExpression type, List<JCExpression> dims, List<JCExpression> elements) {
        return maker.NewArray(type, dims, elements);
    }

    public JCLiteral Literal(Object o) {
        return maker.Literal(o);
    }

    public JCArrayAccess ArrayAccess(JCExpression array, JCExpression index) {
        return maker.Indexed(array, index);
    }

    public JCTypeCast TypeCast(JCTree type, JCExpression expression) {
        return maker.TypeCast(type, expression);
    }

    public JCReturn Return(JCExpression expression) {
        return maker.Return(expression);
    }

    public JCModifiers Modifiers(long flags) {
        return maker.Modifiers(flags);
    }
}
