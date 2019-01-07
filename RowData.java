public class RowData extends Data
{
    private String attribute;
    private int hamCount;
    private int spamCount;

    public RowData(String attribute, int hamCount, int spamCount)
    {
        super(attribute);
        this.attribute = attribute;
        this.hamCount = hamCount;
        this.spamCount = spamCount;
    }

    public String getAttribute()
    {
        return attribute;
    }

    public int getHamCount()
    {
        return hamCount;
    }

    public int getSpamCount()
    {
        return spamCount;
    }

    public void incHamCount()
    {
        hamCount++;
    }

    public void incSpamCount()
    {
        spamCount++;
    }

    // Overrides abstract superclass compareTo
    public int compareTo(Data item)
    {
        RowData temp = null;
        if(item instanceof RowData)
        {
            temp = (RowData)item;
            return (this.getHamCount() + this.getSpamCount() ) - ( temp.getHamCount() + temp.getSpamCount() );
        }

        return -1;
    }

    // Overrides abstract superclass compareTo
    public int compareTo(String id)
    {
        int output = 0;
        if (id.equals(attribute)) output = 1;
        return output;
    }

    // Overrides abstract superclass toString
    public String toString()
    {
        return attribute + "ham Count: " + hamCount + " spam Count: " + spamCount;
    }
}