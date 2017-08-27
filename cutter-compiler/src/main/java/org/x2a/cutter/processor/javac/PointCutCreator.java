package org.x2a.cutter.processor.javac;

import com.sun.tools.javac.code.Type;
import com.sun.tools.javac.tree.JCTree.*;
import com.sun.tools.javac.util.List;
import com.sun.tools.javac.util.Name;
import org.x2a.cutter.processor.javac.method.ReturningBodyCreator;
import org.x2a.cutter.processor.javac.method.VoidBodyCreator;
import org.x2a.cutter.processor.javac.method.WrapperBodyCreator;


/**
 * Creates the method for a cut method
 */
class PointCutCreator {
    private final TreeFactory factory;
    private final JCMethodDecl methodDecl;
    private final Name oldName;
    private final JCAnnotation annotation;
    private final Name clazzName;

    PointCutCreator(TreeFactory factory, Name clazzName, JCMethodDecl methodDecl, Name oldName, JCAnnotation annotation) {
        this.factory = factory;
        this.methodDecl = methodDecl;
        this.oldName = oldName;
        this.annotation = annotation;
        this.clazzName = clazzName;
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
        final JCBlock body = chooseBodyCreator().createMethodBody();
        return factory.createMethod(copyMods(), oldName, methodDecl.restype, methodDecl.typarams,
                methodDecl.params, methodDecl.thrown, body, methodDecl.defaultValue);
    }

    private JCModifiers copyMods() { //shallow copy probably good enough here
        JCModifiers original = methodDecl.mods;

        JCModifiers newMods = factory.Modifiers(original.flags);
        newMods.annotations = original.annotations;
        return newMods;
    }

    private WrapperBodyCreator chooseBodyCreator() {
        JCNewClass pointCutNewClassNode = createPointCutNewClass(methodDecl.params);
        if (methodDecl.restype.type instanceof Type.JCVoidType) {
            return new VoidBodyCreator(factory, pointCutNewClassNode, methodDecl.name, methodDecl.params);
        } else {
            return new ReturningBodyCreator(factory, pointCutNewClassNode, methodDecl.name, methodDecl.params, methodDecl.restype);
        }
    }

    private JCNewClass createPointCutNewClass(List<JCVariableDecl> params) {
        JCExpression clazz = Utils.getPointCut(annotation);
        return factory.NewClass(null, List.nil(), clazz, createPointCutArgs(params), null);
    }

    private List<JCExpression> createPointCutArgs(List<JCVariableDecl> params) {
        return List.of(createJoinPoint(), createParameterArray(params));
    }

    private JCMethodInvocation createParameterArray(List<JCVariableDecl> params) {
        JCNewArray paramArray = getParameterObjectArray(params);
        JCFieldAccess methodFieldAccess = getCutUtilsField("toParameters");
        return factory.createMethodInvocation(factory.List(), methodFieldAccess, List.of(paramArray));
    }


    private JCMethodInvocation createJoinPoint() {
        JCLiteral methodName = factory.Literal(oldName.toString());
        JCMethodInvocation clazzMethod = factory.createMethodInvocation(factory.List(), factory.Ident("getClass"), factory.List());
        JCFieldAccess clazzField = factory.FieldAccess(factory.Ident(clazzName), factory.getName("class"));

        JCFieldAccess factoryMethodField = getCutUtilsField("createJoinPoint");

        return factory.createMethodInvocation(List.nil(), factoryMethodField, List.of(clazzField, methodName));
    }

    private JCFieldAccess getCutUtilsField(String name) {
        JCFieldAccess cutUtils = factory.FieldAccess(factory.Ident("Cut"), factory.getName("Utils"));
        return factory.FieldAccess(cutUtils, factory.getName(name));
    }

    private JCNewArray getParameterObjectArray(List<JCVariableDecl> params) {
        List<JCExpression> arrayValues = factory.List();

        for (JCVariableDecl var : params) {
            JCLiteral nameLiteral = factory.Literal(var.name.toString());
            JCExpression classExpression = getVarTypeExpression(var);
            JCIdent varIdentifier = factory.Ident(var.name);

            arrayValues = arrayValues.append(nameLiteral).append(classExpression).append(varIdentifier);
        }

        return factory.ArrayInitalizer(factory.Ident("Object"), factory.List(), arrayValues);
    }


    private JCExpression getVarTypeExpression(JCVariableDecl var) {
        JCExpression typeExpression = var.vartype;
        return resolveStaticArgType(typeExpression);
    }

    private JCExpression resolveStaticArgType(JCExpression tree) { //this might work for objects too... imports could cause issue though
        return factory.FieldAccess(tree, factory.getName("class"));
    }


}
