package portal.servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import portal.dao.AccountDao;
import portal.dto.AccountDto;

@WebServlet("/AccountInsertServlet")
public class AccountInsertServlet extends HttpServlet {

    @Override
    protected void doPost(
            HttpServletRequest request,
            HttpServletResponse response)
            throws ServletException, IOException {

        request.setCharacterEncoding("UTF-8");

        String loginId = request.getParameter("loginId");
        String staffName = request.getParameter("staffName");
        String password = request.getParameter("password");
        String role = request.getParameter("role");

        AccountDao dao = new AccountDao();
        
        AccountDto dto = new AccountDto();
        dto.setLoginId(loginId);
        dto.setStaffName(staffName);
        dto.setPassword(password);
        dto.setRole(role);


        if (loginId == null
                || loginId.length() < 4
                || loginId.length() > 50) {

            request.setAttribute(
                "errorMessage",
                "ログインIDは4～50文字で入力してください。");

            request.getRequestDispatcher(
                "/WEB-INF/jsp/AccountAdd.jsp")
                .forward(request, response);

            return;
        }
        
        if (staffName == null
                || staffName.length() < 1
                || staffName.length() > 50) {

            request.setAttribute(
                "errorMessage",
                "スタッフ名は1～50文字で入力してください。");

            request.getRequestDispatcher(
                "/WEB-INF/jsp/AccountAdd.jsp")
                .forward(request, response);

            return;
        }
        
        if (password == null
                || password.length() < 8) {

            request.setAttribute(
                "errorMessage",
                "パスワードは8文字以上で入力してください。");

            request.getRequestDispatcher(
                "/WEB-INF/jsp/AccountAdd.jsp")
                .forward(request, response);

            return;
        }
        
        
        if (dao.existsLoginId(loginId)) {

            request.setAttribute(
                "errorMessage",
                "ログインIDが既に存在します。");

            request.getRequestDispatcher(
                "/WEB-INF/jsp/AccountAdd.jsp")
                .forward(request, response);

            return;
        }
        
        if (dao.existsStaffName(staffName)) {

            request.setAttribute(
                "errorMessage",
                "スタッフ名が既に存在します。");

            request.getRequestDispatcher(
                "/WEB-INF/jsp/AccountAdd.jsp")
                .forward(request, response);

            return;
        }

        int result = dao.insert(dto);
        
        if (result > 0) {
            response.sendRedirect(
                request.getContextPath()
                + "/AccountHomeServlet");
        } else {

            request.setAttribute(
                "errorMessage",
                "アカウント登録に失敗しました。");

            request.getRequestDispatcher(
                "/WEB-INF/jsp/AccountAdd.jsp")
                .forward(request, response);
        }
    }
}