// CLASS: Data
//
// Author: Gareth Wiebe, 7789614
//
// REMARKS: Abstract class that provides
// common functionality for data
//
//-----------------------------------------

abstract class Data
{
    private String id;

    /**
    * Class constructor
    *
    * @param  id   the ID
    */
    Data(String id)
    {
        this.id = id;
    }

    /**
    * Forces a toString method
    *
    * @return the class as a string
    */
    abstract public String toString();
    
    /**
    * Compares id to a key
    *
    * @param  key the string to compare
    * @return true if id matches key, false otherwise
    */
    public Boolean hasKey(String key)
    {
        return id.equals(key);
    }

    /**
    * Accessor for id
    *
    * @return this id
    */
    public String getId()
    {
        return this.id;
    }

    /**
    * Compares to Data items by id
    *
    * @param  item the item code
    * @return a negative integer, zero, or a positive integer 
    * as this object is less than, equal to, or greater than the specified object.
    */
    public int compareTo(Data item)
    {
        return id.compareTo(item.getId());
    }

    /**
    * Compares to Data items by id, overloaded.
    *
    * @param  item the item code
    * @return a negative integer, zero, or a positive integer 
    * as this object is less than, equal to, or greater than the specified object.
    */
    public int compareTo(String id)
    {
        return this.id.compareTo(id);
    }
}