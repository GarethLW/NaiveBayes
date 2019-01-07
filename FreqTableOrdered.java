import java.util.*;

public class FreqTableOrdered extends FreqTable
{
    private OrderedLinkedList freqTable;

    public FreqTableOrdered()
    {
        super();
        freqTable = new OrderedLinkedList();
    }

    // adds a non spam attribute to the freqtable
    public void addAttribute(String attribute, boolean spam)
    {
        // search for attribute in the table
        RowData item = getAttribute(attribute);
        if (item != null)
        {
            if(!spam) 
            {
                item.incHamCount();
                incTotalHam();
            }
            else if (spam) 
            {
                item.incSpamCount();
                incTotalSpam();
            }
        }

        // didn't find the attribute so add it
        else
        {
            incTotalUWords();
            if(!spam) 
            {
                orderedAdd(new RowData(attribute, 1, 0));
                incTotalHam();
            }
            else if(spam) 
            {
                orderedAdd(new RowData(attribute, 0, 1));
                incTotalSpam();
            }
        }
    }

    public void orderedAdd(RowData newItem)
    {
        freqTable.insert(newItem);
    }

    // returns the rowdata item matching the attribute
    // returns null if not found
    public RowData getAttribute(String attribute)
    {
        RowData output = null;
        Data temp = null;
        temp = freqTable.search(attribute);
        
        if(temp instanceof RowData)
        {
            output = (RowData)temp;
        }

        return output;
    }

    public String tableView()
    {
        return freqTable.print();
    }
}