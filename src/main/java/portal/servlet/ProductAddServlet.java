package portal.servlet;

import java.io.IOException;


import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import portal.dto.AccountDto;

@WebServlet("/ProductAddServlet")
public class ProductAddServlet extends HttpServlet {
	@Override
	protected void doGet(
	        HttpServletRequest request,
	        HttpServletResponse response)
	        throws ServletException, IOException {

	    HttpSession session = request.getSession(false);

	    if (session == null) {
	        response.sendRedirect(
	                request.getContextPath() + "/dashboard");
	        return;
	    }

	    AccountDto loginUser =
	            (AccountDto) session.getAttribute("loginUser");

	    if (loginUser == null
	            || !"MANAGER".equals(loginUser.getRole())) {

	        response.sendRedirect(
	                request.getContextPath() + "/dashboard");
	        return;
	    }

	    request.getRequestDispatcher(
	            "/WEB-INF/jsp/ProductAdd.jsp")
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
	                request.getContextPath() + "/dashboard");
	        return;
	    }

	    AccountDto loginUser =
	            (AccountDto) session.getAttribute("loginUser");

	    if (loginUser == null
	            || !"MANAGER".equals(loginUser.getRole())) {

	        response.sendRedirect(
	                request.getContextPath() + "/dashboard");
	        return;
	    }

	    request.setCharacterEncoding("UTF-8");

        String productName = request.getParameter("productName");
        String categoryId = request.getParameter("categoryId");
        String price = request.getParameter("price");
        
        
        
        
        
        
        
        // 商品名チェック
        if (productName == null || productName.trim().isEmpty()) {

            request.setAttribute(
                    "errorMsg",
                    "商品名が入力されていません。");

            request.getRequestDispatcher(
                    "/WEB-INF/jsp/ProductAdd.jsp")
                    .forward(request, response);
            return;
        }
        
     // 商品名100文字以内チェック
        if (productName.trim().length() > 100) {

            request.setAttribute(
                    "errorMsg",
                    "商品名は100文字以内で入力してください。");

            request.getRequestDispatcher(
                    "/WEB-INF/jsp/ProductAdd.jsp")
                    .forward(request, response);
            return;
        }

        // カテゴリチェック
        if (categoryId == null || categoryId.isEmpty()) {

            request.setAttribute(
                    "errorMsg",
                    "カテゴリーが選択されていません。");

            request.getRequestDispatcher(
                    "/WEB-INF/jsp/ProductAdd.jsp")
                    .forward(request, response);
            return;
        }

        // 価格チェック
        if (price == null || price.trim().isEmpty()) {

            request.setAttribute(
                    "errorMsg",
                    "価格が入力されていません。");

            request.getRequestDispatcher(
                    "/WEB-INF/jsp/ProductAdd.jsp")
                    .forward(request, response);
            return;
        }
        
        int priceValue;

        try {
            priceValue = Integer.parseInt(price);

            if (priceValue < 0) {

                request.setAttribute(
                        "errorMsg",
                        "価格は0以上で入力してください。");

                request.getRequestDispatcher(
                        "/WEB-INF/jsp/ProductAdd.jsp")
                        .forward(request, response);
                return;
            }

        } catch (NumberFormatException e) {

            request.setAttribute(
                    "errorMsg",
                    "価格は整数で入力してください。");

            request.getRequestDispatcher(
                    "/WEB-INF/jsp/ProductAdd.jsp")
                    .forward(request, response);
            return;
        }

        // ↓ここからDB登録処理
        // ProductDao dao = new ProductDao();
        // dao.insert(...);
        String saleFlag = request.getParameter("saleFlag");

     // 確認画面へ値を渡す
     request.setAttribute("productName", productName);
     request.setAttribute("categoryId", Integer.parseInt(categoryId));
     request.setAttribute("price", Integer.parseInt(price));
     request.setAttribute("saleFlag", Integer.parseInt(saleFlag));

     // 確認画面表示
     request.getRequestDispatcher(
             "/WEB-INF/jsp/ProductAddConfirm.jsp")
             .forward(request, response);
        
    }
}