package nl.han.ica.icss.parser;

import java.util.Stack;


import nl.han.ica.datastructures.IHANStack;
import nl.han.ica.icss.ast.*;
import nl.han.ica.icss.ast.literals.*;
import nl.han.ica.icss.ast.operations.AddOperation;
import nl.han.ica.icss.ast.operations.MultiplyOperation;
import nl.han.ica.icss.ast.operations.SubtractOperation;
import nl.han.ica.icss.ast.selectors.ClassSelector;
import nl.han.ica.icss.ast.selectors.IdSelector;
import nl.han.ica.icss.ast.selectors.TagSelector;
import nl.han.ica.datastructures.HANStack;
//temp import fix
//import nl.han.ica.icss.ast.Stylesheet;
//import nl.han.ica.icss.parser.ICSSBaseListener;
//import nl.han.ica.icss.ast.AST;
//import nl.han.ica.icss.ast.ASTNode;
//import nl.han.ica.icss.parser.ICSSParser;
//import nl.han.ica.icss.ast.Stylerule;


/**
 * This class extracts the ICSS Abstract Syntax Tree from the Antlr Parse tree.
 */
public class ASTListener extends ICSSBaseListener {
	
	//Accumulator attributes:
	private AST ast;

	//Use this to keep track of the parent nodes when recursively traversing the ast
	private IHANStack<ASTNode> currentContainer;

	public ASTListener() {
		ast = new AST();
		currentContainer = new HANStack<>();
	}
    public AST getAST() {
        return ast;
    }

    @Override
    public void enterStylesheet(ICSSParser.StylesheetContext ctx){
        Stylesheet stylesheet = new Stylesheet();
        currentContainer.push(stylesheet);
    }

    @Override
    public void exitStylesheet(ICSSParser.StylesheetContext ctx){
        Stylesheet sheet = (Stylesheet) currentContainer.pop();
        ast.root = sheet;
    }

    @Override
    public void enterStylerule(ICSSParser.StyleruleContext ctx){
        Stylerule rule = new Stylerule();
        currentContainer.push(rule);
    }

    @Override
    public void exitStylerule(ICSSParser.StyleruleContext ctx){
        Stylerule rule = (Stylerule) currentContainer.pop();
        currentContainer.peek().addChild(rule);
    }


    @Override
    public void enterTagSelector(ICSSParser.TagSelectorContext ctx){
        TagSelector selector = new TagSelector(ctx.getText());
        currentContainer.push(selector);
    }

    @Override
    public void exitTagSelector(ICSSParser.TagSelectorContext ctx){
        TagSelector selector = (TagSelector) currentContainer.pop();
        currentContainer.peek().addChild(selector);
    }

    @Override
    public void enterIdSelector(ICSSParser.IdSelectorContext ctx){
        IdSelector selector = new IdSelector(ctx.getText());
        currentContainer.push(selector);
    }

    @Override
    public void exitIdSelector(ICSSParser.IdSelectorContext ctx){
        IdSelector selector = (IdSelector) currentContainer.pop();
        currentContainer.peek().addChild(selector);
    }

    @Override
    public void enterClassSelector(ICSSParser.ClassSelectorContext ctx){
        ClassSelector selector = new ClassSelector(ctx.getText());
        currentContainer.push(selector);
    }

    @Override
    public void exitClassSelector(ICSSParser.ClassSelectorContext ctx){
        ClassSelector selector = (ClassSelector) currentContainer.pop();
        currentContainer.peek().addChild(selector);
    }

    @Override
    public void enterDeclarations(ICSSParser.DeclarationsContext ctx){
        Declaration declaration = new Declaration(ctx.getText());
        currentContainer.push(declaration);
    }

    @Override
    public void exitDeclarations(ICSSParser.DeclarationsContext ctx){
        Declaration declaration = (Declaration) currentContainer.pop();
        currentContainer.peek().addChild(declaration);
    }

    @Override
    public void enterProperty(ICSSParser.PropertyContext ctx) {
        PropertyName property = new PropertyName(ctx.getText());
        currentContainer.push(property);
    }

    @Override
    public void exitProperty(ICSSParser.PropertyContext ctx) {
        PropertyName property = (PropertyName) currentContainer.pop();
        currentContainer.peek().addChild(property);
    }

    @Override
    public void enterValue(ICSSParser.ValueContext ctx) {
        VariableReference var = new VariableReference(ctx.getText());
        currentContainer.push(var);
    }

    @Override
    public void exitValue(ICSSParser.ValueContext ctx) {
        VariableReference var = (VariableReference) currentContainer.pop();
        currentContainer.peek().addChild(var);
    }
}