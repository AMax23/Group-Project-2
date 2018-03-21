package project1;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Enumeration;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;
import java.util.zip.ZipEntry;

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
		
		if (allFiles == null) return null;
	
		
		for (File f : allFiles) {
			String fileName = f.getName().toLowerCase();
			// If is directory, parse files within it recursively
			if	(f.isDirectory()){				
				sb.append(filesToString(f.getAbsolutePath()));
			}
			// If is jar file, parse files within it
			if (f.getName().endsWith(".jar")) {
				sb = parseJar(f,pathname,sb);
			}
			// If is java file, read file and append to StringBuilder
			if (f.isFile() && fileName.endsWith(".java")) {
				sb = readJavaFile(f,sb);
			}
		}
		return sb.toString();											
	}
	
	/**	 
	 **	 Reads through Java file and adds each line into StringBuilder object
	 **/
	public StringBuilder readJavaFile (File javaFile, StringBuilder sb) throws IOException {
		BufferedReader reader = null;
		try {
			reader = new BufferedReader(new FileReader(javaFile));		
			String aLine;

			while ((aLine = reader.readLine()) != null) {		
				sb.append(aLine);								
				sb.append(System.lineSeparator());				
			}
		} finally {
			reader.close();										
		}
		return sb;
	}
	
	/**	 
	 **	 Runs through elements in Jar file and will read any Java files into the StringBuilder object
	 **/
	public StringBuilder parseJar(File jarFile, String dirPath, StringBuilder sb) throws IOException {
		// Create JarFile object and check for entries
		JarFile jFile = new JarFile(jarFile);
		Enumeration<JarEntry> jEntries = jFile.entries();
		
		// Loop through entries
		while(jEntries.hasMoreElements()) {
			
			// Find java files to read into StringBuilder
			ZipEntry jarElem = jEntries.nextElement();
			String jarName = jarElem.getName();
			if (jarName.endsWith(".java")) {
				// Convert jar entry into java file
				InputStream input = jFile.getInputStream(jarElem);
				String javaFilePath = dirPath + "\\temp\\" + jarName;
				Files.copy(input, Paths.get(javaFilePath));
				
				// Read file
				File javaFile = new File(javaFilePath);
				sb = readJavaFile(javaFile,sb);
			}
		}
		jFile.close();
		return sb;
	}

/**	 
 **	 Parameters: The string of code to parse, all parameters needed to set up bindings, unitname, and environment
 **  Build a tree out of the user's code 
 **	 Return: The starting node
 **/
	public ASTNode makeSyntaxTree(char[] sourceCode, String[] classpath, String[] sources, String unitName ) {

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
