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

@WebServlet("/AccountEditorServlet")
public class AccountEditorServlet extends HttpServlet {

    @Override
    protected void doGet(
            HttpServletRequest request,
            HttpServletResponse response)
            throws ServletException, IOException {

        request.setCharacterEncoding("UTF-8");

        // =========================
        // 🔐 セッション＆ログインチェック
        // =========================
        HttpSession session = request.getSession(false);

        if (session == null ||
            session.getAttribute("loginUser") == null) {

            response.sendRedirect(
                    request.getContextPath() + "/login");
            return;
        }

        AccountDto loginUser =
                (AccountDto) session.getAttribute("loginUser");

        // =========================
        // 🔐 権限チェック
        // =========================
        if (!"MANAGER".equals(loginUser.getRole())) {
            response.sendRedirect(
                    request.getContextPath() + "/AccountHomeServlet");
            return;
        }

        // =========================
        // ここから元の処理
        // =========================

        String idStr = request.getParameter("id");

        if (idStr == null) {
            response.sendRedirect(
                    request.getContextPath()
                    + "/AccountHomeServlet");
            return;
        }

        int accountId = Integer.parseInt(idStr);

        AccountDao dao = new AccountDao();

        AccountDto account =
                dao.findById(accountId);

        if (account == null) {
            response.sendRedirect(
                    request.getContextPath()
                    + "/AccountHomeServlet");
            return;
        }

        request.setAttribute(
                "account",
                account);

        request.getRequestDispatcher(
                "/WEB-INF/jsp/AccountEditor.jsp")
                .forward(request, response);
    }
}