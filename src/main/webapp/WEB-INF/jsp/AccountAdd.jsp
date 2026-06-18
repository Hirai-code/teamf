<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html lang="ja">
<head>
<meta charset="UTF-8">
<title>アカウント追加</title>

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

input, select {
    width: 100%;
    padding: 10px;
    border: 1px solid #ccc;
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

.back-btn:hover {
    opacity: 0.8;
}

.next-btn {
    background: #006241;
    color: white;
}

.next-btn:hover {
    opacity: 0.85;
}
</style>

</head>

<body>

<h1>アカウント追加</h1>

<!-- ここはInsert用Servletにするのが正解 -->
<form action="${pageContext.request.contextPath}/AccountInsertServlet" method="post">

    <div class="form-box">

        <div class="form-group">
            <label>ログインID</label>
            <input type="text" name="loginId" required>
        </div>

        <div class="form-group">
            <label>スタッフ名</label>
            <input type="text" name="staffName" required>
        </div>

        <div class="form-group">
            <label>パスワード</label>
            <input type="password" name="password" required>
        </div>

        <div class="form-group">
            <label>ロール</label>
            <select name="role">
                <option value="1">管理者</option>
                <option value="0">スタッフ</option>
            </select>
        </div>

        <div class="button-area">
            <button type="button" class="btn back-btn" onclick="history.back()">
                戻る
            </button>

            <button type="submit" class="btn next-btn">
                確認
            </button>
        </div>

    </div>

</form>

</body>
</html>