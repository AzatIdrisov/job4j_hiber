package ru.job4j.hql;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "vacancy_store")
public class VacancyStore {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String name;

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Vacancy> vacancies = new ArrayList<>();

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Vacancy> getVacancies() {
        return vacancies;
    }

    public void setVacancies(List<Vacancy> vacancies) {
        this.vacancies = vacancies;
    }

    public void addVacancy(Vacancy vacancy) {
        this.vacancies.add(vacancy);
    }

    public static VacancyStore of(String username) {
        VacancyStore v = new VacancyStore();
        v.name = username;
        return v;
    }

    @Override
    public String toString() {
        return "VacancyStore{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", vacancies=" + vacancies +
                '}';
    }
}
