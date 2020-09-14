package com.example.annotation_compiler;

import com.example.annotation.BindPath;
import com.google.auto.service.AutoService;
import com.squareup.javapoet.ClassName;
import com.squareup.javapoet.JavaFile;
import com.squareup.javapoet.MethodSpec;
import com.squareup.javapoet.TypeSpec;

import java.io.IOException;
import java.util.AbstractCollection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import javax.annotation.processing.AbstractProcessor;
import javax.annotation.processing.Filer;
import javax.annotation.processing.ProcessingEnvironment;
import javax.annotation.processing.Processor;
import javax.annotation.processing.RoundEnvironment;
import javax.annotation.processing.SupportedAnnotationTypes;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.Element;
import javax.lang.model.element.ExecutableElement;
import javax.lang.model.element.Modifier;
import javax.lang.model.element.PackageElement;
import javax.lang.model.element.TypeElement;
import javax.lang.model.element.VariableElement;


/**
 * 处理注解中的类  会在代码中 找到 专门标记activity 的注解 得到标记的类  成成activityutils 的 类
 */
@AutoService(Processor.class)// 注册为 注解解析类
//@SupportedAnnotationTypes({"com.example.annotation.BindPath"}) //使用生明 这个注解处理器 要找是那个注解
public class AnnotationCompilser extends AbstractProcessor {

    private Filer filer;  //  生成java  的 工具

    @Override
    public synchronized void init(ProcessingEnvironment processingEnv) {
        super.init(processingEnv);
        filer = processingEnv.getFiler();
    }

    /**
     * 支持java的版本
     *
     * @return
     */
    @Override
    public SourceVersion getSupportedSourceVersion() {
        return processingEnv.getSourceVersion();
    }

    /**
     * 生明 这个注解处理器 要找是那个注解
     *
     * @return
     */
    @Override
    public Set<String> getSupportedAnnotationTypes() {
        Set<String> types = new HashSet<>();
        types.add(BindPath.class.getCanonicalName());
        return types;
    }

    /**
     * 这个是 核心方法   去程序中  找标记了 的内容
     *
     * @param annotations
     * @param roundEnv
     * @return
     */
    @Override
    public boolean process(Set<? extends TypeElement> annotations, RoundEnvironment roundEnv) {
//       获取类 节点的集合  类名 方法名  变量名
        Set<? extends Element> elementsAnnotatedWith = roundEnv.getElementsAnnotatedWith(BindPath.class);
        Map<String, String> map = new HashMap<>();
        System.out.println("----------------------66666" );
        for (Element element : elementsAnnotatedWith) {
//           TypeElement   类节点
//           ExecutableElement  方法节点
//           VariableElement  变量节点
//           PackageElement  包节点
//        获取类节点
            TypeElement element1 = (TypeElement) element;
//            获取注解value
            String key = element1.getAnnotation(BindPath.class).value();
//           获取包名和类名
            String activitynae = element1.getQualifiedName().toString();
            map.put(key, activitynae + ".class");
        }

//        生成文件
        if (map.size() > 0) {
//         生成我们的工具
            createClass(map);
        }else{
            System.out.println("----------------------66666" );
//             com.example.router.Arouter.gerInstance().addactivity("main/main",MemberActivity.class);
             map.put("66/66","MemberActivity.class");
        }
        return false;
    }

    private void createClass(Map<String, String> map) {
        try {
            //   创建我们需要的方法
            MethodSpec.Builder methodSpecBuilder = MethodSpec.methodBuilder("putActity").
                    addModifiers(Modifier.PUBLIC).
                    returns(void.class);

//   创建方法里面 需要 执行的 代码
            Iterator<String> iterator = map.keySet().iterator();
            while (iterator.hasNext()) {
                String key = iterator.next();  // key =  类上 的注解的
                String activityName = map.get(key);
                System.out.println("aaaaaa    "+"=====");
                methodSpecBuilder.addStatement("com.example.router.Arouter.gerInstance().addactivity(\"" + key + "\"," + activityName + ")");
            }

            MethodSpec methodSpec = methodSpecBuilder.build();

//         创建类
            ClassName iRouter = ClassName.get("com.example.router", "IRouter");
//         创建工具
            TypeSpec typeSpec = TypeSpec.classBuilder("ActivityUtils" + System.currentTimeMillis())
                    .addModifiers(Modifier.PUBLIC).
                            addSuperinterface(iRouter).
                            addMethod(methodSpec).build();

//    构建目录
            JavaFile javaFile = JavaFile.builder("com.lww.utils", typeSpec).build();

            javaFile.writeTo(filer);
        } catch (IOException e) {

System.out.println("----------------------"+e.toString());
        }

    }
}