package org.example;

import java.util.List;

public class Main {
    public static void main(String[] args) {
        FreightWagon freight = new FreightWagon("F-1001", 25.0, "RailCargo", 60.0);
        PassengerWagon passenger = new PassengerWagon("P-2002", 55.0, "Rail", 54);

        // ---------- 1. Информация о вагонах ----------
        System.out.println("=== Wagons ===");
        printInfo(freight);
        printInfo(passenger);

        freight.setOwner("TransLogistic");
        System.out.println("New owner of F-1001: " + freight.getOwner());

        // ---------- 2. Полный цикл грузового вагона ----------
        System.out.println("\n=== Freight trip ===");
        Cargo glass = new Cargo(20.0, true, 150_000, "Glass panels");

        freight.giveTask("Minsk");
        System.out.println("Task given, status: " + freight.getStatus());

        freight.loadCargo(glass);
        System.out.println("Loaded '" + freight.getCargo().getDescription()
                + "', status: " + freight.getStatus());
        System.out.println("Fragile: " + freight.isCargoFragile()
                + ", value: " + freight.getCargoValue());
        System.out.println("Total weight: " + freight.getTotalWeight() + " t");

        freight.depart();
        System.out.println("Departed to " + freight.getDestination()
                + ", status: " + freight.getStatus());

        freight.arrive();
        System.out.println("Arrived, status: " + freight.getStatus());

        freight.unloadCargo();
        System.out.println("Unloaded, status: " + freight.getStatus()
                + ", total weight: " + freight.getTotalWeight() + " t");

        // ---------- 3. Недопустимые действия ----------
        System.out.println("\n=== Invalid actions ===");
        tryAction("Depart without task", freight::depart);
        tryAction("Ask fragility of empty wagon", freight::isCargoFragile);

        freight.giveTask("Brest");
        Cargo tooHeavy = new Cargo(80.0, false, 5_000, "Coal");
        tryAction("Load cargo heavier than max load", () -> freight.loadCargo(tooHeavy));
        freight.loadCargo(new Cargo(55.0, false, 5_000, "Coal"));
        freight.depart();
        freight.arrive();
        freight.unloadCargo();

        // ---------- 4. Поломка и ремонт ----------
        System.out.println("\n=== Breakdown and repair ===");
        freightTrip(freight, "Gomel", new Cargo(30.0, false, 10_000, "Grain"));   // 3-я поездка
        System.out.println("After 3rd trip, status: " + freight.getStatus());

        tryAction("Give task to broken wagon", () -> freight.giveTask("Vitebsk"));

        freight.startRepair();
        System.out.println("Repair started, status: " + freight.getStatus());
        freight.finishRepair();
        System.out.println("Repair finished, status: " + freight.getStatus());

        // ---------- 5. Обслуживание предотвращает поломку ----------
        System.out.println("\n=== Passenger wagon with service ===");
        passengerTrip(passenger, "Grodno", 40);
        passengerTrip(passenger, "Mogilev", 54);
        passenger.service();
        System.out.println("Serviced after 2 trips");
        passengerTrip(passenger, "Minsk", 30);
        passengerTrip(passenger, "Brest", 20);
        System.out.println("After 4 trips with service, status: " + passenger.getStatus());

        passenger.giveTask("Pinsk");
        tryAction("Board more passengers than seats", () -> passenger.board(60));
        passenger.board(50);
        System.out.println("Boarded " + passenger.getPassengerCount() + "/"
                + passenger.getSeatCount() + ", total weight: "
                + passenger.getTotalWeight() + " t");

        // ---------- 6. Работа через интерфейсы ----------
        System.out.println("\n=== All wagons via WagonInfo ===");
        List<WagonInfo> wagons = List.of(freight, passenger);
        for (WagonInfo wagon : wagons) {
            printInfo(wagon);
        }
    }

    private static void printInfo(WagonInfo wagon) {
        System.out.println(wagon.getRegistrationNumber()
                + " | owner: " + wagon.getOwner()
                + " | status: " + wagon.getStatus()
                + " | tare: " + wagon.getTareWeight() + " t"
                + " | total: " + wagon.getTotalWeight() + " t");
    }

    private static void freightTrip(FreightWagon wagon, String destination, Cargo cargo) {
        wagon.giveTask(destination);
        wagon.loadCargo(cargo);
        wagon.depart();
        wagon.arrive();
        wagon.unloadCargo();
    }

    private static void passengerTrip(PassengerWagon wagon, String destination, int passengers) {
        wagon.giveTask(destination);
        wagon.board(passengers);
        wagon.depart();
        wagon.arrive();
        wagon.disembark();
    }

    private static void tryAction(String description, Runnable action) {
        try {
            action.run();
            System.out.println(description + ": OK");
        } catch (IllegalStateException | IllegalArgumentException e) {
            System.out.println(description + ": refused (" + e.getMessage() + ")");
        }
    }
}