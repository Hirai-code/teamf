package portal.servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import portal.dao.SalesDao;

@WebServlet("/SalesDeleteServlet")
public class SalesDeleteServlet extends HttpServlet {

private static final long serialVersionUID = 1L;

@Override
protected void doPost(
        HttpServletRequest request,
        HttpServletResponse response)
        throws ServletException, IOException {

    request.setCharacterEncoding("UTF-8");

    String id = request.getParameter("id");

    if (id != null && !id.isEmpty()) {

        SalesDao dao = new SalesDao();

        dao.delete(Integer.parseInt(id));
    }

    HttpSession session = request.getSession();
    session.setAttribute("message", "売上の削除をしました");

    response.sendRedirect("SalesListServlet");
}
}