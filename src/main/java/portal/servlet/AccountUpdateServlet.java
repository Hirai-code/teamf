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

@WebServlet("/AccountUpdateServlet")
public class AccountUpdateServlet extends HttpServlet {

    @Override
    protected void doPost(
            HttpServletRequest request,
            HttpServletResponse response)
            throws ServletException, IOException {

        request.setCharacterEncoding("UTF-8");

        HttpSession session = request.getSession();

        int accountId =
                Integer.parseInt(request.getParameter("accountId"));

        String loginId = request.getParameter("loginId");
        String staffName = request.getParameter("staffName");
        String password = request.getParameter("password");
        String role = request.getParameter("role");

        AccountDao dao = new AccountDao();

        AccountDto current = dao.findById(accountId);

        if (current == null) {
            session.setAttribute("errorMessage", "対象アカウントが存在しません。");
            response.sendRedirect("AccountHomeServlet");
            return;
        }

        // =========================
        // 重複チェック
        // =========================
        if (dao.existsLoginIdForUpdate(loginId, accountId)) {
            session.setAttribute("errorMessage", "ログインIDが既に存在します。");
            response.sendRedirect("AccountHomeServlet");
            return;
        }

        if (dao.existsStaffNameForUpdate(staffName, accountId)) {
            session.setAttribute("errorMessage", "スタッフ名が既に存在します。");
            response.sendRedirect("AccountHomeServlet");
            return;
        }

        // =========================
        // 店長制御（重要）
        // =========================
        int adminCount = dao.countAdmin();

        if ("ADMIN".equals(current.getRole())
                && !"ADMIN".equals(role)
                && adminCount <= 1) {

            session.setAttribute(
                    "errorMessage",
                    "店長が1人しかいないため一般スタッフに変更できません。"
            );

            response.sendRedirect("AccountHomeServlet");
            return;
        }

        // =========================
        // DTO作成
        // =========================
        AccountDto dto = new AccountDto();
        dto.setAccountId(accountId);
        dto.setLoginId(loginId);
        dto.setStaffName(staffName);
        dto.setRole(role);

        // ★パスワード空なら更新しない
        if (password != null && !password.trim().isEmpty()) {
            dto.setPassword(password);
        } else {
            dto.setPassword(current.getPassword());
        }

        // =========================
        // 更新
        // =========================
        int result = dao.update(dto);

        if (result > 0) {
            session.setAttribute("message", "更新しました。");
            response.sendRedirect("AccountHomeServlet");
        } else {
            session.setAttribute("errorMessage", "更新に失敗しました。");
            response.sendRedirect("AccountHomeServlet");
        }
    }
}