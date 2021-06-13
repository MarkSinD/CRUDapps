package com.kubstu.programm.service;

import com.kubstu.programm.entity.Tour;
import com.kubstu.programm.exception.TourNotFoundException;
import com.kubstu.programm.repository.TourRepository;
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
public class TourService {
    public static final int TOUR_PER_PAGE = 100;

    @Autowired
    private TourRepository tourRepository;

    public Page<Tour> listByPage(int pageNum, String sortField, String sortDir, String keyword) {
        Sort sort = Sort.by(sortField);
        sort = sortDir.equals("asc") ? sort.ascending() : sort.descending();

        Pageable pageable = PageRequest.of(pageNum - 1, TOUR_PER_PAGE, sort);

        if(keyword != null){
            return tourRepository.findAll(keyword, pageable);
        }
        return tourRepository.findAll(pageable);
    }

    public Tour save(Tour tour) {
        return tourRepository.save(tour);
    }


    public boolean isNameUnique(Integer id, String name){
        Tour tourByEmail = tourRepository.getTourByEmail(id);

        if( id != null && tourByEmail == null) {
            return true;
        }
        else if( id != null && tourByEmail != null) {
            if(!tourByEmail.getId().equals(id))
                return false;
            else
                return true;
        }
        else if( id == null && tourByEmail != null) {
            return false;
        }
        else if(id == null && tourByEmail == null) {
            return true;
        }
        return false;
    }

    public Tour getTourById(Integer id) throws TourNotFoundException {
        try {
            return tourRepository.findById(id).get();
        } catch (NoSuchElementException exception){
            throw new TourNotFoundException("Could not find any tour with ID");
        }
    }

    public List<Tour> getTours(){
        return (List<Tour>) tourRepository.findAll();
    }

    public void delete(Integer id) throws TourNotFoundException{
        Long countById = tourRepository.countById(id);
        if(countById == null || countById == 0){
            throw new TourNotFoundException("Could not find any tour with ID" + id);
        }
        tourRepository.deleteById(id);
    }

}

