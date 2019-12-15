//
//
// Exit - represents an exit to a location
// 
// Last modification date : October 07, 1997
//
public class Exit 
{
	// Numerical codes
	public static final int UNDEFINED = 0;
	public static final int NORTH = 1;
	public static final int SOUTH = 2;
	public static final int EAST  = 3;
	public static final int WEST  = 4;
	public static final int UP    = 5;
	public static final int DOWN  = 6;
	public static final int NORTHEAST = 7;
	public static final int NORTHWEST = 8;
	public static final int SOUTHEAST = 9;
	public static final int SOUTHWEST = 10;
	public static final int IN = 11;
	public static final int OUT = 12;

	// String codes	
	public static final String[] dirName = 
	{ 
		"UNDEFINED",
		"NORTH",
		"SOUTH",
		"EAST",
		"WEST",
		"UP",
		"DOWN",
		"NORTHEAST",
		"NORTHWEST",
		"SOUTHEAST",
		"SOUTHWEST",
		"IN",
		"OUT"
	};

	public static final String[] shortDirName = 
	{
		"NULL",
		"N",
		"S",
		"E",
		"W",
		"U",
		"D",
		"NE",
		"NW",
		"SE",
		"SW",
		"I",
		"O"		
	};

	// Member variables
	private Location leadsTo = null;
	private int direction;

	// Full name of direction eg SOUTHEAST
	private String directionName;

	// Shortened version of direction eg SE
	private String shortDirectionName;

	// Default constructor
	public Exit()
	{
		direction = Exit.UNDEFINED;
		leadsTo = null;
		directionName = dirName[UNDEFINED];
		shortDirectionName = shortDirName[UNDEFINED];
	}

	// Full constructor
	public Exit( int dir, Location lead )
	{
		direction = dir;

		// Assign direction names
		if (dir <= dirName.length )
			directionName = dirName[direction];
		if (dir <= shortDirName.length )
			shortDirectionName = shortDirName[direction];

		// Assign location
		leadsTo = lead;
	}

	// toString method
	public String toString()
	{
		return directionName;
	}

	// Assigns direction name
	public void setDirectionName( String dirname )
	{
		directionName = dirname;
	}

	// Returns direction name
	public String getDirectionName()
	{
		return directionName;
	}

	// Assigns short direction name
	public void setShortDirectionName ( String shortName )
	{
		shortDirectionName = shortName;
	}

	// Returns short direction name
	public String getShortDirectionName ()
	{
		return shortDirectionName;
	}

	// Assigns location
	public void setLeadsTo ( Location lead )
	{
		leadsTo = lead;
	}

	// Returns location
	public Location getLeadsTo (  )
	{
		return leadsTo;
	}

}

