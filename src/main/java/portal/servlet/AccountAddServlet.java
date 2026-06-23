package portal.servlet;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import portal.dao.AccountDao;
import portal.dto.AccountDto;

@WebServlet("/AccountAddServlet")
public class AccountAddServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
    	
    	HttpSession session = request.getSession(false);

    	if (session == null
    	        || session.getAttribute("loginUser") == null) {

    	    response.sendRedirect(
    	            request.getContextPath() + "/login");
    	    return;
    	}

        request.setCharacterEncoding("UTF-8");

        // =========================
        // DAOから一覧取得
        // =========================
        AccountDao dao = new AccountDao();
        List<AccountDto> accountList = dao.findAll();

        // =========================
        // JSPへ渡す
        // =========================
        request.setAttribute("accountList", accountList);

        // =========================
        // JSPへフォワード
        // =========================
        request.getRequestDispatcher("/WEB-INF/jsp/AccountAdd.jsp")
               .forward(request, response);
    }
}