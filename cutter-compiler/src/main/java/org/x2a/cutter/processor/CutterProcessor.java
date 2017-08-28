package org.x2a.cutter.processor;

import com.sun.source.util.Trees;
import com.sun.tools.javac.code.Symbol;
import com.sun.tools.javac.model.JavacElements;
import com.sun.tools.javac.processing.JavacProcessingEnvironment;
import com.sun.tools.javac.tree.JCTree;
import com.sun.tools.javac.tree.TreeMaker;
import com.sun.tools.javac.tree.TreeTranslator;
import org.x2a.cutter.annotation.Cut;
import org.x2a.cutter.processor.javac.MethodTranslator;
import org.x2a.cutter.processor.javac.TreeFactory;

import javax.annotation.processing.*;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.Element;
import javax.lang.model.element.ElementKind;
import javax.lang.model.element.TypeElement;
import javax.tools.Diagnostic;
import java.lang.reflect.Method;
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
        TreeMaker treeMaker = TreeMaker.instance(javacEnv.getContext());
        JavacElements elements = JavacElements.instance(javacEnv.getContext());

        TreeFactory treeFactory = new TreeFactory(javacEnv);

        treeTranslator = new MethodTranslator(treeFactory);
    }

    @Override
    public boolean process(Set<? extends TypeElement> annotations, RoundEnvironment roundEnv) {
        try {
            verifyCuts(roundEnv);
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

    public void verifyCuts(RoundEnvironment env) {
        for (Element e : env.getElementsAnnotatedWith(Cut.class)) {
            if (e.getKind() == ElementKind.METHOD) {
                Symbol.MethodSymbol methodSymbol = (Symbol.MethodSymbol) e;
                Symbol parent = methodSymbol.owner;
                if (parent.getKind() != ElementKind.CLASS) {
                    throw new CutterCompileException("Cut at " + parent.getSimpleName() + "::" + e.getSimpleName() + " is invalid. Cuts must be placed on Classes");
                }
                //TODO: currently impossible to find anonymous classes
            } else {
                throw new CutterCompileException(e.getSimpleName() + " is annotated with Cut, but Cut only applies to methods");
            }
        }
    }



}
