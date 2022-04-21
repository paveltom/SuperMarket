package com.company.PresentationLayer;

public class MenuPrinter {

    private String[] options = {"exit"};

    public MenuPrinter(){
        runPrinter();
    }

    public void printMenu(String[] options){
        for (String option : options){
            System.out.println(option);
        }
        System.out.print("Choose your option : ");
    }
    // while?

    // execute funcs depending on user choice

    // parts to be presented:
    // order a delivery, add a driver, add a truck, show drivers/trucks, show delivery history, search by parameter


    private void runPrinter(){
        Scanner scanner = new Scanner(System.in);
        int option;
        while (true){
            printMenu(options);
            option = scanner.nextInt();
        }
    }
    }


    //
}
