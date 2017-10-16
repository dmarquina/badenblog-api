package com.badenblog.persistence.util;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

@SuppressWarnings("rawtypes")
public interface CustomRowMapper<X> extends RowMapper {

  @Override
  X mapRow(ResultSet rs, int rowNum) throws SQLException;
}
