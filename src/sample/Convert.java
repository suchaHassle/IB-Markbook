/**
 * This class returns converted marks based off of the boundaries given by
 * the teacher and the corresponding ontario marks
 */
package sample;

public class Convert
{
    public double[] markBoundaries;
    public int[] ontarioMarks = {};

    /**
     * Constructs a new conversion for each classroom
     *
     * @param boundaries
     * @param ontarioMarks
     */
    public Convert (double[] boundaries, int[] ontarioMarks)
    {
        markBoundaries = new double [boundaries.length];
        this.ontarioMarks = new int [ontarioMarks.length];

        for (int i = 0; i < boundaries.length; i++)
            markBoundaries[i] = boundaries[i];
        for (int i = 0; i < boundaries.length; i++)
            this.ontarioMarks[i] = ontarioMarks[i];
    }
    /**
     * Converts IB Mark to Ontario marks.
     *
     * @param rawMark the raw mark
     * @return the Ontario Mark
     */
    public int convert (double rawMark)
    {
        double lowerThird;
        double middleThird;

        for (int i = this.markBoundaries.length - 2; i >= 0; i--)
        {
            if (rawMark > this.markBoundaries[i])
            {
                lowerThird = ((markBoundaries[i+1] -
                        markBoundaries[i]) / 3)
                        + markBoundaries[i];
                middleThird = ((markBoundaries[i+1] - markBoundaries[i]) * 2
                        / 3) + markBoundaries[i];

                if (rawMark <= lowerThird)
                    return ontarioMarks[i];
                else if (rawMark <= middleThird)
                {
                    double temp = (ontarioMarks[i + 1] - ontarioMarks[i]) / 2;
                    return  (int)(temp + ontarioMarks[i]);
                }
                else
                    return ontarioMarks[i + 1];
            }
        }
        return -1;
    }

    /**
     * Returns a string of their IB score
     *
     * @param rawMark
     * @return IB Score
     */
    public String IBScore (double rawMark)
    {
        double lowerThird;
        double middleThird;

        for (int i = this.markBoundaries.length - 2; i >= 0; i = i - 2)
        {
            if (rawMark >= this.markBoundaries[i] && rawMark != 100)
            {
                lowerThird = ((markBoundaries[i+1] - markBoundaries[i]) / 3)
                        + markBoundaries[i];
                middleThird = ((markBoundaries[i+1] - markBoundaries[i]) * 2
                        / 3) + markBoundaries[i];

                if (rawMark <= lowerThird)
                    return ((int)Math.ceil(i/2)+1 + "-");
                else if (rawMark > middleThird)
                    return ((int)Math.ceil(i/2)+1 + "+");
                else if (rawMark <= middleThird)
                    return ((int)Math.ceil(i/2)+1 + "");
            }
        }
        if (rawMark == 100)
            return "7+";
        else
            return "-";
    }
}
