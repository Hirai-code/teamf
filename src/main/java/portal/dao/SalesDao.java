package portal.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import portal.dto.SalesDto;
import portal.util.DbUtil;

public class SalesDao {

    // =========================
    // 一覧取得
    // =========================
    private static final String SELECT_ALL =
            "SELECT s.sale_id, s.sale_date, s.account_id, a.staff_name, " +
            "s.trade_name, s.unit_price, s.sale_number, s.note, s.updated_at " +
            "FROM sales s " +
            "JOIN accounts a ON s.account_id = a.account_id " +
            "WHERE s.deleted_flg = 0 " +
            "ORDER BY s.sale_date DESC";

    public List<SalesDto> findAll() {

        List<SalesDto> list = new ArrayList<>();

        try (Connection con = DbUtil.getConnection();
             PreparedStatement ps = con.prepareStatement(SELECT_ALL);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {

                SalesDto dto = new SalesDto();

                dto.setSaleId(rs.getInt("sale_id"));
                dto.setSaleDate(rs.getDate("sale_date"));
                dto.setAccountId(rs.getInt("account_id"));
                dto.setStaffName(rs.getString("staff_name"));
                dto.setTradeName(rs.getString("trade_name"));
                dto.setUnitPrice(rs.getInt("unit_price"));
                dto.setSaleNumber(rs.getInt("sale_number"));
                dto.setNote(rs.getString("note"));
                dto.setUpdateAt(rs.getTimestamp("updated_at"));

                list.add(dto);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return list;
    }

    // =========================
    // 検索（改善版）
    // =========================
    public List<SalesDto> search(
            String startDate,
            String endDate,
            String staffName,
            String minAmount,
            String maxAmount) {

        List<SalesDto> list = new ArrayList<>();

        StringBuilder sql = new StringBuilder();

        sql.append(
            "SELECT s.sale_id, s.sale_date, s.account_id, a.staff_name, " +
            "s.trade_name, s.unit_price, s.sale_number, s.note, s.updated_at " +
            "FROM sales s " +
            "JOIN accounts a ON s.account_id = a.account_id " +
            "WHERE s.deleted_flg = 0 "
        );

        List<Object> params = new ArrayList<>();

        if (startDate != null && !startDate.isEmpty()) {
            sql.append(" AND s.sale_date >= ? ");
            params.add(startDate);
        }

        if (endDate != null && !endDate.isEmpty()) {
            sql.append(" AND s.sale_date <= ? ");
            params.add(endDate);
        }

        if (staffName != null && !staffName.isEmpty()) {
            sql.append(" AND a.staff_name LIKE ? ");
            params.add("%" + staffName + "%");
        }

        if (minAmount != null && !minAmount.isEmpty()) {
            sql.append(" AND (s.unit_price * s.sale_number) >= ? ");
            params.add(Integer.parseInt(minAmount));
        }

        if (maxAmount != null && !maxAmount.isEmpty()) {
            sql.append(" AND (s.unit_price * s.sale_number) <= ? ");
            params.add(Integer.parseInt(maxAmount));
        }

        sql.append(" ORDER BY s.sale_date DESC ");

        try (Connection con = DbUtil.getConnection();
             PreparedStatement ps = con.prepareStatement(sql.toString())) {

            for (int i = 0; i < params.size(); i++) {
                Object param = params.get(i);

                if (param instanceof Integer) {
                    ps.setInt(i + 1, (Integer) param);
                } else {
                    ps.setString(i + 1, (String) param);
                }
            }

            try (ResultSet rs = ps.executeQuery()) {

                while (rs.next()) {

                    SalesDto dto = new SalesDto();

                    dto.setSaleId(rs.getInt("sale_id"));
                    dto.setSaleDate(rs.getDate("sale_date"));
                    dto.setAccountId(rs.getInt("account_id"));
                    dto.setStaffName(rs.getString("staff_name"));
                    dto.setTradeName(rs.getString("trade_name"));
                    dto.setUnitPrice(rs.getInt("unit_price"));
                    dto.setSaleNumber(rs.getInt("sale_number"));
                    dto.setNote(rs.getString("note"));
                    dto.setUpdateAt(rs.getTimestamp("updated_at"));

                    list.add(dto);
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return list;
    }

    // =========================
    // 登録
    // =========================
    public int insert(SalesDto dto) {

        String sql =
            "INSERT INTO sales (" +
            "sale_date, account_id, category_id, item_id, trade_name, " +
            "unit_price, sale_number, note, deleted_flg, update_by" +
            ") VALUES (?,?,?,?,?,?,?,?,?,?)";

        try (Connection con = DbUtil.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setDate(1, dto.getSaleDate());
            ps.setInt(2, dto.getAccountId());
            ps.setInt(3, dto.getCategoryId());
            ps.setInt(4, dto.getItemId());
            ps.setString(5, dto.getTradeName());
            ps.setInt(6, dto.getUnitPrice());
            ps.setInt(7, dto.getSaleNumber());
            ps.setString(8, dto.getNote());
            ps.setInt(9, 0);
            ps.setInt(10, dto.getUpdateBy());

            return ps.executeUpdate();

        } catch (Exception e) {
            e.printStackTrace();
        }

        return 0;
    }

    // =========================
    // 削除（論理削除）
    // =========================
    public int delete(int saleId) {

        String sql =
            "UPDATE sales SET deleted_flg = 1 WHERE sale_id = ?";

        try (Connection con = DbUtil.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, saleId);
            return ps.executeUpdate();

        } catch (Exception e) {
            e.printStackTrace();
        }

        return 0;
    }

    // =========================
    // 1件取得
    // =========================
    public SalesDto findById(int saleId) {

        SalesDto dto = null;

        String sql =
            "SELECT s.sale_id, s.sale_date, s.trade_name, s.unit_price, " +
            "s.sale_number, s.note, s.account_id, a.staff_name " +
            "FROM sales s " +
            "JOIN accounts a ON s.account_id = a.account_id " +
            "WHERE s.sale_id = ? AND s.deleted_flg = 0";

        try (Connection con = DbUtil.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, saleId);

            try (ResultSet rs = ps.executeQuery()) {

                if (rs.next()) {

                    dto = new SalesDto();
                    dto.setSaleId(rs.getInt("sale_id"));
                    dto.setSaleDate(rs.getDate("sale_date"));
                    dto.setTradeName(rs.getString("trade_name"));
                    dto.setUnitPrice(rs.getInt("unit_price"));
                    dto.setSaleNumber(rs.getInt("sale_number"));
                    dto.setNote(rs.getString("note"));
                    dto.setStaffName(rs.getString("staff_name"));
                    dto.setAccountId(rs.getInt("account_id"));
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return dto;
    }

    // =========================
    // メモ更新
    // =========================
    public int updateNote(int saleId, String note) {

        String sql =
            "UPDATE sales SET note = ?, updated_at = CURRENT_TIMESTAMP WHERE sale_id = ?";

        try (Connection con = DbUtil.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, note);
            ps.setInt(2, saleId);

            return ps.executeUpdate();

        } catch (Exception e) {
            e.printStackTrace();
        }

        return 0;
    }
}