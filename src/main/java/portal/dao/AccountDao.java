package portal.dao;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import portal.dto.AccountDto;
import portal.util.DbUtil;

public class AccountDao {

    // =========================
    // ログイン
    // =========================
    private static final String LOGIN_SQL =
        "SELECT account_id, login_id, staff_name, role, password " +
        "FROM accounts " +
        "WHERE login_id = ? " +
        "AND delete_flag = 0";

    public AccountDto login(String loginId, String password) {

        try (Connection con = DbUtil.getConnection();
             PreparedStatement ps = con.prepareStatement(LOGIN_SQL)) {

            ps.setString(1, loginId);

            try (ResultSet rs = ps.executeQuery()) {

                if (!rs.next()) return null;

                String dbPassword = rs.getString("password");

                String inputPassword =
                    (dbPassword != null && dbPassword.length() == 64)
                        ? hashPassword(password)
                        : password;

                if (!dbPassword.equals(inputPassword)) {
                    return null;
                }

                AccountDto account = new AccountDto();
                account.setAccountId(rs.getInt("account_id"));
                account.setLoginId(rs.getString("login_id"));
                account.setStaffName(rs.getString("staff_name"));
                account.setRole(rs.getString("role"));

                return account;
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

    // =========================
    // 一覧取得
    // =========================
    public List<AccountDto> findAll() {

        List<AccountDto> list = new ArrayList<>();

        String sql =
            "SELECT account_id, login_id, staff_name, role, updated_at " +
            "FROM accounts " +
            "WHERE delete_flag = 0 " +
            "ORDER BY account_id";

        try (Connection con = DbUtil.getConnection();
             PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {

                AccountDto dto = new AccountDto();
                dto.setAccountId(rs.getInt("account_id"));
                dto.setLoginId(rs.getString("login_id"));
                dto.setStaffName(rs.getString("staff_name"));
                dto.setRole(rs.getString("role"));
                dto.setUpdateAt(rs.getTimestamp("updated_at"));

                list.add(dto);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return list;
    }

    // =========================
    // 1件取得（編集画面用）
    // =========================
    public AccountDto findById(int accountId) {

        String sql =
            "SELECT account_id, login_id, staff_name, role " +
            "FROM accounts " +
            "WHERE account_id = ? AND delete_flag = 0";

        try (Connection con = DbUtil.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, accountId);

            try (ResultSet rs = ps.executeQuery()) {

                if (rs.next()) {

                    AccountDto dto = new AccountDto();
                    dto.setAccountId(rs.getInt("account_id"));
                    dto.setLoginId(rs.getString("login_id"));
                    dto.setStaffName(rs.getString("staff_name"));
                    dto.setRole(rs.getString("role"));

                    return dto;
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

    // =========================
    // 更新
    // =========================
    public int update(AccountDto dto) {

        String sql =
            "UPDATE accounts " +
            "SET login_id = ?, staff_name = ?, password = ?, role = ? " +
            "WHERE account_id = ? AND delete_flag = 0";

        try (Connection con = DbUtil.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, dto.getLoginId());
            ps.setString(2, dto.getStaffName());
            ps.setString(3, hashPassword(dto.getPassword()));
            ps.setString(4, dto.getRole());
            ps.setInt(5, dto.getAccountId());

            return ps.executeUpdate();

        } catch (Exception e) {
            e.printStackTrace();
        }

        return 0;
    }
    
	 // =========================
	 // 登録
	 // =========================
    public int insert(AccountDto dto) {

        String sql =
            "INSERT INTO accounts " +
            "(login_id, staff_name, password, role, delete_flag, created_at, updated_at) " +
            "VALUES (?, ?, ?, ?, 0, NOW(), NOW())";

        try (Connection con = DbUtil.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, dto.getLoginId());
            ps.setString(2, dto.getStaffName());
            ps.setString(3, hashPassword(dto.getPassword()));
            ps.setString(4, dto.getRole());

            return ps.executeUpdate();

        } catch (Exception e) {
            e.printStackTrace();
        }

        return 0;
    }
	 
    // =========================
    // ログインID重複チェック
    // =========================
    public boolean existsLoginId(String loginId) {

	    String sql =
	        "SELECT COUNT(*) " +
	        "FROM accounts " +
	        "WHERE login_id = ? " +
	        "AND delete_flag = 0";

	    try (Connection con = DbUtil.getConnection();
	         PreparedStatement ps = con.prepareStatement(sql)) {

	        ps.setString(1, loginId);

	        try (ResultSet rs = ps.executeQuery()) {

	            if (rs.next()) {
	                return rs.getInt(1) > 0;
	            }
	        }

	    } catch (Exception e) {
	        e.printStackTrace();
	    }
	    return false;
	}
	 
	// =========================
	// スタッフ名重複チェック
	// =========================
	public boolean existsStaffName(String staffName) {

	    String sql =
	        "SELECT COUNT(*) " +
	        "FROM accounts " +
	        "WHERE staff_name = ? " +
	        "AND delete_flag = 0";

	    try (Connection con = DbUtil.getConnection();
	         PreparedStatement ps = con.prepareStatement(sql)) {

	        ps.setString(1, staffName);

	        try (ResultSet rs = ps.executeQuery()) {

	            if (rs.next()) {
	                return rs.getInt(1) > 0;
	            }
	        }

	    } catch (Exception e) {
	        e.printStackTrace();
	    }

	    return false;
	}
	 
	// =========================
	// 削除（論理削除）
	// =========================
	public int delete(int accountId) {

	    String sql =
	        "UPDATE accounts " +
	        "SET delete_flag = 1, updated_at = NOW() " +
	        "WHERE account_id = ?";

	    try (Connection con = DbUtil.getConnection();
	         PreparedStatement ps = con.prepareStatement(sql)) {

	        ps.setInt(1, accountId);

	        return ps.executeUpdate();

	    } catch (Exception e) {
	        e.printStackTrace();
	    }

	    return 0;
	}

    // =========================
    // ハッシュ化（SHA-256）
    // =========================
    private String hashPassword(String password) {

        try {
            MessageDigest md = MessageDigest.getInstance("SHA-256");

            byte[] hash =
                md.digest(password.getBytes(StandardCharsets.UTF_8));

            StringBuilder sb = new StringBuilder();

            for (byte b : hash) {
                sb.append(String.format("%02x", b));
            }

            return sb.toString();

        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
    }
}