package system.libraries;

import java.io.IOException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author tediscript
 */
public class Controller {

    protected HttpServletRequest request;
    protected HttpServletResponse response;
    protected RequestMethod method;
    protected Input input;
    protected Load load;

    public void processRequest(Capsule capsule) {
        request = capsule.getRequest();
        response = capsule.getResponse();
        method = capsule.getMethod();
        input = new Input(request, method);
        load = new Load(response);
        init();
    }

    public void init() {
    }

    public void index() {
    }

    public void redirect(String address) {
        try {
            response.sendRedirect(address);
        } catch (IOException ex) {
        }
    }

    public String baseUrl() {
        String url = "http://localhost:8080/";
        return url;
    }
}
