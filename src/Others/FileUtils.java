package Others;

import PhysiotherapyCabinet.Customer;

import java.io.*;

/**
 * Klasa wykorzystywana do zapisu i odczytu ekstensji z pliku
 *
 */
public class FileUtils  {
    /**
     * Metoda używana do odczytu ekstensji z pliku
     *
     * @throws Exception
     */
    public static void readFromFile() throws Exception {


        if(new File("data").isFile())
        {
            try
            {
                FileInputStream fileInput = new FileInputStream("data");
                ObjectInputStream streamInput = new ObjectInputStream(fileInput);
                ObjectPlus.readExtents(streamInput);
                Customer.readCustomerMap(streamInput);
                streamInput.close();
                fileInput.close();
            }
            catch(IOException i){
                i.printStackTrace();
                return;
            }
            catch(ClassNotFoundException c){
                System.out.println("Nie znaleziono klasy.");
                c.printStackTrace();
                return;
            }
        }
    }

    /**
     * Metoda używa do zapisu ekstencji do pliku
     *
     * @throws IOException
     */
    public static void saveToFile() throws IOException {
        FileOutputStream fileOutput = new FileOutputStream("data");
        ObjectOutputStream StreamOutput = new ObjectOutputStream(fileOutput);
        ObjectPlus.saveExtents(StreamOutput);
        Customer.saveCustomerMap(StreamOutput);
        StreamOutput.close();
        fileOutput.close();
    }
}
