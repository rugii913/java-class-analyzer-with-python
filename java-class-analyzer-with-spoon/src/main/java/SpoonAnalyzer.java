import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import spoon.Launcher;
import spoon.reflect.declaration.CtClass;
import spoon.reflect.declaration.CtField;
import spoon.reflect.declaration.CtMethod;

public class SpoonAnalyzer {

    private static final Logger logger = LoggerFactory.getLogger(SpoonAnalyzer.class);

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

        logger.debug("ctClass.getSimpleName(): {}", ctClass.getSimpleName());

        for (CtField<?> field : ctClass.getFields()) {
            logger.debug("field.getVisibility(): {}", field.getVisibility());
            logger.debug("field.getType(): {}", field.getType());
            logger.debug("field.getSimpleName(): {}", field.getSimpleName());
            logger.debug("field.getDefaultExpression(): {}", field.getDefaultExpression());
        }

        for (CtMethod<?> method : ctClass.getMethods()) {
            logger.debug("method.getSignature(): {}", method.getSignature());
            logger.debug("method.getVisibility(): {}", method.getVisibility());
            logger.debug("method.getType(): {}", method.getType());
            logger.debug("method.getSimpleName(): {}", method.getSimpleName());
            logger.debug("method.getParameters(): {}", method.getParameters());
            logger.debug("method.getBody(): {}", method.getBody());
        }
    }
}
