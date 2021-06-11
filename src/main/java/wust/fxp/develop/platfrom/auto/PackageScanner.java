package wust.fxp.develop.platfrom.auto;

import lombok.extern.slf4j.Slf4j;
import wust.fxp.develop.platfrom.utils.ObjectUtil;


import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.net.JarURLConnection;
import java.net.URL;
import java.net.URLDecoder;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;
import java.util.Map;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

/**
 * 扫描包中的所有对象
 */
@Slf4j
public class PackageScanner {

    public static String separator=File.separator;
    private List<Class<?>> classes;
    public static String projectPath=System.getProperty("user.dir");
    public static String packagePath = projectPath + separator+"src"+separator+"main"+separator+"java";

    /*
     * 无参构造方法，内部调用带参的构造方法。
     *
     * @throw classNotFound
     *
     */
    public PackageScanner() throws ClassNotFoundException {
        this(separator+"src"+separator+"main"+separator+"java","");
    }

    /*
     * 实现，调用fileScanner进行目录扫描和加载
     *
     * @param String 传入需要扫描的包
     *
     * @throw classNotFound
     */
    public PackageScanner(String baseScannDir,String basePackage) throws ClassNotFoundException {
        packagePath = projectPath + baseScannDir;
        String filePath = packagePath + basePackage.replace(".", separator);
        classes = new ArrayList<Class<?>>();
        fileScanner(new File(filePath));
    }

    private void fileScanner(File file) throws ClassNotFoundException {
        if (file.isFile() && file.getName().lastIndexOf(".java") == file.getName().length() - 5) {//5是".java"的长度
            String filePath = file.getAbsolutePath();
            String qualifiedName = filePath.substring(packagePath.length(), filePath.length() - 5).replace('\\', '.');
            classes.add(Class.forName(qualifiedName));
            return;
        } else if (file.isDirectory()) {
            for (File f : file.listFiles())
                fileScanner(f);
        }
    }

    /*
     * 得到加载到的类对象的List,返回的是ArrayList
     */
    public List<Class<?>> getClasses() {
        return this.classes;
    }

    private List<Class<?>> getClassesWithAnnotationFromPackage(String packageName, Class<? extends Annotation> annotation) {
        List<Class<?>> classList = new ArrayList<Class<?>>();
        String packageDirName = packageName.replace('.', '/');
        Enumeration<URL> dirs = null;

        try {
            dirs = Thread.currentThread().getContextClassLoader().getResources(packageDirName);
        }
        catch (IOException e) {
            log.error("Failed to get resource", e);
            return null;
        }

        while (dirs.hasMoreElements()) {
            URL url = dirs.nextElement();//file:/D:/E/workspaceGitub/springboot/JSONDemo/target/classes/com/yq/wust.fxp.develop.platfrom.controller
            String protocol = url.getProtocol();//file

            //https://docs.oracle.com/javase/7/docs/api/java/net/URL.html
            //http, https, ftp, file, and jar
            //本文只需要处理file和jar
            if ("file".equals(protocol) ) {
                String filePath = null;
                try {
                    filePath = URLDecoder.decode(url.getFile(), "UTF-8");///D:/E/workspaceGitub/springboot/JSONDemo/target/classes/com/yq/wust.fxp.develop.platfrom.controller
                }
                catch (UnsupportedEncodingException e) {
                    log.error("Failed to decode class file", e);
                }

                filePath = filePath.substring(1);
                getClassesWithAnnotationFromFilePath(packageName, filePath, classList, annotation);
            } else if ("jar".equals(protocol)) {
                JarFile jar = null;
                try {
                    jar = ((JarURLConnection) url.openConnection()).getJarFile();
                    //扫描jar包文件 并添加到集合中
                }
                catch (Exception e) {
                    log.error("Failed to decode class jar", e);
                }

                List<Class<?>> alClassList = new ArrayList<Class<?>>();
                findClassesByJar(packageName, jar, alClassList);
                getClassesWithAnnotationFromAllClasses(alClassList, annotation, classList);
            }
            else {
                log.warn("can't process the protocol={}", protocol);
            }
        }

        return classList;
    }
    private static void findClassesByJar(String pkgName, JarFile jar, List<Class<?>> classes) {
        String pkgDir = pkgName.replace(".", "/");
        Enumeration<JarEntry> entry = jar.entries();

        while (entry.hasMoreElements()) {
            // 获取jar里的一个实体 可以是目录 和一些jar包里的其他文件 如META-INF等文
            JarEntry jarEntry = entry.nextElement();
            String name = jarEntry.getName();
            // 如果是以/开头的
            if (name.charAt(0) == '/') {
                // 获取后面的字符串
                name = name.substring(1);
            }

            if (jarEntry.isDirectory() || !name.startsWith(pkgDir) || !name.endsWith(".class")) {
                continue;
            }
            //如果是一个.class文件 而且不是目录
            // 去掉后面的".class" 获取真正的类名
            String className = name.substring(0, name.length() - 6);
            Class<?> tempClass = loadClass(className.replace("/", "."));
            // 添加到集合中去
            if (tempClass != null) {
                classes.add(tempClass);
            }
        }
    }

    /**
     * 加载类
     * @param fullClsName 类全名
     * @return
     */
    private static Class<?> loadClass(String fullClsName ) {
        try {
            return Thread.currentThread().getContextClassLoader().loadClass(fullClsName );
        } catch (ClassNotFoundException e) {
            log.error("PkgClsPath loadClass", e);
        }
        return null;
    }


    //filePath is like this 'D:/E/workspaceGitub/springboot/JSONDemo/target/classes/com/yq/wust.fxp.develop.platfrom.controller'
    private void getClassesWithAnnotationFromFilePath(String packageName, String filePath, List<Class<?>> classList,
                                                      Class<? extends Annotation> annotation) {
        Path dir = Paths.get(filePath);//D:\E\workspaceGitub\springboot\JSONDemo\target\classes\com\yq\wust.fxp.develop.platfrom.controller

        try (DirectoryStream<Path> stream = Files.newDirectoryStream(dir)) {
            for(Path path : stream) {
                String fileName = String.valueOf(path.getFileName()); // for current dir , it is 'helloworld'
                //如果path是目录的话， 此处需要递归，
                boolean isDir = Files.isDirectory(path);
                if(isDir) {
                    getClassesWithAnnotationFromFilePath(packageName + "." + fileName , path.toString(), classList, annotation);
                }
                else  {
                    String className = fileName.substring(0, fileName.length() - 6);

                    Class<?> classes = null;
                    String fullClassPath = packageName + "." + className;
                    try {
                        log.info("fullClassPath={}", fullClassPath);
                        classes = Thread.currentThread().getContextClassLoader().loadClass(fullClassPath);
                    }
                    catch (ClassNotFoundException e) {
                        log.error("Failed to find class={}", fullClassPath, e);
                    }

                    if (null != classes && null != classes.getAnnotation(annotation)) {
                        classList.add(classes);
                    }
                }
            }
        }
        catch (IOException e) {
            log.error("Failed to read class file", e);
        }
    }

    private void getClassesWithAnnotationFromAllClasses(List<Class<?>> inClassList,
                                                        Class<? extends Annotation> annotation, List<Class<?>> outClassList) {
        for(Class<?> myClasss : inClassList) {
            if (null != myClasss && null != myClasss.getAnnotation(annotation)) {
                outClassList.add(myClasss);
            }
        }
    }
    private void geMethodWithAnnotationFromFilePath(String fullClassPath, Map<Annotation, Method> checkIdMethodMap,
                                                    Annotation checkAnn) {
        Class<?> classes = null;
        try {
            log.info("fullClassPath={}", fullClassPath);
            classes = Thread.currentThread().getContextClassLoader().loadClass(fullClassPath);

            Method[] methods = classes.getDeclaredMethods();

            for (Method method : methods) {
                checkAnn = method.getAnnotation(checkAnn.getClass());
                if (!ObjectUtil.isEmpty(checkAnn)) {
                    checkIdMethodMap.put (checkAnn, method );
                }
            }
        }
        catch (ClassNotFoundException e) {
            log.error("Failed to find class={}", fullClassPath, e);
        }
    }


}