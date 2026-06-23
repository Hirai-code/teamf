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

        HttpSession session = request.getSession();

        AccountDto loginUser =
                (AccountDto) session.getAttribute("loginUser");

        // =========================
        // ★ログインチェック
        // =========================
        if (loginUser == null) {
            response.sendRedirect(request.getContextPath() + "/login");
            return;
        }

        String idStr = request.getParameter("id");

        AccountDao dao = new AccountDao();

        try {

            // =========================
            // IDチェック
            // =========================
            int accountId = Integer.parseInt(idStr);

            // =========================
            // 削除対象取得
            // =========================
            AccountDto target = dao.findById(accountId);

            if (target == null) {
                session.setAttribute("errorMessage", "対象ユーザーが存在しません。");
                response.sendRedirect("AccountHomeServlet");
                return;
            }

            // =========================
            // ② 自分削除禁止
            // =========================
            if (loginUser.getAccountId() == accountId) {
                session.setAttribute("errorMessage", "自分自身は削除できません。");
                response.sendRedirect("AccountHomeServlet");
                return;
            }

            // =========================
            // ③ 最後の店長削除禁止
            // =========================
            int adminCount = dao.countAdmin();

            if ("ADMIN".equals(target.getRole()) && adminCount <= 1) {
                session.setAttribute("errorMessage", "最後の店長は削除できません。");
                response.sendRedirect("AccountHomeServlet");
                return;
            }

            // =========================
            // 削除実行（論理削除）
            // =========================
            int result = dao.delete(accountId);

            if (result > 0) {
                session.setAttribute("message", "削除しました。");
            } else {
                session.setAttribute("errorMessage", "削除に失敗しました。");
            }

        } catch (NumberFormatException e) {
            session.setAttribute("errorMessage", "不正なIDです。");

        } catch (Exception e) {
            e.printStackTrace();
            session.setAttribute("errorMessage", "システムエラーが発生しました。");
        }

        response.sendRedirect("AccountHomeServlet");
    }
}