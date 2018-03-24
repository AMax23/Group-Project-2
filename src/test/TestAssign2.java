package test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;

import org.eclipse.jdt.core.dom.ASTNode;
import org.junit.Test;

import project1.TargetType;
import project1.TreeBuilder;
import project1.TypeCounter;
import project1.UserContact;

public class TestAssign2 {
	
	// CHANGE THESE 2 ACCORDING TO YOUR COMPUTER 
	private static String BASEDIR = "C:\\Users\\Masroor Hussain Syed\\Desktop\\UCalgary Courses\\Seng300\\Assignment\\Assign2\\src\\testFiles\\";
		
    /*
     * Test a directory with no java files to parse.
     */
    @Test
    public void testDirWithNoJavaFiles() throws FileNotFoundException, IOException {
    		//TypeCounter counter = new TypeCounter();
	    	TreeBuilder builder = new TreeBuilder();
	    	String pathname =BASEDIR + "empty\\";
	    	String [] sources = {pathname};		
			String [] classpath = {pathname};
			ASTNode cu = builder.makeSyntaxTree("".toCharArray(), classpath, sources, "");
			assertEquals("", cu.toString());
    }
    
    /*
     * Test by giving an invalid directory (1 that doesnt exist)
     */
    @Test
    public void testInvalidDir() {
	    	UserContact uc = new UserContact();
	    	boolean isvalid = uc.isValidDir("Random string not a directory");
	    	assertFalse(isvalid);
    }
    
    /*
     * Test type int. Count references to that.
     */
    @Test
    public void testPrimitiveTypeINTRef() throws FileNotFoundException, IOException {
    	
    		TypeCounter counter = new TypeCounter();
    		TreeBuilder builder = new TreeBuilder();
    		
    		String pathname = BASEDIR;
	    	String [] sources = {pathname};		
			String [] classpath = {pathname};
			
			String javaCode = "public class A { int i = 9;  \\n int j; }";
			
	    	ASTNode cu = builder.makeSyntaxTree(javaCode.toCharArray(),classpath, sources, javaCode);
	    	ArrayList<TargetType> count;
    		count = counter.count(cu);
    		int refCount = 0;
    		
    		// Print out all values in each list
    		for (TargetType s: count) {
    			if(s.getType().equals("int")) {
    				refCount = s.getRef();
    			}
    		}
    		
    		assertEquals(2,refCount);
    }
    
    /*
     * Test type int. Count Declaration to that.
     */
    @Test
    public void testPrimitiveTypeINTDec() throws FileNotFoundException, IOException {
    	
    		TypeCounter counter = new TypeCounter();
    		TreeBuilder builder = new TreeBuilder();
    		
    		String pathname = BASEDIR;
	    	String [] sources = {pathname};		
			String [] classpath = {pathname};
			
			String javaCode = "public class A { int i = 9;  \\n int j; }";
			
	    	ASTNode cu = builder.makeSyntaxTree(javaCode.toCharArray(),classpath, sources, javaCode);
	    	ArrayList<TargetType> count;
    		count = counter.count(cu);
    		int decCount = 0;
    		
    		// Print out all values in each list
    		for (TargetType s: count) {
    			if(s.getType().equals("int")) {
    				decCount = s.getDec();
    			}
    		}
    		
    		assertEquals(0,decCount);
    }
    
    /*
     * Test type int with an empty source file
     */
    @Test
    public void testPrimitiveTypeINTRefWithNoSource() throws FileNotFoundException, IOException {
    	TypeCounter counter = new TypeCounter();
		TreeBuilder builder = new TreeBuilder();
		
		String pathname = BASEDIR;
    	String [] sources = {pathname};		
		String [] classpath = {pathname};
		
		String javaCode = " ";
		
    	ASTNode cu = builder.makeSyntaxTree(javaCode.toCharArray(),classpath, sources, javaCode);
    	ArrayList<TargetType> count;
		count = counter.count(cu);
		int refCount = 0;
		
		for (TargetType s: count) {
			if(s.getType().equals("int")) {
				refCount = s.getRef();
			}
		}
		
		assertEquals(0,refCount);
    }
    

    /*
     * Test Enum with 0 declarations
     */
    @Test
    public void testEnumDecWithNone() throws FileNotFoundException, IOException {
    	TypeCounter counter = new TypeCounter();
		TreeBuilder builder = new TreeBuilder();
		
		String pathname = BASEDIR;
    	String [] sources = {pathname};		
		String [] classpath = {pathname};
		
		String javaCode = "public class A {int j;}";
		
    	ASTNode cu = builder.makeSyntaxTree(javaCode.toCharArray(),classpath, sources, javaCode);
    	ArrayList<TargetType> count;
		count = counter.count(cu);
		int refCount = 0;
		
		for (TargetType s: count) {
			System.out.println(s.getType());
			if(s.getType().equals("A.colour")) {
				refCount = s.getDec();
			}
		}
		
		assertEquals(0,refCount);

    }

    /*
     * Test type enum
     */
    @Test
    public void testSimpleTypeENUMDec() throws FileNotFoundException, IOException {
    	
    	TypeCounter counter = new TypeCounter();
		TreeBuilder builder = new TreeBuilder();
		
		String pathname = BASEDIR;
    	String [] sources = {pathname};		
		String [] classpath = {pathname};
		
		String javaCode = "public class A { enum colour {RED}; \\n int j; }";
		
    	ASTNode cu = builder.makeSyntaxTree(javaCode.toCharArray(),classpath, sources, javaCode);
    	ArrayList<TargetType> count;
		count = counter.count(cu);
		int refCount = 0;
		
		// Print out all values in each list
		for (TargetType s: count) {
			System.out.println(s.getType());
			if(s.getType().equals("A.colour")) {
				refCount = s.getDec();
			}
		}
		
		assertEquals(1,refCount);

    }
    

    /*
     * Test creation of syntax tree with null
     */
    @Test(expected = IllegalArgumentException.class)
    public void testTreeBuilderMakeSyntaxTreeWithNull() throws FileNotFoundException, IOException {
    		TreeBuilder builder = new TreeBuilder();
    		assertNotNull(builder.makeSyntaxTree(null, null, null, null));
    }

    /**
     * test parsing a directory with no sub directories
     * @throws FileNotFoundException
     * @throws IOException
     */
    @Test
    public void testgetListForDirWithNoSubDir() throws FileNotFoundException, IOException {
    	ArrayList<TargetType> DirWithNoSubDir = new ArrayList<TargetType>();
    	DirWithNoSubDir.add(new TargetType("D", 1, 0));
    	DirWithNoSubDir.add(new TargetType("testFiles.temp.noJar.Test3",1,1));
    	DirWithNoSubDir.add(new TargetType("Test3",1,0));
    	DirWithNoSubDir.add(new TargetType("testFiles.temp.noJar.Test2",1,1));
    	DirWithNoSubDir.add(new TargetType("testFiles.Test2 ", 1, 0));
    	DirWithNoSubDir.add(new TargetType("testFiles.temp.noJar.Foo", 1, 0));
    	DirWithNoSubDir.add(new TargetType("int", 1, 0));
    	DirWithNoSubDir.add(new TargetType("java.lang.String", 1, 0));
    	DirWithNoSubDir.add(new TargetType("Test2", 1, 0));
    	DirWithNoSubDir.add(new TargetType("testFiles.temp.noJar.Test3.Cars", 0, 1));
    	DirWithNoSubDir.add(new TargetType("testFiles.temp.noJar.Test3.interTest", 0, 1));
    	
    	TypeCounter counter = new TypeCounter();
		TreeBuilder builder = new TreeBuilder();
		String pathname = BASEDIR + "temp\\noJar\\";
		String [] sources = {pathname};		
		String [] classpath = {pathname};				
		counter = builder.parseDir(pathname,counter, classpath, sources);
    	
		ArrayList<TargetType> actual = counter.getList();
		boolean isDiff = false;
		
		if (!(DirWithNoSubDir.size() == actual.size())) {
			fail("the sizes are different");
		}
		
		for (TargetType s: actual) {
			int count = 0;
			for(TargetType d: DirWithNoSubDir ) {
				if(s.getType().equals(d.getType()) && s.getDec() == d.getDec() && s.getRef() == d.getRef()) {
					isDiff = true;
					break;
				}
				count++;
				
				if(count == actual.size()){
					break;
				}
			
				
			}
		}
		
		assertTrue(isDiff);
    }
    /**
     * test parsing a directory with sub directories and jar files
     * @throws FileNotFoundException
     * @throws IOException
     */
    @Test
    public void testgetListForDirWithSubDirAndJar() throws FileNotFoundException, IOException {
    	ArrayList<TargetType> DirWithNoSubDir = new ArrayList<TargetType>();
    	DirWithNoSubDir.add(new TargetType("testFiles.temp.noJar.Test3",1,1));
    	DirWithNoSubDir.add(new TargetType("testFiles.jarWithDir.A ", 0, 1));
    	DirWithNoSubDir.add(new TargetType("D", 1, 0));
    	DirWithNoSubDir.add(new TargetType("Test3",4,1));
    	DirWithNoSubDir.add(new TargetType("testFiles.temp.noJar.Test2",1,1));
    	DirWithNoSubDir.add(new TargetType("testFiles.temp.noJar.Foo", 1, 0));
    	DirWithNoSubDir.add(new TargetType("int", 4, 0));
    	DirWithNoSubDir.add(new TargetType("java.lang.String", 3, 0));
    	DirWithNoSubDir.add(new TargetType("Test2", 2, 0));
    	DirWithNoSubDir.add(new TargetType("testFiles.temp.noJar.Test3.Cars", 0, 1));
    	DirWithNoSubDir.add(new TargetType("testFiles.temp.noJar.Test3.interTest", 0, 1));
    	DirWithNoSubDir.add(new TargetType("testFiles.temp.Test3", 1, 1));
    	DirWithNoSubDir.add(new TargetType("testFiles.temp.Test2", 1, 1));
    	DirWithNoSubDir.add(new TargetType("testFiles.temp.Foo", 1, 0));
    	DirWithNoSubDir.add(new TargetType("testFiles.temp.Test3.Cars", 0, 1));
    	DirWithNoSubDir.add(new TargetType("testFiles.temp.Test3.interTest", 0, 1));
    	DirWithNoSubDir.add(new TargetType("testFiles.Test2 ", 2, 1));
    	DirWithNoSubDir.add(new TargetType("Test3.colour", 0, 1));
    	
    	
    	TypeCounter counter = new TypeCounter();
		TreeBuilder builder = new TreeBuilder();
		String pathname = BASEDIR;
		String [] sources = {pathname};		
		String [] classpath = {pathname};				
		counter = builder.parseDir(pathname,counter, classpath, sources);
    	
		ArrayList<TargetType> actual = counter.getList();
		boolean isDiff = false;
		
		if (!(DirWithNoSubDir.size() == actual.size())) {
			fail("the sizes are different");
		}
		
		for (TargetType s: actual) {
			int count = 0;
			for(TargetType d: DirWithNoSubDir ) {
				if(s.getType().equals(d.getType()) && s.getDec() == d.getDec() && s.getRef() == d.getRef()) {
					isDiff = true;
					break;
				}
				count++;
				
				if(count == actual.size()){
					break;
				}
			
				
			}
		}
		
		assertTrue(isDiff);
    	
    }

    /**
     * test parsing jar files with no sub directories
     * @throws FileNotFoundException
     * @throws IOException
     */
    @Test
    public void testgetListForJarFilesWithNoSubFolder() throws FileNotFoundException, IOException {
    	
    	ArrayList<TargetType> DirWithJar = new ArrayList<TargetType>();
    	DirWithJar.add(new TargetType("testFiles.temp.Test3",1,1));
    	DirWithJar.add(new TargetType("Test3",1,0));
    	DirWithJar.add(new TargetType("testFiles.temp.Test2",1,1));
    	DirWithJar.add(new TargetType("testFiles.Test2", 1, 0));
    	DirWithJar.add(new TargetType("testFiles.temp.Foo", 1, 0));
    	DirWithJar.add(new TargetType("int", 1, 0));
    	DirWithJar.add(new TargetType("java.lang.String", 1, 0));
    	DirWithJar.add(new TargetType("Test2", 1, 0));
    	DirWithJar.add(new TargetType("testFiles.temp.Test3.Cars", 0, 1));
    	DirWithJar.add(new TargetType("testFiles.temp.Test3.interTest", 0, 1));
    	
    	TypeCounter counter = new TypeCounter();
		TreeBuilder builder = new TreeBuilder();
		String pathname = BASEDIR + "temp\\oneJar\\";
		String [] sources = {pathname};		
		String [] classpath = {pathname};				
		counter = builder.parseDir(pathname,counter, classpath, sources);
    	
		ArrayList<TargetType> actual = counter.getList();
		boolean isDiff = false;
		
		if (!(DirWithJar.size() == actual.size())) {
			fail("the sizes are different");
		}
		
		for (TargetType s: actual) {
			int count = 0;
			for(TargetType d: DirWithJar ) {
				if(s.getType().equals(d.getType()) && s.getDec() == d.getDec() && s.getRef() == d.getRef()) {
					isDiff = true;
					break;
				}
				count++;
				
				if(count == actual.size()){
					break;
				}
			
				
			}
		}
		
		assertTrue(isDiff);
    	
    	
    }

    /**
     * test parsing jar files with sub directories
     * @throws FileNotFoundException
     * @throws IOException
     */
    @Test
    public void testgetListForJarFilesWithSubFolder() throws FileNotFoundException, IOException {
    	ArrayList<TargetType> DirWithJar = new ArrayList<TargetType>();
    	DirWithJar.add(new TargetType("testFiles.jarWithDir.A",0,1));
    	DirWithJar.add(new TargetType("int",1,0));
   	
    	TypeCounter counter = new TypeCounter();
		TreeBuilder builder = new TreeBuilder();
		String pathname = BASEDIR + "temp\\jarWithDir\\";
		String [] sources = {pathname};		
		String [] classpath = {pathname};				
		counter = builder.parseDir(pathname,counter, classpath, sources);
    	
		ArrayList<TargetType> actual = counter.getList();
		boolean isDiff = false;
		
		if (!(DirWithJar.size() == actual.size())) {
			fail("the sizes are different");
		}
		
		for (TargetType s: actual) {
			int count = 0;
			for(TargetType d: DirWithJar ) {
				if(s.getType().equals(d.getType()) && s.getDec() == d.getDec() && s.getRef() == d.getRef()) {
					isDiff = true;
					break;
				}
				count++;
				
				if(count == actual.size()){
					break;
				}
			
				
			}
		}
		
		assertTrue(isDiff);
    }

    /**
     * test parsing for variable dec for dir with no subdir
     * @throws FileNotFoundException
     * @throws IOException
     */
    @Test
    public void testgetList2ForDirWithNoSubDir() throws FileNotFoundException, IOException {
    	ArrayList<TargetType> DirWithJar = new ArrayList<TargetType>();
    	DirWithJar.add(new TargetType("test2",0,1));
    	DirWithJar.add(new TargetType("Test3",0,1));
    	
    	TypeCounter counter = new TypeCounter();
		TreeBuilder builder = new TreeBuilder();
		String pathname = BASEDIR + "temp\\noJar\\";
		String [] sources = {pathname};		
		String [] classpath = {pathname};				
		counter = builder.parseDir(pathname,counter, classpath, sources);
    	
		ArrayList<TargetType> actual = counter.getList2();
		boolean isDiff = false;
		
		if (!(DirWithJar.size() == actual.size())) {
			fail("the sizes are different");
		}
		
		for (TargetType s: actual) {
			int count = 0;
			for(TargetType d: DirWithJar ) {
				if(s.getType().equals(d.getType()) && s.getDec() == d.getDec()) {
					isDiff = true;
					break;
				}
				count++;
				
				if(count == actual.size()){
					break;
				}
	
			}
		}
		
		assertTrue(isDiff);
    	
    }
    
    /**
     * test parsing for variable dec for dir with subdir and jar
     * @throws FileNotFoundException
     * @throws IOException
     */
    @Test
    public void testgetList2ForDirWithSubDirAndJar() throws FileNotFoundException, IOException {
    	ArrayList<TargetType> DirWithJar = new ArrayList<TargetType>();
    	DirWithJar.add(new TargetType("test2",0,2));
    	DirWithJar.add(new TargetType("Test3",0,3));
    	
    	TypeCounter counter = new TypeCounter();
		TreeBuilder builder = new TreeBuilder();
		String pathname = BASEDIR;
		String [] sources = {pathname};		
		String [] classpath = {pathname};				
		counter = builder.parseDir(pathname,counter, classpath, sources);
    	
		ArrayList<TargetType> actual = counter.getList2();
		boolean isDiff = false;
		
		if (!(DirWithJar.size() == actual.size())) {
			fail("the sizes are different");
		}
		
		for (TargetType s: actual) {
			int count = 0;
			for(TargetType d: DirWithJar ) {
				if(s.getType().equals(d.getType()) && s.getDec() == d.getDec()) {
					isDiff = true;
					break;
				}
				count++;
				
				if(count == actual.size()){
					break;
				}
	
			}
		}
		
		assertTrue(isDiff);
    }
    
    /**
     * test parsing for variable dec for jar with no subdir
     * @throws FileNotFoundException
     * @throws IOException
     */
    @Test
    public void testgetList2ForJarFilesWithNoSubFolder() throws FileNotFoundException, IOException {
    	ArrayList<TargetType> DirWithJar = new ArrayList<TargetType>();
    	DirWithJar.add(new TargetType("test2",0,1));
    	DirWithJar.add(new TargetType("Test3",0,1));
    	
    	TypeCounter counter = new TypeCounter();
		TreeBuilder builder = new TreeBuilder();
		String pathname = BASEDIR + "temp\\oneJar\\";
		String [] sources = {pathname};		
		String [] classpath = {pathname};				
		counter = builder.parseDir(pathname,counter, classpath, sources);
    	
		ArrayList<TargetType> actual = counter.getList2();
		boolean isDiff = false;
		
		if (!(DirWithJar.size() == actual.size())) {
			fail("the sizes are different");
		}
		
		for (TargetType s: actual) {
			int count = 0;
			for(TargetType d: DirWithJar ) {
				if(s.getType().equals(d.getType()) && s.getDec() == d.getDec()) {
					isDiff = true;
					break;
				}
				count++;
				
				if(count == actual.size()){
					break;
				}
	
			}
		}
		
		assertTrue(isDiff);
    }
    
    /**
     * test parsing for variable dec for dir with subdir
     * @throws FileNotFoundException
     * @throws IOException
     */
    @Test
    public void testgetList2ForJarFilesWithSubFolder() throws FileNotFoundException, IOException { 
    	// No variable dec so empty
    	ArrayList<TargetType> DirWithJar = new ArrayList<TargetType>();
    	
    	TypeCounter counter = new TypeCounter();
		TreeBuilder builder = new TreeBuilder();
		String pathname = BASEDIR + "temp\\jarWithDir\\";
		String [] sources = {pathname};		
		String [] classpath = {pathname};				
		counter = builder.parseDir(pathname,counter, classpath, sources);
    	
		ArrayList<TargetType> actual = counter.getList2();
		boolean isSame = true;
		
		if (!(DirWithJar.size() == actual.size())) {
			fail("the sizes are different");
		}
		
		for (TargetType s: actual) {
			int count = 0;
			for(TargetType d: DirWithJar ) {
				if(!(s.getType().equals(d.getType()) && s.getDec() == d.getDec())) {
					isSame = false;
					break;
				}
				count++;
				
				if(count == actual.size()){
					break;
				}
	
			}
		}
		
		assertTrue(isSame);
    	
    	
    }
    
}