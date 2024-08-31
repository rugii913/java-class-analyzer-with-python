package io.analyzer;

import org.apache.commons.io.FileUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import spoon.JarLauncher;
import spoon.reflect.CtModel;
import spoon.reflect.declaration.CtType;

import java.io.File;
import java.io.IOException;

public class MainController {

    private final Logger logger = LoggerFactory.getLogger(MainController.class);

    public void control() {
        File targetJarPath = new File("target-jar\\java-class-analyzer-with-spoon-1.0-SNAPSHOT.jar");
        File decompiledSourceCodePath = new File("target-jar\\out");

        JarLauncher jarLauncher = new JarLauncher(targetJarPath.getPath(), decompiledSourceCodePath.getPath());
        jarLauncher.buildModel();
        CtModel model = jarLauncher.getModel();

        for (CtType<?> compileTimeType : model.getAllTypes()) {
            System.out.println(compileTimeType.getSimpleName());
            System.out.println(compileTimeType.getFields());
        }

        try {
            if (decompiledSourceCodePath.exists()) {
                FileUtils.cleanDirectory(decompiledSourceCodePath);
            }
        } catch (IOException e) {
            logger.error("decompile 파일 삭제 과정 중 오류", e);
            throw new RuntimeException(e);
        }

        // 기초적인 사용법 https://spoon.gforge.inria.fr/launcher.html
        // API docs https://spoon.gforge.inria.fr/mvnsites/spoon-core/apidocs/
        /*
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
        */
    }
}
