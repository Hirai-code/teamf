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

    String salesDate = request.getParameter("salesDate");
    String itemId = request.getParameter("itemId");
    String quantity = request.getParameter("quantity");
    String memo = request.getParameter("memo");
    

    if (memo != null && memo.length() > 500) {

        request.setAttribute(
                "errorMessage",
                "メモは500文字以内で入力してください。");

        request.setAttribute("salesDate", salesDate);
        request.setAttribute("itemId", itemId);
        request.setAttribute("quantity", quantity);
        request.setAttribute("memo", memo);

        ProductDao productDao = new ProductDao();

        request.setAttribute(
                "itemList",
                productDao.findAll());

        request.getRequestDispatcher(
                "/WEB-INF/jsp/SalesAdd.jsp")
                .forward(request, response);

        return;
    }

    ProductDao productDao = new ProductDao();

    ProductDto product =
            productDao.findById(Integer.parseInt(itemId));

    HttpSession session = request.getSession();

    AccountDto loginUser =
            (AccountDto) session.getAttribute("loginUser");

    if (loginUser == null) {
        response.sendRedirect("login.jsp");
        return;
    }

    SalesDto dto = new SalesDto();

    dto.setSaleDate(Date.valueOf(salesDate));
    dto.setItemId(Integer.parseInt(itemId));
    dto.setSaleNumber(Integer.parseInt(quantity));
    dto.setTradeName(product.getItemName());
    dto.setUnitPrice(product.getPrice());
    dto.setCategoryId(product.getCategoryId());
    dto.setAccountId(loginUser.getAccountId());
    dto.setUpdateBy(loginUser.getAccountId());
    dto.setNote(memo);

    SalesDao dao = new SalesDao();

    int result = dao.insert(dto);

    if (result == 0) {
        request.setAttribute("errorMessage", "売上登録に失敗しました。");
        request.getRequestDispatcher("/WEB-INF/jsp/SalesAdd.jsp")
               .forward(request, response);
        return;
    }

    session.setAttribute("message", "売上の登録をしました");

    response.sendRedirect("SalesListServlet");
}
}