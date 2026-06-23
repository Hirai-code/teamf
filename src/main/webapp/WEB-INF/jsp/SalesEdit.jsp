<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib prefix="c"
uri="http://java.sun.com/jsp/jstl/core"%>


<!DOCTYPE html>
<html>
<head>

<meta charset="UTF-8">

<title>売上編集</title>


<style>

body{
font-family:"Yu Gothic";
background:#f2f0eb;
}


.container{

width:500px;
margin:50px auto;

background:white;

padding:30px;

border-radius:15px;

}


label{

font-weight:bold;

}


input,textarea{

width:100%;

padding:10px;

margin-top:8px;

}


button{

margin-top:20px;

padding:12px 30px;

border:none;

border-radius:20px;

background:#00704a;

color:white;

cursor:pointer;

}

.button-area{
    margin-top:20px;
    display:flex;
    gap:10px;
}

.button-area button{
    flex:1;
}

.back-btn{
    background:#999;
}


</style>

</head>


<body>


	<div class="container">
	
	
	<h2>売上メモ編集</h2>
	<c:if test="${not empty errorMessage}">
    <div style="
        background:#f8d7da;
        color:#842029;
        border:1px solid #f5c2c7;
        padding:10px;
        margin-bottom:15px;
        border-radius:8px;">
        ${errorMessage}
    </div>
</c:if>
	
	
	<form action="SalesEditServlet"
      method="post"
      onsubmit="return confirm('更新しますか？');">
	
	
	<input type="hidden"
	name="saleId"
	value="${sale.saleId}">
	
	
	
	<p>
	商品名：
	${sale.tradeName}
	</p>
	
	
	<p>
	数量：
	${sale.saleNumber}
	</p>
	
	
	<p>
	登録者：
	${sale.staffName}
	</p>
	
	
	
	<label>
	メモ
	</label>
	
	
	<textarea
	name="note"
	rows="5">${sale.note}</textarea>
	
	
	
	<button type="submit">
		保存
	</button>
	
	<button type="button"
			class="back-btn"
            onclick="location.href='SalesListServlet'">
        戻る
    </button>
	
	
	
	</form>
	
	
	</div>

</body>
</html>