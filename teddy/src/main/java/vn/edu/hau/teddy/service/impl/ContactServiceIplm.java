package vn.edu.hau.teddy.service.impl;

import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import vn.edu.hau.teddy.model.Contact;
import vn.edu.hau.teddy.repository.ContactRepository;
import vn.edu.hau.teddy.service.ContactService;

@Service
public class ContactServiceIplm implements ContactService {

    private final ContactRepository contactRepository;

    public ContactServiceIplm(ContactRepository contactRepository) {
        this.contactRepository = contactRepository;
    }

    @Override
    public Contact save(Contact entity) {
        return contactRepository.save(entity);
    }
    @Override
    public List<Contact> searchContact(String keyword){
        return contactRepository.searchContact(keyword);
    }
    @Override
    public Page<Contact> gettAllContact(int page, int pageSize){
//        Pageable pageable = PageRequest.of(page, pageSize);
        Sort sort = Sort.by(Sort.Order.desc("createdDate"));
        Pageable pageable = PageRequest.of(page, pageSize, sort);
        return contactRepository.findAll(pageable);
    }

    @Override
    public Page<Contact> searchPageContact(String keyword, int page, int pageSize){
        List list = this.searchContact(keyword);
        Pageable pageable = PageRequest.of(page, pageSize);
        Integer start = (int)pageable.getOffset();
        Integer end = Math.toIntExact((pageable.getOffset() + pageable.getPageSize()) > list.size() ? list.size() : pageable.getOffset() + pageable.getPageSize());

        list = list.subList(start, end);
        return new PageImpl<Contact>(list, pageable, this.searchContact(keyword).size());
    }


    @Override
    public List<Contact> saveAll(List<Contact> entities) {
        return (List<Contact>)contactRepository.saveAll(entities);
    }

    @Override
    public Optional<Contact> findById(Integer integer) {
        return contactRepository.findById(integer);
    }

    @Override
    public boolean existsById(Integer integer) {
        return contactRepository.existsById(integer);
    }

    @Override
    public Iterable<Contact> findAll() {
        return contactRepository.findAll();
    }


    @Override
    public void deleteById(Integer integer) {
        contactRepository.deleteById(integer);
    }

}
