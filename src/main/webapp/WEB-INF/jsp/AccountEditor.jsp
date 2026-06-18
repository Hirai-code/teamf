<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ page import="portal.dto.AccountDto" %>

<%
AccountDto account =
    (AccountDto) request.getAttribute("account");
%>

<!DOCTYPE html>
<html lang="ja">
<head>
<meta charset="UTF-8">
<title>アカウント編集</title>

<style>
body{
    margin:0;
    font-family:"Yu Gothic", sans-serif;
    background:#dff3e3;
}

.container{
    width:500px;
    margin:50px auto;
    background:white;
    padding:30px;
    border-radius:15px;
    box-shadow:0 4px 15px rgba(0,0,0,0.15);
}

h1{
    text-align:center;
    color:#006241;
    margin-bottom:30px;
}

.form-group{
    margin-bottom:20px;
}

label{
    display:block;
    margin-bottom:8px;
    font-weight:bold;
    color:#006241;
}

input,
select{
    width:100%;
    padding:12px;
    border:1px solid #ccc;
    border-radius:5px;
    box-sizing:border-box;
}

.button-area{
    display:flex;
    justify-content:flex-end;
    gap:10px;
    margin-top:30px;
}

.back-btn{
    background:#999;
    color:white;
    border:none;
    padding:12px 25px;
    border-radius:5px;
    cursor:pointer;
}

.update-btn{
    background:#006241;
    color:white;
    border:none;
    padding:12px 25px;
    border-radius:5px;
    cursor:pointer;
}

.update-btn:hover{
    background:#004d33;
}
</style>

</head>
<body>

<div class="container">

    <h1>👤 アカウント編集</h1>

    <form action="AccountEditorServlet" method="post">

        <div class="form-group">
            <label>アカウントID</label>
            <input type="text" name="accountId"
                   value="<%= account != null ? account.getAccountId() : "" %>"
                   readonly>
        </div>

        <div class="form-group">
            <label>ログインID</label>
            <input type="text" name="loginId"
                   value="<%= account != null ? account.getLoginId() : "" %>"
                   required>
        </div>

        <div class="form-group">
            <label>スタッフ名</label>
            <input type="text" name="staffName"
                   value="<%= account != null ? account.getStaffName() : "" %>"
                   required>
        </div>

        <div class="form-group">
            <label>パスワード</label>
            <input type="password" name="password" required>
        </div>

        <div class="form-group">
            <label>ロール</label>
            <select name="role" required>
                <option value="">選択してください</option>

                <option value="MANAGER"
                    <%= account != null && "MANAGER".equals(account.getRole()) ? "selected" : "" %>>
                    店長
                </option>

                <option value="STAFF"
                    <%= account != null && "STAFF".equals(account.getRole()) ? "selected" : "" %>>
                    スタッフ
                </option>

            </select>
        </div>

        <div class="button-area">

            <button type="button"
                    class="back-btn"
                    onclick="history.back()">
                戻る
            </button>

            <button type="submit"
                    class="update-btn">
                更新
            </button>

        </div>

    </form>

</div>

</body>
</html>