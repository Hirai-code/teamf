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

    private static final long serialVersionUID = 1L;

    @Override
    protected void doPost(
            HttpServletRequest request,
            HttpServletResponse response)
            throws ServletException, IOException {

        request.setCharacterEncoding("UTF-8");

        HttpSession session = request.getSession();

        // =========================
        // ログインチェック
        // =========================
        AccountDto loginUser =
                (AccountDto) session.getAttribute("loginUser");

        if (loginUser == null) {
            response.sendRedirect(request.getContextPath() + "/login");
            return;
        }

        try {

            // =========================
            // パラメータ取得
            // =========================
            String salesDate = request.getParameter("salesDate");
            String itemId = request.getParameter("itemId");
            String quantity = request.getParameter("quantity");
            String memo = request.getParameter("memo");

            int itemIdValue = Integer.parseInt(itemId);
            int quantityValue = Integer.parseInt(quantity);

            // =========================
            // 商品取得
            // =========================
            ProductDao productDao = new ProductDao();
            ProductDto product = productDao.findById(itemIdValue);

            if (product == null) {
                session.setAttribute("errorMessage", "商品が存在しません。");
                response.sendRedirect("SalesAddServlet");
                return;
            }

            if (product.getSellingFlg() == 0) {
                session.setAttribute("errorMessage", "この商品は販売停止中です。");
                response.sendRedirect("SalesAddServlet");
                return;
            }

            // =========================
            // DTO作成
            // =========================
            SalesDto dto = new SalesDto();

            dto.setSaleDate(Date.valueOf(salesDate));
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
                session.setAttribute("errorMessage", "売上登録に失敗しました。");
                response.sendRedirect("SalesAddServlet");
                return;
            }

            // =========================
            // 成功処理
            // =========================
            session.setAttribute("message", "売上の登録が完了しました。");

            response.sendRedirect("SalesListServlet");

        } catch (NumberFormatException e) {
            session.setAttribute("errorMessage", "入力値が不正です。");
            response.sendRedirect("SalesAddServlet");

        } catch (Exception e) {
            e.printStackTrace();
            session.setAttribute("errorMessage", "予期せぬエラーが発生しました。");
            response.sendRedirect("SalesAddServlet");
        }
    }
}