package ca.jrvs.apps.stockquote.dao;

import ca.jrvs.apps.stockquote.Quote;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static ca.jrvs.apps.stockquote.Main.logger;

public class QuoteDao implements CrudDao<Quote, String>  {

  private Connection connection;
  private static final String INSERT = "INSERT INTO quote (symbol, open," +
      "high, low, price, volume, latest_trading_day, previous_close, change, change_percent," +
      "timestamp) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
  private static final String UPDATE = "UPDATE quote SET open=?, " +
      "high = ?, low = ?, price = ?, volume = ?, latest_trading_day = ?, previous_close = ?," +
      "change = ?, change_percent = ?, timestamp = ? WHERE symbol = ?";
  private static final String DELETE = "DELETE FROM quote WHERE symbol = ?";
  private static final String DELETE_ALL = "DELETE FROM quote";
  private static final String GET_ONE = "SELECT * FROM quote WHERE symbol=?";
  private static final String SELECT_ALL  = "SELECT * FROM quote";

  public QuoteDao(Connection connection) {
    this.connection = connection;
  }

  @Override
  public Optional<Quote> findById(String s) throws IllegalArgumentException {
    try(PreparedStatement statement = this.connection.prepareStatement(GET_ONE);){
      statement.setString(1, s);
      ResultSet rs = statement.executeQuery();
      if(rs.next()){
        Quote quote = mapToQuote(rs);
        return Optional.of(quote);
      }
    }catch (SQLException e){
      logger.error(e.getMessage());
      throw new RuntimeException(e);
    }
    return Optional.empty();
  }

  @Override
  public List<Quote> findAll() {
    List<Quote> quotes = new ArrayList<>();
    try(PreparedStatement statement = this.connection.prepareStatement(SELECT_ALL);){
      ResultSet rs = statement.executeQuery();
      while(rs.next()){
        Quote quote = mapToQuote(rs);
        quotes.add(quote);
      }
    }catch(SQLException e){
      logger.error(e.getMessage());
      throw new RuntimeException(e);
    }
    return quotes;
  }

  @Override
  public Quote save(Quote dto) {
    Optional<Quote> existingData = this.findById(dto.getTicker());

    String sql = existingData.isPresent() ? UPDATE : INSERT;
    // assumption that update statement cannot update ticker symbol
    try (PreparedStatement statement = this.connection.prepareStatement(sql)) {
          int ind;
          if(existingData.isPresent()){
            ind = 0;
            statement.setString(11, dto.getTicker());
          } else {
            ind = 1;
            statement.setString(1, dto.getTicker());
          }
          statement.setDouble(ind+1, dto.getOpen());
          statement.setDouble(ind+2, dto.getHigh());
          statement.setDouble(ind+3, dto.getLow());
          statement.setDouble(ind+4, dto.getPrice());
          statement.setDouble(ind+5, dto.getVolume());
          statement.setDate(ind+6, dto.getLatestTradingDay());
          statement.setDouble(ind+7, dto.getPreviousClose());
          statement.setDouble(ind+8, dto.getChange());
          statement.setString(ind+9, dto.getChangePercent());
          statement.setTimestamp(ind+10, dto.getTimestamp());
          statement.execute();
          return this.findById(dto.getTicker()).get();
        } catch (SQLException e) {
          logger.error(e.getMessage());
          throw new RuntimeException(e);
        }
  }

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

  private Quote mapToQuote(ResultSet rs) throws SQLException {
    Quote quote = new Quote();
    quote.setTicker(rs.getString("symbol"));
    quote.setOpen(rs.getDouble("open"));
    quote.setHigh(rs.getDouble("high"));
    quote.setLow(rs.getDouble("low"));
    quote.setPrice(rs.getDouble("price"));
    quote.setVolume(rs.getInt("volume"));
    quote.setLatestTradingDay(rs.getDate("latest_trading_day"));
    quote.setPreviousClose(rs.getDouble("previous_close"));
    quote.setChange(rs.getDouble("change"));
    quote.setChangePercent(rs.getString("change_percent"));
    quote.setTimestamp(rs.getTimestamp("timestamp"));
    return quote;
  }
}
