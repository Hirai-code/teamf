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
    justify-content:flex-end;
}


.back-btn{
    background:#999;
}


</style>

</head>


<body>


	<div class="container">
	
	
	<h2>売上メモ編集</h2>
	
	
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
	
	
	<div class="button-area">
	
	<button type="button"
			class="back-btn"
            onclick="location.href='SalesListServlet'">
        戻る
    </button>
	
	<button type="submit">
		保存
	</button>
	
	
	
	
	</form>
	
	
	</div>

</body>
</html>