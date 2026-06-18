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

@WebServlet("/ProductUpdateServlet")
public class ProductUpdateServlet extends HttpServlet {

    private static final long serialVersionUID = 1L;

    @Override
    protected void doPost(
            HttpServletRequest request,
            HttpServletResponse response)
            throws ServletException, IOException {

        request.setCharacterEncoding("UTF-8");

        String itemIdStr = request.getParameter("itemId");

        if (itemIdStr == null) {
        
        	

        	response.sendRedirect(
        	        request.getContextPath()
        	        + "/item");
            return;
        }

        int itemId = Integer.parseInt(itemIdStr);

        String itemName = request.getParameter("itemName");
        String categoryIdStr = request.getParameter("categoryId");
        String priceStr = request.getParameter("price");
        String sellingFlgStr = request.getParameter("sellingFlg");

        // 商品名チェック
        if (itemName == null || itemName.trim().isEmpty()) {

            ProductDto dto = new ProductDto();
            dto.setItemId(itemId);
            dto.setItemName(itemName);

            request.setAttribute(
                    "errorMsg",
                    "商品名が入力されていません。");

            request.setAttribute("item", dto);

            request.getRequestDispatcher(
                    "/WEB-INF/jsp/ProductEditor.jsp")
                    .forward(request, response);
            return;
        }
        
        //商品名文字数チェック
        
        if (itemName.trim().length() > 100) {

            ProductDto dto = new ProductDto();
            dto.setItemId(itemId);
            dto.setItemName(itemName);

            request.setAttribute(
                    "errorMsg",
                    "商品名は100文字以内で入力してください。");

            request.setAttribute("item", dto);

            request.getRequestDispatcher(
                    "/WEB-INF/jsp/ProductEditor.jsp")
                    .forward(request, response);
            return;
        }

        // 価格チェック
        if (priceStr == null || priceStr.trim().isEmpty()) {

            ProductDto dto = new ProductDto();
            dto.setItemId(itemId);
            dto.setItemName(itemName);

            request.setAttribute(
                    "errorMsg",
                    "価格が入力されていません。");

            request.setAttribute("item", dto);

            request.getRequestDispatcher(
                    "/WEB-INF/jsp/ProductEditor.jsp")
                    .forward(request, response);
            return;
        }
        
        int price;

        try {

            price = Integer.parseInt(priceStr);

            if (price < 0) {

                ProductDto dto = new ProductDto();
                dto.setItemId(itemId);
                dto.setItemName(itemName);

                request.setAttribute(
                        "errorMsg",
                        "価格は0以上で入力してください。");

                request.setAttribute("item", dto);

                request.getRequestDispatcher(
                        "/WEB-INF/jsp/ProductEditor.jsp")
                        .forward(request, response);
                return;
            }

        } catch (NumberFormatException e) {

            ProductDto dto = new ProductDto();
            dto.setItemId(itemId);
            dto.setItemName(itemName);

            request.setAttribute(
                    "errorMsg",
                    "価格は整数で入力してください。");

            request.setAttribute("item", dto);

            request.getRequestDispatcher(
                    "/WEB-INF/jsp/ProductEditor.jsp")
                    .forward(request, response);
            return;
        }

        int categoryId = Integer.parseInt(categoryIdStr);
        int sellingFlg = Integer.parseInt(sellingFlgStr);

        ProductDto dto = new ProductDto();

        dto.setItemId(itemId);
        dto.setItemName(itemName);
        dto.setCategoryId(categoryId);
        dto.setPrice(price);
        dto.setSellingFlg(sellingFlg);

        HttpSession session = request.getSession();

        AccountDto loginUser =
                (AccountDto) session.getAttribute("loginUser");

        int accountId =
                loginUser.getAccountId();

        ProductDao dao = new ProductDao();

        int count =
                dao.update(dto, accountId);

        if (count > 0) {
        	    session.setAttribute(
                    "successMsg",
                    "商品を更新しました。");

            response.sendRedirect(
                    request.getContextPath() + "/item");

        } else {

            request.setAttribute(
                    "errorMsg",
                    "更新に失敗しました");

            request.setAttribute(
                    "item",
                    dto);

            request.getRequestDispatcher(
                    "/WEB-INF/jsp/ProductEditor.jsp")
                    .forward(request, response);
        }
    }
}