<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>

<%
request.setCharacterEncoding("UTF-8");

String productName = (String)request.getAttribute("productName");

Integer categoryId = (Integer)request.getAttribute("categoryId");

Integer price = (Integer)request.getAttribute("price");

Integer saleFlag = (Integer)request.getAttribute("saleFlag");

String categoryName = "";

if (categoryId != null){

	if (categoryId == 1) {
    	categoryName = "Drink";
	} else if (categoryId == 2) {
    	categoryName = "Food";
	} else if (categoryId == 3) {
   		categoryName = "Goods";
	}
}

String saleFlagName = "";

if (saleFlag != null && saleFlag == 1) {
    saleFlagName = "販売中";
} else {
    saleFlagName = "販売停止";
}
%>

<!DOCTYPE html>
<html lang="ja">
<head>
<meta charset="UTF-8">
<title>商品追加確認</title>

<style>
body {
    margin: 0;
    font-family: "Yu Gothic", sans-serif;
    background: #f2f6f3;
}

h1 {
    text-align: center;
    color: #006241;
    margin-top: 30px;
}

.form-box {
    width: 500px;
    margin: 30px auto;
    background: #fff;
    padding: 30px;
    border-radius: 10px;
}

.row {
    margin-bottom: 15px;
}

.label {
    font-weight: bold;
}

.value {
    padding: 8px;
    background: #f5f5f5;
    border-radius: 5px;
}

.button-area {
    display: flex;
    justify-content: flex-end;
    gap: 10px;
    margin-top: 30px;
}

.btn {
    border: none;
    padding: 10px 25px;
    border-radius: 5px;
    cursor: pointer;
}

.back-btn {
    background: #999;
    color: white;
}

.submit-btn {
    background: #006241;
    color: white;
}
</style>

</head>

<body>

<h1>商品追加確認</h1>

<div class="form-box">

    <div class="row">
        <div class="label">商品名</div>
        <div class="value"><%= productName %></div>
    </div>

    <div class="row">
        <div class="label">カテゴリー</div>
        <div class="value"><%= categoryName %></div>
    </div>

    <div class="row">
        <div class="label">価格</div>
        <div class="value"><%= price %> 円</div>
    </div>

    <div class="row">
        <div class="label">販売フラグ</div>
        <div class="value"><%= saleFlagName %></div>
    </div>

    <form action="ProductRegistServlet" method="post">

        <input type="hidden" name="productName" value="<%= productName %>">
        <input type="hidden" name="categoryId" value="<%= categoryId %>">
        <input type="hidden" name="price" value="<%= price %>">
        <input type="hidden" name="saleFlag" value="<%= saleFlag %>">

        <div class="button-area">

            <button type="button" class="btn back-btn" onclick="history.back()">
                戻る
            </button>

            <button type="submit" class="btn submit-btn">
                登録する
            </button>

        </div>

    </form>

</div>

</body>
</html>>