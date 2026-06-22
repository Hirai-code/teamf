package portal.servlet;

import java.io.IOException;
import java.sql.Date;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import portal.dao.ProductDao;
import portal.dao.SalesDao;
import portal.dto.AccountDto;
import portal.dto.ProductDto;
import portal.dto.SalesDto;

@WebServlet("/SalesInsertServlet")
public class SalesInsertServlet extends HttpServlet {

    @Override
    protected void doPost(
            HttpServletRequest request,
            HttpServletResponse response)
            throws ServletException, IOException {

        request.setCharacterEncoding("UTF-8");

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

        String salesDate = request.getParameter("salesDate");
        String itemId = request.getParameter("itemId");
        String quantity = request.getParameter("quantity");
        String memo = request.getParameter("memo");

        // =========================
        // 売上日チェック
        // =========================
        if (salesDate == null || salesDate.trim().isEmpty()) {
            forwardError(request, response,
                    "売上日を入力してください。");
            return;
        }

        Date saleDateValue;
        try {
            saleDateValue = Date.valueOf(salesDate);
        } catch (IllegalArgumentException e) {
            forwardError(request, response,
                    "売上日は正しい日付で入力してください。");
            return;
        }

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
        // DTO作成
        // =========================
        SalesDto dto = new SalesDto();
        dto.setSaleDate(saleDateValue);
        dto.setItemId(itemIdValue);
        dto.setSaleNumber(quantityValue);
        dto.setTradeName(product.getItemName());
        dto.setUnitPrice(product.getPrice());
        dto.setCategoryId(product.getCategoryId());
        dto.setAccountId(loginUser.getAccountId());
        dto.setUpdateBy(loginUser.getAccountId());
        dto.setNote(memo);

        // =========================
        // 登録処理
        // =========================
        SalesDao dao = new SalesDao();
        int result = dao.insert(dto);

        if (result == 0) {
            forwardError(request, response,
                    "売上登録に失敗しました。");
            return;
        }

        session.setAttribute("message", "売上の登録をしました。");

        response.sendRedirect(
                request.getContextPath() + "/SalesListServlet");
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