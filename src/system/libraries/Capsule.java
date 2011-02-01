package system.libraries;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author tediscript
 */
public class Capsule extends java.lang.Object {

    private HttpServletRequest request;
    private HttpServletResponse response;
    private RequestMethod method;

    public Capsule(HttpServletRequest request, HttpServletResponse response, RequestMethod method) {
        this.request = request;
        this.response = response;
        this.method = method;
    }

    public RequestMethod getMethod() {
        return method;
    }

    public void setMethod(RequestMethod method) {
        this.method = method;
    }

    public HttpServletRequest getRequest() {
        return request;
    }

    public void setRequest(HttpServletRequest request) {
        this.request = request;
    }

    public HttpServletResponse getResponse() {
        return response;
    }

    public void setResponse(HttpServletResponse response) {
        this.response = response;
    }


}
