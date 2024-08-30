import spoon.Launcher;
import spoon.reflect.declaration.CtClass;
import spoon.reflect.declaration.CtField;
import spoon.reflect.declaration.CtMethod;

public class SpoonAnalyzer {

    public static void main(String[] args) {
        // 기초적인 사용법 https://spoon.gforge.inria.fr/launcher.html
        // API docs https://spoon.gforge.inria.fr/mvnsites/spoon-core/apidocs/

        CtClass<?> ctClass = Launcher.parseClass("""
                class TestClass {
                    private String testField1 = "This is default expression of testField1.";
                
                    public void testMethod1() { System.out.println("test method 1"); }
                    public void testMethod2() { System.out.println("test method 2"); }
                }
                """);

        System.out.println("ctClass.getSimpleName(): " + ctClass.getSimpleName());

        for (CtField<?> field : ctClass.getFields()) {
            System.out.println("field.getVisibility(): " + field.getVisibility());
            System.out.println("field.getType(): " + field.getType());
            System.out.println("field.getSimpleName(): " + field.getSimpleName());
            System.out.println("field.getDefaultExpression(): " + field.getDefaultExpression());
        }

        for (CtMethod<?> method : ctClass.getMethods()) {
            System.out.println("method.getSignature(): " + method.getSignature());
            System.out.println("method.getVisibility(): " + method.getVisibility());
            System.out.println("method.getType(): " + method.getType());
            System.out.println("method.getSimpleName(): " + method.getSimpleName());
            System.out.println("method.getParameters(): " + method.getParameters());
            System.out.println("method.getBody(): " + method.getBody());
        }
    }
}
