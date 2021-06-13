package com.kubstu.programm.service;

import com.kubstu.programm.entity.Client;
import com.kubstu.programm.exception.ClientNotFoundException;
import com.kubstu.programm.repository.ClientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.NoSuchElementException;


@Service
@Transactional
public class ClientService {
    public static final int CLIENT_PER_PAGE = 100;

    @Autowired
    private ClientRepository clientRepository;

    public Page<Client> listByPage(int pageNum, String sortField, String sortDir, String keyword) {
        Sort sort = Sort.by(sortField);
        sort = sortDir.equals("asc") ? sort.ascending() : sort.descending();

        Pageable pageable = PageRequest.of(pageNum - 1, CLIENT_PER_PAGE, sort);

        if(keyword != null){
            return clientRepository.findAll(keyword, pageable);
        }
        return clientRepository.findAll(pageable);
    }

    public Client save(Client client) {
        return clientRepository.save(client);
    }


    public boolean isNameUnique(Integer id, String name){
        Client administrationByName = clientRepository.getClientByEmail(id);

        if( id != null && administrationByName == null) {
            return true;
        }
        else if( id != null && administrationByName != null) {
            if(!administrationByName.getId().equals(id))
                return false;
            else
                return true;
        }
        else if( id == null && administrationByName != null) {
            return false;
        }
        else if(id == null && administrationByName == null) {
            return true;
        }
        return false;
    }

    public Client getClientById(Integer id) throws ClientNotFoundException {
        try {
            return clientRepository.findById(id).get();
        } catch (NoSuchElementException exception){
            throw new ClientNotFoundException("Could not find any client with ID");
        }
    }

    public List<Client> getClients(){
        return (List<Client>) clientRepository.findAll();
    }

    public void delete(Integer id) throws ClientNotFoundException{
        Long countById = clientRepository.countById(id);
        if(countById == null || countById == 0){
            throw new ClientNotFoundException("Could not find any client with ID" + id);
        }
        clientRepository.deleteById(id);
    }

}

