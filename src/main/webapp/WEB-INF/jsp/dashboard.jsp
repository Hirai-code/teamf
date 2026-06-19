<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ page import="portal.dto.AccountDto" %>

<%
AccountDto loginUser =
    (AccountDto) session.getAttribute("loginUser");
%>

<!DOCTYPE html>
<html lang="ja">
<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<title>ダッシュボード | Cafe売上管理システム</title>

<link rel="preconnect" href="https://fonts.googleapis.com">
<link rel="preconnect" href="https://fonts.gstatic.com" crossorigin>
<link href="https://fonts.googleapis.com/css2?family=Noto+Sans+JP:wght@400;500;700&display=swap" rel="stylesheet">

<style>
:root{
    --sb-dark-green:#1e3932;
    --sb-light-green:#00704a;
    --sb-accent-green:#d4e9e2;
    --sb-warm-white:#f2f0eb;
    --text-dark:#212121;
    --text-muted:#6b6b6b;
}

*{
    box-sizing:border-box;
    margin:0;
    padding:0;
}

body {
    font-family: 'Noto Sans JP', "Yu Gothic", sans-serif;

    background:
        linear-gradient(
            rgba(242,240,235,0.85),
            rgba(242,240,235,0.85)
        ),
        url("${pageContext.request.contextPath}/images/coffee-bg.png");

    background-size: cover;
    background-position: center;
    background-repeat: no-repeat;
    background-attachment: fixed;

    color: var(--text-dark);
    min-height: 100vh;
    display: flex;
    flex-direction: column;
}

.header{
    background-color:var(--sb-dark-green);
    color:white;
    padding:24px 40px;
    display:flex;
    justify-content:space-between;
    align-items:center;
    box-shadow:0 4px 12px rgba(0,0,0,0.1);
}

.header-title{
    font-size:24px;
    font-weight:700;
    letter-spacing:0.05em;
    display:flex;
    align-items:center;
    gap:12px;
}

.user-info{
    display:flex;
    align-items:center;
    gap:16px;
    font-size:14px;
    background-color:rgba(255,255,255,0.1);
    padding:8px 16px;
    border-radius:20px;
}

.user-actions{
    display:flex;
    align-items:center;
    gap:10px;
}

.logout-btn{
    background:#d32f2f;
    color:white;
    text-decoration:none;
    padding:6px 12px;
    border-radius:16px;
    font-size:13px;
    font-weight:700;
    transition:.3s;
}

.logout-btn:hover{
    background:#b71c1c;
    transform:translateY(-1px);
}
}

.user-role{
    background-color:var(--sb-light-green);
    color:white;
    padding:2px 8px;
    border-radius:4px;
    font-size:11px;
    font-weight:bold;
}

.main-content{
    flex:1;
    display:flex;
    flex-direction:column;
    justify-content:center;
    align-items:center;
    padding:60px 20px;
}

.welcome-message{
    text-align:center;
    margin-bottom:50px;
}

.welcome-message h1{
    font-size:28px;
    color:var(--sb-dark-green);
    margin-bottom:8px;
}

.welcome-message p{
    color:var(--text-muted);
    font-size:15px;
}

.container{
    display:flex;
    justify-content:center;
    align-items:center;
    gap:32px;
    max-width:1100px;
    width:100%;
    flex-wrap:wrap;
}

.menu-card{
    width:280px;
    height:220px;
    background:white;
    border-radius:16px;
    box-shadow:0 6px 16px rgba(0,0,0,0.06);
    display:flex;
    flex-direction:column;
    justify-content:center;
    align-items:center;
    text-decoration:none;
    color:var(--sb-dark-green);
    position:relative;
    overflow:hidden;
    border:1px solid rgba(0,0,0,0.04);
    transition:all .3s;
}

.menu-card:hover{
    transform:translateY(-8px);
    box-shadow:0 12px 24px rgba(30,57,50,.15);
    border-color:var(--sb-light-green);
}

.menu-card::before{
    content:'';
    position:absolute;
    top:0;
    left:0;
    width:100%;
    height:6px;
    background-color:var(--sb-light-green);
    opacity:0;
    transition:.3s;
}

.menu-card:hover::before{
    opacity:1;
}

.icon-wrapper{
    width:70px;
    height:70px;
    background-color:var(--sb-accent-green);
    border-radius:50%;
    display:flex;
    justify-content:center;
    align-items:center;
    font-size:32px;
    margin-bottom:20px;
}

.title{
    font-size:20px;
    font-weight:700;
    margin-bottom:6px;
}

.description{
    font-size:13px;
    color:var(--text-muted);
    text-align:center;
    padding:0 20px;
}

.footer{
    text-align:center;
    padding:20px;
    font-size:12px;
    color:var(--text-muted);
    border-top:1px solid rgba(0,0,0,0.05);
}
</style>
</head>

<body>

<div class="header">

    <div class="header-title">
        <span>☕</span>
        Shoma-Cafe Backroom System
    </div>

 <div class="user-info">

    <div class="user-actions">

        <span class="user-role">
            <%= loginUser != null ? loginUser.getRole() : "" %>
        </span>

        <span>
            <%= loginUser != null ? loginUser.getStaffName() : "" %>
        </span>

        <a href="${pageContext.request.contextPath}/logout"
           class="logout-btn">
            ログアウト
        </a>

    </div>

</div>
</div>

<div class="main-content">

    <div class="welcome-message">
        <h1>メインメニュー</h1>
        <p>業務を行うメニューを選択してください。</p>
    </div>

    <div class="container">

        <a href="${pageContext.request.contextPath}/item" class="menu-card">
            <div class="icon-wrapper">☕</div>
            <div class="title">商品一覧</div>
            <div class="description">
                商品マスタの確認、新しい商品の登録・編集を行います。
            </div>
        </a>

        <a href="${pageContext.request.contextPath}/SalesSearchServlet"
   class="menu-card">
            <div class="icon-wrapper">💹</div>
            <div class="title">売上管理</div>
            <div class="description">
                日々の売上の検索、登録、編集、削除を行います。
            </div>
        </a>

        <%
	if(loginUser != null &&
	   "MANAGER".equals(loginUser.getRole())){
	%>
	
	<a href="${pageContext.request.contextPath}/AccountHomeServlet"
	   class="menu-card">
	
	    <div class="icon-wrapper">👥</div>
	
	    <div class="title">
	        アカウント管理
	    </div>
	
	    <div class="description">
	        スタッフのアカウント一覧、新規追加、権限編集を行います。
	    </div>
	
	</a>
	
	<%
	}
	%>

    </div>

</div>

<div class="footer">
    &copy; 2026 Cafe Backroom System All Rights Reserved.
</div>

</body>
</html>