package project1;

import org.eclipse.jdt.core.dom.ASTNode;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;

public class Assign2 {

	public static void main (String [] args) throws FileNotFoundException, IOException {

		/** ENTER TARGET DIRECTORY, TARGET TYPE, AND SOURCE DIRECTORY MANUALLY
		 	Uncomment the following and enter the specified strings: **/
		String BASEDIR = "C:\\Users\\jicka_000\\eclipse-workspace\\Group-Project-2\\src\\";
		
		String folderName = args[0];

		/** USER INPUT **/
		// Get pathname from console argument (!!! FOR QUICK TESTING use this instead of user input !!!)
//		String pathname = BASEDIR + folderName;
		
		// Get pathname from user input
		UserContact uc = new UserContact();
		String pathname = BASEDIR + uc.getPathname();		
		
		// Make sure that the user enters a valid directory
		while (!uc.isValidDir(pathname)) {
			pathname = BASEDIR + uc.getPathname();
		}
		
		// Initiate objects to parse and count files
		TypeCounter counter = new TypeCounter();
		TreeBuilder builder = new TreeBuilder();
		String [] sources = {pathname};		
		String [] classpath = {pathname};														// Where the files are located
		counter = builder.parseDir(pathname,counter, classpath, sources);					// Put contents of directory into one string

		// Get final lists after counting complete
		ArrayList<TargetType> types = counter.getList();
		ArrayList<TargetType> varDec = counter.getList2();
		
		// Print out all values in each list
		for (TargetType s: types) {
			System.out.printf("\nType: %-25s  Declarations found: %-5d References found: %-2d\n",s.getType(),s.getDec(), s.getRef());
		}
		for (TargetType s: varDec) {
			System.out.printf("\nDeclarations of %-1s found: %-2d\n",s.getType(),s.getDec());
		}

	}
}
