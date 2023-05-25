package com.primerparcial.primer.parcial.service;

import com.primerparcial.primer.parcial.model.Car;
import com.primerparcial.primer.parcial.repository.CarRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.primerparcial.primer.parcial.model.User;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CarServiceImp implements CarService {

    @Autowired
    private final CarRepository carRepository;
    private CarService carService;

    @Override
    public Boolean createCar(Car car){
        try {
            Car carSave = carRepository.save(car);
            return true;
        }catch (Exception e){
            return false;
        }
    }

    @Override
    public Car getCar(Long id){
        Optional<Car> carOptional = carRepository.findById(id);
        if (carOptional.isPresent()) {
            return carOptional.get();
        } else {
            // Manejo de caso cuando el carro no existe
            throw new NoSuchElementException("Car not found with ID: " + id);
        }
        //  return carRepository.findById(id).get();
    }

    @Override
    public List<Car> getAllCars(){
        return carRepository.findAll();
    }

    @Override
    public Boolean updateCar(Car car, Long id){
        try {
            Car carBD = carRepository.findById(id).get();
            List<Car> carro =carRepository.findAll();
            boolean p=true;
            if(carBD == null) {
                return false;
            }
            for (Car car1:carro) {
                if(car1.getCar_vin().equals(carBD.getCar_vin())){
                    p=false;
                }
            }
            if(p==true) {
                carBD.setCar(car.getCar());
                carBD.setCar_model(car.getCar_model());
                carBD.setCar_color(car.getCar_color());
                carBD.setCar_model_year(car.getCar_model_year());
                carBD.setCar_vin(car.getCar_vin());
                carBD.setPrice(car.getPrice());
                carBD.setAvailability(car.getAvailability());
                Car carUp = carRepository.save(carBD);
                return true;
            }
            return false;
        }catch (Exception e){
            return false;
        }
    }

    @Override
    public Boolean deleteCar(Long id, Car car) {
        try {
            Car carDB = carRepository.findById(id).get();
            if (carDB == null){
                return false;
            }
            carRepository.delete(carDB);
            return true;
        }catch (Exception e){
            return false;
        }
    }

    @Override
    public List<Car> getCarsByUser(User user) {

        return carRepository.findByUser(user);
    }

}

