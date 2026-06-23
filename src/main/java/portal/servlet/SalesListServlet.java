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
	
	 HttpSession session =
	            request.getSession(false);

	    if (session == null
	            || session.getAttribute("loginUser") == null) {

	        response.sendRedirect(
	                request.getContextPath() + "/login");
	        return;
	    }

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
    
 // 期間チェック
    if (startDate != null && !startDate.isEmpty()
            && endDate != null && !endDate.isEmpty()) {

        if (startDate.compareTo(endDate) > 0) {

            request.setAttribute(
                    "errorMessage",
                    "期間Fromは期間To以前の日付を入力してください。");

            // 入力値保持
            request.setAttribute("startDate", startDate);
            request.setAttribute("endDate", endDate);
            request.setAttribute("minAmount", minAmount);
            request.setAttribute("maxAmount", maxAmount);

            request.getRequestDispatcher(
                    "/WEB-INF/jsp/SalesSearch.jsp")
                    .forward(request, response);

            return;
        }
    }

    request.setAttribute("salesList", list);

    // ⭐ ここが追加部分
  

    String message = (String) session.getAttribute("message");

    request.setAttribute("message", message);

    session.removeAttribute("message");

    request.getRequestDispatcher("/WEB-INF/jsp/SalesList.jsp")
           .forward(request, response);
}
}