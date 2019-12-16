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
                    Option pickUpItems = new Option("PICK UP", generateItemList(room1.getAllItem()));
                    Vector<GameEvent> tempEvents = generateItemEventsList(room1.getAllItem(), 0, room1);
                    for (GameEvent event: tempEvents) {
                        pickUpItems.addEvent(event);
                    }
                    return moveToRoom(pickUpItems);
                }));
                room1.addEvent(new GameEvent("LOOK", () -> room1.getAllItemsString()));
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
                room2.addEvent(new GameEvent("PICK UP", () -> {
                    Option pickUpItems = new Option("PICK UP", generateItemList(room2.getAllItem()));
                    Vector<GameEvent> tempEvents = generateItemEventsList(room2.getAllItem(), 0, room2);
                    for (GameEvent event: tempEvents) {
                        pickUpItems.addEvent(event);
                    }
                    return moveToRoom(pickUpItems);
                }));
                room2.addEvent(new GameEvent("LOOK", () -> room2.getAllItemsString()));
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
                room3.addEvent(new GameEvent("PICK UP", () -> {
                    Option pickUpItems = new Option("PICK UP", generateItemList(room3.getAllItem()));
                    Vector<GameEvent> tempEvents = generateItemEventsList(room3.getAllItem(), 0, room3);
                    for (GameEvent event: tempEvents) {
                        pickUpItems.addEvent(event);
                    }
                    return moveToRoom(pickUpItems);
                }));
                room3.addEvent(new GameEvent("LOOK", () -> room3.getAllItemsString()));
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
                room4.addEvent(new GameEvent("PICK UP", () -> {
                    Option pickUpItems = new Option("PICK UP", generateItemList(room4.getAllItem()));
                    Vector<GameEvent> tempEvents = generateItemEventsList(room4.getAllItem(), 0, room4);
                    for (GameEvent event: tempEvents) {
                        pickUpItems.addEvent(event);
                    }
                    return moveToRoom(pickUpItems);
                }));
                room4.addEvent(new GameEvent("LOOK", () -> room4.getAllItemsString()));
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
                room5.addEvent(new GameEvent("PICK UP", () -> {
                    Option pickUpItems = new Option("PICK UP", generateItemList(room5.getAllItem()));
                    Vector<GameEvent> tempEvents = generateItemEventsList(room5.getAllItem(), 0, room5);
                    for (GameEvent event: tempEvents) {
                        pickUpItems.addEvent(event);
                    }
                    return moveToRoom(pickUpItems);
                }));
                room5.addEvent(new GameEvent("LOOK", () -> room5.getAllItemsString()));
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
                room6.addEvent(new GameEvent("PICK UP", () -> {
                    Option pickUpItems = new Option("PICK UP", generateItemList(room6.getAllItem()));
                    Vector<GameEvent> tempEvents = generateItemEventsList(room6.getAllItem(), 0, room6);
                    for (GameEvent event: tempEvents) {
                        pickUpItems.addEvent(event);
                    }
                    return moveToRoom(pickUpItems);
                }));
                room6.addEvent(new GameEvent("LOOK", () -> room6.getAllItemsString()));
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
                room7.addEvent(new GameEvent("PICK UP", () -> {
                    Option pickUpItems = new Option("PICK UP", generateItemList(room7.getAllItem()));
                    Vector<GameEvent> tempEvents = generateItemEventsList(room7.getAllItem(), 0, room7);
                    for (GameEvent event: tempEvents) {
                        pickUpItems.addEvent(event);
                    }
                    return moveToRoom(pickUpItems);
                }));
                room7.addEvent(new GameEvent("LOOK", () -> room7.getAllItemsString()));
                room7.addEvent(new GameEvent("GO NORTH", () -> {
                    return moveInDirection(Exit.NORTH);
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
                room8.placeItem(new Item("SCREWDRIVER"));
                room8.addEvent(new GameEvent("PICK UP", () -> {
                    Option pickUpItems = new Option("PICK UP", generateItemList(room8.getAllItem()));
                    Vector<GameEvent> tempEvents = generateItemEventsList(room8.getAllItem(), 0, room8);
                    for (GameEvent event: tempEvents) {
                        pickUpItems.addEvent(event);
                    }
                    return moveToRoom(pickUpItems);
                }));
                room8.addEvent(new GameEvent("LOOK", () -> room8.getAllItemsString()));
                room8.addEvent(new GameEvent("GO NORTH", () -> {
                    return moveInDirection(Exit.NORTH);
                 }));
        

                 /**
                 * 
                 * ROOM 9
                 * 
                 */
                room9.addExit(new Exit(Exit.NORTH, room6));
                rooms = new Vector<>();
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

    public String generateItemList(Vector<Item> items){
        String result = "";
        Vector<String> itemOptions = generateItemStringList("ROOM", items, 0);
        for (String opt: itemOptions) {
            result += opt;
        }
        return result;
    }
    public Vector<String> generateItemStringList(String locationName, Vector<Item> items, int startFrom){
        int runningCount = startFrom;
        Vector<String> list = new Vector<>();
        for (Item item: items) {
            if(item instanceof Container){
                Vector<String> tempList = generateItemStringList(item.getName(), ((Container)item).getAllItem(), runningCount);
                runningCount += tempList.size();
                list.addAll(tempList);
            } else {
                list.add((runningCount + 1) + ".) There is a |" + item.getName() + "| in |" + locationName + "|.\n");
                runningCount++;
            }
        }
        return list;
    }
    public Vector<GameEvent> generateItemEventsList(Vector<Item> items, int startFrom, Location room){
        int runningCount = startFrom;
        Vector<GameEvent> events = new Vector<>();
        for (Item item: items) {
            if(item instanceof Container){
                Vector<GameEvent> tempList = generateItemEventsList(((Container)item).getAllItem(), runningCount, room);
                runningCount += tempList.size();
                events.addAll(tempList);
            } else {
                events.add(new GameEvent(Integer.toString(runningCount), () -> {
                    if(hand.isEmpty()){
                        try {
                            moveToRoom(room);
                            return "You are already carrying the " + hand.getFirstItem() + ".";
                        } catch (ContainerEmptyException e) {
                            try {
                                hand.addItem(item);
                                items.remove(item);
                                moveToRoom(room);
                                return "You got the | " + item.getName() + " |.";
                            } catch (ContainerFullException ex) {
                                ex.printStackTrace();
                                moveToRoom(room);
                                return " ";
                            }
                        }
                    } else {
                        try {
                            hand.addItem(item);
                        } catch (ContainerFullException e) {
                            e.printStackTrace();
                            moveToRoom(room);
                            return " ";
                        }
                        items.remove(item);
                        moveToRoom(room);
                        return "You got the | " + item.getName() + " |.";
                    }
                }));
                runningCount++;
            }
        }
        return events;
    }

}


//happy 500 boys