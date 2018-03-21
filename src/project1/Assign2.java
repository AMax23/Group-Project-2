package project1;

import org.eclipse.jdt.core.dom.ASTNode;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;

public class Assign2 {
	
	public static void main (String [] args) throws FileNotFoundException, IOException {
	
		/** ENTER TARGET DIRECTORY, TARGET TYPE, AND SOURCE DIRECTORY MANUALLY
		 	Uncomment the following and enter the specified strings: **/			
//		String pathname = "/Users/ahmed/Downloads/testFiles/";						// AHMED			
//		String [] sources = {"/Users/ahmed/Downloads/jar"};															// AHMED
//		String targetType = "enum";
		
//		String pathname = "<ENTER TARGET DIRECTORY HERE>"; 		
//		String targetType = "<ENTER TYPE HERE>";		
//		String [] sources = {"<ENTER JAR FILES DIRECTORY HERE>"};				
								
		
		/** USER INPUT **/
		UserContact uc = new UserContact();
		String pathname = uc.getPathname();																// Ask user for directory path
		
		// Make sure that the user enters a valid directory 
		while (!uc.isValidDir(pathname)) {
			pathname = uc.getPathname();
		}
		
		String [] sources = uc.getJarFiles();															// Ask user for the directory containing their jar files 		
		
		// Make sure that the user enters a valid directory for sources
		while (!uc.isValidDir(sources[0])) {
			sources = uc.getJarFiles(); 
		}

		TreeBuilder builder = new TreeBuilder();
		String sourceString = builder.filesToString(pathname);											// Put contents of directory into one string
		String [] classpath = {pathname};																// Where the files are located
		ASTNode cu = builder.makeSyntaxTree(sourceString.toCharArray(),classpath, sources, sourceString); // Build syntax tree from the string file content

		TypeCounter counter = new TypeCounter();
											
		counter = new TypeCounter();
		
		ArrayList<TargetType> types = counter.count(cu);

		for (TargetType s: types) {
			System.out.printf("\nType: %-25s  Declarations found: %-5d References found: %-2d\n",s.getType(),s.getDec(), s.getRef());
		}

	}
}