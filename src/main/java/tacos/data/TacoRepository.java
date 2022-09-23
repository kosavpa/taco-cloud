package tacos.data;

import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.CrudRepository;

import tacos.entity.Taco;

public interface TacoRepository extends CrudRepository<Taco, Long> {

    Iterable<Taco> findAll(Pageable pageRequest);

}
