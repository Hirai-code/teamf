package portal.servlet;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import portal.dao.ProductDao;
import portal.dto.AccountDto;
import portal.dto.ProductDto;

@WebServlet("/item")
public class ProductListServlet extends HttpServlet {

    @Override
    protected void doGet(
            HttpServletRequest request,
            HttpServletResponse response)
            throws ServletException, IOException {

        request.setCharacterEncoding("UTF-8");

        // セッション取得（なければ作らない）
        HttpSession session = request.getSession(false);

        // 未ログインチェック①（セッションなし）
        if (session == null) {
            response.sendRedirect(
                    request.getContextPath() + "/dashboard");
            return;
        }

        // ログインユーザー取得
        AccountDto loginUser =
                (AccountDto) session.getAttribute("loginUser");

        // 未ログインチェック②（ログイン情報なし）
        if (loginUser == null) {
            response.sendRedirect(
                    request.getContextPath() + "/dashboard");
            return;
        }

        // 商品一覧取得
        ProductDao dao = new ProductDao();
        List<ProductDto> itemList = dao.findAll();

        request.setAttribute("itemList", itemList);

        // 成功メッセージ（セッション → リクエストへ移す）
        String successMsg =
                (String) session.getAttribute("successMsg");

        if (successMsg != null) {
            request.setAttribute("successMsg", successMsg);
            session.removeAttribute("successMsg");
        }

        // JSPへフォワード（WEB-INF配下なので直アクセス不可）
        request.getRequestDispatcher(
                "/WEB-INF/jsp/ProductList.jsp")
                .forward(request, response);
    }
}