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

    @Override
    protected void doGet(
            HttpServletRequest request,
            HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession session = request.getSession(false);

        if (session == null) {
            response.sendRedirect(
                    request.getContextPath() + "/login");
            return;
        }

        AccountDto loginUser =
                (AccountDto) session.getAttribute("loginUser");

        if (loginUser == null) {
            response.sendRedirect(
                    request.getContextPath() + "/login");
            return;
        }

        ProductDao dao = new ProductDao();

        request.setAttribute("itemList", dao.findAll());
        request.setAttribute("loginUser", loginUser);

        request.getRequestDispatcher(
                "/WEB-INF/jsp/SalesAdd.jsp")
                .forward(request, response);
    }

    @Override
    protected void doPost(
            HttpServletRequest request,
            HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession session = request.getSession(false);

        if (session == null) {
            response.sendRedirect(
                    request.getContextPath() + "/login");
            return;
        }

        AccountDto loginUser =
                (AccountDto) session.getAttribute("loginUser");

        if (loginUser == null) {
            response.sendRedirect(
                    request.getContextPath() + "/login");
            return;
        }

        request.setCharacterEncoding("UTF-8");

        String salesDate = request.getParameter("salesDate");
        String itemId = request.getParameter("itemId");
        String quantity = request.getParameter("quantity");
        String memo = request.getParameter("memo");

        // =========================
        // 商品IDチェック
        // =========================
        if (itemId == null || itemId.trim().isEmpty()) {
            forwardError(request, response,
                    "商品を選択してください。");
            return;
        }

        int itemIdValue;
        try {
            itemIdValue = Integer.parseInt(itemId);
        } catch (NumberFormatException e) {
            forwardError(request, response,
                    "商品IDが不正です。");
            return;
        }

        // =========================
        // 数量チェック
        // =========================
        if (quantity == null || quantity.trim().isEmpty()) {
            forwardError(request, response,
                    "数量を入力してください。");
            return;
        }

        int quantityValue;
        try {
            quantityValue = Integer.parseInt(quantity);
            if (quantityValue <= 0) {
                forwardError(request, response,
                        "数量は1以上で入力してください。");
                return;
            }
        } catch (NumberFormatException e) {
            forwardError(request, response,
                    "数量は整数で入力してください。");
            return;
        }

        // =========================
        // 商品取得
        // =========================
        ProductDao productDao = new ProductDao();
        ProductDto product = productDao.findById(itemIdValue);

        if (product == null) {
            forwardError(request, response,
                    "商品が存在しません。");
            return;
        }

        // =========================
        // null安全（念のため）
        // =========================
        int unitPrice = product.getPrice();
        int totalAmount = unitPrice * quantityValue;

        // =========================
        // 画面へ渡す
        // =========================
        request.setAttribute("salesDate", salesDate);
        request.setAttribute("itemId", itemIdValue);
        request.setAttribute("quantity", quantityValue);
        request.setAttribute("memo", memo);

        request.setAttribute("itemName", product.getItemName());
        request.setAttribute("unitPrice", unitPrice);
        request.setAttribute("totalAmount", totalAmount);

        request.setAttribute("loginUser", loginUser);

        request.getRequestDispatcher(
                "/WEB-INF/jsp/SalesAddConfirm.jsp")
                .forward(request, response);
    }

    /**
     * エラー共通処理
     */
    private void forwardError(
            HttpServletRequest request,
            HttpServletResponse response,
            String message)
            throws ServletException, IOException {

        request.setAttribute("errorMessage", message);

        request.setAttribute("salesDate",
                request.getParameter("salesDate"));
        request.setAttribute("itemId",
                request.getParameter("itemId"));
        request.setAttribute("quantity",
                request.getParameter("quantity"));
        request.setAttribute("memo",
                request.getParameter("memo"));

        request.getRequestDispatcher(
                "/WEB-INF/jsp/SalesAdd.jsp")
                .forward(request, response);
    }
}