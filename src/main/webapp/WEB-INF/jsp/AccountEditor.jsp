<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib prefix="c"
uri="http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE html>

<html lang="ja">
<head>
<meta charset="UTF-8">
<meta name="viewport"
      content="width=device-width, initial-scale=1.0">

<title>アカウント編集 | Cafe売上管理システム</title>

<link rel="preconnect"
      href="https://fonts.googleapis.com">

<link rel="preconnect"
      href="https://fonts.gstatic.com"
      crossorigin>

<link href="https://fonts.googleapis.com/css2?family=Noto+Sans+JP:wght@400;500;700&display=swap"
      rel="stylesheet">

<style>

:root{
    --sb-dark-green:#1e3932;
    --sb-light-green:#00704a;
    --sb-accent-green:#d4e9e2;
    --sb-warm-white:#f2f0eb;

    --text-dark:#212121;
    --text-muted:#6b6b6b;

    --danger-red:#d9534f;
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

    box-shadow:0 4px 12px rgba(0,0,0,0.1);
}

.header-title{
    font-size:22px;
    font-weight:700;
    letter-spacing:0.05em;
}

.top-right{
    display:flex;
    align-items:center;
    gap:10px;
}

.nav-btn{
    color:white;
    text-decoration:none;

    font-size:14px;

    background:rgba(255,255,255,0.15);

    padding:8px 16px;

    border-radius:20px;

    transition:0.2s;
}

.nav-btn:hover{
    background:var(--sb-light-green);
}

/* コンテンツ */

.container{
    width:95%;
    max-width:800px;
    margin:40px auto;
}

.card{
    background:white;

    border-radius:12px;

    padding:35px;

    box-shadow:0 6px 16px rgba(0,0,0,0.05);
}

.section-title{
    margin-top:0;
    margin-bottom:25px;

    color:var(--sb-dark-green);

    border-bottom:2px solid var(--sb-accent-green);

    padding-bottom:10px;
}

/* エラー表示 */

.error-box{
    background:#ffeaea;
    border:1px solid #f3c1c1;

    color:var(--danger-red);

    padding:12px;

    border-radius:8px;

    margin-bottom:20px;
}

/* フォーム */

.form-group{
    margin-bottom:20px;
}

.form-group label{
    display:block;

    margin-bottom:8px;

    font-weight:700;

    color:var(--sb-dark-green);
}

.form-control{
    width:100%;

    padding:12px;

    border:1px solid #ccc;

    border-radius:8px;

    box-sizing:border-box;

    font-size:14px;
}

.form-control:focus{
    outline:none;

    border-color:var(--sb-light-green);

    box-shadow:0 0 0 3px rgba(0,112,74,0.15);
}

/* ボタン */

.button-area{
    display:flex;
    justify-content:center;
    gap:12px;

    margin-top:30px;
}

.btn{
    border:none;

    border-radius:20px;

    padding:10px 24px;

    color:white;

    cursor:pointer;

    font-size:14px;
    font-weight:500;

    transition:0.2s;
}

.btn:hover{
    opacity:0.85;
    transform:translateY(-1px);
}

.btn-back{
    background:#6c757d;
}

.btn-save{
    background:var(--sb-light-green);
}

</style>

</head>

<body>

<!-- ヘッダー -->

<div class="header">
<div class="header-title">
    👤 アカウント編集
</div>

<div class="top-right">

    <a href="AccountHomeServlet"
       class="nav-btn">
        📋 アカウント一覧
    </a>

    <a href="${pageContext.request.contextPath}/dashboard"
       class="nav-btn">
        ← メインメニュー
    </a>

</div>

</div>

<!-- メイン -->

<div class="container">
<div class="card">

    <h2 class="section-title">
        アカウント情報
    </h2>

    <c:if test="${not empty errorMessage}">
        <div class="error-box">
            ${errorMessage}
        </div>
    </c:if>

    <form action="${pageContext.request.contextPath}/AccountUpdateServlet"
          method="post">

        <input type="hidden"
               name="accountId"
               value="${account.accountId}">

        <div class="form-group">

            <label>ログインID</label>

            <input type="text"
                   name="loginId"
                   class="form-control"
                   value="${account.loginId}"
                   minlength="4"
                   maxlength="50"
                   required>

        </div>

        <div class="form-group">

            <label>スタッフ名</label>

            <input type="text"
                   name="staffName"
                   class="form-control"
                   value="${account.staffName}"
                   maxlength="50"
                   required>

        </div>

        <div class="form-group">

            <label>パスワード</label>

            <input type="password"
                   name="password"
                   class="form-control"
                   minlength="8"
                   required>

        </div>

        <div class="form-group">

            <label>ロール</label>

            <select name="role"
                    class="form-control">

                <option value="MANAGER"
                    <c:if test="${account.role == 'MANAGER'}">
                        selected
                    </c:if>>
                    店長
                </option>

                <option value="STAFF"
                    <c:if test="${account.role == 'STAFF'}">
                        selected
                    </c:if>>
                    売上登録専用
                </option>

            </select>

        </div>

        <div class="button-area">

            <button type="button"
                    class="btn btn-back"
                    onclick="location.href='AccountHomeServlet'">
                戻る
            </button>

            <button type="submit"
                    class="btn btn-save">
                保存
            </button>

        </div>

    </form>

</div>

</div>

</body>
</html>
