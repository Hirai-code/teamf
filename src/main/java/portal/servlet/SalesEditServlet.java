package portal.servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import portal.dao.SalesDao;
import portal.dto.AccountDto;
import portal.dto.SalesDto;

@WebServlet("/SalesEditServlet")
public class SalesEditServlet extends HttpServlet {

    @Override
    protected void doGet(
            HttpServletRequest request,
            HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession session = request.getSession(false);

        if (session == null) {
            response.sendRedirect(
                    request.getContextPath() + "/login");
            return;
        }

        AccountDto loginUser =
                (AccountDto) session.getAttribute("loginUser");

        if (loginUser == null) {
            response.sendRedirect(
                    request.getContextPath() + "/login");
            return;
        }

        String idStr = request.getParameter("id");

        if (idStr == null || idStr.trim().isEmpty()) {
            response.sendRedirect(
                    request.getContextPath() + "/SalesListServlet");
            return;
        }

        int saleId;
        try {
            saleId = Integer.parseInt(idStr);
        } catch (NumberFormatException e) {
            response.sendRedirect(
                    request.getContextPath() + "/SalesListServlet");
            return;
        }

        SalesDao dao = new SalesDao();
        SalesDto sale = dao.findById(saleId);

        if (sale == null) {
            response.sendRedirect(
                    request.getContextPath() + "/SalesListServlet");
            return;
        }

        request.setAttribute("sale", sale);
        request.setAttribute("loginUser", loginUser);

        request.getRequestDispatcher(
                "/WEB-INF/jsp/SalesEdit.jsp")
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
                    request.getContextPath() + "/login");
            return;
        }

        AccountDto loginUser =
                (AccountDto) session.getAttribute("loginUser");

        if (loginUser == null) {
            response.sendRedirect(
                    request.getContextPath() + "/login");
            return;
        }

        request.setCharacterEncoding("UTF-8");

        String saleIdStr = request.getParameter("saleId");
        String note = request.getParameter("note");

        if (saleIdStr == null || saleIdStr.trim().isEmpty()) {
            response.sendRedirect(
                    request.getContextPath() + "/SalesListServlet");
            return;
        }

        int saleId;
        try {
            saleId = Integer.parseInt(saleIdStr);
        } catch (NumberFormatException e) {
            response.sendRedirect(
                    request.getContextPath() + "/SalesListServlet");
            return;
        }

        SalesDao dao = new SalesDao();

        SalesDto sale = dao.findById(saleId);
        if (sale == null) {
            response.sendRedirect(
                    request.getContextPath() + "/SalesListServlet");
            return;
        }

        int result = dao.updateNote(saleId, note);

        if (result > 0) {
            session.setAttribute("message", "売上を更新しました");
        } else {
            session.setAttribute("message", "更新に失敗しました");
        }

        response.sendRedirect(
                request.getContextPath() + "/SalesListServlet");
    }
}