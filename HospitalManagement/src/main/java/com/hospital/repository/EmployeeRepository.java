package com.hospital.repository;
import org.springframework.data.repository.*;
import com.hospital.model.*;

public interface EmployeeRepository extends CrudRepository<Employee, Long>{

}
