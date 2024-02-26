package ca.jrvs.apps.stockquote.dao;

import ca.jrvs.apps.stockquote.Position;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static ca.jrvs.apps.stockquote.Main.logger;

public class PositionDao implements CrudDao<Position, String> {

  private Connection connection;

  private static final String INSERT = "INSERT INTO position (symbol, number_of_shares, " +
      "value_paid) VALUES (?, ?, ?)";
  private static final String UPDATE = "UPDATE position SET number_of_shares = ?, value_paid = ? " +
      "WHERE symbol = ?";
  private static final String DELETE = "DELETE FROM position WHERE symbol = ?";
  private static final String DELETE_ALL = "DELETE FROM position";
  private static final String GET_ONE = "SELECT * FROM position WHERE symbol=?";
  private static final String SELECT_ALL  = "SELECT * FROM position";

  public PositionDao(Connection connection) {
    this.connection = connection;
  }

  @Override
  public Position save(Position dto) throws IllegalArgumentException {
    Optional<Position> existingData = this.findById(dto.getTicker());

    String sql = existingData.isPresent() ? UPDATE : INSERT;
    // assumption that update statement cannot update ticker symbol
    try (PreparedStatement statement = this.connection.prepareStatement(sql)) {
      logger.info("Saving Position {}", dto.getTicker());
      int ind;
      if(existingData.isPresent()){
        ind = 0;
        statement.setString(3, dto.getTicker());
      } else {
        ind = 1;
        statement.setString(1, dto.getTicker());
      }
      statement.setInt(ind+1, dto.getNumOfShares());
      statement.setDouble(ind+2, dto.getValuePaid());
      statement.execute();
      return this.findById(dto.getTicker()).get();
    } catch (SQLException e) {
      logger.error(e.getMessage());
      throw new RuntimeException(e);
    }
  }

  @Override
  public Optional<Position> findById(String s) throws IllegalArgumentException {
    try(PreparedStatement statement = this.connection.prepareStatement(GET_ONE);){
      statement.setString(1, s);
      ResultSet rs = statement.executeQuery();
      if(rs.next()){
        Position position = mapToPosition(rs);
        return Optional.of(position);
      }
    }catch (SQLException e){
      logger.error(e.getMessage());
      throw new RuntimeException(e);
    }
    return Optional.empty();
  }

  @Override
  public List<Position> findAll() {
    List<Position> positions = new ArrayList<>();
    try(PreparedStatement statement = this.connection.prepareStatement(SELECT_ALL);){
      ResultSet rs = statement.executeQuery();
      while(rs.next()){
        Position position = mapToPosition(rs);
        positions.add(position);
      }
    }catch(SQLException e){
      logger.error(e.getMessage());
      throw new RuntimeException(e);
    }
    return positions;
  }


  // TODO: could probably refactor code since both DAOs use this exact code
  @Override
  public void deleteById(String s) throws IllegalArgumentException {
    try(PreparedStatement statement = this.connection.prepareStatement(DELETE);){
      statement.setString(1, s);
      statement.execute();
    }catch (SQLException e){
      logger.error(e.getMessage());
      throw new RuntimeException(e);
    }
  }

  @Override
  public void deleteAll() {
    try(PreparedStatement statement = this.connection.prepareStatement(DELETE_ALL);){
      statement.execute();
    }catch (SQLException e){
      logger.error(e.getMessage());
      throw new RuntimeException(e);
    }
  }

  private Position mapToPosition(ResultSet rs) throws SQLException {
    Position position = new Position();
    position.setTicker(rs.getString("symbol"));
    position.setNumOfShares(rs.getInt("number_of_shares"));
    position.setValuePaid(rs.getDouble("value_paid"));
    return position;
  }
}
