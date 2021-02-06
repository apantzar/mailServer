/**
 * Buffer class is responsible for what the buffer contains,
 * to add new elements into the buffer, to clean buffer string,
 * to return the value of the buffer and checks if the buffer is empty
 * or not.
 */


package com.pantz.utils;

public class BufferClass {

    private String variableOfBuffer, returnableValue , notDefaultIndex , spacerStringPD , theUIpart="";


    /**
     * This is an "empty" constructor in order to call the constructor
     * with the String index instead of the empty default constructor.
     */
    public BufferClass(){
        this("");
    }

    public BufferClass(String index){
        setVariableOfBuffer(index);
    }


    /**
     * A simple adder
     * @param index --> the index that will be added into the buffer
     */

    public void adder(String index){
        this.variableOfBuffer = this.variableOfBuffer + index;

    }

    /**
     * That will clean buffer's value
     */

    public void cleaner(){
        this.variableOfBuffer ="";
    }



    public void setVariableOfBuffer(String index){
        variableOfBuffer = index;
    }


    /**
     *
     * @return returnableValue --> the value that will have the buffer each time
     * this method use extra String variable: returnableValue since the buffer
     * should be cleaned after the pushing. As a result returnableValue variable is
     * used as temporary variable to store the buffer's value.
     */

    public String push(){

        this.returnableValue = variableOfBuffer;
        cleaner();
        return returnableValue;

    }


    /**This method checks if the buffer is empty or
     * not.
     * @return true or false
     */

    public boolean emptyChecker(){
        if(variableOfBuffer.isEmpty()){
            return true;
        }else{
            return  false;
        }
    }


    /**
     * This method will be used for that strings with a given size.
     * @param size ---> size of the string that will be insert
     * @param index  ---> the index value that will be insert
     */
    public void addStringWithSize(int size , String index){
        notDefaultIndex = index;
        if(size - index.length() <= 0){
            notDefaultIndex = notDefaultIndex.substring(0,size);

        }else{

            spacerStringPD = "";
            for(int counter=0; counter< size - index.length(); counter++){

                notDefaultIndex = notDefaultIndex + spacerStringPD;
            }

        }

        adder(notDefaultIndex);

    }


    /**
     * Building some UI
     * @param howMany ---> how many times to add the symbol
     * @param symbolToPrint ---> the symbol that will add.
     */
    public void addDisplayLine(int howMany , char symbolToPrint){
        for(int counter2=0; counter2<howMany;counter2++){

            adder(theUIpart+ System.lineSeparator());

        }

    }


    //to change line

    public void changeLine(){
        adder(System.lineSeparator());
    }




}
