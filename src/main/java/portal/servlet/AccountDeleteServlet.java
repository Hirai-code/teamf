package portal.servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import portal.dao.AccountDao;

@WebServlet("/AccountDeleteServlet")
public class AccountDeleteServlet extends HttpServlet {

    @Override
    protected void doPost(
            HttpServletRequest request,
            HttpServletResponse response)
            throws ServletException, IOException {

        request.setCharacterEncoding("UTF-8");

        String idStr = request.getParameter("id");

        try {

            int accountId = Integer.parseInt(idStr);

            AccountDao dao = new AccountDao();

            dao.delete(accountId);

            HttpSession session = request.getSession();
            session.setAttribute(
                "successMessage",
                "アカウントを削除しました。"
            );

        } catch (Exception e) {
            e.printStackTrace();
        }
        response.sendRedirect(
        	    request.getContextPath()
        	    + "/AccountHomeServlet"
        	);
}}