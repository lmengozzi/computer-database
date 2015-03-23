package com.excilys.lmengozzi.cdb.webapp;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@WebServlet("/dashboard")
public class DashboardServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;
	
	private static final Pattern PAGE_PATTERN = Pattern.compile("^\\d+$");

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        ComputerBackingBean cbb = new ComputerBackingBean();
        String page = req.getParameter("page");
        if(page != null) {
            Matcher m = PAGE_PATTERN.matcher(page);
            if(m.find()) {
                cbb.init(Integer.parseInt(m.group()));
            }
            else {
                cbb.init(0);
            }
        }
        else {
            cbb.init(0);
        }
        req.setAttribute("computerModel", cbb);
        req.getRequestDispatcher("dashboard.jsp")
                .forward(req, resp);
    }
}
