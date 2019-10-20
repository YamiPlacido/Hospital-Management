package sent;

import com.hospital.model.Employee;
import com.hospital.model.ExaminationType;
import com.hospital.model.Symptom;
import com.hospital.repo.EmployeeRepositoryCustom;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Set;

public interface EmployeeRepository extends JpaRepository<Employee, Integer>, EmployeeRepositoryCustom {
    @Query("SELECT s FROM Symptom s")
    public Set<Symptom> findAllSymptom();
    @Query("SELECT et FROM ExaminationType et")
    public Set<ExaminationType> findAllExamination();
    @Query("SELECT e FROM Employee e WHERE e.speciality.specialityId = :id")
    public List<Employee> findEmployeesBySpeciality(@Param("id") int speciality_id);
    @Query("SELECT e FROM ExaminationType et LEFT JOIN Employee e ON et.speciality.specialityId = e.speciality.specialityId" +
            " WHERE et.id = :id")
    public List<Employee> findExaminatorsByExaminationType(@Param("id") int examination_type_id);

}
