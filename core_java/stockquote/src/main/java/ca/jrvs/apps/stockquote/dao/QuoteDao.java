package ca.jrvs.apps.stockquote.dao;

import ca.jrvs.apps.stockquote.Quote;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

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
    Quote quote = new Quote();
    try(PreparedStatement statement = this.connection.prepareStatement(GET_ONE);){
      statement.setString(1, s);
      ResultSet rs = statement.executeQuery();
      rs.next();
      // not sure if i need to loop since it should just be one result
      if(rs.next()){
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
        return Optional.of(quote);
      }
    }catch (SQLException e){
      e.printStackTrace();
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
        quotes.add(quote);
      }
    }catch(SQLException e){
      e.printStackTrace();
      throw new RuntimeException(e);
    }
    return quotes;
  }

  @Override
  public Quote save(Quote dto) {
    // TODO: adjust all these statement sets into one function
    if(this.findById(dto.getTicker()).isPresent()) {
      // assumption that update statement cannot update ticker symbol
      try(PreparedStatement statement = this.connection.prepareStatement(UPDATE);){
        statement.setDouble(1, dto.getOpen());
        statement.setDouble(2, dto.getHigh());
        statement.setDouble(3, dto.getLow());
        statement.setDouble(4, dto.getPrice());
        statement.setDouble(5, dto.getVolume());
        statement.setDate(6, dto.getLatestTradingDay());
        statement.setDouble(7, dto.getPreviousClose());
        statement.setDouble(8, dto.getChange());
        statement.setString(9, dto.getChangePercent());
        statement.setTimestamp(10, dto.getTimestamp());
        statement.setString(11, dto.getTicker());
        statement.execute();
        return this.findById(dto.getTicker()).get();
      }catch(SQLException e){
        e.printStackTrace();
        throw new RuntimeException(e);
      }
    } else {
      try (PreparedStatement statement = this.connection.prepareStatement(INSERT);) {
        statement.setString(1, dto.getTicker());
        statement.setDouble(2, dto.getOpen());
        statement.setDouble(3, dto.getHigh());
        statement.setDouble(4, dto.getLow());
        statement.setDouble(5, dto.getPrice());
        statement.setDouble(6, dto.getVolume());
        statement.setDate(7, dto.getLatestTradingDay());
        statement.setDouble(8, dto.getPreviousClose());
        statement.setDouble(9, dto.getChange());
        statement.setString(10, dto.getChangePercent());
        statement.setTimestamp(11, dto.getTimestamp());
        statement.execute();
        return this.findById(dto.getTicker()).get();
      } catch (SQLException e) {
        e.printStackTrace();
        throw new RuntimeException(e);
      }
    }
  }

  @Override
  public void deleteById(String s) throws IllegalArgumentException {
    try(PreparedStatement statement = this.connection.prepareStatement(DELETE);){
      statement.setString(1, s);
      statement.execute();
    }catch (SQLException e){
      e.printStackTrace();
      throw new RuntimeException(e);
    }
  }

  @Override
  public void deleteAll() {
    try(PreparedStatement statement = this.connection.prepareStatement(DELETE_ALL);){
      statement.execute();
    }catch (SQLException e){
      e.printStackTrace();
      throw new RuntimeException(e);
    }
  }
}
