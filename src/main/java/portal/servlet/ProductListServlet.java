package portal.servlet;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import portal.dao.ProductDao;
import portal.dto.ProductDto;

@WebServlet("/item")
public class ProductListServlet extends HttpServlet {

    @Override
    protected void doGet(
            HttpServletRequest request,
            HttpServletResponse response)
            throws ServletException, IOException {

        request.setCharacterEncoding("UTF-8");

        ProductDao dao = new ProductDao();

        List<ProductDto> itemList = dao.findAll();

        request.setAttribute("itemList", itemList);

        // ★ここからメッセージ処理
        HttpSession session = request.getSession();

        String successMsg =
                (String) session.getAttribute("successMsg");

        if (successMsg != null) {
            request.setAttribute("successMsg", successMsg);
            session.removeAttribute("successMsg");
        }

        request.getRequestDispatcher(
                "/WEB-INF/jsp/ProductList.jsp")
                .forward(request, response);
    }
}