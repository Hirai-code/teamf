package portal.servlet;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import portal.dao.SalesDao;
import portal.dto.SalesDto;

@WebServlet("/SalesListServlet")
public class SalesListServlet extends HttpServlet {


@Override
	protected void doGet(
		HttpServletRequest request,
		HttpServletResponse response)
				throws ServletException,IOException{

		request.setCharacterEncoding("UTF-8");
	
		String startDate=request.getParameter("startDate");
		String endDate=request.getParameter("endDate");
		
		String staffName=request.getParameter("staffName");
		
		String minAmount=request.getParameter("minAmount");
		String maxAmount=request.getParameter("maxAmount");
		
		
		
		/*
		期間チェック
		*/
		if(startDate != null &&
		   !startDate.isEmpty() &&
		   endDate != null &&
		   !endDate.isEmpty()) {


		    if(startDate.compareTo(endDate) > 0) {

		        request.setAttribute(
		            "errorMessage",
		            "期間Fromは期間To以前の日付を指定してください。"
		        );


		        request.setAttribute(
		            "startDate",
		            startDate
		        );

		        request.setAttribute(
		            "endDate",
		            endDate
		        );


		        request.setAttribute(
		            "staffName",
		            staffName
		        );

		        request.setAttribute(
		            "minAmount",
		            minAmount
		        );

		        request.setAttribute(
		            "maxAmount",
		            maxAmount
		        );


		        request.getRequestDispatcher(
		            "/WEB-INF/jsp/SalesSearch.jsp")
		            .forward(request,response);

		        return;

		    }

		}
	
	
	
		/*
		金額チェック
		*/
		
		if(minAmount != null &&
				   !minAmount.isEmpty() &&
				   maxAmount != null &&
				   !maxAmount.isEmpty()) {


				    if(Integer.parseInt(minAmount)
				       >
				       Integer.parseInt(maxAmount)) {


				        request.setAttribute(
				            "errorMessage",
				            "金額Fromは金額To以下にしてください。"
				        );


				        request.setAttribute(
				            "startDate",
				            startDate
				        );

				        request.setAttribute(
				            "endDate",
				            endDate
				        );

				        request.setAttribute(
				            "staffName",
				            staffName
				        );


				        request.setAttribute(
				            "minAmount",
				            minAmount
				        );


				        request.setAttribute(
				            "maxAmount",
				            maxAmount
				        );


				        request.getRequestDispatcher(
				            "/WEB-INF/jsp/SalesSearch.jsp")
				            .forward(request,response);


				        return;

				    }

				}
	
	
	
	SalesDao dao=new SalesDao();
	
	
	List<SalesDto> list=
	dao.search(
	startDate,
	endDate,
	staffName,
	minAmount,
	maxAmount);
	
	
	
	request.setAttribute(
	"salesList",
	list);
	
	
	
	request.getRequestDispatcher(
	"/WEB-INF/jsp/SalesList.jsp")
	.forward(request,response);
	
	
	}

}