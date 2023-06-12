package com.jiajia.butterkniftcompilerlib;

import com.google.auto.service.AutoService;
import com.jiajia.butterknifeannotationlib.MyBindView;
import com.squareup.javapoet.ClassName;
import com.squareup.javapoet.JavaFile;
import com.squareup.javapoet.MethodSpec;
import com.squareup.javapoet.TypeSpec;

import java.io.IOException;
import java.lang.annotation.Annotation;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.annotation.processing.AbstractProcessor;
import javax.annotation.processing.Filer;
import javax.annotation.processing.ProcessingEnvironment;
import javax.annotation.processing.Processor;
import javax.annotation.processing.RoundEnvironment;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.Element;
import javax.lang.model.element.Modifier;
import javax.lang.model.element.TypeElement;
import javax.lang.model.util.Elements;

@AutoService(Processor.class)
public class ButterKnifeProcess extends AbstractProcessor {

    private Filer mFiler;
    private Elements mElementsUtils;

    @Override
    public synchronized void init(ProcessingEnvironment processingEnv) {
        super.init(processingEnv);
        mFiler = processingEnv.getFiler();
        mElementsUtils = processingEnv.getElementUtils();
    }


    // 1.指定处理的版本
    @Override
    public SourceVersion getSupportedSourceVersion() {
        return SourceVersion.latestSupported();
    }

    // 2.给到需要处理的注解
    @Override
    public Set<String> getSupportedAnnotationTypes() {
        Set<String> types = new LinkedHashSet<>();
        for (Class<? extends  Annotation> annotation : getSupportedAnnotations())
            types.add(annotation.getCanonicalName());
        return types;
    }

    private Set<Class<? extends Annotation>> getSupportedAnnotations() {
        Set<Class<? extends Annotation>> annotations = new LinkedHashSet<>();
        // 需要解析的注解BindView OnClick等
        annotations.add(MyBindView.class);
        return annotations;
    }

    @Override
    public boolean process(Set<? extends TypeElement> set, RoundEnvironment roundEnvironment) {

        Set<? extends Element> elements = roundEnvironment.getElementsAnnotatedWith(MyBindView.class);

        // 解析属性，主要是分清楚是那些文件的注解
        Map<Element, List<Element>> elementListMap = new LinkedHashMap<>();
        for (Element element : elements) {
            Element encloseElement = element.getEnclosingElement();
            List<Element> viewBindElement = elementListMap.get(encloseElement);
            if (viewBindElement == null) {
                viewBindElement = new ArrayList<>();
                elementListMap.put(encloseElement, viewBindElement);
            }
            viewBindElement.add(element);
        }

        // 生成代码
        for (Map.Entry<Element, List<Element>> entry : elementListMap.entrySet()) {
            Element encloseElement = entry.getKey(); // 文件
            List<Element> viewBindElements = entry.getValue(); // 文件中的注解
            // public final class xxxActivity_ViewBinding implements MyUnBinder
            String activityClassNameStr = encloseElement.getSimpleName().toString();
            ClassName unBinderClassName = ClassName.get("com.jiajia.butterknife","MyUnBinder");
            ClassName activityClassName = ClassName.bestGuess(activityClassNameStr);
            TypeSpec.Builder classBuilder = TypeSpec.classBuilder(activityClassNameStr + "_ViewBinding")
                    .addModifiers(Modifier.FINAL, Modifier.PUBLIC).addSuperinterface(unBinderClassName)
                    .addField(activityClassName, "target", Modifier.PRIVATE);

            // 构造函数
            MethodSpec.Builder constructorMethodBuilder =MethodSpec.constructorBuilder()
                    .addParameter(activityClassName, "target").addModifiers(Modifier.PUBLIC);
            constructorMethodBuilder.addStatement("this.target = target"); // 函数内容

            // 实现UnBinder接口中的方法
            ClassName callerSuperClassName = ClassName.get("androidx.annotation", "CallSuper");
            MethodSpec.Builder unbinderMethodBuilder = MethodSpec.methodBuilder("unbind")
                    .addAnnotation(Override.class)
                    .addAnnotation(callerSuperClassName)
                    .addModifiers(Modifier.FINAL, Modifier.PUBLIC);


            for (Element viewBindElement : viewBindElements) {
                String fileName = viewBindElement.getSimpleName().toString();
                ClassName utilsClassName = ClassName.get("com.jiajia.butterknife","Utils");
                int resId = viewBindElement.getAnnotation(MyBindView.class).value();
                constructorMethodBuilder.addStatement("target.$L = $T.findViewById(target, $L)", fileName, utilsClassName, resId);
            }

            classBuilder.addMethod(constructorMethodBuilder.build()); // 写
            classBuilder.addMethod(unbinderMethodBuilder.build()); // 写

            try {
                String packageName = mElementsUtils.getPackageOf(encloseElement).getQualifiedName().toString();
                JavaFile.builder(packageName, classBuilder.build())
                         .addFileComment("MyButterKnife自动生成").build().writeTo(mFiler);
            } catch (IOException e) {
                e.printStackTrace();
                System.out.println("翻车了");
            }
        }

        return false;
    }
}