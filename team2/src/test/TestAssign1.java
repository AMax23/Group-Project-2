package test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import java.io.FileNotFoundException;
import java.io.IOException;
import org.eclipse.jdt.core.dom.ASTNode;
import org.junit.Test;

import project1.TreeBuilder;
import project1.TypeCounter;
import project1.UserContact;

public class TestAssign1 {
	
	// CHANGE THESE 2 ACCORDING TO YOUR COMPUTER 
	private static String BASEDIR = "/users/ahmed/downloads/test2";
	// location in which your JDT DOM jar files are kept
	private static String SOURCES = "/users/ahmed/downloads/jar";
	
	

    /*
     * Test a directory with no java files to parse.
     */
    @Test
    public void testDirWithNoJavaFiles() {
	    	TreeBuilder builder = new TreeBuilder();
	    	String [] classpath = {""};													
	    	String [] sources = {SOURCES};
	    	ASTNode cu = builder.makeSyntaxTree("".toCharArray(),classpath, sources, "A");
	    	assertNotNull(cu); 	
    	
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
    public void testPrimitiveTypeINT() throws FileNotFoundException, IOException {
	    	TreeBuilder builder = new TreeBuilder();
	    	String [] classpath = {BASEDIR};																
	    	String [] sources = {SOURCES};
	    	ASTNode cu = builder.makeSyntaxTree("public class A { int i = 9;  \\n int j; }".toCharArray(),classpath, sources, "A");
	    	int count;
    		TypeCounter counter = new TypeCounter();
		count = counter.countRef(cu, "int");
    		assertEquals(2,count);
    }
    
    /*
     * Test type int with an empty source file
     */
    @Test
    public void testPrimitiveTypeINTNoSource() throws FileNotFoundException, IOException {
	    	TreeBuilder builder = new TreeBuilder();
	    	String [] classpath = {BASEDIR};																
	    	String [] sources = {SOURCES};
	    	ASTNode cu = builder.makeSyntaxTree("".toCharArray(),classpath, sources, "A");
	    	int count;
    		TypeCounter counter = new TypeCounter();
		count = counter.countRef(cu, "int");
    		assertEquals(0,count);
    }
    
    /*
     * Test boolean type with empty file. 0 references 
     */
    @Test
    public void testPrimitiveTypeBooleanNoSource() throws FileNotFoundException, IOException {
	    	TreeBuilder builder = new TreeBuilder();
	    	String [] classpath = {BASEDIR};																
	    	String [] sources = {SOURCES};
	    	ASTNode cu = builder.makeSyntaxTree("".toCharArray(),classpath, sources, "A");
	    	int count;
    		TypeCounter counter = new TypeCounter();
		count = counter.countRef(cu, "boolean");
    		assertEquals(0,count);
    }
    
    /*
     * Test boolean type with empty file. 0 references 
     */
    @Test
    public void testPrimitiveTypeCharNoSource() throws FileNotFoundException, IOException {
	    	TreeBuilder builder = new TreeBuilder();
	    	String [] classpath = {BASEDIR};																
	    	String [] sources = {SOURCES};
	    	ASTNode cu = builder.makeSyntaxTree("".toCharArray(),classpath, sources, "A");
	    	int count;
    		TypeCounter counter = new TypeCounter();
		count = counter.countRef(cu, "char");
    		assertEquals(0,count);
    }
    
    /*
     * Test java.lang.String with 0 references 
     */
    @Test
    public void testSimpleTypeStringNone() throws FileNotFoundException, IOException {
	    	TreeBuilder builder = new TreeBuilder();
	    	String [] classpath = {BASEDIR};																
	    	String [] sources = {SOURCES};
	    	ASTNode cu = builder.makeSyntaxTree("Public class A{}".toCharArray(),classpath, sources, "A");
	    	int count;
    		TypeCounter counter = new TypeCounter();
		count = counter.countRef(cu, "java.lang.String");
    		assertEquals(0,count);
    }
    
    /*
     * Test type char. 
     */
    @Test
    public void testPrimitiveTypeCHAR() throws FileNotFoundException, IOException {
	    	TreeBuilder builder = new TreeBuilder();
	    	String [] classpath = {BASEDIR};																
	    	String [] sources = {SOURCES};
	    	ASTNode cu = builder.makeSyntaxTree("public class A { char i = 9;  \\n int j; }".toCharArray(),classpath, sources, "A");
	    	int count;
    		TypeCounter counter = new TypeCounter();
		count = counter.countRef(cu, "char");
    		assertEquals(1,count);
    }
    
    /*
     * Test Enum with 0 declarations
     */
    @Test
    public void testEnumDecWithNone() throws FileNotFoundException, IOException {
	    	TreeBuilder builder = new TreeBuilder();
	    	String [] classpath = {BASEDIR};																
	    	String [] sources = {SOURCES};
	    	ASTNode cu = builder.makeSyntaxTree("public class A { char i = 9;  \\n int j; }".toCharArray(),classpath, sources, "A");
	    	int count;
    		TypeCounter counter = new TypeCounter();
		count = counter.countDec(cu, "enum");
    		assertEquals(0,count);
    }
    
    /*
     * Test Annotation declaration with 0 declarations
     */
    @Test
    public void testAnnotationTypeDecNone() throws FileNotFoundException, IOException {
	    	TreeBuilder builder = new TreeBuilder();
	    	String [] classpath = {BASEDIR};																
	    	String [] sources = {SOURCES};
	    	ASTNode cu = builder.makeSyntaxTree("public class A { char i = 9;  \\n int j; }".toCharArray(),classpath, sources, "A");
	    	int count;
    		TypeCounter counter = new TypeCounter();
		count = counter.countDec(cu, "java.lang.Annotation");
    		assertEquals(0,count);
    }
    
    /*
     * test java.lang.String
     */
    @Test
    public void testSimpleTypeString() throws FileNotFoundException, IOException {
	    	TreeBuilder builder = new TreeBuilder();
	    	String [] classpath = {BASEDIR};																
	    	String [] sources = {SOURCES};
	    	ASTNode cu = builder.makeSyntaxTree("public class A { String Foo = \"A\";  \\n int j; }".toCharArray(),classpath, sources, "A");
	    	int count;
    		TypeCounter counter = new TypeCounter();
		count = counter.countRef(cu, "java.lang.String");
    		assertEquals(1,count);
    }
    
    /*
     * Test type boolean
     */
    @Test
    public void testPrimitiveTypeBOOLEAN() throws FileNotFoundException, IOException {
	    	TreeBuilder builder = new TreeBuilder();									
	    	String [] classpath = {BASEDIR};																
	    	String [] sources = {SOURCES};
	    	ASTNode cu = builder.makeSyntaxTree("public class A {boolean i = false;  \\n int j; }".toCharArray(),classpath, sources, "A");
	    	int count;
    		TypeCounter counter = new TypeCounter();
		count = counter.countRef(cu, "boolean");
    		assertEquals(1,count);
    }
    
    /*
     * Test type enum
     */
    @Test
    public void testSimpleTypeENUM() throws FileNotFoundException, IOException {
	    	TreeBuilder builder = new TreeBuilder();								
	    	String [] classpath = {BASEDIR};																
	    	String [] sources = {SOURCES};
	    	ASTNode cu = builder.makeSyntaxTree("public class A { enum colour {RED}; }".toCharArray(),classpath, sources, "A");
	    	int count;
    		TypeCounter counter = new TypeCounter();
		count = counter.countRef(cu, "enum");
    		assertEquals(1,count);
    }
    
    /*
     * Check Class declaration count. 
     */
    @Test
    public void testTypeDeclarationCLASS() throws FileNotFoundException, IOException {
	    	TreeBuilder builder = new TreeBuilder();									
	    	String [] classpath = {BASEDIR};																
	    	String [] sources = {SOURCES};
	    	ASTNode cu = builder.makeSyntaxTree("public class A { char i = 9;  \\n int j; }".toCharArray(),classpath, sources, "A");
	    	int count;
    		TypeCounter counter = new TypeCounter();
		count = counter.countDec(cu, "A");
    		assertEquals(1,count);
    }
    
    /*
     * Test Class method (new constructor) references 
     */
    @Test
    public void testTypeDeclarationCLASSMETHOD() throws FileNotFoundException, IOException {
	    	TreeBuilder builder = new TreeBuilder();									
	    	String [] classpath = {BASEDIR};																
	    	String [] sources = {SOURCES};
	    	ASTNode cu = builder.makeSyntaxTree("public class A { public static main (String [] args}{A a = new A();} }".toCharArray(),classpath, sources, "A");
	    	int count;
    		TypeCounter counter = new TypeCounter();
		count = counter.countRef(cu, "A");
    		assertEquals(2,count);
    }
    
    /*
     * Test annotation @Test
     */
    @Test
    public void testMarkerAnnotation() throws FileNotFoundException, IOException {
	    	TreeBuilder builder = new TreeBuilder();									
	    	String [] classpath = {BASEDIR};																
	    	String [] sources = {SOURCES};
	    	ASTNode cu = builder.makeSyntaxTree("@Test public class A { char i = 9;  \\n int j; }".toCharArray(),classpath, sources, "A");
	    	int count;
    		TypeCounter counter = new TypeCounter();
		count = counter.countRef(cu, "Test");
    		assertEquals(1,count);
    }
    
    /*
     * Test method parameter having 1 string parameter. 
     */
    @Test
    public void testMethodParameters() throws FileNotFoundException, IOException {
	    	TreeBuilder builder = new TreeBuilder();									
	    	String [] classpath = {BASEDIR};																
	    	String [] sources = {SOURCES};
	    	ASTNode cu = builder.makeSyntaxTree("@Test public class A { public void method1(String x){} }".toCharArray(),classpath, sources, "A");
	    	int count;
    		TypeCounter counter = new TypeCounter();
		count = counter.countRef(cu, "java.lang.String");
    		assertEquals(1,count);
    }
    
    /*
     * Check normal annotation with expression. Count references to that. 
     */
    @Test
    public void testNormalAnnotation() throws FileNotFoundException, IOException {
	    	TreeBuilder builder = new TreeBuilder();										
	    	String [] classpath = {BASEDIR};																
	    	String [] sources = {SOURCES};
	    	ASTNode cu = builder.makeSyntaxTree("@Foo (expected = bar) public class A {}".toCharArray(),classpath, sources, "A");
	    	int count;
    		TypeCounter counter = new TypeCounter();
		count = counter.countRef(cu, "Foo");
    		assertEquals(1,count);
    }
    
    /*
     * Test by giving empty file to parse.
     */
    @Test(expected = NullPointerException.class)
    public void testFilesToStringNUll() throws FileNotFoundException, IOException {
	    	TreeBuilder builder = new TreeBuilder();										
	    	String cu = builder.filesToString("");
	    	assertNotNull(cu);
    }
    
    /*
     * Test creation of syntax tree with null
     */
    @Test(expected = IllegalArgumentException.class)
    public void testTreeBuilderMakeSyntaxTree() throws FileNotFoundException, IOException {
    		TreeBuilder builder = new TreeBuilder();
		assertNotNull(builder.makeSyntaxTree(null, null, null, null));
    }

    
}