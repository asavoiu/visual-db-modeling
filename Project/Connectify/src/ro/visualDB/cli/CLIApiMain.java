package ro.visualDB.cli.api;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * Created with IntelliJ IDEA.
 * User: Bogdan
 * Date: 13.06.2013
 * Time: 09:00
 * To change this template use File | Settings | File Templates.
 */
public class CLIApiMain {

    private static String dbType = "";
    private static String dbHost;
    private static String dbPort;
    private static String dbUserName;
    private static String dbPassword;
    private static String dbName;
    private static String dbSSLActiveValue = "";
    private static Boolean dbSSLActive = false;

    public static void main(String[] args) throws IOException {
        //TODO remove before commit
        System.out.println("Ai introdus argumentele:");
        for (String s: args) {
            System.out.println(s);
        }


            if(args[0].equals("connect")){
                if(args.length==1){
                    BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

                    while(!dbType.toLowerCase().equals("postgresql") && !dbType.toLowerCase().equals("mysql")){
                        System.out.print("Database type: PostgreSQL/MySQL");
                        dbType = br.readLine();
                    }

                    System.out.print("Insert host: ");
                    dbHost = br.readLine();

                    System.out.print("Insert database port: ");
                    dbPort = br.readLine();

                    System.out.print("Insert username: ");
                    dbUserName = br.readLine();

                    System.out.print("Insert password: ");
                    dbPassword = br.readLine();

                    System.out.print("Insert databse name(Optonal: ");
                    dbName = br.readLine();

                    if(dbType.toLowerCase().equals("postgresql")){
                        while(!dbSSLActiveValue.toLowerCase().equals("y") && !dbSSLActiveValue.toLowerCase().equals("n")){
                            System.out.print("Activate SSL? (Y/N)");
                            dbSSLActiveValue = br.readLine().toUpperCase();
                        }
                        dbSSLActive = (dbSSLActiveValue.equals("Y"));
                    }

                }
            } else if(args.length >= 6){
                dbType = args[0];
                dbHost = args[1];
                dbPort = args[2];
                dbUserName = args[3];
                dbPassword = args[4];
                dbName = args[5];

                if(args.length > 6){
                    dbSSLActive = (dbSSLActiveValue.equals("Y"));
                }
            }

            if(dbType.toLowerCase().equals("postgresql")){
                //TODO connect PostgreSQL
            }else if(dbType.toLowerCase().equals("postgresql")){
                //TODO connect MySQL
        }
    }

}