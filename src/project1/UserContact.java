package project1;

import java.io.File;
import java.util.Scanner;

public class UserContact {

	//Ask the user for the pathname (command line)
	public String getPathname() {
		System.out.print("Please enter the directory name: ");
		Scanner keyboard = new Scanner(System.in);			
		String pathname = keyboard.nextLine();							
		return pathname;
	}
	
//	//Ask the user for the pathname (command line)
//	public String getTargetType() {
//		System.out.print("\nPlease enter a fully qualified name of a java type: ");
//		Scanner keyboard = new Scanner (System.in);
//		String targetType = keyboard.nextLine();
//		return targetType;
//	}
//
//	//Ask the user for the pathname for the jar files (command line)
//	public String[] getJarFiles() {
//		System.out.print("Please enter the pathname for the directory in which your JDT DOM jar files are kept: ");
//		Scanner keyboard = new Scanner (System.in);
//		String[] sources = {keyboard.nextLine()};
//		return sources;
//	}
	
	/**	 
	 **	 Parameters: String pathname: location of the folder
	 **	 Return: Boolean based on whether the file exists or not. And prints an error message.
	 **/
	public boolean isValidDir (String pathname) {
		
		File dir = new File (pathname);
		
		if (!dir.exists()) 
			System.out.println("Error: Directory does not exist. Try again\n");
		return dir.exists();
	}
}