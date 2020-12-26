package platform;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

public interface CodeRepository extends CrudRepository<Code, Long> {

    @Query(
            value = "SELECT *\n" +
                    "FROM CODE \n" +
                    "WHERE TIME = 0\n" +
                    "AND VIEWS = 0\n" +
                    "ORDER BY DATE DESC\n" +
                    "LIMIT 10;",

            nativeQuery = true
    )
    List<Code> findTop10ByOrderByDateDesc();


    @Query(value = "SELECT ID, CODE, DATE, GREATEST((TIME - DATEDIFF(SECOND, DATE, now())), 0) as TIME, VIEWS\n" +
            "FROM CODE\n" +
            "WHERE ID = ?1\n" +
            "AND (CODE.TIME < 1 OR (TIME - DATEDIFF(SECOND, DATE, now())) > 0)\n" +
            "AND VIEWS > -1;",
            nativeQuery = true)
    Optional<Code> findById(UUID id);
}
