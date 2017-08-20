package org.x2a.cutter.processor.javac;

import com.sun.tools.javac.code.Symbol;
import com.sun.tools.javac.model.JavacElements;
import com.sun.tools.javac.processing.JavacProcessingEnvironment;
import com.sun.tools.javac.tree.JCTree;
import com.sun.tools.javac.tree.JCTree.*;
import com.sun.tools.javac.tree.TreeMaker;
import com.sun.tools.javac.util.List;
import com.sun.tools.javac.util.Name;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

public class TreeFactory { //TODO: This class should handle more things and it should handle them better

    private final TreeMaker maker;
    private final JavacElements elements;
    private final JavacProcessingEnvironment environment;

    public TreeFactory(final TreeMaker maker, final JavacElements elements, final JavacProcessingEnvironment environment) {
        this.maker = maker;
        this.elements = elements;
        this.environment = environment;
    }

    public Name getName(String name) {
        return elements.getName(name);
    }

    public JCIdent Ident(Name name, Symbol symbol) {
        try {
            Constructor<JCIdent> identConstructor = JCIdent.class.getDeclaredConstructor(Name.class, Symbol.class);

            return identConstructor.newInstance(name, symbol);
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
        return null; //FIXME: this is an antipattern
    }

    public <T> List<T> List() {
        return List.<T>nil();
    }
}
