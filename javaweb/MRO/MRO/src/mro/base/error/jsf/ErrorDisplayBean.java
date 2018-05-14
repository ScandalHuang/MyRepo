package mro.base.error.jsf;

import java.io.PrintWriter;
import java.io.Serializable;
import java.io.StringWriter;
import java.util.Map;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.servlet.ServletException;

@ManagedBean(name="ErrorDisplayBean")
@ViewScoped
public class ErrorDisplayBean implements Serializable{
	private static final long serialVersionUID = -2525863632710245957L;

	public String getInfoMessage() {
        return "An unexpected processing error has occurred.<br /> Please cut and paste the following information" + 
        		" into an email and send it to <b>" +
        		"MIS email address" + "</b>. <br />";
    }

    public String getStackTrace() {
        FacesContext context = FacesContext.getCurrentInstance();
        Map requestMap = context.getExternalContext().getRequestMap();
        Throwable ex = (Throwable) requestMap.get("javax.servlet.error.exception");
        StringWriter writer = new StringWriter();
        PrintWriter pw = new PrintWriter(writer);
        fillStackTrace(ex, pw);

        return writer.toString();
    }

    private void fillStackTrace(Throwable ex, PrintWriter pw) {
        if (null == ex) {
            return;
        }

        ex.printStackTrace(pw);

        if (ex instanceof ServletException) {
            Throwable cause = ((ServletException) ex).getRootCause();

            if (null != cause) {
                pw.println("Root Cause:");
                fillStackTrace(cause, pw);
            }
        } else {
            Throwable cause = ex.getCause();

            if (null != cause) {
                pw.println("Cause:");
                fillStackTrace(cause, pw);
            }
        }
    }
}
