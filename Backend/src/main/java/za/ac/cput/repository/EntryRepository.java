package za.ac.cput.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import za.ac.cput.entity.Entry;

@Repository
public interface EntryRepository extends JpaRepository<Entry, Long> {
}
