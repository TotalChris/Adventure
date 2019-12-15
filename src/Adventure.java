import java.util.ArrayList;
import java.util.Vector;

public class Adventure {

    Location currentRoom;
    Vector<Location> rooms;
    Location room1;
    Location room2;
    Location room3;
    Location room4;
    Location room5;
    Location room6;
    Location room7;
    Location room8;
    Location room9;
    Container hand;
    long connectionId;
    AdventureServer server;

    public Adventure(long connectionId, AdventureServer server) throws UnknownConnectionException {
        this.connectionId = connectionId;
        this.server = server;
        this.init();
        this.onboarding();
    }

    private void onboarding() throws UnknownConnectionException {
        Location space = new Location("Virtual Spaaaaaaace");
        space.addEvent(new GameEvent("START", () -> moveToRoom(room8)));
        currentRoom = space;
        server.sendMessage(connectionId, "Welcome To The Michigan Tech E-Sports Heist Game! \nYour friend is entering the Michigan Tech Esports team and needs a computer that he can rely on to help him win the tournament. \nHowever, both of you are poor college students and do not have the funds to pay for this gaming computer. \nInstead of saving up and potentially not participating this year, you decide to steal parts from a local supplier.\n You must steal the best possible parts from the supplier and not get caught in the process. \nSend START to begin the game.");
    }

    /**
     * Moves the player to a new room using the direction specified
     *
     * @param i The integer code specifying the direction to move in. Direction codes can be used by using the notation Exit.DIRECTION
     * @return the next room's description, to be printed by the calling method.
     */
    public String moveInDirection(int i){
        Location nextRoom = currentRoom.getExit(i).getLeadsTo();
        return moveToRoom(nextRoom);
    }
    /**
     * Moves the player to the new room specified
     *
     * @param room The new room to move to. The new room must be a Location.
     * @return The next room's description, to be printed by the calling method.
     *
     * @see Location
     */
    public String moveToRoom(Location room) {
        if(room != null) {
            currentRoom = room;
            return currentRoom.getDescription();
        } else {
            return "";
        }
    }
    /**
     * Creates the rooms used by the game, and links them
     * together via each room's 'exits' vector.
     */
    public void init(){
                rooms = new Vector<>();
                hand = new Container(1,"Hand");
                //construct the game
                room1 = new Location("High-Value GPU Stockroom");
                room2 = new Location("GPU Stockroom");
                room3 = new Location("Motherboard/PSU Stockroom");
                room4 = new Location("CPU Stockroom");
                room5 = new Location("Main Room");
                room6 = new Location("Case/RAM Stockroom");
                room7 = new Location("High-Value CPU Stockroom");
                room8 = new Location("Entryway");
                room9 = new Location("Security Room");

                //room1
                room1.addExit(new Exit(Exit.EAST, room2));
                room1.setDescription("As you enter, you see over-the-top reflections on every surface, real-time god rays shine down from the ceiling……...FUUUUUTTUUURRE!!!");
                room1.placeItem(new Container(2, "Pressure Plate"));
                try {
                    ((Container)room1.getItemByName("Pressure Plate")).addItem(new Item("RTX 2080 TI"));
                } catch (ContainerFullException e) {
                    e.printStackTrace();
                }
                room1.addEvent(new GameEvent("LOOK", () -> {
                    Container pressurePlate = ((Container)room1.getItemByName("Pressure Plate"));
                    try {
                        return "On the table in front of you, you see a " + pressurePlate.getFirstItem().getName() + ". It is resting on a plate that looks shinier than the rest.";
                    } catch (ContainerEmptyException e) {
                        return ""; //TODO: ending
                    }
                }));
                room1.addEvent(new GameEvent("DROP", () -> {
                    String tempName = "";
                    try{
                        Item tempItem = hand.getFirstItem();
                        room1.placeItem(tempItem);
                        tempName = tempItem.getName();
                        hand.removeItem(tempItem);
                        return "You drop the " + tempName + ".";
                    } catch(ContainerEmptyException e){
                        return "There is nothing in your hand to drop.";
                    } catch(ItemNotFoundException e){
                        return "You drop the " + tempName + ".";
                    }
                }));
                room1.addEvent(new GameEvent("PLACE", () -> {
                    if(hand.getAllItem().size() == 0){
                        return "There is nothing in your hand to drop.";
                    } else {
                        Item tempItem = hand.getAllItem().get(0);
                        room1.placeItem(tempItem);
                        String tempName = tempItem.getName();
                        try {
                            hand.removeItem(tempItem);
                        } catch (ItemNotFoundException | ContainerEmptyException ignored) {
                        }
                        return "You place the " + tempName + " on the table next to the |RTX 2080 TI|";
                    }
                }));
                room1.addEvent(new GameEvent("PICK UP RTX 2080 TI", () -> {
                    Container pressurePlate = (Container) room1.getItemByName("Pressure Plate");
                    try{
                        pressurePlate.getItemByName("Weight");
                        try {
                            hand.addItem(pressurePlate.getItemByName("RTX 2080 TI"));
                            pressurePlate.removeItem(pressurePlate.getItemByName("RTX 2080 TI"));
                        } catch(ContainerFullException e){
                            return "You are already carrying the " + hand.getAllItem().get(0).getName() + ".";
                        } catch(ItemNotFoundException e){
                            return "The |RTX 2080 TI| is not on the table.";
                        }
                        return "You picked up the |RTX 2080 TI|";
                    } catch(ItemNotFoundException | ContainerEmptyException e) { //if the weight isn't on the pressure plate
                        return ""; //TODO: ending
                    }
                }));
                room1.addEvent(new GameEvent("GO EAST", () -> {
                    moveInDirection(Exit.EAST);
                    return "";
                }));
        
                //room2
                room2.addExit(new Exit(Exit.WEST, room1));
                room2.addExit(new Exit(Exit.SOUTH, room5));
                
                //room3
                room3.addExit(new Exit(Exit.SOUTH, room6));
                
                //room4
                room4.addExit(new Exit(Exit.SOUTH, room7));
                room4.addExit(new Exit(Exit.EAST, room5));
                
                //room5
                room5.addExit(new Exit(Exit.NORTH, room2));
                room5.addExit(new Exit(Exit.WEST, room4));
                room5.addExit(new Exit(Exit.EAST, room6));
                room5.addExit(new Exit(Exit.SOUTH, room8));
                
                //room6
                room6.addExit(new Exit(Exit.NORTH, room3));
                room6.addExit(new Exit(Exit.WEST, room5));
                room6.addExit(new Exit(Exit.SOUTH, room9));
                
                //room7
                room7.addExit(new Exit(Exit.NORTH, room4));
                
                //room8
                room8.addExit(new Exit(Exit.NORTH, room5));
                room8.setDescription("You enter the warehouse where the computer parts are stored.\n" +
                        "The feel is dark and musty, as if it is only cleaned every few weeks.\n" +
                        "There is a toolbox sitting in the corner that contains various tools, one of which is a screwdriver.\n" +
                        "Your only option is north through a door into a brighter room.");
        
                //room9
                room9.addExit(new Exit(Exit.NORTH, room6));

                rooms.add(room1);
                rooms.add(room2);
                rooms.add(room3);
                rooms.add(room4);
                rooms.add(room5);
                rooms.add(room6);
                rooms.add(room7);
                rooms.add(room8);
                rooms.add(room9);
    }

    public String processCommand(String data) {
        GameEvent event = currentRoom.getEvent(data);
        if(event != null){
            return event.doAction();
        } else {
            return "Sorry, you can't do that right now. Try something different.";
        }
    }

    public void end() {
    }
}
