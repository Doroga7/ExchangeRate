/**
 * класс для работы с данными после выгрузки из БД
 * TODO в разработке
 */
class WorkingArray {
    private String[][] workingArray = new String[34][5];

    public String[][] getWorkingArray() {
        return workingArray;
    }

    public void setWorkingArray(String[][] workingArray) {
        this.workingArray = workingArray;
    }

    public void saveArray (int i, String numCode, String charCode, String nominal, String name, String value){
        workingArray[i][0] = numCode;
        workingArray[i][1] = charCode;
        workingArray[i][2] = nominal;
        workingArray[i][3] = name;
        workingArray[i][4] = value;
        System.out.println(workingArray.toString());
    }
}



