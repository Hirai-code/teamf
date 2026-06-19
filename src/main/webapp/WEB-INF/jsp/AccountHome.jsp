<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE html>
<html lang="ja">
<head>
<meta charset="UTF-8">
<title>アカウント一覧</title>

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

body{
    margin:0;
    font-family:'Noto Sans JP',"Yu Gothic",sans-serif;
    background:var(--sb-warm-white);
    color:var(--text-dark);
}

/* ヘッダー */

.header{
    background:var(--sb-dark-green);
    color:white;
    padding:20px 40px;
    display:flex;
    justify-content:space-between;
    align-items:center;
    box-shadow:0 4px 12px rgba(0,0,0,.1);
}

.header h1{
    margin:0;
    font-size:22px;
    font-weight:700;
}

.back-home{
    color:white;
    text-decoration:none;
    font-size:14px;
    background:rgba(255,255,255,.15);
    padding:8px 16px;
    border-radius:20px;
    transition:.2s;
}

.back-home:hover{
    background:var(--sb-light-green);
}

/* メイン */

.container{
    width:90%;
    max-width:1200px;
    margin:40px auto;
}
.success-message{
    background:#d4edda;
    color:#155724;
    border:1px solid #c3e6cb;
    padding:12px 20px;
    margin-bottom:20px;
    border-radius:8px;
    font-weight:bold;
}

/* テーブル */

table{
    width:100%;
    border-collapse:collapse;
    background:white;
    border-radius:12px;
    overflow:hidden;
    box-shadow:0 6px 16px rgba(0,0,0,.05);
}

th,
td{
    padding:16px;
    border-bottom:1px solid #eee;
    text-align:center;
}

th{
    background:#e6f2ec;
    color:var(--sb-dark-green);
    font-weight:700;
}

/* ボタン */

.action{
    display:flex;
    justify-content:center;
    gap:8px;
}

.edit-btn,
.delete-btn{
    border:none;
    padding:8px 16px;
    border-radius:20px;
    color:white;
    cursor:pointer;
    font-size:13px;
    font-weight:500;
    transition:.2s;
}

.edit-btn{
    background:var(--warning-orange);
}

.delete-btn{
    background:var(--danger-red);
}

.edit-btn:hover,
.delete-btn:hover{
    opacity:.85;
    transform:translateY(-1px);
}

/* 追加ボタン */

.add-btn{
    position:fixed;
    right:30px;
    bottom:30px;

    width:65px;
    height:65px;

    border:none;
    border-radius:50%;

    background:var(--sb-light-green);
    color:white;

    font-size:36px;
    font-weight:bold;

    cursor:pointer;

    box-shadow:0 4px 12px rgba(0,0,0,.2);

    transition:.2s;
}

.add-btn:hover{
    background:var(--sb-dark-green);
    transform:scale(1.05);
}

/* データなし */

.empty-message{
    text-align:center;
    padding:30px;
    color:#666;
}
</style>
</head>

<body>

<div class="header">
    <h1>👤 アカウント一覧</h1>
    <a href="${pageContext.request.contextPath}/dashboard" class="back-home">
    ← メインメニューへ戻る
</a>
</div>

<div class="container">
<c:if test="${not empty sessionScope.successMessage}">
    <div class="success-message">
        ${sessionScope.successMessage}
    </div>
    <c:remove var="successMessage" scope="session"/>
</c:if>

<c:if test="${empty accountList}">
    <div class="empty-message">
        アカウント情報がありません。
    </div>
</c:if>

<c:if test="${not empty accountList}">

<table>

    <tr>
        <th>アカウントID</th>
        <th>ログインID</th>
        <th>スタッフ名</th>
        <th>ロール</th>
        <th>更新日時</th>
        <th>編集</th>
        <th>削除</th>
    </tr>

    <c:forEach var="acc" items="${accountList}">

    <tr>
        <td>${acc.accountId}</td>
        <td>${acc.loginId}</td>
        <td>${acc.staffName}</td>
        <td>${acc.role}</td>
        <td>${acc.updateAt}</td>

        <!-- 編集（GET） -->
            <td>
        <form action="${pageContext.request.contextPath}/AccountEditorServlet" method="get" style="display:inline;">
            <input type="hidden" name="id" value="${acc.accountId}">
            <button type="submit" class="edit-btn">編集</button>
        </form>
    </td>

    <!-- 削除ボタン（POST） -->
    <td>
        <form action="${pageContext.request.contextPath}/AccountDeleteServlet"
      method="post"
              style="display:inline;"
              onsubmit="return confirm('削除しますか？');">

            <input type="hidden" name="id" value="${acc.accountId}">
            <button type="submit" class="delete-btn">削除</button>
        </form>
    </td>

</tr>

    </c:forEach>

</table>
</c:if>

</div>

<form action="${pageContext.request.contextPath}/AccountAddServlet">
    <button type="submit" class="add-btn">＋</button>
</form>

</body>
</html>