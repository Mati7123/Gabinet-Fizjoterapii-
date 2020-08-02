import Others.*;
import PhysiotherapyCabinet.*;

import GUI.*;

import java.math.BigInteger;
import java.time.LocalDate;
import java.time.LocalTime;

/**
 * Klasa main urchomiająca aplikacje i tworząca podstawowe obiekty i ich asocjacje
 */
public class Main implements RoleName {

    public static void main(String[] args) throws Exception {

        FileUtils.readFromFile();

        //createObjects();
        new StartFrame();
        FileUtils.saveToFile();

    }

    /**
     * Metoda tworząca przykładowe obiekty
     */
    public static void createObjects() throws Exception {


        Customer customer1 = new Customer("Andrzej", "Kowalski", 552163879, "Warszawska", "16", "05-092", "Łomianki", "Aleksandra", "Kowalska", LocalDate.of(2010, 12, 30));
        Customer customer2 = new Customer("Maciej", "Nowak", 589322163, "Ostrobramska", "5a", "00-965", "Warszawa", "Marta", "Nowa", LocalDate.of(2009, 5, 15));
        Customer customer3 = new Customer("Franciszek", "Baranowicz", 682145852, "Rolnicza", "233e", "05-092", "Kiełpin", "Mateusz", "Baranowicz", LocalDate.of(2011, 6, 22));
        Customer customer4 = new Customer("Alicja", "Flak", 987123654, "Marymocnka", "68", "00-853", "Warszawa", "Tomasz", "Kowalski", LocalDate.of(2013, 1, 6));
        Customer customer5 = new Customer("Marta", "Sobieraj", 888813514, "Polna", "277a m1", "00-932", "Warszawsa", "Tomasz", "Sobieraj", LocalDate.of(2012, 6, 9));
        Customer customer6 = new Customer("Daniel", "Kreg", 888813114, "Wiosenna", "27", "00-931", "Warszawa", "Tomasz", "Kreg", LocalDate.of(2019, 6, 9));
        Customer customer7 = new Customer("Ewa", "Smyk", 888813114, "Wiosenna", "27", "00-931", "Warszawa", "Tomasz", "Kreg", LocalDate.of(2019, 8, 9));
        Customer customer8 = new Customer("Sylwester", "Smyk", 888813114, "Wiosenna", "27", "00-931", "Warszawa", "Tomasz", "Kreg", LocalDate.of(2019, 8, 9));
        customer3.setDiscount(5);
        customer4.setRegularCustomer(true);

        Physiotherapist physiotherapist1 = new Physiotherapist("Kamil", "Fronawicz", 632456789, "Wiklinowa", "15c", "05-092", "Kiełpin", 5000, 900311071323L, new BigInteger("39102010260000150201027408"), LocalDate.of(1990, 3, 11), "polski");
        Physiotherapist physiotherapist2 = new Physiotherapist("Wojciech", "Rutek", 663558142, "Włościańska", "61", "05-092", "Łomianki", 3000, 89082108589L, new BigInteger("39123010260000150201027408"), LocalDate.of(1989, 8, 21), "angielski");
        Physiotherapist physiotherapist3 = new Physiotherapist("Anna", "Szymanowska", 891254147, "Wiejska", "13", "05-092", "Łomianki", 8000, 92090227132L, new BigInteger("45102010260000150201027438"), LocalDate.of(1985, 1, 5), "polski");
        Physiotherapist physiotherapist4 = new Physiotherapist("Radosław", "Kallina", 662360444, "Wiejska", "89", "02-083", "Warszawa", 3560, 80122427132L, new BigInteger("25102010260000150801027438"), LocalDate.of(1980, 12, 24), "polski");
        Physiotherapist physiotherapist5 = new Physiotherapist("Piotr", "Woloń", 891231478, "Warszawska", "823", "09-321", "Radom", 5879, 80122327112L, new BigInteger("69102010341000159801021438"), LocalDate.of(1980, 12, 23), "polski");
        physiotherapist2.addLanguage("czeski");

        Receptionist receptionist1 = new Receptionist("Jolanta", "Stach", 662332321, "Teleexpressu", "3", "82-120", "Krynica Morska", 2500, 98101122645L, new BigInteger("60102010260000150201027408"), LocalDate.of(1998, 1, 11), "biuro@fizjogab.com");
        Receptionist receptionist2 = new Receptionist("Kamila", "Batory", 698123458, "Plastusia", "3", "05-092", "Augustó∑", 2500, 99101122645L, new BigInteger("23102010260000150201027408"), LocalDate.of(1999, 1, 11), "recepcja@fizjogab.com");

        Specialization specialization1 = new Specialization("Masaż");
        Specialization specialization2 = new Specialization("Rehabilitacja w ortopedii");
        Specialization specialization3 = new Specialization("Wady postaw");
        Specialization specialization4 = new Specialization("Diagnostyka funkcjonalna");
        Specialization specialization5 = new Specialization("Fizjoterapia w pediatrii");

        PhysiotherapistSpecialization physiotherapistSpecialization1 = new PhysiotherapistSpecialization(LocalDate.of(2015, 1, 3), specialization5, physiotherapist1);
        PhysiotherapistSpecialization physiotherapistSpecialization2 = new PhysiotherapistSpecialization(LocalDate.of(2014, 3, 3), specialization2, physiotherapist2);
        PhysiotherapistSpecialization physiotherapistSpecialization3 = new PhysiotherapistSpecialization(LocalDate.of(2014, 3, 3), specialization1, physiotherapist1);
        PhysiotherapistSpecialization physiotherapistSpecialization4 = new PhysiotherapistSpecialization(LocalDate.of(2014, 3, 3), specialization3, physiotherapist3);
        PhysiotherapistSpecialization physiotherapistSpecialization5 = new PhysiotherapistSpecialization(LocalDate.of(2014, 3, 3), specialization4, physiotherapist4);
        PhysiotherapistSpecialization physiotherapistSpecialization6 = new PhysiotherapistSpecialization(LocalDate.of(2014, 3, 3), specialization5, physiotherapist5);

        JobPermission jobPermission1 = new JobPermission("Praca z dziećmi powyżej 1 roku");
        JobPermission jobPermission2 = new JobPermission("Praca z niemowlętami");

        IndividualRoom individualRoom1 = new IndividualRoom(1, 10, true, true, true, true, null);
        IndividualRoom individualRoom2 = new IndividualRoom(2, 15, false, false, false, false, 1);
        IndividualRoom individualRoom3 = new IndividualRoom(3, 13, true, false, false, false, 1);
        IndividualRoom individualRoom4 = new IndividualRoom(4, 13, true, true, false, false, null);
        IndividualRoom individualRoom5 = new IndividualRoom(5, 11, false, false, false, false, 1);

        GroupRoom groupRoom1 = new GroupRoom(6, 30, true, true, true, false, null);
        GroupRoom groupRoom2 = new GroupRoom(7, 36, true, false, false, true, 8);

        AvailableVisitType availablevisitType1 = new AvailableVisitType("Rehabilitacja niemowląt", 100, 250);
        AvailableVisitType availablevisitType2 = new AvailableVisitType("Wizyta ostopeatyczna", 100, 300);
        AvailableVisitType availablevisitType3 = new AvailableVisitType("Integracja sensoryczna", 50, 200);
        AvailableVisitType availablevisitType4 = new AvailableVisitType("Fizjoterapia pediatryczna", 100, 200);

        GroupVisit groupVisit1 = GroupVisit.createGroupVisit(availablevisitType3, LocalDate.of(2020, 8, 5), LocalTime.of(12, 50), 60, 100);
        GroupVisit groupVisit2 = GroupVisit.createGroupVisit(availablevisitType2, LocalDate.of(2020, 7, 4), LocalTime.of(18, 0), 60, 100);
        GroupVisit groupVisit3 = GroupVisit.createGroupVisit(availablevisitType1, LocalDate.of(2020, 7, 4), LocalTime.of(12, 0), 60, 100);

        IndividualVisit individualVisit1 = IndividualVisit.createIndvidualVisit(availablevisitType1, LocalDate.of(2020, 8, 11), LocalTime.of(8, 0), 30, 150);
        IndividualVisit individualVisit2 = IndividualVisit.createIndvidualVisit(availablevisitType2, LocalDate.of(2020, 8, 2), LocalTime.of(9, 0), 60, 150);
        IndividualVisit individualVisit3 = IndividualVisit.createIndvidualVisit(availablevisitType4, LocalDate.of(2020, 8, 3), LocalTime.of(10, 0), 60, 150);
        IndividualVisit individualVisit4 = IndividualVisit.createIndvidualVisit(availablevisitType3, LocalDate.of(2020, 8, 7), LocalTime.of(11, 0), 60, 150);
        IndividualVisit individualVisit5 = IndividualVisit.createIndvidualVisit(availablevisitType2, LocalDate.of(2020, 8, 8), LocalTime.of(11, 0), 60, 250);

        jobPermission1.addLink(physiotherapistRole, permissionRole, physiotherapist1, 123);
        jobPermission2.addLink(physiotherapistRole, permissionRole, physiotherapist1, 696);
        jobPermission2.addLink(physiotherapistRole, permissionRole, physiotherapist2, 234);
        jobPermission1.addLink(physiotherapistRole, permissionRole, physiotherapist3, 345);
        jobPermission1.addLink(physiotherapistRole, permissionRole, physiotherapist4, 456);
        jobPermission2.addLink(physiotherapistRole, permissionRole, physiotherapist5, 567);

        receptionist1.addLink(visitRole, receptionistRole, groupVisit1);
        receptionist1.addLink(visitRole, receptionistRole, groupVisit2);
        receptionist2.addLink(visitRole, receptionistRole, groupVisit3);

        receptionist1.addLink(visitRole, receptionistRole, individualVisit1);
        receptionist2.addLink(visitRole, receptionistRole, individualVisit2);
        receptionist1.addLink(visitRole, receptionistRole, individualVisit3);
        receptionist2.addLink(visitRole, receptionistRole, individualVisit4);
        receptionist1.addLink(visitRole, receptionistRole, individualVisit5);

        groupVisit1.addLink(customerRole, visitRole, customer1);
        groupVisit1.addLink(customerRole, visitRole, customer2);
        groupVisit1.addLink(customerRole, visitRole, customer3);
        groupVisit1.addLink(customerRole, visitRole, customer4);
        groupVisit1.addLink(customerRole, visitRole, customer5);
        groupVisit2.addLink(customerRole, visitRole, customer3);
        groupVisit2.addLink(customerRole, visitRole, customer4);
        groupVisit2.addLink(customerRole, visitRole, customer5);
        groupVisit3.addLink(customerRole, visitRole, customer7);
        groupVisit3.addLink(customerRole, visitRole, customer8);

        individualVisit1.addLink(customerRole, visitRole, customer6);
        individualVisit2.addLink(customerRole, visitRole, customer2);
        individualVisit3.addLink(customerRole, visitRole, customer3);
        customer1.addLink(visitRole, customerRole, individualVisit4);
        individualVisit5.addLink(customerRole, visitRole, customer3);

        physiotherapist3.addLink(visitRole, physiotherapistRole, groupVisit1);
        physiotherapist2.addLink(visitRole, physiotherapistRole, groupVisit2);
        physiotherapist1.addLink(visitRole, physiotherapistRole, groupVisit3);
        physiotherapist5.addLink(visitRole, physiotherapistRole, individualVisit1);
        physiotherapist3.addLink(visitRole, physiotherapistRole, individualVisit2);
        physiotherapist4.addLink(visitRole, physiotherapistRole, individualVisit3);
        physiotherapist2.addLink(visitRole, physiotherapistRole, individualVisit4);
        physiotherapist1.addLink(visitRole, physiotherapistRole, individualVisit5);

        groupVisit3.addLink(roomRole, visitRole, groupRoom1);
        groupVisit2.addLink(roomRole, visitRole, groupRoom2);
        groupVisit1.addLink(roomRole, visitRole, groupRoom2);

        individualVisit1.addLink(roomRole, visitRole, individualRoom1);
        individualVisit3.addLink(roomRole, visitRole, individualRoom4);
        individualVisit2.addLink(roomRole, visitRole, individualRoom2);
        individualVisit4.addLink(roomRole, visitRole, individualRoom3);
        individualVisit5.addLink(roomRole, visitRole, individualRoom5);
    }
}
