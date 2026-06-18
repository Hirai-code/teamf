package portal.servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import portal.dao.AccountDao;
import portal.dto.AccountDto;

@WebServlet("/login")
public class LoginServlet extends HttpServlet {

    @Override
    protected void doGet(
            HttpServletRequest request,
            HttpServletResponse response)
            throws ServletException, IOException {

        request.getRequestDispatcher(
                "/WEB-INF/jsp/login.jsp")
                .forward(request, response);
    }

    @Override
    protected void doPost(
            HttpServletRequest request,
            HttpServletResponse response)
            throws ServletException, IOException {
    	
        request.setCharacterEncoding("UTF-8");

        String loginId =
                request.getParameter("loginId");

        String password =
                request.getParameter("password");

        if (loginId == null || loginId.isBlank()
                || password == null || password.isBlank()) {

            request.setAttribute(
                    "errorMessage",
                    "ログインIDとパスワードを入力してください。");
            
            request.getRequestDispatcher(
                    "/WEB-INF/jsp/login.jsp")
                    .forward(request, response);
            return;
        }

        AccountDao dao = new AccountDao();

        AccountDto account =
                dao.login(loginId, password);

        if (account == null) {

            request.setAttribute(
                    "errorMessage",
                    "ログインIDまたはパスワードが正しくありません。");

            request.getRequestDispatcher(
                    "/WEB-INF/jsp/login.jsp")
                    .forward(request, response);

            return;
        }

        HttpSession session =
                request.getSession();

        session.setAttribute(
                "loginUser",
                account);

        // 商品登録用の更新者ID保存
        session.setAttribute(
                "accountId",
                account.getAccountId());
        
        response.sendRedirect(
                request.getContextPath()
                + "/dashboard");
    }
}