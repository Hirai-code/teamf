package portal.servlet;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import portal.dao.AccountDao;
import portal.dto.AccountDto;

@WebServlet("/AccountHomeServlet")
public class AccountHomeServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request,
                         HttpServletResponse response)
            throws ServletException, IOException {

        request.setCharacterEncoding("UTF-8");

        AccountDao dao = new AccountDao();
        List<AccountDto> accountList = dao.findAll();
        

        request.setAttribute("accountList", accountList);

        request.getRequestDispatcher("/WEB-INF/jsp/AccountHome.jsp")
               .forward(request, response);
    }
}