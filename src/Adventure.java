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


                 /**
                 * 
                 * ROOM 1
                 * 
                 */
                room1.addExit(new Exit(Exit.EAST, room2));
                room1.setDescription("As you enter, you see over-the-top reflections on every surface, real-time god rays shine down from the ceiling……...FUUUUUTTUUURRE!!!");
                room1.placeItem(new Container(2, "Pressure Plate"));
                try {
                    ((Container)room1.getItemByName("Pressure Plate")).addItem(new Item("NVIDIA RTX 2080 TI"));
                } catch (ContainerFullException e) {
                    e.printStackTrace();
                } catch (ItemNotFoundException e) {
                    e.printStackTrace();
                }
                room1.addEvent(new GameEvent("PICK UP", () -> {
                    try{
                        
                    }
                }));

                room1.addEvent(new GameEvent("LOOK", () -> {
                    try {
                        Container pressurePlate = ((Container)room1.getItemByName("Pressure Plate"));
                        return "On the table in front of you, you see a " + pressurePlate.getFirstItem().getName() + ". It is resting on a plate that looks shinier than the rest of the table.";
                    } catch (ContainerEmptyException e) {
                        return ""; //TODO: ending
                    } catch (ItemNotFoundException e){
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
                        return "You place the " + tempName + " on the table next to the |NVIDIA RTX 2080 TI|";
                    }
                }));
                room1.addEvent(new GameEvent("PICK UP NVIDIA RTX 2080 TI", () -> {
                    try{
                        Container pressurePlate = (Container) room1.getItemByName("Pressure Plate");
                        pressurePlate.getItemByName("WEIGHT");
                        try {
                            hand.addItem(pressurePlate.getItemByName("NVIDIA RTX 2080 TI"));
                            pressurePlate.removeItem(pressurePlate.getItemByName("NVIDIA RTX 2080 TI"));
                        } catch(ContainerFullException e){
                            return "You are already carrying the " + hand.getAllItem().get(0).getName() + ".";
                        } catch(ItemNotFoundException e){
                            return "The |NVIDIA RTX 2080 TI| is not on the table.";
                        }
                        return "You picked up the |NVIDIA RTX 2080 TI|";
                    } catch(ContainerEmptyException e) { //if nothing is on the pressure plate
                        return ""; //TODO: ending
                    } catch(ItemNotFoundException e){ //if the weight isn't on the pressure plate
                        return ""; //TODO: ending
                    }
                }));
                room1.addEvent(new GameEvent("GO EAST", () -> {
                    return moveInDirection(Exit.EAST);
                }));
        

                /**
                 * 
                 * ROOM 2
                 * 
                 */
                room2.addExit(new Exit(Exit.SOUTH, room5));
                room2.setDescription("As you walk through the door, you see your reflection on the wall, why did they make everything so darn shiny!?! \n" +
                "As you move through the world starts to feel even slower, but at least it looks pretty. \n" +
                "You have the option to GET a AMD RX 580. \n" +
                "Your movement options lie to the WEST and to the SOUTH.");
                
                room2.placeItem(new Item("AMD RX 580")); //place the items in the room, or in a container in the room
                room2.placeItem(new Item("NVIDIA GTX 650"));
                room2.placeItem(new Item("WEIGHT"));
                room2.addEvent(new GameEvent("LOOK", () -> {
                    String response = "";
                    Vector<Item> itemList = room2.getAllItem();  
                    for (Item item : itemList) {
                        response += "There is a |" + item.getName() + "| in the room";
                    }
                    return response;
                }));
                room2.addEvent(new GameEvent("DROP", () -> {
                    String tempName = "";
                    try{
                        Item tempItem = hand.getFirstItem();
                        room2.placeItem(tempItem);
                        tempName = tempItem.getName();
                        hand.removeItem(tempItem);
                        return "You drop the " + tempName + ".";
                    } catch(ContainerEmptyException e){
                        return "There is nothing in your hand to drop.";
                    } catch(ItemNotFoundException e){
                        return "You drop the " + tempName + ".";
                    }
                }));
                room2.addEvent(new GameEvent("PICK UP AMD RX 580", () -> {
                    try {
                        hand.addItem(room2.getItemByName("AMD RX 580"));
                    } catch(ContainerFullException e){
                        return "You are already carrying the " + hand.getAllItem().get(0).getName() + ".";
                    } catch(ItemNotFoundException e){
                        return "The |AMD RX 580| is not avialable for pick up";
                    }
                    return "You picked up the |AMD RX 580|";
                }));
                room2.addEvent(new GameEvent("PICK UP NVIDIA GTX 650", () -> {
                    try {
                        hand.addItem(room2.getItemByName("NVIDIA GTX 650"));
                    } catch(ContainerFullException e){
                        return "You are already carrying the " + hand.getAllItem().get(0).getName() + ".";
                    } catch(ItemNotFoundException e){
                        return "The |NVIDIA GTX 650| is not on the table.";
                    }
                    return "You picked up the |NVIDIA GTX 650|";
                }));
                room2.addEvent(new GameEvent("GO WEST", () -> {
                    return moveInDirection(Exit.WEST);
                }));
                room2.addEvent(new GameEvent("GO SOUTH", () -> {
                    return moveInDirection(Exit.SOUTH);
                }));
                

                 /**
                 * 
                 * ROOM 3
                 * 
                 */
                room3.addExit(new Exit(Exit.SOUTH, room6));
                room3.setDescription("You enter a room filled with motherboards and power supplies. \n" +
                "Nothing awaits to surprise you, however, there is a piece of paper with something written on it. \n" +
                "The paper reads 1267 and points to a . \n" +
                "Your movement options are to the SOUTH.");
                
                room3.placeItem(new Item("Motherboard")); //place the items in the room, or in a container in the room
                room3.placeItem(new Item("Power Supply"));
                room3.addEvent(new GameEvent("DROP", () -> {
                    String tempName = "";
                    try{
                        Item tempItem = hand.getFirstItem();
                        room3.placeItem(tempItem);
                        tempName = tempItem.getName();
                        hand.removeItem(tempItem);
                        return "You drop the " + tempName + ".";
                    } catch(ContainerEmptyException e){
                        return "There is nothing in your hand to drop.";
                    } catch(ItemNotFoundException e){
                        return "You drop the " + tempName + ".";
                    }
                }));
                room3.addEvent(new GameEvent("PICK UP MOTHERBOARD", () -> {
                    try {
                        hand.addItem(room3.getItemByName("Motherboard"));
                    } catch(ContainerFullException e){
                        return "You are already carrying the " + hand.getAllItem().get(0).getName() + ".";
                    } catch(ItemNotFoundException e){
                        return "The Motherboard is not avialable for pick up";
                    }
                    return "You picked up the Motherboard";
                }));
                room3.addEvent(new GameEvent("PICK UP POWER SUPPLY", () -> {
                    try {
                        hand.addItem(room3.getItemByName("Power Supply"));
                    } catch(ContainerFullException e){
                        return "You are already carrying the " + hand.getAllItem().get(0).getName() + ".";
                    } catch(ItemNotFoundException e){
                        return "The Power Supply is not avialable for pickup.";
                    }
                    return "You picked up the Power Supply";
                }));
                room3.addEvent(new GameEvent("GO SOUTH", () -> {
                    return moveInDirection(Exit.SOUTH);
                }));
                
                    
                 /**
                 * 
                 * ROOM 4
                 * 
                 */
                room4.addExit(new Exit(Exit.SOUTH, room7));
                room4.addExit(new Exit(Exit.EAST, room5));
                room4.setDescription("As you enter you see stacks of boxes that contain what looks to be CPUs. \n" +
                "However, they do not appear to be of high value and seem to be all the same.");
                
                room4.placeItem(new Item("Intel pentium CPU")); //place the items in the room, or in a container in the room
                room4.addEvent(new GameEvent("DROP", () -> {
                    String tempName = "";
                    try{
                        Item tempItem = hand.getFirstItem();
                        room4.placeItem(tempItem);
                        tempName = tempItem.getName();
                        hand.removeItem(tempItem);
                        return "You drop the " + tempName + ".";
                    } catch(ContainerEmptyException e){
                        return "There is nothing in your hand to drop.";
                    } catch(ItemNotFoundException e){
                        return "You drop the " + tempName + ".";
                    }
                }));
                room4.addEvent(new GameEvent("PICK UP Intel pentium CPU", () -> {
                    try {
                        hand.addItem(room4.getItemByName("Intel pentium CPU"));
                    } catch(ContainerFullException e){
                        return "You are already carrying the " + hand.getAllItem().get(0).getName() + ".";
                    } catch(ItemNotFoundException e){
                        return "The Intel pentium CPU is not avialable for pick up";
                    }
                    return "You picked up the Intel pentium CPU";
                }));
                room4.addEvent(new GameEvent("GO SOUTH", () -> {
                    return moveInDirection(Exit.SOUTH);
                }));
                room4.addEvent(new GameEvent("GO EAST", () -> {
                    return moveInDirection(Exit.EAST);
                }));
                

                 /**
                 * 
                 * ROOM 5
                 * 
                 */
                room5.addExit(new Exit(Exit.NORTH, room2));
                room5.addExit(new Exit(Exit.WEST, room4));
                room5.addExit(new Exit(Exit.EAST, room6));
                room5.addExit(new Exit(Exit.SOUTH, room8));
                room5.setDescription("You feel blinded by a ridiculous amount of rgb lights, who designed this place? \n" +
                "To the side you see a cart, looks like a good place to put computer parts! \n" + "There are no parts in this room but there is a weight for some reason.");
                room5.addEvent(new GameEvent("DROP", () -> {
                    String tempName = "";
                    try{
                        Item tempItem = hand.getFirstItem();
                        room5.placeItem(tempItem);
                        tempName = tempItem.getName();
                        hand.removeItem(tempItem);
                        return "You drop the " + tempName + ".";
                    } catch(ContainerEmptyException e){
                        return "There is nothing in your hand to drop.";
                    } catch(ItemNotFoundException e){
                        return "You drop the " + tempName + ".";
                    }
                }));
                room5.addEvent(new GameEvent("GO NORTH", () -> {
                    return moveInDirection(Exit.NORTH);
                }));
                room5.addEvent(new GameEvent("GO WEST", () -> {
                    return moveInDirection(Exit.WEST);
                }));
                room5.addEvent(new GameEvent("GO SOUTH", () -> {
                    return moveInDirection(Exit.SOUTH);
                }));
                room5.addEvent(new GameEvent("GO EAST", () -> {
                    return moveInDirection(Exit.EAST);
                }));
                

                 /**
                 * 
                 * ROOM 6
                 * 
                 */
                room6.addExit(new Exit(Exit.NORTH, room3));
                room6.addExit(new Exit(Exit.WEST, room5));
                room6.addExit(new Exit(Exit.SOUTH, room9));
                room6.setDescription("You walk into a room with towering ceilings, along the walls are massive shelves lined with boxes big and small. \n" +
                "After some searching you find one labeled case and another called ram.");
                
                room6.placeItem(new Item("RAM")); //place the items in the room, or in a container in the room
                room6.placeItem(new Item("Case"));
                room6.addEvent(new GameEvent("DROP", () -> {
                    String tempName = "";
                    try{
                        Item tempItem = hand.getFirstItem();
                        room6.placeItem(tempItem);
                        tempName = tempItem.getName();
                        hand.removeItem(tempItem);
                        return "You drop the " + tempName + ".";
                    } catch(ContainerEmptyException e){
                        return "There is nothing in your hand to drop.";
                    } catch(ItemNotFoundException e){
                        return "You drop the " + tempName + ".";
                    }
                }));
                room6.addEvent(new GameEvent("PICK UP RAM", () -> {
                    try {
                        hand.addItem(room6.getItemByName("RAM"));
                    } catch(ContainerFullException e){
                        return "You are already carrying the " + hand.getAllItem().get(0).getName() + ".";
                    } catch(ItemNotFoundException e){
                        return "The RAM is not avialable for pick up";
                    }
                    return "You picked up the RAM";
                }));
                room6.addEvent(new GameEvent("PICK UP Case", () -> {
                    try {
                        hand.addItem(room6.getItemByName("Case"));
                    } catch(ContainerFullException e){
                        return "You are already carrying the " + hand.getAllItem().get(0).getName() + ".";
                    } catch(ItemNotFoundException e){
                        return "The Case is not avialable for pick up";
                    }
                    return "You picked up the Case";
                }));
                room6.addEvent(new GameEvent("GO SOUTH", () -> {
                    return moveInDirection(Exit.SOUTH);
                }));
                room6.addEvent(new GameEvent("GO WEST", () -> {
                    return moveInDirection(Exit.WEST);
                }));
                room6.addEvent(new GameEvent("GO NORTH", () -> {
                    return moveInDirection(Exit.NORTH);
                }));
                

                 /**
                 * 
                 * ROOM 7
                 * 
                 */
                room7.addExit(new Exit(Exit.NORTH, room4));
                room7.setDescription("The room is lit by a dim blue glow, the room smells stale as though no engineering has happened here in quite a while \n" +
                "On a shelf you see a box that says Intel *Strikethough* Skylake, kaby lake...Coffee Lake 9900k. Guess this will do.");
                
                room7.placeItem(new Item("Intel 9900K")); //place the items in the room, or in a container in the room
                room7.addEvent(new GameEvent("DROP", () -> {
                    String tempName = "";
                    try{
                        Item tempItem = hand.getFirstItem();
                        room7.placeItem(tempItem);
                        tempName = tempItem.getName();
                        hand.removeItem(tempItem);
                        return "You drop the " + tempName + ".";
                    } catch(ContainerEmptyException e){
                        return "There is nothing in your hand to drop.";
                    } catch(ItemNotFoundException e){
                        return "You drop the " + tempName + ".";
                    }
                }));
                room7.addEvent(new GameEvent("PICK UP Intel 9900K", () -> {
                    try {
                        hand.addItem(room7.getItemByName("Intel 9900K"));
                    } catch(ContainerFullException e){
                        return "You are already carrying the " + hand.getAllItem().get(0).getName() + ".";
                    } catch(ItemNotFoundException e){
                        return "The Intel 9900K is not avialable for pick up";
                    }
                    return "You picked up the Intel 9900K";
                }));
                room7.addEvent(new GameEvent("GO NORTH", () -> {
                    moveInDirection(Exit.NORTH);
                    return "";
                }));
                

                 /**
                 * 
                 * ROOM 8
                 * 
                 */
                room8.addExit(new Exit(Exit.NORTH, room5));
                room8.setDescription("You enter the warehouse where the computer parts are stored.\n" +
                        "The feel is dark and musty, as if it is only cleaned every few weeks.\n" +
                        "There is a toolbox sitting in the corner that contains various tools, one of which is a screwdriver.\n" +
                        "Your only option is north through a door into a brighter room.");
                room8.addEvent(new GameEvent("GO NORTH", () -> {
                    return moveInDirection(Exit.NORTH);
                 }));
        

                 /**
                 * 
                 * ROOM 9
                 * 
                 */
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


//happy 500 boys