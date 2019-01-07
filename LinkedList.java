// CLASS: LinkedList
//
// Author: Gareth Wiebe
//
// REMARKS: Simple Linked list implementation
//
//-----------------------------------------

class LinkedList
{
    private Node top;

    /**
    * Class constructor
    */
    public LinkedList()
    {
        top = null;
    }

    /**
    * Get the data of the list as a string
    *
    * @return the Data in the list
    */
    public String print()
    {
        Node temp = top;
        String output = "";

        while (temp != null)
        {
            Data tempData = temp.getData();
            if (tempData != null)
            {
                output += tempData.toString() + "\n";
            }
            temp = temp.getNext();
        }
        if (output.equals("")) output = "No Items\n";
        return output;
    }

    /**
    * Insert into the list
    *
    * @param  newItem The item to add to the list
    */
    public void insert(Data newItem) 
    {
        Node newNode = new Node (newItem, top);
        top = newNode;
    }

    /**
    * Search for a data item by id
    *
    * @param  key  the key to search for
    * @return the Data item or null on fail
    */
    public Data search(String key)
    {
        Data output = null;
        Node temp = top;
        Boolean done = false;

        while (temp != null && !done)
        {
            if (temp.hasKey(key))
            {
                output = temp.getData();
                done = true;
            }

            else temp = temp.getNext();
        }

        return output;
    }

    /**
    * Check if the list is empty
    *
    * @return true if list is empty, false otherwise
    */
    public Boolean isEmpty()
    {
        return top == null;
    }

    /**
    * Accessor for top
    *
    * @return the top Node
    */
    public Node getTop()
    {
        return top;
    }

    /**
    * Mutator for top
    *
    * @param  top  Node to set top to
    */
    public void setTop(Node top)
    {
        this.top = top;
    }
} // LinkedList