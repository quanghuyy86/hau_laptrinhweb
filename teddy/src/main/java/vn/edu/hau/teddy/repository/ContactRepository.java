package vn.edu.hau.teddy.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import vn.edu.hau.teddy.model.Contact;

import java.util.List;

@Repository
public interface ContactRepository extends CrudRepository<Contact, Integer> {
    @Query("SELECT c FROM Contact c WHERE c.name like %?1%")
    List<Contact> searchContact(String keyword);

    Page<Contact> findAll(Pageable pageable);
}
