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

    request.setCharacterEncoding("UTF-8");

    int saleId = Integer.parseInt(request.getParameter("saleId"));
    String note = request.getParameter("note");

    SalesDao dao = new SalesDao();

    dao.updateNote(saleId, note);

    HttpSession session = request.getSession();
    session.setAttribute("message", "売上の更新をしました");

    response.sendRedirect("SalesListServlet");
}
}