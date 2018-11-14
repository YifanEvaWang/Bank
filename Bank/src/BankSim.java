
import java.util.*;

/**
 This is the main class of the program.
 The program starts running in the main() of this class.
 */
public class BankSim {

    public static void main( String [] args ) {
        // Instantiate Scanner and Bank Objects
        Scanner stdin = new Scanner( System.in );
        Bank bank = new Bank();

        // Start reading in input by reading in first time of arrival
        // As long as time of arrival is positive, keep on looping
        int t = stdin.nextInt();
        while( t >= 0 ) {
            // Increment bank clock
            int c = bank.incrementClock();

            // Read in all clients and tellers arriving in this minute
            for( ; ( t >= 0 ) && ( t <= c ); t = stdin.nextInt() ) {
                boolean businessPerson = stdin.next().equals( "business" );

                // Add client / teller to the Bank
                if( stdin.next().equals( "teller" ) ) {
                    bank.addTeller( stdin.next(), businessPerson, stdin.nextInt(),
                            stdin.nextInt(), stdin.nextLine() );
                } else {
                    bank.addClient( stdin.next(), businessPerson, stdin.nextInt(),
                            stdin.nextInt(), stdin.nextLine() );
                }
            }

            // Perform rest of the Minute simulation
            bank.doMinute();
        }

        // Close the bank after no more clients or tellers arrive
        bank.close();
    }
}
