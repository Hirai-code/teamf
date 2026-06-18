package portal.servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import portal.dao.AccountDao;
import portal.dto.AccountDto;

@WebServlet("/AccountEditorServlet")
public class AccountEditorServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String idStr = request.getParameter("accountId");

        if (idStr != null) {
            int accountId = Integer.parseInt(idStr);

            AccountDao dao = new AccountDao();
            AccountDto account = dao.findById(accountId);

            request.setAttribute("account", account);
        }

        request.getRequestDispatcher("/WEB-INF/jsp/AccountEditor.jsp")
               .forward(request, response);
    }
}