package portal.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import portal.dto.ProductDto;
import portal.util.DbUtil;

public class ProductDao {

    private static final String SELECT_ALL =
    		"SELECT " + 
    	    "i.item_id, " +
    	    "i.item_name, " +
    	    "i.category_id, " +
    	    "c.category_name, " +
    	    "i.price, " +
    	    "i.selling_flg, " +
    	    "i.update_at " +
    	"FROM items i " +
    	"INNER JOIN categories c " +
    	"ON i.category_id = c.category_id " +
    	"WHERE i.deleted_flg = 0 " +
    	"ORDER BY i.item_id ";

    /**
     * 商品一覧取得
     */
    public List<ProductDto> findAll() {

        List<ProductDto> list = new ArrayList<>();

        try (Connection con = DbUtil.getConnection();
             PreparedStatement ps = con.prepareStatement(SELECT_ALL);
             ResultSet rs = ps.executeQuery()) {

        	System.out.println("接続DB：" + con.getCatalog());
        	
            while (rs.next()) {

                ProductDto item = new ProductDto();

                item.setItemId(
                    rs.getInt("item_id"));

                item.setItemName(
                    rs.getString("item_name"));

                item.setCategoryId(
                    rs.getInt("category_id"));

                // 追加
                item.setCategoryName(
                    rs.getString("category_name"));

                item.setPrice(
                    rs.getInt("price"));

                item.setSellingFlg(
                    rs.getInt("selling_flg"));

                item.setUpdateAt(
                    rs.getTimestamp("update_at"));

                list.add(item);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        System.out.println("商品件数：" + list.size());
        return list;
    }

    /**
     * 商品1件取得
     */
    public ProductDto findById(int itemId) {

        ProductDto item = null;

        String sql =
        	    "SELECT " +
        	    "i.item_id, " +
        	    "i.item_name, " +
        	    "i.category_id, " +
        	    "c.category_name, " +
        	    "i.price, " +
        	    "i.selling_flg, " +
        	    "i.update_at " +
        	    "FROM items i " +
        	    "INNER JOIN categories c " +
        	    "ON i.category_id = c.category_id " +
        	    "WHERE i.item_id = ? " +
        	    "AND i.deleted_flg = 0";

        try (Connection con = DbUtil.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, itemId);

            try (ResultSet rs = ps.executeQuery()) {

                if (rs.next()) {

                    item = new ProductDto();

                    item.setItemId(rs.getInt("item_id"));
                    item.setItemName(rs.getString("item_name"));
                    item.setCategoryId(rs.getInt("category_id"));
                    item.setCategoryName(rs.getString("category_name"));
                    item.setPrice(rs.getInt("price"));
                    item.setSellingFlg(rs.getInt("selling_flg"));
                    item.setUpdateAt(rs.getTimestamp("update_at"));
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return item;
    }

    /**
     * 商品削除（論理削除）
     */
    public int delete(int itemId) {

        String sql =
            "UPDATE items " +
            "SET deleted_flg = 1 " +
            "WHERE item_id = ?";

        try (Connection con = DbUtil.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, itemId);

            return ps.executeUpdate();

        } catch (Exception e) {
            e.printStackTrace();
        }

        return 0;
    }
    
    /**
     * 商品1件登録
     */
    public int insert(ProductDto dto, int accountId) {

        String sql =
            "INSERT INTO items " +
            "(item_name, category_id, price, selling_flg, updated_by) " +
            "VALUES (?, ?, ?, ?, ?)";

        try (Connection con = DbUtil.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, dto.getItemName());
            ps.setInt(2, dto.getCategoryId());
            ps.setInt(3, dto.getPrice());
            ps.setInt(4, dto.getSellingFlg());
            ps.setInt(5, accountId);

            return ps.executeUpdate();

        } catch (Exception e) {
            e.printStackTrace();
        }

        return 0;
    }
    
    /**
     * 商品1件アップデート
     */
    public int update(ProductDto dto, int accountId) {

    	String sql =
    		    "UPDATE items SET "
    		    + "item_name = ?, "
    		    + "category_id = ?, "
    		    + "price = ?, "
    		    + "selling_flg = ?, "
    		    + "updated_by = ? "
    		    + "WHERE item_id = ?";

        try(Connection con = DbUtil.getConnection();
            PreparedStatement ps =
                con.prepareStatement(sql)) {

            ps.setString(1, dto.getItemName());
            ps.setInt(2, dto.getCategoryId());
            ps.setInt(3, dto.getPrice());
            ps.setInt(4, dto.getSellingFlg());
            ps.setInt(5, accountId);
            ps.setInt(6, dto.getItemId());

            return ps.executeUpdate();

        } catch(Exception e) {
            e.printStackTrace();
        }

        return 0;
    }
}