package system.libraries;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.StringTokenizer;
import javax.servlet.http.HttpServletRequest;

/**
 *
 * @author tediscript
 */
public class FormValidation {

    private List<Rule> pools = new ArrayList<Rule>();
    private HttpServletRequest request;
    private boolean customRules = false;
    private IFormValidation iFormValidation;
    private HashMap<String, String> messages = new HashMap<String, String>();
    private boolean status = true;

    public FormValidation() {
    }

    public FormValidation(HttpServletRequest request) {
        this.request = request;
    }

    public void setRules(String fieldName, String label, String rules) {
        pools.add(new Rule(fieldName, label, rules));
    }

    public boolean run() {
        for (Rule rule : pools) {
            List<String> tokens = new ArrayList<String>();
            StringTokenizer tokenizer = new StringTokenizer(rule.getRules(), "|");
            while (tokenizer.hasMoreTokens()) {
                tokens.add(tokenizer.nextToken());
            }
            String field = request.getParameter(rule.getField());
            for (String token : tokens) {
                if (token.equals("required")) {
                    boolean eq = true;
                    try {
                        eq = (field.equals("") || field == null);
                    } catch (Exception e) {
                    }
                    if (eq) {
                        String message = "<p>" + rule.getLabel() + " required</p>";
                        setMessage(rule.getField(), message);
                        status = status && false;
                    }
                } else if (token.startsWith("matches")) {
                    token = token.substring(8, token.length() - 1);
                    String field2 = request.getParameter(token);
                    boolean match = false;
                    try {
                        match = field.equals(field2);
                    } catch (Exception e) {
                    }
                    if (!match) {
                        String field2Label = "";
                        for (Rule rl : pools) {
                            if (rl.getField().equals(token)) {
                                field2Label = rl.getLabel();
                            }
                        }
                        String message = "<p>" + rule.getLabel() + " does not match with " + field2Label + " </p>";
                        setMessage(field, message);
                        status = status && false;
                    }
                }
            }
        }
        if (customRules == true) {
            status = status && iFormValidation.customRules(this);
        }
        return status;
    }

    public String getValidationErrors() {
        String reval = "";
        for (String value : messages.values()) {
            reval = reval + value;
        }
        return reval;
    }

    public void setCustomRules(boolean customRules, IFormValidation iFormValidation) {
        this.customRules = customRules;
        this.iFormValidation = iFormValidation;
    }

    public HttpServletRequest getRequest() {
        return request;
    }

    public void setRequest(HttpServletRequest request) {
        this.request = request;
    }

    public void setMessage(String field, String message) {
            String msg = messages.get(field);
            if (msg == null) {
                msg = "";
            }
            msg += message;
            messages.put(field, msg);
    }

    public String formError(String field) {
        return messages.get(field);
    }
}

class Rule {

    private String field;
    private String label;
    private String rules;

    public Rule() {
    }

    public Rule(String field, String label, String rules) {
        this.field = field;
        this.label = label;
        this.rules = rules;
    }

    public String getField() {
        return field;
    }

    public void setField(String field) {
        this.field = field;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public String getRules() {
        return rules;
    }

    public void setRules(String rules) {
        this.rules = rules;
    }
}
