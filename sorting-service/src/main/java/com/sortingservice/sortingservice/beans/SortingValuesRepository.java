package com.sortingservice.sortingservice.beans;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.ZoneId;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import com.sortingservice.sortingservice.controller.SortNumbersController;

@Repository
public class SortingValuesRepository {

	@Autowired
	JdbcTemplate jdbcTemplate;

	private static Logger logger = Logger.getLogger(SortingValuesRepository.class.getName());

	class SortingValuesMapper implements RowMapper<SortedValuesBean> {

		@Override
		public SortedValuesBean mapRow(ResultSet rs, int rowNum) throws SQLException {
			try {
				SortedValuesBean bean = new SortedValuesBean();
				bean.setId(rs.getInt("id"));
				bean.setOriginalstring(rs.getString("originalstring"));
				bean.setSortedstring(rs.getString("sortedstring"));
				bean.setCreateddate(rs.getDate("createddate").toInstant().atZone(ZoneId.systemDefault()));
				return bean;
			} catch (Exception e) {
				logger.log(Level.SEVERE, "Exception thrown in mapRow:", e);
				return null;
			}
		}

	}

	/**
	 * @param sortedValues
	 * @return insert count from SortedValues table.
	 */
	public int insertSortedData(SortedValuesBean sortedValues) {
		try {
			int insert = jdbcTemplate.update(
					"insert into sortedvalues(originalstring, sortedstring, createddate) values(?,?,?)",
					new Object[] { sortedValues.getOriginalstring(), sortedValues.getSortedstring(),
							sortedValues.getCreateddate() });
			return insert;
		} catch (Exception e) {
			logger.log(Level.SEVERE, "Exception thrown in insertSortedData:", e);
			return 0;
		}
	}

	/**
	 * Returns lastSortedValues
	 * @return SortedValuesBean object
	 */
	public SortedValuesBean getLastSortedValues() {
		try {
			SortedValuesBean sortedBean = jdbcTemplate.queryForObject(
					"select * from sortedvalues sval where createddate = (select max(createddate) from sortedvalues svalue where svalue.id = sval.id) ",
					new SortingValuesMapper());
			return sortedBean;
		} catch (Exception e) {
			logger.log(Level.SEVERE, "Exception thrown in getLastSortedValues:", e);
			return null;
		}
	}
}
