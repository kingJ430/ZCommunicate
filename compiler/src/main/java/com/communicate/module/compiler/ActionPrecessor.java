package com.communicate.module.compiler;

import com.communicate.module.annotation.Action;
import com.communicate.module.annotation.Provider;
import com.communicate.module.compiler.model.ProviderElement;
import com.google.auto.service.AutoService;
import com.squareup.javapoet.ClassName;
import com.squareup.javapoet.JavaFile;
import com.squareup.javapoet.MethodSpec;
import com.squareup.javapoet.TypeName;
import com.squareup.javapoet.TypeSpec;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.annotation.processing.AbstractProcessor;
import javax.annotation.processing.Filer;
import javax.annotation.processing.Messager;
import javax.annotation.processing.ProcessingEnvironment;
import javax.annotation.processing.Processor;
import javax.annotation.processing.RoundEnvironment;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.Element;
import javax.lang.model.element.ElementKind;
import javax.lang.model.element.Modifier;
import javax.lang.model.element.TypeElement;
import javax.lang.model.util.Types;

@AutoService(Processor.class)
public class ActionPrecessor extends AbstractProcessor {

    private Messager messager;
    private String mModuleName;
    private Filer mFiler;
    private Types typeUtils;

    @Override
    public synchronized void init(ProcessingEnvironment processingEnv) {
        super.init(processingEnv);
        messager = processingEnv.getMessager();
        mFiler = processingEnv.getFiler();
        typeUtils = processingEnv.getTypeUtils();
        Map<String, String> processingEnvOptions = processingEnv.getOptions();
        for (Map.Entry<String, String> _stringStringEntry : processingEnvOptions.entrySet()) {
            System.out.println(_stringStringEntry.getKey() + ":" + _stringStringEntry.getValue());
            if ("routerType".equals(_stringStringEntry.getKey())) {
                mModuleName = _stringStringEntry.getValue();
                break;
            }
        }
    }

    @Override
    public Set<String> getSupportedAnnotationTypes() {
        Set<String> supportTypes = new HashSet<>();
        supportTypes.add(Action.class.getCanonicalName());
        supportTypes.add(Provider.class.getCanonicalName());
        return supportTypes;
    }

    @Override
    public SourceVersion getSupportedSourceVersion() {
        return SourceVersion.latestSupported();
    }

    @Override
    public boolean process(Set<? extends TypeElement> set, RoundEnvironment roundEnvironment) {
        if(set.isEmpty()) {
            return false;
        } else {
            Set<? extends Element> provideList = roundEnvironment.getElementsAnnotatedWith(Provider.class);
//            Set<? extends Element> actionList = roundEnvironment.getElementsAnnotatedWith(Action.class);
            if (provideList.isEmpty() ) {
                return false;
            } else {
                String aptModuleName = "PrividerMap" + mModuleName;
                generateProviderMapping(aptModuleName, roundEnvironment,provideList);
            }
        }
        return false;
    }

    private void generateProviderMapping(String fileName, RoundEnvironment roundEnv,Set<? extends Element> provideList) {

        ClassName communicateUtil = ClassName.get("com.communicate.module.library.utils", "CommunicateUtil");

        MethodSpec.Builder initBuilder = MethodSpec.methodBuilder("init")
                .addModifiers(Modifier.PUBLIC, Modifier.STATIC)
                .returns(TypeName.VOID);

        ClassName arrayList = ClassName.get("java.util", "ArrayList");
        ClassName providerClassName = null;
        List<ProviderElement> providerElements = new ArrayList<>();
        for (Element element : provideList) {
            if (element.getKind() == ElementKind.CLASS) {
                providerClassName = ClassName.get((TypeElement) element);
                Provider provider = element.getAnnotation(Provider.class);
                ProviderElement providerElement = new ProviderElement();
                providerElement.setProviderName(provider.name());
                providerElement.setProviderCalssName(providerClassName.simpleName().toLowerCase());
                providerElements.add(providerElement);
                initBuilder.addStatement("$T $N = new $T()", providerClassName, providerClassName.simpleName().toLowerCase(), providerClassName);
//                initBuilder.addCode("if( providerMap.get($S) == null ){\n", provider.name());
//                initBuilder.addCode("providerMap.put($S, new $T());\n}\n", provider.name(), arrayList);
//                initBuilder.addCode("(($T)providerMap.get($S)).add($N);\n", arrayList, provider.name(), providerClassName.simpleName().toLowerCase());

                initBuilder.addStatement("$T.map($S,$N)",communicateUtil,provider.name(),providerClassName.simpleName().toLowerCase());
            }
        }

//        for (Element element : actionList) {
//            if (element.getKind() == ElementKind.CLASS) {
//                Action action = element.getAnnotation(Action.class);
//
//                ClassName actionClassName = ClassName.get((TypeElement) element);
//                initBuilder.addStatement("$T $N = new $T()", actionClassName, actionClassName.simpleName().toLowerCase(), actionClassName);
//                String name = actionClassName.simpleName().toLowerCase() +".getName()";
//                for(ProviderElement providerElement : providerElements) {
//                    if (action.privider().equals(providerElement.getProviderName())) {
//                        initBuilder.addCode(providerElement.getProviderCalssName() + ".registerAction($N,$N);",name,actionClassName.simpleName().toLowerCase());
//                        break;
//                    }
//                }
//
//            }
//        }
        TypeSpec providerInit = TypeSpec.classBuilder(fileName)
                .addModifiers(Modifier.PUBLIC)
                .addMethod(initBuilder.build())
                .build();
        try {
            JavaFile.builder("com.provider", providerInit)
                    .build()
                    .writeTo(mFiler);
        } catch (Throwable e) {
            e.printStackTrace();
        }
    }
}
