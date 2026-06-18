<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html lang="ja">
<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<title>売上検索 | Cafe売上管理システム</title>

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
    --text-muted: #6b6b6b;
    --danger-red: #d9534f;
    --border-color: #ddd;
}

* {
    box-sizing: border-box;
    margin: 0;
    padding: 0;
}

body {
    font-family: 'Noto Sans JP', "Yu Gothic", sans-serif;
    background: var(--sb-warm-white);
    color: var(--text-dark);
}

/* ヘッダー */
.header {
    background: var(--sb-dark-green);
    color: white;
    padding: 20px 40px;
    display: flex;
    justify-content: space-between;
    align-items: center;
    box-shadow: 0 4px 12px rgba(0,0,0,0.1);
}

.header-title {
    font-size: 22px;
    font-weight: 700;
    letter-spacing: 0.05em;
}

.back-home {
    color: white;
    text-decoration: none;
    font-size: 14px;
    background: rgba(255,255,255,0.15);
    padding: 8px 16px;
    border-radius: 20px;
    transition: 0.2s;
    font-weight: 500;
}

.back-home:hover {
    background: var(--sb-light-green);
}

/* コンテナ */
.container {
    width: 90%;
    max-width: 650px;
    margin: 50px auto;
}

/* カード */
.search-card {
    background: white;
    padding: 40px;
    border-radius: 16px;
    box-shadow: 0 6px 16px rgba(0,0,0,0.05);
    border: 1px solid rgba(0,0,0,0.04);
}

.search-card-title {
    font-size: 20px;
    font-weight: 700;
    color: var(--sb-dark-green);
    margin-bottom: 24px;
    border-bottom: 2px solid var(--sb-accent-green);
    padding-bottom: 10px;
}

/* エリア */
.search-form {
    display: flex;
    flex-direction: column;
    gap: 24px;
}

.form-group {
    display: flex;
    flex-direction: column;
    gap: 8px;
}

.form-group label {
    font-size: 14px;
    font-weight: 700;
    color: var(--sb-dark-green);
}

.optional-badge {
    background: #999;
    color: white;
    font-size: 11px;
    padding: 2px 6px;
    border-radius: 4px;
    margin-left: 6px;
    vertical-align: middle;
}

/* 範囲指定 */
.range-group {
    display: flex;
    align-items: center;
    gap: 12px;
}

.range-group .form-group {
    flex: 1;
}

.range-separator {
    color: var(--text-muted);
    font-weight: bold;
    margin-top: 24px;
}

/* 入力欄 */
.form-group input {
    width: 100%;
    padding: 12px 16px;
    border: 1px solid var(--border-color);
    border-radius: 8px;
    font-size: 15px;
    font-family: inherit;
    background: #fafafa;
    transition: 0.2s;
}

.form-group input:focus {
    border-color: var(--sb-light-green);
    outline: none;
    background: white;
    box-shadow: 0 0 0 3px rgba(0,112,74,0.1);
}

/* ボタン */
.btn-container {
    display: flex;
    gap: 16px;
    margin-top: 16px;
}

.btn {
    flex: 1;
    padding: 14px;
    border-radius: 25px;
    font-size: 15px;
    font-weight: 700;
    cursor: pointer;
    border: none;
    transition: 0.2s;
    text-decoration: none;
    text-align: center;
    font-family: inherit;
}

.btn-primary {
    background: var(--sb-light-green);
    color: white;
    box-shadow: 0 4px 12px rgba(0,112,74,0.2);
}

.btn-primary:hover {
    background: var(--sb-dark-green);
    transform: translateY(-1px);
}

.btn-secondary {
    background: white;
    color: var(--text-muted);
    border: 1px solid var(--border-color);
}

.btn-secondary:hover {
    background: #f5f5f5;
    color: var(--text-dark);
}
.error-message {

    background: #f8d7da;

    color: #842029;

    border: 1px solid #f5c2c7;

    padding: 12px 20px;

    border-radius: 8px;

    margin-bottom: 20px;

    font-weight: bold;

}
</style>
</head>

<body>

<div class="header">
    <div class="header-title">🔍 売上データの検索</div>
    <a href="${pageContext.request.contextPath}/dashboard" class="back-home">
        ← メインメニューへ戻る
    </a>
</div>

<div class="container">

	<c:if test="${not empty errorMessage}">
	
	    <div class="error-message">
	        ${errorMessage}
	    </div>
	
	</c:if>
    <div class="search-card">

        <div class="search-card-title">
            検索条件の指定
            <c:if test="${not empty errorMessage}">
                <div class="error-summary" style="display:block;">
                    ${errorMessage}
                </div>
            </c:if>
        </div>

        <form action="SalesListServlet" method="get" class="search-form">

            <!-- 期間 -->
            <div class="range-group">

                <div class="form-group">
                    <label for="startDate">
                        期間From
                        <span class="optional-badge">任意</span>
                    </label>
                    <input type="date"
                         id="startDate" 
                         name="startDate"
                         value="${startDate}">
                </div>

                <div class="range-separator">〜</div>

                <div class="form-group">
                    <label for="endDate">
                        期間To
                        <span class="optional-badge">任意</span>
                    </label>
                    <input type="date" 
                            id="endDate"
                            name="endDate"
                            value="${endDate}">
                </div>

            </div>

            <!-- スタッフ名 -->
            <div class="form-group">
                <label for="staffName">
                    スタッフ名
                    <span class="optional-badge">任意</span>
                </label>
                <input type="text"
                       id="staffName"
                       name="staffName"
                       value="${param.staffName}"
                       placeholder="スタッフ名を入力（部分一致）">
            </div>

            <!-- 金額 -->
            <div class="range-group">

                <div class="form-group">
                    <label for="minAmount">
                        金額From
                        <span class="optional-badge">任意</span>
                    </label>
                    <input type="number"
                           id="minAmount"
                           name="minAmount"
                           min="0"
                           value="${minAmount}"
                           placeholder="¥ 最低金額">
                </div>

                <div class="range-separator">〜</div>

                <div class="form-group">
                    <label for="maxAmount">
                        金額To
                        <span class="optional-badge">任意</span>
                    </label>
                    <input type="number"
                           id="maxAmount"
                           name="maxAmount"
                           min="0"
                           value="${maxAmount}"
                           placeholder="¥ 最高金額">
                </div>

            </div>

            <!-- ボタン -->
            <div class="btn-container">
                <button type="reset" class="btn btn-secondary">
                    条件をクリア
                </button>

                <button type="submit" class="btn btn-primary">
                    この条件で検索する
                </button>
            </div>

        </form>

    </div>

</div>

</body>
</html>