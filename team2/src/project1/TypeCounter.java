package project1;

import org.eclipse.jdt.core.dom.ASTNode;
import org.eclipse.jdt.core.dom.ASTVisitor;
import org.eclipse.jdt.core.dom.AnnotationTypeDeclaration;
import org.eclipse.jdt.core.dom.EnumDeclaration;
import org.eclipse.jdt.core.dom.MarkerAnnotation;
import org.eclipse.jdt.core.dom.NormalAnnotation;
import org.eclipse.jdt.core.dom.SimpleType;
import org.eclipse.jdt.core.dom.TypeDeclaration;
import org.eclipse.jdt.core.dom.PrimitiveType;

public class TypeCounter {

	private int decCount;
	private int refCount;
	
/**	 
 **	 Parameters: The starting node of the syntax tree, the type that the user is looking to count
 **	 Count all Annotation, Enumeration, Interface, and Class declarations of the target type.
 **	 Return: The integer decCount.
 **/
	public int countDec(ASTNode cu, String targetType) {
		cu.accept(new ASTVisitor() {	 
			
			// COUNT ANNOTATION DECLARATIONS
			public boolean visit(AnnotationTypeDeclaration node) {			
				String nodeAsString = node.toString();
				if ( nodeAsString.contains("interface "+ targetType) )	
					decCount++;																		
				return true;
			}
			
			// COUNT ENUMERATION DECLARATIONS
			public boolean visit(EnumDeclaration node) {												
				String nodeAsString = node.toString();
				if (nodeAsString.contains("enum "+ targetType)) 						
					decCount++;																	
				return true;
			}
		
			// COUNT CLASS AND INTERFACE DECLARATIONS
			public boolean visit(TypeDeclaration node) {
				if ( node.resolveBinding().getQualifiedName().equals(targetType) )
					decCount++;															
				return true;
			}
		});
		return decCount;
	}
	
/**	 
 **	 Parameters: The starting node of the syntax tree, the type that the user is looking to count
 **	 Count all references to the target type
 **	 Return: The integer refCount.
 **/
	public int countRef(ASTNode cu, String targetType)
	{
		cu.accept(new ASTVisitor() {	
			
			// COUNT NORMAL ANNOTATION TYPE REFERENCES 
			public boolean visit (NormalAnnotation node) {
				try {
				if ( node.resolveTypeBinding().getQualifiedName().equals(targetType) )
					refCount++;
				}catch (Exception e) {
				}
				return false;
			}
			
			// COUNT MARKER ANNOTATION TYPE REFERENCES 
			public boolean visit (MarkerAnnotation node) {
				if ( node.resolveTypeBinding().getQualifiedName().equals(targetType) )
					refCount++;
				return true;
			}

			// COUNT PRIMITIVE TYPE REFERENCES 
			public boolean visit(PrimitiveType node) {	
				if ( node.resolveBinding().getQualifiedName().equals(targetType) & !(node.resolveBinding().getQualifiedName().equals("void")))
					refCount++;
				return true;
			}
			
			// COUNT ALL OTHER TYPE REFERENCES 
			public boolean visit(SimpleType node) { 
				if ( node.resolveBinding().getQualifiedName().equals(targetType) )
					refCount++;
				return true;
			}
		});
		
		return refCount;
	}
}
