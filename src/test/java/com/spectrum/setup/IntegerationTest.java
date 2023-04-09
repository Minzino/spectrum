package com.spectrum.setup;

import com.spectrum.repository.comment.CommentRepository;
import com.spectrum.repository.commentlike.CommentLikeRepository;
import com.spectrum.repository.post.PostRepository;
import com.spectrum.repository.postlike.PostLikeRepository;
import com.spectrum.repository.user.UserRepository;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import org.hibernate.Session;
import org.junit.jupiter.api.AfterEach;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

@SpringBootTest
@ActiveProfiles("test")
public class IntegerationTest implements InitializingBean {

    @Autowired
    protected CommentRepository commentRepository;
    @Autowired
    protected PostRepository postRepository;
    @Autowired
    protected UserRepository userRepository;
    @Autowired
    protected CommentLikeRepository commentLikeRepository;
    @Autowired
    protected PostLikeRepository postLikeRepository;

    private List<String> tableNames = new ArrayList<>();
    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public void afterPropertiesSet() {
        entityManager.unwrap(Session.class).doWork(this::extractTableNames);
    }

    private void cleanUpDatabase(Connection connection) throws SQLException {
        try (Statement statement = connection.createStatement()) {
            statement.executeUpdate("SET FOREIGN_KEY_CHECKS = " + "0");
            for (String tableName : tableNames) {
                statement.executeUpdate("TRUNCATE TABLE " + tableName);
            }
            statement.executeUpdate("SET FOREIGN_KEY_CHECKS = " + "1");
        }
    }

    public void clear() {
        entityManager.unwrap(Session.class).doWork(this::cleanUpDatabase);
    }

    private void extractTableNames(Connection connection) throws SQLException {
        List<String> tableNames = new ArrayList<>();
        ResultSet tables = connection.getMetaData()
            .getTables(connection.getCatalog(), null, null, new String[]{"TABLE"});

        try (tables) {
            while (tables.next()) {
                tableNames.add(tables.getString("table_name"));
            }
            this.tableNames = tableNames;
        }
    }

    @AfterEach
    void teardown() {
        clear();
    }
}
