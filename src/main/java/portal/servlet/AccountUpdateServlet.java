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


        int accountId =
            Integer.parseInt(
                request.getParameter("accountId"));


        String loginId =
            request.getParameter("loginId");


        String staffName =
            request.getParameter("staffName");


        String password =
            request.getParameter("password");


        String role =
            request.getParameter("role");



        AccountDao dao = new AccountDao();



        // ログインID重複チェック
        if (dao.existsLoginIdForUpdate(loginId, accountId)) {

            request.setAttribute(
                "errorMessage",
                "ログインIDが既に存在します。");

            request.setAttribute(
                "account",
                dao.findById(accountId));


            request.getRequestDispatcher(
                "/WEB-INF/jsp/AccountEditor.jsp")
                .forward(request, response);

            return;
        }



        // スタッフ名重複チェック
        if (dao.existsStaffNameForUpdate(staffName, accountId)) {

            request.setAttribute(
                "errorMessage",
                "スタッフ名が既に存在します。");

            request.setAttribute(
                "account",
                dao.findById(accountId));


            request.getRequestDispatcher(
                "/WEB-INF/jsp/AccountEditor.jsp")
                .forward(request, response);

            return;
        }



        AccountDto dto =
            new AccountDto();


        dto.setAccountId(accountId);
        dto.setLoginId(loginId);
        dto.setStaffName(staffName);
        dto.setPassword(password);
        dto.setRole(role);



        int result =
            dao.update(dto);



        if(result > 0){

            HttpSession session = request.getSession();

            session.setAttribute(
                "successMessage",
                "アカウントを更新しました。"
            );

            response.sendRedirect(
                request.getContextPath()
                + "/AccountHomeServlet"
            );

        }else{


            request.setAttribute(
                "errorMessage",
                "更新に失敗しました。");


            request.setAttribute(
                "account",
                dto);


            request.getRequestDispatcher(
                "/WEB-INF/jsp/AccountEditor.jsp")
                .forward(request,response);
        }
    }
}