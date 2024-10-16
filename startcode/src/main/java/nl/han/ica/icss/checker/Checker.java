package nl.han.ica.icss.checker;

import nl.han.ica.datastructures.IHANLinkedList;
import nl.han.ica.icss.ast.*;
import nl.han.ica.icss.ast.literals.ColorLiteral;
import nl.han.ica.icss.ast.literals.PercentageLiteral;
import nl.han.ica.icss.ast.literals.PixelLiteral;
import nl.han.ica.icss.ast.types.ExpressionType;

import java.nio.charset.StandardCharsets;
import java.util.HashMap;



public class Checker {

    private IHANLinkedList<HashMap<String, ExpressionType>> variableTypes;

    public void check(AST ast) {
        // variableTypes = new HANLinkedList<>();
        checkStylesheet(ast.root);
    }

    private void checkStylesheet(Stylesheet node) {
        //de reden dat het nu node 0 is omdat het voorbeeld maar 1 kan hebben
        checkStylerule((Stylerule) node.getChildren().get(0));
    }

    private void checkStylerule(Stylerule node){
        for( ASTNode child : node.getChildren()){
            if(child instanceof Declaration){
                checkDeclaration((Declaration) child);
            }
        }
    }

    private void checkDeclaration(Declaration node){
        if(node.property.name.equals("width")){
            if(!(node.expression instanceof PixelLiteral | node.expression instanceof PercentageLiteral)){
                node.setError("Property: \"Width\" has been assigned an invalid type");
            }
        }else if(node.property.name.contains("color")){
            if(!(node.expression instanceof ColorLiteral)){
                node.setError("Property: \"" + node.property.name + "\" has been assigen an invalid type");
            }
        }
    }
}
