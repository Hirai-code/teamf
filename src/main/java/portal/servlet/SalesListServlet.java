package portal.servlet;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import portal.dao.SalesDao;
import portal.dto.SalesDto;

@WebServlet("/SalesListServlet")
public class SalesListServlet extends HttpServlet {

@Override
protected void doGet(
        HttpServletRequest request,
        HttpServletResponse response)
        throws ServletException, IOException {

    request.setCharacterEncoding("UTF-8");

    String startDate = request.getParameter("startDate");
    String endDate = request.getParameter("endDate");
    String staffName = request.getParameter("staffName");
    String minAmount = request.getParameter("minAmount");
    String maxAmount = request.getParameter("maxAmount");

    SalesDao dao = new SalesDao();

    List<SalesDto> list = dao.search(
            startDate,
            endDate,
            staffName,
            minAmount,
            maxAmount
    );

    request.setAttribute("salesList", list);

    // ⭐ ここが追加部分
    HttpSession session = request.getSession();

    String message = (String) session.getAttribute("message");

    request.setAttribute("message", message);

    session.removeAttribute("message");

    request.getRequestDispatcher("/WEB-INF/jsp/SalesList.jsp")
           .forward(request, response);
}
}