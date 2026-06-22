package portal.servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import portal.dao.SalesDao;
import portal.dto.SalesDto;

@WebServlet("/SalesEditServlet")
public class SalesEditServlet extends HttpServlet {

    private static final long serialVersionUID = 1L;

    // =========================
    // 売上編集画面表示（GET）
    // =========================
    @Override
    protected void doGet(
            HttpServletRequest request,
            HttpServletResponse response)
            throws ServletException, IOException {

        // ①ログインチェック（最初）
        HttpSession session = request.getSession(false);

        if (session == null || session.getAttribute("loginUser") == null) {
            response.sendRedirect(request.getContextPath() + "/login");
            return;
        }

        String id = request.getParameter("id");

        // IDなしは一覧へ戻す
        if (id == null || id.isEmpty()) {
            response.sendRedirect(request.getContextPath() + "/SalesListServlet");
            return;
        }

        SalesDao dao = new SalesDao();

        SalesDto sale = dao.findById(Integer.parseInt(id));

        // データがない場合も一覧へ
        if (sale == null) {
            response.sendRedirect(request.getContextPath() + "/SalesListServlet");
            return;
        }

        request.setAttribute("sale", sale);

        // 編集画面へ
        request.getRequestDispatcher("/WEB-INF/jsp/SalesEdit.jsp")
               .forward(request, response);
    }

    // =========================
    // 更新処理（POST）
    // =========================
    @Override
    protected void doPost(
            HttpServletRequest request,
            HttpServletResponse response)
            throws ServletException, IOException {

        // ①ログインチェック（最初）
        HttpSession session = request.getSession(false);

        if (session == null || session.getAttribute("loginUser") == null) {
            response.sendRedirect(request.getContextPath() + "/login");
            return;
        }

        request.setCharacterEncoding("UTF-8");

        // パラメータ取得
        int saleId = Integer.parseInt(request.getParameter("saleId"));
        String note = request.getParameter("note");

        // 更新処理
        SalesDao dao = new SalesDao();
        dao.updateNote(saleId, note);

        // メッセージ保存
        session.setAttribute("message", "売上を更新しました");

        // 一覧へ戻る
        response.sendRedirect(request.getContextPath() + "/SalesListServlet");
    }
}