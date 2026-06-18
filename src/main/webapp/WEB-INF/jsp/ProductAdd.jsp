<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE html>
<html lang="ja">
<head>
<meta charset="UTF-8">
<title>商品追加</title>

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

.error-message {
    width: 500px;
    margin: 20px auto;
    padding: 12px;
    background: #ffe5e5;
    border: 1px solid #d9534f;
    color: #d9534f;
    border-radius: 5px;
    text-align: center;
    font-weight: bold;
}

.form-box {
    width: 500px;
    margin: 30px auto;
    background: #ffffff;
    padding: 30px;
    border-radius: 10px;
    box-shadow: 0 5px 15px rgba(0,0,0,0.1);
}

.form-group {
    margin-bottom: 20px;
}

label {
    display: block;
    font-weight: bold;
    margin-bottom: 8px;
}

input,
select {
    width: 100%;
    padding: 10px;
    border: 1px solid #ccc;
    border-radius: 5px;
    box-sizing: border-box;
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

.next-btn {
    background: #006241;
    color: white;
}

.next-btn:hover {
    opacity: 0.9;
}

.back-btn:hover {
    opacity: 0.9;
}
</style>

</head>

<body>

<h1>商品追加</h1>

<c:if test="${not empty errorMsg}">
    <div class="error-message">
        ${errorMsg}
    </div>
</c:if>

<form action="ProductAddServlet" method="post">

    <div class="form-box">

        <div class="form-group">
            <label>商品名</label>
            <input type="text"
                   name="productName"
                   value="${param.productName}">
        </div>

        <div class="form-group">
            <label>カテゴリー</label>
            <select name="categoryId">
                <option value="">選択してください</option>

                <option value="1"
                    ${param.categoryId == '1' ? 'selected' : ''}>
                    Drink
                </option>

                <option value="2"
                    ${param.categoryId == '2' ? 'selected' : ''}>
                    Food
                </option>

                <option value="3"
                    ${param.categoryId == '3' ? 'selected' : ''}>
                    Goods
                </option>
            </select>
        </div>

        <div class="form-group">
            <label>価格</label>
            <input type="number"
                   name="price"
                   min="0"
                   value="${param.price}">
        </div>

        <div class="form-group">
            <label>販売フラグ</label>

            <select name="saleFlag">

                <option value="1"
                    ${empty param.saleFlag || param.saleFlag == '1' ? 'selected' : ''}>
                    販売中
                </option>

                <option value="0"
                    ${param.saleFlag == '0' ? 'selected' : ''}>
                    販売停止
                </option>

            </select>
        </div>

        <div class="button-area">

            <button type="button"
                    class="btn back-btn"
                    onclick="history.back()">
                戻る
            </button>

            <button type="submit"
                    class="btn next-btn">
                登録
            </button>

        </div>

    </div>

</form>

</body>
</html>