// CLASS: Node
//
// Author: Gareth Wiebe
//
// REMARKS: Contains a link to another node
// and a Data item
//
//-----------------------------------------

class Node
{
    Node next;
    Data item;

    /**
    * Class constructor
    *
    * @param  item  item for this Node to hold
    * @param  link  link for this Node
    */
    Node (Data item, Node link)
    {
        next = link;
        this.item = item;
    }

    /**
    * Sets the next link
    *
    * @param  next  the next node to link to
    */
    public void setNext(Node next)
    {
        this.next = next;
    }

    /**
    * Accessor for the link Node
    *
    * @return the next node
    */
    public Node getNext()
    {
        return next;
    }

    /**
    * Accessor for the Data
    *
    * @return returns the data in this node
    */
    public Data getData()
    {
        return item;
    }

    /**
    * Check if this node has a given key
    *
    * @param  key  the key to compare the data to
    * @return True if matching key, false otherwise
    */
    public Boolean hasKey(String key)
    {
        return item.hasKey(key);
    }
} // Node