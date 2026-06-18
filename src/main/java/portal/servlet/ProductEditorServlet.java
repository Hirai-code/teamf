package portal.servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import portal.dao.ProductDao;
import portal.dto.ProductDto;

@WebServlet("/ProductEditorServlet")
public class ProductEditorServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    @Override
    protected void doGet(
            HttpServletRequest request,
            HttpServletResponse response)
            throws ServletException, IOException {

        request.setCharacterEncoding("UTF-8");

        // 商品ID取得
        String id = request.getParameter("id");

        if (id == null || id.isEmpty()) {
            response.sendRedirect(
                request.getContextPath()
                + "/ProductListServlet");
            return;
        }

        int itemId = Integer.parseInt(id);

        // 商品情報取得
        ProductDao dao = new ProductDao();
        ProductDto item = dao.findById(itemId);

        // JSPへ渡す
        request.setAttribute("item", item);

        // 編集画面表示
        request.getRequestDispatcher(
                "/WEB-INF/jsp/ProductEditor.jsp")
                .forward(request, response);
    }
}