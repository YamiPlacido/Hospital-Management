package sent;

import com.hospital.model.Position;
import org.springframework.data.jpa.repository.JpaRepository;

//import org.springframework.data.repository.CrudRepository;

public interface PositionRepository extends JpaRepository<Position, Integer> {

}
