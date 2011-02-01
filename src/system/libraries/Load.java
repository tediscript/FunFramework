package system.libraries;

import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import java.io.File;
import java.io.IOException;
import java.io.StringWriter;
import java.io.Writer;
import java.util.HashMap;
import java.util.Map;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author tediscript
 */
public class Load {

    private HttpServletResponse response;

    public Load(HttpServletResponse response) {
        this.response = response;
    }

    public String view(String template) {
        return view(template, new HashMap(), false);
    }

    public String view(String template, boolean returns) {
        return view(template, new HashMap(), returns);
    }

    public String view(String template, Map data) {
        return view(template, data, false);
    }

    public String view(String template, Map data, boolean returns) {
        String reval = null;
        Configuration cfg;
        try {
            cfg = new Configuration();
            cfg.setDirectoryForTemplateLoading(new File("/home/tediscript/NetBeansProjects/FunFramework/src/application/views"));
            Template t = cfg.getTemplate(template);
            response.setContentType("text/html; charset=" + t.getEncoding());
            Writer out;
            if (returns) {
                out = new StringWriter();
            } else {
                out = response.getWriter();
            }
            t.process(data, out);
            if (returns) {
                reval = out.toString();
            }
        } catch (TemplateException ex) {
        } catch (IOException ex) {
        }
        return reval;
    }

    public HttpServletResponse getResponse() {
        return response;
    }

    public void setResponse(HttpServletResponse response) {
        this.response = response;
    }

}
