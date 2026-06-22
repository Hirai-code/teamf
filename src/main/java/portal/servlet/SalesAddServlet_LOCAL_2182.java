package portal.servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import portal.dao.ProductDao;
import portal.dto.AccountDto;
import portal.dto.ProductDto;

@WebServlet("/SalesAddServlet")
public class SalesAddServlet extends HttpServlet {

    private static final long serialVersionUID = 1L;

    // =========================
    // 売上追加画面表示（GET）
    // =========================
    @Override
    protected void doGet(
            HttpServletRequest request,
            HttpServletResponse response)
            throws ServletException, IOException {

        // ①ログインチェック（最初）
        HttpSession session = request.getSession(false);

        if (session == null || session.getAttribute("loginUser") == null) {
            response.sendRedirect(request.getContextPath() + "/login");
            return;
        }

        // ログインユーザー取得
        AccountDto loginUser =
                (AccountDto) session.getAttribute("loginUser");

        // 商品一覧取得
        ProductDao dao = new ProductDao();

        request.setAttribute("itemList", dao.findAll());
        request.setAttribute("loginUser", loginUser);

        // 画面表示
        request.getRequestDispatcher("/WEB-INF/jsp/SalesAdd.jsp")
               .forward(request, response);
    }

    // =========================
    // 売上確認画面（POST）
    // =========================
    @Override
    protected void doPost(
            HttpServletRequest request,
            HttpServletResponse response)
            throws ServletException, IOException {

        // ①ログインチェック（最初）
        HttpSession session = request.getSession(false);

        if (session == null || session.getAttribute("loginUser") == null) {
            response.sendRedirect(request.getContextPath() + "/login");
            return;
        }

        request.setCharacterEncoding("UTF-8");

        // 入力値取得
        String salesDate = request.getParameter("salesDate");
        String itemId = request.getParameter("itemId");
        String quantity = request.getParameter("quantity");
        String memo = request.getParameter("memo");

        // 商品取得
        ProductDao productDao = new ProductDao();
        ProductDto product =
                productDao.findById(Integer.parseInt(itemId));

        // ログインユーザー
        AccountDto loginUser =
                (AccountDto) session.getAttribute("loginUser");

        // 画面へ渡す値
        request.setAttribute("salesDate", salesDate);
        request.setAttribute("itemId", itemId);
        request.setAttribute("quantity", quantity);
        request.setAttribute("memo", memo);

        request.setAttribute("itemName", product.getItemName());
        request.setAttribute("unitPrice", product.getPrice());

        request.setAttribute(
                "totalAmount",
                product.getPrice() * Integer.parseInt(quantity)
        );

        request.setAttribute("loginUser", loginUser);

        // 確認画面へ
        request.getRequestDispatcher("/WEB-INF/jsp/SalesAddConfirm.jsp")
               .forward(request, response);
    }
}