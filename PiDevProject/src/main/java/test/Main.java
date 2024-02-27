package test;

import Entities.Exercices;
import Entities.Nutritions;
import Service.ExercicesServices;
import Service.NutritionsServices;
import utils.DataSource;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Main
{

public static void main(String[] args)
{
    DataSource ds1=DataSource.getInstance();
    Exercices e1=new Exercices("Cardio","Bicyclette");
    ExercicesServices ex1=new ExercicesServices();
    //ex1.add(e1);
   // ex1.readall();
    //ex1.delete(6);
    //ex1.readall();
   // ex1.update(9,"Youssef","Ben Hiba");
   // ex1.readall();
   Nutritions n1=new Nutritions("Maigre");
    NutritionsServices ns=new NutritionsServices();
    //ns.add(n1);
    //ns.readall();
   // ns.update(2,"BULKING");
    //ns.readall();
   // ns.delete(2);
   // ns.readall();
}
}
