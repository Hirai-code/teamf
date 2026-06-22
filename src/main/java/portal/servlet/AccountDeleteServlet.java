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

@WebServlet("/AccountDeleteServlet")
public class AccountDeleteServlet extends HttpServlet {

    @Override
    protected void doPost(
            HttpServletRequest request,
            HttpServletResponse response)
            throws ServletException, IOException {

        request.setCharacterEncoding("UTF-8");

        HttpSession session = request.getSession(false);

        if (session == null) {
            response.sendRedirect(
                    request.getContextPath() + "/login");
            return;
        }

        AccountDto loginUser =
                (AccountDto) session.getAttribute("loginUser");

        if (loginUser == null) {
            response.sendRedirect(
                    request.getContextPath() + "/login");
            return;
        }

        String idStr = request.getParameter("id");

        if (idStr == null || idStr.trim().isEmpty()) {
            session.setAttribute("errorMessage",
                    "不正なリクエストです。");

            response.sendRedirect(
                    request.getContextPath() + "/AccountHomeServlet");
            return;
        }

        int accountId;

        try {
            accountId = Integer.parseInt(idStr);
        } catch (NumberFormatException e) {
            session.setAttribute("errorMessage",
                    "不正なIDです。");

            response.sendRedirect(
                    request.getContextPath() + "/AccountHomeServlet");
            return;
        }

        AccountDao dao = new AccountDao();

        int result = dao.delete(accountId);

        if (result > 0) {

            session.setAttribute(
                    "successMessage",
                    "アカウントを削除しました。");

        } else {

            session.setAttribute(
                    "errorMessage",
                    "削除に失敗しました。");
        }

        response.sendRedirect(
                request.getContextPath() + "/AccountHomeServlet");
    }
}