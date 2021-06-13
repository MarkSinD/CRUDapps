package com.kubstu.programm.repository;

import com.kubstu.programm.entity.Tour;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.annotation.Rollback;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * DECRIPTION
 *
 * @author Mark Sinakaev
 * @version 1.0
 */
@DataJpaTest(showSql = false)
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Rollback(false)
public class TourRepositoryTest {

    @Autowired
    private TourRepository tourRepository;

    @Test
    public void addOne(){
        Tour tour = new Tour();
        tour.setCountry("Turkey");
        tour.setResort("Alanya");
        tour.setTransport("aircraft");
        tour.setVisaService(true);
        tour.setAccommodation(true);
        tour.setAccommodationType("Carmen Suite Hotel 3*");
        tour.setPower(true);
        tour.setPowerType("food buffet");
        tour.setExcursions(true);
        Date departureDate = Date.valueOf("2021-08-16");
        Date arrivalDate = Date.valueOf("2021-08-26");
        tour.setDepartureDate(departureDate);
        tour.setArrivalDate(arrivalDate);
        tour.setTour("all inclusive");

        Tour savedTour = tourRepository.save(tour);
        assertThat(savedTour.getId()).isNotEqualTo(0);
    }

    @Test
    public void addList(){
        Tour tour1 = new Tour();
        tour1.setCountry("Turkey");
        tour1.setResort("Side");
        tour1.setTransport("aircraft");
        tour1.setVisaService(false);
        tour1.setAccommodation(true);
        tour1.setAccommodationType("Nergos Side 3*");
        tour1.setPower(false);
        tour1.setPowerType("none");
        tour1.setExcursions(false);
        Date departureDate = Date.valueOf("2021-08-16");
        Date arrivalDate = Date.valueOf("2021-08-24");
        tour1.setDepartureDate(departureDate);
        tour1.setArrivalDate(arrivalDate);
        tour1.setTour("basic package");

        Tour tour2 = new Tour();
        tour2.setCountry("Indonesia");
        tour2.setResort("Bali");
        tour2.setTransport("aircraft");
        tour2.setVisaService(true);
        tour2.setAccommodation(true);
        tour2.setAccommodationType("Champlung Mas 3*");
        tour2.setPower(true);
        tour2.setPowerType("food buffet");
        tour2.setExcursions(true);
        departureDate = Date.valueOf("2021-07-11");
        arrivalDate = Date.valueOf("2021-09-18");
        tour2.setDepartureDate(departureDate);
        tour2.setArrivalDate(arrivalDate);
        tour2.setTour("all inclusive");

        Tour tour3 = new Tour();
        tour3.setCountry("Starfish Las Palmas 3*");
        tour3.setResort("Varadero");
        tour3.setTransport("aircraft");
        tour3.setVisaService(false);
        tour3.setAccommodation(false);
        tour3.setAccommodationType("Carmen Suite Hotel 3*");
        tour3.setPower(false);
        tour3.setPowerType("food buffet");
        tour3.setExcursions(false);
        departureDate = Date.valueOf("2021-09-14");
        arrivalDate = Date.valueOf("2021-09-20");
        tour3.setDepartureDate(departureDate);
        tour3.setArrivalDate(arrivalDate);
        tour3.setTour("all inclusive");

        Tour tour4 = new Tour();
        tour4.setCountry("Cuba");
        tour4.setResort("Varadero");
        tour4.setTransport("aircraft");
        tour4.setVisaService(true);
        tour4.setAccommodation(true);
        tour4.setAccommodationType("Grand Memories Varadero 5 *");
        tour4.setPower(true);
        tour4.setPowerType("food buffet");
        tour4.setExcursions(true);
        departureDate = Date.valueOf("2021-06-01");
        arrivalDate = Date.valueOf("2021-06-12");
        tour4.setDepartureDate(departureDate);
        tour4.setArrivalDate(arrivalDate);
        tour4.setTour("all inclusive");

        List<Tour> list = new ArrayList<>();
        list.add(tour1);
        list.add(tour2);
        list.add(tour3);
        list.add(tour4);

        Iterable<Tour> tours = tourRepository.saveAll(list);
        assertThat(tours.spliterator().getExactSizeIfKnown()).isEqualTo(4);


    }


}
