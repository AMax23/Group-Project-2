package project1;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import org.eclipse.jdt.core.dom.AST;
import org.eclipse.jdt.core.dom.ASTNode;
import org.eclipse.jdt.core.dom.ASTParser;
import org.eclipse.jdt.core.dom.CompilationUnit;

public class TreeBuilder {

/**	 
 **	 Parameters: The pathname of the users directory which contains code to parse.
 **  Convert the contents of all java files in the directory to one string.
 **	 Return: The string of code from the directory.
 **/
	public String filesToString(String pathname) throws IOException, FileNotFoundException {
		
		StringBuilder sb = new StringBuilder();	
		File directory = new File(pathname);
		File[] allFiles = directory.listFiles();
		
		
		for (File f : allFiles) {						
			String fileName = f.getName().toLowerCase();

			if (f.isFile() && fileName.endsWith(".java")) {
				BufferedReader reader = null;

				try {
					reader = new BufferedReader(new FileReader(f));		
					String aLine;

					while ((aLine = reader.readLine()) != null) {		
						sb.append(aLine);								
						sb.append(System.lineSeparator());				
					}
				}
				finally {
					reader.close();										
				}
			}
		}
		return sb.toString();											
	}

/**	 
 **	 Parameters: The string of code to parse, all parameters needed to set up bindings, unitname, and environment
 **  Build a tree out of the user's code 
 **	 Return: The starting node
 **/
	public ASTNode makeSyntaxTree(char[] sourceCode,String[] classpath, String[] sources, String unitName ) {

		ASTParser parser = ASTParser.newParser(AST.JLS9);
		parser.setSource(sourceCode);
		parser.setKind(ASTParser.K_COMPILATION_UNIT);
		parser.setResolveBindings(true);
		parser.setBindingsRecovery(true);
		parser.setUnitName(unitName);			
		parser.setEnvironment(classpath, sources, new String[] {"UTF-8"}, true);

		final CompilationUnit cu = (CompilationUnit) parser.createAST(null);

		return cu;
	}
}