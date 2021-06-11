package wust.fxp.develop.platfrom.auto;

import lombok.extern.slf4j.Slf4j;
import wust.fxp.develop.platfrom.constant.StyleType;
import wust.fxp.develop.platfrom.utils.CollectionUtils;
import wust.fxp.develop.platfrom.utils.ObjectUtil;
import wust.fxp.develop.platfrom.utils.StringUtils;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.*;

/**
 * 用于代码风格检查和注解扫描
 * @author 凡兴鹏
 * @create 2021-04-28 9:18
 */
@Slf4j
public class AutoScann {

    //扫描标志，为true表示扫描结束
    private boolean scannTag = false;
    //风格检查队列(类）
    private Vector<Class> styleCheckClassQueue = new Vector<>();
    //风格检查队列(属性）
    private Map<Class, Vector<Field>> styleCheckFieldQueue = new HashMap<>();
    //类注解队列
    private Map<Class<? extends Annotation>, Map<Class, Object>> classAnnQueue = new HashMap<>();
    //属性注解队列
    private Map<Class<? extends Annotation>, Map<Class, Map<Field, Object>>> fieldAnnQueue = new HashMap<>();

    //自动扫描注解，把对应注解的类或者属性放入相应队列
    public static AutoScann autoScann(String baseScannDir,String packageName) throws ClassNotFoundException {
        AutoScann autoScann = new AutoScann();
        //扫描包下的所有类到类风格检查队列
        autoScann.styleCheckClassQueue.addAll(new PackageScanner(baseScannDir,packageName).getClasses());
        //扫描类风格检查队列的类的属性添加到属性风格检查队列
        for (Class clazz : autoScann.styleCheckClassQueue) {
            //如果是接口和注解
            if (clazz.isInterface() || clazz.isAnnotation()) {
                continue;
            }
            List fields = new Vector<Field>();
            //如果是一个枚举,不排除静态类
            if (clazz.isEnum()) {
                fields = getAllField(clazz, false);
                if (!CollectionUtils.isEmpty(fields)) {
                    autoScann.styleCheckFieldQueue.put(clazz, new Vector<>(fields));
                }
                continue;
            }
            fields = getAllField(clazz, true);
            if (!CollectionUtils.isEmpty(fields)) {
                autoScann.styleCheckFieldQueue.put(clazz, new Vector<>(fields));
            }
        }
        return autoScann;
    }


    //不包含静态属性
    private static List<Field> getAllField(Class<?> entityClass, boolean staticFlag) {
        List<Field> fieldList = new Vector<Field>();
        Field[] fields = entityClass.getDeclaredFields();
        for (Field field : fields) {
            //排除枚举值
            if (field.isEnumConstant()) {
                continue;
            }
            field = getStaticFeild(field, staticFlag);
            if (field != null) {
                fieldList.add(field);
            }
        }
        //排除从父类继承过来的属性
        Class<?> superClass = entityClass.getSuperclass();
        if (superClass != null
                && !superClass.equals(Object.class)
                && ((!Map.class.isAssignableFrom(superClass)
                && !Collection.class.isAssignableFrom(superClass)))) {
            fieldList.removeAll(getAllField(entityClass.getSuperclass(), false));
        }
        return fieldList;
    }

    private static Field getStaticFeild(Field field, boolean staticFlag) {
        if (staticFlag) {
            //排除静态字段
            if (!Modifier.isStatic(field.getModifiers())) {
                return field;
            }
        } else {
            return field;
        }
        return null;
    }

    //对编码风格进行检查
    public void checkStyle(StyleType classStyle, StyleType feildStyle) {
        //检查类名风格
        checkClassStyle(classStyle);
        //检查属性风格
        checkFeildStyle(feildStyle);
    }

    //检查类风格
    public void checkClassStyle(StyleType classStyle) {
        boolean styleFlag = true;
        for (Class clazz : styleCheckClassQueue) {

            styleFlag = classStyle.checkStyle(clazz.getSimpleName());
            if (!styleFlag) {
                log.info(clazz.getName() + "类名称不符合" + classStyle.getRuleDesc() + "风格");
            }
        }
    }

    public List scannClassAnn(Class<? extends Annotation> annClass, Class proClass) throws Exception {
        List<Object> annValueVector = new Vector<>();
        //扫描类注解
        if (!CollectionUtils.isEmpty(styleCheckFieldQueue)) {
            //扫描风格属性队列,添加类注解Map
            for (Class clazzEle : styleCheckFieldQueue.keySet()) {
                scannClassAnn(clazzEle,annClass,proClass);
            }
        }
        return annValueVector;
    }

    public void scannFieldAnn(Class<? extends Annotation> annClass, Class proClass) throws Exception {
        //扫描类注解
        if (!CollectionUtils.isEmpty(styleCheckFieldQueue)) {
            //扫描风格属性队列
            for (Class clazzEle : styleCheckFieldQueue.keySet()) {
                //扫描风格属性队列,添加属性注解Map
                List<Field> fieldList=styleCheckFieldQueue.get(clazzEle);
                if(!CollectionUtils.isEmpty(fieldList)){
                    for(Field field:fieldList){
                        scannFeildAnn(clazzEle,field,annClass,proClass);
                    }
                }
            }
        }
    }

    //扫描指定类的指定类型注解到注解队列中
    private Map scannClassAnn(Class clazz, Class<? extends Annotation> annClass, Class proClass) throws Exception, InstantiationException {
        Map annValueMap=classAnnQueue.get(annClass);
        if(annValueMap==null){
            annValueMap=new HashMap();
        }
        //获取类的指定注解
        Annotation annotation = clazz.getDeclaredAnnotation(annClass);
        if (ObjectUtil.isEmpty(annotation)) {
            return annValueMap;
        }
        Object proValue = getAnnValue(annotation, proClass);
        if (!ObjectUtil.isEmpty(proValue)) {
            annValueMap.put(clazz, proValue);
        }
        //放入类注解队列
        classAnnQueue.put(annClass, annValueMap);
        return annValueMap;
    }

    //扫描指定类的指定属性的指定注解到Map中
    private Map scannFeildAnn(Class clazz, Field field, Class<? extends Annotation> annClass, Class proClass) throws Exception, InstantiationException {
        Map annClassValueMap = fieldAnnQueue.get(annClass);
        if (annClassValueMap == null) {
            annClassValueMap = new HashMap<>();
        }
        Map annFieldMap = (HashMap)annClassValueMap.get(clazz);
        if(annFieldMap==null){
            annFieldMap=new HashMap();
        }
        Annotation annotation = null;
        //扫描属性注解
        annotation = field.getAnnotation(annClass);
        if (ObjectUtil.isEmpty(annotation)) {
            return annFieldMap;
        }
        //获取注解值并存储注解结果
        Object annValue = getAnnValue(annotation, proClass);
        if (ObjectUtil.isEmpty(annValue)) {
            return annClassValueMap;
        }else {
            annFieldMap.put(field, annValue);
            //注解值的Map放入类的属性注解Map中
            annClassValueMap.put(clazz,annFieldMap);
            //最后放入属性队列中
            fieldAnnQueue.put(annClass, annClassValueMap);
        }
        return annFieldMap;
    }

    //获取注解上的值并用指定类接收值并返回
    Object getAnnValue(Annotation annotation, Class proClass) throws Exception {
        String annMethodName = "";
        String setMethodName = "";
        //获取注解的方法
        Class annCla =  annotation.getClass();
        Method[] methods = annCla.getMethods();
        if (!CollectionUtils.isEmpty(methods)) {
            //获取接收注解值的类的实例
            //实例需要有和方法名相同属性名(不忽略大小写)和与方法返回值类型相同的属性用于接收注解值，还需要对应的空构造函数和get、set方法
            Object pro = proClass.newInstance();
            for (Method annMethod : methods) {
                annMethodName = annMethod.getName();
                if (!StringUtils.isEmpty(annMethodName) && annMethodName.length() > 0) {
                    setMethodName = "set" + annMethodName.substring(0, 1).toUpperCase();
                    if (annMethodName.length() > 1) {
                        setMethodName = setMethodName + annMethodName.substring(1, annMethodName.length());
                    }
                }

                Object annValue = null;
                //获取接收类的get、set方法
                Method[] proMethods = proClass.getMethods();
                if (!CollectionUtils.isEmpty(proMethods)) {
                    for (Method proMethod : proMethods) {
                        if (setMethodName.equals(proMethod.getName())) {
                            proMethod.setAccessible(true);
                            annValue = annMethod.invoke(annotation);
                            if (ObjectUtil.isEmpty(annValue)) {
                                annValue = annMethod.getDefaultValue();
                            }
                            //注解调用方法并且赋值给接收类
                            proMethod.invoke(pro, annValue);
                        }
                    }
                }
            }
            log.info("扫描到的注解值为" + pro.toString());
            return pro;
        }
        return null;
    }


    public void checkFeildStyle(StyleType classStyle) {
        boolean styleFlag = true;
        for (Class key : styleCheckFieldQueue.keySet()) {
            for (Field field : styleCheckFieldQueue.get(key)) {
                styleFlag = classStyle.checkStyle(field.getName());
                if (!styleFlag) {
                    log.info(key.getName() + "类的" + field.getName() + "属性的名称不符合" + classStyle.getRuleDesc() + "风格");
                }
            }
        }

    }


    public boolean isScannTag() {
        return scannTag;
    }

    public void setScannTag(boolean scannTag) {
        this.scannTag = scannTag;
    }

    public Vector<Class> getStyleCheckClassQueue() {
        return styleCheckClassQueue;
    }

    public void setStyleCheckClassQueue(Vector<Class> styleCheckClassQueue) {
        this.styleCheckClassQueue = styleCheckClassQueue;
    }

    public Map<Class, Vector<Field>> getStyleCheckFieldQueue() {
        return styleCheckFieldQueue;
    }

    public void setStyleCheckFieldQueue(Map<Class, Vector<Field>> styleCheckFieldQueue) {
        this.styleCheckFieldQueue = styleCheckFieldQueue;
    }

    public Map<Class<? extends Annotation>, Map<Class, Object>> getClassAnnQueue() {
        return classAnnQueue;
    }

    public void setClassAnnQueue(Map<Class<? extends Annotation>, Map<Class, Object>> classAnnQueue) {
        this.classAnnQueue = classAnnQueue;
    }

    public Map<Class<? extends Annotation>, Map<Class, Map<Field, Object>>> getFieldAnnQueue() {
        return fieldAnnQueue;
    }

    public void setFieldAnnQueue(Map<Class<? extends Annotation>, Map<Class, Map<Field, Object>>> fieldAnnQueue) {
        this.fieldAnnQueue = fieldAnnQueue;
    }
}
