package PhysiotherapyCabinet;

import Others.ObjectPlus;

import java.math.BigInteger;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.Vector;
import java.util.stream.Stream;

/**
 * Abstrakcyjna klasa Empoloyee po której dziedziczą klasy Physiotherapist i Recepctionist
 * Asocjacja 1..* z klasą Visit
 *
 * @see Visit
 * @see Receptionist
 * @see Physiotherapist
 */

public abstract class Employee extends Person {

    private static final long serialVersionUID = 2L;
    private LocalDate hireDate;
    private LocalDate firedDate;
    private double salary;
    private Double bonus;
    private long pesel;
    private BigInteger accountNumber;
    private LocalDate medicalExaminationDate;

    public Employee(String firstname, String surname, int telefonNumber, String street, String houseNumber, String zipCode, String city, double salary, long pesel, BigInteger accountNumber, LocalDate medicalExaminationDate) {
        super(firstname, surname, telefonNumber, street, houseNumber, zipCode, city);
        this.salary = salary;
        this.pesel = pesel;
        this.accountNumber = accountNumber;
        this.medicalExaminationDate = medicalExaminationDate;
        hireDate = LocalDate.now();
    }
    @Override
    public String toString() {
        String join = "";
        join += super.toString() +
                "\nPesel: " + pesel +
                "\nData Zatrudnienia: " + hireDate.toString();
        if (firedDate != null) {
            join += "\nData zwolnienia/odejścia: " + firedDate.toString();
        }
        join += "\nPensja: " + salary;
        if (bonus != null) {
            join += "\nPremia: " + bonus;
        }
        join += "\nNumer konta " + accountNumber +
                " \nTermin padania lekarskiego: " + medicalExaminationDate;
        return join;
    }

    /**
     * Zwraca prawoników którym skończyłą się ważność badania lekarskiego
     *
     * @return
     * @throws Exception
     */
    public static Vector<Employee> checkMedicalExaminationDate() throws Exception {
        Vector<Employee> employees = new Vector<>();
        Stream<Employee> employeeStream = (ObjectPlus.getExtent(Employee.class)).stream();
        employeeStream.filter(e -> e.getMedicalExaminationDate().isBefore(LocalDate.now())).forEach(e -> employees.add(e));
        return employees;
    }

    /**
     * Daje pracownikowi premie ale nie większą niz 20% jego pensji
     *
     * @param bonus
     */
    public void giveBonus(double bonus) {
        if (bonus < 0.2 * salary) {
            setBonus(bonus);
        } else {
            throw new NullPointerException("Za wysoki bonus");
        }
    }

    /**
     * Zwraca długość stażu pracy pracownika (w miesiącach)
     *
     * @return
     */
    public long getEmployementTime() {
        System.out.println(ChronoUnit.MONTHS.between(LocalDate.now(), hireDate));
        return ChronoUnit.MONTHS.between(LocalDate.now(), hireDate);
    }

    public LocalDate getHireDate() {
        return hireDate;
    }

    public void setHireDate(LocalDate hireDate) {
        this.hireDate = hireDate;
    }

    public LocalDate getFiredDate() {
        return firedDate;
    }

    public void setFiredDate(LocalDate firedDate) {
        this.firedDate = firedDate;
    }

    public double getSalary() {
        return salary;
    }

    public void setSalary(double salary) {
        this.salary = salary;
    }

    public Double getBonus() {
        return bonus;
    }

    public void setBonus(Double bonus) {
        this.bonus = bonus;
    }

    public long getPesel() {
        return pesel;
    }

    public void setPesel(long pesel) {
        this.pesel = pesel;
    }

    public BigInteger getAccountNumber() {
        return accountNumber;
    }

    public void setAccountNumber(BigInteger accountNumber) {
        this.accountNumber = accountNumber;
    }

    public LocalDate getMedicalExaminationDate() {
        return medicalExaminationDate;
    }

    public void setMedicalExaminationDate(LocalDate medicalExaminationDate) {
        this.medicalExaminationDate = medicalExaminationDate;
    }
}
