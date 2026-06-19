<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib prefix="c"
    uri="http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE html>
<html lang="ja">
<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<title>売上一覧 | Cafe売上管理システム</title>
<link rel="preconnect" href="https://fonts.googleapis.com">
<link rel="preconnect" href="https://fonts.gstatic.com" crossorigin>
<link href="https://fonts.googleapis.com/css2?family=Noto+Sans+JP:wght@400;500;700&display=swap" rel="stylesheet">
 
<style>
	:root {
    	/* 検索画面・商品一覧と完全に同一のカラーパレット */
    	--sb-dark-green: #1e3932;
    	--sb-light-green: #00704a;
    	--sb-accent-green: #d4e9e2;
    	--sb-warm-white: #f2f0eb;
    	--text-dark: #212121;
    	--text-muted: #6b6b6b;
    	--warning-orange: #f0ad4e;
    	--danger-red: #d9534f;
	}

	body {
    	margin: 0;
    	font-family: 'Noto Sans JP', "Yu Gothic", sans-serif;
    	background: var(--sb-warm-white);
    	color: var(--text-dark);
	}
 
	/* ヘッダーデザインの統一 */
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
	    letter-spacing: 0.05em;
	}
	
	.top-right {
	    display: flex;
	    align-items: center;
	    gap: 10px;
	}
	
	/* ナビゲーションボタン：カプセル型のモダンな形状に統一 */
	.nav-btn {
	    color: white;
	    text-decoration: none;
	    font-size: 14px;
	    background: rgba(255, 255, 255, 0.15);
	    padding: 8px 16px;
	    border-radius: 20px;
	    transition: 0.2s;
	    font-weight: 500;
	}
	
	.nav-btn:hover {
	    background: var(--sb-light-green);
	}
	 
	.container {
	    width: 95%;
	    max-width: 1300px;
	    margin: 40px auto;
	}
	 
	/* テーブル：角丸（12px）と、商品一覧と同じ薄いシャドウを適用 */
	table {
	    width: 100%;
	    border-collapse: collapse;
	    background: white;
	    border-radius: 12px;
	    overflow: hidden;
	    box-shadow: 0 6px 16px rgba(0,0,0,0.05);
	}
	 
	th, td {
	    padding: 16px 14px;
	    border-bottom: 1px solid #eee;
	    text-align: center;
	    font-size: 14px;
	}
	 
	/* ヘッダーカラー：薄緑の背景に深緑テキストを適用して統一 */
	th {
	    background: #e6f2ec;
	    color: var(--sb-dark-green);
	    font-weight: 700;
	}
	
	/* 合計金額行のスタイル：統一感を高めるカラー調整 */
	.table-summary td {
	    background: #f4fbf6;
	    color: var(--sb-dark-green);
	    font-weight: bold;
	    font-size: 15px;
	    border-bottom: none;
	}
	 
	/* 操作ボタン：商品一覧と共通のカプセル型ボタンに統一 */
	.btn {
	    padding: 6px 16px;
	    border-radius: 20px;
	    text-decoration: none;
	    color: white;
	    font-size: 13px;
	    font-weight: 500;
	    display: inline-block;
	    border: none;
	    cursor: pointer;
	    transition: 0.2s;
	    font-family: inherit;
	}
	
	.btn:hover {
	    opacity: 0.85;
	    transform: translateY(-1px);
	}
	 
	.edit { background: var(--warning-orange); }
	.delete { background: var(--danger-red); }
	 
	.action {
	    display: flex;
	    justify-content: center;
	    gap: 8px;
	}
	
	/* フローティング追加ボタン：深緑カラーと滑らかなシャドウ */
	.add-btn {
	    position: fixed;
	    bottom: 30px;
	    right: 30px;
	    width: 60px;
	    height: 60px;
	    border: none;
	    border-radius: 50%;
	    background: var(--sb-light-green);
	    color: white;
	    font-size: 35px;
	    cursor: pointer;
	    box-shadow: 0 4px 12px rgba(0,0,0,0.25);
	    transition: 0.2s;
	    display: flex;
	    justify-content: center;
	    align-items: center;
	}
	
	.add-btn:hover {
	    background: var(--sb-dark-green);
	    transform: scale(1.05);
	}
	</style>
</head>
<body>


	<div class="header">
	
	    <div class="header-title">
	        💰 売上一覧
	    </div>
	
	
	    <div class="top-right">
	
	        <a href="SalesSearchServlet" class="nav-btn">
	            🔍 売上検索へ戻る
	        </a>
	
	
	        <a href="${pageContext.request.contextPath}/dashboard" class="nav-btn">
	            ← メインメニュー
	        </a>
	
	    </div>
	
	</div>
	
	
	
	
	<div class="container">
	
		<table>
		
		
		<thead>
		
		<tr>
		
		<th>売上ID</th>
		<th>売上日</th>
		<th>商品名</th>
		<th>数量</th>
		<th>販売時単価</th>
		<th>合計金額</th>
		<th>登録者</th>
		<th>メモ</th>
		<th>更新日時</th>
		<th>編集</th>
		<th>削除</th>
		
		</tr>
		
		</thead>
		
		
		
		<tbody>
		
		
		<c:set var="total" value="0"/>
		
		
		<c:forEach var="sale" items="${salesList}">

		<tr>
		
		<td>${sale.saleId}</td>
		
		
		<td>${sale.saleDate}</td>
		
		
		<td>
		    <strong>
		        ${sale.tradeName}
		    </strong>
		</td>
		
		
		<td>${sale.saleNumber}</td>
		
		
		<td>${sale.unitPrice}円</td>
		
		
		<c:set var="subtotal"
		       value="${sale.unitPrice * sale.saleNumber}" />
		
		
		<td>
		
		    <strong>
		        ${subtotal}円
		    </strong>
		
		</td>
		
		<td>${sale.staffName}</td>

		<td>${sale.note}</td>

		<td>${sale.updateAt}</td>
		


	<td>
	
		<c:if test="${loginUser.role == 'MANAGER'
          || loginUser.accountId == sale.accountId}">
		
		<div class="action">
		
		<button class="btn edit"
		onclick="location.href='SalesEditServlet?id=${sale.saleId}'">
		
		編集
		
		</button>
		
		</div>
		
		</c:if>
		
	</td>
	
	
	<td>
	
		<c:if test="${loginUser.role == 'MANAGER'
	          || loginUser.accountId == sale.accountId}">
		
		<form action="SalesDeleteServlet"
		method="post"
		onsubmit="return confirm('削除しますか？');">
		
		
		<input type="hidden"
		name="startDate"
		value="${param.startDate}">
		
		
		<input type="hidden"
		name="endDate"
		value="${param.endDate}">
		
		
		<input type="hidden"
		name="staffName"
		value="${param.staffName}">
		
		
		<input type="hidden"
		name="minAmount"
		value="${param.minAmount}">
		
		
		<input type="hidden"
		name="maxAmount"
		value="${param.maxAmount}">
		
		
		<input type="hidden"
		name="id"
		value="${sale.saleId}">
		
		
		<button class="btn delete">
		削除
		</button>
		
		
		</form>
		
		</c:if>

	</td>
		
		
		</tr>
		
		
		
		<c:set var="total"
		       value="${total + subtotal}"/>


</c:forEach>
		
		
		</tbody>
		
		
		
		
		<tfoot>
		
		
		<tr class="table-summary">
		
		
		<td colspan="6" style="text-align:right;">
		
		検索結果 合計金額：
		
		</td>
		
		
		
		<td>
		
		${total}円
		
		</td>
		
		<td colspan="4"></td>
		
		</tr>
		
		</tfoot>
		
		</table>
	
	
	</div>
	
	
	
	<c:if test="${loginUser.role == 'MANAGER'
          || loginUser.role == 'STAFF'}">

    	<form action="SalesAddServlet" method="get">
        	<button type="submit" class="add-btn">
        	    ＋
        	</button>
    	</form>

	</c:if>
	
	
</body>

</html>