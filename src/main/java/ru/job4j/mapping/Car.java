package ru.job4j.mapping;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.StringJoiner;

@Entity
@Table(name = "car")
public class Car {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @ManyToOne
    @JoinColumn(name = "engine_id", foreignKey = @ForeignKey(name = "ENGINE_ID_FK") )
    private Engine engine;

    @ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinTable (
            name = "history_owner",
            joinColumns = @JoinColumn (name = "car_id"),
            inverseJoinColumns = @JoinColumn (name = "driver_id")
    )
    private List<Driver> drivers = new ArrayList<>();

    public Car(Engine engine) {
        this.engine = engine;
    }

    public Car(int id) {
        this.id = id;
    }

    public Car(int id, Engine engine) {
        this.id = id;
        this.engine = engine;
    }

    public Car() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Engine getEngine() {
        return engine;
    }

    public void setEngine(Engine engine) {
        this.engine = engine;
    }

    public List<Driver> getDrivers() {
        return drivers;
    }

    public void setDrivers(List<Driver> drivers) {
        this.drivers = drivers;
    }

    public void addDriver(Driver driver) {
        drivers.add(driver);
    }

}
