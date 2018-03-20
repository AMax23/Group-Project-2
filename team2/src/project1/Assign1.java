package project1;

import org.eclipse.jdt.core.dom.ASTNode;
import java.io.FileNotFoundException;
import java.io.IOException;

public class Assign1 {
	
	public static void main (String [] args) throws FileNotFoundException, IOException {
	
		/** ENTER TARGET DIRECTORY, TARGET TYPE, AND SOURCE DIRECTORY MANUALLY
		 	Uncomment the following and enter the specified strings: **/
//		String pathname = "C:\Users\teale\Documents\School\SENG 300\TestCode";  // TEALE				
//		String pathname = "/Users/ahmed/Downloads/test7";						// AHMED			
//		String [] sources = {"C:\\Users\\teale\\Documents\\eclipse-java-oxygen-2-win32-x86_64\\eclipse\\plugins"};	// TEALE
//		String [] sources = {"/Users/ahmed/Downloads/jar"};															// AHMED
//		String targetType = "Time";
		
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
		String targetType = uc.getTargetType();	
											
		counter = new TypeCounter();
		int decCount = counter.countDec(cu, targetType);
		int refCount = counter.countRef(cu, targetType);

		System.out.printf("\nType: %2s\t\tDeclarations found: %1d\tReferences found: %d \n",targetType,decCount,refCount);


	}
}