package com.Hootsybit;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.beans.factory.annotation.Autowired;
import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
public class DatabaseConnectionTest {

    @Autowired
    private DataSource dataSource;

    @Test
    public void testDatabaseConnection() throws Exception {
        try (Connection connection = dataSource.getConnection()) {
            assertNotNull(connection);
            System.out.println("Database connected successfully!");
        }
    }

    @Test
    public void testTablesExist() throws Exception {
        try (Connection connection = dataSource.getConnection();
             Statement statement = connection.createStatement()) {
            
            // 检查user_info表是否存在
            ResultSet rs1 = statement.executeQuery(
                "SELECT EXISTS (SELECT * FROM information_schema.tables WHERE table_name = 'user_info')");
            assertTrue(rs1.next() && rs1.getBoolean(1), "user_info table should exist");
            
            // 检查health_record表是否存在
            ResultSet rs2 = statement.executeQuery(
                "SELECT EXISTS (SELECT * FROM information_schema.tables WHERE table_name = 'health_record')");
            assertTrue(rs2.next() && rs2.getBoolean(1), "health_record table should exist");
            
            // 检查file_info表是否存在
            ResultSet rs3 = statement.executeQuery(
                "SELECT EXISTS (SELECT * FROM information_schema.tables WHERE table_name = 'file_info')");
            assertTrue(rs3.next() && rs3.getBoolean(1), "file_info table should exist");
            
            System.out.println("All tables exist!");
        }
    }
}