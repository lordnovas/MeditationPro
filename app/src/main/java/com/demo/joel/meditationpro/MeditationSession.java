package com.demo.joel.meditationpro;

/**
 * Created by jostov on 6/15/15.
 */
public class MeditationSession
{
    private final int HIGHESTMED = 100;
    /*
    one order of magnitude greater than the highest possible meditation value

     */
    private final int RESOLUTION = 10;
    private final int BUFFERSIZE = 1000;
    private String sessionTitle;
    private int [] dataBuffer;
    private int currentPosition;

    /*
    Brainwave codes
     */
    private final int alpha = 0;
    private final int beta = 1;
    private final int gamma= 2;
    private final int delta = 3;
    private final int epsilon = 4;
    private final int theta = 5;

    /*
    Accepts the length in seconds
     */
    public MeditationSession(int length){
    }

    /*
    Gratuitously overloaded constructor that also accepts
    a title.
     */
    public MeditationSession(int length, String title){
        sessionTitle = title;
        int size = (BUFFERSIZE > length * (1000/RESOLUTION))? length * 1000/RESOLUTION:BUFFERSIZE;
        dataBuffer = new int[size];
        currentPosition = 0;
    }

    /*
    This will add a data point to the buffer, and will dump the buffer if it
    gets too big.
    I suggest using the final brainwave codes for this, but this is probably
    a really shitty way of implementing them. Please let me know of improvements.
     */
    public void addPoint(int meditationScore, int dominantBrainwave){
        currentPosition++;
        if (currentPosition > dataBuffer.length){
            dumpBuffer();
        }
        dataBuffer[currentPosition] = meditationScore + dominantBrainwave * HIGHESTMED;
    }

    /*
    dumpbuffer empties the current buffer into the database and resets the currentPosition.
    Depending on how the database is set up, this can be done in several ways,

    Possibilities
    ---------------------
    - Each session has its own dedicated subtable, and the column represents some
    interval of data collected.

    - Each session is stored in one db row with the new data appended to the end
     as the buffer is filled

    -Other stuff
     */
    public void dumpBuffer(){
        //DB store method goes here
        currentPosition = 0;
    }

    public String getSessionTitle()
    {
        return sessionTitle;
    }

    public void setSessionTitle(String sessionTitle)
    {
        this.sessionTitle = sessionTitle;
    }

    public int getCurrentPosition()
    {
        return currentPosition;
    }

    public void setCurrentPosition(int currentPosition)
    {
        this.currentPosition = currentPosition;
    }

    /*
    GetMedScoreAt returns the meditation score at the desired index
    if no index is provided, it will return the most recently
    set value
     */
    public int getMedScoreAt(int index){
        return dataBuffer[index] % HIGHESTMED;
    }
    public int getMedScoreAt(){
        return getMedScoreAt(currentPosition);
    }

    /*
    GetDWaveAt returns the dominant brainwave code at the desired index
    if no index is provided, it will return the most recently
    set value
     */
    public int getDWaveAt(int index){
        return dataBuffer[index] / HIGHESTMED;
    }
    public int getDWaveAt(){
        return getDWaveAt(currentPosition);
    }

    public int[] getDataBuffer()
    {
        return dataBuffer;
    }

    public void setDataBuffer(int[] dataBuffer)
    {
        this.dataBuffer = dataBuffer;
    }
}
