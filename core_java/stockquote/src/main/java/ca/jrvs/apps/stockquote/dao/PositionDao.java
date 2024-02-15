package ca.jrvs.apps.stockquote.dao;

import ca.jrvs.apps.stockquote.Position;
import java.sql.Connection;
import java.util.List;
import java.util.Optional;

public class PositionDao implements CrudDao<Position, String> {

  private Connection connection;

  public PositionDao(Connection connection) {
    this.connection = connection;
  }

  @Override
  public Position save(Position entity) throws IllegalArgumentException {
    return null;
  }

  @Override
  public Optional<Position> findById(String s) throws IllegalArgumentException {
    return Optional.empty();
  }

  @Override
  public List<Position> findAll() {
    return null;
  }

  @Override
  public void deleteById(String s) throws IllegalArgumentException {

  }

  @Override
  public void deleteAll() {

  }
}
