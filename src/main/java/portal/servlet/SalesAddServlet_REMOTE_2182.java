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

    @Override
    protected void doGet(
            HttpServletRequest request,
            HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession session =
                request.getSession();

        AccountDto loginUser = (AccountDto) session.getAttribute(
                        "loginUser");
        
        if(loginUser == null){
            response.sendRedirect(
                    request.getContextPath()
                    + "/login");
            return;
        }

        ProductDao dao = new ProductDao();

        request.setAttribute(
                "itemList",
                dao.findAll());

        request.setAttribute(
                "loginUser",
                loginUser);

        request.getRequestDispatcher(
                "/WEB-INF/jsp/SalesAdd.jsp")
                .forward(request, response);
    }


    @Override
    protected void doPost(
            HttpServletRequest request,
            HttpServletResponse response)
            throws ServletException, IOException {

        request.setCharacterEncoding("UTF-8");

        HttpSession session = request.getSession();

        AccountDto loginUser =
                (AccountDto) session.getAttribute(
                        "loginUser");
        
        if(loginUser == null){
            response.sendRedirect(
                    request.getContextPath()
                    + "/login");
            return;
        }

        String salesDate =
                request.getParameter("salesDate");

        String itemId =
                request.getParameter("itemId");

        String quantity =
                request.getParameter("quantity");

        String memo =
                request.getParameter("memo");

        /*
         * 商品選択チェック
         */
        if(itemId == null
                || itemId.trim().isEmpty()){
        	request.setAttribute(
                    "errorMsg",
                    "商品名を選択してください。");

        	request.getRequestDispatcher(
                    "/WEB-INF/jsp/SalesAdd.jsp")
                    .forward(request, response);
            return;
        }

        int itemIdValue;

        try{
            itemIdValue =
                    Integer.parseInt(itemId);

        }catch(NumberFormatException e){

        	request.setAttribute(
                    "errorMsg",
                    "商品の指定が不正です。");

        	request.getRequestDispatcher(
                    "/WEB-INF/jsp/SalesAdd.jsp")
                    .forward(request, response);
            return;
        }

        /*
         * 数量チェック
         */
        if(quantity == null
                || quantity.trim().isEmpty()){
        	
        	request.setAttribute(
                    "errorMsg",
        			"数量を入力してください。");

        	request.getRequestDispatcher(
                    "/WEB-INF/jsp/SalesAdd.jsp")
                    .forward(request, response);
            return;
        }

        int quantityValue;
        
        try{
        	quantityValue =
                    Integer.parseInt(quantity);
            if(quantityValue <= 0){
            	request.setAttribute(
                        "errorMsg",
            			"数量は1以上で入力してください。");

            	request.getRequestDispatcher(
                        "/WEB-INF/jsp/SalesAdd.jsp")
                        .forward(request, response);
                        
                return;
            }
        }catch(NumberFormatException e){
        	request.setAttribute(
                    "errorMsg",
        			"数量は整数で入力してください。");

        	request.getRequestDispatcher(
                    "/WEB-INF/jsp/SalesAdd.jsp")
                    .forward(request, response);
                    
            return;
        }

        /*
         * 商品取得
         */
        ProductDao productDao =
                new ProductDao();
        ProductDto product =
                productDao.findById(itemIdValue);

        if(product == null){

        	request.setAttribute(
                    "errorMsg",
        			"選択された商品が存在しません。");

        	request.getRequestDispatcher(
                    "/WEB-INF/jsp/SalesAdd.jsp")
                    .forward(request, response);
                    
            return;
        }

        /*
         * 確認画面へ渡す
         */
        request.setAttribute(
                "salesDate",
                salesDate);

        request.setAttribute(
                "itemId",
                itemIdValue);

        request.setAttribute(
                "quantity",
                quantityValue);

        request.setAttribute(
                "memo",
                memo);

        request.setAttribute(
                "itemName",
                product.getItemName());

        request.setAttribute(
                "unitPrice",
                product.getPrice());

        request.setAttribute(
                "totalAmount",
                product.getPrice()
                *
                quantityValue);

        request.setAttribute(
                "loginUser",
                loginUser);
        
        request.getRequestDispatcher(
                "/WEB-INF/jsp/SalesAddConfirm.jsp")
                .forward(request, response);
    }
}