package io.cloudadc.data.local.clickhouse;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.clickhouse.jdbc.ClickHouseDataSource;

import io.cloudadc.data.Entity;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping(path="/local/clickhouse", consumes = { "application/json"}, produces = { "application/json"})
@Tag(name = "ClickHouse", description = "Query Local ClickHouse")
public class ClickHouseController {
	
	Logger log = LoggerFactory.getLogger(ClickHouseController.class);
	
	
	@RequestMapping(path = {"/"}, method = {RequestMethod.POST})
	@Operation(summary = "ClickHouse Query/Metadata", description = "Extract ClickHouse Metadata, execute ClickHouse Query")
	public Map<String, String> clickhouse(@io.swagger.v3.oas.annotations.parameters.RequestBody @RequestBody Entity entity) throws SQLException {
		
		log.info("ClickHouse Query/Metadata: " + entity);

		Map<String, String> map = new HashMap<>();
		
		Properties properties = new Properties();
		
		if(entity.getProperties() != null) {
			properties.putAll(entity.getProperties());
		}
		
		ClickHouseDataSource dataSource = new ClickHouseDataSource(entity.getUrl(), properties);
		
		String SQL_DROP_DB = "DROP DATABASE IF EXISTS helloworld";
		String SQL_CREATE_DB = "CREATE DATABASE IF NOT EXISTS helloworld";
		String SQL_CREATE_TABLE = "CREATE TABLE IF NOT EXISTS helloworld.my_first_table(user_id UInt32, message String, timestamp DateTime, metric Float32) ENGINE = MergeTree() PRIMARY KEY (user_id, timestamp)";
		String SQL_INSERT = "INSERT INTO helloworld.my_first_table (user_id, message, timestamp, metric) VALUES (1001, 'Hello World', now(), 2.7185)";
		String SQL_QUERY = "SELECT * FROM helloworld.my_first_table";
		
		Connection connection = dataSource.getConnection(entity.getUsername(), entity.getPassword());
		DatabaseMetaData databaseMetaData = connection.getMetaData();
		map.put("url", databaseMetaData.getURL());
		map.put("username", databaseMetaData.getUserName());
		map.put("name", databaseMetaData.getDatabaseProductName());
		map.put("version", databaseMetaData.getDriverVersion());
		map.put("driver", databaseMetaData.getDriverName());
		map.put("driverversion", databaseMetaData.getDriverVersion());
		
		StringBuffer sb = new StringBuffer();
		Statement stmt = connection.createStatement();
		stmt.execute(SQL_DROP_DB);
		sb.append("drop db[").append(SQL_DROP_DB).append("]").append(", ");
		stmt.execute(SQL_CREATE_DB);
		sb.append("create db[").append(SQL_CREATE_DB).append("]").append(", ");
		stmt.execute(SQL_CREATE_TABLE);
		sb.append("create table[").append(SQL_CREATE_TABLE).append("]").append(", ");
		stmt.execute(SQL_INSERT);
		sb.append("indert table[").append(SQL_INSERT).append("]").append(", ");
		ResultSet resultSet = stmt.executeQuery(SQL_QUERY);
		ResultSetMetaData resultSetMetaData = resultSet.getMetaData();
		int columns = resultSetMetaData.getColumnCount();
		sb.append("query table[").append(SQL_QUERY).append("]").append(", ");
		sb.append("query results: ");
		while (resultSet.next()) {
			for (int c = 1; c <= columns; c++) {
				sb.append(resultSet.getObject(c) + (c < columns ? ", " : " "));
			}
		}
		
		map.put("implementation", sb.toString());
		
		resultSet.close();
		stmt.close();
		connection.close();
		
		return map;
	}

}
