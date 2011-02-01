package system.libraries;

import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.StringTokenizer;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.lang.reflect.*;

/**
 *
 * @author tediscript
 */
public class Dispatcher extends HttpServlet {

    private HttpServletRequest request;
    private HttpServletResponse response;
    private RequestMethod method;

    protected void processRequest() {

        String uri = request.getServletPath();
        LinkedList<String> args = new LinkedList<String>();
        StringTokenizer tokenizer = new StringTokenizer(uri, "/");
        while (tokenizer.hasMoreTokens()) {
            args.addLast(tokenizer.nextToken());
        }
        //harus masuk konfig default routing
        String controllerPath = "application.controllers.";
        String strClass = controllerPath + "Welcome";
        String strMethod = "index";
        if (args.size() > 0) {
            strClass = args.removeFirst();
            String firstLetter = strClass.substring(0, 1);  // Get first letter
            String remainder = strClass.substring(1);
            strClass = controllerPath + firstLetter.toUpperCase() + remainder;
        }
//        if (args.size() > 0) {
//            strMethod = args.removeFirst();
//        }

        try {
            routes(strClass, strMethod, args);
        } catch (ClassNotFoundException ex) {
//            System.out.println("class not found");
            routesError();
        } catch (NoSuchMethodException ex) {
//            System.out.println("no such method");
            routesError();
        } catch (IllegalArgumentException ex) {
//            System.out.println("illegal argument");
            routesError();
        } catch (NullPointerException ex) {
//            System.out.println("null pointer");
            routesError();
        }
    }

    private void routes(String strClass, String strMethod, LinkedList argsLink) throws ClassNotFoundException, NoSuchMethodException, IllegalArgumentException {
        try {
            Class c = Class.forName(strClass);
            Object i = c.newInstance();
            Class[] params0 = new Class[]{Capsule.class};
            Object[] args0 = new Object[]{new Capsule(request, response, method)};
            Method m0 = c.getSuperclass().getDeclaredMethod("processRequest", params0);
            m0.invoke(i, args0);
            Method mtd = null;
            Type types[] = null;
            Method methods[] = c.getDeclaredMethods();
            for (Method m : methods) {
                if (m.getName().equalsIgnoreCase(strMethod)) {
                    Type tps[] = m.getGenericParameterTypes();
                    if (tps.length == argsLink.size()) {
                        mtd = m;
                        types = tps;
                    }
                }
            }
            Class params[] = new Class[argsLink.size()];
            Object args[] = new Object[argsLink.size()];
            int j = 0;
            for (Type t : types) {
                String token = t.toString();
                if (token.equals("int") || token.equals("class java.lang.Integer")) {
                    params[j] = Integer.class;
                    args[j] = Integer.parseInt(argsLink.removeFirst().toString());
                } else if (token.equals("boolean") || token.equals("class java.lang.Boolean")) {
                    params[j] = Boolean.class;
                    args[j] = Boolean.parseBoolean(argsLink.removeFirst().toString());
                } else if (token.equals("long") || token.equals("class java.lang.Long")) {
                    params[j] = Long.class;
                    args[j] = Long.parseLong(argsLink.removeFirst().toString());
                } else if (token.equals("float") || token.equals("class java.lang.Float")) {
                    params[j] = Float.class;
                    args[j] = Float.parseFloat(argsLink.removeFirst().toString());
                } else if (token.equals("double") || token.equals("class java.lang.Double")) {
                    params[j] = Double.class;
                    args[j] = Double.parseDouble(argsLink.removeFirst().toString());
                } else if (token.equals("char") || token.equals("class java.lang.Character")) {
                    params[j] = Character.class;
                    args[j] = argsLink.removeFirst().toString().toCharArray()[0];
                } else if (token.equals("class java.lang.String")) {
                    params[j] = String.class;
                    args[j] = argsLink.removeFirst().toString();
                } else {
                    params[j] = Object.class;
                    args[j] = argsLink.removeFirst();
                }
                j++;
            }
            Object r = mtd.invoke(i, args);
        } catch (InstantiationException ex) {
            Logger.getLogger(Dispatcher.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            Logger.getLogger(Dispatcher.class.getName()).log(Level.SEVERE, null, ex);
        } catch (InvocationTargetException ex) {
            Logger.getLogger(Dispatcher.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void routesError() {
        try {
            String error404 = "application.controllers.Error404";
            String strMethod = "index";
            Class c = Class.forName(error404);
            Object i = c.newInstance();
            Class[] params0 = new Class[]{Capsule.class};
            Object[] args0 = new Object[]{new Capsule(request, response, method)};
            Method m0 = c.getSuperclass().getDeclaredMethod("processRequest", params0);
            m0.invoke(i, args0);
            Method m = c.getDeclaredMethod(strMethod, new Class[0]);
            Object r = m.invoke(i, new Object[0]);
        } catch (IllegalArgumentException ex) {
            Logger.getLogger(Dispatcher.class.getName()).log(Level.SEVERE, null, ex);
        } catch (InvocationTargetException ex) {
            Logger.getLogger(Dispatcher.class.getName()).log(Level.SEVERE, null, ex);
        } catch (NoSuchMethodException ex) {
            Logger.getLogger(Dispatcher.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SecurityException ex) {
            Logger.getLogger(Dispatcher.class.getName()).log(Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            Logger.getLogger(Dispatcher.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            Logger.getLogger(Dispatcher.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(Dispatcher.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    void invoke(String aClass, String aMethod, Class[] params, Object[] args) throws ClassNotFoundException, InstantiationException, IllegalAccessException, NoSuchMethodException, IllegalArgumentException, InvocationTargetException {
        Class c = Class.forName(aClass);
        Object i = c.newInstance();
        Class[] params0 = new Class[]{Capsule.class};
        Object[] args0 = new Object[]{new Capsule(request, response, method)};
        Method m0 = c.getSuperclass().getDeclaredMethod("processRequest", params0);
        m0.invoke(i, args0);
        Method m = c.getDeclaredMethod(aMethod, params);
        Object r = m.invoke(i, args);
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException,
            IOException {
        this.request = request;
        this.response = response;
        this.method = RequestMethod.GET;
        processRequest();
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException,
            IOException {
        this.request = request;
        this.response = response;
        this.method = RequestMethod.POST;
        processRequest();
    }
}
