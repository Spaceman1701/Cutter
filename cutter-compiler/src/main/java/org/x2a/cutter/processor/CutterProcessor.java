package org.x2a.cutter.processor;

import com.sun.source.util.Trees;
import com.sun.tools.internal.xjc.reader.TypeUtil;
import com.sun.tools.javac.code.Symbol;
import com.sun.tools.javac.code.Type;
import com.sun.tools.javac.code.Types;
import com.sun.tools.javac.model.JavacElements;
import com.sun.tools.javac.processing.JavacProcessingEnvironment;
import com.sun.tools.javac.tree.JCTree;
import com.sun.tools.javac.tree.TreeMaker;
import com.sun.tools.javac.tree.TreeTranslator;
import org.x2a.cutter.annotation.Cut;
import org.x2a.cutter.annotation.RequiredAnnotations;
import org.x2a.cutter.cut.Advice;
import org.x2a.cutter.processor.javac.MethodTranslator;
import org.x2a.cutter.processor.javac.TreeFactory;

import javax.annotation.processing.*;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.*;
import javax.lang.model.type.MirroredTypeException;
import javax.lang.model.type.MirroredTypesException;
import javax.lang.model.type.TypeMirror;
import javax.tools.Diagnostic;
import java.lang.annotation.Annotation;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

@SupportedAnnotationTypes("org.x2a.cutter.annotation.Cut")
@SupportedSourceVersion(SourceVersion.RELEASE_8)
public class CutterProcessor extends AbstractProcessor {

    private JavacProcessingEnvironment javacEnv;

    private Trees trees;
    private TreeTranslator treeTranslator;

    @Override
    public void init(ProcessingEnvironment env) {
        this.processingEnv = env;
        this.javacEnv = (JavacProcessingEnvironment) env;
        this.trees = Trees.instance(javacEnv);
        TreeFactory treeFactory = new TreeFactory(javacEnv);

        treeTranslator = new MethodTranslator(treeFactory);
    }

    @Override
    public boolean process(Set<? extends TypeElement> annotations, RoundEnvironment roundEnv) {
        try {
            verifyCuts(roundEnv, createRequirementMap(roundEnv));
            if (!roundEnv.processingOver()) {
                Set<? extends Element> elements = roundEnv.getRootElements();
                for (Element e : elements) {
                    JCTree tree = (JCTree) trees.getTree(e);
                    tree.accept(treeTranslator);
                }
            }
        } catch (RuntimeException e) {
            javacEnv.getMessager().printMessage(Diagnostic.Kind.ERROR, "Cutter compile-time exception: " + e.getMessage());
            e.printStackTrace();
        }

        return false;
    }

    private RequirementMap createRequirementMap(RoundEnvironment env) {
        RequirementMap map = new RequirementMap();
        Symbol.ClassSymbol adviceBaseType = javacEnv.getElementUtils().getTypeElement("org.x2a.cutter.cut.Advice");
        for (Element e : env.getElementsAnnotatedWith(RequiredAnnotations.class)) {
            if (e.getKind() != ElementKind.CLASS) {
                throw new CutterCompileException("@RequiredAnnotations must be placed on a class!");
            }
            TypeMirror typeMirror = e.asType();
            Symbol.ClassSymbol clazzSym = (Symbol.ClassSymbol) e;
            if (!clazzSym.isSubClass(adviceBaseType, Types.instance(javacEnv.getContext()))) {
                throw new CutterCompileException("@RequiredAnnotations must be placed on an Advice class");
            }
            RequiredAnnotations annotation = e.getAnnotation(RequiredAnnotations.class);
            map.putAll(typeMirror, getValue(annotation));
        }
        return map;
    }

    public void verifyCuts(RoundEnvironment env, RequirementMap requirementMap) {
        for (Element e : env.getElementsAnnotatedWith(Cut.class)) {
            if (e.getKind() == ElementKind.METHOD) {
                Symbol.MethodSymbol methodSymbol = (Symbol.MethodSymbol) e;
                Symbol parent = methodSymbol.owner;
                if (parent.getKind() != ElementKind.CLASS) {
                    throw new CutterCompileException("Cut at " + parent.getSimpleName() + "::" + e.getSimpleName() + " is invalid. Cuts must be placed on Classes");
                }
                if (methodSymbol.getModifiers().contains(Modifier.ABSTRACT)) {
                    throw new CutterCompileException("Cut at " + parent.getSimpleName() + "::" + e.getSimpleName() + " is invalid. Cuts cannot be placed on abstract methods");
                }
                //TODO: currently impossible to find anonymous classes

                Cut cut = e.getAnnotation(Cut.class);
                TypeMirror advice = getTypeMirror(cut);
                List<? extends TypeMirror> requiredAnnotations = requirementMap.getRequiredAnnotations(advice);
                Set<TypeMirror> annotations = buildTypeMirrorSet(e.getAnnotationMirrors());
                for (TypeMirror required : requiredAnnotations) {
                    if (!annotations.contains(required)) {
                        throw new CutterCompileException("missing required annotation: " + required);
                    }
                }


            } else {
                throw new CutterCompileException(e.getSimpleName() + " is annotated with Cut, but Cut only applies to methods");
            }
        }
    }

    private Set<TypeMirror> buildTypeMirrorSet(List<? extends AnnotationMirror> annotationMirrors) {
        Set<TypeMirror> mirrors = new HashSet<>();
        for (AnnotationMirror am : annotationMirrors) {
            mirrors.add(am.getAnnotationType());
        }
        return mirrors;
    }

    private TypeMirror getTypeMirror(Cut cutProxy) {
        try {
            return javacEnv.getElementUtils().getTypeElement(cutProxy.value().getCanonicalName()).asType();
        } catch (MirroredTypeException e) {
            return e.getTypeMirror();
        }
    }
    //TODO: both these methods can be replaced by annoying logic that doesn't involve exceptions
    private List<? extends TypeMirror> getValue(RequiredAnnotations annotationProxy) {
        try {
            annotationProxy.value();
        } catch (MirroredTypesException e) {
            return e.getTypeMirrors();
        }
        return null;
    }




}
