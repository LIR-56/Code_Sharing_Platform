package platform;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

public interface CodeRepository extends CrudRepository<Code, Long> {

    List<Code> findTop10ByOrderByDateDesc();
}
