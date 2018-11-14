
import java.util.*;

/**
 This is the Bank class, which simulates the interactions within a bank.
 */
public class Bank {
    // Teller and client lines
    private ArrayList<Teller> bankTellers = new ArrayList<Teller>();
    private ArrayList<Client> personalClients = new ArrayList<Client>();
    private ArrayList<Client> businessClients = new ArrayList<Client>();

    private int clock = 0;    // Bank clock
    private boolean debug;    // debug flag

    // Lists to store bank stats
    private ArrayList<Integer> hourlyBusinessStats = new ArrayList<Integer>();
    private ArrayList<Integer> hourlyPersonalStats = new ArrayList<Integer>();
    private ArrayList<Integer> summaryBusinessStats = new ArrayList<Integer>();
    private ArrayList<Integer> summaryPersonalStats = new ArrayList<Integer>();

    /* Default constructor: nothing to do */


    /* Complete the simulation and output the stats.
     */
    public void close() {
        // As long as the tellers are busy, keep the bank running
        while( !bankTellers.isEmpty() ) {
            // increment clock and simulate the minute
            incrementClock();
            doMinute();
        }

        // Output the stats
        System.out.println( "Hourly Average Line-Ups" );

        // Loop through the lists and output the summary stats.
        int n = summaryPersonalStats.size();
        for( int i = 0; i < n; i++ ) {
            System.out.format( "hour %d : personal %d, business %d\n", i + 1,
                    summaryPersonalStats.get( i ), summaryBusinessStats.get( i ) );
        }
    }


    /* Add a teller to the teller line.

       Parameters: name     : name of teller
                   business : flag indicating business or personal teller
                   shift    : Length of teller's bank shift
                   numLangs : Number of languages the teller speaks
                   langs    : list of languages spoken by teller
     */
    public void addTeller( String name, boolean business, int shift, int numLangs,
                           String langs ) {
        // Create new Teller object and print out arrival of teller
        Teller t = new Teller( name, clock, business, shift );
        System.out.println( clock + " : " + t + " arrives" );

        // Parse the languages and add them to the teller
        for( Scanner s = new Scanner( langs ); numLangs > 0; numLangs-- ) {
            t.addLanguage( s.next() );
        }

        // Add teller to teller line
        bankTellers.add( t );
    }


    /* Add a client to a client line.

       Parameters: name     : name of teller
                   business : flag indicating business or personal teller
                   req      : Length of clients request
                   numLangs : Number of languages the teller speaks
                   langs    : list of languages spoken by teller
     */
    public void addClient( String name, boolean business, int req, int numLangs,
                           String langs ) {
        // Create new Client object and print out arrival of client
        Client c = new Client( name, clock, business, req );
        System.out.println( clock + " : " + c + " arrives" );

        // Parse the languages and add them to the client
        for( Scanner s = new Scanner( langs ); numLangs > 0; numLangs-- ) {
            c.addLanguage( s.next() );
        }

        // Add client to busienss or personal line
        if( business ) {
            businessClients.add( c );
        } else {
            personalClients.add( c );
        }
    }


    /* Increment bank clock and return it

       Returns: value of clock post-increment
     */
    public int incrementClock() {
        clock++;
        return clock;
    }


    /* Simulate one minute of bank time
     */
    public void doMinute() {
        // Increment each teller's clock
        for( Teller t : bankTellers ) {
            // Incrment teller's clock and perform further actions if needed
            if( t.clockTick() ) {
                // Check if client is ready to leave
                Client c = t.exitClient();
                if( c != null ) {
                    // If so, print out the status
                    System.out.println( clock + " : " + c + " leaves" );
                }
            }
        }

        // Remove tellers who are finished.
        // We first add them to a toRemove list, and then remove
        // them afterwards
        ArrayList<Teller> toRemove = new ArrayList<Teller>();
        for( Teller t : bankTellers ) {
            // Check if teller is finished
            if( t.finished() ) {
                // If so add them to the toRemove list, and print out the status
                toRemove.add( t );
                System.out.println( clock + " : " + t + " leaves" );
            }
        }

        // Remove all tellers in the toRemove list from the Teller line.
        for( Teller t : toRemove ) {
            bankTellers.remove( t );
        }

        // Check if tellers are available to take new clients
        for( Teller t : bankTellers ) {
            // Check if teller is available
            if( t.available() ) {
                // If teller is a business teller and business clients are waiting
                if( t.isBusinessPerson() && !businessClients.isEmpty() ) {
                    // Grab next business client, set her to the teller, and print status
                    Client c = businessClients.remove( 0 );
                    t.setClient( c );
                    System.out.println( clock + " : " + c + " is served by " +  t );
                } else if( !personalClients.isEmpty() ) {
                    // Otherwise, if personal clients are waiting
                    // Grab next personal client, set him to the teller, and print status
                    Client c = personalClients.remove( 0 );
                    t.setClient( c );
                    System.out.println( clock + " : " + c + " is served by " +  t );
                }
            }
        }

        // Record size of business and personal client lines
        hourlyBusinessStats.add( businessClients.size() );
        hourlyPersonalStats.add( personalClients.size() );

        // If an hour has passed, compute summary stats
        if( ( clock % 60 ) == 0 ) {
            // Compute personal lime length median
            int mean = 0;
            for( int i : hourlyPersonalStats ) {
                mean += i;
            }
            // add summary stat to summary stat list and clear hourly list of stats
            summaryPersonalStats.add( mean / hourlyPersonalStats.size() );
            hourlyPersonalStats.clear();


            // Compute business lime length median
            mean = 0;
            for( int i : hourlyBusinessStats ) {
                mean += i;
            }
            // add summary stat to summary stat list and clear hourly list of stats
            summaryBusinessStats.add( mean / hourlyBusinessStats.size() );
            hourlyBusinessStats.clear();
        }
    }
}
