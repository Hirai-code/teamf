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
    // 画面表示（初期表示）
    // =========================
    @Override
    protected void doGet(
            HttpServletRequest request,
            HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession session = request.getSession();

        AccountDto loginUser =
                (AccountDto) session.getAttribute("loginUser");

        if (loginUser == null) {
            response.sendRedirect(request.getContextPath() + "/login");
            return;
        }

        // =========================
        // InsertServletからのエラー受け取り
        // =========================
        String errorMessage =
                (String) session.getAttribute("errorMessage");

        if (errorMessage != null) {
            request.setAttribute("errorMessage", errorMessage);
            session.removeAttribute("errorMessage");
        }

        // =========================
        // 商品一覧取得
        // =========================
        ProductDao dao = new ProductDao();

        request.setAttribute("itemList", dao.findAll());
        request.setAttribute("loginUser", loginUser);

        request.getRequestDispatcher(
                "/WEB-INF/jsp/SalesAdd.jsp")
                .forward(request, response);
    }

    // =========================
    // 入力 → 確認画面へ
    // =========================
    @Override
    protected void doPost(
            HttpServletRequest request,
            HttpServletResponse response)
            throws ServletException, IOException {

        request.setCharacterEncoding("UTF-8");

        HttpSession session = request.getSession();

        AccountDto loginUser =
                (AccountDto) session.getAttribute("loginUser");

        if (loginUser == null) {
            response.sendRedirect(request.getContextPath() + "/login");
            return;
        }

        // =========================
        // パラメータ取得
        // =========================
        String salesDate = request.getParameter("salesDate");
        String itemId = request.getParameter("itemId");
        String quantity = request.getParameter("quantity");
        String memo = request.getParameter("memo");

        // =========================
        // バリデーション
        // =========================

        if (salesDate == null || salesDate.isEmpty()) {
            request.setAttribute("errorMessage", "売上日を入力してください。");
            doGet(request, response);
            return;
        }

        int itemIdValue;
        try {
            itemIdValue = Integer.parseInt(itemId);
        } catch (Exception e) {
            request.setAttribute("errorMessage", "商品を選択してください。");
            doGet(request, response);
            return;
        }

        int quantityValue;
        try {
            quantityValue = Integer.parseInt(quantity);

            if (quantityValue <= 0) {
                request.setAttribute("errorMessage", "数量は1以上で入力してください。");
                doGet(request, response);
                return;
            }

        } catch (Exception e) {
            request.setAttribute("errorMessage", "数量は整数で入力してください。");
            doGet(request, response);
            return;
        }

        // =========================
        // 商品取得
        // =========================
        ProductDao productDao = new ProductDao();
        ProductDto product = productDao.findById(itemIdValue);

        if (product == null) {
            request.setAttribute("errorMessage", "商品が存在しません。");
            doGet(request, response);
            return;
        }

        if (product.getSellingFlg() == 0) {
            request.setAttribute("errorMessage", "この商品は販売停止中です。");
            doGet(request, response);
            return;
        }

        // =========================
        // 確認画面へ渡す
        // =========================
        request.setAttribute("salesDate", salesDate);
        request.setAttribute("itemId", itemIdValue);
        request.setAttribute("quantity", quantityValue);
        request.setAttribute("memo", memo);

        request.setAttribute("itemName", product.getItemName());
        request.setAttribute("unitPrice", product.getPrice());
        request.setAttribute(
                "totalAmount",
                product.getPrice() * quantityValue
        );

        request.setAttribute("loginUser", loginUser);

        request.getRequestDispatcher(
                "/WEB-INF/jsp/SalesAddConfirm.jsp")
                .forward(request, response);
    }
}