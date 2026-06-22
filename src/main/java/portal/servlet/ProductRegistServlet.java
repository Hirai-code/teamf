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