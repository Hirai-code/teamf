package portal.servlet;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import portal.dao.AccountDao;
import portal.dto.AccountDto;

@WebServlet("/AccountHomeServlet")
public class AccountHomeServlet extends HttpServlet {

    private static final long serialVersionUID = 1L;


    @Override
    protected void doGet(HttpServletRequest request,
                         HttpServletResponse response)
            throws ServletException, IOException {


        request.setCharacterEncoding("UTF-8");


        // セッション取得
        HttpSession session = request.getSession();


        // ログインユーザー取得
        AccountDto loginUser =
                (AccountDto) session.getAttribute("loginUser");
        System.out.println("LOGIN USER: " + loginUser.getLoginId());
        System.out.println("ROLE: " + loginUser.getRole());



        // 未ログインの場合
        if (loginUser != null) {
            System.out.println("LOGIN USER: " + loginUser.getLoginId());
            System.out.println("ROLE: " + loginUser.getRole());
        }


        // MANAGER権限チェック
        if (!"MANAGER".equals(loginUser.getRole())) {


            request.setAttribute(
                "errorMessage",
                "アカウント管理機能は管理者権限（MANAGER）のみ利用できます。"
            );


            request.getRequestDispatcher(
                "/WEB-INF/jsp/error.jsp"
            ).forward(request, response);


            return;
        }



        // MANAGERの場合のみ一覧取得
        AccountDao dao = new AccountDao();

        List<AccountDto> accountList =
                dao.findAll();


        request.setAttribute(
            "accountList",
            accountList
        );


        request.getRequestDispatcher(
            "/WEB-INF/jsp/AccountHome.jsp"
        ).forward(request, response);

    }
}