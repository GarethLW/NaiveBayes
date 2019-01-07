import java.util.*;

public class FreqTable
{
    private ArrayList<RowData> freqTable;
    private int totalHam;
    private int totalSpam;
    private int totalUWords;
    private final int BUFFER = 131072;

    public FreqTable()
    {
        freqTable = new ArrayList<RowData>(BUFFER);
        totalHam = 0;
        totalSpam = 0;
        totalUWords = 0;
    }

    // adds an attribute to the freqtable.
    // increments the given attribute in the spam column
    // if spam is true, else increments it in the non spam
    // column. If the attribute does not exist it adds it to
    // the table and increments it to 1 in the appropriate
    // column
    public void addAttribute(String attribute, boolean spam)
    {
        // search for attribute in the table
        RowData item = getAttribute(attribute);
        if (item != null)
        {
            if(!spam) 
            {
                item.incHamCount();
                totalHam++;
            }
            else if (spam) 
            {
                item.incSpamCount();
                totalSpam++;
            }
        }

        // didn't find the attribute so add it
        else
        {
            totalUWords++;
            if(!spam) 
            {
                add(new RowData(attribute, 1, 0));
                totalHam++;
            }
            else if(spam) 
            {
                add(new RowData(attribute, 0, 1));
                totalSpam++;
            }
        }
    }

    public void add(RowData newItem)
    {
        freqTable.add(newItem);
    }

    // returns the rowdata item matching the attribute
    // returns null if not found
    public RowData getAttribute(String attribute)
    {
        RowData output = null;
        boolean done = false;

        for(RowData item: freqTable)
        {
            if (item.getAttribute().equals(attribute))
            {
                output = item;
                done = true;
            }

            if (done) break;
        }

        return output;
    }

    public String tableView()
    {
        String output = "Name:\tSpam:\tHam:\n";
        for(RowData item: freqTable)
        {
            output += item.getAttribute() + "\t" + item.getSpamCount() + "\t" + item.getHamCount() +"\n";
        }

        return output;
    }

    public int getTotalHam()
    {
        return totalHam;
    }

    public int getTotalSpam()
    {
        return totalSpam;
    }

    public int getTotalUWords()
    {
        return totalUWords;
    }

    public void incTotalHam()
    {
        totalHam++;
    }

    public void incTotalSpam()
    {
        totalSpam++;
    }

    public void incTotalUWords()
    {
        totalUWords++;
    }
}