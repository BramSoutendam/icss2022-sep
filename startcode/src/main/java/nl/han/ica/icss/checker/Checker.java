package nl.han.ica.icss.checker;

import nl.han.ica.datastructures.IHANLinkedList;
import nl.han.ica.icss.ast.*;
import nl.han.ica.icss.ast.literals.*;
import nl.han.ica.icss.ast.types.ExpressionType;

import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Arrays;
import nl.han.ica.datastructures.HANLinkedList;



public class Checker {
    private HashMap<String, ExpressionType> addibleToVariableTypes = new java.util.HashMap<>();
    private IHANLinkedList<HashMap<String, ExpressionType>> variableTypes;
    private final String[] validProperties = {"width", "color", "background-color"};

    public void check(AST ast) {
        variableTypes = new HANLinkedList<>();
        checkStylesheet(ast.root);
    }

    private void checkStylesheet(Stylesheet node) {
        for(int i = 0; i < node.getChildren().size(); i++){
            if(node.getChildren().get(i) instanceof VariableAssignment){
                checkVariableAssignment((VariableAssignment) node.getChildren().get(i));
            }
        }
        variableTypes.addFirst(addibleToVariableTypes);
        for(int i = 0; i < node.getChildren().size(); i++){
            if(node.getChildren().get(i) instanceof Stylerule){
                checkStylerule((Stylerule) node.getChildren().get(i));
            }
        }
    }

    private void checkStylerule(Stylerule node){
        for( ASTNode child : node.getChildren()){
            if(child instanceof Declaration){
                checkDeclaration((Declaration) child);
            }
        }
    }

    private void checkDeclaration(Declaration node){
        //checks if the property is even valid
        if(!Arrays.stream(validProperties).anyMatch(node.property.name::equals)){
            node.setError("Property: \"" + node.property.name + "\" was given an invalid name" );
        }else {
            //performs type-specific checks
            if (node.property.name.equals("width")) {
                if(node.expression instanceof VariableReference){
                    VariableReference variableReference = (VariableReference) node.expression;
                    if(!(isVarThisType(variableReference, ExpressionType.PIXEL)|isVarThisType(variableReference, ExpressionType.PERCENTAGE))){
                        node.setError("Property: \"Width\" has been assigned an variable invalid type");
                    }
                }else if (!(node.expression instanceof PixelLiteral | node.expression instanceof PercentageLiteral)) {
                    node.setError("Property: \"Width\" has been assigned an invalid type");
                }
            } else if (node.property.name.contains("color")) {
                if (!(node.expression instanceof ColorLiteral)) {
                    node.setError("Property: \"" + node.property.name + "\" has been assigned an invalid type");
                }
            }
        }
    }

    private void checkVariableAssignment(VariableAssignment variableAssignment) {
        if(variableAssignment.expression instanceof BoolLiteral){
            addibleToVariableTypes.put(variableAssignment.name.name, ExpressionType.BOOL);
        } else if (variableAssignment.expression instanceof PixelLiteral) {
            addibleToVariableTypes.put(variableAssignment.name.name, ExpressionType.PIXEL);
        }else if (variableAssignment.expression instanceof PercentageLiteral) {
            addibleToVariableTypes.put(variableAssignment.name.name, ExpressionType.PERCENTAGE);
        }else if (variableAssignment.expression instanceof ScalarLiteral) {
            addibleToVariableTypes.put(variableAssignment.name.name, ExpressionType.SCALAR);
        }else if (variableAssignment.expression instanceof ColorLiteral) {
            addibleToVariableTypes.put(variableAssignment.name.name, ExpressionType.COLOR);
        }
    }

    private Boolean isVarThisType(VariableReference varRef, ExpressionType desiredType){
        for(int i = 0; i < variableTypes.getSize(); i++){
            if(variableTypes.getFirst().containsKey(varRef.name)){
                if(variableTypes.getFirst().get(varRef.name) == desiredType){
                    return true;
                }
            }
        }
        return false;
    }
}
