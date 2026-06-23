<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE html>
<html lang="ja">
<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<title>商品一覧 | Cafe売上管理システム</title>

<link rel="preconnect" href="https://fonts.googleapis.com">
<link rel="preconnect" href="https://fonts.gstatic.com" crossorigin>
<link href="https://fonts.googleapis.com/css2?family=Noto+Sans+JP:wght@400;500;700&display=swap" rel="stylesheet">

<style>
:root {
    --sb-dark-green: #1e3932;
    --sb-light-green: #00704a;
    --sb-accent-green: #d4e9e2;
    --sb-warm-white: #f2f0eb;
    --text-dark: #212121;
    --warning-orange: #f0ad4e;
    --danger-red: #d9534f;
}

body {
    margin: 0;
    font-family: 'Noto Sans JP', "Yu Gothic", sans-serif;
    background: var(--sb-warm-white);
    color: var(--text-dark);
}

.header {
    background: var(--sb-dark-green);
    color: white;
    padding: 20px 40px;
    display: flex;
    justify-content: space-between;
    align-items: center;
    box-shadow: 0 4px 12px rgba(0, 0, 0, 0.1);
}

.header-title {
    font-size: 22px;
    font-weight: 700;
}

.back-home {
    color: white;
    text-decoration: none;
    font-size: 14px;
    background: rgba(255, 255, 255, 0.15);
    padding: 8px 16px;
    border-radius: 20px;
    transition: 0.2s;
}
.header-dog-icon {
    width: 60px;
    height: 60px;
    object-fit: contain;
    margin-right: 10px;
}
.header-left {
    display: flex;
    align-items: center;
    gap: 12px;
}
.back-home:hover {
    background: var(--sb-light-green);
}

.container {
    width: 90%;
    max-width: 1200px;
    margin: 40px auto;
}

.add-btn {
    display: inline-block;
    padding: 12px 24px;
    background: var(--sb-light-green);
    color: white;
    border-radius: 25px;
    text-decoration: none;
    font-weight: bold;
    margin-bottom: 20px;
    box-shadow: 0 4px 10px rgba(0,0,0,0.1);
    transition: 0.2s;
}

.add-btn:hover {
    background: var(--sb-dark-green);
    transform: translateY(-2px);
}

table {
    width: 100%;
    border-collapse: collapse;
    background: white;
    border-radius: 12px;
    overflow: hidden;
    box-shadow: 0 6px 16px rgba(0,0,0,0.05);
}

th, td {
    padding: 16px;
    border-bottom: 1px solid #eee;
    text-align: center;
}

th {
    background: #e6f2ec;
    color: var(--sb-dark-green);
    font-weight: 700;
}

th:last-child,
td:last-child {
    width: 180px;
}

.btn {
    padding: 6px 16px;
    border-radius: 20px;
    text-decoration: none;
    color: white;
    font-size: 13px;
    font-weight: 500;
    display: inline-block;
    transition: 0.2s;
}

.btn:hover {
    opacity: 0.85;
    transform: translateY(-1px);
}

.edit {
    background: var(--warning-orange);
}

.delete {
    background: var(--danger-red);
}

.action {
    display: flex;
    justify-content: center;
    gap: 8px;
}

.status-badge {
    padding: 4px 12px;
    border-radius: 12px;
    font-size: 12px;
    font-weight: bold;
}

.status-on {
    background: var(--sb-accent-green);
    color: var(--sb-dark-green);
}

.status-off {
    background: #e0e0e0;
    color: #666;
}

.empty-message {
    text-align: center;
    padding: 30px;
    color: #666;
}
.success-message {
    background: #d4edda;
    color: #155724;
    border: 1px solid #c3e6cb;
    padding: 15px;
    margin-bottom: 20px;
    border-radius: 8px;
    text-align: center;
    font-weight: bold;
}
</style>

</head>

<body>
<c:set var="user" value="${sessionScope.loginUser}" />

<div class="header">

    <div class="header-left">
        <img
            src="${pageContext.request.contextPath}/images/dog-product.png"
            alt="Dog Icon Product"
            class="header-dog-icon">

        <div class="header-title">
            商品一覧
        </div>
    </div>

    <a href="${pageContext.request.contextPath}/dashboard"
       class="back-home">
       ← メインメニューへ戻る
    </a>

</div>
</div>

<div class="container">
<c:if test="${not empty successMsg}">
    <div class="success-message">
        ${successMsg}
    </div>
</c:if>

    <c:if test="${user.role == 'MANAGER'}">
    <a href="${pageContext.request.contextPath}/ProductAddServlet" class="add-btn">
        ＋ 新しい商品を追加
    </a>
</c:if>

    <table>

        <tr>
            <th>商品ID</th>
            <th>商品名</th>
            <th>カテゴリー名</th>
            <th>価格</th>
            <th>販売中</th>
            <th>更新日時</th>
            <th>操作</th>
        </tr>

        <c:choose>

            <c:when test="${not empty itemList}">

                <c:forEach var="item" items="${itemList}">

                    <tr>
                        <td>${item.itemId}</td>

                        <td>
                            <strong>${item.itemName}</strong>
                        </td>

                        <td>${item.categoryName}</td>

                        <td>${item.price}円</td>

                        <td>
                            <c:choose>

                               <c:when test="${item.sellingFlg == 1}">
    <span class="status-badge status-on">販売中</span>
</c:when>
<c:otherwise>
    <span class="status-badge status-off">停止</span>
</c:otherwise>

                            </c:choose>
                        </td>

                        <td>${item.updateAt}</td>

                        <td>
    <c:if test="${user.role == 'MANAGER'}">
        <div class="action">

            <a href="${pageContext.request.contextPath}/ProductEditorServlet?id=${item.itemId}"
               class="btn edit">
                編集
            </a>

            <form action="${pageContext.request.contextPath}/ProductDeleteServlet"
      method="post"
      onsubmit="return confirm('本当に削除しますか？');">

    <input type="hidden"
           name="id"
           value="${item.itemId}">

    <button type="submit"
            class="btn delete">
        削除
    </button>

</form>

        </div>
    </c:if>
</td>
                    </tr>

                </c:forEach>

            </c:when>

            <c:otherwise>

                <tr>
                    <td colspan="7" class="empty-message">
                        商品データがありません。
                    </td>
                </tr>

            </c:otherwise>

        </c:choose>

    </table>

</div>

</body>
</html>