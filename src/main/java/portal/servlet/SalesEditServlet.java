package portal.servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import portal.dao.SalesDao;
import portal.dto.SalesDto;

@WebServlet("/SalesEditServlet")
public class SalesEditServlet extends HttpServlet {

private static final long serialVersionUID = 1L;

@Override
protected void doGet(
        HttpServletRequest request,
        HttpServletResponse response)
        throws ServletException, IOException {
	
	HttpSession session = request.getSession(false);

	if (session == null
	        || session.getAttribute("loginUser") == null) {

	    response.sendRedirect(
	            request.getContextPath() + "/login");
	    return;
	}

    String id = request.getParameter("id");

    SalesDao dao = new SalesDao();

    SalesDto sale = dao.findById(Integer.parseInt(id));

    request.setAttribute("sale", sale);

    request.getRequestDispatcher("/WEB-INF/jsp/SalesEdit.jsp")
           .forward(request, response);
}

@Override
protected void doPost(
		
        HttpServletRequest request,
        HttpServletResponse response)
        throws ServletException, IOException {
	
	HttpSession session = request.getSession(false);

	if (session == null
	        || session.getAttribute("loginUser") == null) {

	    response.sendRedirect(
	            request.getContextPath() + "/login");
	    return;
	}

    request.setCharacterEncoding("UTF-8");

    int saleId = Integer.parseInt(request.getParameter("saleId"));
    String note = request.getParameter("note");
    System.out.println("文字数=" + note.length());
    if (note != null && note.length() > 500) {

        request.setAttribute(
                "errorMessage",
                "メモは500文字以内で入力してください。");

        SalesDao dao = new SalesDao();
        SalesDto sale = dao.findById(saleId);

        request.setAttribute("sale", sale);

        request.getRequestDispatcher(
                "/WEB-INF/jsp/SalesEdit.jsp")
                .forward(request, response);

        return;
    }

    SalesDao dao = new SalesDao();

    dao.updateNote(saleId, note);

    session.setAttribute("message", "売上の更新をしました");

    response.sendRedirect("SalesListServlet");
}
}