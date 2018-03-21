package project1;

import java.util.ArrayList;

import org.eclipse.jdt.core.dom.ASTNode;
import org.eclipse.jdt.core.dom.ASTVisitor;
import org.eclipse.jdt.core.dom.AnnotationTypeDeclaration;
import org.eclipse.jdt.core.dom.EnumDeclaration;
import org.eclipse.jdt.core.dom.MarkerAnnotation;
import org.eclipse.jdt.core.dom.NormalAnnotation;
import org.eclipse.jdt.core.dom.SimpleType;
import org.eclipse.jdt.core.dom.Type;
import org.eclipse.jdt.core.dom.TypeDeclaration;
import org.eclipse.jdt.core.dom.PrimitiveType;

public class TypeCounter {

//	private int decCount;
//	private int refCount;

	//	ArrayList <TargetType> types00 = new ArrayList<TargetType>();
	//	ArrayList <String> types2 = new ArrayList<String>();
	//	
	ArrayList<TargetType> types = new ArrayList<TargetType>();

	/**	 
	 **	 Parameters: The starting node of the syntax tree, the type that the user is looking to count
	 **	 Count all Annotation, Enumeration, Interface, and Class declarations of the target type.
	 **	 Return: The integer decCount.
	 **/
	private ArrayList<TargetType> countDec(ASTNode cu) {
		cu.accept(new ASTVisitor() {	 

			// COUNT ANNOTATION DECLARATIONS
			public boolean visit(AnnotationTypeDeclaration node) {			
//				String nodeAsString = node.toString();
//				if ( nodeAsString.contains("interface "+ targetType) )	
//					decCount++;																		
				return true;
			}

			// COUNT ENUMERATION DECLARATIONS
			public boolean visit(EnumDeclaration node) {												
//				String nodeAsString = node.toString();
//				System.out.println(nodeAsString);
//				if (nodeAsString.contains("enum "+ targetType)) 		
//
//					decCount++;																	
				return true;
			}

			// COUNT CLASS AND INTERFACE DECLARATIONS
			public boolean visit(TypeDeclaration node) {
				
				if ( (types.isEmpty()) ){

					types.add(new TargetType(node.resolveBinding().getQualifiedName(),0,1));

				}else {
					if ( !(types.isEmpty()) ) {

						TargetType repeat = null;
						boolean first = true;

						ArrayList<String> typeNames = new ArrayList<String>();

						for (TargetType s: types) {

							typeNames.add(s.getType());
							if (typeNames.contains((node.resolveBinding().getQualifiedName())) && first){
								repeat = s;
								first = false;
							}
						}

						if (repeat != null) {
							repeat.addDec();
						}
						if (!(typeNames.contains(node.resolveBinding().getQualifiedName()))) {
							types.add(new TargetType(node.resolveBinding().getQualifiedName(), 0, 1));
						}
					}
				}
														
				return true;
			}
		});
		return types;
	}

	/**	 
	 **	 Parameters: The starting node of the syntax tree, the type that the user is looking to count
	 **	 Count all references to the target type
	 **	 Return: The integer refCount.
	 **/
	private ArrayList<TargetType> countRef(ASTNode cu)
	{
		cu.accept(new ASTVisitor() {	

			// COUNT NORMAL ANNOTATION TYPE REFERENCES 
			public boolean visit (NormalAnnotation node) {
				try {
					//				if (!(types.contains(node.resolveTypeBinding().getQualifiedName()))){
					//					types.add(node.resolveTypeBinding().getQualifiedName());
					//				}
//					if ( node.resolveTypeBinding().getQualifiedName().equals(targetType) )
//						refCount++;
				}catch (Exception e) {
				}
				return false;
			}

			// COUNT MARKER ANNOTATION TYPE REFERENCES 
			public boolean visit (MarkerAnnotation node) {
				//				if (!(types.contains(node.resolveTypeBinding().getQualifiedName()))){
				//					types.add(node.resolveTypeBinding().getQualifiedName());
				//				}
//				if ( node.resolveTypeBinding().getQualifiedName().equals(targetType) )
//					refCount++;
				return true;
			}

			// COUNT PRIMITIVE TYPE REFERENCES 
			public boolean visit(PrimitiveType node) {

				addVisitRef(node);

				return true;
			}

			// COUNT ALL OTHER TYPE REFERENCES 
			public boolean visit(SimpleType node) {

				addVisitRef(node);

				return true;
			}
		});


		return types;
	}


	private String getFullName(Type node) {
		return node.resolveBinding().getQualifiedName();

	}

	private void addVisitRef(Type node) {
		if ((types.isEmpty()) && (!(getFullName(node).equals("void")))){

			types.add(new TargetType(getFullName(node),1,0));

		}else {
			if (!(types.isEmpty())) {

				TargetType repeat = null;
				boolean first = true;

				ArrayList<String> typeNames = new ArrayList<String>();

				for (TargetType s: types) {

					typeNames.add(s.getType());
					if (typeNames.contains((getFullName(node))) && first){
						repeat = s;
						first = false;
					}
				}

				if (repeat != null) {
					repeat.addRef();
				}
				if (!(getFullName(node).equals("void")) && !(typeNames.contains(getFullName(node)))) {
					types.add(new TargetType(getFullName(node), 1, 0));
				}
			}
		}
	}
	
//	private void addVisitDec(Type node) {
//		if ((types.isEmpty()) && (!(getFullName(node).equals("void")))){
//
//			types.add(new TargetType(getFullName(node),0,1));
//
//		}else {
//			if (!(types.isEmpty())) {
//
//				TargetType repeat = null;
//				boolean first = true;
//
//				ArrayList<String> typeNames = new ArrayList<String>();
//
//				for (TargetType s: types) {
//
//					typeNames.add(s.getType());
//					if (typeNames.contains((getFullName(node))) && first){
//						repeat = s;
//						first = false;
//					}
//				}
//
//				if (repeat != null) {
//					repeat.addRef();
//				}
//				if (!(getFullName(node).equals("void")) && !(typeNames.contains(getFullName(node)))) {
//					types.add(new TargetType(getFullName(node), 0, 1));
//				}
//			}
//		}
//	}
	
	public ArrayList<TargetType> count(ASTNode cu) {
		countRef(cu);
		countDec(cu);
		return types;
	}
}
