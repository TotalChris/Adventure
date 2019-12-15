import java.util.HashMap;

/**
 * CS1131 Final Project
 *
 * Last edited on 12/10/19
 *
 * @author Ryan Miller, Chris Haueisen, Ethan Brinks, Kirk Macquarrie
 */
public class Server implements ConnectionListener {
    AdventureServer adventureServer = null;
    HashMap<Long, Adventure> games = new HashMap<>();

    public Server( ) {
        adventureServer = new AdventureServer( );
        adventureServer.setOnTransmission( this );
    }

    public void start( int port ) {
        adventureServer.startServer( port );
    }

    @Override
    public void handle ( ConnectionEvent e ) {
        System.out.println( "EVENT RECEIVED - YOU MUST PARSE THE DATA AND RESPOND APPROPRIATELY");
        System.out.println( String.format ( "connectionId=%d, data=%s", e.getConnectionID (), e.getData() ));
        try {
            switch ( e.getCode ( ) ) {
                case CONNECTION_ESTABLISHED:
                    games.put(e.getConnectionID(), new Adventure(e.getConnectionID(), adventureServer));
                    adventureServer.sendMessage(e.getConnectionID(), "");
                    break;
                case TRANSMISSION_RECEIVED:
                    adventureServer.sendMessage ( e.getConnectionID ( ), String.format (
                            "MESSAGE RECEIVED: connectionId=%d, data=%s", e.getConnectionID ( ), e.getData ( ) ) );
                    adventureServer.sendMessage(e.getConnectionID(), games.get(e.getConnectionID()).processCommand(e.getData()));
                    adventureServer.sendMessage(e.getConnectionID(), "");
                    break;
                case CONNECTION_TERMINATED:
                    games.get(e.getConnectionID()).end();
                    games.remove(e.getConnectionID());
                    break;
                default:
                    // What is a reasonable default?
            }
        } catch ( UnknownConnectionException unknownConnectionException ) {
            unknownConnectionException.printStackTrace ( );
        }
    }

    public static void main ( String[] args ) {
        Server server = new Server();
        server.start ( 2112 );
    }

}

