package system.libraries;

import javax.servlet.http.HttpServletRequest;

/**
 *
 * @author tediscript
 */
public class Input {

    private HttpServletRequest request;
    private RequestMethod method;

    public Input() {
    }

    public Input(HttpServletRequest request, RequestMethod method) {
        this.request = request;
        this.method = method;
    }

    public String ipAddress(){
        return request.getRemoteAddr();
    }
    

    public String get(String parameter) {
        String reval = null;
        if (method == RequestMethod.GET) {
            reval = request.getParameter(parameter);
        }
        return reval;
    }

    public String post(String parameter) {
        String reval = null;
        if (method == RequestMethod.POST) {
            reval = request.getParameter(parameter);
        }
        return reval;
    }

    public HttpServletRequest getRequest() {
        return request;
    }

    public void setRequest(HttpServletRequest request) {
        this.request = request;
    }

    public RequestMethod getMethod() {
        return method;
    }

    public void setMethod(RequestMethod method) {
        this.method = method;
    }
}
