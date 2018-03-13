package com.xjf.servlet;

import com.xjf.annotation.MyController;
import com.xjf.annotation.MyRequestMapping;
import com.xjf.annotation.MyRequestMethod;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.lang.reflect.Method;
import java.net.URL;
import java.util.*;

public class MyServlet extends HttpServlet {

    private static final Logger LOG = LoggerFactory.getLogger(MyServlet.class);

    private static final String SCAN_PACKAGE_PATH = "com.xjf.controller";

    private List<String> classNames = new ArrayList<String>();

    private Map<String, Object> iocContext = Collections.synchronizedMap(new HashMap<String, Object>());

    private Map<String, Method> handlerMapping = Collections.synchronizedMap(new HashMap<String, Method>());

    private Map<String, Object> controllerMap = Collections.synchronizedMap(new HashMap<String, Object>());

    private Map<String, MyRequestMethod> methods = Collections.synchronizedMap(new HashMap<String, MyRequestMethod>());

    @Override
    public void init() throws ServletException {
        doScanner(SCAN_PACKAGE_PATH);
        doInstance();
        initHandlerMapping();
    }


    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        super.service(req, resp);
    }

    private void doScanner(String scanPackage) {
        URL url = this.getClass().getClassLoader().getResource("/" + scanPackage.replaceAll("\\.","/"));
        File dir = new File(url.getFile());
        for (File file : dir.listFiles()) {
            if (file.isDirectory()) {
                doScanner(scanPackage + "." + file.getName());
            } else if (file.getName().endsWith(".class")){
                String className = scanPackage + "." + file.getName();
                classNames.add(className);
            }
        }
    }

    private void doInstance() {
        if (classNames.isEmpty()) {
            return;
        }
        for (String className : classNames)
            try {
                Class<?> clazz = Class.forName(className);
                if (clazz.isAnnotationPresent(MyController.class)) {
                    iocContext.put(toLowerFirstWord(clazz.getSimpleName()), clazz.newInstance());
                } else {
                    continue;
                }
            } catch (Exception e) {
                LOG.error(e.getMessage());
                continue;
            }
    }

    private void initHandlerMapping() {
        if (iocContext.isEmpty()) {
            return;
        }
        for (Map.Entry<String, Object> entry : iocContext.entrySet()) {
            Class<? extends Object> clazz = entry.getValue().getClass();
            String baseUrl = "";
            if (!clazz.isAnnotationPresent(MyController.class)) {
                continue;
            }
            if (clazz.isAnnotationPresent(MyRequestMapping.class)) {
                MyRequestMapping myRequestMapping = clazz.getAnnotation(MyRequestMapping.class);
                baseUrl = myRequestMapping.value();
            }
            Method[] methods = clazz.getMethods();
            for (Method method : methods) {
                if (!method.isAnnotationPresent(MyRequestMapping.class)) {
                    continue;
                }
                MyRequestMapping methodMapping = method.getAnnotation(MyRequestMapping.class);
                String url = methodMapping.value();
                url =(baseUrl+"/"+url).replaceAll("/+", "/");
                handlerMapping.put(url, method);
                try {
                    controllerMap.put(url, clazz.newInstance());
                } catch (InstantiationException e) {
                    LOG.error(e.getMessage());
                } catch (IllegalAccessException e) {
                    LOG.error(e.getMessage());
                }
                LOG.info("Register handlerMapping : " + url + "," + method + " success!");
            }
        }
    }

    private String toLowerFirstWord(String name) {
        char[] charArray = name.toCharArray();
        charArray[0] += 32;
        return String.valueOf(charArray);
    }
}
