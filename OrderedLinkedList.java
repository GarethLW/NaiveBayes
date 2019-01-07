// CLASS: OrderedLinkedList
//
// Author: Gareth Wiebe
//
// REMARKS: Maintains an ordered linkedlist
// by extending a linkedlist
//
//-----------------------------------------

class OrderedLinkedList extends LinkedList
{

    /**
    * Insert item into the list, overrides
    * LinkedList insert. Maintains order.
    *
    * @param  newItem  new item to insert into the list
    */
    public void insert(Data newItem) 
    {
        Node newNode = null;
        Node temp = getTop();
        Node last = getTop();

        Boolean done = false;

        while (temp != null && !done)
        {
            // if this item belongs behind temp
            if ((temp.getData()).compareTo(newItem) > 0) done = true;

            else
            {
                last = temp;
                temp = temp.getNext();
            }
        }

        newNode = new Node(newItem, temp);

        // if this is the first item or this item belongs on top
        if (temp == getTop()) setTop(newNode);

        // normal case
        else if (last != null) last.setNext(newNode);

        else System.out.println("Insert error\n");
    }

    /**
    * Search for a data item by id, overrides super
    * class search.
    *
    * @param  key  the key to search for
    * @return the Data item or null on fail
    */
    public Data search(String key)
    {
        Data output = null;
        Node temp = getTop();
        Boolean done = false;

        while (temp != null && !done)
        {

            if (temp.hasKey(key))
            {
                output = temp.getData();
                done = true;
            }

            else 
            {
                if ((temp.getData()).compareTo(key) > 0) done = true;
                temp = temp.getNext();
            }
        }

        return output;
    }
}