package portal.servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import portal.dao.ProductDao;
import portal.dto.ProductDto;

@WebServlet("/ProductRegistServlet")
public class ProductRegistServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request,
            HttpServletResponse response)
            throws ServletException, IOException {

    	 request.setCharacterEncoding("UTF-8");

         String productName = request.getParameter("productName");
         String categoryId = request.getParameter("categoryId");
         String price = request.getParameter("price");
         String saleFlag = request.getParameter("saleFlag");

         /*
          * 商品名チェック
          */
         if (productName == null
                 || productName.trim().isEmpty()) {
             request.setAttribute(
                     "errorMsg",
                     "商品名が入力されていません。");
             request.getRequestDispatcher(
                     "/WEB-INF/jsp/ProductAdd.jsp")
                     .forward(request, response);
             return;
         }

         int categoryIdValue;
         /*
          * カテゴリチェック
          */
         if (categoryId == null
                 || categoryId.trim().isEmpty()) {
             request.setAttribute(
                     "errorMsg",
                     "カテゴリーが選択されていません。");
             request.getRequestDispatcher(
                     "/WEB-INF/jsp/ProductAdd.jsp")
                     .forward(request, response);
             return;
         }

         try {
             categoryIdValue = Integer.parseInt(categoryId);
         } catch (NumberFormatException e) {
             request.setAttribute(
                     "errorMsg",
                     "カテゴリーの値が不正です。");
             request.getRequestDispatcher(
                     "/WEB-INF/jsp/ProductAdd.jsp")
                     .forward(request, response);
             return;
         }

         int priceValue;
         
         /*
          * 価格チェック
          */
         if (price == null
                 || price.trim().isEmpty()) {
             request.setAttribute(
                     "errorMsg",
                     "価格が入力されていません。");
             request.getRequestDispatcher(
                     "/WEB-INF/jsp/ProductAdd.jsp")
                     .forward(request, response);
             return;
         }

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




         int saleFlagValue;
         /*
          * 販売状態チェック
          */
         if (saleFlag == null
                 || saleFlag.trim().isEmpty()) {
             request.setAttribute(
                     "errorMsg",
                     "販売状態が選択されていません。");
             request.getRequestDispatcher(
                     "/WEB-INF/jsp/ProductAdd.jsp")
                     .forward(request, response);
             return;
         }

         try {
             saleFlagValue = Integer.parseInt(saleFlag);
         } catch (NumberFormatException e) {
             request.setAttribute(
                     "errorMsg",
                     "販売状態の値が不正です。");
             request.getRequestDispatcher(
                     "/WEB-INF/jsp/ProductAdd.jsp")
                     .forward(request, response);
             return;
         }

        /*
         * DTO作成
         */

        ProductDto dto = new ProductDto();
        
        dto.setItemName(
            request.getParameter("productName"));
        dto.setCategoryId(
            Integer.parseInt(
                request.getParameter("categoryId")));
        dto.setPrice(
            Integer.parseInt(
                request.getParameter("price")));
        dto.setSellingFlg(
            Integer.parseInt(
                request.getParameter("saleFlag")));

        // ログインユーザー取得
        HttpSession session = request.getSession();

        Integer accountId =
            (Integer) session.getAttribute("accountId");

        ProductDao dao = new ProductDao();


        int count = dao.insert(dto, accountId);


        if (count > 0) {

            session.setAttribute(
                "successMsg",
                "商品を登録しました。");

            response.sendRedirect(
                request.getContextPath()
                + "/item");
            return;

           

        

        } else {

            request.setAttribute(
                "errorMsg",
                "登録に失敗しました");


            request.getRequestDispatcher(
                "/WEB-INF/jsp/ProductAdd.jsp")
                .forward(request, response);
        }
    }
}