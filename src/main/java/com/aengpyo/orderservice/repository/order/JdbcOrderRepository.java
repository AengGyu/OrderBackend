package com.aengpyo.orderservice.repository.order;

import com.aengpyo.orderservice.domain.Grade;
import com.aengpyo.orderservice.domain.order.Order;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.sql.PreparedStatement;
import java.util.List;
import java.util.Optional;

@Repository
public class JdbcOrderRepository implements OrderRepository{

    private final JdbcTemplate jdbcTemplate;

    public JdbcOrderRepository(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    @Override
    public Order save(Order order) {
        String sql = "insert into orders (member_id, member_name, grade, product_id, product_name, price, quantity, total_price)" +
                "values(?,?,?,?,?,?,?,?)";

        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(con ->{
            PreparedStatement ps = con.prepareStatement(sql, new String[]{"id"});
            ps.setLong(1,order.getMemberId());
            ps.setString(2, order.getMemberName());
            ps.setString(3, order.getGrade().name());
            ps.setLong(4,order.getProductId());
            ps.setString(5, order.getProductName());
            ps.setInt(6, order.getPrice());
            ps.setInt(7, order.getQuantity());
            ps.setInt(8,order.getTotalPrice());
            return ps;
        },keyHolder);

        order.setId(keyHolder.getKey().longValue());
        return order;
    }

    @Override
    public Optional<Order> findById(Long id) {
        String sql = "select * from orders where id=?";

        try {
            Order order = jdbcTemplate.queryForObject(sql, orderRowMapper(), id);
            return Optional.of(order);
        } catch (EmptyResultDataAccessException e) {
            return Optional.empty();
        }
    }

    @Override
    public List<Order> findByMemberId(Long memberId) {
        String sql = "select * from orders where member_id=?";

        return jdbcTemplate.query(sql, orderRowMapper(), memberId);
    }

    @Override
    public List<Order> findByProductId(Long productId) {
        String sql = "select * from orders where product_id=?";

        return jdbcTemplate.query(sql, orderRowMapper(), productId);
    }

    @Override
    public List<Order> findAll() {
        String sql = "select * from orders";

        return jdbcTemplate.query(sql, orderRowMapper());
    }

    private RowMapper<Order> orderRowMapper() {
        return ((rs, rowNum) -> {
            Order order = new Order();
            order.setId(rs.getLong("id"));
            order.setMemberId(rs.getLong("member_id"));
            order.setMemberName(rs.getString("member_name"));
            order.setGrade(Grade.valueOf(rs.getString("grade")));
            order.setProductId(rs.getLong("product_id"));
            order.setProductName(rs.getString("product_name"));
            order.setPrice(rs.getInt("price"));
            order.setQuantity(rs.getInt("quantity"));
            order.setTotalPrice(rs.getInt("total_price"));
            return order;
        });
    }
}
