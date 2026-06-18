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


        ProductDao dao = new ProductDao();


        request.setAttribute(
                "itemList",
                dao.findAll()
        );


        request.getRequestDispatcher(
                "/WEB-INF/jsp/SalesAdd.jsp")
                .forward(request,response);

    }




    @Override
    protected void doPost(
            HttpServletRequest request,
            HttpServletResponse response)
            throws ServletException, IOException {


        request.setCharacterEncoding("UTF-8");



        String salesDate =
                request.getParameter("salesDate");


        String itemId =
                request.getParameter("itemId");


        String quantity =
                request.getParameter("quantity");


        String memo =
                request.getParameter("memo");




        ProductDao productDao =
                new ProductDao();



        ProductDto product =
                productDao.findById(
                        Integer.parseInt(itemId)
                );




        HttpSession session =
                request.getSession();



        AccountDto loginUser =
                (AccountDto)
                session.getAttribute("loginUser");


        if(loginUser == null){

            response.sendRedirect(
                "login.jsp");

            return;
        }


        request.setAttribute(
                "salesDate",
                salesDate);



        request.setAttribute(
                "itemId",
                itemId);



        request.setAttribute(
                "quantity",
                quantity);



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
                Integer.parseInt(quantity));



        request.setAttribute(
                "loginUser",
                loginUser);



        request.getRequestDispatcher(
                "/WEB-INF/jsp/SalesAddConfirm.jsp")
                .forward(request,response);


    }


}