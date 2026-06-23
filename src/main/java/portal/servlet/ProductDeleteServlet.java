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

@WebServlet("/ProductDeleteServlet")
public class ProductDeleteServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    @Override
    protected void doPost(HttpServletRequest request,
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

        int itemId = Integer.parseInt(
                request.getParameter("id"));

        ProductDao dao = new ProductDao();

        dao.delete(itemId);
        

        

        session.setAttribute(
                "successMsg",
                "商品を削除しました。");

        response.sendRedirect(
                request.getContextPath()
                + "/item");
        return;
    }
}