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
        String saleFlag = request.getParameter("saleFlag");
        
        
        
        
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
        int categoryIdValue;

        try{
            categoryIdValue =
                    Integer.parseInt(categoryId);

        }catch(NumberFormatException e){
            forwardError(
                    request,
                    response,
                    "カテゴリーの値が不正です。");
            return;

        }


        /*
         * 価格チェック
         */
        if(price == null
                || price.trim().isEmpty()){
            forwardError(
                    request,
                    response,
                    "価格が入力されていません。");
            return;
        }

        int priceValue;
        try{
            priceValue =
                    Integer.parseInt(price);
            if(priceValue < 0){
                forwardError(
                        request,
                        response,
                        "価格は0以上で入力してください。");
                return;
            }
        }catch(NumberFormatException e){
            forwardError(
                    request,
                    response,
                    "価格は整数で入力してください。");
            return;
        }


        /*
         * 販売状態チェック
         */
        if(saleFlag == null
                || saleFlag.trim().isEmpty()){
            forwardError(
                    request,
                    response,
                    "販売状態が選択されていません。");
            return;
        }

        int saleFlagValue;

        try{
            saleFlagValue =
                    Integer.parseInt(saleFlag);

        }catch(NumberFormatException e){

            forwardError(
                    request,
                    response,
                    "販売状態の値が不正です。");
            return;
        }


        /*
         * 確認画面へ渡す
         */

        request.setAttribute(
                "productName",
                productName);

        request.setAttribute(
                "categoryId",
                categoryIdValue);

        request.setAttribute(
                "price",
                priceValue);

        request.setAttribute(
                "saleFlag",
                saleFlagValue);

        request.getRequestDispatcher(
                "/WEB-INF/jsp/ProductAddConfirm.jsp")
                .forward(request, response);

    }


    /**
     * エラー時共通処理
     */
    private void forwardError(
            HttpServletRequest request,
            HttpServletResponse response,
            String message)
            throws ServletException, IOException {

        request.setAttribute(
                "errorMsg",
                message);

        // 入力値保持
        request.setAttribute(
                "productName",
                request.getParameter("productName"));

        request.setAttribute(
                "categoryId",
                request.getParameter("categoryId"));

        request.setAttribute(
                "price",
                request.getParameter("price"));

        request.setAttribute(
                "saleFlag",
                request.getParameter("saleFlag"));

        request.getRequestDispatcher(
                "/WEB-INF/jsp/ProductAdd.jsp")
                .forward(request, response);
    }

}